package com.comunicamosmas.api.web.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.domain.MigracionTarifa;
import com.comunicamosmas.api.service.IMigracionTarifaService;
import com.comunicamosmas.api.service.dto.MigracionTarifaFindContratoDTO;
import com.comunicamosmas.api.service.dto.MigracionTarifasInfoDTO;

@RestController
@CrossOrigin
@RequestMapping("/api/controlmas")
public class MigracionTarifaController {

	@Autowired
	IMigracionTarifaService migracionTarifaService;

	@GetMapping("/migracionTarifa/infobyidcontrato/{id}")
	public ResponseEntity<?> infoByIdContrato(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		try {
			MigracionTarifasInfoDTO result = migracionTarifaService.migracionTarifaInfo(id);
			response.put("response", result);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.put("response", e.getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}

	/** ENDPOINT SAVE */
	@PostMapping("/migracionTarifa/save")
	public ResponseEntity<?> save(@RequestBody MigracionTarifa migracionTarifa) {
		Map<String, Object> response = new HashMap<>();

		try {
			MigracionTarifa save = migracionTarifaService.save(migracionTarifa);

			response.put("response", save);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.put("response", e.getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * ENDPOINT GET BUSCAR CAMBIO DE TARIFA POR CONTRATO
	 */
	@GetMapping("/migracionTarifa/findbycontrato/{contrato}")
	public ResponseEntity<?> findbycontrato(@PathVariable Long contrato) {
		Map<String, Object> response = new HashMap<>();
		try {

			MigracionTarifaFindContratoDTO result = migracionTarifaService.migracionFindByContrato(contrato);

			response.put("response", result);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.put("response", e.getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}

	/***/
	@PostMapping("/migracionTarifa/cambioplan")
	public ResponseEntity<?> cambioPlan(@RequestParam Long idContrato) {
		Map<String, Object> response = new HashMap<>();
		try {

			response.put("response", "ok");

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.put("response", e.getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * ENDPOINT GET LISTAR CAMBIOS DE TARIFAS POR CONTRATO
	 */
	@GetMapping("/migraciontarifa/listbycontrato/{id}")
	public ResponseEntity<?> findAllByContrato(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {

			List<MigracionTarifaFindContratoDTO> result = migracionTarifaService.findAll(id);
			response.put("response", result);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.put("response", e.getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}

}
