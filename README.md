```markdown
# 🎓 SIGA - Sistema de Gestão Acadêmica

**Versão Alpha 0.1**

[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.6-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18.4-blue.svg)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

---

## 📋 Índice

- [Visão Geral](#visão-geral-do-sistema)
- [Requisitos do Sistema](#requisitos-do-sistema)
- [Arquitetura](#arquitetura-de-software-e-camadas)
- [Modelagem de Dados](#modelagem-de-dados-entidades)
- [Tecnologias](#tecnologias)
- [API Endpoints](#especificação-da-api-endpoints-principais)
- [Como Executar](#como-executar)
- [Testes da API](#testes-da-api)
- [Erros Comuns](#erros-comuns-no-desenvolvimento-e-como-evitá-los)
- [Roadmap](#roadmap-próximos-passos)
- [Contribuição](#contribuição)
- [Licença](#licença)

---

## 📚 Visão Geral do Sistema

O **SIGA (Sistema de Gestão Acadêmica)** é uma API REST desenvolvida para centralizar, automatizar e simplificar as operações diárias de uma instituição de ensino. O sistema gerencia desde o controle de acesso de diferentes níveis de usuários até o ciclo de vida acadêmico do estudante (matrículas, notas, turmas e frequências).

### 🎯 Objetivos

- Automatizar o processo de gestão acadêmica
- Fornecer uma API REST segura e documentada
- Garantir escalabilidade e manutenibilidade
- Oferecer uma base sólida para futuras expansões

---

## 📋 Requisitos do Sistema

### 📌 Requisitos Funcionais (RF)

| ID | Descrição |
|----|-----------|
| **RF-001** | O sistema deve permitir o login de usuários gerando um token JWT contendo suas permissões (Roles) |
| **RF-002** | O administrador deve ser capaz de cadastrar Professores e Alunos. O sistema deve criar automaticamente as credenciais de acesso vinculadas a eles |
| **RF-003** | O sistema deve permitir o mapeamento de Cursos e suas respectivas Disciplinas com carga horária |
| **RF-004** | O sistema deve permitir criar Turmas para um período letivo, vinculando uma Disciplina a um Professor |
| **RF-005** | O sistema deve permitir a inscrição de Alunos em Turmas ativas |
| **RF-006** | O professor da turma deve conseguir lançar notas (Avaliações) e registrar faltas (Frequência) dos alunos matriculados na sua turma |
| **RF-007** | O aluno deve ser capaz de visualizar suas notas, médias e situação final em cada disciplina cursada |

### ⚙️ Requisitos Não-Funcionais (RNF)

| ID | Descrição |
|----|-----------|
| **RNF-001** | Senhas devem ser obrigatoriamente criptografadas utilizando o algoritmo **BCrypt** antes de persistidas no banco de dados |
| **RNF-002** | A API deve ser estritamente RESTful, utilizando os verbos HTTP corretos e retornando códigos de status padronizados |
| **RNF-003** | Utilização do Spring Data JPA com estratégias de carregamento preguiçoso (*Lazy Loading*) para evitar consultas desnecessárias ao banco de dados |
| **RNF-004** | O projeto deve conter um `Dockerfile` configurado para facilitar o build multi-stage e deploy automatizado |

---

## 🏗️ Arquitetura de Software e Camadas

A aplicação segue a arquitetura padrão de mercado baseada em camadas especializadas, garantindo o princípio da **Responsabilidade Única (SOLID)**:

```
[Client: React/Postman] 
       │ (Requisição HTTP + Bearer Token)
       ▼
[Controller Layer] ──► Valida a entrada com @Valid (DTOs)
       │
       ▼
[Service Layer] ───► Onde reside a Regra de Negócio (Validações, Cálculos de Média)
       │
       ▼
[Repository Layer] ─► Interfaces que estendem JpaRepository (Consultas ao Banco)
       │
       ▼
[Database: PostgreSQL]

