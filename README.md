# tire-shop-web-backend

Tire Shop REST API for use with React web rewrite

## To run the application:

1. Build with maven

```
mvn clean package
```

2. run jar, passing DB properties as VM parameters:
```
java \
    -Dspring.datasource.url=jdbc:xyz \
    -Dspring.datasource.username=xyz \
    -Dspring.datasource.password=xyz \
    -jar target/tire-shop-web-backend-0.0.1-SNAPSHOT.jar
```

3. access the services at http://localhost:8080/


## Authorization
### Users
Users are stored in the `employee2` table. 
Possible roles are `ADMIN` and `EMPLOYEE`. 
New `EMPLOYEE` users can be created with the `/signup` service, 
which is only accessible to `ADMIN` users. 
New `ADMIN` users must be created in the database.

### Authentication
The services require username/password via Basic Authentication (browser pop-up or `Authorization` header) 

## Available services

### Rest Repository services
The `/` URL will show the available Spring Data Rest Repo services, which are available to any authenticated user:

```
{
"_links" : {
"lineitems" : {
"href" : "http://localhost:8080/lineitems{?projection}",
"templated" : true
},
"products" : {
"href" : "http://localhost:8080/products{?page,size,sort}",
"templated" : true
},
"customers" : {
"href" : "http://localhost:8080/customers"
},
"orders" : {
"href" : "http://localhost:8080/orders{?page,size,sort}",
"templated" : true
},
"employees" : {
"href" : "http://localhost:8080/employees"
},
"profile" : {
"href" : "http://localhost:8080/profile"
}
}
}
```

### Other services
In addition to the Rest Repo services, there are two more services:

#### POST /signup
To create a user with EMPLOYEE role, POST to  `/signup` and pass credentials for an existing user with ADMIN role
```
POST http://localhost:8080/signup
Authorization: Basic USER PASS
Content-Type: application/json

{
  "username": "dude2",
  "password": "pass",
  "fullName": "Another Dude"
}
```

#### GET /order-details/{orderId}
To get Order details including Customer and an array of Products/Counts,
GET `/order-details/{orderId}`

Requiwith an existing user with EMPLOYEE role
