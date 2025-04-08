package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Empresa;

import liquibase.pro.packaged.d;
import liquibase.pro.packaged.r;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IEmpresaDao extends CrudRepository<Empresa, Long> {
	
    @Query(value = "SELECT * FROM empresas m WHERE m.nombre_comercial LIKE %?1%", nativeQuery = true)
    public List<Empresa> findByLikeNombreComercial(String empresa);
    
    @Query(value= "SELECT * FROM empresas m "
    		+ "INNER JOIN contratos co on co.id_empresa = m.id_empresa"
    		+ " WHERE co.id_contrato = :idContrato", nativeQuery=true)
    public Empresa findEmpresaByContrato(@Param("idContrato") Long IdContrato);

    @Query(value="SELECT * FROM empresas m WHERE m.estado = 1" , nativeQuery = true)
    public Optional<List<Empresa>> findAllByStatus();

    @Query(value="SELECT * FROM empresas m WHERE m.id_ciudad IN (SELECT uc.id_ciudad FROM usuarios_ciudades uc WHERE uc.id_usuario = :idUser)" , nativeQuery = true)
    public List<Empresa> findFilter(@Param("idUser") Long idUser);

    @Query(value="SELECT d.valor_base , d.valor_iva , d.valor_parcial , d.valor_total  , d.instalacion, d.reconexion, d.materiales, d.traslado, d.otros, d.concepto_aux, d.mes_servicio, "+
            "r.num_resolucion , r.prefijo , r.fecha_resolucion , r.rango_inicio , r.rango_final , r.vigencia, " +
            "s.nombre , d.factura, d.facturado_fecha, d.id_contrato , d.resultado_factura_electronica " +
            "FROM deudas d "+
            "inner join resoluciones r  on r.id_resolucion = d.facturado_id_resolucion " +
            "inner join servicios s on s.id_servicio = d.id_servicio "+
            "WHERE d.id_cliente = :idCliente AND d.factura = :factura AND d.refiere = :refiere and d.facturado_fecha like concat('%',:mes,'%') " , nativeQuery = true)
    public Optional<List<Object[]>> findDeudasAndEmpresaAndResolucion(@Param("idCliente") Integer idCliente , @Param("factura") String factura , 
    @Param("refiere") String refiere , @Param("mes") String mes);

    @Query(value="SELECT d.valor_base , d.valor_iva , d.valor_parcial , d.valor_total  , d.instalacion, d.reconexion, d.materiales, d.traslado, d.otros, d.concepto_aux, d.mes_servicio, "+
            "r.num_resolucion , r.prefijo , r.fecha_resolucion , r.rango_inicio , r.rango_final , r.vigencia, " +
            "s.nombre , d.factura, d.facturado_fecha, d.id_contrato , d.resultado_factura_electronica , concat(di.a_tipo,di.a_numero,di.a_letra, di.b_tipo, di.b_numero,di.b_letra,'-', di.numero, '/', di.barrio) " +
            "FROM deudas d "+
            "inner  join contratos co on co.id_contrato = d.id_contrato " +
            " inner  join direcciones di on di.id_direccion = co.id_direccion_servicio " +
            "inner join resoluciones r  on r.id_resolucion = d.facturado_id_resolucion " +
            "inner join servicios s on s.id_servicio = d.id_servicio "+
            "WHERE d.id_cliente = :idCliente AND d.factura = :factura AND d.refiere = :refiere and d.facturado_fecha like concat('%',:mes,'%') " , nativeQuery = true)
    public Optional<List<Object[]>> findDeudasAndEmpresaAndResolucionForCombo(@Param("idCliente") Integer idCliente , @Param("factura") String factura , 
    @Param("refiere") String refiere , @Param("mes") String mes);

}
