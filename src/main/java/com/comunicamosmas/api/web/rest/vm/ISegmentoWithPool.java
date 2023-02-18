package com.comunicamosmas.api.web.rest.vm;

public interface ISegmentoWithPool {
    /**SELECT 
ms.id_segmento_ip as id, ms.id_estacion as idEstacion , ms.segmento , 
ms.estado  , mikrotik_pool.nombre_pool as nombrePool FROM mikrotik_segmento_ip ms 
inner join mikrotik_pool on mikrotik_pool.id_pool = ms.id_pool
where mikrotik_pool.id_pool = 1*/
    public Long getId();

    public Long idEstacion();

    public String segmento();

    public String estado();

    public String NombrePool();
}
