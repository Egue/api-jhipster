/**
 * printbridge-client.js
 * Librería mínima para conectarse a printbridge desde el navegador.
 * Uso: <script src="printbridge-client.js"></script>
 */
class PrintBridge {
  constructor(url = "ws://127.0.0.1:8181") {
    this.url = url;
    this.ws = null;
    this.pending = new Map(); // id -> {resolve, reject}
  }

  connect() {
    return new Promise((resolve, reject) => {
      this.ws = new WebSocket(this.url);
      this.ws.onopen = () => resolve();
      this.ws.onerror = (e) => reject(e);
      this.ws.onmessage = (evt) => this._handleMessage(evt);
      this.ws.onclose = () => {
        for (const { reject } of this.pending.values()) {
          reject(new Error("Conexión cerrada con printbridge"));
        }
        this.pending.clear();
      };
    });
  }

  _handleMessage(evt) {
    const msg = JSON.parse(evt.data);
    const waiter = this.pending.get(msg.id);
    if (!waiter) return;
    this.pending.delete(msg.id);

    if (msg.type === "result") {
      msg.success ? waiter.resolve(msg) : waiter.reject(new Error(msg.message));
    } else {
      waiter.resolve(msg);
    }
  }

  _send(payload) {
    return new Promise((resolve, reject) => {
      const id = crypto.randomUUID();
      this.pending.set(id, { resolve, reject });
      this.ws.send(JSON.stringify({ ...payload, id }));
    });
  }

  ping() {
    return this._send({ type: "ping" });
  }

  listPrinters() {
    return this._send({ type: "list_printers" }).then((r) => r.printers);
  }

  /**
   * Manda bytes RAW (ej. comandos ESC/POS) a una impresora.
   * @param {string} target - "network:192.168.1.50:9100" | "usb:/dev/usb/lp0" | "system:NombreImpresora"
   * @param {Uint8Array} bytes
   */
  printRaw(target, bytes) {
    return this._send({ type: "print", target, data: u8ToBase64(bytes) });
  }
}

// Codifica un Uint8Array en base64 por tramos para no rebasar el límite de
// argumentos de Function.prototype.apply con salidas largas (ESC/POS).
function u8ToBase64(bytes) {
  let binary = "";
  const CHUNK = 0x8000;
  for (let i = 0; i < bytes.length; i += CHUNK) {
    binary += String.fromCharCode.apply(null, bytes.subarray(i, i + CHUNK));
  }
  return btoa(binary);
}

// ---- Ejemplo de uso: imprimir un ticket simple con ESC/POS ----
async function ejemploTicket() {
  const bridge = new PrintBridge();
  await bridge.connect();

  console.log("Impresoras del sistema:", await bridge.listPrinters());

  const ESC = 0x1b;
  const GS = 0x1d;
  const encoder = new TextEncoder();

  const bytes = new Uint8Array([
    ESC, 0x40, // Inicializar impresora
    ESC, 0x61, 0x01, // Centrar texto
    ...encoder.encode("MI TIENDA\n"),
    ESC, 0x61, 0x00, // Alinear izquierda
    ...encoder.encode("--------------------------------\n"),
    ...encoder.encode("1x Producto A         $10.00\n"),
    ...encoder.encode("2x Producto B         $20.00\n"),
    ...encoder.encode("--------------------------------\n"),
    ...encoder.encode("TOTAL:                $30.00\n\n\n"),
    GS, 0x56, 0x00, // Cortar papel
  ]);

  // Ajusta el target según tu impresora:
  //await bridge.printRaw("network:192.168.1.50:9100", bytes);
  await bridge.printRaw("system:TM20", bytes);
  // o: await bridge.printRaw("usb:/dev/usb/lp0", bytes);
}
