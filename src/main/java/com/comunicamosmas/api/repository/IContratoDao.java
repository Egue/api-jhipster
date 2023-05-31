package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.service.dto.DatosClienteDTO;
import com.comunicamosmas.api.service.dto.ListContratoDTO; 

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IContratoDao extends CrudRepository<Contrato, Long> {
    @Query(
        value = "SELECT \n" +
        "mun.municipio as nombreMunicipio,\n" +
        "ser.nombre as nombreServicio,\n" +
        "co.id_contrato as idContrato,\n" +
        "dir.barrio,\n" +
        "concat(dir.tipo,' / ',dir.a_tipo,' ',dir.a_numero,' ',dir.a_letra,' ',dir.b_tipo,' ',dir.b_numero,' ',dir.b_letra,' ',dir.numero,' / ',dir.nota) as direccion,\n" +
        "CASE  \n" +
        "	WHEN co.estado =  0 THEN 'Creado'\n" +
        "    WHEN co.estado = 1 THEN 'Activo'\n" +
        "    WHEN co.estado = 2 THEN 'Cortado'\n" +
        "    WHEN co.estado = 3 THEN 'Anulado'\n" +
        "    WHEN co.estado = 4 THEN 'Retirado'\n" +
        "    WHEN co.estado = 5 THEN 'Suspendido'\n" +
        "END as estado\n" +
        " FROM clientes\n" +
        "\n" +
        "INNER JOIN contratos co ON co.id_cliente = clientes.id_cliente\n" +
        "INNER JOIN direcciones dir ON dir.id_direccion = co.id_direccion_servicio\n" +
        "INNER JOIN servicios ser ON ser.id_servicio = co.id_servicio\n" +
        "INNER JOIN lista_municipios mun ON mun.id_municipio = dir.municipio\n" +
        "WHERE clientes.id_cliente = :idCliente",
        nativeQuery = true )
	
    public List<Object[]> findByIdCliente(@Param("idCliente") Long idCliente);
    
    /**
     * consultar para cargar los datos de conctacto del cliente*/
    @Query(value="SELECT \n"
    		+ "co.id_contrato idContrato, \n"
    		+ "co.estrato,\n"
    		+ "case\n"
    		+ "	when cl.tipo_cliente = 'J' then cl.razon_social\n"
    		+ "    when cl.tipo_cliente = 'N' then CONCAT(cl.apellido_paterno , ' ',cl.apellido_materno , ' ',cl.nombre_primer , ' ',cl.nombre_segundo)\n"
    		+ "    end  as nombreCliente,\n"
    		+ "cl.documento,\n"
    		+ "cl.celular_a as celularA,\n"
    		+ "cl.celular_b as celularB,\n"
    		+ "cl.mail,\n"
    		+ "ta.nombre nombreTarifa, \n"
    		+ "ta.velocidad,\n"
    		+ "FORMAT(ta.valor , 2)as  valor ,\n"
    		+ "dir.longitud as longDireccion,\n"
    		+ "dir.latitud as latDireccion,\n"
    		+ "es.latitud as latEstacion,\n"
    		+ "es.longitud as longEstacion,\n"
    		+ "concat(dir.tipo,' / ',dir.a_tipo,' ',dir.a_numero,' ',dir.a_letra,' ',dir.b_tipo,' ',dir.b_numero,' ',dir.b_letra,' ',dir.numero,' / ',dir.nota,' / ',dir.barrio) as direccionServicio\n"
    		+ "\n"
    		+ "FROM contratos co\n"
    		+ "\n"
    		+ "left join clientes cl on cl.id_cliente = co.id_cliente\n"
    		+ "left join tarifas ta on ta.id_tarifa = co.id_tarifa_promo\n"
    		+ "left join direcciones dir on dir.id_direccion = co.id_direccion_servicio\n"
    		+ "left join estaciones es on es.id_estacion = co.id_estacion\n"
    		+ "where co.id_contrato = :idContrato" , nativeQuery=true)
    public List<Object[]> datosClienteByIdContrato(@Param(value="idContrato") Long idContrato);
    
    /**
     * utilizado para informacion de factura encabezado*/
    @Query(value="SELECT  \n"
    		+ "cl.id_cliente as idCliente,\n"
    		+ "concat(cl.documento,'-',cl.dv) documento,\n"
    		+ "concat(cl.celular_a,'/',cl.celular_b) celular,\n"
    		+ "case\n"
    		+ "when cl.tipo_cliente = 'J' then cl.razon_social\n"
    		+ "when cl.tipo_cliente = 'N' then CONCAT(cl.apellido_paterno , ' ',cl.apellido_materno , ' ',cl.nombre_primer , ' ',cl.nombre_segundo)\n"
    		+ "end  as nombreCliente,\n"
    		+ "concat(dir.tipo,' / ',dir.a_tipo,' ',dir.a_numero,' ',dir.a_letra,' ',dir.b_tipo,' ',dir.b_numero,' ',dir.b_letra,' ',dir.numero,' / ',dir.nota,' / ',dir.barrio,' / ',mun.municipio , ' / ',dep.departamento) as direccion\n"
    		+ " \n"
    		+ "FROM clientes cl\n"
    		+ "\n"
    		+ "inner join contratos co on co.id_cliente = cl.id_cliente\n"
    		+ "inner join direcciones dir on dir.id_direccion = co.id_direccion_factura\n"
    		+ "inner join lista_municipios mun on mun.id_municipio = dir.municipio\n"
    		+ "inner join lista_departamentos dep on dir.departamento = dep.id_departamento\n"
    		+ "where co.id_contrato = :idContrato", nativeQuery = true)
    public List<Object[]> infoByFactura(@Param("idContrato") Long idContrato);

	/**CARTERA */
	@Query(value="SELECT \n"
	+ "co.id_contrato AS ff_iddelcontrato,\n"
	+ "co.fisico AS ff_fisico,\n"
	+ "#co.id_cliente AS ff_idedelcliente,\n"
	+ "#co.id_servicio AS ff_idedelservicio,\n"
	+ "#co.limite_promo AS ff_limitepromotarifa,\n"
	+ "co.id_zona AS ff_idzonacontra,\n"
	+ "#co.id_direccion_servicio AS ff_iddireccion,\n"
	+ "co.marca AS ff_marcacontrato,
	+ "co.inicio AS ff_iniciocontrato,
	+ "co.grupo AS ff_grupocontra, 
	+ "CASE 
	+ "	WHEN co.estado = 0 THEN 'Por Aprobar'
	+ "	WHEN co.estado = 1 THEN 'Activo'
	+ "	WHEN co.estado = 2 THEN 'Cortado'
	+ "	WHEN co.estado = 3 THEN 'Anulado'
	+ "	WHEN co.estado = 4 THEN 'Retirado'
	+ "	WHEN co.estado = 5 THEN 'Suspensión temporal'
	+ "END ff_estadocontrato,
	+ "co.ultimo_pago AS ultimo_pago,
	+ "co.ultimo_estado AS fecha_ultimo_estado,
	+ "co.estrato AS estrato_contrato,
	+ "co.venta as modalidad,
	+ "
	+ "/* TARIFAS */ 
	+ "ta.valor AS 'tarifa_general_valor',
	+ "ta.numero_canales AS 'numero_canales',
	+ "ta.velocidad AS 'velocidad',
	+ "tb.nombre AS 'tarifa_nombre',
	+ "tb.valor AS 'tarifa_promo_valor',
	+ "CASE
	+ "	WHEN tb.tipo_banda = 0
	+ "	THEN 'No Aplica'
	+ "	WHEN tb.tipo_banda = 1
	+ "	THEN 'Banda Ancha'
	+ "	WHEN tb.tipo_banda = 2
	+ "	THEN 'Dedicado'
	+ "END as t_tipo_banda,
	+ "
	+ "/*	CLIENTES */
	+ "concat(clientes.telefono,'/', clientes.celular_a,'/', clientes.celular_b ) as contacto,
	+ "clientes.documento AS cliente_documento,
	+ "clientes.dv ,
	+ "clientes.tipo_cliente AS cliente_tipo_cliente,
	+ "case 
	+ "	WHEN clientes.tipo_cliente = 'J' THEN clientes.razon_social 
	+ "	WHEN clientes.tipo_cliente = 'N' THEN concat(clientes.apellido_paterno,' ',clientes.apellido_materno,' ',clientes.nombre_primer,' ',clientes.nombre_segundo)
	+ "END nombre_cliente,
	+ "clientes.mail,
	+ "/*	ZONAS */
	+ "zonas.nombre AS zona_nombre,
	+ "/*	SERVICIOS */
	+ "servicios.nombre AS servicios_nombre,
	+ "/* PAGOS */
	+ "SUM(if(deudasb.instalacion = '0' && deudasb.reconexion = '0' && deudasb.materiales = '0' && deudasb.traslado = '0' && deudasb.otros = '0' && (deudasb.estado = '1' || deudasb.estado = '3') , 1, 0)) AS mesesdebe,
	+ "SUM(if(deudasb.estado = '1' || deudasb.estado = '3', deudasb.valor_total, 0)) AS total_debe,
	+ "SUM(if(deudasb.estado = '1' || deudasb.estado = '3', deudasb.valor_parcial, 0)) AS total_abonos,
	+ "/*SUM(if(deudasb.estado = '1' || deudasb.estado = '3', 1, 0)) AS mesesdebe,*/
	+ "
	+ "/* DIRECCIONES */
	+ "concat(direcciones.a_tipo , ' ',direcciones.a_numero,' ',direcciones.a_letra,' ',direcciones.b_tipo , ' ',direcciones.b_numero,' ',direcciones.b_letra,' ',direcciones.numero , ' ',direcciones.nota) as direccion ,
	+ "direcciones.barrio AS dirr_barrio,
	+ "direcciones.tipo as dirr_tipo, 
	+ "lista_municipios.municipio,
	+ "lista_departamentos.departamento ,
	+ " 
	+ "CASE 
	+ "	WHEN co.id_tecnologia =  1
		+ "THEN 'Fibra'
		+ "WHEN co.id_tecnologia =  2
		+ "THEN 'Inalámbrico'
		+ "WHEN co.id_tecnologia =  3
		+ "THEN 'EOC'
		+ "WHEN co.id_tecnologia =  4
		+ "THEN 'FTTH'
		+ "WHEN co.id_tecnologia =  5
		+ "THEN 'Analoga Coaxial'
		+ "WHEN co.id_tecnologia =  6
		+ "THEN 'Digital Cerrada'
		+ "WHEN co.id_tecnologia =  7
		+ "THEN 'Digital Abierta'
		+ "WHEN co.id_tecnologia =  8
		+ "THEN 'FTTH ONU'
		+ "WHEN co.id_tecnologia =  9
		+ "THEN '3G'
		+ "WHEN co.id_tecnologia =  10
		+ "THEN 'Analoga EOC'
		+ "WHEN co.id_tecnologia =  11
		+ "THEN 'Analoga FTTH'
	+ "END as t_tipo_tecnologia,
	+ "CONCAT(usr.nombre , ' ' , usr.apellidos) AS vendedor
	+ "
	+ "FROM contratos co
	+ "LEFT JOIN
	+ "  clientes
	+ "	ON co.id_cliente = clientes.id_cliente
	+ "LEFT JOIN
	+ "  zonas
	+ "	ON co.id_zona = zonas.id_zona 
	+ "LEFT JOIN
	+ "  servicios
	+ "	ON co.id_servicio = servicios.id_servicio    
	+ "/**********************************************/
	+ "LEFT JOIN
	+ "  deudas deudasb
	+ "	ON co.id_contrato = deudasb.id_contrato
	+ "/**********************************************/
	+ "LEFT JOIN
	+ "  direcciones
	+ "	ON co.id_direccion_servicio = direcciones.id_direccion  
	+ "LEFT JOIN
	+ "  lista_municipios
	+ "	ON direcciones.municipio = lista_municipios.id_municipio
	+ "LEFT JOIN
	+ "  lista_departamentos
	+ "	ON direcciones.departamento = lista_departamentos.id_departamento                              
	+ "LEFT JOIN
	+ "	tarifas ta
	+ "	ON co.id_tarifa = ta.id_tarifa    
	+ "LEFT JOIN
	+ "	tarifas tb
	+ "	ON co.id_tarifa_promo = tb.id_tarifa
	+ "LEFT JOIN
	+ "		usuarios usr
	+ "		ON co.id_vendedor = usr.id_usuario
	+ "
	+ "/* LEFT JOIN pagos pag 
	+ "	ON co.id_contrato = pag.id_contrato
	+ "*/
	+ "WHERE co.id_servicio = '1' 
	+ "AND co.estado IN(0,1,2,5)
	+ "GROUP BY 
	+ "/* deudasb.mes_servicio,*/
	+ "co.id_contrato  " , nativeQuery=true)
	public List<Object[]> carta
}
