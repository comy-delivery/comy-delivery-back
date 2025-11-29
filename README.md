Comy Delivery – Backend

Plataforma robusta de delivery desenvolvida em Spring Boot, fornecendo APIs REST para gestão completa de restaurantes, pedidos, entregas e usuários.

Índice

Visão Geral

Tecnologias

Pré-requisitos

Configuração do Ambiente

Instalação

Execução do Projeto

Estrutura do Código

Funcionalidades

Documentação da API

Variáveis de Ambiente

Banco de Dados

Testes da API

Troubleshooting

Equipe

1. Visão Geral

O Comy Delivery é um backend moderno e escalável projetado para sustentar uma plataforma de delivery.
O sistema integra restaurantes, clientes e entregadores, oferecendo funcionalidades de gestão de pedidos, cálculo automático de frete, sistema de cupons, avaliações, e recuperação de senha via e-mail.

2. Tecnologias

Java 21

Spring Boot 3.5.7

Spring Data JPA

Spring Validation

Spring Cloud OpenFeign (CEP)

PostgreSQL

Lombok

BCrypt

JavaMailSender

Springdoc OpenAPI (Swagger)

Maven

3. Pré-requisitos

Java 21+

Maven 3.9+ (ou Maven Wrapper incluso)

PostgreSQL 14+

Git

IDE (IntelliJ IDEA recomendado)

4. Configuração do Ambiente
Banco de Dados

Crie o banco:

CREATE DATABASE comy_delivery;

Variáveis de Ambiente

Configure no sistema ou crie um arquivo .env:

# Banco de Dados
DATABASE_URL=jdbc:postgresql://localhost:5432/comy_delivery
DATABASE_USERNAME=seu_usuario
DATABASE_PASSWORD=sua_senha

# E-mail (Gmail)
EMAIL_SENDER=seu_email@gmail.com
SENHA_EMAIL_SENDER=sua_senha_app

# API Externa
AWESOMEAPI_KEY=sua_chave

# URLs (opcional)
FRONTEND_URL=http://localhost:4200
BACKEND_URL=http://localhost:8084
PASSWORD_RECOVERY_URL=http://localhost:4200/reset-password


Para envio de e-mails via Gmail, habilite verificação em duas etapas e gere uma senha de app.

5. Instalação

Clone o repositório:

git clone https://github.com/seu-usuario/comy-delivery-back.git
cd comy-delivery-back


Instale dependências:

./mvnw clean install        # Linux/Mac
mvnw.cmd clean install      # Windows

6. Execução do Projeto
Via Maven Wrapper (Recomendado)
./mvnw spring-boot:run

Via Maven
mvn spring-boot:run

Via JAR
./mvnw clean package
java -jar target/comy-delivery-back-0.0.1-SNAPSHOT.jar

Via IDE

Execute a classe:

ComyDeliveryBackApplication.java

7. Endpoints e Acesso

Base API: http://localhost:8084

Documentação Swagger: http://localhost:8084/swagger-ui.html

Health Check: http://localhost:8084/api/health

8. Estrutura do Código
src/main/java/com/comy_delivery_back/
├── client/                # Integrações externas
├── configuration/         # CORS, Swagger, beans globais
├── controller/            # Endpoints REST
├── dto/                   # Request/Response DTOs
├── enums/                 # Tipos e estados
├── exception/             # Exceções customizadas
├── model/                 # Entidades JPA
├── repository/            # Repositórios JPA
├── scheduler/             # Tarefas agendadas
├── security/              # Configuração de autenticação
├── service/               # Regras de negócio
└── utils/                 # Funções auxiliares

9. Funcionalidades
Usuários

Cadastro e autenticação

Recuperação de senha

Desativação de conta (soft delete)

Restaurantes

Catálogo de produtos

Promoções

Horários de funcionamento

Dashboard gerencial

Pedidos

Múltiplos itens e adicionais

Cupons de desconto

Cálculo automático de frete por distância

Fluxo completo de status

Entregas

Atribuição automática de entregador

Status em tempo real

Dashboard do entregador

Endereços

Busca de CEP via AwesomeAPI

Conversão para coordenadas

Cálculo de distância (Haversine)

10. Documentação da API

Documentação gerada automaticamente:

🔗 http://localhost:8084/swagger-ui.html

11. Variáveis de Ambiente – Tabela
Variável	Descrição	Obrigatório	Padrão
DATABASE_URL	URL do PostgreSQL	Sim	—
DATABASE_USERNAME	Usuário do banco	Sim	—
DATABASE_PASSWORD	Senha do banco	Sim	—
EMAIL_SENDER	E-mail remetente	Sim	—
SENHA_EMAIL_SENDER	Senha de App Gmail	Sim	—
AWESOMEAPI_KEY	API CEP	Sim	—
FRONTEND_URL	URL do Frontend	Não	http://localhost:4200

BACKEND_URL	URL do Backend	Não	http://localhost:8084

PASSWORD_RECOVERY_URL	URL de reset de senha	Não	http://localhost:8084/reset-password
12. Banco de Dados
Inicialização

Hibernate ddl-auto=update

Dados iniciais em data.sql

Usuários iniciais
Tipo	Usuário	Senha
Admin	admin_master	SenhaForte123
Restaurante	pizzaria_top	SenhaForte123
Cliente	cliente_joao	SenhaForte123
Entregador	driver_carlos	SenhaForte123
13. Testes da API
cURL
curl http://localhost:8084/api/health
curl http://localhost:8084/api/restaurante/2

Postman / Insomnia

Importe diretamente a documentação Swagger.

14. Troubleshooting
Erro: Connection refused (Banco)

Verifique se o PostgreSQL está em execução e se as credenciais estão corretas.

Erro: AuthenticationFailedException (Email)

Gere nova senha de App no Gmail.

Erro: Port 8084 already in use

Altere a porta:

server.port=8085

15. Equipe

Arthur

Emilio

Heloisa

Jude

Sinara

Contato: dev.hmtvrs@gmail.com
