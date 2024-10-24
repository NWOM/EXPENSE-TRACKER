FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY ./ app
RUN ./gradlew build
CMD ["java","-jar","build/libs/your-app-name.jar"]


