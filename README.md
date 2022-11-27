# RESTful Service Demo
Sample RESTful web service that allows users to manage Book and Author entities

## Features
- CRUD for Author and Book entities
- Retrieving list of books by author name
- Retrieving full book info by book title
- Logging total amount of books in system every ***n*** minutes
- Integration with [external API](https://randomuser.me/documentation) that allows retrieving random author
- Integration tests with test containers

## How to run
1. Build the project: ```mvn clean package```
1. Run the .jar: ```java -jar restful-service-demo-${version}.jar```

## Usage
Explore [postman collections](https://github.com/sumtsov/restful-service-demo/tree/master/src/main/resources/postman-collections)

## API Reference
- swagger-ui: ```http://localhost:8080/swagger-ui/index.html```
- json:```http://localhost:8080/v2/api-docs```