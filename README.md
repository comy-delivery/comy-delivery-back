🍕 Comy Delivery – Backend










Aplicação backend do sistema Comy Delivery, desenvolvida com Spring Boot, oferecendo APIs REST completas para gestão de clientes, restaurantes, entregadores, pedidos e entregas.

Sobre • Tecnologias • Instalação • Como Rodar • Funcionalidades • Equipe

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

Banco de Dados

Testando a API

Troubleshooting

Equipe de Desenvolvimento

📌 Sobre o Projeto

O Comy Delivery – Backend é uma plataforma robusta que gerencia toda a lógica de negócios do sistema Comy Delivery.
O backend fornece:

Autenticação e autorização

Gestão de usuários (clientes, restaurantes, entregadores, admins)

Gestão de restaurantes e produtos

Sistema de pedidos completo

Rastreamento de entregas

Sistema de cupons

Avaliações

Notificações por e-mail (recuperação de senha)

Integração externa com API de CEP

🛠️ Tecnologias Utilizadas

Java 21

Spring Boot 3.5.7

Spring Data JPA

Spring Validation

Spring Security (estrutura)

OpenFeign (CEP)

Lombok

PostgreSQL

Swagger / OpenAPI

JavaMailSender

Maven

🧰 Pré-requisitos

Java 21+

Maven 3.9+

PostgreSQL 14+

Git

IDE (IntelliJ recomendado)

⚙️ Instalação e Configuração
1. Clone o repositório
git clone https://github.com/seu-usuario/comy-delivery-back.git
cd comy-delivery-back

2. Configure o banco de dados
CREATE DATABASE comy_delivery;

3. Configure as variáveis de ambiente / .env
DATABASE_URL=jdbc:postgresql://localhost:5432/comy_delivery
DATABASE_USERNAME=seu_usuario
DATABASE_PASSWORD=sua_senha

EMAIL_SENDER=seu_email@gmail.com
SENHA_EMAIL_SENDER=sua_senha_app

AWESOMEAPI_KEY=sua_chave

FRONTEND_URL=http://localhost:4200
BACKEND_URL=http://localhost:8084
PASSWORD_RECOVERY_URL=http://localhost:4200/reset-password

4. Instalar dependências
./mvnw clean install

▶️ Como Rodar o Projeto
Via Maven Wrapper
./mvnw spring-boot:run

Via Maven
mvn spring-boot:run

Via JAR
./mvnw clean package
java -jar target/comy-delivery-back-0.0.1-SNAPSHOT.jar

📂 Estrutura do Projeto
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

🧩 Funcionalidades
Usuários

Cadastro e autenticação

Recuperação de senha por e-mail

Perfis: cliente, restaurante, entregador e admin

Restaurantes

Cadastro e atualização

Catálogo de produtos

Promoções

Horários de funcionamento

Dashboard

Pedidos

Criação de pedido com múltiplos itens

Adicionais

Cupons

Fluxo completo de status

Dashboard para restaurante

Entregas

Atribuição automática

Rastreio em tempo real

Dashboard do entregador

Avaliações

Comentários

Nota do restaurante

Nota do entregador

Endereços

Busca por CEP via AwesomeAPI

Cálculo de distância

📘 Documentação da API

Swagger disponível em:

🔗 http://localhost:8084/swagger-ui.html

🔐 Variáveis de Ambiente
Variável	Descrição	Obrigatório
DATABASE_URL	URL do PostgreSQL	✔️
DATABASE_USERNAME	Usuário	✔️
DATABASE_PASSWORD	Senha	✔️
EMAIL_SENDER	Gmail remetente	✔️
SENHA_EMAIL_SENDER	Senha de app	✔️
AWESOMEAPI_KEY	Chave da API CEP	✔️
FRONTEND_URL	URL do frontend	❌
BACKEND_URL	URL do backend	❌
PASSWORD_RECOVERY_URL	Link para reset	❌
🗄️ Banco de Dados
Hibernate

ddl-auto=update

Dados iniciais (seed)

Usuários padrões:

Tipo	Login	Senha
Admin	admin_master	SenhaForte123
Restaurante	pizzaria_top	SenhaForte123
Cliente	cliente_joao	SenhaForte123
Entregador	driver_carlos	SenhaForte123
🔍 Testando a API
cURL
curl http://localhost:8084/api/health

Postman / Insomnia

Importe o Swagger.

🐛 Troubleshooting
Banco não conecta

Verifique se PostgreSQL está rodando

Confira usuário e senha

Erro ao enviar e-mail

Ative verificação em duas etapas

Gere senha de app

Porta em uso
server.port=8085

👥 Equipe de Desenvolvimento
Integrante	GitHub
Arthur	https://github.com/Thurrrr

Emilio	https://github.com/emilioaugusto

Heloisa	https://github.com/helomt

Jude	https://github.com/judevieira

Sinara	https://github.com/sinara-santinoni
