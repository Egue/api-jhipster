package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.repository.IContratoDao;
import com.comunicamosmas.api.web.rest.vm.IListContratoVM;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContratoServiceImpl implements IContratoService {

    @Autowired
    IContratoDao contratoDao;

    @Override
    public List<Contrato> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Contrato save(Contrato contrato) {
        // TODO Auto-generated method stub
        return null;
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
    public List<IListContratoVM> findByIdCliente(Long idCliente) {
        // TODO Auto-generated method stub
        System.out.print(idCliente + "buscando....");
        return (List<IListContratoVM>) contratoDao.findByIdCliente(idCliente);
    }
}
