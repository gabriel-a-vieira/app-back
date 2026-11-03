# Etapa 1: compila o .jar com Maven (JDK completo, só existe durante o build)
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copia primeiro só o que define dependências, para o Docker reaproveitar esse cache
# quando só o código-fonte mudar
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

COPY src src
RUN ./mvnw clean package -DskipTests -B

# Etapa 2: imagem final enxuta, só com o JRE e o .jar
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# O Render injeta a porta via variável PORT (lida em application.properties)
EXPOSE 8081

# Ajustado para o plano gratuito do Render (512MB, CPU fracionada):
# - MaxRAMPercentage: limita a heap para caber nos 512MB
# - UseSerialGC: GC mais leve em memória e CPU para 1 core
# - TieredStopAtLevel=1: só o compilador C1, sobe bem mais rápido com pouca CPU
# - Xss512k: pilha menor por thread, economiza memória
# - ExitOnOutOfMemoryError: se estourar memória, o processo morre e o Render reinicia limpo
ENTRYPOINT ["java", \
    "-XX:MaxRAMPercentage=75", \
    "-XX:+UseSerialGC", \
    "-XX:TieredStopAtLevel=1", \
    "-Xss512k", \
    "-XX:+ExitOnOutOfMemoryError", \
    "-jar", "app.jar"]
