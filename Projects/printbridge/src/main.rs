#![cfg_attr(all(not(debug_assertions), windows), windows_subsystem = "windows")]

mod printer;
mod tray;

use base64::{engine::general_purpose::STANDARD as B64, Engine};
use futures_util::{SinkExt, StreamExt};
use serde::{Deserialize, Serialize};
use std::process::Command;
use tokio::net::TcpListener;
use tokio_tungstenite::accept_hdr_async;
use tokio_tungstenite::tungstenite::handshake::server::{ErrorResponse, Request, Response};
use tokio_tungstenite::tungstenite::Message;

#[derive(Deserialize)]
#[serde(tag = "type", rename_all = "snake_case")]
enum ClientMessage {
    ListPrinters { id: String },
    Print {
        id: String,
        target: String,
        data: String,
    },
    Ping { id: String },
}

#[derive(Serialize)]
#[serde(tag = "type", rename_all = "snake_case")]
enum ServerMessage {
    Printers {
        id: String,
        printers: Vec<String>,
    },
    Result {
        id: String,
        success: bool,
        message: String,
    },
    Pong {
        id: String,
        version: String,
    },
}

fn list_system_printers() -> Vec<String> {
    #[cfg(target_os = "linux")]
    {
        if let Ok(out) = Command::new("lpstat").arg("-p").output() {
            let text = String::from_utf8_lossy(&out.stdout);
            return text
                .lines()
                .filter_map(|l| l.strip_prefix("printer "))
                .map(|l| l.split_whitespace().next().unwrap_or("").to_string())
                .filter(|s| !s.is_empty())
                .collect();
        }
    }
    #[cfg(target_os = "macos")]
    {
        if let Ok(out) = Command::new("lpstat").arg("-p").output() {
            let text = String::from_utf8_lossy(&out.stdout);
            return text
                .lines()
                .filter_map(|l| l.strip_prefix("printer "))
                .map(|l| l.split_whitespace().next().unwrap_or("").to_string())
                .filter(|s| !s.is_empty())
                .collect();
        }
    }
    #[cfg(target_os = "windows")]
    {
        use std::os::windows::process::CommandExt;
        // CREATE_NO_WINDOW: que el powershell no abra una ventana de consola.
        const CREATE_NO_WINDOW: u32 = 0x0800_0000;
        if let Ok(out) = Command::new("powershell")
            .creation_flags(CREATE_NO_WINDOW)
            .args(["-Command", "Get-Printer | Select-Object -ExpandProperty Name"])
            .output()
        {
            let text = String::from_utf8_lossy(&out.stdout);
            return text
                .lines()
                .map(|l| l.trim().to_string())
                .filter(|s| !s.is_empty())
                .collect();
        }
    }
    Vec::new()
}

fn parse_args() -> Result<(u16, Vec<String>), String> {
    let mut port: u16 = 8181;
    let mut allowed: Vec<String> = Vec::new();
    let mut args = std::env::args().skip(1);
    while let Some(arg) = args.next() {
        if let Some(v) = arg.strip_prefix("--allow-origin=") {
            allowed.push(v.to_string());
        } else if let Some(v) = arg.strip_prefix("--port=") {
            port = v.parse().map_err(|_| format!("Puerto inválido: {v}"))?;
        } else {
            match arg.as_str() {
                "--allow-origin" => {
                    let v = args
                        .next()
                        .ok_or("--allow-origin requiere un valor".to_string())?;
                    allowed.push(v);
                }
                "--port" => {
                    let v = args
                        .next()
                        .ok_or("--port requiere un valor".to_string())?;
                    port = v.parse().map_err(|_| format!("Puerto inválido: {v}"))?;
                }
                "--help" | "-h" => {
                    tray::log(&format!(
                        "HJPrints v{}\nUso: HJPrints [--port N] [--allow-origin URL]...\n  --port N             puerto a escuchar (default 8181)\n  --allow-origin URL    origen permitido (repetible). Sin él,\n                        CUALQUIER página puede imprimir.",
                        env!("CARGO_PKG_VERSION")
                    ));
                    std::process::exit(0);
                }
                other => return Err(format!("Argumento desconocido: {other}")),
            }
        }
    }
    Ok((port, allowed))
}

