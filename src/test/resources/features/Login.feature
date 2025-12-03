Feature: User Login Functionality
  As a user
  I want to login to the Contact List application
  So that I can manage my contacts

  @smoke @login
  Scenario: Successful login with registered user
    Given I am on the login page
    When I enter valid email "john.test.2.doe@test.com" and password "SecurePass123"
    And I click the submit button
    Then I should be logged in successfully

  @smoke @negative @login
  Scenario: Login with invalid credentials
    Given I am on the login page
    When I enter invalid email "invalid@example.com" and password "wrongpassword"
    And I click the submit button
    Then I should see an error message
    And I should remain on the login page

