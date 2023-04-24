package com.comunicamosmas.api.service;

 
import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.domain.Orden; 
import com.comunicamosmas.api.repository.IOrdenDao;
import com.comunicamosmas.api.service.dto.OrdenByContratoDTO;
import com.comunicamosmas.api.service.dto.OrdenByTipoOrdenDTO;
import com.comunicamosmas.api.service.dto.OrdenForInstalacionFindByIdOrdenDTO;
import com.comunicamosmas.api.service.dto.OrdenInstalacionDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

@Service
public class OrdenServiceImpl implements IOrdenService {

    @Autowired
    IOrdenDao ordenDao;

    @Override
    public List<Orden> findAll() {
        
        return (List<Orden>)ordenDao.findAll();
    }

    @Override
    public Orden save(Orden orden) {
        // TODO Auto-generated method stub
        return ordenDao.save(orden);
    }

    @Override
    public Long deleteById(Long id) {
         
        return null;
    }

    @Override
    public Orden findById(Long id) {
        // TODO Auto-generated method stub
        return ordenDao.findById(id).orElse(null);
    }

    @Override
    public List<OrdenInstalacionDTO> ordenInstalacion() {
        return (List<OrdenInstalacionDTO>) ordenDao.ordenInstalacion();
    }

    @Override
    public OrdenForInstalacionFindByIdOrdenDTO ordenForInstalacionFindByIdOrden(Long id) {
        // TODO Auto-generated method stub
        return (OrdenForInstalacionFindByIdOrdenDTO) ordenDao.ordenForInstalacionFindByIdOrden(id);
    }

    @Override
    public String findTelefonoByIdOrden(Long idOrden) {
        // TODO Auto-generated method stub
        return ordenDao.findTelefonoByIdOrden(idOrden);
    }

    @Override
    public List<OrdenInstalacionDTO> getListFindBetwee(String valor1, String valor2) {
        // TODO Auto-generated method stub
        return (List<OrdenInstalacionDTO>) ordenDao.getListFindBetwee(valor1, valor2);
    }

	@Override
	public List<Orden> findAllByTipo(Long idTipo) {
		// TODO Auto-generated method stub
		return (List<Orden>)ordenDao.findAllByIdTipo(idTipo);
	}

	@Override
	public List<OrdenByContratoDTO> listOrdenByIdContrato(Long idContrato) {
		
		List<Object[]> result = ordenDao.listOrdenByIdContrato(idContrato);
		List<OrdenByContratoDTO> orden = new ArrayList<>();
		for(Object[] rs : result)
		{
			OrdenByContratoDTO obj = new OrdenByContratoDTO();
			obj.setIdContrato((Integer) rs[0]);
			obj.setIdOrden((Integer)rs[1]);
			obj.setNumeroA((Integer)rs[2]);
			obj.setNumeroB((Integer) rs[3]);
			obj.setTipoOrden((String) rs[4]);
			obj.setOrigen((String) rs[5]);
			obj.setRegistro((String) rs[6]);
			obj.setAsignacion((String) rs[7]);
			obj.setEjecucion((String) rs[8]);
			obj.setUserAsigna((String) rs[9]);
			obj.setUserEjecuta((String) rs[10]);
			
			orden.add(obj);
			
		}
		return orden;
	}

	@Override
	public List<OrdenByTipoOrdenDTO> listOrdenReconexion() {
		List<Object[]> result = ordenDao.listOrdenByTipoOrden(3L);
		List<OrdenByTipoOrdenDTO> orden = new ArrayList<>();
		for(Object[] rs : result)
		{
			OrdenByTipoOrdenDTO obj = new OrdenByTipoOrdenDTO();
			 
			 obj.setIdContrato((Integer) rs[0]);
			 obj.setIdOrden((Integer) rs[1]);
			 obj.setRegistro((String) rs[2]);
			 obj.setNombreCliente((String) rs[3]);
			 obj.setNombreEstacion((String) rs[4]);
			orden.add(obj);
		}
		return orden; 
		
	}

