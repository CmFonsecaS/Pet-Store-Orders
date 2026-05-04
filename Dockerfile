FROM eclipse-temurin:17-jdk AS buildstage

WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# Le damos permisos de ejecución al wrapper de maven
RUN chmod +x mvnw
# Descargamos dependencias primero para cachear esta capa
RUN ./mvnw dependency:go-offline

COPY src /app/src
COPY Wallet_MIDBDUOC /app/wallet

ENV TNS_ADMIN=/app/wallet

# Compilamos y empaquetamos saltando las pruebas para que no falle si la DB no está lista en build time
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jdk
COPY --from=buildstage /app/target/petstore-orders-0.0.1-SNAPSHOT.jar /app/petstore-orders.jar
COPY Wallet_MIDBDUOC /app/wallet

ENV TNS_ADMIN=/app/wallet
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/petstore-orders.jar"]
