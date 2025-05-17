package com.comunicamosmas.api.repository;
 
import com.comunicamosmas.api.domain.Orden; 
import com.comunicamosmas.api.service.dto.OrdenForInstalacionFindByIdOrdenDTO;
import com.comunicamosmas.api.service.dto.OrdenInstalacionDTO;
import com.comunicamosmas.api.service.dto.OrderDetalleDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IOrdenDao extends CrudRepository<Orden, Long> {
    @Query(
        value = "SELECT o.id_orden as idOrden, o.id_contrato as idContrato , concat(c.apellido_paterno , ' ', c.nombre_primer , ' ', c.nombre_segundo) as nombreCliente,\n" +
        "	c.documento , \n" +
        "    e.nombre_comercial as nombreComercial,\n" +
        "    s.nombre ,  tt.nombre as tipoTecnologia\n" +
        " FROM ordenes o\n" +
        "inner join clientes c on c.id_cliente = o.id_cliente\n" +
        "inner join empresas e on e.id_empresa = o.id_empresa\n" +
        "inner join contratos co on co.id_contrato = o.id_contrato\n" +
        "inner join servicios s on s.id_servicio = o.id_servicio\n" +
        "inner join tarifas t on t.id_tarifa = co.id_tarifa_promo\n" +
        "inner join tipos_tecnologia tt on tt.id_tecnologia = t.id_tecnologia\n" +
        "WHERE tt.servicio = 1 AND o.tipo_orden = 1 AND o.estado IN (0,1,2,3) and o.anulada = 0 AND o.winmax=0 AND o.id_usuario_ejecuta > 0",
        nativeQuery = true
    )
    public List<OrdenInstalacionDTO> ordenInstalacion();

    @Query(
        value = "SELECT o.id_orden as idOrden , o.id_contrato as idContrato, concat(c.apellido_paterno , ' ', c.nombre_primer , ' ', c.nombre_segundo) as nombreCliente,\n" +
        "tt.nombre as tecnologia , t.codigo_mikrotik as codigoMikrotik, t.velocidad,\n" +
        "s.id_servicio as idServicio , e.id_empresa as idEmpresa , ld.departamento , lm.municipio , d.barrio, \n" +
        "concat(d.tipo,'/',d.a_tipo,' ',d.a_numero,' ',d.a_letra,' ',d.b_tipo,' ',d.b_numero,' ',d.b_letra,' ',d.numero) as direccion	\n" +
        "  FROM ordenes o\n" +
        "inner join clientes c on c.id_cliente = o.id_cliente\n" +
        "inner join empresas e on e.id_empresa = o.id_empresa\n" +
        "inner join contratos co on co.id_contrato = o.id_contrato\n" +
        "inner join servicios s on s.id_servicio = o.id_servicio\n" +
        "inner join tarifas t on t.id_tarifa = co.id_tarifa_promo\n" +
        "inner join tipos_tecnologia tt on tt.id_tecnologia = t.id_tecnologia\n" +
        "inner join direcciones d on d.id_direccion = o.id_direccion\n" +
        "inner join lista_municipios lm on lm.id_municipio = d.municipio\n" +
        "inner join lista_departamentos ld on ld.id_departamento = d.departamento\n" +
        "WHERE tt.servicio = 1 AND o.id_orden  = :id",
        nativeQuery = true
    )
    public OrdenForInstalacionFindByIdOrdenDTO ordenForInstalacionFindByIdOrden(@Param("id")Long id);

    @Query(
        value = "SELECT u.telefono FROM ordenes o INNER JOIN usuarios u ON u.id_usuario = o.id_usuario_ejecuta WHERE" + " o.id_orden = :idOrden",
        nativeQuery = true
    )
    public String findTelefonoByIdOrden(@Param("idOrden")Long idOrde);

    @Query(
        value = "SELECT o.id_orden as idOrden, o.id_contrato as idContrato , concat(c.apellido_paterno , ' ', c.nombre_primer , ' ', c.nombre_segundo) as nombreCliente,\n" +
        "	c.documento , \n" +
        "    e.nombre_comercial as nombreComercial,\n" +
        "    s.nombre ,  tt.nombre as tipoTecnologia\n" +
        " FROM ordenes o\n" +
        "inner join clientes c on c.id_cliente = o.id_cliente\n" +
        "inner join empresas e on e.id_empresa = o.id_empresa\n" +
        "inner join contratos co on co.id_contrato = o.id_contrato\n" +
        "inner join servicios s on s.id_servicio = o.id_servicio\n" +
        "inner join tarifas t on t.id_tarifa = co.id_tarifa_promo\n" +
        "inner join tipos_tecnologia tt on tt.id_tecnologia = t.id_tecnologia\n" +
        "WHERE tt.servicio = 1 AND o.tipo_orden = 1 AND o.estado IN (0,1,2,3) and o.anulada = 0 AND o.winmax=0 AND o.id_usuario_ejecuta > 0" +
        " AND o.fechaf_registra BETWEEN :valor1 AND :valor2",
        nativeQuery = true
    )
    public List<OrdenInstalacionDTO> getListFindBetwee(@Param("valor1")String valor1, @Param("valor2")String valor2);
    
    //ordenes buscar por tipo
    @Query(value="SELECT id_orden, id_contrato FROM ordenes where tipo_orden = :tipoOrden" , nativeQuery = true)
    public List<Orden> findAllByIdTipo(@Param("tipoOrden") Long tipoOrden);
    
    //ordenes por contrato lista
    @Query(value="select \n"
    		+ "ord.id_contrato as idContrato,\n"
    		+ "ord.id_orden as idOrden,\n"
    		+ "ord.numero_a as numeroA,\n"
    		+ "ord.numero_b as numeroB,\n"
    		+ "ores.nombre as tipoOrden,\n"
    		+ "ord.refiere as origen,\n"
    		+ "date_format(ord.fechaf_registra , \"%Y-%m-%d\") as registro,\n"
    		+ "date_format(ord.fechaf_asigna ,\"%Y-%m-%d\" ) as asignacion,\n"
    		+ "date_format(ord.fechaf_asiste ,\"%Y-%m-%d\" ) as ejecucion,\n"
    		+ "concat(users.nombre,\" \",users.apellidos) as userAsigna,\n"
    		+ "concat(user_eje.nombre,\" \",user_eje.apellidos) as userEjecuta\n"
    		+ "from ordenes ord\n"
    		+ "inner join ordenes_estados ores on ores.id_estado = ord.tipo_orden\n"
    		+ "left join usuarios users on users.id_usuario = ord.id_usuario_asigna\n"
    		+ "left join usuarios user_eje on user_eje.id_usuario = ord.id_usuario_ejecuta\n"
    		+ "where ord.id_contrato = :idContrato" ,nativeQuery=true)
    public List<Object[]> listOrdenByIdContrato(@Param("idContrato") Long idContrato);
    
    
    /**BUSCAR RECONEXION ACTIVAS*/
    @Query(value="select \n"
    		+ "ord.id_contrato as idContrato,\n"
    		+ "ord.id_orden as idOrden,  \n"
    		+ "date_format(ord.fechaf_registra , \"%Y-%m-%d\") as registro,\n"
    		+ "case 	\n"
    		+ "	when clientes.tipo_cliente = 'N' then concat(clientes.nombre_primer,' ', clientes.nombre_segundo,' ', clientes.apellido_paterno , ' / ',clientes.documento)\n"
    		+ "    when clientes.tipo_cliente = 'J' then concat(clientes.razon_social,' /', clientes.documento)\n"
    		+ "    end as nombreCliente,\n"
    		+ "estaciones.nombre as nombreEstacion\n"
    		+ "\n"
    		+ "from ordenes ord\n"
    		+ "inner join tipos_tecnologia tt on tt.id_tecnologia = ord.id_tecnologia\n"
    		+ "inner join clientes on clientes.id_cliente = ord.id_cliente\n"
    		+ "left join estaciones on ord.id_estacion = estaciones.id_estacion\n"
    		+ "\n"
    		+ "where ord.tipo_orden = :tipoOrden and ord.estado IN (0,1,2,3) and ord.abierta = 1 and ord.anulada = 0 and ord.winmax = 0  and tt.servicio = 1 " ,nativeQuery=true)
    public List<Object[]> listOrdenByTipoOrden(@Param("tipoOrden") Long tipoOrden);
    
    /**BUSCAR ORDENES X IDSERVICIO TIPOCLIENTE*/
    @Query(value="select \n"
    		+ "ord.id_contrato as idContrato,\n"
    		+ "ord.id_orden as idOrden,  \n"
    		+ "date_format(ord.fechaf_registra , \"%Y-%m-%d\") as registro,\n"
    		+ "case 	\n"
    		+ "	when clientes.tipo_cliente = 'N' then concat(clientes.nombre_primer,' ', clientes.nombre_segundo,' ', clientes.apellido_paterno , ' / ',clientes.documento)\n"
    		+ "    when clientes.tipo_cliente = 'J' then concat(clientes.razon_social,' /', clientes.documento)\n"
    		+ "    end as nombreCliente,\n"
    		+ "estaciones.nombre as nombreEstacion , \n"
    		+ "ord.nota  \n"
    		+ "\n"
    		+ "from ordenes ord\n"
    		+ "inner join tipos_tecnologia tt on tt.id_tecnologia = ord.id_tecnologia\n"
    		+ "inner join clientes on clientes.id_cliente = ord.id_cliente\n"
    		+ "left join estaciones on ord.id_estacion = estaciones.id_estacion\n"
    		+ "\n"
    		+ "where ord.tipo_orden = :tipoOrden and ord.id_servicio = :idServicio and clientes.tipo_cliente = :tipoCliente and ord.estado IN (0,1,2,3) and ord.abierta = 1 and ord.anulada = 0 and ord.winmax = 0  and tt.servicio = 1 " ,nativeQuery=true)
    public List<Object[]> ordenesByIdServicioAndTipoClienteAndTipoOrden(@Param("tipoOrden")Long tipoOrden , @Param("idServicio") Long idServicio, @Param("tipoCliente") String tipoCliente);
    
    /**BUSCAR ULTIMO REGISTRO DE REFIERE A O B*/
    @Query(value = " select * from ordenes where ordenes.refiere = :refiere AND ordenes.id_servicio = :idServicio order by  ordenes.id_orden desc limit 0,1", nativeQuery = true)
    public Orden findLastRegisterByRefiere(@Param("refiere") String refiere , @Param("idServicio") Long idServicio);
    
    /*BUSCAR POR TIPO Y CONTRATO ACTIVAS**/
    @Query(value="SELECT * FROM ordenes where ordenes.abierta = 1 and ordenes.anulada = 0 "
    		+ "and ordenes.estado = 0 and ordenes.tipo_orden = :tipoOrden and ordenes.id_contrato = :idContrato ", nativeQuery = true)
    public Orden findOrdenActivaByTipo(@Param("tipoOrden")Long tipoOrden , @Param("idContrato")Long idContrato);

    //reporte de ordenes por tipo y observaciones de visitas usando
    //between de fecha a fecha 
    /*
     * @Param("idServicio")
     * @Param("fechaInicio")
     * @Param("fechaFinal")
     * @Param("tipoOrden")
     */
    @Query(value="SELECT ord.id_orden,  \n" + //
            "                        es.nombre, \n" + //
            "                        ord.causa_solicitud , \n" + //
            "                        ord.id_contrato ,  \n" + //
            "                        ord.fechaf_registra, \n" + //
            "                        ord.refiere ,  \n" + //
            "                        cli.tipo_cliente, \n" + //
            "                        case  \n" + //
            "                        WHEN cli.tipo_cliente = 'J' THEN CONCAT(cli.razon_social) \n" + //
            "                            WHEN cli.tipo_cliente = 'N' THEN CONCAT(cli.nombre_primer, cli.nombre_segundo , ' / ' , cli.apellido_paterno, cli.apellido_materno) \n" + //
            "                        END as cliente, \n" + //
            "                        cli.documento, \n" + //
            "                        dir.barrio,\n" + //
            "                        concat(dir.tipo, ' ',dir.a_tipo,' ',dir.a_numero,' ',dir.a_letra,' ', dir.b_tipo , ' ' , dir.b_numero, ' ',dir.b_letra , ' ',dir.numero , '/', dir.nota) as direccion,\n" + //
            "                        cli.celular_b,\n" + //
            "                        cli.celular_a,\n" + //
            "                        tip.nombre as nombre_tecnologia, \n" + //
            "                        ord.nota, \n" + //
            "                        case when ord.estado = 0 THEN 'Sin asignar' \n" + //
            "                        when ord.estado = 1 THEN 'Asignada' \n" + //
            "                            when ord.estado = 2 THEN 'En proceso' \n" + //
            "                            when ord.estado = 3 THEN 'Ejecutada' \n" + //
            "                            when ord.estado = 4 THEN 'Anulada' \n" + //
            "                        end as estado, \n" + //
            "                        ord.nota_final, \n" + //
            "                        ord.anulada, \n" + //
            "                        group_concat(ov.detalle separator ',') visitaFallida \n" + //
            "                        \n" + //
            "                        FROM ordenes ord  \n" + //
            "                        inner join contratos co on co.id_contrato = ord.id_contrato\n" + //
            "                        inner join clientes cli on cli.id_cliente = co.id_cliente\n" + //
            "                        inner join ordenes_estados es on es.id_estado = ord.tipo_orden \n" + //
            "                        left join ordenes_visitas ov on ov.id_orden = ord.id_orden                         \n" + //
            "                        left join direcciones dir on dir.id_direccion = co.id_direccion_servicio\n" + //
            "                        left join tipos_tecnologia tip on tip.id_tecnologia  = co.id_tecnologia\n" + //
            "                        where ord.fechaf_registra between :fechaInicio and :fechaFinal  \n" + //
            "                        and ord.tipo_orden = :idTipo \n" + //
            "                        and ord.id_servicio = :idServicio \n" + //
            "                        group by ord.id_orden" , nativeQuery = true)
    public Optional<List<Object[]>> findTipoAndIdServicioAndFechaCreadaWithVisitaFallida(@Param("idServicio") Long idServicio , @Param("idTipo") Long idTipo ,
    @Param("fechaInicio") Long fechaInicio, @Param("fechaFinal") Long fechaFinal);

    @Query(value="SELECT MONTH(winmax_marca) mes,count(*)  as cantidad from ordenes \n" + //
            "WHERE winmax = 1 AND YEAR(winmax_marca) = :ano AND tipo_orden = :tipo and id_servicio IN  (:servicios) group by mes\n" + //
            "ORDER BY mes ASC" , nativeQuery = true)
    public Optional<List<Object[]>> chatLineCortado(@Param("servicios") List<Integer> servicios , @Param("ano") Integer ano , @Param("tipo")Integer tipo);
    
    //buscar ultimo registro de ordenes del tipo
    @Query(value="SELECT ord.numero_a , ord.numero_b FROM ordenes ord \n" 
    +"WHERE ord.refiere = :origen AND ord.id_empresa = :idEmpresa ORDER BY ord.numero_b DESC LIMIT 0, 1" , nativeQuery = true)
    public Optional<List<Object[]>> findNumeroOrden(@Param("origen") String origen , @Param("idEmpresa") Long idEmpresa);


    @Query(value = """
    SELECT ord.id_orden,  
           es.nombre, 
           ord.causa_solicitud, 
           ord.id_contrato,  
           ord.fechaf_registra, 
           ord.refiere,  
           cli.tipo_cliente, 
           CASE  
               WHEN cli.tipo_cliente = 'J' THEN CONCAT(cli.razon_social) 
               WHEN cli.tipo_cliente = 'N' THEN CONCAT(cli.nombre_primer, cli.nombre_segundo, ' / ', cli.apellido_paterno, cli.apellido_materno) 
           END as cliente, 
           cli.documento, 
           dir.barrio,
           CONCAT(dir.tipo, ' ', dir.a_tipo, ' ', dir.a_numero, ' ', dir.a_letra, ' ', dir.b_tipo, ' ', dir.b_numero, ' ', dir.b_letra, ' ', dir.numero, '/', dir.nota) as direccion,
           cli.celular_b,
           cli.celular_a,
           tip.nombre as nombre_tecnologia, 
           ord.nota, 
           CASE 
               WHEN ord.estado = 0 THEN 'Sin asignar' 
               WHEN ord.estado = 1 THEN 'Asignada' 
               WHEN ord.estado = 2 THEN 'En proceso' 
               WHEN ord.estado = 3 THEN 'Ejecutada' 
               WHEN ord.estado = 4 THEN 'Anulada' 
           END as estado, 
           ord.nota_final, 
           ord.anulada, 
           GROUP_CONCAT(ov.detalle SEPARATOR ',') as visitaFallida ,
            ord.id_usuario_ejecuta
    FROM ordenes ord  
    INNER JOIN contratos co ON co.id_contrato = ord.id_contrato
    INNER JOIN clientes cli ON cli.id_cliente = co.id_cliente
    INNER JOIN ordenes_estados es ON es.id_estado = ord.tipo_orden 
    LEFT JOIN ordenes_visitas ov ON ov.id_orden = ord.id_orden                         
    LEFT JOIN direcciones dir ON dir.id_direccion = co.id_direccion_servicio
    LEFT JOIN tipos_tecnologia tip ON tip.id_tecnologia = co.id_tecnologia
    WHERE ord.fechaf_registra BETWEEN :fechaInicio AND :fechaFinal
    AND ord.tipo_orden = :idTipo 
    AND ord.id_servicio = :idServicio 
    AND ord.estado IN (:estado) 
    AND ord.abierta = :abierta 
    AND ord.anulada = :anulada
    GROUP BY ord.id_orden
    /*#{#pageable}*/""",
    countQuery = """
    SELECT COUNT(DISTINCT ord.id_orden)             
    FROM ordenes ord  
    INNER JOIN contratos co ON co.id_contrato = ord.id_contrato
    INNER JOIN clientes cli ON cli.id_cliente = co.id_cliente
    INNER JOIN ordenes_estados es ON es.id_estado = ord.tipo_orden 
    LEFT JOIN ordenes_visitas ov ON ov.id_orden = ord.id_orden                         
    LEFT JOIN direcciones dir ON dir.id_direccion = co.id_direccion_servicio
    LEFT JOIN tipos_tecnologia tip ON tip.id_tecnologia = co.id_tecnologia
    WHERE ord.fechaf_registra BETWEEN :fechaInicio AND :fechaFinal
    AND ord.tipo_orden = :idTipo 
    AND ord.id_servicio = :idServicio 
    AND ord.estado IN (:estado) 
    AND ord.anulada = :anulada 
    AND ord.abierta = :abierta""",
    nativeQuery = true)
Page<Object[]> findOrdenesDetalladas(
    @Param("abierta") Long abierta,
    @Param("anulada") Long anulada,
    @Param("estado") List<Integer> estado,
    @Param("idServicio") Long idServicio,
    @Param("idTipo") Long idTipo,
    @Param("fechaInicio") Long fechaInicio,
    @Param("fechaFinal") Long fechaFinal,
    Pageable pageable);

    @Query(value = """
    SELECT * from ordenes ord
    inner join usuarios usr on usr.id_usuario = ord.id_usuario_asigna
    WHERE ord.abierta = 1 and ord.anulada = 0 AND ord.estado IN(0,1,2) """, nativeQuery = true)
    public List<Orden> findAllOrdenesActivesByAsignation();

    
}
