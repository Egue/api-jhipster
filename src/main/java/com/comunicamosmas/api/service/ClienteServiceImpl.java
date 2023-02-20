package com.comunicamosmas.api.service;
 
import com.comunicamosmas.api.domain.Cliente; 
import com.comunicamosmas.api.repository.IClienteDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    IClienteDao clienteDao;

    @Override
    public List<Cliente> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Cliente save(Cliente cliente) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Cliente findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Cliente getClientByIdContrato(Long idContrato) {
        return clienteDao.getClientByIdContrato(idContrato);
    }

    @Override
    public List<Cliente> findByDocumento(String documento) {
        // TODO Auto-generated method stub
        return (List<Cliente>) clienteDao.findByDocumento(documento);
    }
}
