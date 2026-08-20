use std::io::Write;
use std::net::TcpStream;
use std::time::Duration;

/// Un "target" de impresora se identifica con un string tipo:
///   network:192.168.1.50:9100   -> impresora térmica en red (RAW socket, típico puerto 9100)
///   usb:/dev/usb/lp0            -> dispositivo RAW en Linux (impresora USB expuesta como archivo)
///   system:NombreImpresora      -> usa el spooler del sistema operativo (CUPS en Unix, spooler en Windows)
///
/// Esto imita lo que hace QZ Tray: puedes mandar bytes ESC/POS crudos sin pasar
/// por el diálogo de impresión del navegador.
pub fn send_raw(target: &str, data: &[u8]) -> Result<(), String> {
    if let Some(rest) = target.strip_prefix("network:") {
        return send_network(rest, data);
    }
    if let Some(path) = target.strip_prefix("usb:") {
        return send_device_file(path, data);
    }
    if let Some(name) = target.strip_prefix("system:") {
        return send_via_system(name, data);
    }
    Err(format!(
        "Target de impresora inválido: '{target}'. Usa network:HOST:PORT, usb:/ruta o system:NOMBRE"
    ))
}

/// Envía bytes crudos por TCP. La mayoría de impresoras térmicas de red
/// (Epson, Star, genéricas chinas compatibles ESC/POS) escuchan en el puerto 9100.
fn send_network(host_port: &str, data: &[u8]) -> Result<(), String> {
    let mut stream = TcpStream::connect(host_port)
        .map_err(|e| format!("No se pudo conectar a {host_port}: {e}"))?;
    stream
        .set_write_timeout(Some(Duration::from_secs(5)))
        .ok();
    stream
        .write_all(data)
        .map_err(|e| format!("Error escribiendo al socket: {e}"))?;
    stream.flush().map_err(|e| e.to_string())?;
    Ok(())
}

/// Escribe directamente al archivo de dispositivo (típico en Linux: /dev/usb/lp0).
/// El usuario debe tener permisos sobre ese dispositivo (grupo `lp` en Linux).
#[cfg(unix)]
fn send_device_file(path: &str, data: &[u8]) -> Result<(), String> {
    use std::fs::OpenOptions;
    let mut f = OpenOptions::new()
        .write(true)
        .open(path)
        .map_err(|e| format!("No se pudo abrir dispositivo {path}: {e}"))?;
    f.write_all(data).map_err(|e| e.to_string())?;
    Ok(())
}

#[cfg(windows)]
fn send_device_file(_path: &str, _data: &[u8]) -> Result<(), String> {
    Err("usb:/ruta no está soportado en Windows; usa system:NOMBRE en su lugar".to_string())
}

/// Manda el trabajo como RAW al spooler del sistema operativo.
/// - Unix (Linux/macOS con CUPS): usa `lp -d NOMBRE -o raw`
/// - Windows: escribe el archivo temporal y lo copia en modo binario al share de la impresora
fn send_via_system(printer_name: &str, data: &[u8]) -> Result<(), String> {
    #[cfg(unix)]
    {
        use std::io::Write as _;
        use std::process::{Command, Stdio};
        let mut child = Command::new("lp")
            .args(["-d", printer_name, "-o", "raw"])
            .stdin(Stdio::piped())
            .spawn()
            .map_err(|e| format!("No se pudo ejecutar 'lp': {e}"))?;
        child
            .stdin
            .as_mut()
            .ok_or("No se pudo abrir stdin de lp")?
            .write_all(data)
            .map_err(|e| e.to_string())?;
        let status = child.wait().map_err(|e| e.to_string())?;
        if !status.success() {
            return Err(format!("'lp' terminó con error: {status}"));
        }
        Ok(())
    }
    #[cfg(windows)]
    {
        // Enfoque simple: escribir a un archivo temporal único y copiarlo en modo binario
        // al nombre de la impresora compartida (\\localhost\NombreImpresora) o puerto.
        // Los procesos se lanzan con CREATE_NO_WINDOW para que no aparezca una
        // ventana de cmd a mitad de impresión.
        // Para producción real conviene usar la API Win32 Spooler (winspool.drv)
        // vía el crate `windows` para más control (StartDocPrinter/WritePrinter).
        use std::fs;
        use std::os::windows::process::CommandExt;
        use std::process::Command;
        use std::sync::atomic::{AtomicU32, Ordering};

        const CREATE_NO_WINDOW: u32 = 0x0800_0000;

        static NEXT_JOB: AtomicU32 = AtomicU32::new(0);
        let file_name = format!(
            "printbridge_{}_{}.prn",
            std::process::id(),
            NEXT_JOB.fetch_add(1, Ordering::Relaxed)
        );
        let tmp = std::env::temp_dir().join(file_name);
        fs::write(&tmp, data).map_err(|e| e.to_string())?;
        let copy = Command::new("cmd")
            .creation_flags(CREATE_NO_WINDOW)
            .args([
                "/C",
                "copy",
                "/b",
                tmp.to_str().unwrap(),
                &format!("\\\\localhost\\{printer_name}"),
            ])
            .status()
            .map_err(|e| e.to_string())?;
        let _ = fs::remove_file(&tmp);
        if !copy.success() {
            return Err("Falló el copy /b hacia la impresora".to_string());
        }
        Ok(())
    }
    #[cfg(not(any(unix, windows)))]
    {
        Err("Plataforma no soportada".to_string())
    }
}
