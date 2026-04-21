[![en](https://img.shields.io/badge/lang-en-red.svg)](./README-en.md)

# ✨ Página de Login

Aplicação full-stack de login desenvolvida como projeto de aprendizado para praticar React no frontend e Java Spring Boot no backend.

## 🛠️ Tecnologias

**Frontend:** React (Vite) — hospedado no GitHub Pages  
**Backend:** Java Spring Boot — hospedado no Railway  
**Banco de dados:** H2 em memória

## 🎯 Funcionalidades

- Cadastro de conta com nome de usuário, e-mail e senha
- Login com e-mail e senha
- Senhas armazenadas com segurança usando hash BCrypt
- Dashboard simples exibindo uma mensagem de boas-vindas após o login

## 💻 Frontend

Desenvolvido com React usando `useState` para gerenciamento de estado dos formulários e `JavaScript` nativo para as chamadas à API.

## 💻 Backend

API REST construída com Spring Boot expondo dois endpoints:

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/auth/register` | Criar uma nova conta |
| `POST` | `/api/auth/login` | Autenticar e realizar login |

## 🚀 Demonstração
https://elissonh.github.io/login-page/

## 📝 Como executar localmente

**Backend**
```bash
cd backend
mvn spring-boot:run
```
Disponível em `http://localhost:8080`

**Frontend**
```bash
cd frontend
npm install
npm run dev
```
Disponível em `http://localhost:5173`