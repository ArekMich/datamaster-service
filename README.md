# Data mining service

## How to run in production mode:
#### 1. Prerequisites:
You need to have mvn package installed as well as java 11. You also
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

### How to sign up user:
##### 1. Http method: POST
##### 2. Endpoint: http://localhost:8090/api/auth/signup
##### 3. Body raw JSON(application/json): 
```json
{
    "name": "Arek Michalik",
    "username": "arekmich",
    "email": "arkadiusz.michalik@gmail.com",
    "password": "arekmich"
}
``` 
##### 4. Send Request
##### 5. Result
Response Status should be "201 Created" and body 
```json
{
    "success": true,
    "message": "User registered successfully"
}
```
    
    
### How to sign in:
##### 1. Http method: POST
##### 2. Endpoint: http://localhost:8090/api/auth/signin
##### 3. Body raw JSON(application/json): 
```json
{
    "usernameOrEmail":"arekmich",
    "password": "arekmich"
}
```
##### 4. Send Request
##### 5. Result
Response Status should be "200 OK" and body 
```json
{
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTgyNDAwMDYwLCJleHAiOjE1ODMwMDQ4NjB9._s_ymvspdeHb9hrJSenG2j_ka6T0lspascRS1bONCCMn6xf9lZAAW3zJnDGMrw-zsczh41xQTpyP52Z5tDByyA",
    "tokenType": "Bearer"
}
```       
          
### Get the currently logged in user:
##### 1. Http method: GET
##### 2. Endpoint: http://localhost:8090/api/user/me
##### 3. Headers: 
```
Key: Authorization
Value: Bearer $ACCESS_TOKEN
``` 
######$ACCESS_TOKEN is a value in body field "accessToken" from response: http://localhost:8090/api/auth/signin
##### 4. Send Request
##### 5. Result
Response Status should be "200 OK" and body 
```json
{
    "id": 1,
    "username": "arekmich",
    "name": "Arek Michalik"
}
```                                      
                  
### Check if a username is available for registration:
##### 1. Http method: GET
##### 2. Endpoint: http://localhost:8090/api/user/checkUsernameAvailability?username=arekmich
##### 3. Send Request
##### 4. Result
Response Status should be "200 OK" and body 
```json
{
    "available": false/true
}
```      


### Check if an email is available for registration:
##### 1. Http method: GET
##### 2. Endpoint: http://localhost:8090/api/user/checkEmailAvailability?email=arkadiusz.michalik@gmail.com
##### 3. Send Request
##### 4. Result
Response Status should be "200 OK" and body 
```json
{
    "available": false/true
}
```      


### Get the public profile of a user.:
##### 1. Http method: GET
##### 2. Endpoint: http://localhost:8090/api/users/arekmich
##### 3. Send Request
##### 4. Result
Response Status should be "200 OK" and body 
```json
{
    "id": 1,
    "username": "arekmich",
    "name": "Arek Michalik",
    "joinedAt": "2020-02-23T15:50:09.050Z"
}
```                                          
    
