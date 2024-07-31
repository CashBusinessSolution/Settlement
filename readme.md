# Project Documentation

## Overall Structure

The project is a microservices-based architecture using Spring Boot and Maven. It consists of multiple microservices, each responsible for a specific functionality. The main components are:

1. **Discovery Service**: Manages service registration and discovery using Eureka.
2. **Gateway Service**: Acts as an API gateway, routing requests to the appropriate microservices.
3. **Admin Dashboard**: Provides an interface for administrative tasks.
4. **Config Server**: Manages external configurations for the microservices.
5. **Merchant Onboarding Service**: Handles the onboarding process for merchants.
6. **Merchant Servicing Service**: Manages services related to merchant operations.
7. **TMS Service**: Handles transaction management services.

### Microservices Overview

#### 1. Discovery Service

**Role**:
- Manages service registration and discovery.
- Ensures that all microservices can find and communicate with each other.

**Dependencies**:
- `spring-boot-starter-web`: For building web applications.
- `spring-cloud-starter-netflix-eureka-server`: For service discovery.
- `spring-boot-starter-data-redis`: For Redis integration.
- `spring-boot-starter-cache`: For caching support.

**Why These Dependencies**:
- **Spring Boot**: Simplifies the development of production-ready applications.
- **Eureka**: Provides a robust service discovery mechanism.
- **Redis**: Enhances performance by caching frequently accessed data.

#### 2. Gateway Service

**Role**:
- Acts as a single entry point for all client requests.
- Routes requests to the appropriate microservices based on predefined routes.

**Dependencies**:
- `spring-boot-starter-webflux`: For building reactive web applications.
- `spring-cloud-starter-gateway`: For routing and filtering requests.
- `spring-cloud-starter-netflix-eureka-client`: For service discovery.
- `spring-boot-starter-data-redis`: For Redis integration.
- `spring-boot-starter-cache`: For caching support.

**Why These Dependencies**:
- **Spring Boot**: Simplifies the development of production-ready applications.
- **Spring Cloud Gateway**: Provides a flexible and powerful way to route and filter requests.
- **Redis**: Enhances performance by caching frequently accessed data.

#### 3. Admin Dashboard

**Role**:
- Provides an interface for administrative tasks.

**Dependencies**:
- `spring-boot-starter-web`: For building web applications.
- `spring-boot-starter-thymeleaf`: For server-side rendering of HTML.

**Why These Dependencies**:
- **Spring Boot**: Simplifies the development of production-ready applications.
- **Thymeleaf**: Provides a modern server-side Java template engine for web applications.

#### 4. Config Server

**Role**:
- Manages external configurations for the microservices.

**Dependencies**:
- `spring-cloud-config-server`: For serving configuration properties to client applications.

**Why These Dependencies**:
- **Spring Cloud Config**: Provides server and client-side support for externalized configuration in a distributed system.

#### 5. Merchant Onboarding Service

**Role**:
- Handles the onboarding process for merchants.

**Dependencies**:
- `spring-boot-starter-web`: For building web applications.
- `spring-boot-starter-data-jpa`: For data persistence.
- `spring-cloud-starter-netflix-eureka-client`: For service discovery.

**Why These Dependencies**:
- **Spring Boot**: Simplifies the development of production-ready applications.
- **Spring Data JPA**: Provides easy integration with relational databases.
- **Eureka**: Provides a robust service discovery mechanism.

#### 6. Merchant Servicing Service

**Role**:
- Manages services related to merchant operations.

**Dependencies**:
- `spring-boot-starter-web`: For building web applications.
- `spring-boot-starter-data-jpa`: For data persistence.
- `spring-cloud-starter-netflix-eureka-client`: For service discovery.

**Why These Dependencies**:
- **Spring Boot**: Simplifies the development of production-ready applications.
- **Spring Data JPA**: Provides easy integration with relational databases.
- **Eureka**: Provides a robust service discovery mechanism.

#### 7. TMS Service

**Role**:
- Handles transaction management services.

**Dependencies**:
- `spring-boot-starter-web`: For building web applications.
- `spring-boot-starter-data-jpa`: For data persistence.
- `spring-cloud-starter-netflix-eureka-client`: For service discovery.

**Why These Dependencies**:
- **Spring Boot**: Simplifies the development of production-ready applications.
- **Spring Data JPA**: Provides easy integration with relational databases.
- **Eureka**: Provides a robust service discovery mechanism."# Settlement" 