	@Override
	public List<OrdenByTipoOrdenDTO> listOrdenesCortes() {
		List<Object[]> result = ordenDao.listOrdenByTipoOrden(2L);
		List<OrdenByTipoOrdenDTO> orden = new ArrayList<>();
		for(Object[] rs : result)
		{
			OrdenByTipoOrdenDTO obj = new OrdenByTipoOrdenDTO();
			 
			 obj.setIdContrato((Integer) rs[0]);
			 obj.setIdOrden((Integer) rs[1]);
			 obj.setRegistro((String) rs[2]);
			 obj.setNombreCliente((String) rs[3]);
			 obj.setNombreEstacion((String) rs[4]);
			orden.add(obj);
		}
		return orden; 
	}

	@Override
	public List<OrdenByTipoOrdenDTO> listOrdenesByIdServicioAndTipoCliente(Long tipoOrden, Long idServicio, String tipoCliente) {
		List<Object[]> result = ordenDao.ordenesByIdServicioAndTipoClienteAndTipoOrden(tipoOrden, idServicio, tipoCliente);
		List<OrdenByTipoOrdenDTO> orden = new ArrayList<>();
		for(Object[] rs : result)
		{
			OrdenByTipoOrdenDTO obj = new OrdenByTipoOrdenDTO();
			obj.setIdContrato((Integer) rs[0]);
			obj.setIdOrden((Integer) rs[1]);
			obj.setRegistro((String) rs[2]);
			obj.setNombreCliente((String) rs[3]);
			obj.setNombreEstacion((String) rs[4]);
			obj.setNota((String) rs[5]);
			orden.add(obj);
		}
		return orden;
	}

	@Override
	public Orden findLastRegisterByRefiere(String refiere , Long idServicio) {
		// TODO Auto-generated method stub
		return ordenDao.findLastRegisterByRefiere(refiere, idServicio);
	}

