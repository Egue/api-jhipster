package com.comunicamosmas.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.Menu;
import com.comunicamosmas.api.repository.IMenuDao;
import com.comunicamosmas.api.service.IMenuService;
import com.comunicamosmas.api.service.dto.MenuDTO;
import com.comunicamosmas.api.service.dto.MenuDTOJson;
import com.comunicamosmas.api.service.dto.MenuDTOJson.SubMenu;

@Service
public class MenuServiceImpl implements IMenuService {

    @Autowired
    IMenuDao menuDao;

    @Override
    public List<Menu> findMenu() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findMenu'");
    }

    @Override
    public List<MenuDTO> listMenu() {
        // TODO Auto-generated method stub
        Optional<List<Object[]>> result = menuDao.listMenu();

        List<MenuDTO> menu = new ArrayList<>();

        result.ifPresent(resp -> {

            for (Object[] rs : resp) {
                MenuDTO obj = new MenuDTO();

                obj.setId((int) rs[0]);
                obj.setTitle((String) rs[1]);
                obj.setIcon((String) rs[2]);
                obj.setActive(false);

                menu.add(obj);
            }
        });

        return menu;

    }

    @Override
    public Optional<List<Object[]>> subMenuFindByParent(int parent) {

        Optional<List<Object[]>> result = menuDao.subMenu(parent);

        return result;
    }

    @Override
    public List<MenuDTOJson.Menu> menu(String role) {
        // buscar role
        Optional<List<Object[]>> result = menuDao.menu(role);

        List<MenuDTOJson.Menu> menuJson = new ArrayList<>();

        result.ifPresent(resp -> {

            for (Object[] rs : resp) {
                MenuDTOJson menuDto = new MenuDTOJson();

                MenuDTOJson.Menu menu = menuDto.new Menu();

                menu.setTitle((String) rs[1]);

                menu.setIcon((String) rs[2]);

                List<MenuDTOJson.SubMenu> subMenu = this.subMenu((int) rs[0], role);

                menu.setSubMenu(subMenu);

                menuJson.add(menu);
            }
        });

        return menuJson;
    }

    private List<MenuDTOJson.SubMenu> subMenu(int parent, String role) {
        Optional<List<Object[]>> result = menuDao.subMenu(role, parent);

        List<MenuDTOJson.SubMenu> subMe = new ArrayList<>();

        result.ifPresent(resp -> {

            for (Object[] rs : resp) {
                MenuDTOJson menu = new MenuDTOJson();

                MenuDTOJson.SubMenu obj = menu.new SubMenu();

                obj.setTittle((String) rs[0]);
                obj.setIcon((String) rs[1]);
                obj.setUrl((String) rs[2]);

                subMe.add(obj);
            }

        });

        return subMe;
    }

    @Override
    public List<SubMenu> subMenuByParent(Integer parent) {

        Optional<List<Object[]>> result = menuDao.subMenu(parent);

        List<SubMenu> submenu = result.map(res -> {
            List<SubMenu> sub = new ArrayList<>();

            for (Object[] rs : res) {
                MenuDTOJson menu = new MenuDTOJson();

                MenuDTOJson.SubMenu obj = menu.new SubMenu();

                obj.setTittle((String) rs[1]);
                obj.setIcon((String) rs[2]);
                obj.setUrl((String) rs[3]);
                sub.add(obj);
            }

            return sub;
        }).orElse(new ArrayList<>());

        return submenu;

    }

    @Override
    public void save(Menu menu) {

        this.menuDao.save(menu);
    }

    @Override
    public List<Menu> bindByParent(Integer parent) {

        Optional<List<Object[]>> resulset = menuDao.findByParent(parent);

        List<Menu> menu = resulset.map(resp -> {
            List<Menu> sub = new ArrayList<>();
            for (Object[] rs : resp) {
                Menu obj = new Menu();
                Long id = Long.parseLong(rs[0].toString());
                obj.setId((Long) id);
                obj.setTitle((String) rs[1]);
                obj.setIcon((String) rs[2]);
                obj.setUrl((String) rs[3]);
                Long parent_id = Long.parseLong(rs[4].toString());
                obj.setParent((Long) parent_id);

                sub.add(obj);

            }

            return sub;

        }).orElse(new ArrayList<>());

        return menu;
    }

}
