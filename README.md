# 🍕 Comy Delivery - Backend

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen)
![Java](https://img.shields.io/badge/Java-21-orange)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14%2B-blue)
![Maven](https://img.shields.io/badge/Maven-3.9%2B-red)

Aplicação backend corporativa do Comy Delivery, responsável pelas APIs REST, regras de negócio, autenticação, pedidos, entregas e integrações externas.

[Sobre](#-sobre-o-projeto) • [Tecnologias](#-tecnologias-utilizadas) • [Instalação](#%EF%B8%8F-instalação-e-configuração) • [Como Rodar](#%EF%B8%8F-como-rodar-o-projeto) • [Funcionalidades](#-funcionalidades) • [Equipe](#-equipe-de-desenvolvimento)

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação e Configuração](#%EF%B8%8F-instalação-e-configuração)
- [Como Rodar o Projeto](#%EF%B8%8F-como-rodar-o-projeto)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Funcionalidades](#-funcionalidades)
- [Endpoints Principais](#-endpoints-principais)
- [Autenticação](#-autenticação)
- [Variáveis de Ambiente](#-variáveis-de-ambiente)
- [Build para Produção](#-build-para-produção)
- [Troubleshooting](#-troubleshooting)
- [Scripts Úteis](#-scripts-úteis)
- [Equipe de Desenvolvimento](#-equipe-de-desenvolvimento)

---

## 🎯 Sobre o Projeto

O **Comy Delivery Backend** é uma API REST completa desenvolvida em Spring Boot, oferecendo:

- ⚙️ Regras de negócio claras e organizadas
- 🔐 Sistema completo de autenticação com JWT
- 📦 Gestão de pedidos com múltiplos fluxos
- 🚚 Módulo de entregas e dashboard do entregador
- 🍽️ Módulo de restaurantes com catálogo e produtos
- ⭐ Sistema de avaliações
- 📍 Cálculo de frete por coordenadas (Haversine)
- 📧 Recuperação de senha via e-mail
- 🌐 Integração com API externa de CEP (AwesomeAPI)

---

## 🚀 Tecnologias Utilizadas

| Tecnologia | Versão | Descrição |
|------------|--------|-----------|
| Java | 21 | Linguagem principal |
| Spring Boot | 3.5.7 | Framework de backend |
| Spring Data JPA | - | Persistência |
| Spring Security | - | Autenticação JWT |
| Maven | 3.9+ | Build e dependências |
| PostgreSQL | 14+ | Banco de dados |
| Lombok | - | Redução de boilerplate |
| OpenFeign | - | API externa (CEP) |
| JavaMailSender | - | Envio de emails |
| Swagger | - | Documentação |

---

## 📦 Pré-requisitos

Antes de começar, instale:

- 🟢 **Java 21**
- 🟢 **Maven 3.9+**
- 🟢 **PostgreSQL 14+**
- 🔥 **Git**
- 💻 **IDE recomendada:** IntelliJ

Verifique versões:
```bash
java -version
mvn -version
psql --version
```

---

## ⚙️ Instalação e Configuração

### 1️⃣ Clone o Repositório
```bash
git clone https://github.com/comy-delivery/comy-delivery-back.git
cd comy-delivery-back
```

### 2️⃣ Crie o Banco de Dados
```sql
CREATE DATABASE comy_delivery;
```

### 3️⃣ Configure o `.env`
```env
DATABASE_URL=jdbc:postgresql://localhost:5432/comy_delivery
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=root

EMAIL_SENDER=seu_email@gmail.com
SENHA_EMAIL_SENDER=sua_senha_app

AWESOMEAPI_KEY=sua_chave

FRONTEND_URL=http://localhost:4200
BACKEND_URL=http://localhost:8084
PASSWORD_RECOVERY_URL=http://localhost:4200/reset-password
```

### 4️⃣ Rodar o Maven (instalação)
```bash
./mvnw clean install
```

---

## ▶️ Como Rodar o Projeto

### Desenvolvimento
```bash
./mvnw spring-boot:run
```

### Produção (JAR)
```bash
./mvnw clean package -DskipTests
java -jar target/comy-delivery-back.jar
```

### Subir com outra porta
```bash
java -jar target/comy-delivery-back.jar --server.port=8085
```

---

## 🌐 Endpoints Principais

| Recurso | URL |
|---------|-----|
| **Swagger** | http://localhost:8084/swagger-ui.html |
| **Health** | http://localhost:8084/api/health |
| **Login** | `POST /api/auth/login` |
| **Restaurantes** | `/api/restaurante` |
| **Pedidos** | `/api/pedido` |
| **Entregas** | `/api/entregas` |
| **Avaliações** | `/api/avaliacao` |

---

## 📁 Estrutura do Projeto
```
src/
├── controller/           # Controllers REST
├── service/              # Regras de negócio
├── repository/           # JPA Repositories
├── model/                # Entidades
├── dto/                  # DTOs
│   ├── request/
│   └── response/
├── security/             # JWT, filtros, configs
├── configuration/        # CORS, Swagger, etc.
├── client/               # Feign clients
├── exception/            # Tratamento global
└── utils/                # Helpers gerais
```

---

## 🎯 Funcionalidades

### 👤 Para Clientes

- ✅ Cadastro/login
- ✅ Lista de restaurantes
- ✅ Cardápio com adicionais
- ✅ Aplicação de cupons
- ✅ Endereços
- ✅ Avaliações
- ✅ Histórico de pedidos

### 🍕 Para Restaurantes

- ✅ Painel administrativo
- ✅ Cadastro de produtos e adicionais
- ✅ Horários de funcionamento
- ✅ Gerenciamento de pedidos
- ✅ Dashboard

### 🚴 Para Entregadores

- ✅ Entregas disponíveis
- ✅ Atualização de status
- ✅ Dashboard diário
- ✅ Histórico

### ⚙️ Funcionalidades Gerais

- ✅ Autenticação com JWT
- ✅ Tokens com refresh
- ✅ Tratamento global de exceções
- ✅ Integração com API externa
- ✅ Cálculo automático de frete
- ✅ Envio de email para recuperação de senha

---

## 🔐 Autenticação

O backend utiliza **JWT** com:

- Token de acesso
- Token de refresh
- Filtros de autenticação

**Roles:**

- `CLIENTE`
- `RESTAURANTE`
- `ENTREGADOR`
- `ADMIN`

**Headers utilizados:**
```
Authorization: Bearer <token>
```

---

## 🔧 Variáveis de Ambiente

| Variável | Descrição |
|----------|-----------|
| `DATABASE_URL` | URL do PostgreSQL |
| `DATABASE_USERNAME` | Usuário |
| `DATABASE_PASSWORD` | Senha |
| `EMAIL_SENDER` | Email para envio |
| `SENHA_EMAIL_SENDER` | Senha de app |
| `AWESOMEAPI_KEY` | Chave externa |
| `FRONTEND_URL` | URL do front |
| `BACKEND_URL` | URL do back |
| `PASSWORD_RECOVERY_URL` | Link de recuperação |

---

## 📦 Build para Produção
```bash
./mvnw clean package -DskipTests
java -jar target/comy-delivery-back.jar
```

---

## 🐛 Troubleshooting

### ❌ Erro: "Connection refused"
➡️ Verifique se o PostgreSQL está rodando.

### ❌ Erro: "Port 8084 already in use"
➡️ Execute em outra porta:
```bash
java -jar target/*.jar --server.port=8085
```

### ❌ Erro ao enviar email
➡️ Gere uma senha de app no Gmail.

### ❌ Erro de CORS
➡️ Ajuste o domínio em `FRONTEND_URL`.

---

## 📝 Scripts Úteis
```bash
./mvnw clean compile
./mvnw test
./mvnw dependency:tree
./mvnw spring-boot:run
```

---

## 👥 Equipe de Desenvolvimento

| Integrante | GitHub |
|------------|--------|
| **Arthur** | [@Thurrrr](https://github.com/Thurrrr) |
| **Emilio** | [@emilioaugusto](https://github.com/emilioaugusto) |
| **Heloisa** | [@helomt](https://github.com/helomt) |
| **Jude** | [@judevieira](https://github.com/judevieira) |
| **Sinara** | [@sinara-santinoni](https://github.com/sinara-santinoni) |

---

## 📄 Licença

Este projeto é de propriedade da equipe **Comy Delivery**.

---

<div align="center">
  
⭐ **Desenvolvido com Spring Boot e ❤️**

[⬆ Voltar ao topo](#-comy-delivery---backend)

</div>
