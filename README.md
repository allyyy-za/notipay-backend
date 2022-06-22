# Notipay

NotiPay is a paid subscriptions and bills manager web application that allows users to log subscription and bill services with their information such as name, amount, and renewal/due date. This way, users will be able to pay on time since they will be able to see when a certain payment is due or when a certain subscription needs to be renewed. 

## ✅ Features

- Log paid subscription and bill services with their details such as renewal and due date, name, and amount.
- Track your paid subscriptions and bills with their renewal and due dates.

## 💻 Tech Stack for the Backend

- Spring Boot
- Spring Security
- MySQL
- JWT


## 🖱 Implementation of Technologies Used

### Spring Security and JWT
- Spring Security and JWT were used for the security of the web application.
- How is it used on the web application?
    - **Initial Setup for the Spring Security and JWT**
    ```xml
    <dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
		<groupId>com.auth0</groupId>
		<artifactId>java-jwt</artifactId>
		<version>3.19.2</version>
    </dependency>
    <dependency>
		<groupId>io.jsonwebtoken</groupId>
		<artifactId>jjwt</artifactId>
		<version>0.9.1</version>
    </dependency>
    ```
- **Configuration of Spring Security**
    - Create a SecurityConfiguration class that extends Web SecurityConfigurerAdapter
    - Override two configure() methods with AuthenticationManagerBuilder and HttpSecurity as the parameter.
    - Create a bean for AuthenticationManager, DaoAuthenticationProvider, and PasswordEncoder.
- **Creation of JWT Filter class**
    - This extends the Spring Web Filter OncePerRequestFilter class. For any incoming request this Filter class gets executed. It checks if the request has a valid JWT token. If it has a valid JWT Token then it sets the Authentication in the context, to specify that the current user is authenticated.
    - Create a JWTFilter class that extends OncePerRequestFilter. This filter base class aims to guarantee a single request dispatch, on any servlet container.
    - Create a doFilterInternal method with request, response, and chain as parameters.
- **Creation of JWT utility class**
    - This is the class that helps the application generate JWT token, get username from JWT, and validate the JWT.
    - Set the values for the token validity and secret key.
    - Create methods such as:
        - getUsernameFromToken
        - getIssuedAtDateFromToken
        - getExprirationDateFromToken
        - getClaimFromToken
        - getAllClaimsFromToken
        - isTokenExpired
        - generateToken
        - doGenerateToken
        - validateToken
    - Most of the methods above are just helper functions. The most important methods are the generateToken and validateToken.
- **Creation of AccountDetails class**
    - This is the class that grants authorization and provides getters and setters such as username and password.
    - This class should implement UserDetails interface and implements the methods needed.
- **Creation of AccountDetailsService class**
    - This is the class that creates an account details with username as the parameter.
    - This class should implement UserDetailsService interface and implement the methods needed.

## 🖱 Connecting Frontend to Backend

- Since I have used Spring Boot as a REST API and React.js for frontend, there should be connection between them in such a way that frontend will communicate with backend.
- The frontend will send HTTP request via the fetch API. In the fetch API, the link from the annotations in the backend will be called.
- An example is to call the backend for login with an annotation of @PostMapping("/auth/signin”), has a fetch API as follows:
```JavaScript
fetch("api/auth/signin", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(user),
    }).then((response) => {
      if (response.status === 200) {
        setAuth(response.headers.get("Authorization"));
        if (auth) {
          navigate("/home");
          window.location.reload();
        }
      } else {
        return response.text();
      }
    }).then((data) => { setError(data); });
 ```
- You can see here the link inside the @PostMapping annotation was called with other attributes such as method, headers, and body.
- The response will come from the backend such that if the status is 200, this will mean that it is successful. Otherwise, it will result to an error.
- Data is being communicated from two servers, frontend and backend, in JSON format via the body of the HTTP request.
- In the backend, the Spring Boot application will the look for the annotation @RequestBody, that can be found in the parameter, as it will need to find the body of the request.
```Java
@PostMapping("/auth/signin")
	public ResponseEntity<String> authenticateUser(@RequestBody AuthCredentials request) {
		return userService.authenticateUser(request);
	}
```
- This is the code for the backend. You can see the link in the annotation PostMapping and in the parameters, there is annotation RequestBody to look for the body of the HTTP request. 
