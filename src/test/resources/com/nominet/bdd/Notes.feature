Feature: General nominet note functionalituy
  
  Scenario: User saves a note
    When a user posts a note
    Then the user receives status code of 201
    And the note is successfully saved

  Scenario: User retreives note
    When a user attempts to retrieve a note by providing id
    Then the user receives status code of 200
    And the note is successfully retrieved

  Scenario: User updates note
    When a user attempts to retrieve a note by providing id
    And then updates the note text with My Note Text-Modified
    Then the user receives status code of 200
    And the note is successfully updated
        
  Scenario: User deletes note
    When a user attempts to delete a note by providing id
    Then the user receives status code of 200
    And the note is successfully deleted       
 