```

### Estrutura do Projeto

```
src/main/java/com/backend/siga/
├── config/                     # Configurações globais
│   ├── SecurityConfig.java    # Configuração de segurança
│   ├── JwtUtil.java           # Utilitário JWT
│   ├── JwtAuthenticationFilter.java # Filtro de autenticação
│   └── WebConfig.java         # Configuração CORS
├── features/                   # Features do sistema
│   ├── gestao_academica/       # Feature: Gestão Acadêmica
│   │   ├── controller/        # Endpoints REST
│   │   ├── service/           # Regras de negócio
│   │   ├── repository/        # Acesso a dados
│   │   ├── model/             # Entidades JPA
│   │   └── dto/               # Objetos de transferência
│   └── gestao_usuarios/        # Feature: Gestão de Usuários
│       ├── controller/        # Endpoints REST
│       ├── service/           # Regras de negócio
│       ├── repository/        # Acesso a dados
│       ├── model/             # Entidades JPA
│       └── dto/               # Objetos de transferência
└── SigaApplication.java        # Ponto de entrada
```

---

## 📊 Modelagem de Dados (Entidades)

Para garantir performance e isolamento de escopo, separamos a entidade de autenticação das entidades de negócio.

```
       ┌────────────────┐
       │    Usuario     │ (Id, Email, Senha, Role)
       └───────┬────────┘
               │
      ┌────────┴────────┐
      │1                │1
┌─────▼─────┐     ┌─────▼─────┐
│  Professor│     │   Aluno   │
└─────┬─────┘     └─────┬─────┘
      │1                │*
      │                 │
      │*                │*
┌─────▼─────┐     ┌─────▼──────────┐
│   Turma   │◄────┤ MatriculaTurma │ (Notas, Frequência, Status)
└─────▲─────┘     └────────────────┘
      │*
      │1
┌─────┴─────┐
│ Disciplina│
└─────▲─────┘
      │*
      │1
┌─────┴─────┐
│   Curso   │
└───────────┘
```

---

## 🚀 Tecnologias

### Backend
- **Java 21** - Linguagem de programação
- **Spring Boot 4.0.6** - Framework principal
- **Spring Security** - Autenticação e autorização
- **Spring Data JPA** - ORM para banco de dados
- **JJWT 0.12.5** - Tokens de autenticação
- **Lombok** - Redução de código boilerplate

### Banco de Dados
- **PostgreSQL 18.4** - Banco de dados relacional
- **Hibernate** - Mapeamento objeto-relacional

### Ferramentas
- **Maven** - Gerenciador de dependências
- **Git** - Controle de versão
- **Docker** - Containerização (opcional)

---

## 📡 Especificação da API (Endpoints Principais)

Todos os endpoints (exceto o de login) exigem o cabeçalho `Authorization: Bearer <TOKEN>`.

### 🔐 Autenticação (`/api/auth`)

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| POST | `/login` | Autentica um usuário e retorna o token JWT | ❌ |
| POST | `/register` | Registrar novo usuário | ❌ |

### 👨‍🎓 Alunos (`/api/alunos`)

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| POST | `/api/alunos` | Cadastra um novo aluno | ✅ |
| GET | `/api/alunos` | Lista todos os alunos | ✅ |
| GET | `/api/alunos/{id}` | Busca aluno por ID | ✅ |
| GET | `/api/alunos/email/{email}` | Busca aluno por email | ✅ |
| GET | `/api/alunos/bi/{bi}` | Busca aluno por BI | ✅ |
| PATCH | `/api/alunos/{id}/inativar` | Inativa um aluno | ✅ |
| PATCH | `/api/alunos/{id}/ativar` | Ativa um aluno | ✅ |

### 📚 Turmas (`/api/turmas`)

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| POST | `/api/turmas` | Cria uma nova turma | ✅ |
| GET | `/api/turmas` | Lista todas as turmas | ✅ |
| GET | `/api/turmas/{id}` | Busca turma por ID | ✅ |
| GET | `/api/turmas/professor/{professor}` | Busca turmas por professor | ✅ |
| GET | `/api/turmas/periodo` | Busca turmas por período | ✅ |

### 📝 Matrículas

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| POST | `/api/alunos/{alunoId}/matricular/{turmaId}` | Matricula aluno em turma | ✅ |
| DELETE | `/api/alunos/{alunoId}/desmatricular/{turmaId}` | Desmatricula aluno da turma | ✅ |

---

## 🔧 Como Executar

### Pré-requisitos

```bash
# Java 21+
java -version

# PostgreSQL 15+
psql --version

# Maven 3.6+
mvn --version
```

### Passos

#### 1. Clonar o repositório

```bash
git clone https://github.com/SEU_USUARIO/siga.git
cd siga
```

#### 2. Criar banco de dados

```bash
# Substitua wesley pelo seu usuário
psql -U wesley -d postgres -c "CREATE DATABASE siga_db;"
```

#### 3. Configurar application.properties

```properties
# src/main/resources/application.properties
spring.application.name=siga
server.port=8080

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/siga_db
spring.datasource.username=wesley
spring.datasource.password=

# Disable Docker Compose
spring.docker.compose.enabled=false

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true

# JWT
jwt.secret=chaveSecretaSuperSegura123456789
jwt.expiration=86400000

