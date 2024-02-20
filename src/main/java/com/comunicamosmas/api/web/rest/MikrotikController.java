package com.comunicamosmas.api.web.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.domain.MikrotikHijoSimpleQueue;
import com.comunicamosmas.api.domain.MikrotikPadreSimpleQueue;
import com.comunicamosmas.api.domain.Tarifa;
import com.comunicamosmas.api.domain.WinmaxPass;
import com.comunicamosmas.api.service.IContratoService;
import com.comunicamosmas.api.service.IMikrotikPadreSimpleQueueService;
import com.comunicamosmas.api.service.IMikrotikService;
import com.comunicamosmas.api.service.ITarifaService;
import com.comunicamosmas.api.service.dto.MikrotikPPPActiveDTO;
import com.comunicamosmas.api.service.dto.MikrotikPPPProfileDTO;
import com.comunicamosmas.api.service.dto.MikrotikPPPSecretDTO;
import com.comunicamosmas.api.service.dto.MikrotikQueueSimpleDTO;
import com.comunicamosmas.api.service.dto.MikrotikQueueSimpleHijoDTO;
import com.comunicamosmas.api.service.dto.SegmentoIPDTO;
import com.comunicamosmas.api.service.dto.UpdateRemoteAddress;
import com.comunicamosmas.api.service.dto.MikrotikQueueSimplePadreDTO;
import com.comunicamosmas.api.service.dto.ValorStringDTO;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;
 
import me.legrange.mikrotik.MikrotikApiException;

@RestController
@CrossOrigin
@RequestMapping("/api/controlmas")
public class MikrotikController {

	@Autowired
	IMikrotikService mikrotikService;

	@Autowired
	IContratoService contratoService;

	@Autowired
	ITarifaService tarifaService;

	@Autowired
	IMikrotikPadreSimpleQueueService padreSimpleQueueService;

