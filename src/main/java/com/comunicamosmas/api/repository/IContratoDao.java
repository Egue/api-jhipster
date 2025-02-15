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
    		+ "concat(dir.tipo,' / ',dir.a_tipo,' ',dir.a_numero,' ',dir.a_letra,' ',dir.b_tipo,' ',dir.b_numero,' ',dir.b_letra,' ',dir.numero,' / ',dir.nota,' / ',dir.barrio) as direccionServicio,\n"
    		+ "co.fisico, \n"
			+ "cl.tipo_cliente \n"
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
	+ "#CASE WHEN co.marca = '0000-00-00 00:00:00' then 'Fecha Desconocida' ELSE DATE_FORMAT(co.marca, '%Y-%m-%d %H:%i:%s') END AS ff_marcacontrato,\n"
	+ "CONVERT(co.marca , CHAR) as co_marca,\n"
	+ "co.inicio AS ff_iniciocontrato,\n"
	+ "co.grupo AS ff_grupocontra, \n"
	+ "CASE \n"
	+ "	WHEN co.estado = 0 THEN 'Por Aprobar'\n"
	+ "	WHEN co.estado = 1 THEN 'Activo'\n"
	+ "	WHEN co.estado = 2 THEN 'Cortado'\n"
	+ "	WHEN co.estado = 3 THEN 'Anulado'\n"
	+ "	WHEN co.estado = 4 THEN 'Retirado'\n"
	+ "	WHEN co.estado = 5 THEN 'Suspensión temporal'\n"
	+ "END ff_estadocontrato,\n"
	+ "#CASE WHEN co.ultimo_pago = '0000-00-00 00:00:00' then 'Fecha Desconocida' ELSE DATE_FORMAT(co.ultimo_pago , '%Y-%m-%d %H:%i:%s')END AS ultimo_pago,\n"
	+ "CONVERT(co.ultimo_pago , CHAR) as co_ultimo_pago ,\n"
	+ "#CASE WHEN co.ultimo_estado = '0000-00-00 00:00:00' then 'Fecha Desconocida' ELSE DATE_FORMAT(co.ultimo_estado , '%Y-%m-%d %H:%i:%s') END  AS fecha_ultimo_estado,\n"
	+ "CONVERT(co.ultimo_estado , CHAR) as co_ultimo_estado,\n"
	+ "co.estrato AS estrato_contrato,\n"
	+ "co.venta as modalidad,\n"
	+ "ta.valor AS 'tarifa_general_valor',\n"
	+ "ta.numero_canales AS 'numero_canales',\n"
	+ "ta.velocidad AS 'velocidad',\n"
	+ "tb.nombre AS 'tarifa_nombre',\n"
	+ "tb.valor AS 'tarifa_promo_valor',\n"
	+ "CASE\n"
	+ "	WHEN tb.tipo_banda = 0\n"
	+ "	THEN 'No Aplica'\n"
	+ "	WHEN tb.tipo_banda = 1\n"
	+ "	THEN 'Banda Ancha'\n"
	+ "	WHEN tb.tipo_banda = 2\n"
	+ "	THEN 'Dedicado'\n"
	+ "END as t_tipo_banda,\n"
	+ "concat(clientes.telefono,'/', clientes.celular_a,'/', clientes.celular_b ) as contacto,\n"
	+ "clientes.documento AS cliente_documento,\n"
	+ "clientes.dv ,\n"
	+ "clientes.tipo_cliente AS cliente_tipo_cliente,\n"
	+ "case \n"
	+ "	WHEN clientes.tipo_cliente = 'J' THEN clientes.razon_social \n"
	+ "	WHEN clientes.tipo_cliente = 'N' THEN concat(clientes.apellido_paterno,' ',clientes.apellido_materno,' ',clientes.nombre_primer,' ',clientes.nombre_segundo)\n"
	+ "END nombre_cliente,\n"
	+ "clientes.mail,\n"
	+ "zonas.nombre AS zona_nombre,\n"
	+ "servicios.nombre AS servicios_nombre,\n"
	+ "SUM(if(deudasb.instalacion = '0' && deudasb.reconexion = '0' && deudasb.materiales = '0' && deudasb.traslado = '0' && deudasb.otros = '0' && (deudasb.estado = '1' || deudasb.estado = '3') , 1, 0)) AS mesesdebe,\n"
	+ "SUM(if(deudasb.estado = '1' || deudasb.estado = '3', deudasb.valor_total, 0)) AS total_debe,\n"
	+ "SUM(if(deudasb.estado = '1' || deudasb.estado = '3', deudasb.valor_parcial, 0)) AS total_abonos,\n"
	+ "/*SUM(if(deudasb.estado = '1' || deudasb.estado = '3', 1, 0)) AS mesesdebe,*/\n"
	+ "concat(direcciones.a_tipo , ' ',direcciones.a_numero,' ',direcciones.a_letra,' ',direcciones.b_tipo , ' ',direcciones.b_numero,' ',direcciones.b_letra,' ',direcciones.numero , ' ',direcciones.nota) as direccion ,\n"
	+ "direcciones.barrio AS dirr_barrio,\n"
	+ "direcciones.tipo as dirr_tipo, \n"
	+ "lista_municipios.municipio,\n"
	+ "lista_departamentos.departamento ,\n"
	+ "CASE \n"
	+ "	WHEN co.id_tecnologia =  1\n"
		+ "THEN 'Fibra'\n"
		+ "WHEN co.id_tecnologia =  2\n"
		+ "THEN 'Inalámbrico'\n"
		+ "WHEN co.id_tecnologia =  3\n"
		+ "THEN 'EOC'\n"
		+ "WHEN co.id_tecnologia =  4\n"
		+ "THEN 'FTTH'\n"
		+ "WHEN co.id_tecnologia =  5\n"
		+ "THEN 'Analoga Coaxial'\n"
		+ "WHEN co.id_tecnologia =  6\n"
		+ "THEN 'Digital Cerrada'\n"
		+ "WHEN co.id_tecnologia =  7\n"
		+ "THEN 'Digital Abierta'\n"
		+ "WHEN co.id_tecnologia =  8\n"
		+ "THEN 'FTTH ONU'\n"
		+ "WHEN co.id_tecnologia =  9\n"
		+ "THEN '3G'\n"
		+ "WHEN co.id_tecnologia =  10\n"
		+ "THEN 'Analoga EOC'\n"
		+ "WHEN co.id_tecnologia =  11\n"
		+ "THEN 'Analoga FTTH'\n"
		+"WHEN co.id_tecnologia = 12 THEN 'Otros' \n"
	+ "END as t_tipo_tecnologia,\n"
	+ "CONCAT(usr.nombre , ' ' , usr.apellidos) AS vendedor,\n"
	+ "estaciones.nombre as nombre_estacion \n"
	+ "FROM contratos co\n"
	+ "LEFT JOIN\n"
	+ "  clientes\n"
	+ "	ON co.id_cliente = clientes.id_cliente\n"
	+ "LEFT JOIN\n"
	+ "  zonas\n"
	+ "	ON co.id_zona = zonas.id_zona \n"
	+ "LEFT JOIN\n"
	+ "  servicios\n"
	+ "	ON co.id_servicio = servicios.id_servicio    \n"
	+ "LEFT JOIN\n"
	+ "  deudas deudasb\n"
	+ "	ON co.id_contrato = deudasb.id_contrato\n"
	+ "LEFT JOIN\n"
	+ "  direcciones\n"
	+ "	ON co.id_direccion_servicio = direcciones.id_direccion  \n"
	+ "LEFT JOIN\n"
	+ "  lista_municipios\n"
	+ "	ON direcciones.municipio = lista_municipios.id_municipio\n"
	+ "LEFT JOIN\n"
	+ "  lista_departamentos\n"
	+ "	ON direcciones.departamento = lista_departamentos.id_departamento  \n"                            
	+ "LEFT JOIN\n"
	+ "	tarifas ta\n"
	+ "	ON co.id_tarifa = ta.id_tarifa    \n"
	+ "LEFT JOIN\n"
	+ "	tarifas tb\n"
	+ "	ON co.id_tarifa_promo = tb.id_tarifa\n"
	+ "LEFT JOIN estaciones ON co.id_estacion = estaciones.id_estacion \n"
	+ "LEFT JOIN\n"
	+ "		usuarios usr\n"
	+ "		ON co.id_vendedor = usr.id_usuario\n"	 
	+ "WHERE co.id_servicio IN (:servicios) \n"
	+"AND co.grupo IN (:origen)\n"
	+ "AND co.estado IN (:estados) \n"
	+ "GROUP BY \n"
	+ "co.id_contrato" , nativeQuery=true)
	public Optional<List<Object[]>> carteraByidServicio(@Param("servicios") List<Integer> servicios , @Param("estados") List<String> estados, @Param("origen") List<String> origen);

 
}
