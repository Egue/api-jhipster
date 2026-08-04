package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.OrdenArticulo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrdenArticuloDao extends CrudRepository<OrdenArticulo, Long> {

    @Query(value = """
        SELECT
         u.id_usuario ,
         concat(u.nombre , " " , u.apellidos) as empleado,
          ia.nombre,
                                                                                          o.nota,
                                                                                          o.nota_final,
                                                                                          o.fechaf_registra,
                                                                                          o.fechaf_asiste,
                                                                                          case
                                                                                          	when cl.tipo_cliente = "J" THEN  concat(cl.razon_social ," / " , cl.documento)
                                                                                              when cl.tipo_cliente = "N" THEN concat(cl.nombre_primer , " ", cl.apellido_paterno , " / ", cl.documento)
                                                                                          end as cliente,
                                                                                          e.nombre as estacion,
                                                                                          oa.id_contrato,
                                                                                          oa.id_estacion,
                                                                                          oa.cantidad_empresa,
                                                                                          oa.cantidad_usuario
                                                                                          FROM ordenes_articulos oa
                                                                                              left join contratos co on co.id_contrato = oa.id_contrato
                                                                                              inner join clientes cl on cl.id_cliente = co.id_cliente
                                                                                              left join estaciones e on e.id_estacion = oa.id_estacion
                                                                                              inner join usuarios u on u.id_usuario = oa.id_usuario
                                                                                              inner join ordenes o on o.id_orden = oa.id_orden
                                                                                              inner join inventarios_articulos ia on ia.id_articulo = oa.id_articulo

        where o.fechaf_asiste between :inicio and :fin
        """, nativeQuery = true)
    public List<Object[]> consumoByBetween(@Param("inicio") String inicio, @Param("fin") String fin);
}
