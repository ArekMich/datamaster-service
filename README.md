# backend for data mining service

## How to run in production mode:

0. Prerequisites

You need to have mvn package installed as well as java 11. You also
need to add it to path if its not added.

1. build .jar file

```mvn -f pom.xml clean package```

2. Run application in prod mode

```java -jar -Dspring.profiles.active=prod target/data-mining-service-0.0.1-SNAPSHOT.jar```

