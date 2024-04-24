package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Direccion;
import com.comunicamosmas.api.service.dto.DireccionDTO;

import java.util.List;
import java.util.Optional;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; 

public interface IDireccionDao extends JpaRepository<Direccion, Long> {

    @Query(value="SELECT d.id_direccion as id , d.id_cliente  as idCliente, d.barrio, d.latitud, d.longitud ,\n" + //
                "d.nota, d.estado, d.rara, d.tipo, CONCAT(d.a_tipo , ' ', d.a_numero,d.a_letra, ' ',d.b_tipo,' ',d.b_numero,d.b_letra,'/',d.barrio,'/',lm.municipio,'/',ld.departamento) as direccion FROM direcciones d\n" + //
                "INNER JOIN lista_municipios lm ON lm.id_municipio = d.municipio \n" + //
                "INNER JOIN lista_departamentos ld ON ld.id_departamento  = d.departamento \n" + //
                "WHERE d.id_cliente = :idCliente" , nativeQuery = true)
    public Optional<List<Object[]>> findByIdCliente(@Param("idCliente") Long idCliente);
}
