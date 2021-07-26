## Setup
Change the datasource URL, the username, and the password for the local MySQL server in application.properties

## Execution
Run the application using the following command:

```
  mvnw spring-boot:run
```

## Testing
The following command adds a user: 

```
curl localhost:8080/demo/add -d name=First -d email=someemail@someemailprovider.com
```

The following command shows all users:

```
curl localhost:8080/demo/all
```
