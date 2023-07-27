package com.comunicamosmas.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.Authority;
import com.comunicamosmas.api.repository.AuthorityRepository;
import com.comunicamosmas.api.service.IAuthorityService;

@Service
public class AuthorityServiceImpl implements IAuthorityService{

    @Autowired
    AuthorityRepository authorityDao;

    @Override
    public List<Authority> findAll() {
        // TODO Auto-generated method stub

        return (List<Authority>) authorityDao.findAll();
        
    }
    
}
