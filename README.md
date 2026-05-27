# 🏥 MedFlow — Hospital Management System API

A hospital backend REST API built with Java 17 and Spring Boot 3, demonstrating secure JWT authentication with refresh tokens, clean modular architecture, PostgreSQL with Flyway migrations — all following real-world backend development practices.

---

## 🚀 TL;DR — Why this project?

* 🔐 **JWT Security:** Stateless authentication featuring a secure Access & Refresh Token mechanism.
* 🧩 **Clean Layered Architecture:** Strict separation of concerns following the Controller ➔ Service ➔ Repository pattern.
* 📊 **Data Pagination & Filtering:** Optimized Pageable data loading and filtering to efficiently handle large datasets without overloading the database.
* 🗄 **Relational Database:** A robust PostgreSQL database schema consisting of 10+ interconnected, fully normalized entity tables.

---

## 📌 Project Overview

This REST API models a real-world hospital management system. The project places a strong emphasis on production-grade backend engineering, data security, and robust business logic execution:

* **Role-Permission Based Access Control:** Strict user authorization and permission restriction based on roles (Admin, Doctor, Reception).
* **Data Integrity:** Ensured data consistency and reliable data handling across the database using strict cascade deletion rules and entity relationships.
* **Clean Separation of Concerns:** Highly decoupled system functionalities cleanly separated into logical, modular packages.

---

## 🧰 Tech Stack

* ☕ **Java 17:** Modern LTS Java version widely used in enterprise backends.
* 🌱 **Spring Boot 3.x:** REST API framework (controllers, DI, validation, configuration profiles).
* 🔐 **Spring Security + JWT:** Stateless authentication, Access & Refresh token separation, Custom JWT filter and token service, Permission-based authorization with `@PreAuthorize("hasAuthority(...)")`.
* 🗄 **PostgreSQL + Spring Data JPA:** Relational database with strong entity relationships, JPA Auditing (`createdAt`, `updatedAt`, `createdBy`, `updatedBy`).
* 🛫 **Flyway:** Versioned SQL migrations for controlled schema evolution.

### ⚡ Performance & Optimization
* Pagination implemented for list endpoints.
* Optimized JPA queries and indexing applied to frequently queried fields.
* Interface Projections: Optimized database performance by fetching only required fields.
* Implemented a type-safe Generic Abstract CRUD Service to securely decouple database entities from request/response DTOs.

---

## 🏗 Architecture Design

The project follows a modular + layered architecture:

`Controller` ➔ `Service` *(Mapper, Validator)* ➔ `Repository` ➔ `Database`

### 🔹 Layers
* **Controller:** Request handling & validation.
* **Service:** Core business logic.
* **Mapper:** Data conversion between Entities and DTOs.
* **Validator:** Validates incoming data against custom business rules and throws specific domain exceptions.
* **Repository:** Data access layer.
* **DTO:** Request / Response models.

### 🔹 Cross-Cutting Concerns
* Security & JWT Authentication
* Aspect-Oriented Programming (AOP)
* Centralized Exception Handling
* JPA Auditing
* Application Configuration & Utilities
* Database Interface Projections

---

## 🔑 Key Features & Endpoints

### 🔐 Auth (`/api/v1/auth`)
* `POST /login` — Issue access & refresh tokens
* `POST /refresh` — Renew access token
* `POST /logout` — Invalidate refresh token

### 📦 Users & Core Modules
* CRUD operations (with generic interfaces)
* Dynamic filtering and search functionality

### 🛡 Security & Data Integrity
* JWT-based stateless authentication with Access & Refresh token separation.
* Fine-grained Role-Based Access Control (RBAC) with controller-level checks via `hasAuthority(...)`.
* Flyway-managed schema migrations (e.g., role-permission mapping and seed data via `V1__init.sql`).

---

## 🗂 Project Structure

```text
src/main/java/hospital/medflow
├── aop             # Aspect-Oriented Programming (LoggingAspect)
├── config          # Application configuration beans (JPA Auditing, OpenAPI/Swagger)
│   └── security    # Spring Security & JWT authentication filters/configs
├── controller      # REST Controllers handling incoming HTTP requests and endpoints
├── criteria        # Dynamic filtering and specification query models (BaseCriteria, DataList)
├── dto             # Data Transfer Objects organized by feature modules (Request/Response)
├── exception       # Centralized error handling and global exception handlers
├── mapper          # Entity-DTO conversion layers (MapStruct / Custom)
├── model           # Core domain layers
│   ├── entity      # Database entities mapping to PostgreSQL tables
│   └── enums       # Enumeration types for status and type controls
├── projection      # Spring Data JPA interface projections for optimized database reads
├── repository      # Data access layer extending JpaRepository for DB interactions
├── service         # Core business logic processing and transaction handling
├── utils           # Common helper classes, constants, and global path mappings (ApiPath, SecurityUtils)
└── validator       # Custom business validation logic executing before the service layer