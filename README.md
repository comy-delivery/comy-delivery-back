<p align="center"> <img src="https://raw.githubusercontent.com/sinara-santinoni/assets/main/comy-banner.png" alt="Comy Delivery Banner" width="100%" /> </p> <h1 align="center">🍕 Comy Delivery - Backend</h1> <p align="center"> Aplicação backend corporativa desenvolvida em <strong>Spring Boot</strong>, responsável por todas as regras de negócio, autenticação, pedidos, entregas e integrações externas da plataforma Comy Delivery. </p> <p align="center"> <img src="https://img.shields.io/badge/Java-21-EC2025?style=for-the-badge&logo=java&logoColor=white" /> <img src="https://img.shields.io/badge/Spring_Boot-3.5.7-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" /> <img src="https://img.shields.io/badge/PostgreSQL-14+-4169E1?style=for-the-badge&logo=postgresql&logoColor=white" /> <img src="https://img.shields.io/badge/Maven-3.9+-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" /> <img src="https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?style=for-the-badge&logo=swagger&logoColor=black" /> </p>
📑 Índice

Sobre o Projeto

Tecnologias Utilizadas

Pré-requisitos

Instalação e Configuração

Como Rodar o Projeto

Estrutura do Projeto

Funcionalidades

Documentação da API

Variáveis de Ambiente

Build para Produção

Equipe de Desenvolvimento

🧩 Sobre o Projeto

O Comy Delivery - Backend é a camada de serviços da plataforma, fornecendo APIs REST para clientes, restaurantes e entregadores.
É responsável por autenticação, pedidos, entregas, cupons, avaliações, cálculos de frete e comunicação com APIs externas.

🛠️ Tecnologias Utilizadas

Java 21

Spring Boot 3.5.7

Spring Data JPA

OpenFeign

Lombok

PostgreSQL

JavaMailSender

Swagger / OpenAPI

Maven

📦 Pré-requisitos

Java 21

Maven 3.9+

PostgreSQL 14+

Git

IDE (IntelliJ recomendado)

⚙️ Instalação e Configuração
1. Clonar repositório
git clone https://github.com/comy-delivery/comy-delivery-back.git
cd comy-delivery-back

2. Configurar banco
CREATE DATABASE comy_delivery;

3. Criar arquivo .env
DATABASE_URL=jdbc:postgresql://localhost:5432/comy_delivery
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=root

EMAIL_SENDER=seu_email@gmail.com
SENHA_EMAIL_SENDER=sua_senha_app

AWESOMEAPI_KEY=sua_chave

FRONTEND_URL=http://localhost:4200
BACKEND_URL=http://localhost:8084
PASSWORD_RECOVERY_URL=http://localhost:4200/reset-password

▶️ Como Rodar o Projeto
./mvnw spring-boot:run


Ou:

mvn spring-boot:run

📁 Estrutura do Projeto
src/main/java/com/comy_delivery_back/
├── controller/
├── service/
├── repository/
├── model/
├── dto/
├── configuration/
├── security/
├── utils/
└── exception/

🚀 Funcionalidades
👥 Usuários

Cadastro e autenticação

Perfis: Cliente, Restaurante, Entregador, Admin

Recuperação de senha

🍕 Restaurantes

Produtos

Promoções

Horários e disponibilidade

Dashboard

🛍️ Pedidos

Itens + adicionais

Cupons

Fluxo de status

Dashboard do restaurante

🚚 Entregas

Rastreamento

Atribuição

Dashboard do entregador

📘 Documentação da API

Acesse:

🔗 http://localhost:8084/swagger-ui.html

🔐 Variáveis de Ambiente
Variável	Obrigatória	Descrição
DATABASE_URL	✔	URL do banco
DATABASE_USERNAME	✔	Usuário do banco
DATABASE_PASSWORD	✔	Senha do banco
EMAIL_SENDER	✔	Email remetente
SENHA_EMAIL_SENDER	✔	Senha de app Gmail
AWESOMEAPI_KEY	✔	Chave de API CEP
FRONTEND_URL	✖	URL do front
BACKEND_URL	✖	URL do back
PASSWORD_RECOVERY_URL	✖	URL de reset
👥 Equipe de Desenvolvimento
Integrante	GitHub
Arthur	https://github.com/Thurrrr

Emilio	https://github.com/emilioaugusto

Heloisa	https://github.com/helomt

Jude	https://github.com/judevieira

Sinara	https://github.com/sinara-santinoni
