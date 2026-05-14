# Sistema_academico
SGA - Sistema de Gestão Académica

O SGA é uma plataforma robusta e escalável concebida para a digitalização integral de instituições de ensino. O sistema centraliza operações administrativas, pedagógicas e financeiras, permitindo uma gestão eficiente e transparente do percurso académico, desde a matrícula inicial até à graduação.
🚀 Funcionalidades Principais
🏛️ Gestão Administrativa

    Controle de Matrículas: Processamento digital de inscrições e renovações.

    Gestão de Turmas: Organização de horários, salas e alocação de docentes.

    Emissão de Documentos: Geração de declarações, certificados e históricos escolares com validação digital.

📖 Gestão Pedagógica

    Lançamento de Notas: Registro automatizado de avaliações, exames e cálculo de médias.

    Diário de Classe: Controle de assiduidade (faltas) e registro de sumários pelos docentes.

    Portal do Aluno: Acesso em tempo real a notas, horários e materiais de apoio.

💰 Gestão Financeira

    Controle de Propinas: Monitoramento de pagamentos, multas e isenções.

    Integração Fintech: Pagamentos facilitados via Mobile Money (M-Pesa, e-Mola) e referências bancárias.

    Relatórios: Dashboards financeiros para análise de receitas e inadimplência.

🛠️ Stack Tecnológica

O projeto utiliza o que há de mais moderno no ecossistema Java para garantir alta performance e segurança:

    Linguagem: Java 21 (LTS) com foco em Virtual Threads.

    Framework: Spring Boot 4.0.x.

    Persistência: Spring Data JPA e Hibernate.

    Base de Dados: PostgreSQL.

    Segurança: Spring Security + JWT (JSON Web Token).

    Ferramentas: Lombok, Docker e Maven.

⚙️ Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina:

    JDK 21

    Maven 3.9+

    Docker (opcional, para a base de dados)

🔧 Instalação e Execução

    Clone o repositório:
    Bash
    git clone https://github.com/wesleysemende133/Sistema_academico.git
    
    Configure o banco de dados:
    O projeto utiliza o Spring Boot Docker Compose. Se tiver o Docker instalado, a base de dados subirá automaticamente ao iniciar a aplicação. Caso contrário, configure o application.properties com as credenciais do seu Postgres local.

    Compile e execute a aplicação:
    Bash

    mvn spring-boot:run

📄 Licença

Este projeto está sob a licença MIT.

Desenvolvido por Wesley Agnaldo 🇲🇿
