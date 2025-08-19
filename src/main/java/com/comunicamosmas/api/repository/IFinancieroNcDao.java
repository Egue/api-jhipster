package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.FinancieroNc;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IFinancieroNcDao extends CrudRepository<FinancieroNc, Long> {

    public List<FinancieroNc> findByIdDeuda(Long idDeuda);

    @Query(value = """
            SELECT 
fn.valor_base,
fn.valor_iva,
fn.marca,
fn.numero_nc,
fn.id_nc,
concat(u.nombre , ' ', u.apellidos) usur,
ja.texto,
fn.comentario
 FROM financiero_nc fn
 inner join usuarios u on u.id_usuario = fn.id_admin
 inner join justifica_anulaciones ja on ja.id_justifica = fn.id_justificacion
 where fn.id_nc = :idNc
            """, nativeQuery = true)
    public List<Object[]> findInfoNc(@Param("idNc") Long idNc);
}
