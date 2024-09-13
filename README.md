# Development environment
## Prerequisites

- JDK 17
- Maven
- MySQL
- MongoDB
- Docker

## Ports used
Make sure that the necessary ports are available on your machine.

java
10001 - Gateway
10002 - PatientService
10003 - NoteService
10004 - RiskService
10005 - ClientFrontService
27017 - Default MongoDB
3306 - Default MySQL
```

## Starting the Application

Go to the project root folder and run the following command to start the application:

```bash
docker-compose up -d
```

## Connecting to the application

Different roles: `doctor`, `organizer`
Common password: `rootroot

## Green Code recommendations

### 1. Software eco-design
#### Reusability and Modularity:

- Adopt a modular architecture to allow components to be reused and avoid redundant code.

### 2. MongoDB management
#### Indexing:

- Use indexes to optimise read requests and reduce the load on the MongoDB server.
- Monitor and maintain indexes to ensure they are relevant and efficient.

### 3. Efficient use of memory:

- Prefer the use of suitable data structures to minimise memory consumption.
- Use lightweight libraries and avoid unnecessary dependencies.

Translated with DeepL (https://www.deepl.com/app/?utm_source=ios&utm_medium=app&utm_campaign=share-translation)