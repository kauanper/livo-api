# Comandos para iniciar o container docker

## Perfil de desenvolvimento  
*Perfil usado para bando de dados e testes pelos desenvolvedores*  
  
**Construir o container**  
`docker-compose -f docker/docker-compose.dev.yaml --env-file .env up --build`

**Iniciar o container**  
`docker-compose -f docker/docker-compose.dev.yaml --env-file .env start`

**Parar o container**  
`docker-compose -f docker/docker-compose.dev.yaml --env-file .env stop`
  
**Resetar volumes**  
`docker-compose -f docker/docker-compose.dev.yaml down -v`

---

## Perfil de staging
*Perfil usado para pelos para adicionar a aplicação em container para testes de desenvolvedores do **frontend***

**Construir o container**  
`docker-compose -f docker/docker-compose.staging.yaml --env-file .env up --build`

**Iniciar o container**  
`docker-compose -f docker/docker-compose.staging.yaml --env-file .env start`

**Parar o container**  
`docker-compose -f docker/docker-compose.staging.yaml --env-file .env stop`

**Resetar volumes**  
`docker-compose -f docker/docker-compose.staging.yaml down -v`
---

## Liberar memória  
Em casos que builds frequentes, pode ser necessário liberar memória do docker, a seguir estão alguns comando úteis:  

**Limpa containers parados**  
`docker container prune`  

**Limpa imagens não usadas**  
`docker image prune -a`  

**Limpa volumes não usados (atenção se guardar dados no banco)**  
`docker volume prune`  

**Limpa cache de build (libera bastante espaço)**  
`docker builder prune`  

# Instruções
O docker compose possui muitos bancos de dados e serviços, por isso pode demorar um pouco a subir, segue algumas instruções para veriricar seu bom funcionamento:
- esperar alguns segundos após sua inicialização
- verificar o discovery server na rota http://localhost:8761/
- veja se os seguintes serviços estão listados e com o status "up"
  - API-GATEWAY
  - AUTH-SERVICE
  - BOOK-SERVICE
  - USER-SERVICE
  - LIBRARY-SERVICE