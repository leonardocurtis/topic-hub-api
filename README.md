# 💬 Topic Hub API

![Java](https://img.shields.io/badge/Java-21-ff2121?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.x-brightgreen?style=for-the-badge&logo=spring)
![MongoDB](https://img.shields.io/badge/MongoDB-8.x-orange?style=for-the-badge&logo=MongoDB)
![Backend](https://img.shields.io/badge/Backend-API-8A2BE2?style=for-the-badge)
![Security](https://img.shields.io/badge/Security-JWT-blue?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-258a60?style=for-the-badge)

A **Topic Hub API** is a RESTful backend application built with Spring Boot that simulates a discussion platform where users can create topics, post responses, and interact with content.

This project focuses on real-world backend concerns such as authentication, authorization, domain rules, and scalable API design.

---

## 🚀 Features

### 👤 User Management
- JWT-based authentication
- Retrieve authenticated user profile
- Update profile information
- Change password securely
- Deactivate account (auto-reactivates on login)

### 🧵 Topics
- Create topics
- List topics with pagination
- View topic details
- Update topic content
- Close topics (prevent new responses)
- Soft delete (admin only)

### 💬 Responses
- Add responses to topics
- Mark a response as solved
- Deactivate responses (moderation)
- Automatically reopen topic when solution is removed

### 🎓 Courses
- Create and manage courses
- List courses with pagination
- Update course data
- Archive courses (admin only)

---

## 🔐 Security

- JWT authentication
- Role-based authorization:
  - USER
  - MODERATOR
  - ADMIN
- Endpoint protection using @PreAuthorize
- Secure password validation flow

---

## 🧠 Business Rules Highlights

- Only one response can be marked as **solved per topic**
- Removing a solved response **reopens the topic**
- Closed topics **cannot receive new responses**
- Deactivated users **cannot access the platform**
- Account is automatically reactivated upon login
- Soft delete strategy for topics, responses, and users

---

## 🛠️ Tech Stack

- Java 21
- Spring Boot 3+
- Spring Security
- JWT
- Spring Data MongoDB
- Maven
- OpenAPI / Swagger

---

## 🔗 API Documentation

Swagger UI available at:

``` text
http://localhost:8080/swagger-ui.html
```
---

## 📌 Example Endpoints

> MongoDB IDs are 24-character hex strings (ObjectId)

### 👤 User

``` http
GET /users/me  
PATCH /users/me  
PATCH /users/me/password  
PATCH /users/me/deactivate
```  

### 🧵 Topics

``` http
POST /topics  
GET /topics?page=0&size=10  
GET /topics/{id}  
PATCH /topics/{id}  
PATCH /topics/{id}/close  
DELETE /topics/{id} 
``` 

### 💬 Responses

``` http
POST /topics/{id}/responses  
PATCH /topics/{id}/responses/{id}/solve  
PATCH /topics/{id}/responses/{id}/deactivate  
```

### 🎓 Courses

``` http
POST /courses  
GET /courses?page=0&size=10  
GET /courses/{id}  
PATCH /courses/{id}  
DELETE /courses/{id}  
```

---

## 🔐 Environment Variables

Sensitive data is not stored in the repository.

| Variable     | Description                  |
|--------------|------------------------------|
| MONGODB_URI  | MongoDB connection string    |
| JWT_SECRET   | Secret key for JWT signing   | 

---

## ⚙️ Configuration (application.yml)

The project uses **YAML** configuration.
You must provide your own database credentials and JWT Secret Key.

```yml
spring:
  mongodb:
    uri: ${MONGODB_URI}

api:
  security:
    token:
      secret: ${JWT_SECRET}
```
---

## ▶️ Running the Project

### Prerequisites

- Java 17+
- Maven
- MongoDB

### Clone the repository

``` bash
git clone https://github.com/leonardocurtis/topic-hub-api.git 
```

### Run the application

``` bash
mvn spring-boot:run  
``` 
---

## 📈 Learning Outcomes

- REST API design with best practices
- JWT authentication and authorization
- Role-based access control
- Clean architecture and separation of concerns
- DTO pattern for API communication
- Pagination and filtering
- Soft delete strategies
- API documentation with Swagger

---

## 📄 License

This project is licensed under the **MIT License**.

---

## 👨‍💻 Author


Developed by **Leonardo Curtis**.
Focused on Java back-end development, clean architecture, and continuous learning.
