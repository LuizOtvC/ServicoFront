# Casa Fácil — Front-end

Interface web do sistema **Casa Fácil**, um hub de serviços domiciliares com motor de matchmaking algorítmico que conecta prestadores de serviço a contratantes.

> Back-end: [github.com/LuizOtvC/ServicoBack](https://github.com/LuizOtvC/ServicoBack)

---

## Sobre o projeto

O front-end do Casa Fácil é uma aplicação web desenvolvida com Spring MVC e Thymeleaf, que consome a API REST do back-end. A autenticação é gerenciada via token JWT armazenado em sessão.

---

## Telas disponíveis

- **Login e Registro** — autenticação de usuários
- **Perfil** — visualização de dados, habilidades e dias de trabalho, com opções de edição
- **Editar Perfil** — atualização de nome, telefone, descrição e dias disponíveis
- **Habilidades** — cadastro de serviços com nível de experiência (Básico, Intermediário, Avançado)
- **Projetos disponíveis** — listagem de projetos abertos com filtros de área, orçamento e dias da semana
- **Detalhes do projeto** — informações completas e score de compatibilidade com o projeto
- **Criar Projeto** — publicação de projetos com serviços necessários e dias disponíveis
- **Meus Projetos** — gerenciamento de projetos criados pelo usuário
- **Propostas recebidas** — visualização de candidatos ordenados por score de matchmaking, com alertas de conflito de agenda
- **Enviar Proposta** — formulário de proposta com valor e descrição
- **Minhas Propostas** — acompanhamento das propostas enviadas
- **Notificações** — mensagens automáticas do sistema com indicador de lidas/não lidas
- **Avaliação** — avaliação mútua entre contratante e prestador após conclusão do projeto
- **Perfil de outro usuário** — visualização de perfil e habilidades de outros usuários

---

## Tecnologias

- Java 21
- Spring Boot 3
- Spring MVC + Thymeleaf
- Bootstrap 5 + Tabler Icons
- JWT (controle de sessão via HttpSession)
- Maven

---

## Como rodar localmente

### Pré-requisitos

- Java 21
- Maven
- Back-end do Casa Fácil rodando em `http://localhost:9000`

### Passo a passo

**1. Clone o repositório**
```bash
git clone https://github.com/LuizOtvC/ServicoFront.git
cd ServicoFront
```

**2. Configure a URL do back-end no `application.properties`**
```properties
server.port=8081
api.base.url=http://localhost:8080
```

**3. Rode o projeto**
```bash
mvn spring-boot:run
```

A aplicação ficará disponível em `http://localhost:9001`.

> O back-end precisa estar rodando antes de iniciar o front-end.
