package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.InventarioCategoria;
import com.comunicamosmas.api.repository.IInventarioCategoriaDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventarioCategoriaServiceImpl implements IInventarioCategoriaService {

    @Autowired
    IInventarioCategoriaDao inventarioCategoriaDao;

    @Override
    public List<InventarioCategoria> findAll() {
        // TODO Auto-generated method stub
        return (List<InventarioCategoria>) inventarioCategoriaDao.findAll();
    }

    @Override
    public void save(InventarioCategoria inventarioCategoria) {
        // TODO Auto-generated method stub
        inventarioCategoriaDao.save(inventarioCategoria);
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public InventarioCategoria findById(Long id) {
        return inventarioCategoriaDao.findById(id).orElse(null);
    }
}
