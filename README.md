# EXPENSE-TRACKER
# Microservices Architecture for Scalable and Secure Applications

![Architecture Overview](./path/to/your/exp.png)

## 🚀 Project Overview

Welcome to the **Microservices Architecture for Secure and Scalable Applications** repository! This project is a comprehensive demonstration of how to build a robust, secure, and scalable application using modern microservices architecture principles.

### 🌟 Features

- **Modular Architecture**: Decoupled services to ensure each component can evolve independently.
- **Security First**: Centralized authentication service with JWT tokenization and refresh token support.
- **Scalability**: Designed for horizontal scaling using Docker and Kubernetes.
- **Event-Driven Communication**: Implemented using Kafka and RabbitMQ for seamless inter-service messaging.
- **Resilient and Fault-Tolerant**: Leveraging Redis for caching and RabbitMQ for reliable message handling.
- **Extensive API Gateway**: Using Kong API Gateway for managing and routing requests efficiently.

## 🛠️ Technology Stack

- **Spring Boot**: For building microservices.
- **Kong API Gateway**: For managing API requests.
- **Kafka & RabbitMQ**: For event-driven architecture.
- **Redis**: For caching and session management.
- **Docker & Kubernetes**: For containerization and orchestration.
- **MySQL**: For relational database management.

## 📜 Architecture Overview

This architecture is designed with a focus on separation of concerns and scalability. The core components include:

1. **API Gateway**: Manages and routes all incoming client requests.
2. **Auth Service**: Handles user authentication, tokenization, and user session management.
3. **Notification Service**: Sends notifications using events produced by other services.
4. **User Service**: Manages user information and integrates with templating services.
5. **Billing Service**: Handles billing operations and transactions.
6. **Ledger Service**: Maintains transaction records for auditing purposes.
7. **Reporting Service**: Generates reports based on data collected from various services.
8. **Templatization Service**: Handles the generation of templates for dynamic content.

## 📌 Endpoints

### Auth Service
- **`POST /auth/v1/login`**: Authenticate users and issue JWT tokens.
- **`POST /auth/v1/signup`**: Register a new user.
- **`POST /auth/v1/refreshToken`**: Refresh JWT tokens using a valid refresh token.

### User Service
- **`GET /user/v1/{userId}`**: Retrieve user details by user ID.
- **`POST /user/v1/update`**: Update user information.

### Billing Service
- **`POST /billing/v1/invoice`**: Generate an invoice for a transaction.
- **`GET /billing/v1/{userId}/invoices`**: Retrieve all invoices for a specific user.

### Notification Service
- **`POST /notify/v1/send`**: Send a notification to a user.

### Ledger Service
- **`GET /ledger/v1/{transactionId}`**: Retrieve transaction details by ID.

### Reporting Service
- **`GET /report/v1/{reportId}`**: Fetch a specific report.
- **`POST /report/v1/generate`**: Generate a new report based on the provided data.

## 🏗️ How to Run the Project

1. **Clone the Repository**:
    ```bash
    git clone https://github.com/yourusername/microservices-architecture.git
    cd microservices-architecture
    ```

2. **Setup Docker**:
    - Ensure Docker and Docker Compose are installed.
    - Run the following command to build and start the containers:
    ```bash
    docker-compose up --build
    ```

3. **Access the Services**:
    - The API Gateway will be accessible at `http://localhost:8000/`.
    - Use tools like Postman or Curl to interact with the endpoints.

## ⚙️ Configuration

All configurations are managed via environment variables. Refer to the `.env.example` file for the required settings and customize them as needed for your environment.

## 🧪 Testing

Unit and integration tests are provided for each microservice. To run the tests, navigate to the service directory and execute:
```bash
./gradlew test

