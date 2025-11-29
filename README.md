Comy Delivery – Backend
Plataforma de Delivery • Arquitetura Moderna • APIs Corporativas
<br> <p align="center"> <img src="https://img.shields.io/badge/Java-21-EC2025?style=for-the-badge&logo=java&logoColor=white" /> <img src="https://img.shields.io/badge/Spring_Boot-3.5.7-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" /> <img src="https://img.shields.io/badge/PostgreSQL-14+-4169E1?style=for-the-badge&logo=postgresql&logoColor=white" /> <img src="https://img.shields.io/badge/Maven-3.9+-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" /> <img src="https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?style=for-the-badge&logo=swagger&logoColor=black" /> </p> <br>

Aplicação backend do Comy Delivery, construída com padrões empresariais, arquitetura modular e foco em robustez, escalabilidade e segurança.
Responsável por todas as APIs REST da plataforma – incluindo usuários, pedidos, entregas, avaliações, cupons e integração externa.

Índice

Visão Geral

Arquitetura

Tecnologias

Requisitos

Configuração

Execução

Estrutura do Código

Principais Domínios

Documentação da API

Variáveis de Ambiente

Dados Iniciais

Testes de API

Suporte e Troubleshooting

Equipe

Visão Geral

O backend do Comy Delivery foi desenvolvido com foco em:

Disponibilidade – APIs responsivas e independentes

Escalabilidade – arquitetura modular orientada a domínio

Segurança – senhas criptografadas com BCrypt, camadas isoladas e boas práticas

Integridade dos dados – validação avançada e regras de negócio consolidadas

Integração externa – consumo de APIs e cálculo de distâncias

A solução implementa toda a regra de negócios necessária para sustentar o ecossistema de delivery composto por:

◼ Clientes
◼ Restaurantes
◼ Entregadores
◼ Administradores

Arquitetura

✔ Arquitetura em camadas
✔ DTO Pattern
✔ Services isolados
✔ Spring Data JPA com repositórios inteligentes
✔ Integração via Feign Client
✔ Documentação automática via OpenAPI
✔ Validação backend-first

Controller → Service → Repository → Model → Database

Tecnologias

Java 21

Spring Boot 3.5.7

PostgreSQL

Spring Data JPA

Spring Validation

Lombok

JavaMailSender

OpenFeign

Swagger / OpenAPI

Maven

Requisitos

Java 21+

Maven 3.9+

PostgreSQL 14+

Git

IDE (IntelliJ recomendado)

Configuração
Banco de Dados
CREATE DATABASE comy_delivery;

Variáveis de Ambiente

Crie um .env com:

DATABASE_URL=jdbc:postgresql://localhost:5432/comy_delivery
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=postgres

EMAIL_SENDER=seuemail@gmail.com
SENHA_EMAIL_SENDER=sua_senha_app

AWESOMEAPI_KEY=sua_chave_cep

FRONTEND_URL=http://localhost:4200
BACKEND_URL=http://localhost:8084
PASSWORD_RECOVERY_URL=http://localhost:4200/reset-password

Execução
Maven Wrapper (Recomendado)
./mvnw spring-boot:run

JAR
./mvnw clean package
java -jar target/comy-delivery-back.jar

IDE

Executar classe:

ComyDeliveryBackApplication.java

Estrutura do Código
src/main/java/com/comy_delivery_back/
├── client/
├── configuration/
├── controller/
├── dto/
│   ├── request/
│   └── response/
├── enums/
├── exception/
├── model/
├── repository/
├── scheduler/
├── security/
├── service/
└── utils/

Principais Domínios
Usuários

Cadastro e autenticação

Recuperação de senha

Perfis: Cliente, Restaurante, Entregador, Admin

Restaurantes

Cadastro completo

Catálogo de produtos

Promoções

Gestão operacional

Pedidos

Criação de pedido

Adicionais e cupons

Fluxo completo de status

Dashboard

Entregas

Atribuição automática

Rastreamento por status

Dashboard do entregador

Avaliações

Nota do restaurante

Nota do entregador

Endereços

CEP → Coordenadas

Cálculo de distância (Haversine)

Documentação da API

A interface de documentação está disponível em:

📄 http://localhost:8084/swagger-ui.html

Variáveis de Ambiente
Variável	Descrição	Obrigatória
DATABASE_URL	URL do PostgreSQL	✔
DATABASE_USERNAME	Usuário	✔
DATABASE_PASSWORD	Senha	✔
EMAIL_SENDER	Gmail remetente	✔
SENHA_EMAIL_SENDER	Senha de App	✔
AWESOMEAPI_KEY	API CEP	✔
FRONTEND_URL	URL do Frontend	✖
BACKEND_URL	URL do Backend	✖
PASSWORD_RECOVERY_URL	Reset de senha	✖
Dados Iniciais

Usuários gerados automaticamente:

Perfil	Login	Senha
Admin	admin_master	SenhaForte123
Restaurante	pizzaria_top	SenhaForte123
Cliente	cliente_joao	SenhaForte123
Entregador	driver_carlos	SenhaForte123
Testes de API
Health Check
curl http://localhost:8084/api/health

Swagger

Importar a collection diretamente do Swagger UI.

Suporte e Troubleshooting
Conexão negada ao banco

Verificar serviço PostgreSQL

Conferir credenciais

Erro ao enviar e-mail

Verificação em duas etapas

Criar senha de app

Porta em uso
server.port=8085

Equipe de Desenvolvimento
Integrante	GitHub
Arthur	https://github.com/Thurrrr

Emilio	https://github.com/emilioaugusto

Heloisa	https://github.com/helomt

Jude	https://github.com/judevieira

Sinara	https://github.com/sinara-santinoni
