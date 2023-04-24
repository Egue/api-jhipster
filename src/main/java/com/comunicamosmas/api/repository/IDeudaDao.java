package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Deuda;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IDeudaDao extends CrudRepository<Deuda, Long> {
	
	@Query(value="SELECT \n"
			+ "deu.id_deuda as idDeuda,\n"
			+ "deu.factura,\n"
			+ "deu.mes_servicio as periodo,\n"
			+ "CASE\n"
			+ "	WHEN generador = 3 THEN 'Creado Masivamente'\n"
			+ "    WHEN generador = 1 THEN 'Orden de servicio'\n"
			+ "    WHEN generador = 2 THEN 'Cargo Manual'\n"
			+ "END as generador,\n"
			+ "deu.valor_total as valor, \n"
			+ "deu.valor_parcial as  abono \n"
			+ "\n"
			+ "FROM deudas deu where deu.id_contrato = :contrato" , nativeQuery = true)
	public List<Object[]> findByIdContrato(@Param("contrato") Long contrato);
}
