# Fase de build: compilação do projeto
FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /order-app

COPY pom.xml ./
RUN mvn dependency:go-offline -B

COPY . .
RUN mvn clean package -DskipTests

# Verificação de conteúdo para depuração
RUN ls -l /order-app/target

# Fase de runtime: execução da aplicação
FROM openjdk:17-jdk-alpine

WORKDIR /order-app

COPY --from=build /order-app/target/*.jar order-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
