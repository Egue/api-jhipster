package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Deuda;
import com.comunicamosmas.api.domain.EmailCampaign;
import com.comunicamosmas.api.domain.FinancieroNc;
import com.comunicamosmas.api.repository.IDeudaDao;
import com.comunicamosmas.api.repository.IFinancieroNcDao;
import com.comunicamosmas.api.repository.IPagoDao;
import com.comunicamosmas.api.service.dto.DeudasForFacturaDTO; 
import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;
import com.comunicamosmas.api.service.dto.EstadoCuentaDeudasDTO;
import com.comunicamosmas.api.service.dto.PagosEstadoCuentaDTO;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;

import java.sql.Timestamp;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DeudaServiceImpl implements IDeudaService {

	@Autowired
	IDeudaDao deudaDao;

	@Autowired
	IPagoDao pagoDao;


	@Autowired
	IEmailCampaignService emailCampaignService;

	@Autowired
	IFinancieroNcDao financieroNcDao;

	/*private final IDeudaDao deudaDao; 
	private final IPagoService pagoService;
	private final IEmailCampaignService emailCampaignService;

	public DeudaServiceImpl(IDeudaDao deudaDao , IPagoService pagoService , IEmailCampaignService emailCampaignService)
	{
		this.deudaDao = deudaDao;
		this.pagoService = pagoService;
		this.emailCampaignService = emailCampaignService;
	}*/

	@Override
	public List<Deuda> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Deuda save(Deuda deuda) {
		 
		return deudaDao.save(deuda);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		  deudaDao.deleteById(id);;
	}

	@Override
	public Deuda findById(Long id) {
		// TODO Auto-generated method stub
		return deudaDao.findById(id).orElse(null);
	}

	@Override
	public Page<EstadoCuentaDeudasDTO> findByIdContrato(Long contrato , Pageable page) {
		Page<Object[]> deudas = deudaDao.findByIdContrato(contrato , page);
		return deudas.map(this::convertirAEstadoCuentaDeudasDTO);
		 
	}

	private EstadoCuentaDeudasDTO convertirAEstadoCuentaDeudasDTO(Object[] resultado)
	{
		EstadoCuentaDeudasDTO obj = new EstadoCuentaDeudasDTO();

		obj.setIdDeuda((Integer) resultado[0]);
		obj.setFactura((Integer) resultado[1]);
		String periodo  = this.convertPeriodoToString((Integer) resultado[2]);
		obj.setPeriodo(periodo);
		obj.setGenerador((String) resultado[3]);
		obj.setValor((Double) resultado[4]);
		obj.setAbono((Float) resultado[5]);
		String concepto = "";
		if((Integer) resultado[6] == 1){ 
			concepto = "Instalación";
		}else if((Integer) resultado[7] == 1){ 
			concepto = "Reconexión"; 
		}else if((Integer) resultado[8] == 1){ 
			concepto = "Materiales"; 
		}else if((Integer) resultado[9] == 1){ 
			concepto = "Traslado";
		}else if((Integer) resultado[10] == 1){
			concepto = (String) resultado[11] ;
		}else{
			concepto = "Mensualidad";
		}
		obj.setConcepto(concepto);
		List<Object[]> pagosBydeuda = pagoDao.findByIdDeuda((Integer) resultado[0]);
		List<PagosEstadoCuentaDTO> pagos = pagosBydeuda.stream()
									.map(this::convertPagosEstadoCuentaDTO)
										.collect(Collectors.toList());
		 
		obj.setPagos(pagos);
		//nc
		Integer id_deuda = (Integer) resultado[0];
		List<FinancieroNc> nc = financieroNcDao.findByIdDeuda(Long.valueOf(id_deuda));
		obj.setNotasCredito(nc);
		//saldo favor
		return obj;
	} 

	private String convertPeriodoToString(Integer periodo)
	{
		DateTimeFormatter inpuTimeFormatter = DateTimeFormatter.ofPattern("yyyyMM");
		YearMonth yearMonth = YearMonth.parse(periodo.toString() , inpuTimeFormatter);

		DateTimeFormatter ouTimeFormatter  =DateTimeFormatter.ofPattern("MMMM / yyyy" , new Locale("es" , "ES"));

		return yearMonth.format(ouTimeFormatter);
	}

	private PagosEstadoCuentaDTO convertPagosEstadoCuentaDTO(Object[] pago)
    {
        PagosEstadoCuentaDTO pagosCuentaDTO = new PagosEstadoCuentaDTO();

        pagosCuentaDTO.setIdPago((Integer) pago[0]);
        pagosCuentaDTO.setReciboCaja((Integer) pago[1]);
        pagosCuentaDTO.setValorCobrado((Float) pago[2]);
        pagosCuentaDTO.setMarca((Timestamp) pago[3]);

        return pagosCuentaDTO;
    }

	@Override
	public String findDeudaByIdContrato(Long idContrato) {
		// TODO Auto-generated method stub
		return deudaDao.findDeudaByIdContrato(idContrato);
	}

	@Override
	public List<DeudasForFacturaDTO> findDeudaByFacturaAndMesServiceAndIdEmpresa(Long factura, Long mesServicio,
			Long idEmpresa, Integer idCliente, String origen) {
		// TODO Auto-generated method stub
		Optional<List<Object[]>> result = deudaDao.findDeudaByFacturaAndMesServiceAndIdEmpresa(factura, mesServicio, idEmpresa,idCliente,origen);

		List<DeudasForFacturaDTO> listDeudas = result.map(resp -> 			 
			resp.stream().map( rs -> {
				DeudasForFacturaDTO obj = new DeudasForFacturaDTO();
				obj.setId_deuda((Integer) rs[0]);
				obj.setId_cliente((Integer) rs[1]);
				obj.setRefiere((String) rs[2]);
				obj.setEstado((Integer) rs[3]);
				obj.setFactura((Integer) rs[4]);
				obj.setServicio((String) rs[5]);
				obj.setId_tarifa((Integer) rs[6]);
				obj.setValor_base((Double) rs[7]);
				obj.setValor_iva((Float) rs[8]);
				obj.setValor_reteiva((Float) rs[9]);
				obj.setValor_refuente((Float) rs[10]);
				obj.setValor_reteica((Float) rs[11]);
				obj.setValor_bomberil((Float) rs[12]);
				obj.setValor_otrosimp((Float) rs[13]);
				obj.setValor_parcial((Float) rs[14]);
				obj.setValor_total((Double) rs[15]);
				obj.setValor_intereses((Integer) rs[16]);
				obj.setId_contrato((Integer) rs[17]);
				obj.setId_servicio((Integer) rs[18]);
				obj.setId_empresa((Integer) rs[19]);
				obj.setMarca((Timestamp) rs[20]);
				obj.setId_ciudad((Integer) rs[21]);
				obj.setMes_servicio((Integer) rs[22]);
				obj.setFechaf_genera((Integer) rs[23]);
				obj.setFechaf_corte((Integer) rs[24]);
				obj.setFecha_limite((Integer) rs[25]);
				obj.setRepetitivo((Integer) rs[26]);
				obj.setPucc((String) rs[27]);
				obj.setNiff((String) rs[28]);
				obj.setInstalacion((Integer) rs[29]);
				obj.setReconexion((Integer) rs[30]);
				obj.setMateriales((Integer) rs[31]);
				obj.setTraslado((Integer) rs[32]);
				obj.setOtros((Integer) rs[33]);
				obj.setConcepto_aux((String) rs[34]);
				obj.setGenerador((Integer) rs[35]);
				obj.setId_usuario((Integer) rs[36]);
				obj.setFacturado_fecha((Integer) rs[37]);
				obj.setFacturado_hora((String) rs[38]);
				obj.setFacturado_id_resolucion((Integer) rs[39]);
				obj.setPasarela((String) rs[40]);
				obj.setFac_electronica((String) rs[41]);
				obj.setResultado_factura_electronica((String) rs[42]);
				obj.setNombre((String) rs[43]);
				obj.setRango_inicio((String) rs[44]);
				obj.setRango_final((String) rs[45]);
				obj.setPrefijo((String) rs[46]);
				obj.setNum_resolucion((String) rs[47]);
				obj.setVigencia((Integer) rs[48]);
				obj.setFecha_resolucion((Integer) rs[49]);
				return obj;
			}).collect(Collectors.toList())

		).orElse(new ArrayList<>());

		if(listDeudas.isEmpty())
		{
			throw new ExceptionNullSql(new Date(), "Consulta Vacia", "no se encontro deudas para la factura");
		}

		return listDeudas;
	}

	@Override
	public Optional<List<Object[]>> findSalgoAnterior(Integer fecha, List<Integer> consulta) {

		Optional<List<Object[]>> result = deudaDao.saldoAnterior(fecha, consulta);

		return result;
	}

	@Override
	public List<EmailCampaignDetalleDTO> findDeudaByIdCampaingAndOrigen(String factura, Integer idCampaign) {
		// TODO Auto-generated method stub
		String origen = "";
		Integer facturaInt = 0;
		if (factura.contains("*")) {
			origen = "B";
			try {
				String cadeString = factura.replace("*", "");
				facturaInt = Integer.parseInt(cadeString);
			} catch (Exception e) {
				throw new ExceptionNullSql(new Date(), factura, " No es válido");
			}
		} else {
			origen = "A";
			facturaInt = Integer.parseInt(factura);
		}
		// consultar
		EmailCampaign campaign = emailCampaignService.findById(idCampaign);
		Integer mesServicio = Integer.parseInt(campaign.getAnno() + campaign.getMes());
		System.out.println(origen + " : " + facturaInt + ": " + campaign.getIdEmpresa() + ": " + mesServicio);
		Optional<List<Object[]>> result = deudaDao.findDeudaByFacturaAndMesEmpresaOrigen(facturaInt, mesServicio,
				campaign.getIdEmpresa(), origen);

		List<EmailCampaignDetalleDTO> facturas = result.map(resp -> {
			List<EmailCampaignDetalleDTO> list = new ArrayList<>();
			for (Object[] rs : resp) {
				EmailCampaignDetalleDTO obj = new EmailCampaignDetalleDTO();
				obj.setFactura((String) rs[0].toString());
				obj.setIdCampaign(campaign.getId());
				obj.setOrigen((String) rs[1]);
				obj.setIdCliente((Integer) rs[3]);
				obj.setEmail((String) rs[4]);
				obj.setNombreCliente((String) rs[5]);
				obj.setNombreServicio((String) rs[6].toString());
				list.add(obj);
			}

			return list;
		}).orElse(new ArrayList<>());

		return facturas;
	}

	@Override
	public List<DeudasForFacturaDTO> deudasByIdContrato(Long idContrato) {
		// TODO Auto-generated method stub
		 Optional<List<Object[]>> result = deudaDao.deudasByIdContrato(idContrato);

		 List<DeudasForFacturaDTO> deudas = result.map(resp -> {
			List<DeudasForFacturaDTO> list = new ArrayList<>();
			for(Object[] rs : resp)
			{
				DeudasForFacturaDTO obj = new DeudasForFacturaDTO();
				obj.setId_deuda((Integer) rs[0]);
				obj.setId_servicio((Integer) rs[1]);
				obj.setId_empresa((Integer) rs[2]);
				obj.setId_contrato((Integer) rs[3]);
				obj.setId_cliente((Integer) rs[4]);
				obj.setValor_parcial((Float) rs[5]);
				obj.setValor_total((Double) rs[6]);
				obj.setMes_servicio((Integer) rs[7]);
				list.add(obj);
			}
			return list;
		 }).orElse(new ArrayList<>());

		 return deudas;
	}

}
