package com.comunicamosmas.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.comunicamosmas.api.domain.Relations; 

public interface IRelationsDao extends CrudRepository<Relations , Long> {

    @Query(value = "SELECT m.* FROM  jhipster_relations r INNER JOIN jhipster_menu m on m.id = r.menu_id \n"
    + "WHERE r.role_id = :role AND r.active IS NULL" , nativeQuery=true)
    public Optional<List<Object[]>> relations(@Param("role") String role);


    @Query(value = "SELECT r.role_id from jhipster_relations r WHERE r.role_id = :role  AND r.menu_id = :idMenu" , nativeQuery = true)
    public Optional<Object[]> existByRoleAndMenu(@Param("role") String role, @Param("idMenu") int idMenu);


    @Query(value="SELECT r.*, m.title,  m.icon FROM jhipster_relations r INNER JOIN jhipster_menu m on m.id = r.menu_id \n"
    +"WHERE m.parent_id = :idMenu AND r.role_id = :role" , nativeQuery = true)
    public Optional<List<Object[]>> findRoleAndMenu(@Param("role") String role, @Param("idMenu") int idMenu);

    @Query(value="SELECT * FROM jhipster_relations WHERE role_id = :role AND active is not null" , nativeQuery = true)
    public Optional<List<Object[]>> relationsByRole(@Param("role") String role);
     


     
    
}
