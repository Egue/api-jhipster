package com.geotracker.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "orders")
public class Orders extends PanacheMongoEntity {

    public String id_orden;
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
    public String fechaf_asigna;
    public String fechaf_asiste;
    public String fechaf_anula;
    public String fechaf_descarga;
    public String hora_asiste_inicio;
    public String hota_asiste_fin;
    public String estado;
    public String nota;
    public String id_ciudad;
    public String id_empresa;
    public String id_servicio;
    public String id_usuario_registra;
    public String id_usuario_asigna;
    public String id_usuario_ejecuta;
    public String id_usuario_descarga;
    public String id_usuario_anula;
    public String anula_justifica;
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;
    public String ultima_dow;
    public String abierta;
    public String anulada;
    public String nota_final;
    public String winmax;
    public String winmax_id_usuario;
    public String winmax_marca;
    public String id_ticket_soporte;
    public String pdf_descarga_fecha;
    public String pdf_descarga_usuario;
    public String id_tecnologia;
    public String tipo_reconecta;
    public String api_automatica;
    public String log_api;
}
