# Project: SOAP Web Service for Revolving Credit Line Management

## Objective
Develop a SOAP web service to manage revolving credit lines, providing functionalities to interact with entities such as **Credit Line**, **Borrower**, **Lender**, and **Subject**. The project includes the implementation of SOAP clients and utilizes **JPA**, **JPQL**, and **EJB** for persistence management and business logic.

## Key Features

- **SOAP Interface**  
  Create a SOAP interface for the `CreditLineEJB` service, ensuring secure and structured communication.

- **Persistence Management with JPA**  
  - Utilize JPA to map entities and relationships.
  - Manage transactions and perform database operations.
  - Execute JPQL queries for data manipulation.

- **Business Logic with EJB**  
  - Implement EJB components for synchronous and asynchronous business methods.
  - Manage the lifecycle of EJBs and use interceptors.
  - Control transactions, distinguishing between Container-Managed Transactions (CMT) and Bean-Managed Transactions (BMT).

- **SOAP Clients**
  - **Standalone**: Independent application to invoke web service methods.
  - **Web**: Angular client with a modern and responsive interface.
  - **Multiplatform**: Tools for generating SOAP proxies in different programming languages.

## Technologies Used

- **Java** for backend development.
- **SOAP** for structured information exchange via XML.
- **JPA** and **JPQL** for data persistence and manipulation.
- **EJB** for business logic and transaction management.
- **Angular** for the web client.
- **Maven/Gradle** for dependency management.
- **WildFly** as the application server.
