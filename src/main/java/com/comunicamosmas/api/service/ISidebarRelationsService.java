package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.SidebarRelations;
import java.util.List;
import java.util.Optional;

public interface ISidebarRelationsService {
    public List<SidebarRelations> findByIdRole(String rol);
}
