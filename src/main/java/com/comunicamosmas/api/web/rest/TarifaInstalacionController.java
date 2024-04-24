package com.comunicamosmas.api.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

import com.comunicamosmas.api.domain.TarifaInstalacion;
import com.comunicamosmas.api.service.ITarifaInstalacionService;

@RestController
@RequestMapping("/api/controlmas")
public class TarifaInstalacionController {
    

    private final ITarifaInstalacionService tarifaInstalacionService;

    public TarifaInstalacionController(ITarifaInstalacionService tarifaInstalacionService)
    {
        this.tarifaInstalacionService = tarifaInstalacionService;
    }

    /*
     * tarifainstalacion?idServicio
     */
    @GetMapping("/tarifainstalacion")
    public ResponseEntity<?> find(@RequestParam("idServicio") Long idServicio , @RequestParam("valor")Long valor)
    {
        try {
            Optional<List<TarifaInstalacion>> list = tarifaInstalacionService.findByIdServicio(idServicio  ,valor);

            return ResponseEntity.status(HttpStatus.OK).body(list);
        } catch (Exception e) {
            // TODO: handle exception

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
