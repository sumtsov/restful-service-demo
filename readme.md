# Book Inventory Service

Book Inventory Service is a RESTful web service that allows user to manage Book and Author entities

## Features

- CRUD for Author and Book entities
- Retrieving list of books by author name
- Retrieving full book info by book title
- Logging total amount of books in system every ***n*** minutes
- Integration with [external API][1] that allows retrieving random author
- Tests with test containers

## Installation

1. Clone repository: ```git clone```
1. Build the project: ```mvn clean package```
1. Run the .jar: ```java -jar book-inventory-service-${version}.jar```

## Usage

Explore postman collections: ```book-inventory-service/src/main/resources/postman-collections/```

## API Reference

1. Run the .jar: ```java -jar book-inventory-service-${version}.jar```
1. Explore swagger:
    1. swagger.json:```http://127.0.0.1:8080/v2/api-docs```
    1. swagger-ui: ```http://127.0.0.1:8080/swagger-ui/index.html```

[1]: (https://randomuser.me/documentation)