# URL shortener API

This is my proposed solution for the challenge to manage the creation and retrieval of resources which represent pairs of original URLs and their corresponding short URLs.  
Welcome to this code and have fun managing cars!

Imagine you're building a URL shortener as a potential next-big-thing product. To test it, we would like to first have an MVP with only basic functionality.  
However, if everything goes according to plan, we will invest more effort into this so even the MVP should be a modern, nicely written, maintainable and production ready API.
For the MVP we don't want any kind of user registration or security. We only care that we provide a proper REST API that will enable other users/ clients to send a URL and will receive some kind of identifier/ hash to which this URL corresponds in our system. 
And the other way around of course, meaning that a user should be able to resolve the full URL.
To speed up the development, we won't be writing everything on our own, so please think of libraries/ components you could use to fulfill the task.

## Assumptions, compromises and good-to-knows

In order to speed up the development, the testing of the features is done by unit and integration tests. For next releases, it will be necessary to do more Unit testing and less integration testing, in accordance with the established best practices (testing pyramid).

## Getting started

To build the project, it is necessary to run the following commands in the project root:

```
gradle build
```

To create a docker image called carsimage, using the description from Dockerfile:
```
docker build -f ./app/Dockerfile -t urlshortenerimage .
```

To run create urlshortenercontainer using urlshortenerimage:
```
docker run --name urlshortenercontainer -p 8080:8080 urlshortenerimage:latest
```

After the steps already mentioned, the system should be up und running. Now we can launch the integration tests with the following command:

```
gradle integrationTest
```

To stop the container carscontainer:
```
docker stop urlshortenercontainer
```

To delete unused resources like images and containers:
```
docker system prune
```

## Most important technologies and tools used

- Java 21
- Spring boot for the server framework
- Spring data JPA as specification of the database management
- H2 as a database engine  
- Docker for containerizing the application
- Gradle for building the project
- Rest assured to test the API
- Lombok to generate code

## About the API specification

The URL shortener API is defined in the following OpenAPI file:

## Sample request to create a short url for the given URL (local environment)


```
POST http://localhost:8080/api/url
Content-Type: application/json

{
  "originalUrl": "https://dein-antrag.dkb.de/girokonto-start/"
}
```

The expected response for the first request is:
```
HTTP/1.1 201 
Location: http://localhost:8080/api/url/GAg1tz
Content-Length: 0
Date: Wed, 31 Jul 2024 20:28:43 GMT

<Response body is empty>

Response code: 201
```

The expected response for the second request is:

```
HTTP/1.1 409 
Content-Type: text/plain;charset=UTF-8
Content-Length: 140
Date: Wed, 31 Jul 2024 20:30:46 GMT

The URL {
  "originalUrl": "https://dein-antrag.dkb.de/girokonto-start/"
} could not be created because it is already present in the system.

Response code: 409
```

## Possible developments on the functional side

Endpoints to delete entities, to edit/filter/query/search, etc.

## In order to be production-ready, the following matters still need to be addressed
Better test coverage  
OpenApi documentation for the endpoints

#### Scalability    
Discuss the expected usage of the application to develop the expected metrics that the product needs to meet  
Create performance tests  
Implement a cache mechanism to speed up the retrieval of the original URL from the short URL
After having checked where the performance bottlenecks are, implement a strategy to scale the application horizontally

#### Databases
Database indexing for speeding up queries
Check what is better: NoSQ or SQL database?
Choose strategy for horizontal scaling

#### Devops
CI/CD, including the following stages: code fetch, vulnerabilities-analysis, static code analysis, compile, run dev tests, deploy on test environment, run API tests, performance tests, deploy on prod, create RELEASE    
Kubernetes    
New gradle task to independently run integrationTest with one single command and cleanup the created images/volumes after the tests are successfully finished      
Document new features/bugfixes released    

#### Security
Use TLS to secure the HTTP connection  
Metrics and Health endpoints  
Check the parameters sent to the API for possible injection attacks  
Authentication/authorization (scopes, roles)  
Monitoring for the different stages  

#### And most importantly before a production release: the feedback from the team and stakeholders