package com.geotracker.resource;

import com.geotracker.repository.GeoRedisRepository;
import com.geotracker.repository.LocationEventRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class GeoHealthCheck implements HealthCheck {

    @Inject LocationEventRepository eventRepo;

    @Override
    public HealthCheckResponse call() {
        try {
            long count = eventRepo.count();
            return HealthCheckResponse.named("GeoTracker")
                    .up()
                    .withData("mongodb-events", count)
                    .build();
        } catch (Exception e) {
            return HealthCheckResponse.named("GeoTracker")
                    .down()
                    .withData("error", e.getMessage())
                    .build();
        }
    }
}
