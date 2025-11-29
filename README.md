🍕 Comy Delivery – Backend

Aplicação backend corporativa desenvolvida com Spring Boot, responsável por autenticação, pedidos, entregas, clientes, restaurantes e integrações externas da plataforma Comy Delivery.

<p align="center"> <a href="#sobre-o-projeto">Sobre</a> • <a href="#tecnologias-utilizadas">Tecnologias</a> • <a href="#instalação-e-configuração">Instalação</a> • <a href="#como-rodar-o-projeto">Como Rodar</a> • <a href="#funcionalidades">Funcionalidades</a> • <a href="#equipe-de-desenvolvimento">Equipe</a> </p>
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

O Comy Delivery – Backend é a camada responsável por toda a lógica de negócio e comunicação com o banco de dados.
Ele fornece APIs REST robustas para:

Clientes

Restaurantes

Entregadores

Administradores

E também implementa:

Autenticação

Recuperação de senha

Pedidos e entregas

Avaliações

Cupons

Integração com API externa de CEP

Cálculo de frete por distância

🛠 Tecnologias Utilizadas

Java 21

Spring Boot 3.5.7

Spring Data JPA

Spring Validation

Spring Security

OpenFeign (API CEP)

JavaMailSender

Lombok

PostgreSQL

Swagger / OpenAPI

Maven

📦 Pré-requisitos

Java 21

Maven 3.9+

PostgreSQL 14+

Git

IDE (IntelliJ recomendado)

⚙️ Instalação e Configuração
1️⃣ Clonar o repositório
git clone https://github.com/comy-delivery/comy-delivery-back.git
cd comy-delivery-back

2️⃣ Criar o banco de dados
CREATE DATABASE comy_delivery;

3️⃣ Criar arquivo .env com as variáveis
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
Usando o Maven Wrapper
./mvnw spring-boot:run

Usando Maven instalado
mvn spring-boot:run

Gerar JAR
./mvnw clean package -DskipTests
java -jar target/comy-delivery-back.jar

📁 Estrutura do Projeto
src/main/java/com/comy_delivery_back/
├── controller/
├── service/
├── repository/
├── model/
├── dto/
│   ├── request/
│   └── response/
├── security/
├── configuration/
├── exception/
├── utils/
└── client/   # Serviços externos

🚀 Funcionalidades
🔐 Autenticação

Login e registro

Recuperação de senha com token

Perfis: Cliente, Restaurante, Entregador, Admin

🍽 Restaurantes

Cadastro

Horários e disponibilidade

Produtos e promoções

Dashboard

🛍 Pedidos

Itens, adicionais e cupons

Cálculo automático de frete por distância

Status completo do fluxo do pedido

Dashboard do restaurante

🚚 Entregas

Atribuição automática de entregador

Atualização de status

Dashboard do entregador

⭐ Avaliações

Avaliação de restaurantes

Avaliação de entregas

📍 Endereços

Busca automática por CEP

Cálculo de distância (Haversine)

📘 Documentação da API

Acesse:

🔗 http://localhost:8084/swagger-ui.html

Todas as rotas estão documentadas e organizadas por domínio.

🔐 Variáveis de Ambiente
Variável	Obrigatória	Descrição
DATABASE_URL	✔️	URL do banco
DATABASE_USERNAME	✔️	Usuário
DATABASE_PASSWORD	✔️	Senha
EMAIL_SENDER	✔️	Gmail remetente
SENHA_EMAIL_SENDER	✔️	Senha de App
AWESOMEAPI_KEY	✔️	Chave da API externa
FRONTEND_URL	❌	URL do front
BACKEND_URL	❌	URL do back
PASSWORD_RECOVERY_URL	❌	Link de recuperação
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
