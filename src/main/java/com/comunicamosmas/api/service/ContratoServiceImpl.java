package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.repository.IContratoDao;
import com.comunicamosmas.api.service.dto.ContratoInfoFacturaDTO;
import com.comunicamosmas.api.service.dto.DatosClienteDTO;
import com.comunicamosmas.api.service.dto.ListContratoDTO;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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
				System.out.print("acan andamos imprimiendo" + datos[0] + ": " +datos[2] + "///////////////");
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
}
