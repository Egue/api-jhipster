package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.InventarioModelo;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IInventarioModeloDao extends CrudRepository<InventarioModelo, Long> {
    @Query(value = "SELECT * FROM inventarios_modelos m WHERE m.id_marca = ?1", nativeQuery = true)
    public List<InventarioModelo> findByIdModelo(Long id);
}
