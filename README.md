# Spring Boot DynamoDB CRUD Application

A simple CRUD application using Spring Boot and AWS DynamoDB.

## Requirements

- **Java 17** or later
- **Maven**
- AWS credentials for DynamoDB access

## Steps to Run

1. **Clone the project:**

   ```bash
   git clone https://github.com/sambanna07/spring-boot-dynamo-db-crud.git
   cd spring-boot-dynamo-db-crud
   ```

2. **Configure AWS credentials:**

   In `src/main/resources/application.yml`, replace the placeholders with your AWS details:
   aws.dynamodb.accessKey=REPLACE_WITH_YOUR_AWS_ACCESS_KEY
   aws.dynamodb.secretKey=REPLACE_WITH_YOUR_AWS_SECRET_KEY
   aws.dynamodb.region=<DYNAMODB-REGION>
   aws.dynamodb.endpoint=dynamodb.<DYNAMODB-REGION>.amazonaws.com

3. **Build the project:**
   mvn clean install

4. **Run the application:**
   mvn spring-boot:run

   The application will be available at `http://localhost:8080`.
