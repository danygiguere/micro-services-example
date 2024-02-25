# Microservices example with Eureka Service, an Api Gateway and micro services (using Spring Boot with Kotlin Coroutines, r2dbc and JWT)

### About
- This is just a demo project to demonstrate how to build microservices with Eureka Service, an Api Gateway and micro services (using Spring Boot with Kotlin Coroutines, r2dbc and JWT).

##### Eureka Server
- https://cloud.spring.io/spring-cloud-netflix/reference/html/
- Using Eureka Server we will start a Eureka Sever and all the micro services will register to it.
- Multiple instances of a micro services are load balanced.

##### API Gateway
- The api gateway is linked to the Eureka Server and will reroute all requests to the appropriate micro service.
- For instance a request to `http://localhost:8080/posts-service/posts` (where port 8080 is the API Gateway), will reroute the request to the posts-service at the `/posts` route

### Installation
- To install this project, make sure you have a mysql database running (Or you can use this dockerized db: https://github.com/danygiguere/docker_db), then start first the  eureka-server, then the api-gateway and then the micro services (users and posts). I'm not adding the h2 in-memory database to this example project.
- To see the eureka-server go to http://localhost:8010/ to see the api gateway and microservices instances registered with Eureka.
- Then you can use the postman collection (in the postman folder) and you can do (for instance) a get request to http://localhost:8080/posts-service/posts.
- Note that the api-gateway is running on port 8080. The `/posts-service` is the micro service name (`spring.application.name`) that you want to call and the `/posts` is the route you want to reach in the microservice.


### Todo
- create a user service
- add jwt and implement Login and register and refresh token
- add a config server
- block micro services so they only accept requests from the API Gateway
- create a Remix app client
- implement reset and forgot password
- add spring cloud bus
- add Resilience4j
- add Tracing with Micrometer
