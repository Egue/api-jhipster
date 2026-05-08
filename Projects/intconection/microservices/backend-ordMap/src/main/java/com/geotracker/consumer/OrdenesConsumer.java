package com.geotracker.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geotracker.dto.events.OrdenEvent;
import com.geotracker.service.OrderService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class OrdenesConsumer {

    @Inject
    OrderService orderService;

    @Inject
    ObjectMapper objectMapper;

    @Incoming("ordenes")
    public void procesarOrdenes(String event) throws Exception{
         OrdenEvent order = objectMapper.readValue(event , OrdenEvent.class);
         switch (order.event){
             case "orden.created" -> orderService.createOrderEvent(order.data);
             case "orden.updated" -> orderService.updatedOrderEvent(order.data);
             default -> eventNotFound();
         }


    }

    private void eventNotFound(){
        System.out.println("event no foound");
    }


}
