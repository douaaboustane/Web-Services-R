Variante C — Spring Boot MVC + JPA + PostgreSQL + Prometheus

Cette variante implémente un service web REST basé sur Spring Boot 3, Spring Web, Spring Data JPA et PostgreSQL.
Elle expose des endpoints CRUD pour les entités Item et Category et inclut un système complet de monitoring via Prometheus et Grafana pour le benchmark de performance.

 Environnement technique
Composant	Technologie utilisée
Langage	Java 17
Framework Web	Spring Boot MVC
ORM	Spring Data JPA + Hibernate
Base de données	PostgreSQL 15
Pool de connexions	HikariCP
Monitoring	Actuator + Micrometer + Prometheus + Grafana
Build Tool	Maven 3.9+
Conteneurisation	Docker Compose

 Arborescence du projet
VarianteC/
├─ src/
│  ├─ main/java/com/bench/variantec/
│  │  ├─ controllers/        → RestControllers (Items, Categories)
│  │  ├─ dto/                → DTO pour sérialisation
│  │  ├─ repo/               → Repositories Spring Data
│  │  ├─ service/            → Logique métier
│  │  └─ VarianteCApplication.java
│  └─ main/resources/
│     └─ application.properties
├─ docker/
│  ├─ init/                  → 01-schema.sql / 02-seed.sql
│  ├─ monitoring/            → Prometheus / Grafana / Postgres Exporter
│  └─ docker-compose.yml
├─ pom.xml
└─ README.md

 Configuration de la base de données
Lancer PostgreSQL en local (Docker)
cd docker
docker compose up -d


Les fichiers 01-schema.sql et 02-seed.sql créent automatiquement :

une base bench_rest

des tables category et item

des données de test (categories, items)
