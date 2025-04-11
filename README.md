# 🔐 Fullstack JWT Auth + RBAC: Spring Boot + MySQL + Docker

This project is a fullstack secure web application using:

- **Backend**: Spring Boot (Java) with JWT authentication, refresh tokens, encryption API, and Role-Based Access Control (RBAC)
- **Database**: MySQL (local or Docker)
- **Security**: Password hashing, token revocation, rate limiting, AEAD encryption with IV
- **DevOps**: Docker support for both backend and frontend

---

## ✅ Features

### 🔐 Authentication & Authorization
- JWT access + refresh token
- Session management via client-side storage (localStorage)
- Token revocation and logout endpoint
- Role-Based Access Control (RBAC)
    - Admin and User roles
    - Promote/demote users with API
    - Role-based endpoint protection

### 🔒 Crypto API
- AES encryption/decryption
- AEAD mode with IV (CBC/GCM)
- Authenticated encryption
- Key/IV stored in H2 (or MySQL)
- Error handling and input validation

### 📦 Dev Features
- Docker + Docker Compose support
- Environment-based configuration
- Rate limiting middleware
- Auto seed admin user (via `data.sql`)
- Postman collection for testing

---

## 🚀 Quickstart (Dev with Docker)

```bash
# 1. Clone the project
git clone https://github.com/your-repo/fullstack-jwt-rbac.git
cd fullstack-jwt-rbac

# 2. Start all services
docker-compose up --build
```

## 🔧 Backend API (Spring Boot)

    Base URL: http://localhost:8080
### 🔐 Authentication Endpoints
    Method	Endpoint	Access	Description
    POST	/auth/signup	Public	Register a new user with role(s)
    POST	/auth/login	Public	Login and receive access/refresh tokens
    POST	/auth/logout	Authenticated	Logout the user and revoke token
    POST	/auth/refresh	Authenticated (refresh token)	Issue new access token
    PUT	/auth/promote	Admin	Promote a user to admin
    PUT	/auth/demote	Admin	Demote an admin to regular user

### 🔒 Encryption Endpoints
    Method	Endpoint	Access	Description
    POST	/crypto/encrypt	Authenticated	Encrypt a plaintext message
    POST	/crypto/decrypt	Authenticated	Decrypt a cipher with IV
    GET	/crypto/secure	Authenticated	Test protected endpoint (secured access)

### ⚙️ Technologies Used
#### ✅ Backend Stack

    Spring Boot 3.x

    Spring Security 6

    Spring Data JPA

    JWT for access/refresh token (e.g., using java-jwt)

    MySQL

    Lombok for boilerplate reduction

    BCrypt password hashing

    AES (GCM/AEAD) encryption with IV

    Hibernate Validator for input validation

    Bucket4j or Spring filter for rate limiting

    Docker support
