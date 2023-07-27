package com.comunicamosmas.api.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.Menu;
import com.comunicamosmas.api.domain.Relations;
import com.comunicamosmas.api.repository.IRelationsDao;
import com.comunicamosmas.api.service.IMenuService;
import com.comunicamosmas.api.service.IRelationsService;
import com.comunicamosmas.api.service.dto.MenuDTO;
import com.comunicamosmas.api.service.dto.SubMenuRelationsDTO;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;

 

@Service
public class RelationsServiceImpl implements IRelationsService{

    @Autowired
    IRelationsDao relationsDao;
    
    @Autowired
    IMenuService menuService;

    @Override
    public List<MenuDTO> findByRole(String role) {
        
        Optional<List<Object[]>> result = relationsDao.relations(role);

        List<MenuDTO> relations = new ArrayList<>();

        result.ifPresent(resp -> {
            for(Object[] rs: resp)
            {
                    MenuDTO obj = new MenuDTO();

                    obj.setId((int) rs[0]);
                    obj.setTitle((String) rs[1]);
                    obj.setIcon((String) rs[2]);

                    relations.add(obj);
            }
        });
        
       

        return relations;

       
        
    }



    @Override
    public void save(Relations relations) { 

       //validar que no exista ya creado el registros
        if(this.findByRoleAndMenu(relations.getRole(), relations.getMenu()))
        {
           throw new ExceptionNullSql(new Date(), "Ya registra", "Ya existe un registro");
        }
        //relations.setActive("N");
        relationsDao.save(relations);
        //buscando 
         //buscar el menu de la relations
         Optional<List<Object[]>> subMenu = menuService.subMenuFindByParent(relations.getMenu());

         subMenu.ifPresent( resp -> {
            for(Object[] rs : resp)
            {
                Relations obj = new Relations();
                obj.setActive("N");
                obj.setRole(relations.getRole());
                obj.setMenu((int) rs[0]);

                relationsDao.save(obj);
            }
         });

    }

    private boolean findByRoleAndMenu(String role, int idMenu)
    {
        Optional<Object[]> count = relationsDao.existByRoleAndMenu(role, idMenu);

         if(count.isPresent() && count.get().length > 0)
         {
            return true;
         }else{
            return false;
         }
        
    }



    @Override
    public List<SubMenuRelationsDTO> subMenuFindRoleAndMenu(String role, int idMenu) {

         Optional<List<Object[]>> result = relationsDao.findRoleAndMenu(role, idMenu);

         List<SubMenuRelationsDTO> submenu = new ArrayList<>();

         result.ifPresent(resp -> {

            for(Object[] rs : resp)
            {
                SubMenuRelationsDTO obj = new SubMenuRelationsDTO();

                obj.setRole_id((String) rs[0]);
                obj.setMenu_id((int) rs [1]);
                obj.setId((int) rs[2]);
                obj.setActive((String) rs[3]);
                obj.setTittle((String) rs[4]);
                obj.setIcon((String) rs[5]);

                submenu.add( obj);
            }
         });

         return submenu;
    }



    @Override
    public Relations findById(Long idRelations) {
        // TODO Auto-generated method stub
        return relationsDao.findById(idRelations).orElse(null);
    }



    @Override
    public void updated(SubMenuRelationsDTO subMenu) {

          Relations obj = new Relations();
          
          obj.setId((long) subMenu.getId());
          obj.setMenu(subMenu.getMenu_id());
          obj.setRole(subMenu.getRole_id());
          obj.setActive(subMenu.getActive());

          relationsDao.save(obj);
    }



    @Override
    public void sincronice(Integer parent , String role) {
        // TODO Auto-generated method stub
        //consultar una tabla y comprar con otra 
        try {

            List<Menu> menusList = menuService.bindByParent(parent); 

            List<Relations> relations = this.relationsByRole(role);

            //comparar relations con menuslits
            if(!relations.isEmpty())
            {
                if(!menusList.isEmpty())
                {
                  
                    //extraemos los idMenu existentes en relations
                    List<Integer> existingMenuIds = relations.stream()
                                    .map(Relations::getMenu)
                                    .collect(Collectors.toList());
                   

                    //filtrar los menus que no estan presentes en la lista relations
                    List<Menu> toCreateMenus= menusList.stream()
                    .filter(menu->existingMenuIds.stream().noneMatch(id-> id == Integer.parseInt(menu.getId().toString())))
                    .collect(Collectors.toList());
                    
                    if(!toCreateMenus.isEmpty())
                    {
                        for(Menu rs:toCreateMenus)
                        {
                            Relations relati = new Relations();
                            relati.setRole(role);
                            relati.setActive("N");
                            relati.setMenu(Integer.parseInt(rs.getId().toString()));
                            this.save(relati);

                        }
                    }
                    
                }else{
                    throw new ExceptionNullSql(new Date() , "Menu List" , "no hay Menu con el parent_id");
                }

            }else{

                throw new ExceptionNullSql(new Date() , "Relations is empty" , "No se encontro nada con el rol");
            }


            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private List<Relations> relationsByRole(String role)
    {
        Optional<List<Object[]>> result = relationsDao.relationsByRole(role);

        List<Relations> relations = result.map( resp -> {
                List<Relations> rela = new ArrayList<>();
                    for(Object[] rs :resp)
                    {
                        Relations obj = new Relations();
                        obj.setRole((String) rs[0]);
                        obj.setMenu((Integer) rs[1]);
                        Long id = Long.parseLong(rs[2].toString());
                        obj.setId((Long) id);
                        
                        rela.add(obj);
                    }
                return rela;

        }).orElse(new ArrayList<>());

        return relations;
    }

    
    

}
