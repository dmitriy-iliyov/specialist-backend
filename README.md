# specialist-backend
![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-6DB33F?logo=springboot)
![Hibernate](https://img.shields.io/badge/Hibernate-8.0.1.Final-59666C?logo=hibernate)
![Maven](https://img.shields.io/badge/Maven-3+-C71A36?logo=apachemaven)

![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16+-336791?logo=postgresql)
![Redis](https://img.shields.io/badge/Redis-7+-DC382D?logo=redis)
![Lua](https://img.shields.io/badge/Lua-Redis%20Scripts-2C2D72?logo=lua)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker)

![Prometheus](https://img.shields.io/badge/Prometheus-Metrics-E6522C?logo=prometheus)
![Grafana](https://img.shields.io/badge/Grafana-Dashboards-F46800?logo=grafana)

## Tech Stack

### Runtime & Frameworks
- Java 21
- Spring Boot 3.5.6
- Hibernate 8.0.1.Final
- Maven

### Data & Messaging
- PostgreSQL
- Redis (Lua scripts for atomic operations)

### Infrastructure
- Docker Compose

### Observability
- Prometheus
- Grafana

## Monitoring & Metrics

The system is instrumented with Micrometer and exposes metrics via Spring Boot Actuator.

### Prometheus
- Scrapes metrics from producer and consumer services
- Collects:
  - application throughput
  - processing latency
  - error rates
  - outbox backlog size

### Grafana
- Visualizes:
  - produced vs consumed events
  - outbox table growth
  - consumer lag
  - retry / failure rates

Preconfigured dashboards can be connected to the Prometheus data source.

## Running Locally

All services (applications, PostgreSQL, Redis, Kafka, Prometheus, Grafana)
are started via Docker Compose.

```bash
docker compose up -d
```