package com.comunicamosmas.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.comunicamosmas.api.domain.Menu;

public interface IMenuDao extends CrudRepository<Menu , Long>{


    @Query(value="SELECT id, title, icon FROM jhipster_menu m where m.parent_id  < 1" , nativeQuery = true)
    public Optional<List<Object[]>> listMenu();

    @Query(value="SELECT id, title, icon , url FROM jhipster_menu m WHERE m.parent_id = :id" , nativeQuery = true)
    public Optional<List<Object[]>> subMenu(@Param("id") int id);

    //buscarMenu por sql
    @Query(value ="SELECT m.id , m.title, m.icon FROM jhipster_relations r   INNER JOIN jhipster_menu m ON r.menu_id = m.id \n"
            +"WHERE r.role_id = :role AND m.parent_id < 1" , nativeQuery =   true)
    public Optional<List<Object[]>> menu(@Param("role") String role);

    @Query(value="SELECT m.title, m.icon, m.url FROM jhipster_relations r INNER JOIN jhipster_menu m ON r.menu_id = m.id \n" 
            + "WHERE r.role_id = :role AND m.parent_id = :parent and r.active ='Y'", nativeQuery = true)
    public Optional<List<Object[]>> subMenu(@Param("role") String role , @Param("parent") int parent);

    @Query(value="SELECT * FROM jhipster_menu WHERE parent_id  = :parent" , nativeQuery = true)
    public Optional<List<Object[]>> findByParent(@Param("parent") Integer parent);
    

   
}
