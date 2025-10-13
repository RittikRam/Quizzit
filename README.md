# MyQuizzApp - RESTful Quiz Platform Backend

MyQuizzApp is a robust backend service designed to power a flexible online quiz platform. Built with Spring Boot, it follows a clean layered architecture (Controller, Service, Repository) and uses PostgreSQL for persistence.

The application allows for the creation and management of **Topics**, **Quizzes**, **Questions**, and **Options**, along with a mechanism for handling quiz submissions and scoring.

## ✨ Features

### Current Core Functionality

* **Topic Management:** Full CRUD operations for organizing quizzes into subjects.
* **Quiz Management:** Create, retrieve, and list quizzes associated with specific topics and difficulty levels.
* **Data Validation:** Robust input validation using `jakarta.validation` on all DTOs.
* **Centralized Exception Handling:** Custom exceptions (`DuplicateResourceException`, `ResourceNotFoundException`) mapped to appropriate HTTP status codes (409, 404, 400, 500) via a `@RestControllerAdvice`.

### Data Model Highlights

* **Entities:** `Topic`, `Quiz`, `Question`, `Option`.
* **Enums:** `DifficultyLevel` (EASY, MEDIUM, HARD) and `QuestionType` (SINGLE\_CHOICE, MULTI\_CHOICE).

## 💻 Technologies Used

* **Java Version:** 21
* **Framework:** Spring Boot 3.x
* **Persistence:** Spring Data JPA
* **Database:** PostgreSQL
* **Build Tool:** Maven
* **Utility:** Lombok (for boilerplate reduction)

## 🛠️ Project Setup

### Prerequisites

1.  Java JDK 21 or later.
2.  Maven.
3.  A running instance of PostgreSQL.

### Database Configuration

The application is configured to connect to a local PostgreSQL database. Ensure your database is running and update the credentials in `MyQuizzApp/src/main/resources/application.properties` if needed.

| Property | Value (as per code) | Notes |
| :--- | :--- | :--- |
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/Quizzit` | The database name is `Quizzit`. |
| `spring.datasource.username` | `postgres` | |
| `spring.datasource.password` | `root` | **Highly recommended to change this in a production environment.** |
| `spring.jpa.hibernate.ddl-auto` | `update` | Hibernate will automatically update the schema based on entities. |

### Running the Application

1.  **Clone the repository:**
    ```bash
    git clone <your-repo-url>
    cd MyQuizzApp
    ```
2.  **Build the project:**
    ```bash
    ./mvnw clean install
    ```
3.  **Run the application:**
    ```bash
    ./mvnw spring-boot:run
    ```
The application will start on the default Spring Boot port (8080).

## 🌍 API Endpoints

The API is accessible at the root path `/api`.

### 1. Topic Endpoints (`/api/topics`)

| Method | Path | Description | Body (Request DTO) |
| :--- | :--- | :--- | :--- |
| **GET** | `/api/topics` | Retrieve all topics. | None |
| **GET** | `/api/topics/{id}` | Retrieve a single topic by ID. | None |
| **POST** | `/api/topics` | Create a new topic. | `TopicRequestDTO` |
| **PUT** | `/api/topics/{id}` | Update an existing topic. | `TopicRequestDTO` |

### 2. Quiz Endpoints (`/api/quizzes`)

| Method | Path | Description | Body (Request DTO) |
| :--- | :--- | :--- | :--- |
| **GET** | `/api/quizzes` | Retrieve all quizzes. | None |
| **GET** | `/api/quizzes/{id}` | Retrieve a single quiz by ID. | None |
| **POST** | `/api/quizzes` | Create a new quiz. | `QuizRequestDTO` (requires `topicIdDTO`) |

### 3. Question Endpoints (Future Implementation)

These endpoints are necessary to fully complete the application's functionality.

| Method | Path | Description |
| :--- | :--- | :--- |
| **POST** | `/api/quizzes/{quizId}/questions` | **(To be implemented)** Create a new question for a specific quiz, including its options. |
| **GET** | `/api/quizzes/{quizId}/questions` | **(To be implemented)** Retrieve all questions for a quiz (Admin view, includes correct options). |
| **POST** | `/api/quizzes/submit` | **(To be implemented)** Submit user answers for scoring. |

## 📐 Project Structure

The project follows a standard Spring Boot/Maven structure.
