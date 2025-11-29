Comy Delivery - Backend
Sistema de delivery de comida desenvolvido com Spring Boot, oferecendo APIs REST completas para gerenciamento de restaurantes, pedidos, entregas e clientes.
📋 Índice

Sobre o Projeto
Tecnologias Utilizadas
Pré-requisitos
Instalação e Configuração
Como Rodar o Projeto
Estrutura do Projeto
Funcionalidades Principais
Documentação da API
Variáveis de Ambiente
Equipe de Desenvolvimento

🎯 Sobre o Projeto
O Comy Delivery é uma plataforma completa de delivery que conecta restaurantes, clientes e entregadores. O sistema oferece funcionalidades como gestão de pedidos em tempo real, cálculo automático de frete baseado em distância, sistema de cupons, avaliações e recuperação de senha por e-mail.
🚀 Tecnologias Utilizadas

Java 21
Spring Boot 3.5.7
Spring Data JPA
Spring Validation
Spring Cloud OpenFeign (integração com API de CEP)
PostgreSQL (banco de dados)
Lombok (redução de boilerplate)
BCrypt (criptografia de senhas)
JavaMailSender (envio de e-mails)
Springdoc OpenAPI (documentação Swagger)
Maven (gerenciamento de dependências)

📦 Pré-requisitos
Antes de começar, certifique-se de ter instalado:

Java 21 ou superior (Download)
Maven 3.9+ (ou use o Maven Wrapper incluído no projeto)
PostgreSQL 14+ (Download)
Git (Download)
IDE de sua preferência (IntelliJ IDEA, Eclipse, VS Code)

⚙️ Instalação e Configuração
1. Clone o Repositório
bashgit clone https://github.com/seu-usuario/comy-delivery-back.git
cd comy-delivery-back
2. Configure o Banco de Dados
Crie um banco de dados PostgreSQL:
sqlCREATE DATABASE comy_delivery;
3. Configure as Variáveis de Ambiente
Crie um arquivo .env na raiz do projeto ou configure as variáveis de ambiente no seu sistema:
properties# Banco de Dados
DATABASE_URL=jdbc:postgresql://localhost:5432/comy_delivery
DATABASE_USERNAME=seu_usuario
DATABASE_PASSWORD=sua_senha

# E-mail (Gmail)
EMAIL_SENDER=seu_email@gmail.com
SENHA_EMAIL_SENDER=sua_senha_app

# API Externa (AwesomeAPI - CEP)
AWESOMEAPI_KEY=sua_chave_api

# URLs (Opcional - valores padrão)
FRONTEND_URL=http://localhost:4200
BACKEND_URL=http://localhost:8084
PASSWORD_RECOVERY_URL=http://localhost:4200/reset-password?
📧 Configuração do Gmail
Para usar o envio de e-mails via Gmail, você precisa gerar uma Senha de App:

Acesse Conta Google
Vá em Segurança → Verificação em duas etapas (ative se ainda não estiver)
Em Senhas de app, gere uma nova senha
Use essa senha na variável SENHA_EMAIL_SENDER

4. Instale as Dependências
bash# Linux/Mac
./mvnw clean install

# Windows
mvnw.cmd clean install
▶️ Como Rodar o Projeto
Opção 1: Usando Maven Wrapper (Recomendado)
bash# Linux/Mac
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
Opção 2: Usando Maven Instalado
bashmvn spring-boot:run
Opção 3: Rodando o JAR
bash# Primeiro, gere o JAR
./mvnw clean package

# Depois execute
java -jar target/comy-delivery-back-0.0.1-SNAPSHOT.jar
```

### Opção 4: Pela IDE

1. Abra o projeto na sua IDE
2. Localize a classe `ComyDeliveryBackApplication.java`
3. Clique com o botão direito → **Run 'ComyDeliveryBackApplication'**

## 🌐 Acessando a Aplicação

Após iniciar o servidor, a aplicação estará disponível em:

- **API Base:** `http://localhost:8084`
- **Swagger UI:** `http://localhost:8084/swagger-ui.html`
- **Health Check:** `http://localhost:8084/api/health`

