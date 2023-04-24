package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Pago;

import java.util.List;

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
}
