# printbridge

Alternativa mínima y gratuita a QZ Tray para imprimir en impresoras térmicas
(ESC/POS) directamente desde el navegador, sin el diálogo de impresión.
Compilado y probado en este entorno (Rust 1.75, Linux).

## Cómo funciona

```
[Página web] --WebSocket (ws://127.0.0.1:8181)--> [printbridge] --> Impresora
```

1. `printbridge` corre en segundo plano en la máquina del usuario.
2. Tu web se conecta por WebSocket usando `examples/client.js`.
3. Le mandas bytes RAW (ej. comandos ESC/POS) en base64 y el bridge los
   entrega a la impresora por la vía que elijas:
   - `network:HOST:PORT` → socket TCP directo (típico puerto **9100** en
     térmicas de red — Epson, Star, genéricas chinas compatibles).
   - `usb:/dev/usb/lp0` → escribe directo al archivo de dispositivo (Linux).
   - `system:NombreImpresora` → usa el spooler del SO (`lp -o raw` en
     Linux/macOS; en Windows via `copy /b` — ver nota abajo).

## Compilar

```bash
cargo build --release
# binario en target/release/printbridge
```

Para Windows/macOS necesitas compilar en esa plataforma (o usar
cross-compilation / GitHub Actions con matrix de OS), ya que el código usa
`#[cfg(windows)]` / `#[cfg(unix)]` para las rutas específicas de cada SO.

## Correrlo

```bash
./target/release/printbridge
# printbridge escuchando en ws://127.0.0.1:8181
```

Opciones (repetibles):

```bash
./target/release/printbridge --port 8181 --allow-origin=https://midominio.com --allow-origin=http://localhost:4200
```

- `--port N` cambia el puerto (default `8181`).
- `--allow-origin URL` agrega un origen permitido. Sin él, **cualquier**
  pestaña con acceso a `localhost` puede imprimir (útil para desarrollo,
  pero no para producción). Al pasar al menos uno, los handshake WebSocket
  cuyo header `Origin` no coincida se rechazan con HTTP 403.

## Interfaz de Windows (bandeja + panel de logs)

En Windows el binario es una app de bandeja (sin ventana de consola en
release — la negra que veías antes):

- Al arrancar se abre un **panel de logs** que muestra la actividad del
  bridge (conexiones, impresiones OK/fallidas, rechazos por origen).
- Cerrando con la X o **minimizando** el panel, la ventana se oculta pero
  printbridge sigue corriendo.
- El **icono de bandeja** (una impresora azul celeste, generada en el
  código) permite volver a mostrar el panel con doble clic (o clic
  derecho → "Mostrar panel de logs") y salir con clic derecho → "Salir".

Compílalo en release para que la consola no aparezca:

```bash
cargo build --release
./target/release/printbridge --allow-origin=https://midominio.com
```

> En debug (`cargo build` / `cargo run`) sí verás la consola a la vez que
> el panel de logs; es intencional para desarrollo.

## Protocolo (JSON sobre WebSocket)

**Cliente → servidor:**

```json
{ "type": "ping", "id": "abc" }
{ "type": "list_printers", "id": "abc" }
{ "type": "print", "id": "abc", "target": "network:192.168.1.50:9100", "data": "<base64>" }
```

**Servidor → cliente:**

```json
{ "type": "pong", "id": "abc", "version": "0.1.0" }
{ "type": "printers", "id": "abc", "printers": ["EPSON_TM_T20", "HP_LaserJet"] }
{ "type": "result", "id": "abc", "success": true, "message": "OK" }
```

## Lo que falta para producción (a diferencia de QZ Tray)

QZ Tray gratis te daba, además del bridge, estas piezas — decide cuáles
necesitas y las vamos agregando:

1. **Autostart**: ya hay icono en bandeja y app sin consola en Windows;
   falta registrar el arranque automático al iniciar sesión (Task
   Scheduler o el registro `HKCU\Software\Microsoft\Windows\CurrentVersion\Run`),
   un LaunchAgent en macOS y un systemd user service o `.desktop` en Linux.
2. **Restricción de origen**: ya se valida el header `Origin` del handshake
   contra una whitelist (`--allow-origin`). La firma con certificado de
   mensajes (como QZ Tray) sigue pendiente.
3. **Instalador firmado** por plataforma (.msi, .pkg, .deb/.rpm o AppImage)
   para distribución fácil a tus usuarios finales.
4. **Impresión no-RAW** (PDF/HTML) si en algún momento necesitas imprimir
   algo que no sea ESC/POS — QZ Tray también soporta eso vía el driver del
   SO; aquí ahora mismo solo hay RAW.

## Nota sobre impresión RAW en Windows

El fallback `system:Nombre` en Windows usa `copy /b` al share de la
impresora, que funciona para pruebas rápidas pero no es robusto. Para
producción real conviene usar la API Win32 Spooler (`StartDocPrinter` /
`WritePrinter` de `winspool.drv`) vía el crate `windows`, que da control
fino y mejor manejo de errores. Puedo agregarlo si vas a soportar Windows
en serio.
