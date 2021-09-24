# Small Shop API
Small Shop API is a REST API to manage customer data for a small shop. It will  work  as  the  backend  side  for  a  CRM  interface.
## Disclaimer
This is still a Work In Progress. Do not assume that the chosen approaches and their implementation are correct. This project is a first contact with Java, Spring and Spring Boot, so mistakes are around and there is room for improvement.
## Features
- The API is only accessible by a registered user.
- A user can (basic CRUD):
  -  List all customers in the database.
  -  Get full customer information, including a photo URL.
  -  Create a new customer.
  -  Update an existing customer.
  -  Delete an existing customer. 
- An admin can Create, List, Update and Delete users, and change the admin status of other users.
## API documentation
The API specification is written using version 3.0.3 of OAS (https://github.com/OAI/OpenAPI-Specification/blob/master/versions/3.0.3.md), the Open API Specification. The specification file in YAML format is located in **docs/api/openapi.yml**.
To check the documentation:
1. Clone this project.
2. In a web browser, load **docs/api/index.html**.
## Run Locally
### Manually
Clone the project
```bash
  git clone https://github.com/vmdef/smallshopapi.git
```
Go to the project directory
```bash
  cd smallshopapi/smallshop
```
Built the app

```bash
  ./gradlew clean build  
```

Start the server

```bash
  java -jar build/libs/smallshop-0.0.1-SNAPSHOT.jar 
```
### Using docker
Build an image with the following command
```bash
docker build -t myorg/smallshop .
```

Then we can run it by running the following command:
```bash
docker run -p 8080:8080 myorg/smallshop
```
## Testing the application
Automated tests have not been fully implemented yet:(
### Postman
Import the collection *postmanCollection/SmallShopAPI.postman_collection.json*.
There are 3 folders:
- Authentication: to authenticate as an admin or regular user.
- Customers: customers related tests (you have to authenticate before).
- Users: users related tests (you have to authenticate as admin user before).

## Database
The app uses an in-memory H2 database. You can launch H2 console at http://localhost:8080/h2-console. Change the default JDBC URL to *jdbc:h2:mem:smallshop*
