FROM openjdk:8
ADD ./target/spring-boot-redis-cache-0.0.1-SNAPSHOT.jar src/spring-boot-redis-cache-0.0.1-SNAPSHOT.jar
WORKDIR src
ENTRYPOINT ["java","-jar", "spring-boot-redis-cache-0.0.1-SNAPSHOT.jar"]