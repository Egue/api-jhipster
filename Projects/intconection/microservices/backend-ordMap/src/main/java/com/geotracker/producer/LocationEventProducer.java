package com.geotracker.producer;

import io.smallrye.reactive.messaging.MutinyEmitter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.logging.Logger;

@ApplicationScoped
public class LocationEventProducer {

    private static final Logger LOG = Logger.getLogger(LocationEventProducer.class);

    @Inject
    @Channel("location-events-out")
    MutinyEmitter<String> emitter;

    public void send(String payload) {
        emitter.sendAndForget(payload);
        LOG.debugf("Evento enviado a Kafka: %s", payload);
    }
}
