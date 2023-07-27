package com.comunicamosmas.api.service;

import java.util.List;
import java.util.Optional;

import com.comunicamosmas.api.domain.Menu;
import com.comunicamosmas.api.service.dto.MenuDTO;
import com.comunicamosmas.api.service.dto.MenuDTOJson;

public interface IMenuService {
    
    public List<Menu> findMenu();

    public List<MenuDTO> listMenu();

    public Optional<List<Object[]>> subMenuFindByParent(int parent);

    public List<MenuDTOJson.SubMenu> subMenuByParent(Integer parent);


    public List<MenuDTOJson.Menu> menu(String role);

    public void save(Menu menu);

    public List<Menu> bindByParent(Integer parent);

    
 
}
