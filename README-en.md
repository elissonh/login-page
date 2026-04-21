[![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](./README.md)

# ✨ Login Page

A full-stack login application built as a learning project to practice React on the frontend and Java Spring Boot on the backend.

## 🛠️ Tech Stack

**Frontend:** React (Vite) — hosted on GitHub Pages  
**Backend:** Java Spring Boot — hosted on Railway  
**Database:** H2 in-memory

## 🎯 Features

- Register an account with username, email and password
- Login with email and password
- Passwords stored securely using BCrypt hashing
- Simple dashboard displaying a welcome message after login

## 💻 Frontend

Built with React using `useState` for form state management and vanilla `JavaScript` for API calls.

## 💻 Backend

REST API built with Spring Boot exposing two endpoints:

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/auth/register` | Create a new account |
| `POST` | `/api/auth/login` | Authenticate and login |

## 🚀 Demo
https://elissonh.github.io/login-page/

## 📝 How to run locally

**Backend**
```bash
cd backend
mvn spring-boot:run
```
Runs at `http://localhost:8080`

**Frontend**
```bash
cd frontend
npm install
npm run dev
```
Runs at `http://localhost:5173`