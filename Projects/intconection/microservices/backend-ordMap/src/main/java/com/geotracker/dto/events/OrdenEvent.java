package com.geotracker.dto.events;

public class OrdenEvent {
    public String event;
    public String timestamp;
    public OrdenEventDTO data;

    public static class OrdenEventDTO
    {
    public String id_orden ;
    public String tipo_orden;
    public String refiere;
    public String causa_solicitud;
    public String numero_a;
    public String numero_b;
    public String id_contrato;
    public String id_direccion;
    public String id_cliente;
    public String id_estacion;
    public String id_zona;
    public String fechaf_registra;
    public String fechaf_solicita;
    public String estado;
    public String nota;
    public String id_ciudad;
    public String id_empresa;
    public String id_servicio;
    public String id_usuario_registra;
    public String id_tecnologia;
    public String id_usuario_ejecuta;
    public String anulada;
    public String id_usuario_anulada;
    public String fechaf_anula;
    public String fechaf_asigna;
}
}

