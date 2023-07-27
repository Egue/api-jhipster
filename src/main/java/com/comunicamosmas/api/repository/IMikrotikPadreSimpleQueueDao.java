package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.MikrotikPadreSimpleQueue;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IMikrotikPadreSimpleQueueDao extends CrudRepository<MikrotikPadreSimpleQueue, Long> {
    @Query(
        value = "SELECT * FROM mikrotik_padre_simple_queue mp WHERE mp.name LIKE CONCAT('%', :namePadre, '%') AND \n" +
        "mp.limit_at = :velocidad AND mp.reuso < :reuso AND mp.id_estacion = :estacion LIMIT 0,1",
        nativeQuery = true
    )
    public MikrotikPadreSimpleQueue findByVelocidad(
        @Param("namePadre") String namePadre,
        @Param("velocidad") String velocidad,
        @Param("reuso") Long reuso,
        @Param("estacion") Long estacion
    );

    @Query(
        value = "SELECT * FROM mikrotik_padre_simple_queue mp WHERE mp.id_tarifa_reuso = :idPlan AND  mp.reuso < :reuso AND mp.id_estacion = :idEstacion LIMIT 0,1",
        nativeQuery = true
    )
    public MikrotikPadreSimpleQueue findByIdPlanAndReuso(@Param("idPlan")Long idPlan, @Param("reuso")Long reuso, @Param("idEstacion")Long idEstacion);
    
    /*
     * query para buscar los datos del componente reuso**/
    @Query(value="SELECT \n"
    		+ "co.id_contrato as idContrato,\n"
    		+ "co.id_estacion as idEstacion,\n"
    		+ "ta.velocidad,\n"
    		+ "tt.nombre as nombreTecnologia,\n"
    		+ "wx.id_pass as idWinmaxPass\n"
    		+ " FROM contratos co \n"
    		+ "inner join tarifas ta on ta.id_tarifa = co.id_tarifa_promo\n"
    		+ "inner join tipos_tecnologia tt on tt.id_tecnologia = ta.id_tecnologia\n"
    		+ "inner join winmax_pass wx on wx.id_contrato = co.id_contrato\n"
    		+ "where co.id_contrato = :idContrato limit 0,1" , nativeQuery = true)
    public List<Object[]> simpleQueueInfoComponent(@Param("idContrato") Long idContrato);
    
    /**
     * listar padres por estacion*/
    @Query(value="SELECT \n"
    		+ "padre.id_padre as id,\n"
    		+ "padre.name,\n"
    		+ "padre.target,\n"
    		+ "padre.comment,\n"
    		+ "padre.limit_at as limitAt,\n"
    		+ "padre.max_limit as maxLimit,\n"
    		+ "padre.reuso \n"
    		+ "FROM  mikrotik_padre_simple_queue padre \n"
    		+ "where padre.id_estacion = :idEstacion", nativeQuery =true)
    public List<Object[]> listPadreByEstacion(@Param("idEstacion") Long idEstacion);
    
}
