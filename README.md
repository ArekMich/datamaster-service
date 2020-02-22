# Data mining service

## How to run in production mode:

    1. Prerequisites
    You need to have mvn package installed as well as java 11. You also
    need to add it to path if its not added.


    2. build .jar file
    ```mvn -f pom.xml clean package```


    3. Run application in prod mode
    ```java -jar -Dspring.profiles.active=prod target/data-mining-service-0.0.1-SNAPSHOT.jar```


## How to sign up user:

    1. Http method: POST
    2. Endpoint: http://localhost:8090/api/auth/signup
    3. Body raw JSON(application/json): {
                                     	  "name": "Arek Michalik",
                                     	  "username": "arekmich",
                                     	  "email": "arkadiusz.michalik@gmail.com",
                                     	  "password": "arekmich"
                                        } 
    4. Send Request
    Response Status should be "201 Created" and body {
                                                         "success": true,
                                                         "message": "User registered successfully"
                                                     }
    
## How to sign in:

    1. Http method: POST
    2. Endpoint: http://localhost:8090/api/auth/signin
    3. Body raw JSON(application/json): {
                                        	"usernameOrEmail":"arekmich",
                                        	"password": "arekmich"
                                        }
    4. Send Request
    Response Status should be "200 OK" and body {
                                                    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTgyNDAwMDYwLCJleHAiOjE1ODMwMDQ4NjB9._s_ymvspdeHb9hrJSenG2j_ka6T0lspascRS1bONCCMn6xf9lZAAW3zJnDGMrw-zsczh41xQTpyP52Z5tDByyA",
                                                    "tokenType": "Bearer"
                                                }       
                                                
                                                                
    