	//crear reconexion
	@Override
	public void reconexion(Contrato contrato , String comentario) {

		System.out.print("Crear reconexion");
		/*
		 * "id_orden": 587525,"tipo_orden": 3,"refiere": "A","causa_solicitud": "Orden de Servicio",
		 * "numero_a": 111022,"numero_b": 0,"id_contrato": 81306,"id_direccion": 100294,
		 * "id_cliente": 61161,
		 * "id_estacion": 0,"id_zona": 1,"fechaf_registra": 20230331,
		 * "fechaf_solicita": 20230331,
		 * "fechaf_asigna": 0,
		 * "fechaf_asiste": 0,
		 * "fechaf_anula": 0,
		 * "fechaf_descarga": 0,"hora_asiste_inicio": "","hota_asiste_fin": "","estado": 0,
		 * "nota": "prueba para crear anutomticamente","id_ciudad": 2,"id_empresa": 1,"id_servicio": 1,
		 * "id_usuario_registra": 202,"id_usuario_asigna": 0,"id_usuario_ejecuta": 0,"id_usuario_descarga": 0,
		 * "id_usuario_anula": 0,"anula_justifica": "","a": "","b": "","c": "","d": "","e": "","f": "","g": "","h": "","i": "","j": "",
		 * "ultima_dow": "2023-03-31 16:02:34","abierta": 1,"anulada": 0,"nota_final": "","winmax": 0,"winmax_id_usuario": 0,"winmax_marca": "","id_ticket_soporte": 0,"pdf_descarga_fecha": "","pdf_descarga_usuario": 0,"id_tecnologia": 4,"tipo_reconecta": 0,"api_automatica": 0,"log_api": ""}**/
		Orden orden = new Orden();
		orden.setTipoOrden(3L);
		orden.setRefiere(contrato.getGrupo());
		orden.setCausaSolicitud("Orden de Servicio - Reconexion de servicio");
		//numero obtener ultimo y sumar 1
		if(contrato.getGrupo().equals("A"))
		{	Orden nuevoOrden =  ordenDao.findLastRegisterByRefiere("A" , contrato.getIdServicio());
			Long nuevo = nuevoOrden.getNumeroA() + 1L;
			orden.setNumeroA(nuevo);
			orden.setNumeroB(0L);
		}else {
			Orden nuevoOrden =  ordenDao.findLastRegisterByRefiere("B" , contrato.getIdServicio());
			Long nuevo = nuevoOrden.getNumeroA() + 1L;
			orden.setNumeroB(nuevo);
			orden.setNumeroA(0L);
		}
		orden.setIdContrato(contrato.getId());
		orden.setIdDireccion(contrato.getIdDireccionServicio());
		orden.setIdCliente(contrato.getIdCliente());
		orden.setIdEstacion(0L);
		orden.setIdZona(contrato.getIdZona());
		orden.setFechafRegistra(Long.parseLong(this.formatearFecha("yyyyMMdd")));
		orden.setFechafSolicita(Long.parseLong(this.formatearFecha("yyyyMMdd")));
		orden.setFechafAsigna(0L);
		orden.setFechafAsiste(0L);
		orden.setFechafAnula(0L);
		orden.setFechafDescarga(0L);
		orden.setHoraAsisteInicio("");
		orden.setHotaAsisteFin("");
		orden.setEstado(0L);
		orden.setNota(comentario);
		orden.setIdCiudad(contrato.getIdCiudad());
		orden.setIdEmpresa(contrato.getIdEmpresa());
		orden.setIdServicio(contrato.getIdServicio());
		orden.setIdUsuarioRegistra(1L);
		orden.setIdUsuarioAsigna(0L);
		orden.setIdUsuarioEjecuta(0L);
		orden.setIdUsuarioDescarga(0L);
		orden.setIdUsuarioAnula(0L);
		orden.setAnulaJustifica("");
		orden.setA(" ");
		orden.setB(" ");
		orden.setC(" ");
		orden.setD(" ");
		orden.setE(" ");
		orden.setF(" ");
		orden.setG(" ");
		orden.setH(" ");
		orden.setI(" ");
		orden.setJ(" ");  
		orden.setUltimaDow(this.formatearFecha("yyyy-MM-dd HH:mm:ss"));
		orden.setAbierta(1L);
		orden.setAnulada(0L);
		orden.setNotaFinal(" ");
		orden.setWinmax(0L);
		orden.setWinmaxIdUsuario(0L);
		orden.setWinmaxMarca("");
		orden.setIdTicketSoporte(0L);
		orden.setPdfDescargaFecha("");
		orden.setPdfDescargaUsuario(0L);
		orden.setIdTecnologia(contrato.getIdTecnologia());
		orden.setTipoReconecta(0L);
		orden.setApiAutomatica(0L);
		orden.setLogApi("");
		ordenDao.save(orden);
		
	}
	
	private String formatearFecha(String formato)
	{
		 LocalDate fechaActual = LocalDate.now();
		    
		    // Formatear la fecha en el formato deseado (yyyyMMdd)
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		    String fechaFormateada = fechaActual.format(formatter);
		    
		    return fechaFormateada;
	}

	@Override
	public Orden findOrdenActivaByTipo(Long tipoOrden, Long idContrato) {
		
		return ordenDao.findOrdenActivaByTipo(tipoOrden , idContrato);
	}

	@Override
	public void anularOrden(Orden orden, String comentario) {
		
		orden.setFechafAsigna(Long.parseLong(this.formatearFecha("yyyyMMdd")));
		
		orden.setFechafAnula(Long.parseLong(this.formatearFecha("yyyyMMdd")));
		
		orden.setNota(comentario);
		
		orden.setIdUsuarioAsigna(1L);
		
		orden.setIdUsuarioAnula(1L);
		
		orden.setAnulada(1L);
		
		ordenDao.save(orden);	
		
	}
}
