package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Pago;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IPagoDao extends CrudRepository<Pago, Long> {
	
	@Query(value="SELECT \n"
			+ "pagos.id_pago as idPago,\n"
			+ "pagos.id_recibo_caja as reciboCaja,\n"
			+ "pagos.valor_cobro as valorCobrado,\n"
			+ "pagos.marca as marca\n"
			+ " FROM pagos where pagos.id_deuda = :idDeuda" , nativeQuery=true)
	public List<Object[]> findByIdDeuda(@Param("idDeuda") Integer idDeuda);

	@Query(value = "SELECT \n" + //
			"\t\tpag.id_recibo_caja AS id_recibo_caja, \n" + //
			"\t\tpag.marca AS marca,\n" + //
			"\t\tpag.id_contrato AS id_contrato,\n" + //
			"\t\tpag.id_cliente AS id_cliente,\n" + //
			"\t\tpag.id_servicio AS id_servicio,\n" + //
			"\t\tpag.id_deuda AS id_deuda,\n" + //
			"\t\tpag.mes_servicio AS mes_servicio,\n" + //
			"\t\tpag.valor_cobro AS valor_cobro,\n" + //
			"\t\tpag.id_medio_pago AS id_medio_pago,\n" + //
			"\t\tpag.id_cajero AS id_cajero,\n" + //
			"\t\tpag.lugar AS lugar,\n" + //
			"\t\tpag.comprobante AS comprobante,\n" + // 
			"\t\tdeud.valor_iva AS valor_iva,\n" + // 
			"\t\tcontrato.estrato AS estrato,\n" + //
			"\t\tCASE WHEN clien.tipo_cliente = 'N' THEN CONCAT(clien.apellido_paterno,' ',clien.apellido_materno,' ',clien.nombre_primer,' ',clien.nombre_segundo)\n" + //
			"\t\t WHEN clien.tipo_cliente ='J' THEN clien.razon_social END  AS nombrecliente,"+
			"\t\tclien.documento AS documentocliente,\n" + //
			"\t\tclien.tipo_cliente AS cliente_tipo_cliente,\n" + //
			"\t\tclien.razon_social AS cliente_razon_social,\n" + // 
			"\t\tserv.nombre AS nombreservicio,\n" + // 
			"\t\tmpagos.nombre AS nombremediopago,\n" + // 
			"\t\tdireccion.barrio AS barrio,\n" + // 
			"\t\tCONCAT(cajero.nombre,' ',cajero.apellidos) AS nombredelcajero,\n" + //
			"\t\ttipotec.nombre as servicioTecnologia,\n" + //
			"\t\tdireccion.municipio AS municipio,\n" + //
			"\t\t deud.instalacion, deud.reconexion, deud.materiales , deud.traslado , deud.otros , deud.concepto_aux \n" + //
			"\t\tFROM pagos pag\n" + //
			"\t\tLEFT JOIN contratos contrato\n" + //
			"\t\t\tON pag.id_contrato = contrato.id_contrato\n" + //
			"\n" + //
			"\t\tLEFT JOIN direcciones direccion\n" + //
			"\t\t\tON contrato.id_direccion_servicio = direccion.id_direccion\n" + //
			"\n" + //
			"\t\tLEFT JOIN clientes clien\n" + //
			"\t\t\tON pag.id_cliente = clien.id_cliente\n" + //
			"\t\tLEFT JOIN servicios serv\n" + //
			"\t\t\tON pag.id_servicio = serv.id_servicio\n" + //
			"\t\tLEFT JOIN deudas deud\n" + //
			"\t\t\tON pag.id_deuda = deud.id_deuda\n" + //
			"\t\tLEFT JOIN medios_pago mpagos\n" + //
			"\t\t\tON pag.id_medio_pago = mpagos.id_medio_pago\n" + //
			"\t\tLEFT JOIN usuarios cajero\n" + //
			"\t\t\tON pag.id_cajero = cajero.id_usuario\n" + //
			"\t\tLEFT JOIN tipos_tecnologia tipotec\n" + //
			"\t\t\tON contrato.id_tecnologia = tipotec.id_tecnologia\n" + //
			"\n" + //
			"\t\tWHERE contrato.grupo IN (:origen) AND pag.id_ciudad IN (:ciudades) AND \n" + //
			"\t\tpag.fechaf BETWEEN :inicial AND :final\n" + //
			"\t\tGROUP BY pag.id_pago" , nativeQuery = true)
	public Optional<List<Object[]>> reciboCaja(@Param("ciudades")List<Integer> ciudades , 
	@Param("inicial") Integer fecha_iniciar , @Param("final") Integer fecha_final , @Param("origen") List<String> origen);

	@Query(value="SELECT pa.id_recibo_caja FROM pagos pa \n" 
	+" WHERE pa.id_servicio = :idServicio AND pa.lugar = :origen ORDER BY pa.id_recibo_caja  DESC LIMIT 0 , 1 " , nativeQuery = true)
	public Optional<List<Object[]>> findLastRc(@Param("idServicio")Long idServicio , @Param("origen") String origen);

}
