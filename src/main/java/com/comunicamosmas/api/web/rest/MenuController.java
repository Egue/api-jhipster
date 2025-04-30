package com.comunicamosmas.api.web.rest;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comunicamosmas.api.domain.Menu;
import com.comunicamosmas.api.domain.Relations;
import com.comunicamosmas.api.service.IMenuService;
import com.comunicamosmas.api.service.IRelationsService;
import com.comunicamosmas.api.service.dto.MenuDTO;
import com.comunicamosmas.api.service.dto.MenuDTOJson;
import com.comunicamosmas.api.service.dto.SubMenuRelationsDTO;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;
import com.mysql.fabric.Response;

import tech.jhipster.config.JHipsterProperties.Http;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlmas")
public class MenuController {

    @Autowired
    IMenuService menuService;

    @Autowired
    IRelationsService relationsService;

    @GetMapping("/menu")
    public ResponseEntity<?> menu() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<MenuDTO> result = menuService.listMenu();

            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/menu")
    public ResponseEntity<?> saveMenu(@RequestBody MenuDTO menuDTO) {
        Map<String, Object> response = new HashMap<>();

        try {
            if (menuDTO.getTitle() != null) {
                Menu menu = new Menu();
                menu.setTitle(menuDTO.getTitle());
                menu.setIcon(menuDTO.getIcon());
                menu.setParent(0L);
                menuService.save(menu);
            } else {

                throw new ExceptionNullSql(new Date(), "Post null", "servicios null");
            }

            response.put("response", "creado");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (ExceptionNullSql e) {

            response.put("error", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("error", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/relations/{role}")
    public ResponseEntity<?> relations(@PathVariable String role) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<MenuDTO> result = relationsService.findByRole(role);

            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/relations")
    public ResponseEntity<?> saveRelations(@RequestBody Relations relations) {
        Map<String, Object> response = new HashMap<>();

        try {
            if (relations.getRole() != null) {
                relationsService.save(relations);
            } else {

                throw new ExceptionNullSql(new Date(), "Post null", "servicios null");
            }

            response.put("response", "creado");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (ExceptionNullSql e) {

            response.put("error", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("error", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/subMenu")
    public ResponseEntity<?> subMenu(@RequestParam String role, @RequestParam int idMenu) {
        Map<String, Object> response = new HashMap<>();

        try {

            List<SubMenuRelationsDTO> subMenu = relationsService.subMenuFindRoleAndMenu(role, idMenu);

            response.put("response", subMenu);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/relations")
    public ResponseEntity<?> updated(@RequestBody SubMenuRelationsDTO subMenu) {
        Map<String, Object> response = new HashMap<>();

        try {

            relationsService.updated(subMenu);

            response.put("response", "actualizado");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("error", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/relations/test/{role}")
    public ResponseEntity<?> testMenu(@PathVariable String role) {
        Map<String, Object> response = new HashMap<>();

        try {

            List<MenuDTOJson.Menu> menu = menuService.menu(role);

            response.put("response", menu);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("error", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/submenu/{id}")
    public ResponseEntity<?> findSubMenuByParent(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<MenuDTOJson.SubMenu> subMenu = menuService.subMenuByParent(id);

            response.put("response", subMenu);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("error", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/submenu/save")
    public ResponseEntity<?> save(@RequestBody Menu menu) {
        Map<String, Object> response = new HashMap<>();

        try {

            menuService.save(menu);

            response.put("response", "creado");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("error", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/relations/sincronize")
    public ResponseEntity<?> sincroniceMenu(@RequestParam("id") Integer parent, @RequestParam("role") String role) {
        Map<String, Object> response = new HashMap<>();
        try {

            relationsService.sincronice(parent, role);

            response.put("response", "creado");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (ExceptionNullSql e) {
            response.put("error", e.getMessage() + ":" + e.getDetails());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            // TODO: handle exception
            response.put("error", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
