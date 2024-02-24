# Microservices example with Eureka Service, an Api Gateway, a user micro-service and a post micro-service (using Spring Boot with Kotlin Coroutines, r2dbc and JWT)

### About

- This is just a demo project to demonstrate how to build microservices with Eureka Service, an Api Gateway, a user micro-service and a post micro-service (using Spring Boot with Kotlin Coroutines, r2dbc and JWT).

##### Eureka Server
- https://cloud.spring.io/spring-cloud-netflix/reference/html/
- Using Eureka Server we will start a Eureka Sever and all the micro services will register to it.
- Multiple instances of a micro services are load balanced.

##### API Gateway
- The api gateway is linked to the Eureka Server and will reroute all requests to the appropriate micro service.
- For instance a request to `http://localhost:8080/posts-service/posts` (where port 8080 is the API Gateway), will reroute the request to the posts-service at the `/posts` route




### Todo
- create a user service
- add jwt and implement Login and register
- add a config server
- block micro services so they only accept requests from the API Gateway
- create a Remix app client
- implement reset and forgot password
- add spring cloud bus
- add Resilience4j
- add Tracing with Micrometer and Zipkin
