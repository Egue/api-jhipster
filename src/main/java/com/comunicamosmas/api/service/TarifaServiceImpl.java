package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Admin;
import com.comunicamosmas.api.domain.Tarifa;
import com.comunicamosmas.api.repository.IAdminDao;
import com.comunicamosmas.api.repository.ITarifaDao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarifaServiceImpl implements ITarifaService {

    @Autowired
    ITarifaDao tarifaDao;

	@Override
	public List<Tarifa> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tarifa save(Tarifa tarifa) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long deleteById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tarifa findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
 
}
