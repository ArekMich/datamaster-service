FROM maven:alpine
WORKDIR app
COPY ./src ./src
COPY pom.xml ./
RUN mvn -f pom.xml clean package
RUN mv target/*.jar app.jar
RUN mvn javadoc:javadoc
EXPOSE 8090
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","app.jar"]
