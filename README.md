🍕 Comy Delivery - Backend

Aplicação backend corporativa desenvolvida com Spring Boot, fornecendo APIs REST completas para clientes, restaurantes e entregadores.

Sobre
 • Tecnologias
 • Instalação
 • Como Rodar
 • Funcionalidades
 • Equipe

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

O Comy Delivery - Backend é a camada responsável por toda lógica de negócio da plataforma:
gestão de usuários, autenticação, pedidos, entregas, avaliações, cupons e integração com serviços externos.

Construído com arquitetura limpa e profissional, garante:

Segurança

Escalabilidade

Performance

Manutenibilidade

🛠️ Tecnologias Utilizadas

Java 21

Spring Boot 3.5.7

Spring Data JPA

Spring Validation

OpenFeign (CEP)

PostgreSQL

Lombok

JavaMailSender

Swagger / OpenAPI

Maven

📦 Pré-requisitos

Java 21

Maven 3.9+

PostgreSQL 14+

Git

IDE (IntelliJ recomendada)

⚙️ Instalação e Configuração
1. Clonar o repositório
git clone https://github.com/comy-delivery/comy-delivery-back.git
cd comy-delivery-back

2. Criar o banco de dados
CREATE DATABASE comy_delivery;

3. Configurar variáveis (.env)
DATABASE_URL=jdbc:postgresql://localhost:5432/comy_delivery
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=root

EMAIL_SENDER=seu_email@gmail.com
SENHA_EMAIL_SENDER=sua_senha_app

AWESOMEAPI_KEY=sua_chave_api

FRONTEND_URL=http://localhost:4200
BACKEND_URL=http://localhost:8084
PASSWORD_RECOVERY_URL=http://localhost:4200/reset-password

▶️ Como Rodar o Projeto
Via Maven Wrapper
./mvnw spring-boot:run

Via Maven
mvn spring-boot:run

Via JAR
./mvnw clean package
java -jar target/comy-delivery-back.jar

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

Recuperação de senha

Perfis: Cliente, Restaurante, Entregador, Admin

🍽️ Restaurantes

Catálogo de produtos

Promoções

Horários de funcionamento

Dashboard

🛍️ Pedidos

Itens + adicionais

Cupons

Fluxo completo de status

Dashboard do restaurante

🚚 Entregas

Atribuição automática

Status em tempo real

Dashboard do entregador

📍 Endereços

Busca por CEP

Conversão para coordenadas

Distância (Haversine)

📘 Documentação da API

A documentação detalhada está disponível em:

🔗 http://localhost:8084/swagger-ui.html

🔐 Variáveis de Ambiente
Variável	Descrição	Obrigatória
DATABASE_URL	URL do banco	✔️
DATABASE_USERNAME	Usuário	✔️
DATABASE_PASSWORD	Senha	✔️
EMAIL_SENDER	Gmail remetente	✔️
SENHA_EMAIL_SENDER	Senha de App	✔️
AWESOMEAPI_KEY	API CEP	✔️
FRONTEND_URL	URL do front	❌
BACKEND_URL	URL do back	❌
PASSWORD_RECOVERY_URL	Reset de senha	❌
🏭 Build para Produção
./mvnw clean package -DskipTests
java -jar target/comy-delivery-back.jar

👥 Equipe de Desenvolvimento
Integrante	GitHub
Arthur	https://github.com/Thurrrr

Emilio	https://github.com/emilioaugusto

Heloisa	https://github.com/helomt

Jude	https://github.com/judevieira

Sinara	https://github.com/sinara-santinoni
