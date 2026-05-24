
 SIGA

## 1. Visão Geral do Sistema

O **SIGA (Sistema de Gestão Acadêmica)** é uma API REST desenvolvida para centralizar, automatizar e simplificar as operações diárias de uma instituição de ensino. O sistema gerencia desde o controle de acesso de diferentes níveis de usuários até o ciclo de vida acadêmico do estudante (matrículas, notas, turmas e frequências).

---

## 2. Requisitos do Sistema

### 📌 Requisitos Funcionais (RF)

Os requisitos funcionais definem o que o sistema *deve fazer*.

* **RF-001 (Autenticação):** O sistema deve permitir o login de usuários gerando um token JWT contendo suas permissões (*Roles*).
* **RF-002 (Gestão de Usuários):** O administrador deve ser capaz de cadastrar Professores e Alunos. O sistema deve criar automaticamente as credenciais de acesso vinculadas a eles.
* **RF-003 (Gestão de Cursos e Disciplinas):** O sistema deve permitir o mapeamento de Cursos e suas respectivas Disciplinas com carga horária.
* **RF-004 (Alocação de Turmas):** O sistema deve permitir criar Turmas para um período letivo, vinculando uma Disciplina a um Professor.
* **RF-005 (Matrícula):** O sistema deve permitir a inscrição de Alunos em Turmas ativas.
* **RF-006 (Lançamento Acadêmico):** O professor da turma deve conseguir lançar notas (Avaliações) e registrar faltas (Frequência) dos alunos matriculados na sua turma.
* **RF-007 (Boletim/Histórico):** O aluno deve ser capaz de visualizar suas notas, médias e situação final em cada disciplina instalada.

### ⚙️ Requisitos Não-Funcionais (RNF)

Os requisitos não-funcionais definem *como* o sistema deve operar.

* **RNF-001 (Segurança):** Senhas devem ser obrigatoriamente criptografadas utilizando o algoritmo **BCrypt** antes de persistidas no banco de dados.
* **RNF-002 (Arquitetura da API):** A API deve ser estritamente RESTful, utilizando os verbos HTTP corretos (`GET`, `POST`, `PUT`, `DELETE`) e retornando códigos de status padronizados (`200 OK`, `201 Created`, `400 Bad Request`, `403 Forbidden`, `404 Not Found`).
* **RNF-003 (Performance/Persistência):** Utilização do Spring Data JPA com estratégias de carregamento preguiçoso (*Lazy Loading*) para evitar consultas desnecessárias ao banco de dados.
* **RNF-004 (Portabilidade/Deploy):** O projeto deve conter um `Dockerfile` configurado para facilitar o build multi-stage e deploy automatizado em plataformas como **Render** ou **Railway**.

---

## 3. Arquitetura de Software e Camadas

A aplicação segue a arquitetura padrão de mercado baseada em camadas especializadas, garantindo o princípio da **Responsabilidade Única (SOLID)**:

```
[Client: React] 
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
[Database: PostgreSQL/MySQL]

```

---

## 4. Modelagem de Dados (Entidades)

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

## 5. Especificação da API (Endorpoints Principais)

Todos os endpoints (exceto o de login) exigem o cabeçalho `Authorization: Bearer <TOKEN>`.

### Autenticação (`/api/v1/auth`)

* `POST /login` -> Autentica um usuário e retorna o token JWT.

### Administração (`/api/v1/admin`) -> *Apenas `ROLE_ADMIN*`

* `POST /alunos` -> Cadastra um novo aluno (e cria seu respectivo Usuário).
* `POST /professores` -> Cadastra um novo professor.
* `POST /cursos` -> Cria um novo curso.
* `POST /disciplinas` -> Cria uma disciplina atrelada a um curso.

### Acadêmico / Professores (`/api/v1/professor`) -> *Apenas `ROLE_PROFESSOR*`

* `GET /turmas` -> Lista as turmas atribuídas ao professor logado.
* `PUT /turmas/{turmaId}/notas` -> Lança ou atualiza as notas dos alunos daquela turma.
* `PUT /turmas/{turmaId}/frequencia` -> Registra faltas de um estudante.

### Estudantes (`/api/v1/aluno`) -> *Apenas `ROLE_ALUNO*`

* `GET /boletim` -> Retorna as notas, faltas e o status do aluno logado em todas as disciplinas do período corrente.
* `POST /matricula-turma` -> Realiza a inscrição em uma turma disponível.

---

## 6. Erros Comuns no Desenvolvimento e Como Evitá-los (Dica Sênior)

Ao iniciar o código do seu SIGA, preste atenção nestes pontos críticos para evitar refatorações dolorosas no futuro:

1. **Problema do Loop Infinito no JSON:** Como `Aluno` tem `Usuario` e `Usuario` pode fazer referência reversa, se você retornar a Entidade diretamente no Controller, o Jackson (conversor de JSON) entrará em loop infinito.
* *Como evitar:* **Use DTOs (Records)**. Nunca retorne a entidade mapeada do banco diretamente no Controller.


2. **Vazamento de Senhas:** Retornar o hash da senha do usuário em requisições de listagem.
* *Como evitar:* No DTO de resposta de usuários, nunca mapeie ou inclua o campo `senha`.


3. **Configuração Incorreta de CORS em Produção:** Permitir `*` (qualquer origem) em produção ou esquecer de liberar os métodos `OPTIONS` que o navegador envia antes do `POST`.
* *Como evitar:* Criar uma classe `@Configuration` dedicada ao CORS mapeando explicitamente a URL do seu front-end React.


---

Esta documentação serve como o mapa do nosso projeto. Com ela aprovada, podemos começar a codificar a base de forma limpa e estruturada.


📄 Licença

Este projeto está sob a licença MIT.

Desenvolvido por Wesley Agnaldo 🇲🇿
