package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.ClausulaPermanencia;
import com.comunicamosmas.api.domain.Cliente;
import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.domain.SystemConfig;
import com.comunicamosmas.api.repository.IClausulaPermanenciaDao;
import com.comunicamosmas.api.repository.IClienteDao;
import com.comunicamosmas.api.repository.IContratoDao;
import com.comunicamosmas.api.service.dto.ArrayListDTO;
import com.comunicamosmas.api.service.dto.CarteraDTO;
import com.comunicamosmas.api.service.dto.ContratoInfoFacturaDTO;
import com.comunicamosmas.api.service.dto.ContratosFirmasDTO;
import com.comunicamosmas.api.service.dto.DatosClienteDTO;
import com.comunicamosmas.api.service.dto.ListContratoDTO;
import com.comunicamosmas.api.service.dto.PortalWebSaveContrato;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ContratoServiceImpl implements IContratoService {

	private static final Logger logger = Logger.getLogger(ContratoServiceImpl.class.getName());

	EntityManager em;

	@Autowired
	IContratoDao contratoDao;

	@Autowired
	ISystemConfigService systemConfigService;

	@Autowired
	IClienteDao clienteDao;

	@Autowired
	IDireccionService direccionService;

	@Autowired
	ITarifaService tarifaService;

	@Autowired
	IClausulaPermanenciaDao clausulaPermanenciaDao;

	@Autowired
	IPortalWebService portalWebService;

	@Override
	public List<Contrato> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contrato save(Contrato contrato) {
		Contrato save = contratoDao.save(contrato);
		return save;
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		contratoDao.deleteById(id);
	}

	@Override
	public Contrato findById(Long id) {
		// TODO Auto-generated method stub
		return contratoDao.findById(id).orElse(null);
	}

	@Override
	public List<ListContratoDTO> findByIdCliente(Long idCliente) {
		List<Object[]> listContrato = contratoDao.findByIdCliente(idCliente);
		List<ListContratoDTO> listContratoDTO = new ArrayList<>();
		for (Object[] row : listContrato) {
			ListContratoDTO contratoDTO = new ListContratoDTO();
			contratoDTO.setNombreMunicipio((String) row[0]);
			contratoDTO.setNombreServicio((String) row[1]);
			contratoDTO.setIdContrato((Integer) row[2]);
			contratoDTO.setBarrio((String) row[3]);
			contratoDTO.setDireccion((String) row[4]);
			contratoDTO.setEstado((String) row[5]);

			listContratoDTO.add(contratoDTO);
		}
		return listContratoDTO;
	}

	@Override
	public DatosClienteDTO datosContactoByIdContrato(Long idContrato) {
		// TODO Auto-generated method stub
		List<Object[]> listDatos = contratoDao.datosClienteByIdContrato(idContrato);

		if (!listDatos.isEmpty()) {

			DatosClienteDTO datosCliente = new DatosClienteDTO();
			for (Object[] datos : listDatos) {
				// System.out.print("acan andamos imprimiendo" + datos[0] + ": " +datos[2] +
				// "///////////////");
				datosCliente.setIdContrato((Integer) datos[0]);
				datosCliente.setEstrato((Integer) datos[1]);
				datosCliente.setNombreCliente((String) datos[2]);
				datosCliente.setDocumento((BigInteger) datos[3]);
				datosCliente.setCelularA((String) datos[4]);
				datosCliente.setCelularB((String) datos[5]);
				datosCliente.setMail((String) datos[6]);
				datosCliente.setNombreTarifa((String) datos[7]);
				datosCliente.setVelocidad((Integer) datos[8]);
				datosCliente.setValor((String) datos[9]);
				datosCliente.setLongDireccion((String) datos[10]);
				datosCliente.setLatDireccion((String) datos[11]);
				datosCliente.setLatEstacion((String) datos[12]);
				datosCliente.setLongEstacion((String) datos[13]);
				datosCliente.setDireccionServicio((String) datos[14]);
				datosCliente.setFisico((String) datos[15]);
				datosCliente.setTipoCliente((String) datos[16]);
			}

			return datosCliente;
		} else {
			return null;
		}
	}

	@Override
	public ContratoInfoFacturaDTO contratoFindFactura(Long idContrato) {

		List<Object[]> result = contratoDao.infoByFactura(idContrato);

		ContratoInfoFacturaDTO info = new ContratoInfoFacturaDTO();

		for (Object[] rs : result) {
			info.setIdCliente((Integer) rs[0]);
			info.setDocumento((String) rs[1]);
			info.setCelular((String) rs[2]);
			info.setNombreCliente((String) rs[3]);
			info.setDireccion((String) rs[4]);
		}

		return info;

	}

	@Override
	public List<CarteraDTO> carteraByServicio(ArrayListDTO datos) {
		try {

			SystemConfig system = systemConfigService.findByOrigen("origen");
			String[] caracteres = system.getComando().split(",");
			List<String> listOrigen = new ArrayList<>(Arrays.asList(caracteres));
			Optional<List<Object[]>> result = contratoDao.carteraByidServicio(datos.getServicios(), datos.getEstados(),
					listOrigen);

			List<CarteraDTO> cartera = result.map(resp -> resp.stream().map(rs -> {
				CarteraDTO obj = new CarteraDTO();
				obj.setFf_iddelcontrato(rs[0] != null ? rs[0].toString() : "-");
				obj.setFf_fisico(rs[1] != null ? rs[1].toString() : "-");
				obj.setFf_idzonacontra(rs[2] != null ? rs[2].toString() : "-");
				obj.setFf_marcacontrato(rs[3] != null ? rs[3].toString() : "-");
				obj.setFf_iniciocontrato(rs[4] != null ? rs[4].toString() : "-");
				obj.setFf_grupocontra(rs[5] != null ? rs[5].toString() : "-");
				obj.setFf_estadocontrato(rs[6] != null ? rs[6].toString() : "-");
				obj.setUltimo_pago(rs[7] != null ? rs[7].toString() : "-");
				obj.setFecha_ultimo_estado(rs[8] != null ? rs[8].toString() : "-");
				obj.setEstrato_contrato(rs[9] != null ? rs[9].toString() : "-");
				obj.setModalidad(rs[10] != null ? rs[10].toString() : "-");
				obj.setTarifa_general_valor(rs[11] != null ? rs[11].toString() : "-");
				obj.setNumero_canales(rs[12] != null ? rs[12].toString() : "-");
				obj.setVelocidad(rs[13] != null ? rs[13].toString() : "-");
				obj.setTarifa_nombre(rs[14] != null ? rs[14].toString() : "-");
				obj.setTarifa_promo_valor(rs[15] != null ? rs[15].toString() : "-");
				obj.setT_tipo_banda(rs[16] != null ? rs[16].toString() : "-");
				obj.setContacto(rs[17] != null ? rs[17].toString() : "-");
				obj.setCliente_documento(rs[18] != null ? rs[18].toString() : "-");
				obj.setDv(rs[19] != null ? rs[19].toString() : "-");
				obj.setCliente_tipo_cliente(rs[20] != null ? rs[20].toString() : "-");
				obj.setNombre_cliente(rs[21] != null ? rs[21].toString() : "-");
				obj.setMail(rs[22] != null ? rs[22].toString() : "-");
				obj.setZona_nombre(rs[23] != null ? rs[23].toString() : "-");
				obj.setServicios_nombre(rs[24] != null ? rs[24].toString() : "-");
				obj.setMesesdebe(rs[25] != null ? rs[25].toString() : "-");
				obj.setTotal_debe(rs[26] != null ? (double) rs[26] : 0.0);
				obj.setTotal_abonos(rs[27] != null ? (double) rs[27] : 0.0);
				obj.setDireccion(rs[28] != null ? rs[28].toString() : "-");
				obj.setDirr_barrio(rs[29] != null ? rs[29].toString() : "-");
				obj.setDirr_tipo(rs[30] != null ? rs[30].toString() : "-");
				obj.setMunicipio(rs[31] != null ? rs[31].toString() : "-");
				obj.setDepartamento(rs[32] != null ? rs[32].toString() : "-");
				obj.setT_tipo_tecnologia(rs[33] != null ? rs[33].toString() : "-");
				obj.setVendedor(rs[34] != null ? rs[34].toString() : "-");
				obj.setEstacion(rs[35] != null ? rs[35].toString() : "-");
				return obj;
			}).collect(Collectors.toList())).orElse(new ArrayList<>());

			return cartera;

		} catch (NullPointerException e) {
			// Manejar el caso de una referencia nula (NullPointerException)
			throw new ExceptionNullSql(new Date(), "Cartera Pointer", e.getLocalizedMessage());
		} catch (NumberFormatException e) {
			// Manejar el caso de un error al convertir un número (NumberFormatException)
			throw new ExceptionNullSql(new Date(), "Cartera Format", e.getMessage());
		} catch (DataAccessException e) {
			// Manejar el caso de un error en el acceso a datos (por ejemplo, problemas con
			// la base de datos)
			throw new ExceptionNullSql(new Date(), "Cartera Data", e.getMessage());
		} catch (Exception e) {
			throw new ExceptionNullSql(new Date(), "Cartera Exception", e.getMessage());
		}
	}

	@Override
	public void updateSaldoFavor(Contrato contrato, Float valor) {
		 contrato.setSaldoFavor((float) valor);
		 contratoDao.save(contrato);
	}

	@Override
	public ContratosFirmasDTO.Datos generateLink(Long idContrato , String token) {
		ContratosFirmasDTO.Datos infoAdd = tarifaService.findInfoServicioForFirmasContrato(idContrato);
		ContratosFirmasDTO contratoFirmas = new ContratosFirmasDTO();

		ContratosFirmasDTO.Datos datos = contratoFirmas.new Datos();
		datos.setNombre_asesor(infoAdd.getNombre_asesor());
		datos.setRegistro(infoAdd.getRegistro());
		datos.setId_contrato(infoAdd.getId_contrato());
		datos.setId_cliente(infoAdd.getId_cliente());
		datos.setVigencia(infoAdd.getVigencia());
		datos.setComentario(infoAdd.getComentario());
		  
		
		Cliente cliente = clienteDao.getClientByIdContrato(idContrato);		
		String reconexion = cliente.getTipoCliente().equals("N") ? "15000" : "50000";
		datos.setReconexion(reconexion);
		ContratosFirmasDTO.DatosSuscriptor suscriptor = contratoFirmas.new DatosSuscriptor();
		suscriptor.setCelular(cliente.getCelularA()  +"/" + cliente.getCelularB());
		suscriptor.setCorreo(cliente.getMail());
		suscriptor.setEstrato(infoAdd.getDatos_suscriptor().getEstrato());
		suscriptor.setFisico(infoAdd.getDatos_suscriptor().getFisico());
		suscriptor.setIdentificacion(cliente.getDocumento().toString());
		String nameCliente = cliente.getTipoCliente().equals("N") ? cliente.getApellidoPaterno() + " "+cliente.getApellidoMaterno() +" " + cliente.getNombrePrimer() +" " +cliente.getNombreSegundo() : cliente.getRazonSocial();
		suscriptor.setNombre(nameCliente);
		datos.setDatos_suscriptor(suscriptor);
		//contacto		 
		datos.setDatos_contacto(direccionService.findInfoByFimra(idContrato));
		//datos servicios
		ContratosFirmasDTO.DatosServicio servicio = contratoFirmas.new DatosServicio();
		servicio.setConcepto("Internet Fijo");
		servicio.setInstalacion(infoAdd.getDatos_servicio().getInstalacion());
		servicio.setMensualidad(infoAdd.getDatos_servicio().getMensualidad());
		servicio.setVelocidad(infoAdd.getDatos_servicio().getVelocidad());
		datos.setDatos_servicio(servicio);
		//Servicios adicionales
		ContratosFirmasDTO.ServiciosAdicionales adicionales = contratoFirmas.new ServiciosAdicionales();
		adicionales.setConcepto(null);
		adicionales.setMensualidad(null);
		datos.setServicios_adicionales(adicionales);
		//Clausula de permanencia
		ContratosFirmasDTO.ClausulaPermanencia clausura = contratoFirmas.new ClausulaPermanencia();
		ClausulaPermanencia clau = clausulaPermanenciaDao.findByIdContrato(idContrato);
		LocalDate initial = LocalDate.parse(infoAdd.getRegistro());
		LocalDate finale = initial.plusMonths(Integer.parseInt(infoAdd.getVigencia()));
		clausura.setFin(finale.toString());
		clausura.setInicio(infoAdd.getRegistro());
		clausura.setMes_1(clau.getMes1());
		clausura.setMes_2(clau.getMes2().toString());
		clausura.setMes_3(clau.getMes3().toString());
		clausura.setMes_4(clau.getMes4().toString());
		clausura.setMes_5(clau.getMes5().toString());
		clausura.setMes_6(clau.getMes6().toString());
		clausura.setMes_7(clau.getMes7().toString());
		clausura.setMes_8(clau.getMes8().toString());
		clausura.setMes_9(clau.getMes9().toString());
		clausura.setMes_10(clau.getMes10().toString());
		clausura.setMes_11(clau.getMes11().toString());
		clausura.setMes_12(clau.getMes12().toString());
		datos.setClausula_permanencia(clausura);
		//Optional<Object[]> contrato = contratoDao.findByIdFromFirma(idContrato);
		PortalWebSaveContrato save = new PortalWebSaveContrato();
		save.setId_contrato(infoAdd.getId_contrato());
		save.setId_cliente(infoAdd.getId_cliente());
		save.setIdServicio(infoAdd.getIdServicio());
		save.setDatosContrato(datos);
		
		portalWebService.sincroniceContratos(save, token);

		return datos;
	}
}
