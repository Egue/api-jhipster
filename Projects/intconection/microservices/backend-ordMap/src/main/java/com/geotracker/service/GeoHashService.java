package com.geotracker.service;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * Codificación GeoHash simplificada (precisión 8 chars).
 * En producción usar la librería geohash-java o ch.hsr.geohash.
 */
@ApplicationScoped
public class GeoHashService {

    private static final String BASE32 = "0123456789bcdefghjkmnpqrstuvwxyz";
    private static final int PRECISION = 8;

    public String encode(double lat, double lon) {
        boolean evenBit = true;
        int bit = 0, step = 0;
        double[] latRange = {-90, 90};
        double[] lonRange = {-180, 180};
        int idx = 0;
        StringBuilder hash = new StringBuilder();

        while (hash.length() < PRECISION) {
            double mid;
            if (evenBit) {
                mid = (lonRange[0] + lonRange[1]) / 2;
                if (lon >= mid) { idx = (idx << 1) | 1; lonRange[0] = mid; }
                else            { idx =  idx << 1;      lonRange[1] = mid; }
            } else {
                mid = (latRange[0] + latRange[1]) / 2;
                if (lat >= mid) { idx = (idx << 1) | 1; latRange[0] = mid; }
                else            { idx =  idx << 1;      latRange[1] = mid; }
            }
            evenBit = !evenBit;
            if (++bit == 5) {
                hash.append(BASE32.charAt(idx));
                bit = 0; idx = 0; step++;
            }
        }
        return hash.toString();
    }
}
