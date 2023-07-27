package com.comunicamosmas.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.comunicamosmas.api.domain.HanRetiros;

public interface IHanRetirosDao extends CrudRepository< HanRetiros ,Long>{

    @Query(value="SELECT han_retiros.* , concat(usuarios.nombre, ' ',usuarios.apellidos) as nameuser, \n"
    + "CASE WHEN clientes.tipo_cliente ='N' THEN concat(clientes.nombre_primer , ' ', clientes.apellido_paterno , ' / ', clientes.documento)  \n" 
    + "WHEN clientes.tipo_cliente ='J' THEN concat(clientes.razon_social , ' / ', clientes.documento) \n"
    + "END as cliente FROM han_retiros \n"
    +"inner join usuarios on usuarios.id_usuario = han_retiros.id_user \n"
    +"inner join contratos on contratos.id_contrato = han_retiros.id_contrato \n"
    + "inner join clientes on clientes.id_cliente = contratos.id_cliente \n" 
    +"WHERE han_retiros.id_contrato = :idContrato" , nativeQuery = true)
    public Optional<List<Object[]>> findByIdContrato(@Param("idContrato") Long idContrato);

    @Query(value="SELECT re.id, re.descripcion, re.id_user , re.created_at, re.url_documento , re.id_padre, \n"
                + " concat(usuarios.nombre, ' ' , usuarios.apellidos) as username , usuarios.avatar FROM han_retiros re \n"
                + " INNER JOIN usuarios on usuarios.id_usuario  = re.id_user \n"
                + " WHERE re.id_padre = :idPadre ORDER BY re.id ASC", nativeQuery = true)
    public Optional<List<Object[]>> findMessageByidPadre(@Param("idPadre") Integer idPadre);

    @Query(value="SELECT MONTH(created_at) mes,count(*)  as cantidad from han_retiros \n" + //
            "WHERE id_padre is null AND YEAR(created_at) = :ano  AND id_servicio IN (:servicios) group by mes\n" + //
            "ORDER BY mes ASC" , nativeQuery = true)
    public Optional<List<Object[]>> chartLine(@Param("ano") Integer ano , @Param("servicios") List<Integer> servicios);

    @Query(value="SELECT han_retiros.id, han_retiros.id_contrato, \n" + //
            "servicios.nombre as servicio,\n" + //
            "CASE \n" + //
            " WHEN clientes.tipo_cliente = \"J\" THEN clientes.razon_social\n" + //
            " WHEN clientes.tipo_cliente = \"N\" THEN concat(clientes.apellido_paterno,\" \", clientes.nombre_primer)\n" + //
            " END as cliente,\n" + //
            "  clientes.documento,\n" + //
            " direcciones.barrio, \n" + //
            " concat(direcciones.tipo, \" \", direcciones.a_tipo , \" \",direcciones.a_numero , \" \", direcciones.a_letra , \" \", direcciones.b_tipo , \" \", direcciones.b_numero , \" \", direcciones.b_letra , \" \", direcciones.numero) as direccion\n" + //
            " FROM han_retiros \n" + //
            "inner join contratos on contratos.id_contrato = han_retiros.id_contrato\n" + //
            "inner join direcciones on direcciones.id_direccion = contratos.id_direccion_servicio\n" + //
            "inner join clientes on clientes.id_cliente = contratos.id_cliente\n" + //
            "inner join servicios on servicios.id_servicio = han_retiros.id_servicio \n" + //
            "WHERE han_retiros.id_servicio IN (:servicios) AND han_retiros.estado = :estado \n" + //
            "AND han_retiros.id_padre is null" , nativeQuery = true)
    public Optional<List<Object[]>> reporteRetiros(@Param("servicios") List<Integer> servicios , @Param("estado") String estado);
    
}
