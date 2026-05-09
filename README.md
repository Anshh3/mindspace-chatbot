# MindSpace — Mental Health Chatbot
### Spring Boot + HTML/CSS/JS Full Stack Project

A campus mental health chatbot that provides guided mindfulness exercises and connects students to mental health resources.

---

## Tech Stack

| Layer      | Technology                              |
|------------|-----------------------------------------|
| Frontend   | HTML5, CSS3, Vanilla JavaScript         |
| Backend    | Java 17, Spring Boot 3.2               |
| Security   | Spring Security + JWT                   |
| Database   | H2 (dev) / MySQL (production)           |
| ORM        | Spring Data JPA / Hibernate             |
| Build      | Maven                                   |

---

## Project Structure

```
mindspace/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/mindspace/
│   │   │   ├── MindSpaceApplication.java        # Entry point
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java           # Register / Login
│   │   │   │   ├── ChatController.java           # Chat endpoints
│   │   │   │   └── ResourceController.java       # Campus resources
│   │   │   ├── service/
│   │   │   │   ├── AuthService.java              # Auth business logic
│   │   │   │   ├── ChatService.java              # Core chat logic
│   │   │   │   ├── IntentClassifierService.java  # NLP keyword classifier
│   │   │   │   └── ResponseGeneratorService.java # Mindfulness responses
│   │   │   ├── model/
│   │   │   │   ├── User.java
│   │   │   │   ├── ChatSession.java
│   │   │   │   ├── ChatMessage.java
│   │   │   │   └── MentalHealthResource.java
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── ChatSessionRepository.java
│   │   │   │   ├── ChatMessageRepository.java
│   │   │   │   └── MentalHealthResourceRepository.java
│   │   │   ├── config/
│   │   │   │   ├── SecurityConfig.java           # Spring Security
│   │   │   │   ├── JwtUtil.java                  # JWT token utility
│   │   │   │   ├── JwtAuthFilter.java            # JWT filter
│   │   │   │   ├── GlobalExceptionHandler.java   # Error handling
│   │   │   │   └── DataSeeder.java               # Sample data on startup
│   │   │   └── dto/
│   │   │       └── AuthDto.java
│   │   └── resources/
│   │       ├── application.properties            # Dev (H2)
│   │       └── application-prod.properties       # Prod (MySQL)
│   └── test/
│       └── java/com/mindspace/
│           └── IntentClassifierServiceTest.java
```

---

## REST API Endpoints

### Auth (No token required)
| Method | Endpoint              | Description              |
|--------|-----------------------|--------------------------|
| POST   | /api/auth/register    | Register new student     |
| POST   | /api/auth/login       | Login, returns JWT token |

### Chat (Bearer token required)
| Method | Endpoint                  | Description                    |
|--------|---------------------------|--------------------------------|
| POST   | /api/chat/message         | Send message, get bot response |
| GET    | /api/chat/sessions        | List all user sessions         |
| GET    | /api/chat/sessions/{id}   | Get session message history    |

### Resources
| Method | Endpoint                      | Description                  |
|--------|-------------------------------|------------------------------|
| GET    | /api/resources/public         | All resources (public)       |
| GET    | /api/resources/crisis         | 24/7 crisis resources        |
| GET    | /api/resources/type/{type}    | By type (COUNSELING, etc.)   |

---

## How to Run

### 1. Development (H2 in-memory database)

```bash
cd mindspace
mvn spring-boot:run
```

App runs at: http://localhost:8080  
H2 Console: http://localhost:8080/h2-console

### 2. Production (MySQL)

Create the database:
```sql
CREATE DATABASE mindspacedb;
CREATE USER 'mindspace_user'@'localhost' IDENTIFIED BY 'your_secure_password';
GRANT ALL PRIVILEGES ON mindspacedb.* TO 'mindspace_user'@'localhost';
```

Run with prod profile:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

---

## Sample API Usage

### Register
```http
POST /api/auth/register
Content-Type: application/json

{
  "name": "Priya Sharma",
  "email": "priya@campus.edu",
  "password": "securePass123",
  "studentId": "STU2024001"
}
```

### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "priya@campus.edu",
  "password": "securePass123"
}
```
Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "name": "Priya Sharma",
  "email": "priya@campus.edu"
}
```

### Send Chat Message
```http
POST /api/chat/message
Authorization: Bearer <token>
Content-Type: application/json

{
  "message": "I feel anxious about my upcoming exams",
  "sessionId": null
}
```
Response:
```json
{
  "sessionId": 1,
  "reply": "I hear you — exam pressure can feel really overwhelming...",
  "mindfulnessSteps": [
    "Take a slow breath in for 4 counts...",
    "Name 5 things you can see around you..."
  ],
  "detectedIntent": "ANXIETY",
  "resources": [
    {
      "title": "Campus Counseling Center",
      "description": "Free, confidential counseling...",
      "contactInfo": "counseling@campus.edu | (555) 100-2000",
      "free": true,
      "available24x7": false
    }
  ]
}
```

---

## Intent Categories

| Intent      | Triggers                                        |
|-------------|-------------------------------------------------|
| ANXIETY     | exam, stress, nervous, worried, deadline        |
| BREATHING   | breathing, meditate, calm, relax                |
| OVERWHELMED | overwhelmed, burnout, too much, lonely          |
| SLEEP       | sleep, insomnia, tired, restless                |
| COUNSELOR   | counselor, therapist, help, professional        |
| SAD         | sad, depressed, hopeless, crying                |
| GREAT       | great, happy, fine, wonderful                   |
| GENERAL     | (fallback for all other messages)               |

---

## Running Tests

```bash
mvn test
```
