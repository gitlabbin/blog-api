Blog admin API on SpringBoot
----------------------------

A simple rest api to combined users and posts in blog application.

### Service Component Flow

    BlogController
            --> ContentRepository (Users json)
            --> ContentScheduler --> ContentPublisher          |
                                            --> UserSvcImpl    |--> ContentRepository (Users json)
                                            --> PostSvcImpl    |




## Steps to Run

**. Build and run the app using maven**

```bash
./mvnw clean package
java -jar target/blog-api-1.0.0-SNAPSHOT.jar
```

Alternatively, you can run the app without packaging it using -

```bash
./mvnw spring-boot:run
```

The app will start running at <http://localhost:8080/users>.

## Explore Rest APIs

The app defines following endpoint APIs of `GET`.

    http://localhost:8080/users
    
**. Change config properties **

+ open `src/main/resources/application.properties`


```properties
# api service
api.user.url=https://jsonplaceholder.typicode.com/users

api.post.url=https://jsonplaceholder.typicode.com/posts

# Scheduler
fixedDelay.input=120000     # 2 minutes
initialDelay.input=2000     # 2 seconds
```

## Questions and Comments 
 Brian Chen <https://github.com/gitlabbin>
