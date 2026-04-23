package com.geotracker.dto;

import jakarta.validation.constraints.*;

public class LocationRequest {
    @NotBlank(message = "deviceId es obligatorio")
    public String deviceId;

    @DecimalMin("-90.0") @DecimalMax("90.0")
    public double latitude;

    @DecimalMin("-180.0") @DecimalMax("180.0")
    public double longitude;

    public String metadata;
}
