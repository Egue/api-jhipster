package com.comunicamosmas.api.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.Empresa;
import com.comunicamosmas.api.domain.SystemConfig;
import com.comunicamosmas.api.service.ISystemConfigService;
import com.comunicamosmas.api.service.IZipFileCreatorService;
import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;
import com.comunicamosmas.api.service.dto.RespuestaGeneracionPDFFactura;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;

@Service
public class ZipFileCreatorServiceImpl implements IZipFileCreatorService{

    @Autowired
    ISystemConfigService systemService;

    @Override
    public String zipFileFactura(RespuestaGeneracionPDFFactura respuestaPDF, EmailCampaignDetalleDTO detalle) {
        // TODO Auto-generated method stub
         
        //String zipPath = "/home/programador/Documentos/"+respuestaPDF.getNit()+respuestaPDF.getFactura()+".zip";
        SystemConfig system = systemService.findByOrigen("facturas");

        String zipPath = system.getComando()+respuestaPDF.getNit()+detalle.getIdCliente()+ detalle.getOrigen()+respuestaPDF.getFactura()+".zip";
        try (FileOutputStream fos = new FileOutputStream(zipPath);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        FileInputStream fis1 = new FileInputStream(respuestaPDF.getPathPDF());
        FileInputStream fis2 = new FileInputStream(respuestaPDF.getPathXML()))
        {
             // Agregar el primer archivo al archivo ZIP
             File file1 = new File(respuestaPDF.getPathPDF());

             ZipEntry zipEntry1 = new ZipEntry(file1.getName());
             zipOut.putNextEntry(zipEntry1);
             byte[] buffer = new byte[1024];
             int length;
             while ((length = fis1.read(buffer)) > 0) {
                 zipOut.write(buffer, 0, length);
             }
             fis1.close();
 
             // Agregar el segundo archivo al archivo ZIP
             File file2 = new File(respuestaPDF.getPathXML());
             ZipEntry zipEntry2 = new ZipEntry(file2.getName());
             zipOut.putNextEntry(zipEntry2);
             while ((length = fis2.read(buffer)) > 0) {
                 zipOut.write(buffer, 0, length);
             }
             fis2.close();
 
             zipOut.close();

            return zipPath;

        }catch (IOException e) {
            
            throw new ExceptionNullSql(new Date(), "error generando zip", e.getMessage());
        }

        
    }

    @Override
    public String codificarBase64(String path) {
       
        byte[] contenidoZip;

        try {
            
            contenidoZip = Files.readAllBytes(Path.of(path));
            
            String contenidoCodificado64 = Base64.getEncoder().encodeToString(contenidoZip);
                        
            
            return contenidoCodificado64;

        } catch (IOException e) {
            // TODO Auto-generated catch block
             throw new ExceptionNullSql(new Date(), "Error codificando archivo en base 64 ", e.getMessage());
        }
         
        
    }
    
}
