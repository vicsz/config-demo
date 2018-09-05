# Configuration Management with Spring Boot and PCF

Application demonstrating Internal and External Configuration management with Spring Boot and PCF for both Sensitive and non-Sensitive Data.

A variety of options are available for configuration management with varying levels of support, features, and costs including:
- Ability to update variables *without* artifact changes
- Ability to update variables *without* application restarts
- Ability to track / audit changes to config variables
- Ability to share config with different application

Pick the right options for your business needs, default to simplicity, and don't store sensitive data (passwords) in SCM. 

## Configuration Options  

# 1. Properties Files

Variables can be injected from application.properties files using the *Value* Annotation.

Note the support for default values, for example:

```java
@Value("${application.greeting.message:hello from default}")
private String greetingMessage;

```

# 2. Environment Variables

## Locally 

For testing purposes, environment variables can be injected at spring-boot:run time.

```sh
mvn spring-boot:run -Dapplication.greeting.message="Hello from commandline"
```

## On PCF

Environment variables can be updated in PCF in a variety of ways including via GUI, Manifest, and commandline.

To update via the GUI (AppsMan), configuration is under Application Settings -> User Provided Environment Variables. 

To update via the commandline run:

```sh
cf set-env config-demo application.greeting.message "Greetings from PCF env variable"
```

This option can be used for sensitive data such as passwords, preventing them from being available in Source Code, and the Deployable Artifact.

## Note the Order of Precedence 
https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html 

# 3. Spring Profiles 

In addition to application.properties, Spring will load additional application-XX.properties files based on the spring.profiles.active environment variable.

In PCF, this is set to **cloud** by default, meaning application-cloud.properties will be loaded as well. 

To test this locally:
```sh
mvn spring-boot:run -Dspring.profiles.active=qa
```

> Tip - that you can have multiple Active properties enabled

# 4. Marketplace Services 

For storing passwords to Services available in the CF Marketplace, consider just using the Service Binding for password management. 

To view available services:

```sh
cf marketplace
```

# 5. Custom User Provided Services (CUPS)

For Services not available in the CF Marketplace, and where you don't want to use environment variables, and potentially you want to share services between application consider Custom User Provided Services (CUPS).

https://docs.cloudfoundry.org/devguide/services/user-provided.html

This will emulate a Service for you in CF, though you will still be responsible for updating your code to load and use the credentials.

To create the Service:

```sh
cf cups my-custom-service -p '{"username":"admin","password":"pa55woRD"}'
```

You will also need to bind the Service to your application:

```sh
cf bind-service config-demo my-custom-service
```

This setting can then be loaded via VCAP environment variables in your source code, for example:

```java

```

# 6. Config Server
- Requires Spring Cloud Client 
- Default Address is http://localhost:8888
- Marketplace binding will point to correct one

# 7. Refresh Actuator Endpoint <- not that it hits only one instance  .. requires Refresh Scope 
- Requires RefreshScope Annotation
- Requires an update in code



```sh
cf create-service p-config-server traile myconfig
```

#### Bind the Service to your Application

```sh
cf bind-service metrics-demo myforwarder
```

# 8. Vault via PCF
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
cf create-service p-config-server traile myconfig
```

#### Bind the Service to your Application

```sh
cf bind-service metrics-demo myforwarder
```

#### Restage your Application

```sh
cf restage metrics-demo
```
