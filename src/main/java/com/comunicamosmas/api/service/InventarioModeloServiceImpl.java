package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.InventarioModelo;
import com.comunicamosmas.api.repository.IInventarioModeloDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventarioModeloServiceImpl implements IInventarioModeloService {

    @Autowired
    IInventarioModeloDao inventarioModeloDao;

    @Override
    public List<InventarioModelo> findAll() {
        // TODO Auto-generated method stub
        return (List<InventarioModelo>) inventarioModeloDao.findAll();
    }

    @Override
    public void save(InventarioModelo inventarioModelo) {
        // TODO Auto-generated method stub
        inventarioModeloDao.save(inventarioModelo);
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public InventarioModelo findById(Long id) {
        // TODO Auto-generated method stub
        return inventarioModeloDao.findById(id).orElse(null);
    }

    @Override
    public List<InventarioModelo> findByIdModelo(Long id) {
        // TODO Auto-generated method stub
        return (List<InventarioModelo>) inventarioModeloDao.findByIdModelo(id);
    }
}
