package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Empresa;
import com.comunicamosmas.api.repository.IEmpresaDao;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaServiceImpl implements IEmpresaService {

    private final IEmpresaDao empresaDao;

    public EmpresaServiceImpl(IEmpresaDao empresaDao)
    {
        this.empresaDao = empresaDao;
    }

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
        return empresaDao.findById(id).orElse(null);
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

    @Override
    public Optional<List<Empresa>> findAllByStatus() {
        // TODO Auto-generated method stub
        return empresaDao.findAllByStatus();
    }

    @Override
    public List<Empresa> findFilter(Long idUSer) {
        // TODO Auto-generated method stub
        return empresaDao.findFilter(idUSer);
        
    }
}
