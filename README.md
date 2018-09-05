# Configuration Management with Spring Boot and PCF

Application demonstrating Internal and External Configuration management with Spring Boot and PCF for both Sensitive and non-Sensitive Data.

# Best Practices 

YAGNI - default to the simplest solution. 

Don't store passwords in plain text source control. 

## Configuration Options  

#1. Properties Files
    1. Values Annotation
    2. Default Value 
#2. Environment Variables 
https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html 
#3. Spring Profiles 
    1. spring.profiles.active
    2. application-cloud.properties
#4. Marketplace Services 
#5. CUPS - Custom User Provided Services  
#6. Environment variable .. requires a restart 
#7. Refresh Actuator Endpoint <- not that it hits only one instance  .. requires Refresh Scope 
#8. Config Server
    1. Requires Spring Cloud Client 
    2. Default Address is http://localhost:8888
    3. Marketplace binding will point to correct one
#9. Vault via PCF
    1. Spring Cloud Vault .. requires service binding 

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
