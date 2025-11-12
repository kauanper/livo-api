FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o POM e baixa dependências em cache
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte e compila
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Copia o jar gerado da etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Define o perfil ativo (pode ser sobrescrito no compose)
ENV APP_PROFILE=staging

# Expõe a porta da aplicação
EXPOSE 8084

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
