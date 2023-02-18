package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.InventarioArticulo;
import com.comunicamosmas.api.repository.IInventarioArticuloDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventarioArticuloServiceImpl implements IInventarioArticuloService {

    @Autowired
    IInventarioArticuloDao inventarioArticuloDao;

    @Override
    public List<InventarioArticulo> findAll(InventarioArticulo inventarioArticulo) {
        // TODO Auto-generated method stub
        return (List<InventarioArticulo>) inventarioArticuloDao.findAll();
    }

    @Override
    public void save(InventarioArticulo inventarioArticulo) {
        // TODO Auto-generated method stub
        inventarioArticuloDao.save(inventarioArticulo);
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public InventarioArticulo findById(Long id) {
        // TODO Auto-generated method stub
        return inventarioArticuloDao.findById(id).orElse(null);
    }

    @Override
    public List<InventarioArticulo> findInventarioArticuloByIdCategoria(Long id) {
        // TODO Auto-generated method stub
        return (List<InventarioArticulo>) inventarioArticuloDao.findArticuloByIdCategoria(id);
    }
}
