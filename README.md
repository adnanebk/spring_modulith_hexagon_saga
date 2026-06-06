# Spring Modulith Hexagonal Saga Demo

A demonstration of a modular monolith architecture using Spring Modulith, implementing the Saga pattern for distributed transaction management in an e-commerce order processing system.

## Overview

This project showcases a modern approach to building modular applications using Spring Modulith with hexagonal architecture (ports and adapters). It implements a Saga pattern to manage distributed transactions across multiple bounded contexts: Order, Stock, Payment, and Shipping.

## Front-end testing
For testing purposes, i have developed an Angular frontend project available at https://github.com/adnanebk/shopping-page-new-angular

## Architecture

### Modular Design

The application is organized into several modules following Spring Modulith conventions:

- **order**: Order management and orchestration
- **stock**: Inventory and product stock management
- **payment**: Payment processing and validation
- **shipping**: Order shipping and fulfillment
- **coupon**: Discount coupon management and validation
- **common**: Shared data structures, events, and enums
  an example of order module diagram

<img width="428" height="549" alt="diagram-5648590457054428919" src="https://github.com/user-attachments/assets/5d65615e-d0ab-4aa1-8ebe-4154a9c57811" />

### Hexagonal Architecture

Each module follows hexagonal architecture principles:
- **Domain**: Core business logic and entities
- **Application**: Use cases and application services
- **Ports**: Input and output interfaces (API contracts)
- **Infrastructure**: Adapters for external systems (databases, APIs, etc.)

### Saga Pattern

The system uses the Saga pattern to manage distributed transactions:
1. Order is placed (with optional coupon code)
2. Stock is verified and reserved
3. Payment is processed
4. Order is shipped
5. Coupon usage is saved (if coupon was applied)
6. Notifications are sent

Each step publishes events that trigger the next step in the saga, with compensating actions for failure scenarios.

## Tech Stack

- **Java**: 21
- **Spring Boot**: 3.5.9
- **Spring Modulith**: 1.4.1
- **Spring Data JPA**: For database operations
- **H2 Database**: In-memory database for development
- **Maven**: Build and dependency management

## Features

- Modular monolith architecture with clear bounded contexts
- Event-driven communication between modules
- Saga pattern for distributed transaction management
- Hexagonal architecture with ports and adapters
- REST API for order placement
- Discount coupon system with validation rules (minimum amount, once per user, expiration)
- Automatic event publication and consumption
- Transactional data consistency within modules

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

### Running the Application

```bash
# Using Maven wrapper
./mvnw spring-boot:run

# Or using Maven directly
mvn spring-boot:run
```

The application will start on the default port (8080).

### Building the Project

```bash
# Using Maven wrapper
./mvnw clean install

# Or using Maven directly
mvn clean install
```

## Project Structure

```
src/main/java/com/example/demo/
├── common/                 # Shared components
│   ├── data/              # Data transfer objects
│   ├── enums/             # Common enumerations
│   └── events/            # Domain events
├── order/                 # Order module
│   ├── application/       # Order use cases
│   ├── domain/           # Order entities and value objects
│   ├── infra/            # Infrastructure adapters
│   │   ├── adapters/     # Controllers, listeners, mappers
│   │   ├── dto/          # Data transfer objects
│   │   └── entities/     # JPA entities
│   └── ports/            # Input/Output ports
├── stock/                # Stock module
│   ├── application/
│   ├── domain/
│   ├── infra/
│   └── ports/
├── payment/              # Payment module
│   ├── application/
│   ├── domain/
│   ├── infra/
│   └── ports/
├── shipping/             # Shipping module
│   ├── application/
│   ├── domain/
│   ├── infra/
│   └── ports/
└── coupon/               # Coupon module
    ├── application/      # Coupon use cases
    ├── domain/          # Coupon entities and value objects
    ├── infra/           # Infrastructure adapters
    │   ├── adapters/    # Repository adapters, mappers
    │   └── entities/    # JPA entities
    └── ports/           # Input/Output ports
```

## API Endpoints

### Place Order

**POST** `/orders`

Request body:
```json
{
  "userId": 1,
  "paymentToken": "token123",
  "couponCode": "SAVE10",
  "items": [
    {
      "productId": 1,
      "quantity": 2
    }
  ]
}
```

Response:
```json
123
```

Returns the order ID.

## Coupon Module

The coupon module provides discount functionality with flexible validation rules:

### Coupon Types

- **Fixed Discount**: Subtracts a fixed amount from the total
- **Percentage Discount**: Subtracts a percentage from the total

### Validation Rules

- **MINIMUM_AMOUNT**: Ensures the order total meets a minimum threshold
- **ONCE_PER_USER**: Restricts coupon usage to once per user
- **EXPIRATION**: Validates coupon is within valid date range

### Coupon Flow

1. Customer provides coupon code during order placement
2. System validates coupon code and applies validation rules
3. Discount is calculated and applied to order total
4. Upon successful order completion, coupon usage is recorded

## Event Flow

The order processing follows this event flow:

1. **OrderPlacedEvent**: Published when an order is created
2. **OrderProductStockVerifiedEvent**: Published when stock is verified
3. **OrderStockFailedEvent**: Published if stock verification fails
4. **OrderPayedEvent**: Published when payment succeeds
5. **OrderPaymentFailedEvent**: Published when payment fails
6. **OrderShippedEvent**: Published when order is shipped
7. **OrderCanceledEvent**: Published when order is canceled
8. **NotificationEvent**: Published for user notifications

## Development

### Running Tests

```bash
./mvnw test
```

### Database

The application uses H2 in-memory database. The schema is automatically created by Spring Data JPA.

## License

This project is a demonstration project for educational purposes.
