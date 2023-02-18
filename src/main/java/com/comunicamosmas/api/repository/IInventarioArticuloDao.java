package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.InventarioArticulo;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IInventarioArticuloDao extends CrudRepository<InventarioArticulo, Long> {
    @Query(value = "SELECT * FROM inventarios_articulos ar WHERE ar.id_categoria = ?1", nativeQuery = true)
    public List<InventarioArticulo> findArticuloByIdCategoria(Long id);
}