## 📁 Estrutura do Projeto
```
src/main/java/com/comy_delivery_back/
├── client/              # Clientes Feign (APIs externas)
├── configuration/       # Configurações (CORS, Async, Swagger)
├── controller/          # Controllers REST
├── dto/                 # DTOs (Request/Response)
│   ├── request/
│   └── response/
├── enums/               # Enumerações (Status, Tipos, Categorias)
├── exception/           # Exceções customizadas
├── model/               # Entidades JPA
├── repository/          # Repositórios Spring Data
├── scheduler/           # Tarefas agendadas
├── security/            # Configurações de segurança
├── service/             # Lógica de negócio
└── utils/               # Classes utilitárias
🎯 Funcionalidades Principais
👥 Gestão de Usuários

✅ Cadastro e autenticação de Clientes, Restaurantes, Entregadores e Admins
✅ Recuperação de senha por e-mail
✅ Soft delete (desativação de contas)

🍕 Restaurantes

✅ Cadastro com imagens (logo e banner)
✅ Gestão de horários de funcionamento
✅ Sistema de abertura/fechamento automático
✅ Catálogo de produtos com categorias
✅ Sistema de promoções

🛍️ Pedidos

✅ Criação de pedidos com múltiplos itens
✅ Adicionais personalizáveis por item
✅ Aplicação de cupons de desconto
✅ Cálculo automático de frete baseado em distância
✅ Fluxo completo de status (Pendente → Confirmado → Em Preparo → Pronto → Saiu para Entrega → Entregue)
✅ Sistema de aceitação/recusa de pedidos

🚚 Entregas

✅ Atribuição automática de entregadores
✅ Rastreamento de status em tempo real
✅ Cálculo de tempo estimado de entrega
✅ Dashboard de performance para entregadores

🎟️ Cupons

✅ Cupons de valor fixo e percentual
✅ Validação automática de validade e limite de uso
✅ Requisito de valor mínimo de pedido

⭐ Avaliações

✅ Sistema de avaliação de restaurantes
✅ Avaliação de entregadores
✅ Cálculo automático de média de avaliações

📍 Endereços

✅ Integração com API de CEP (AwesomeAPI)
✅ Busca automática de coordenadas
✅ Cálculo de distância (Fórmula de Haversine)
✅ Gestão de múltiplos endereços por usuário

📖 Documentação da API
A documentação completa da API está disponível via Swagger UI:
URL: http://localhost:8084/swagger-ui.html
Principais Endpoints
Restaurantes

POST /api/restaurante - Cadastrar restaurante
GET /api/restaurante/{id} - Buscar por ID
GET /api/restaurante/abertos - Listar restaurantes abertos
PUT /api/restaurante/{id} - Atualizar dados

Clientes

POST /api/cliente - Cadastrar cliente
GET /api/cliente/{id} - Buscar por ID
POST /api/cliente/recuperar-senha - Solicitar recuperação de senha
GET /api/cliente/{id}/restaurantes-distancia - Listar restaurantes por distância

Pedidos

POST /api/pedido - Criar pedido
GET /api/pedido/{id} - Buscar por ID
PATCH /api/pedido/{id}/aceitar - Aceitar/recusar pedido
PATCH /api/pedido/{id}/status - Atualizar status
GET /api/pedido/restaurante/{id}/dashboard - Dashboard do restaurante

Entregas

POST /api/entregas - Cadastrar entrega
PATCH /api/entregas/{id} - Atualizar status
GET /api/entregas/entregador/{id}/dashboard - Dashboard do entregador

🔐 Variáveis de Ambiente
VariávelDescriçãoObrigatórioPadrãoDATABASE_URLURL do banco PostgreSQL✅-DATABASE_USERNAMEUsuário do banco✅-DATABASE_PASSWORDSenha do banco✅-EMAIL_SENDERE-mail remetente✅-SENHA_EMAIL_SENDERSenha de app do Gmail✅-AWESOMEAPI_KEYChave da API de CEP✅-FRONTEND_URLURL do frontend❌http://localhost:4200BACKEND_URLURL do backend❌http://localhost:8084PASSWORD_RECOVERY_URLURL de recuperação de senha❌http://localhost:8084/reset-password
🗄️ Banco de Dados
Inicialização Automática
O projeto utiliza:

Hibernate DDL Auto: update (cria/atualiza tabelas automaticamente)
Data.sql: Arquivo com dados iniciais (usuários, produtos, endereços de exemplo)

Dados Iniciais (Seed)
Após a primeira execução, o sistema cria automaticamente:

1 Admin (admin_master / SenhaForte123)
1 Restaurante (pizzaria_top / SenhaForte123)
1 Cliente (cliente_joao / SenhaForte123)
1 Entregador (motoboy_carlos / SenhaForte123)
Produtos, endereços e um pedido de exemplo

🧪 Testando a API
Usando cURL
bash# Health Check
curl http://localhost:8084/api/health

# Buscar restaurante por ID
curl http://localhost:8084/api/restaurante/2
```

### Usando Postman/Insomnia

Importe a collection do Swagger ou acesse diretamente os endpoints documentados.

## 🐛 Troubleshooting

### Erro de Conexão com o Banco
```
org.postgresql.util.PSQLException: Connection refused
```

**Solução:** Verifique se o PostgreSQL está rodando e se as credenciais estão corretas.

### Erro ao Enviar E-mail
```
AuthenticationFailedException
```

**Solução:** 
1. Verifique se a verificação em duas etapas está ativada no Gmail
2. Gere uma nova Senha de App
3. Use essa senha na variável `SENHA_EMAIL_SENDER`

### Porta 8084 já em uso
```
Port 8084 was already in use
Solução: Altere a porta no application.properties:
propertiesserver.port=8085
📝 Scripts Úteis
bash# Limpar e compilar
./mvnw clean compile

# Rodar testes
./mvnw test

# Gerar JAR sem testes
./mvnw clean package -DskipTests

# Ver dependências
./mvnw dependency:tree
👥 Equipe de Desenvolvimento

Arthur
Emilio
Heloisa
Jude
Sinara

Contato: dev.hmtvrs@gmail.com
📄 Licença
Este projeto é de propriedade da equipe Comy Delivery.
