package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Proveedor;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IProveedorDao extends CrudRepository<Proveedor, Long> {
    @Query(value = "SELECT * FROM proveedores p WHERE p.estado = ?1", nativeQuery = true)
    public List<Proveedor> findByEstadoActivo(int estado);

    @Query(value = "SELECT * FROM proveedores p WHERE p.razon_social LIKE %?1% AND p.estado=1", nativeQuery = true)
    public List<Proveedor> findByLikeRazonSocial(String razonSocial);
}
