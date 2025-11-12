FROM maven:3.9.8-eclipse-temurin-21 AS builder

WORKDIR /app

# Copia o pom.xml e baixa as dependências primeiro (cache eficiente)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o restante do código e empacota a aplicação
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copia o JAR gerado na etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Define o profile do Spring (pode ser sobrescrito pelo docker-compose)
ENV APP_PROFILE=staging

# Porta exposta do serviço
EXPOSE 8083

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
