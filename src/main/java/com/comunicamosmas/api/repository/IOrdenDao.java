package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.EnumOrdenInstalacion;
import com.comunicamosmas.api.domain.Orden;
import com.comunicamosmas.api.service.dto.OrdenForInstalacionFindByIdOrdenDTO;
import com.comunicamosmas.api.service.dto.OrdenInstalacionDTO; 
import java.util.List;
import javax.persistence.Tuple;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IOrdenDao extends CrudRepository<Orden, Long> {
    @Query(
        value = "SELECT o.id_orden as idOrden, o.id_contrato as idContrato , concat(c.apellido_paterno , ' ', c.nombre_primer , ' ', c.nombre_segundo) as nombreCliente,\n" +
        "	c.documento , \n" +
        "    e.nombre_comercial as nombreComercial,\n" +
        "    s.nombre ,  tt.nombre as tipoTecnologia\n" +
        " FROM ordenes o\n" +
        "inner join clientes c on c.id_cliente = o.id_cliente\n" +
        "inner join empresas e on e.id_empresa = o.id_empresa\n" +
        "inner join contratos co on co.id_contrato = o.id_contrato\n" +
        "inner join servicios s on s.id_servicio = o.id_servicio\n" +
        "inner join tarifas t on t.id_tarifa = co.id_tarifa_promo\n" +
        "inner join tipos_tecnologia tt on tt.id_tecnologia = t.id_tecnologia\n" +
        "WHERE tt.servicio = 1 AND o.tipo_orden = 1 AND o.estado IN (0,1,2,3) and o.anulada = 0 AND o.winmax=0 AND o.id_usuario_ejecuta > 0",
        nativeQuery = true
    )
    public List<OrdenInstalacionDTO> ordenInstalacion();

    @Query(
        value = "SELECT o.id_orden as idOrden , o.id_contrato as idContrato, concat(c.apellido_paterno , ' ', c.nombre_primer , ' ', c.nombre_segundo) as nombreCliente,\n" +
        "tt.nombre as tecnologia , t.codigo_mikrotik as codigoMikrotik, t.velocidad,\n" +
        "s.id_servicio as idServicio , e.id_empresa as idEmpresa , ld.departamento , lm.municipio , d.barrio, \n" +
        "concat(d.tipo,'/',d.a_tipo,' ',d.a_numero,' ',d.a_letra,' ',d.b_tipo,' ',d.b_numero,' ',d.b_letra,' ',d.numero) as direccion	\n" +
        "  FROM ordenes o\n" +
        "inner join clientes c on c.id_cliente = o.id_cliente\n" +
        "inner join empresas e on e.id_empresa = o.id_empresa\n" +
        "inner join contratos co on co.id_contrato = o.id_contrato\n" +
        "inner join servicios s on s.id_servicio = o.id_servicio\n" +
        "inner join tarifas t on t.id_tarifa = co.id_tarifa_promo\n" +
        "inner join tipos_tecnologia tt on tt.id_tecnologia = t.id_tecnologia\n" +
        "inner join direcciones d on d.id_direccion = o.id_direccion\n" +
        "inner join lista_municipios lm on lm.id_municipio = d.municipio\n" +
        "inner join lista_departamentos ld on ld.id_departamento = d.departamento\n" +
        "WHERE tt.servicio = 1 AND o.id_orden  = ?",
        nativeQuery = true
    )
    public OrdenForInstalacionFindByIdOrdenDTO ordenForInstalacionFindByIdOrden(Long id);

    @Query(
        value = "SELECT u.telefono FROM ordenes o INNER JOIN usuarios u ON u.id_usuario = o.id_usuario_ejecuta WHERE" + " o.id_orden = ?",
        nativeQuery = true
    )
    public String findTelefonoByIdOrden(Long idOrde);

    @Query(
        value = "SELECT o.id_orden as idOrden, o.id_contrato as idContrato , concat(c.apellido_paterno , ' ', c.nombre_primer , ' ', c.nombre_segundo) as nombreCliente,\n" +
        "	c.documento , \n" +
        "    e.nombre_comercial as nombreComercial,\n" +
        "    s.nombre ,  tt.nombre as tipoTecnologia\n" +
        " FROM ordenes o\n" +
        "inner join clientes c on c.id_cliente = o.id_cliente\n" +
        "inner join empresas e on e.id_empresa = o.id_empresa\n" +
        "inner join contratos co on co.id_contrato = o.id_contrato\n" +
        "inner join servicios s on s.id_servicio = o.id_servicio\n" +
        "inner join tarifas t on t.id_tarifa = co.id_tarifa_promo\n" +
        "inner join tipos_tecnologia tt on tt.id_tecnologia = t.id_tecnologia\n" +
        "WHERE tt.servicio = 1 AND o.tipo_orden = 1 AND o.estado IN (0,1,2,3) and o.anulada = 0 AND o.winmax=0 AND o.id_usuario_ejecuta > 0" +
        " AND o.fechaf_registra BETWEEN :valor1 AND :valor2",
        nativeQuery = true
    )
    public List<OrdenInstalacionDTO> getListFindBetwee(String valor1, String valor2);
}
