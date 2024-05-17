package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.TarifaInstalacion;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ITarifaInstalacionDao extends CrudRepository<TarifaInstalacion, Long> {
    @Query(value="SELECT * FROM tarifas_instalacion ti WHERE ti.id_servicio = :idServicio AND ti.valor like concat('%' , :valor, '%') AND ti.estado = 1 " , nativeQuery=true)
    public Optional<List<TarifaInstalacion>> findByIdServicio(@Param("idServicio") Long idServicio , @Param("valor")Long valor);
}
