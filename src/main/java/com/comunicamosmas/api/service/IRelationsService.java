package com.comunicamosmas.api.service;

import java.util.List;

import com.comunicamosmas.api.domain.Relations;
import com.comunicamosmas.api.service.dto.MenuDTO;
import com.comunicamosmas.api.service.dto.SubMenuRelationsDTO;

public interface IRelationsService {
    
    public void save(Relations relations);

    public List<MenuDTO> findByRole(String role);

    public List<SubMenuRelationsDTO> subMenuFindRoleAndMenu(String role , int idMenu);

    //actualizar 
    public Relations findById(Long idRelations);


    public void updated(SubMenuRelationsDTO subMenu);

    public void sincronice(Integer parent , String role);


}
