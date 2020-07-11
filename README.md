# Data mining service

## How to run in production mode:
#### 1. Prerequisites:
You need to have mvn package installed as well as java 8. You also
need to add it to path if its not added.


#### 2. Build .jar file: 
```
mvn -f pom.xml clean package
```


#### 3. Run application in prod mode:
```
java -jar -Dspring.profiles.active=prod target/data-mining-service-0.0.1-SNAPSHOT.jar
```



## REST API ENDPOINTS

### SWAGGER-UI (http://localhost:8090/swagger-ui.html)

### Calling Protected APIs

Once you’ve obtained the access token using the login API,
 you can call any protected API by passing the accessToken in the Authorization 
 header of the request like
```
Authorization: Bearer <accessToken>
```
 The JwtAuthenticationFilter will read the accessToken from the header, verify it, 
 and allow/deny access to the API.
