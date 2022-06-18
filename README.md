# Notipay


NotiPay is a paid subscriptions and bills manager web application that allows users to log subscription and bill services and manage payments in one place.

NotiPay is the easiest way to manage subscriptions and bills and helps you pay on time, and you will never be late on payment, again.

## ✅ Features

- Log paid subscription and bill services with their details such as renewal and due date, amount, and etc.
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
