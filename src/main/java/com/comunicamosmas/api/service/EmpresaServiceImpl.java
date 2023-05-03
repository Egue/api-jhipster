package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Empresa;
import com.comunicamosmas.api.repository.IEmpresaDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaServiceImpl implements IEmpresaService {

    @Autowired
    IEmpresaDao empresaDao;

    @Override
    public List<Empresa> findAll() {
        // TODO Auto-generated method stub
        return (List<Empresa>) empresaDao.findAll();
    }

    @Override
    public void save(Empresa empresa) {
        // TODO Auto-generated method stub
        empresaDao.save(empresa);
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public Empresa findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Empresa> findByLikeNombreComercial(String empresa) {
        // TODO Auto-generated method stub
        return (List<Empresa>) empresaDao.findByLikeNombreComercial(empresa);
    }

	@Override
	public Empresa findByIdContrato(Long idContrato) {
		// TODO Auto-generated method stub
		return empresaDao.findEmpresaByContrato(idContrato);
	}
}
