package com.geotracker.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.time.Instant;

@MongoEntity(collection = "location_events")
public class LocationEvent extends PanacheMongoEntity {
    public String deviceId;
    public double latitude;
    public double longitude;
    public String geoHash;
    public String source;      // "kafka" | "rest"
    public Instant timestamp;
    public String status;      // "received" | "processed"
    public String rawPayload;

    public static LocationEvent ofKafka(String deviceId, double lat, double lon, String raw) {
        LocationEvent e = new LocationEvent();
        e.deviceId = deviceId; e.latitude = lat; e.longitude = lon;
        e.source = "kafka"; e.timestamp = Instant.now();
        e.status = "received"; e.rawPayload = raw;
        return e;
    }

    public static LocationEvent ofRest(String deviceId, double lat, double lon) {
        LocationEvent e = new LocationEvent();
        e.deviceId = deviceId; e.latitude = lat; e.longitude = lon;
        e.source = "rest"; e.timestamp = Instant.now(); e.status = "received";
        return e;
    }
}
