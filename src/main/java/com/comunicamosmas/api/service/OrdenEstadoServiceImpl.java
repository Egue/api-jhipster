package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Admin;
import com.comunicamosmas.api.repository.IAdminDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdenEstadoServiceImpl implements IAdminService {

    @Autowired
    IAdminDao adminDao;

    @Override
    public List<Admin> findAll() {
        // TODO Auto-generated method stub
        return (List<Admin>) adminDao.findAll();
    }

    @Override
    public Admin save(Admin admin) {
        // TODO Auto-generated method stub
        adminDao.save(admin);
        return null;
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Admin findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
}
