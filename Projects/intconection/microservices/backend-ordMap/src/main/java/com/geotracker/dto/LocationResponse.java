package com.geotracker.dto;

import java.time.Instant;

public class LocationResponse {
    public String id;
    public String deviceId;
    public double latitude;
    public double longitude;
    public String geoHash;
    public Instant timestamp;
    public String source;
    public String status;

    public LocationResponse() {}

    public LocationResponse(String id, String deviceId, double lat, double lon,
                            String geoHash, Instant ts, String source, String status) {
        this.id = id; this.deviceId = deviceId;
        this.latitude = lat; this.longitude = lon;
        this.geoHash = geoHash; this.timestamp = ts;
        this.source = source; this.status = status;
    }
}
