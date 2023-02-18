package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.SidebarRelations;
import com.comunicamosmas.api.repository.ISidebarRelationsDao;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SidebarRelationsServiceImpl implements ISidebarRelationsService {

    @Autowired
    ISidebarRelationsDao sidebarRelationsDao;

    @Override
    public List<SidebarRelations> findByIdRole(String rol) {
        System.out.print("este rol " + rol);
        return (List<SidebarRelations>) sidebarRelationsDao.findByidRole(rol);
    }
}
