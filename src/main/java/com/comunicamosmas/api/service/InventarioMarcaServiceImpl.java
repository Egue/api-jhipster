package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.InventarioMarca;
import com.comunicamosmas.api.repository.IInventarioMarcaDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventarioMarcaServiceImpl implements IInventarioMarcaService {

    @Autowired
    IInventarioMarcaDao inventarioMarcaDao;

    @Override
    public List<InventarioMarca> findAll() {
        // TODO Auto-generated method stub
        return (List<InventarioMarca>) inventarioMarcaDao.findAll();
    }

    @Override
    public void save(InventarioMarca inventarioMarca) {
        // TODO Auto-generated method stub
        inventarioMarcaDao.save(inventarioMarca);
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public InventarioMarca findById(Long id) {
        // TODO Auto-generated method stub
        return inventarioMarcaDao.findById(id).orElse(null);
    }
}
