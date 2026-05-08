package com.geotracker.service;

import com.geotracker.dto.LocationRequest;
import com.geotracker.dto.LocationResponse;
import com.geotracker.model.LocationEvent;
import com.geotracker.repository.GeoRedisRepository;
import com.geotracker.repository.LocationEventRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class LocationService {

    private static final Logger LOG = Logger.getLogger(LocationService.class);

    @Inject LocationEventRepository eventRepo;
    @Inject GeoRedisRepository geoRedis;
    @Inject GeoHashService geoHashService;

    /** Procesa un evento recibido por REST */
    public LocationResponse processRestEvent(LocationRequest req) {
        String geoHash = geoHashService.encode(req.latitude, req.longitude);

        LocationEvent event = LocationEvent.ofRest(req.deviceId, req.latitude, req.longitude);
        event.geoHash = geoHash;
        eventRepo.persist(event);

        geoRedis.saveLocation(req.deviceId, req.latitude, req.longitude);
        geoRedis.saveGeoHash(req.deviceId, geoHash);

        LOG.infof("REST event saved — device=%s geoHash=%s", req.deviceId, geoHash);
        return toResponse(event);
    }

    /** Procesa un evento recibido por Kafka */
    public void processKafkaEvent(String deviceId, double lat, double lon, String raw) {
        String geoHash = geoHashService.encode(lat, lon);

        LocationEvent event = LocationEvent.ofKafka(deviceId, lat, lon, raw);
        event.geoHash = geoHash;
        eventRepo.persist(event);

        geoRedis.saveLocation(deviceId, lat, lon);
        geoRedis.saveGeoHash(deviceId, geoHash);

        LOG.infof("Kafka event saved — device=%s geoHash=%s", deviceId, geoHash);
    }

    /** Historial de un device (MongoDB) */
    public List<LocationResponse> getHistory(String deviceId) {
        return eventRepo.findByDeviceId(deviceId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /** Última posición conocida (Redis) */
    public LocationResponse getLastLocation(String deviceId) {
        return geoRedis.getLocation(deviceId)
                .map(coords -> {
                    String geoHash = geoRedis.getGeoHash(deviceId).orElse("N/A");
                    return new LocationResponse(null, deviceId, coords[0], coords[1],
                            geoHash, null, "redis-cache", "live");
                })
                .orElseThrow(() -> new jakarta.ws.rs.NotFoundException(
                        "No hay posición activa para deviceId=" + deviceId));
    }

    private LocationResponse toResponse(LocationEvent e) {
        return new LocationResponse(
                e.id != null ? e.id.toString() : null,
                e.deviceId, e.latitude, e.longitude,
                e.geoHash, e.timestamp, e.source, e.status
        );
    }
}
