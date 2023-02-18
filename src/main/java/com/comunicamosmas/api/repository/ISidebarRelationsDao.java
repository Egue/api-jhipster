package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.SidebarRelations;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ISidebarRelationsDao extends CrudRepository<SidebarRelations, Long> {
    @Query(value = "SELECT * FROM jhipster_relations jr WHERE jr.id_role = :role", nativeQuery = true)
    public List<SidebarRelations> findByidRole(@Param(value = "role") String role);
}
