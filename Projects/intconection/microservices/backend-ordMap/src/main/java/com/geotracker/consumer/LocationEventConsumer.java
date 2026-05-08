package com.geotracker.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geotracker.producer.LocationEventProducer;
import com.geotracker.service.LocationService;
import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class LocationEventConsumer {
/*
    private static final Logger LOG = Logger.getLogger(LocationEventConsumer.class);

    @Inject LocationService locationService;
    @Inject LocationEventProducer producer;
    @Inject ObjectMapper mapper;

    /**
     * Consume mensajes del topic 'location-events'.
     * Payload esperado: {"deviceId":"abc","lat":4.5,"lon":-74.1}
     */
    /*@Incoming("location-events-in")
    @Blocking  // Operación bloqueante (MongoDB + Redis)
    public void consume(String payload) {
        try {
            JsonNode node = mapper.readTree(payload);
            String deviceId = node.get("deviceId").asText();
            double lat = node.get("lat").asDouble();
            double lon = node.get("lon").asDouble();

            locationService.processKafkaEvent(deviceId, lat, lon, payload);

            // Re-emite evento enriquecido hacia downstream
            producer.send(buildEnrichedPayload(deviceId, lat, lon));

        } catch (Exception ex) {
            LOG.errorf(ex, "Error procesando mensaje Kafka: %s", payload);
        }
    }


    private String buildEnrichedPayload(String deviceId, double lat, double lon) {
        return String.format("{\"deviceId\":\"%s\",\"lat\":%s,\"lon\":%s,\"processed\":true}",
                deviceId, lat, lon);
    }
    */
}
