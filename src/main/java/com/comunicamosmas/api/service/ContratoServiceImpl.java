package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.repository.IContratoDao;
import com.comunicamosmas.api.service.dto.CarteraDTO;
import com.comunicamosmas.api.service.dto.ContratoInfoFacturaDTO;
import com.comunicamosmas.api.service.dto.DatosClienteDTO;
import com.comunicamosmas.api.service.dto.ListContratoDTO;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

@Service
public class ContratoServiceImpl implements IContratoService {

	EntityManager em;
	
    @Autowired
    IContratoDao contratoDao;

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
        for(Object[] row : listContrato)
        {	
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
	List<Object[]>  listDatos = contratoDao.datosClienteByIdContrato(idContrato);
	
		 
		if(!listDatos.isEmpty())
		{
			
			DatosClienteDTO datosCliente = new DatosClienteDTO();
			for(Object[] datos : listDatos)
			{
				//System.out.print("acan andamos imprimiendo" + datos[0] + ": " +datos[2] + "///////////////");
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
			}
			
			return datosCliente;
		}else {
			return null;
		}
	}

	@Override
	public ContratoInfoFacturaDTO contratoFindFactura(Long idContrato) {
		
		List<Object[]> result = contratoDao.infoByFactura(idContrato);
		
		ContratoInfoFacturaDTO info = new ContratoInfoFacturaDTO();
		
		for(Object[] rs : result)
		{
			info.setIdCliente((Integer)rs[0]);
			info.setDocumento((String) rs[1]);
			info.setCelular((String) rs[2]);
			info.setNombreCliente((String) rs[3]);
			info.setDireccion((String) rs[4]);
		}
		
		return info;
		
	}

	 
 

	@Override
	public List<CarteraDTO> carteraByServicio(List<Integer> servicios) {
		// TODO Auto-generated method stub
		 try{
			Optional<List<Object[]>> result = contratoDao.carteraByidServicio(servicios);

			List<CarteraDTO> cartera = new ArrayList<>();

		result.ifPresent(res -> {

			for(Object[] rs : res)
		{
			CarteraDTO obj = new CarteraDTO();

			obj.setFf_iddelcontrato((String) rs[0].toString());
			obj.setFf_fisico((String) rs [1]);
			obj.setFf_idzonacontra((String) rs[2].toString());
			obj.setFf_marcacontrato((String) rs[3].toString());
			obj.setFf_iniciocontrato((String) rs[4].toString());
			obj.setFf_grupocontra((String) rs[5]);
			obj.setFf_estadocontrato((String) rs[6]);
			obj.setUltimo_pago((String) rs[7].toString());
			obj.setFecha_ultimo_estado((String) rs[8].toString());
			obj.setEstrato_contrato((String) rs[9].toString());
			obj.setModalidad((String) rs[10]);
			obj.setTarifa_general_valor((String) rs[11].toString());
			obj.setNumero_canales((String) rs[12].toString());
			obj.setVelocidad((String) rs[13].toString());
			obj.setTarifa_nombre((String) rs[14]);
			obj.setTarifa_promo_valor((String) rs[15].toString());
			obj.setT_tipo_banda((String) rs[16]);
			obj.setContacto((String) rs[17]);
			obj.setCliente_documento((String) rs[18].toString());
			obj.setDv((String) rs[19].toString());
			obj.setCliente_tipo_cliente((String) rs[20]);
			obj.setNombre_cliente((String) rs[21]);
			obj.setMail((String) rs[22]);
			obj.setZona_nombre((String) rs[23]);
			obj.setServicios_nombre((String) rs[24]);
			obj.setMesesdebe((String) rs[25].toString());
			obj.setTotal_debe((double) rs[26]);
			obj.setTotal_abonos((double) rs[27]);
			obj.setDireccion((String) rs[28]);
			obj.setDirr_barrio((String) rs[29]);
			obj.setDirr_tipo((String) rs[30]);
			obj.setMunicipio((String) rs[31]);
			obj.setDepartamento((String) rs[32]);
			obj.setT_tipo_tecnologia((String) rs[33]);
			obj.setVendedor((String) rs[34]);

			cartera.add(obj);
		}


		}); 

		return cartera;
		}catch(Exception e)
		{
				throw new ExceptionNullSql(new Date(), "Cartera", e.getMessage());
		}
	}
}
