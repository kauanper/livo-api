# API Livo 

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)

## Descrição do Projeto
**Livo – Sistema de Acompanhamento de Leitura**

O Livo é um ecossistema desenvolvido para entusiastas da leitura que desejam organizar suas bibliotecas pessoais de forma eficiente e sem as distrações das redes sociais modernas. O foco é a experiência individual, permitindo o registro de progresso, classificação de obras e acompanhamento de metas.

Esta é a **API Livo**, o core do sistema, construída sobre uma arquitetura de microserviços moderna, resiliente e escalável.

---

## Arquitetura do Sistema
O projeto utiliza o padrão de **Microserviços** com o ecossistema Spring Cloud, garantindo alta disponibilidade e separação de responsabilidades.

-   **API Gateway**: Ponto único de entrada (Porta 8080) que roteia as requisições para os serviços específicos.
-   **Service Discovery (Eureka)**: Central de registro onde todos os microserviços se anunciam, permitindo comunicação dinâmica.
-   **Bancos de Dados**: Cada serviço possui seu próprio esquema de dados (PostgreSQL/H2), respeitando o princípio de isolamento.
-   **Comunicação Inter-serviços**: Realizada via **OpenFeign** para chamadas síncronas entre microserviços.

### Lista de Microserviços:
1.  **Auth Service**: Responsável por autenticação, geração de tokens JWT e segurança.
2.  **User Service**: Gerencia perfis de usuários e preferências.
3.  **Book Service**: Centraliza o catálogo de livros, metadados e avaliações.
4.  **Library Service**: Gerencia a biblioteca pessoal (estantes, progresso de leitura, coleções).

---

## Tecnologias e Ferramentas
-   **Linguagem**: Java 21 (LTS)
-   **Framework Principal**: Spring Boot 3.5.x
-   **Segurança**: Spring Security + JWT (JSON Web Token)
-   **Comunicação**: Spring Cloud Gateway, Netflix Eureka, OpenFeign
-   **Persistência**: Spring Data JPA + Hibernate
-   **Banco de Dados**: PostgreSQL (Produção/Staging) e H2 (Desenvolvimento/Testes)
-   **Cache**: Caffeine Cache para melhor performance em consultas frequentes
-   **Containerização**: Docker e Docker Compose
-   **Documentação**: (OpenAPI/Swagger em breve)

---

## Guia de Inicialização

### Pré-requisitos
-   Docker Desktop instalado
-   Java 21 JDK (caso queira rodar localmente sem Docker)
-   Maven 3.9+

### Rodando o ambiente completo (Docker)
A forma mais rápida de subir a API é via Docker Compose. Navegue até a pasta raiz e use os comandos localizados em `docker/comandos.md`:

```bash
# Subir ambiente de testes (Staging)
docker-compose -f docker/docker-compose.staging.yaml --env-file .env up --build
```

### URLs de Acesso
-   **Gateway**: `http://localhost:8080` (Base para todas as chamadas da API)
-   **Discovery Server**: `http://localhost:8761` (Painel visual do Eureka)

---

## Rotas de Acesso (Gateway)
| Serviço | Caminho Base | Descrição |
| :--- | :--- | :--- |
| **Auth** | `/auth/**` | Login, Registro e Autenticação |
| **User** | `/user/**` | Gerenciamento de perfil de usuário |
| **Books** | `/books/**` | Busca e detalhes de livros |
| **Library** | `/library/**` | Gerenciamento de estantes e progresso |

---

## Boas Práticas Implementadas
-   **Clean Code & SOLID**: Código focado em legibilidade e manutenabilidade.
-   **Padrão DTO (Data Transfer Object)**: Transfere apenas o necessário entre as camadas, protegendo as entidades de domínio.
-   **Tratamento Global de Exceções**: Utilização de `@ControllerAdvice` para retornos de erro padronizados.
-   **Mappers**: Conversão eficiente de objetos entre camadas.
-   **Stateless**: A API não armazena estado de sessão, utilizando JWT para escalabilidade horizontal.

---

## Equipe
**Desenvolvedores backend**
*   Lucas Ferreira Nobre
*   Francisco Kauan Pereira Cavalcante
*   Miqueias Bento da Silva
  
**Desenvolvedores frontend**
*   Antonio Rewelli Oliveira dos Santos
*   Julian César Pereira Cardoso
*   Luis Eduardo Vieira de Oliveira
