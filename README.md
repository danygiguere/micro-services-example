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
- Each time we add a route in a micro service, we need to map it in the application.yml (as a route)

### Installation
- To install this project, make sure you have a mysql database running (Or you can use this dockerized db: https://github.com/danygiguere/docker_db).
- Create two database: micro-services-example-users-service and micro-services-example-posts-service
- Then start first the eureka-server, then the api-gateway and then the micro services (I'm not adding the h2 in-memory database to this example project).
- If you want to see the eureka-server go to http://localhost:8010/ where you will see the api gateway and microservices instances registered with Eureka Server.
- Then you can use the postman collection (in the postman folder) and you can do (for instance) a GET request to http://localhost:8080/posts-service/posts.
- Note that the api-gateway is running on port 8080. The `/posts-service` part is the micro service name (`spring.application.name`) that you want to call and the `/posts` part is the route you want to reach in the microservice.


### Todo
- add jwt and implement Login and register and refresh token
- add a config server
- block micro services so they only accept requests from the API Gateway
- create a Remix app client
- implement reset and forgot password
- add spring cloud bus
- add Resilience4j
- add Tracing with Micrometer
