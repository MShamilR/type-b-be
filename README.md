# Type B Backend

Hello World API with name validation built with Spring Boot.

## Prerequisites

- Java 17 or higher
- Maven 3.6+

## How to Run

### 1. Build the Project

```bash
mvn clean install
```

### 2. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### GET /hello-world

Returns a greeting message with the provided name.

#### Parameters

- `name` (optional, query parameter): The name to greet

#### Example cURL Commands

**Success - Valid Name:**
```bash
curl "http://localhost:8080/hello-world?name=John"
```

Response:
```json
{
  "message": "Hello, John!"
}
```

**Success - Multiple Words:**
```bash
curl "http://localhost:8080/hello-world?name=John%20Doe"
```

Response:
```json
{
  "message": "Hello, John Doe!"
}
```

**Error - Empty Name:**
```bash
curl "http://localhost:8080/hello-world?name="
```

Response (400 Bad Request):
```json
{
  "error": "Invalid Input"
}
```

**Error - No Name Parameter:**
```bash
curl "http://localhost:8080/hello-world"
```

Response (400 Bad Request):
```json
{
  "error": "Invalid Input"
}
```

**Error - Invalid Characters:**
```bash
curl "http://localhost:8080/hello-world?name=John123"
```

Response (400 Bad Request):
```json
{
  "error": "Invalid Input"
}
```

## Technologies Used

- Spring Boot 3.2.0
- Java 17
- Maven
- Lombok
