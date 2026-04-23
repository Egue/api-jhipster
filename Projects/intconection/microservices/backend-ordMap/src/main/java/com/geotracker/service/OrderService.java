package com.geotracker.service;

import com.geotracker.dto.events.OrdenEvent;
import com.geotracker.model.Orders;
import com.geotracker.repository.OrdersRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class OrderService {
    @Inject
    OrdersRepository ordersRepository;


    public void createOrderEvent(OrdenEvent.OrdenEventDTO dto){
        Orders or = new Orders();
        or.id_orden = dto.id_orden;
        or.tipo_orden = dto.tipo_orden;
        or.refiere = dto.tipo_orden;
        or.causa_solicitud = dto.causa_solicitud;
        or.numero_a = dto.numero_a;
        or.numero_b = dto.numero_b;
        or.id_contrato = dto.id_contrato;
        or.id_direccion = dto.id_direccion;
        or.id_cliente = dto.id_cliente;
        or.id_estacion = dto.id_estacion;
        or.id_zona = dto.id_zona;
        or.fechaf_registra = dto.fechaf_registra;
        or.fechaf_solicita = dto.fechaf_solicita;
        or.estado = dto.estado;
        or.nota = dto.nota;
        or.id_ciudad = dto.id_ciudad;
        or.id_empresa = dto.id_empresa;
        or.id_servicio = dto.id_servicio;
        or.id_usuario_registra = dto.id_usuario_registra;
        or.id_tecnologia = dto.id_tecnologia;

        ordersRepository.persist(or);
    }

    public void updatedOrderEvent(OrdenEvent.OrdenEventDTO dto){
        ordersRepository.updatedOrder(dto);
    }
}
