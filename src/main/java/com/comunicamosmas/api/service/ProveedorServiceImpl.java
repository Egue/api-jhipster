package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Proveedor;
import com.comunicamosmas.api.repository.IProveedorDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProveedorServiceImpl implements IProveedorService {

    @Autowired
    IProveedorDao proveedorDao;

    @Override
    public List<Proveedor> findAll() {
        // TODO Auto-generated method stub
        return (List<Proveedor>) proveedorDao.findAll();
    }

    @Override
    public void save(Proveedor proveedor) {
        // TODO Auto-generated method stub
        proveedorDao.save(proveedor);
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public Proveedor findById(Long id) {
        // TODO Auto-generated method stub
        return proveedorDao.findById(id).orElse(null);
    }

    @Override
    public List<Proveedor> findByEstadoActivo(int estado) {
        return (List<Proveedor>) proveedorDao.findByEstadoActivo(estado);
    }

    @Override
    public List<Proveedor> findByLikeRazonSocial(String RazonSocial) {
        return (List<Proveedor>) proveedorDao.findByLikeRazonSocial(RazonSocial);
    }
}
