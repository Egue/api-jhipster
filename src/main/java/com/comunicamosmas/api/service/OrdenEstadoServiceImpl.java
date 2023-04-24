package com.comunicamosmas.api.service;
 
import com.comunicamosmas.api.domain.OrdenEstado; 
import com.comunicamosmas.api.repository.IOrdenEstadoDao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdenEstadoServiceImpl implements IOrdenEstadoService {

	@Autowired
	IOrdenEstadoDao ordenEstadoDao;
	
	@Override
	public List<OrdenEstado> findAll() {
		// TODO Auto-generated method stub
		return (List<OrdenEstado>) ordenEstadoDao.findAll();
	}

	@Override
	public void save(OrdenEstado ordenEstado) {
		// TODO Auto-generated method stub
		 ordenEstadoDao.save(ordenEstado);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		  ordenEstadoDao.deleteById(id);;
	}

	@Override
	public OrdenEstado findById(Long id) {
		// TODO Auto-generated method stub
		return ordenEstadoDao.findById(id).orElse(null);
	}

	@Override
	public List<OrdenEstado> findAllByEstadoAndCliente(String rol) {
		// TODO Auto-generated method stub
		List<OrdenEstado> result = new ArrayList<OrdenEstado>();
		System.out.print(rol);
		if(rol.equals("ROLE_ADMIN"))
		{
			 result = ordenEstadoDao.findAllByEstadoAndClienteADMIN();
		}else {
			  result = ordenEstadoDao.findAllByEstadoAndCliente();
		}
		return result;
	}

     
}
