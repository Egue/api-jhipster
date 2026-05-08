package com.geotracker.repository;

import com.geotracker.dto.events.OrdenEvent;
import com.geotracker.model.Orders;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class OrdersRepository implements PanacheMongoRepository<Orders> {

    /*public Optional<Orders> findByCodigo(String ordenId){
        return find("id_orden" , ordenId).firstResultOptional();
    }*/

    public void updatedOrder(OrdenEvent.OrdenEventDTO dto){
        update("tipo_orden = ?1 and id_zona = ?2 and  nota = ?4 " +
            "and id_usuario_registra = ?5 and id_usuario_ejecuta = ?6 and anulada = ?7 and id_usuario_anulada = ?8 and fechaf_anula = ?9 " +
            " and fechaf_asigna = ?10 and id_contrato = ?11 ", dto.tipo_orden , dto.id_zona , dto.nota , dto.id_usuario_registra , dto.id_usuario_ejecuta , dto.anulada , dto.id_usuario_anulada ,
            dto.fechaf_anula , dto.fechaf_asigna , dto.id_contrato)
            .where("id_orden = ?12" , dto.id_orden);
    }

}