/// Rechaza (403) el handshake si la whitelist no está vacía y el header
/// Origin del cliente no coincide con un origen permitido.
fn validate_origin(req: &Request, allowed: &[String]) -> Result<(), ErrorResponse> {
    fn normalized(s: &str) -> String {
        s.trim().trim_end_matches('/').to_ascii_lowercase()
    }
    if allowed.is_empty() {
        return Ok(());
    }
    let origin = req
        .headers()
        .get("origin")
        .and_then(|v| v.to_str().ok())
        .map(normalized);
    let authorized = origin
        .as_deref()
        .map(|o| allowed.iter().any(|a| normalized(a) == o))
        .unwrap_or(false);
    if authorized {
        return Ok(());
    }
    match origin {
        Some(o) => tray::log(&format!("Conexión rechazada: origen no autorizado '{o}'")),
        None => tray::log("Conexión rechazada: falta el header Origin"),
    }
    Err(http::Response::builder()
        .status(403)
        .body(Some("Origen no autorizado".to_string()))
        .unwrap())
}

fn reply_to(text: &str) -> ServerMessage {
    let parsed: Result<ClientMessage, _> = serde_json::from_str(text);
    match parsed {
        Ok(ClientMessage::Ping { id }) => ServerMessage::Pong {
            id,
            version: env!("CARGO_PKG_VERSION").to_string(),
        },
        Ok(ClientMessage::ListPrinters { id }) => ServerMessage::Printers {
            id,
            printers: list_system_printers(),
        },
        Ok(ClientMessage::Print { id, target, data }) => match B64.decode(data.as_bytes()) {
            Ok(bytes) => match printer::send_raw(&target, &bytes) {
                Ok(()) => {
                    tray::log(&format!("Impresión OK hacia {target} ({} bytes)", bytes.len()));
                    ServerMessage::Result {
                        id,
                        success: true,
                        message: "OK".to_string(),
                    }
                }
                Err(e) => {
                    tray::log(&format!("Impresión FALLÓ hacia {target}: {e}"));
                    ServerMessage::Result {
                        id,
                        success: false,
                        message: e,
                    }
                }
            },
            Err(e) => {
                tray::log("Impresión FALLÓ: base64 inválido");
                ServerMessage::Result {
                    id,
                    success: false,
                    message: format!("Base64 inválido: {e}"),
                }
            }
        },
        Err(e) => {
            let id = serde_json::from_str::<serde_json::Value>(text)
                .ok()
                .as_ref()
                .and_then(|v| v.get("id").and_then(|i| i.as_str()))
                .map(|s| s.to_string())
                .unwrap_or_default();
            ServerMessage::Result {
                id,
                success: false,
                message: format!("Mensaje inválido: {e}"),
            }
        }
    }
}

async fn handle_connection(stream: tokio::net::TcpStream, allowed_origins: Vec<String>) {
    let callback = move |req: &Request, response: Response| -> Result<Response, ErrorResponse> {
        validate_origin(req, &allowed_origins)?;
        Ok(response)
    };
    let ws_stream = match accept_hdr_async(stream, callback).await {
        Ok(s) => s,
        Err(e) => {
            tray::log(&format!("Handshake rechazado: {e}"));
            return;
        }
    };
    let (mut write, mut read) = ws_stream.split();

    while let Some(msg) = read.next().await {
        let msg = match msg {
            Ok(m) => m,
            Err(_) => break,
        };
        if !msg.is_text() {
            continue;
        }
        let response = match msg.to_text() {
            Ok(text) => reply_to(text),
            Err(e) => ServerMessage::Result {
                id: String::new(),
                success: false,
                message: format!("El mensaje no es texto UTF-8: {e}"),
            },
        };

        let payload = serde_json::to_string(&response).unwrap_or_default();
        if write.send(Message::Text(payload)).await.is_err() {
            break;
        }
    }
}

#[tokio::main]
async fn main() {
    let (port, allowed_origins) = match parse_args() {
        Ok(v) => v,
        Err(e) => {
            tray::log(&format!("Error: {e}"));
            std::process::exit(2);
        }
    };
    let addr = format!("127.0.0.1:{port}");
    let listener = match TcpListener::bind(&addr).await {
        Ok(l) => l,
        Err(e) => {
            tray::log(&format!("Error: no se pudo abrir el puerto {port}: {e}"));
            std::process::exit(1);
        }
    };

    tray::log(&format!("HJPrints v{} arrancando", env!("CARGO_PKG_VERSION")));
    tray::run_ui();

    if allowed_origins.is_empty() {
        tray::log("AVISO: sin --allow-origin cualquier pestaña puede imprimir. Usa --allow-origin=https://tudominio.com");
    } else {
        tray::log(&format!("Orígenes permitidos: {:?}", allowed_origins));
    }
    tray::log(&format!("HJPrints escuchando en ws://{addr}"));

    while let Ok((stream, peer)) = listener.accept().await {
        tray::log(&format!("Cliente conectado: {peer}"));
        let allowed = allowed_origins.clone();
        tokio::spawn(handle_connection(stream, allowed));
    }
}
