package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.WinmaxPass;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IWinmaxPassDao extends CrudRepository<WinmaxPass, Long> {
    @Query(value = "SELECT count(id_contrato) as contrato from winmax_pass wp WHERE wp.id_contrato = ? LIMIT 0,1", nativeQuery = true)
    public Long countFindByIdContrato(Long idContrato);

    @Query(value = "SELECT * FROM winmax_pass wp WHERE wp.id_contrato = ? LIMIT 0,1", nativeQuery = true)
    public WinmaxPass findByIdContrato(Long idContrato);
    
    @Query(value ="SELECT \n"
    		+ "co.id_contrato as idContrato,\n"
    		+ "case \n"
    		+ "WHEN co.estado = 0 then 'por abrobar' \n"
    		+ "WHEN co.estado = 1 THEN 'Activo' \n"
    		+ "WHEN co.estado = 2 THEN 'Cortado' \n"
    		+ "WHEN co.estado = 3 THEN 'Anulado' \n"
    		+ "WHEN co.estado = 4 THEN 'Retirado' \n"
    		+ "WHEN co.estado = 5 THEN 'Supension temporal' \n"
    		+ "END as contratoEstado, \n"
    		+ "cli.tipo_cliente as tipoCliente,\n"
    		+ "case  \n"
    		+ "		WHEN cli.tipo_cliente ='N' then concat(cli.apellido_paterno,' ',cli.apellido_materno,' ', cli.nombre_primer, ' ', cli.nombre_segundo)\n"
    		+ "        WHEN cli.tipo_cliente = 'J' then concat(cli.razon_social)\n"
    		+ "END as nombreCliente,\n"
    		+ "winmax_pass.usuario,\n"
    		+ "winmax_pass.pass,\n"
    		+ "winmax_pass.marca ,\n"
    		+ "ttg.nombre as nombreTecnologia,\n"
    		+ "ta.velocidad ,\n"
    		+ "ta.nombre as nombreTarifa,\n"
    		+ "ta.valor, \n "
    		+ "ta.codigo_mikrotik as codigoMikrotik\n"
    		+ " FROM controlmas.winmax_pass\n"
    		+ "inner join contratos co on co.id_contrato = winmax_pass.id_contrato\n"
    		+ "inner join tarifas ta on ta.id_tarifa = co.id_tarifa_promo\n"
    		+ "inner join clientes cli on cli.id_cliente = co.id_cliente\n"
    		+ "inner join tipos_tecnologia ttg on ttg.id_tecnologia = ta.id_tecnologia\n"
    		+ "where winmax_pass.id_estacion = :idEstacion" , nativeQuery=true)
    public List<Object[]> findByIdEstacionConDatos(@Param("idEstacion") Long idEstacion); 
}
