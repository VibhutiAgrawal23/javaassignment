For successful run of this project we need to connect to the database goal

Run the following command on the mysql
Create database goal;
use goal;
Create table goals(GoalId varchar(5) primary key,title varchar(20),details varchar(100),eta Integer,createDate Date, updateDate Date);
commit;

We are ready to perform all the CRUD operations on our Goal Project.

For testing rest APIs, use rest client like postman or curl

For Swagger Documentation:
URL:localhost:8080/swagger-ui.html
