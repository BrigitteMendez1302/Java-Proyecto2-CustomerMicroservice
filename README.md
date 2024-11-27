
# Customer Microservice

A microservice for managing customer data in a banking system. This microservice is responsible for handling operations such as creating, retrieving, updating, and deleting customer records.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Configuration](#configuration)
- [API Documentation](#api-documentation)
- [Project Structure](#project-structure)
- [Coverage Report](#coverage-report)
- [License](#license)

---

## Features
- Create new customer records with unique identifiers (DNI).
- Retrieve all customers or a specific customer by ID.
- Update customer information.
- Delete customers with validations (e.g., no active accounts).
- Validation for customer attributes like unique DNI and valid email format.

---

## Technologies Used
- **Java 11**
- **Spring Boot** (Web, Data JPA, Validation)
- **MySQL** (Relational Database)
- **Hibernate** (ORM for database interactions)
- **OpenAPI 3.0** (API Documentation)
- **Maven** (Dependency management)

---

## Installation

### Prerequisites
1. Install **Java 11** or higher.
2. Install **Maven**.
3. Set up **MySQL** (local or cloud).

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/BrigitteMendez1302/Java-Proyecto2-CustomerMicroservice.git
   cd customer-microservice
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Configure the application properties (see [Configuration](#configuration)).

---

## Configuration

Modify the `application.properties` file to match your environment:

```properties
# Spring Application Configuration
spring.application.name=customer

# Server Configuration
server.port=8081

# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/db_proyecto2
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

---

## API Documentation

The API is documented using **OpenAPI 3.0**

### Key Endpoints
| Endpoint                  | Method | Description                         |
|---------------------------|--------|-------------------------------------|
| `/customers`              | POST   | Create a new customer.              |
| `/customers`              | GET    | Retrieve all customers.             |
| `/customers/{id}`         | GET    | Retrieve a specific customer by ID. |
| `/customers/{id}`         | PUT    | Update a customer's information.    |
| `/customers/{id}`         | DELETE | Delete a customer.                  |

---

## Project Structure
```plaintext
src/
├── main/
│   ├── java/
│   │   └── com.example.customer/
│   │       ├── config/        # Contains config classes
│   │       ├── controller/    # REST controllers
│   │       ├── dto/           # Contains the body for Error Response
│   │       ├── model/         # Entity models
│   │       ├── repository/    # Repositories for JPA
│   │       ├── service/       # Service layer
│   │       └── CustomerMicroserviceApplication.java # Main application
│   └── resources/
│       ├── application.properties    # Application configuration
│       ├── api.yml                   # Open API Documentation
│       ├── static/            # Static files (if any)
│       └── templates/         # Templates for views (if any)
└── test/
    └── java/                  # Unit and integration tests
```

---
## Coverage Report
Aquí se muestra el reporte de cobertura de pruebas unitarias:

![Reporte de cobertura](https://i.ibb.co/NLF4V13/customerms.png)

---
## License
This project is licensed under the [MIT License](LICENSE).
