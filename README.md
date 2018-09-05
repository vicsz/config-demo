# Configuration Management with Spring Boot and PCF

Application demonstrating Internal and External Configuration management with Spring Boot and PCF for both Sensitive and non-Sensitive Data.

# Best Practices 

YAGNI - default to the simplest solution. 

Don't store passwords in plain text source control. 

## Configuration Options  

#1. Properties Files
- Values Annotation
- Default Value 
#2. Environment Variables
- Locally testing environment variables
- Order of precedence 
https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html 
#3. Spring Profiles 
- spring.profiles.active
- application-cloud.properties
#4. Marketplace Services 
#5. CUPS - Custom User Provided Services  
#6. Refresh Actuator Endpoint <- not that it hits only one instance  .. requires Refresh Scope 
#7. Config Server
- Requires Spring Cloud Client 
- Default Address is http://localhost:8888
- Marketplace binding will point to correct one
#8. Vault via PCF
- Spring Cloud Vault .. requires service binding 

## Demo Steps

### 1. Deploy to PCF

Build the application:

```sh
./mvn package
```

Deploy to PCF using the CLI:

```sh
cf push
```

> Use --no-route in case of conflicting routes.

### 2. Create and Bind the Config Service

This is required for Config Server Usage.

#### Ensure *Configuration Server* service is available in the CF MarketPlace

```sh
cf marketplace
```

Contact your PCF Cloud Ops team if it is not.

#### Create the Service

You can use a *plan* and *name* of your choice.

```sh
cf create-service metrics-forwarder unlimited myforwarder
```

#### Bind the Service to your Application

```sh
cf bind-service metrics-demo myforwarder
```

#### Restage your Application

```sh
cf restage metrics-demo
```
