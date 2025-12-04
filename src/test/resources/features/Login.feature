Feature: User Login Functionality
  As a user
  I want to login to the Contact List application
  So that I can manage my contacts

  @smoke @login @database
  Scenario: Successful login with credentials from Database
    Given I am on the login page
    When I enter credentials from database for email "john.test.2.doe@test.com"
    And I click the submit button
    Then I should be logged in successfully

  @smoke @login @excel
  Scenario: Successful login with credentials from Excel
    Given I am on the login page
    When I enter credentials from excel row 0
    And I click the submit button
    Then I should be logged in successfully

  @smoke @login @redis
  Scenario: Successful login with credentials from Redis
    Given I am on the login page
    When I enter credentials from redis for email "john.test.2.doe@test.com"
    And I click the submit button
    Then I should be logged in successfully

  @negative @login @database
  Scenario: Login with invalid credentials from Database
    Given I am on the login page
    When I enter credentials from database for email "invalid@example.com"
    And I click the submit button
    Then I should see an error message
    And I should remain on the login page

  @negative @login @excel
  Scenario: Login with invalid credentials from Excel
    Given I am on the login page
    When I enter credentials from excel row 1
    And I click the submit button
    Then I should see an error message
    And I should remain on the login page