# Logging
logging.level.com.backend.siga=DEBUG
```

#### 4. Compilar e executar

```bash
# Compilar
mvn clean compile

# Executar
mvn spring-boot:run
```

#### 5. Credenciais padrão

| Perfil | Username | Password |
|--------|----------|----------|
| Admin | admin | admin123 |
| Aluno | aluno | aluno123 |

---

## 🧪 Testes da API

### 1. Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "username": "admin",
  "nome": "Administrador",
  "role": "ADMIN",
  "alunoId": null
}
```

### 2. Criar Aluno

```bash
curl -X POST http://localhost:8080/api/alunos \
  -H "Authorization: Bearer SEU_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria Tembe",
    "email": "maria@email.com",
    "bi": "AB1234567C",
    "dataNascimento": "1998-05-20",
    "telefone": "821234567",
    "endereco": "Rua 1, Maputo",
    "provincia": "Maputo"
  }'
```

### 3. Listar Alunos

```bash
curl -X GET http://localhost:8080/api/alunos \
  -H "Authorization: Bearer SEU_TOKEN"
```

### 4. Criar Turma

```bash
curl -X POST "http://localhost:8080/api/turmas?disciplina=Matematica&professor=Prof.Armando&ano=2024&semestre=1&vagas=30" \
  -H "Authorization: Bearer SEU_TOKEN"
```

### 5. Matricular Aluno

```bash
curl -X POST "http://localhost:8080/api/alunos/{alunoId}/matricular/{turmaId}" \
  -H "Authorization: Bearer SEU_TOKEN"
```

---

## 🚨 Erros Comuns no Desenvolvimento e Como Evitá-los

### 1. Problema do Loop Infinito no JSON

**Problema:** Como `Aluno` tem `Usuario` e `Usuario` pode fazer referência reversa, se você retornar a Entidade diretamente no Controller, o Jackson entrará em loop infinito.

**Solução:** Use DTOs. Nunca retorne a entidade mapeada do banco diretamente no Controller.

```java
// ❌ ERRADO - Retorna entidade diretamente
@GetMapping("/{id}")
public Aluno buscar(@PathVariable String id) {
    return alunoRepository.findById(id).orElseThrow();
}

// ✅ CORRETO - Usa DTO
@GetMapping("/{id}")
public AlunoResponse buscar(@PathVariable String id) {
    Aluno aluno = alunoRepository.findById(id).orElseThrow();
    return AlunoResponse.from(aluno);
}
```

### 2. Vazamento de Senhas

**Problema:** Retornar o hash da senha do usuário em requisições de listagem.

**Solução:** No DTO de resposta de usuários, nunca mapeie ou inclua o campo `senha`.

```java
// ❌ ERRADO - Inclui senha
public class UsuarioResponse {
    private String password; // NUNCA!
}

// ✅ CORRETO - Sem senha
public class UsuarioResponse {
    private String id;
    private String username;
    private String email;
    private String role;
}
```

### 3. Configuração Incorreta de CORS

**Problema:** Permitir `*` (qualquer origem) em produção ou esquecer de liberar os métodos `OPTIONS`.

**Solução:** Criar uma classe `@Configuration` dedicada ao CORS.

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("https://meu-frontend.com")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
}
```

---

## 🗺️ Roadmap - Próximos Passos

### Versão Alpha 0.2 (Próxima)
- [ ] Adicionar validações com Bean Validation
- [ ] Implementar tratamento de erros global
- [ ] Adicionar logs com SLF4J
- [ ] Criar testes unitários (JUnit 5)
- [ ] Documentação Swagger/OpenAPI

### Versão Alpha 0.3
- [ ] Gestão de notas dos alunos
- [ ] Frequência dos alunos
- [ ] Histórico acadêmico
- [ ] Relatórios básicos

### Versão Beta 1.0
- [ ] Frontend em React
- [ ] Dashboard administrativo
- [ ] Área do aluno
- [ ] Área do professor

---

## 🤝 Contribuição

1. Faça um fork do projeto
2. Crie sua branch: `git checkout -b feature/minha-feature`
3. Commit suas mudanças: `git commit -m 'feat: Minha nova feature'`
4. Push: `git push origin feature/minha-feature`
5. Abra um Pull Request

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👨‍💻 Autor

**Wesley** - [GitHub](https://github.com/SEU_USUARIO)

---

⭐️ **Se este projeto te ajudou, não esqueça de dar uma estrela!**

---

**📅 Última atualização:** Julho 2026
**📌 Versão:** Alpha 0.1
```
