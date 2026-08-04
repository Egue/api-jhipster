package com.intinalambrico.posimpre.resource;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.output.PrinterOutputStream;
import com.intinalambrico.posimpre.service.dto.TicketDTO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.print.PrintService;

@Path("/print/tm20")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PrinterResource {

    @POST
    public Response imprimirTicket(TicketDTO request) {
        try {
            // Nombre de la impresora en Windows/Linux
            String printerName = "TM20";

            PrintService printService = PrinterOutputStream.getPrintServiceByName(printerName);
            PrinterOutputStream printable = new PrinterOutputStream(printService);
            EscPos escpos = new EscPos(printable);

            // Estilo y contenido
            escpos.writeLF("--- " + request.getNombreTienda() + " ---")
                .writeLF("Ticket: " + request.getNumero())
                .feed(2)
                .writeLF("Total: $" + request.getTotal())
                .feed(3)
                .cut(EscPos.CutMode.FULL);

            escpos.close();
            return Response.ok("{\"status\":\"ok\"}").build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }
}