	@GetMapping("/mikrotik/test/{id}")
	public ResponseEntity<?> test(@PathVariable long id) {
		Map<String, Object> response = new HashMap<>();

		try {
			// consultamos la estacion para realizar el test

			response.put("response", "conexión válidada");

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("response", e.getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}

	/** ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::PPOE profiles */
	/** ENDPOINT GET profiles_rb_ PPPOE */
	@GetMapping("/mikrotik/pppoe/profile/{id}")
	public ResponseEntity<?> pppProfile(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		try {
			List<MikrotikPPPProfileDTO> profile = mikrotikService.profileByIdEstacion(id);

			response.put("response", profile);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.put("response", e.getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::secrests:::::::::::::::::::::::
	 */
	/**
	 * ENDPOINT create nuevo secret
	 */
	@PostMapping("/mikrotik/pppsecret/save")
	public ResponseEntity<?> pppSecret(@RequestParam Long idContrato, @RequestParam Long idEstacion,
			@RequestParam String profile,
			@RequestParam String nameSecret, @RequestParam String pass) {
		Map<String, Object> response = new HashMap<>();
		try {
			WinmaxPass winmaxPass = mikrotikService.pppSecrect(idContrato, idEstacion, profile, nameSecret, pass);
			response.put("response", winmaxPass);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("response", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * ENDPOINT GET buscar secret del pppoe por estacion
	 */
	@GetMapping("mikrotik/pppoe/secret/{idEstacion}")
	public ResponseEntity<?> pppoeSecrectFindAll(@PathVariable Long idEstacion) {
		Map<String, Object> response = new HashMap<>();
		try {

			List<MikrotikPPPSecretDTO> result = mikrotikService.pppoeSecrectFindAll(idEstacion);
			response.put("response", result);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.put("response", e.getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}

	/*
	 * endpoint buscar contratos en pppOE secret
	 **/
	//
	@PostMapping("/mikrotik/pppoe/secret/findByName")
	public ResponseEntity<?> pppoeSecretFindByName(@RequestParam Long idContrato) {
		Map<String, Object> response = new HashMap<>();

		try {
			Contrato contrato = contratoService.findById(idContrato);

			if (contrato == null) {
				response.put("response", "No se ha encontrato el contrato");

				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}

			if (contrato.getIdEstacion().toString().equals("0")) {
				response.put("response", "Contrato no asociado a una estación");

				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}

			ValorStringDTO result = mikrotikService.pppeoSecretFindByName(idContrato, contrato.getIdEstacion());
 
			response.put("response", result);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (ExceptionNullSql e) {

			response.put("response", e.getMessage() + ":" + e.getDetails());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

		} catch (Exception e) {

			response.put("response", e.getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * ENDPOINT POST pppoe cambiar profile
	 */
	@PostMapping("/mikrotik/pppoe/secret/changeProfile")
	public ResponseEntity<?> pppoeChangeProfile(@RequestParam String idrb, @RequestParam Long idtarifa,
			@RequestParam Long idContrato, @RequestParam Long idUser, @RequestParam Long idMigracion) {
		Map<String, Object> response = new HashMap<>();

		try {

			List<Map<String, String>> result = mikrotikService.pppoeSecretChangeProfile(idContrato, idtarifa, idrb,
					idUser, idMigracion);

			response.put("response", result);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		}catch(ExceptionNullSql e){
			response.put("response", e.getMessage() + e.getDetails());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		 catch (Exception e) {
			response.put("response", e.getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * actualizacion del remote address en el secrect
	 */
	@PostMapping("/mikrotik/ppp/secret/updatedremoteaddres")
	public ResponseEntity<?> pppUpdatedremoteaddres(@RequestBody UpdateRemoteAddress remote) {
		Map<String, Object> response = new HashMap<>();
		try {
			WinmaxPass winmaxPass = mikrotikService.updatedRemoteAddress(remote);

			response.put("response", "Remote address actualizado");

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.put("response", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::PPOE active
	 * connections
	 */
	/*
	 * ENDPOINT
	 * pppoe buscar activeConections por nombre
	 **/
	@GetMapping("/mikrotik/pppoe/active/findByname/{contrato}")
	public ResponseEntity<?> pppoeActiveFindByName(@PathVariable Long contrato)

	{
		Map<String, Object> response = new HashMap<>();

		try {
			Contrato con = contratoService.findById(contrato);

			ValorStringDTO result = mikrotikService.pppoeActiveFindByName(con.getIdEstacion(), contrato);

			response.put("response", result);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (ExceptionNullSql e) {
			response.put("response", e.getMessage() + ":" + e.getDetails());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			response.put("response", e.getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * ENDPOINT pppoe remover en active connections
	 */
	@PostMapping("/mikrotik/pppoe/active/remove")
	public ResponseEntity<?> pppoeActiveRemove(@RequestParam Long idContrato, @RequestParam String idrb) {
		Map<String, Object> response = new HashMap<>();
		try {
			Contrato contrato = contratoService.findById(idContrato);

			mikrotikService.pppoeActiveRemoveById(contrato.getIdEstacion(), idrb);

			response.put("response", "Eliminado de Active Connections");

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}catch(ExceptionNullSql e)
		{
			response.put("response", e.getMessage() + ":" +e.getDetails());
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

			} catch (Exception e) {

			response.put("response", e.getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * ENDPOINT consulta los profiles actives por estacion
	 */
	@GetMapping("/mikrotik/pppoe/active/{id}")
	public ResponseEntity<?> pppActive(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		try {

			List<MikrotikPPPActiveDTO> result = mikrotikService.pppoeActive(id);

			response.put("response", result);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.put("response", e.getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * :::::::::::::::::::::::::::::::::::::::::Queue
	 * Simple:::::::::::::::::::::::::::::::::::
	 */
	/**
	 * GETMAPPING buscar lista de simpleQueue
	 */
	@GetMapping("/mikrotik/queuesimple/all/{idEstacion}")
	public ResponseEntity<?> queueSimpleAll(@PathVariable Long idEstacion) {
		Map<String, Object> response = new HashMap<>();
		try {

			List<MikrotikQueueSimpleDTO> result = mikrotikService.QueueSimpleAll(idEstacion);

			response.put("response", result);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("response", e.getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * crear nuevo padre en colas simple
	 */
	@PostMapping("/mikrotik/padreQueuesimple/save")
	public ResponseEntity<?> padreQueuesimple(@RequestBody MikrotikQueueSimplePadreDTO create) {
		Map<String, Object> response = new HashMap<>();
		try {

			MikrotikPadreSimpleQueue padre = mikrotikService.padreQueuesimple(create);
			
			response.put("response", padre);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (ExceptionNullSql e) {
			response.put("response", e.getMessage() + ":" + e.getDetails());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			response.put("response", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * crear nuevo hijo en colas simple de un padre ya existente
	 */
	@PostMapping("/mikrotik/hijoQueuesimple/save")
	public ResponseEntity<?> hijoQueuesimple(@RequestBody MikrotikQueueSimpleHijoDTO padre) {
		Map<String, Object> response = new HashMap<>();
		try {

			MikrotikHijoSimpleQueue hijo = mikrotikService.hijoQueueSimple(padre);

			response.put("response", hijo);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (ExceptionNullSql e) {
			 

			response.put("response", e.getMessage() + ":" + e.getDetails());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// ir a base de datos y quitar el padre

			response.put("response", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * ACTUALIZAR EL TARGET DEL PADRE
	 */
	@PostMapping("/mikrotik/queuesimple/updatedtarget")
	public ResponseEntity<?> queueSimpleUpdatedTarget(@RequestParam Long idPadre, @RequestParam Long idEstacion) {
		Map<String, Object> response = new HashMap<>();

		try {

			MikrotikPadreSimpleQueue padre = mikrotikService.updatedTargetPadreInRB(idEstacion, idPadre);

			response.put("response", padre);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (MikrotikApiException e) {
			e.printStackTrace();
			response.put("response", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}

	// change hijo simple quee cambio de plan
	@GetMapping("/mikrotik/queuesimple/removeHijo/{idContrato}")
	public ResponseEntity<?> removeHijoSimpleQueue(@PathVariable Long idContrato) {
		Map<String, Object> response = new HashMap<>();

		try {
			mikrotikService.removeHijoSimpleQueue(idContrato);

			response.put("response", "Contrato removido de la lista");

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (ExceptionNullSql e) {
			response.put("response", e.getMessage() + ":" + e.getDetails());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// TODO: handle exception
			response.put("response", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//eliminar padre estacion
	/*@PutMapping("/mikrotik/queuesimple/removePadre/{name}")
	public ResponseEntity<?> deletePadreSimpleQueue(@PathVariable String name , @RequestParam Long idEstacion){
		
	}*/
	@DeleteMapping("/mikrotik/queuesimple/deletepadresimplequeue/{idPadre}")
	public ResponseEntity<?> deleteFather(@PathVariable Long idPadre)
	{
		Map<String, Object> response = new HashMap<>();
		try {

			mikrotikService.removePadreSimpleQueue(idPadre);

			response.put("response", "Padre eliminado");

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			
		} catch (ExceptionNullSql e) {
			response.put("response", e.getMessage() + ":" + e.getDetails());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			// TODO: handle exception
		}catch(Exception e)
		{
			response.put("response", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//::::::::::::::::::::::::::::::::::::::::::::::::::.SCRIPT ACTUALIZACION PROFILE FOR ESTACION
	@GetMapping("/mikrotik/updatedProfile/{idEstacion}")
	public ResponseEntity<?> updatedProfileMikrotik(@PathVariable Long idEstacion){
		Map<String, Object> response = new HashMap<>();
		try {

			List<String> result =  mikrotikService.updatedProfileEstacion(idEstacion);

			response.put("response", result);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			
		} catch (ExceptionNullSql e) {
			response.put("response", e.getMessage() + ":" + e.getDetails());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			// TODO: handle exception
		}catch(Exception e)
		{
			response.put("response", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * MIKROTIK 
	 * module: IP
	 * @param idEstacion
	 */
	@GetMapping("/mikrotik/ip/pool/{id}")
	public ResponseEntity<?> mikrotik_ip_pool(@PathVariable Long id)
	{
		Map<String , Object> response = new HashMap<>();

		try {

			List<Map<String, String>> result = mikrotikService.findInRBIPPool(id);
			response.put("response", result);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (ExceptionNullSql e) {
			response.put("response", e.getMessage() + ":" + e.getDetails());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			// TODO: handle exception
		}catch(Exception e)
		{
			response.put("response", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
