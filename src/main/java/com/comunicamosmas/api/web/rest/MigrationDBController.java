package com.comunicamosmas.api.web.rest;

import java.io.InputStreamReader;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.comunicamosmas.api.service.dto.MigrationDBDTO;
import com.comunicamosmas.api.service.dto.MigrationDBDTO.ClienteDTO;
import com.comunicamosmas.api.service.impl.MigrationDBServiceImpl;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.bean.CsvToBean;

@RestController
@RequestMapping("/api/controlmas/migrations")
public class MigrationDBController {

    private final MigrationDBServiceImpl migrationDBService;

    public MigrationDBController(MigrationDBServiceImpl migrationDBService)
    {
        this.migrationDBService = migrationDBService;
    }
    
    @PostMapping("/clientes")
    public ResponseEntity<?> uploadCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Archivo vacío");
        }

        try {
            InputStreamReader reader = new InputStreamReader(file.getInputStream());

            CsvToBean<ClienteDTO> csvToBean = new CsvToBeanBuilder<MigrationDBDTO.ClienteDTO>(reader)
                    .withType(MigrationDBDTO.ClienteDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withThrowExceptions(true)
                    .build();

            List<MigrationDBDTO.ClienteDTO> clientes = csvToBean.parse();
         /*   if (!csvToBean.getCapturedExceptions().isEmpty()) {
            StringBuilder errorMsg = new StringBuilder("Errores al procesar el CSV:\n");
            for (CsvException ex : csvToBean.getCapturedExceptions()) {
                errorMsg.append("Fila ").append(ex.getLineNumber())
                        .append(": ").append(ex.getMessage()).append("\n");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg.toString());
        }

        // Validar clientes antes de procesar
        for (MigrationDBDTO.ClienteDTO cliente : clientes) {
            if (cliente.getId() == null || cliente.getCod_municipio() == null || cliente.getCod_municipio_domicilio() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Datos incompletos en el cliente: " + cliente.getIdentificacion());
            }
        }*/
            migrationDBService.create_cliente(clientes);
            return ResponseEntity.ok("Procesado");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar el archivo: " + e.getMessage());
        }
    }
}
