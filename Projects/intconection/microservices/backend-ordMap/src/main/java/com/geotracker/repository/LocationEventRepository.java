package com.geotracker.repository;

import com.geotracker.model.LocationEvent;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class LocationEventRepository implements PanacheMongoRepository<LocationEvent> {

    public List<LocationEvent> findByDeviceId(String deviceId) {
        return list("deviceId", deviceId);
    }

    public List<LocationEvent> findByDeviceIdSorted(String deviceId, int limit) {
        return find("deviceId = ?1", deviceId)
                .page(0, limit)
                .list();
    }

    public long countBySource(String source) {
        return count("source", source);
    }
}
