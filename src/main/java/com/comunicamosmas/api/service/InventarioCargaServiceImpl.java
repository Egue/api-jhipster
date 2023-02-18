package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.InventarioCarga;
import com.comunicamosmas.api.repository.IInventarioCargaDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventarioCargaServiceImpl implements IInventarioCargaService {

    @Autowired
    IInventarioCargaDao inventarioCargaDao;

    @Override
    public List<InventarioCarga> findAll() {
        // TODO Auto-generated method stub
        return (List<InventarioCarga>) inventarioCargaDao.findAll();
    }

    @Override
    public void save(InventarioCarga inventarioCarga) {
        // TODO Auto-generated method stub
        inventarioCargaDao.save(inventarioCarga);
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public InventarioCarga findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
}
