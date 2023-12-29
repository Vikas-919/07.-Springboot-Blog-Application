# Spring Boot Blog Application - Backend

**Overview:**

This Spring Boot Blog Application serves as the backend for a simple blogging platform. The project is structured using a 3-layer architecture, including Controller, Service, and Repository layers. Various features such as validation, security (JWT for authorization and authentication), global exception handling, versioning through URI path, and the use of DTOs (Data Transfer Objects) have been implemented.

**Project Structure:**

**1. Controller Layer:** Handles incoming HTTP requests and delegates processing to the Service layer. Includes endpoints for managing resources like Post, Comment, Category, User, and Role.

**2. Service Layer:** Implements business logic and interacts with the Repository layer. Manages the flow of data between the Controller and Repository layers.

**3. Repository Layer:** Manages data storage and retrieval. Implements interactions with the database.

**Features:**

**1. Validation:** Input data is validated to ensure data integrity and consistency.

**2. Security (JWT):** Implements JSON Web Token (JWT) for secure authentication and authorization.

**3. Exception Handling:** Global exception handling ensures graceful error responses.

**4. Versioning:** Versioning is achieved through URI paths, facilitating backward compatibility.

**5. Data Transfer Objects (DTOs):** Uses DTOs to transfer data between layers, enhancing flexibility and reducing coupling.

**6. Springdoc OpenAPI Swagger**: Integrates Springdoc OpenAPI Swagger for API documentation. Access the Swagger UI at `http://localhost:8080/swagger-ui.html` and the OpenAPI JSON at `http://localhost:8080/v3/api-docs`
**7. AOP Logging Aspect**: Implements AOP logging with `@Before` and `@AfterReturning` advice to log method calls, arguments, and returned data.


**Entity Relationships:**

**1. User and Role:** Utilizes Many-to-Many mapping to establish relationships between users and roles.

**2. Category and Post:** Employs One-to-Many mapping to link categories and posts.

**3. Post and Comment:** Implements One-to-Many mapping for the relationship between posts and comments.


![00](https://github.com/Vikas-919/07.-Springboot-Blog-Application/assets/142529224/fd37ad7c-b3b5-45df-8c45-e99eff0bda37)


**Endpoints:**

**POST:** /api/v1/posts: Create a new post.

**GET:** /api/v1/posts: Retrieve all posts.

**GET:** /api/v1/posts/{postId}: Retrieve a specific post.

**PUT:** /api/v1/posts/{postId}: Update a post.

**DELETE:** /api/v1/posts/{postId}: Delete a post.

... similar endpoints for other resources ...

![01](https://github.com/Vikas-919/07.-Springboot-Blog-Application/assets/142529224/b9db5f1e-2590-4dc6-9642-8472211d8aa3)

![02](https://github.com/Vikas-919/07.-Springboot-Blog-Application/assets/142529224/e9986883-49f4-464e-9574-84e575b1cf27)

![03](https://github.com/Vikas-919/07.-Springboot-Blog-Application/assets/142529224/ce221b56-e318-43d1-99aa-ee6049831d92)

![04](https://github.com/Vikas-919/07.-Springboot-Blog-Application/assets/142529224/42517309-76c9-4c4c-9495-720ea2b1858f)

![05](https://github.com/Vikas-919/07.-Springboot-Blog-Application/assets/142529224/888da8b6-c1d9-425c-b6b5-355a06712e6e)


**Dependencies:**

1. spring-boot-starter-web
2. spring-boot-starter-data-jpa
3. spring-boot-devtools
4. mysql-connector-j
5. lombok
6. modelmapper
7. spring-boot-starter-validation
8. spring-boot-starter-security
9. jjwt-impl, jjwt-api, jjwt-jackson
10. springdoc-openapi-starter-webmvc-ui
11. spring-boot-starter-test


**Contributing:**

We welcome contributions through the submission of issues or pull requests. Your feedback and contributions are immensely valuable!
