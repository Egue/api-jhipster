package com.geotracker.repository;

import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.Optional;

@ApplicationScoped
public class GeoRedisRepository {

    private final ValueCommands<String, String> valueCmd;

    public GeoRedisRepository(RedisDataSource ds) {
        this.valueCmd = ds.value(String.class, String.class);
    }

    private static String key(String deviceId) {
        return "geo:device:" + deviceId;
    }

    /** Guarda lat,lon como "lat,lon" con TTL de 1 hora */
    public void saveLocation(String deviceId, double lat, double lon) {
        valueCmd.setex(key(deviceId), 3600, lat + "," + lon);
    }

    /** Retorna la última posición conocida del device */
    public Optional<double[]> getLocation(String deviceId) {
        String val = valueCmd.get(key(deviceId));
        if (val == null) return Optional.empty();
        String[] parts = val.split(",");
        return Optional.of(new double[]{Double.parseDouble(parts[0]), Double.parseDouble(parts[1])});
    }

    /** Guarda el geoHash en Redis para lookup rápido */
    public void saveGeoHash(String deviceId, String geoHash) {
        valueCmd.setex("geo:hash:" + deviceId, 3600, geoHash);
    }

    public Optional<String> getGeoHash(String deviceId) {
        return Optional.ofNullable(valueCmd.get("geo:hash:" + deviceId));
    }
}
