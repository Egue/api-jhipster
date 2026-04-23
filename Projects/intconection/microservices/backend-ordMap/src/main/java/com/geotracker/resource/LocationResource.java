package com.geotracker.resource;

import com.geotracker.dto.LocationRequest;
import com.geotracker.dto.LocationResponse;
import com.geotracker.service.LocationService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/locations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Locations", description = "API de geolocalización y historial")
public class LocationResource {

    @Inject LocationService locationService;

    @POST
    @Operation(summary = "Registrar nueva posición vía REST")
    public Response registerLocation(@Valid LocationRequest request) {
        LocationResponse resp = locationService.processRestEvent(request);
        return Response.status(Response.Status.CREATED).entity(resp).build();
    }

    @GET
    @Path("/{deviceId}/history")
    @Operation(summary = "Historial completo de posiciones (MongoDB)")
    public List<LocationResponse> getHistory(@PathParam("deviceId") String deviceId) {
        return locationService.getHistory(deviceId);
    }

    @GET
    @Path("/{deviceId}/last")
    @Operation(summary = "Última posición activa del dispositivo (Redis)")
    public LocationResponse getLastLocation(@PathParam("deviceId") String deviceId) {
        return locationService.getLastLocation(deviceId);
    }
}
