package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.EmailCampaignDetalle;
import com.comunicamosmas.api.domain.SystemConfig;
import com.comunicamosmas.api.repository.ISystemConfigDao;
import com.comunicamosmas.api.service.dto.RespuestaGeneracionPDFFactura;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;
import com.resend.Resend;
import com.resend.services.emails.model.Attachment;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResendMailServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(ResendMailServiceImpl.class);

    @Autowired
    ISystemConfigDao systemConfigDao;

    @Autowired
    IZipFileCreatorService zipFileCreatorService;

    @Autowired
    IEmailCampaignDetalleService emailCampaignDetalleService;

    public String sendMailAttachments(RespuestaGeneracionPDFFactura factura , EmailCampaignDetalle destino ){
        SystemConfig api_key = systemConfigDao.findByOrigen("RESENDMAIL_API");
        SystemConfig from = systemConfigDao.findByOrigen("RESEND_FROM");
        Resend resend = new Resend(api_key.getComando());

        String subject = factura.getNit()+";"+factura.getRazon_social()+";"+factura.getPrefijo()+";"+factura.getFactura()+";"+factura.getCodigoDocumento()+";"+factura.getNameComercial();
        String plantilla = this.loadHTML("templates/mail/facturacionHTML.html");
        log.debug("NAME_EMPRESA   = [{}]", factura.getRazon_social());
        log.debug("NAME_CLIENTE   = [{}]", factura.getDestinatario());
        log.debug("FACTURA        = [{}]", factura.getFactura());
        log.debug("FECHA_LIMITE   = [{}]", factura.getFechaLimite());
        log.debug("VALOR_TOTAL    = [{}]", factura.getValorPagar());
        log.debug("EMITE          = [{}]", factura.getMesGenerado());
        log.debug("MAIL_CONTACTO  = [{}]", factura.getDestinatario());
        log.debug("ADDRESS_EMPRESA= [{}]", factura.getDireccionEmpresa());
        String html = this.replaceData(plantilla  ,  Map.of(
            "NAME_EMPRESA" , factura.getRazon_social(),
            "NAME_CLIENTE" , factura.getDestinatario(),
            "FACTURA" , factura.getFactura(),
            "FECHA_LIMITE" , factura.getFechaLimite(),
            "VALOR_TOTAL" , factura.getValorPagar(),
            "EMITE" , factura.getMesGenerado(),
            "MAIL_CONTACTO" , factura.getDestinatario(),
            "ADDRESS_EMPRESA" , factura.getDireccionEmpresa()
        ));

        // Determinar ruta del archivo según origen
        String pathConverter = destino.getOrigen().equals("A") ? factura.getPathZIP() : factura.getPathPDF();

        if (pathConverter == null || pathConverter.trim().isEmpty()) {
            throw new ExceptionNullSql(
                new Date(),
                "Ruta de archivo vacía",
                String.format("No se encontró ruta de archivo %s para factura %s",
                    destino.getOrigen().equals("A") ? "ZIP" : "PDF",
                    destino.getFactura())
            );
        }

        log.debug("   📁 Ruta archivo: {}", pathConverter);

        AttachmentData attachmentData = this.attachmentDocument(destino , factura);
        Attachment attachment = Attachment.builder()
            .fileName(attachmentData.name64)
            .content(attachmentData.file64)
            .build();

        CreateEmailOptions params = CreateEmailOptions.builder()
            .from(from.getComando())
            .to(destino.getEmail())
            .subject(subject)
            .html(html)
            .attachments(List.of(attachment))
            .build();

        try {
            CreateEmailResponse data = resend.emails().send(params);
            String respuestaJson = data.getId();
            destino.setRespuestaMailRelay(respuestaJson);
            destino.setProcesado(2);
            emailCampaignDetalleService.save(destino);
            return "Correo enviado con id: " + data.getId();
        } catch (Exception e) {
            destino.setRespuestaMailRelay("Error de conexion: " + e.getMessage());
            destino.setProcesado(2);
            emailCampaignDetalleService.save(destino);
            throw new RuntimeException(e);
        }
    }

    public AttachmentData attachmentDocument(EmailCampaignDetalle destino , RespuestaGeneracionPDFFactura respondeGeneracion){

        String pathConverter = destino.getOrigen().equals("A") ? respondeGeneracion.getPathZIP() : respondeGeneracion.getPathPDF();
        if (pathConverter == null || pathConverter.trim().isEmpty()) {
            throw new ExceptionNullSql(
                new Date(),
                "Ruta de archivo vacía",
                String.format("No se encontró ruta de archivo %s para factura %s",
                    destino.getOrigen().equals("A") ? "ZIP" : "PDF",
                    destino.getFactura())
            );
        }
        log.debug("   📁 Ruta archivo: {}", pathConverter);

        // Verificar que el archivo existe
        File archivoAdjunto = new File(pathConverter);
        if (!archivoAdjunto.exists()) {
            throw new ExceptionNullSql(
                new Date(),
                "Archivo no encontrado",
                "El archivo no existe en ruta: " + pathConverter
            );
        }

        log.debug("   ✅ Archivo existe: {} ({} bytes)",
            archivoAdjunto.getName(),
            archivoAdjunto.length());
        // Codificar en base64
        log.debug("   🔐 Codificando archivo en Base64...");
        String contentCodificadoBase64 = zipFileCreatorService.codificarBase64(pathConverter);

        if (contentCodificadoBase64 == null || contentCodificadoBase64.trim().isEmpty()) {
            throw new ExceptionNullSql(
                new Date(),
                "Error en codificación Base64",
                "La codificación Base64 del archivo retornó vacío"
            );
        }

        log.debug("   ✅ Base64 generado: {} caracteres", contentCodificadoBase64.length());
        AttachmentData response = new AttachmentData();
        response.setFile64(contentCodificadoBase64);
        response.setName64(archivoAdjunto.getName());
        return response;
    }

    public String loadHTML(String nameFile){
        String ruta = nameFile.startsWith("/") ? nameFile.substring(1) : nameFile;

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(ruta)){
            if(inputStream == null){
                throw new IllegalAccessException("Plantilla not found" + nameFile);
            }
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream , StandardCharsets.UTF_8))) {
                    return reader.lines().collect(Collectors.joining("\n"));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al leer plantilla html: " + e.getMessage());
        }
    }

    public String replaceData(String html , Map<String, String> data){
        if(data == null || data.isEmpty()){
            return null;
        }

        String result = html;

        for(Map.Entry<String, String> entry : data.entrySet()){
            String placeHolder = "["+entry.getKey()+"]";
            String value = entry.getValue() != null ? entry.getValue() : "";
            result = result.replace(placeHolder , value);
        }

        return result;
    }

    private static class AttachmentData{
        private String file64;
        private String name64;

        public void setFile64(String file){
            this.file64 = file;
        }

        public String getFile64(){
            return this.file64;
        }

        public void setName64(String name64){
            this.name64 = name64;
        }

        public String getName64(){
            return name64;
        }
    }
}
