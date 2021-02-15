## Project Information

The task is to develop a Web application to manage Post-It notes.  A user should be able to:

 - Create a note 
 - Update a note 
 - Delete a note 
 - View a list of notes

### IMPLEMENTATION

The project has been implemented as Spring boot web Rest API.   API has been created using HATEOAS - Hypermedia as the Engine of Application State principles.  

The endpoints for performing above tasks are as follows -
http://localhost:8080/notes
http://localhost:8080/notes/{noteId}
http://localhost:8080/notes/{noteId}
http://localhost:8080/notes
http://localhost:8080/notes/{noteId}

### Example CURL commands

 - Create a note -
`curl --location --request POST 'http://localhost:8080/notes' \
--header 'Content-Type: application/json' \
--data-raw '{
"text" : "Test Note 2"
}'`

 - Update a note
`curl --location --request PUT 'http://localhost:8080/notes/1'--header 'Content-Type: application/json' --data-raw '{"id": 1,"text": "Test Note 1" }'`
 
 - Delete note
`curl --location --request DELETE 'http://localhost:8080/notes/1'`
 
 - View a list of notes
 ` curl --location --request GET 'http://localhost:8080/notes`
 
 - View a note based upon id
 `curl --location --request GET 'http://localhost:8080/notes/1'`

 #### Above commands can also be executed via GUI tools like POSTMAN and SOAP UI.  Nominet.postman_collection.json is included in the project base directory that can directly be imported in PostMan.

### Other details

 - Technology stack is Java 8, Spring Boot 2.4.2, JPA, H2 database, Maven, JUnit, Cucumber.
 - To build and test the application run following command from project base directory 
 `mvn clean install`
 - To run the application run following command from project base directory -`mvn spring-boot:run`
OR
`java -jar target/Nominet-0.0.1-SNAPSHOT.jar`
  
 - Notes are saved in database using JPA framework.
 - Currently in memory database h2 is being used.  While the application is running, one can connect to database by using following url - `_http://localhost:8080/h2-console` .  The default JDBC URL is `jdbc:h2:mem:nominetdb` , user name is `sa` and there is no password, i.e. it is blank.
 - Functional test cases have been implemented using Spring Boot Test and Cucumber framework.
