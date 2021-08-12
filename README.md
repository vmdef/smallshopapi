# Small Shop API
Small Shop API is a REST API to manage customer data for a small shop. It will  work  as  the  backend  side  for  a  CRM  interface.
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
