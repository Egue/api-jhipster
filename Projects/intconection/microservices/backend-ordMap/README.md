# geo-tracker — Quarkus Microservice

Microservicio de geolocalización con:
- **Quarkus 3.11** (Java 21)
- **Kafka** — consume eventos de posición y re-emite procesados
- **MongoDB (Panache)** — historial de eventos
- **Redis** — posición en tiempo real + GeoHash cache
- **REST API** — registro manual + consulta de historial/posición

## Estructura

```
src/main/java/com/geotracker/
├── model/
│   └── LocationEvent.java          # Entidad MongoDB
├── dto/
│   ├── LocationRequest.java        # Payload REST entrada
│   └── LocationResponse.java       # Respuesta REST
├── repository/
│   ├── LocationEventRepository.java # Panache Mongo
│   └── GeoRedisRepository.java     # Redis lat/lon + geoHash
├── service/
│   ├── LocationService.java        # Orquestador principal
│   └── GeoHashService.java         # Encoder GeoHash
├── consumer/
│   └── LocationEventConsumer.java  # @Incoming Kafka
├── producer/
│   └── LocationEventProducer.java  # @Outgoing Kafka
└── resource/
    ├── LocationResource.java       # REST endpoints
    └── GeoHealthCheck.java         # @Readiness probe
```

## Endpoints REST

| Método | Ruta                                 | Descripción                  |
|--------|--------------------------------------|------------------------------|
| POST   | /api/locations                       | Registrar posición           |
| GET    | /api/locations/{deviceId}/history    | Historial MongoDB            |
| GET    | /api/locations/{deviceId}/last       | Última posición Redis        |
| GET    | /q/health/ready                      | Health check                 |
| GET    | /q/metrics                           | Métricas Prometheus          |
| GET    | /swagger-ui                          | Swagger UI                   |

## Kafka

**Consumer** — topic: `location-events`
```json
{"deviceId": "device-001", "lat": 4.5709, "lon": -74.2973}
```

**Producer** — topic: `location-events-processed`
```json
{"deviceId": "device-001", "lat": 4.5709, "lon": -74.2973, "processed": true}
```

## Arranque rápido

```bash
# Infraestructura
docker-compose -f docker/docker-compose.yml up -d

# Dev mode (dev services automáticos si no hay infra externa)
./mvnw quarkus:dev

# Build nativo
./mvnw package -Pnative
```

## Variables de entorno (producción)

```bash
QUARKUS_MONGODB_CONNECTION_STRING=mongodb://user:pass@host:27017/geotracker
QUARKUS_REDIS_HOSTS=redis://host:6379
KAFKA_BOOTSTRAP_SERVERS=broker:9092
```
