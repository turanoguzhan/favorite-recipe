# favorite-recipe app

A user can manage favorite recipe. Project serves insert, update, fetch and query operations with the help of REST APIs.

### Implemented Libraries;
- Liquibase
- Spring Data JPA (h2 Database)
- Swagger - OpenAPI 1.6.9
- Lombok

### Using Technologies;
- Java 11
- Spring Boot
- Docker

After run ```mvn clean install``` command you can build and image and then run the project on local docker container;
- ```docker build -t favorite-recipe-app .```
- ```docker run -dp 8080:8080 favorite-recipe-app```
