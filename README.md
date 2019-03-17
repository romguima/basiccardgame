# Card Game API

### Tech Stack

- Java 8
- Maven 3.6.0
- SpringBoot 2.1.3
- SpringMVC
- SpringData
- JPA (with Hibernate as default provider)
- Spring HATEOAS
- JUnit4
- REST Assured
- AssertJ
- Cucumber
- H2
- Swagger

### Quick Start

1. Install the Java JDK 1.8
2. Clone the repo
3. Build it from scratch using Maven Wrapper 
```
mvnw clean install 
```
or install your own Maven and run
```
mvn clean install
```

##### Running the API
- To start from terminal:
```
mvn spring-boot:run
```

The service will start on port 8080 by default.

The following URLs are available:

#####Swagger  (http://localhost:8080/swagger-ui.html)
All the endpoints available on the API are shown and testable through this page.


#####H2 Console (http://localhost:8080/h2/)
The in-memory relational database in use is accessible through its own web console.
To access the DB use the following parameters:
- Saved settings: `Generic H2 (Embedded)`
- JDBC URL: `jdbc:h2:mem:cardgamesdb`
- User Name: `sa`
- No need to specify any password.

## Controllers and Endpoints

All API functionalities are divided between the following controllers (and its base paths):
1. GameController `/games` (Creates, Lists, Retrieves and Removes Games)
2. DeckController `/decks` (Creates and Retrieves Card Decks)
3. PlayerController `/players` (Creates, Lists, Retrieves Player with its Cards per Game)
4. GameDeckController `/games/{id}/deck` (Associates Card Decks into Games, Shuffles Games' Decks, Provides Games' Card Deck reports)
5. GamePlayerController `/games/{id}/players`(Associates Players to Games, Removes Players from Games, Deals Cards to Players on Games)

All resource identifiers used are UUIDs. The usage of Swagger console is encouraged to make it easier to check all the available endpoints and necessary parameters.

## Architecture and Design
The basic MVC model was used to separate the concerns and make the solution more extensible. 

Unit tests and Feature/Integration tests are incorporated into the solution with enabled BDD and TDD capabilities.

[The 12 factor app](https://12factor.net/) were also take into account during the design decisions, such as the synchronization mechanisms that were added through DB pessimistic write locking prior to changes to the Games' card decks.

## Possible Improvements
As per the limited work window, some classes weren't totally covered by Unit Tests and Functional/Integration tests.

Some validations on inputs as well were deprioritized with focus on delivering all the functionality needed for the API.

More advanced integration test scenarios are also missing, concurrency for instance is not covered by the automated tests.   
 