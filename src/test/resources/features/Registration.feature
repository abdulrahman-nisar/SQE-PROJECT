Feature: User Registration Functionality
  As a new user
  I want to register for a Contact List account
  So that I can access the application

  Background:
    Given I am on the login page
    When I click the signup button

  @smoke @registration
  Scenario: Successful registration with valid details
    When I enter registration details:
      | firstName | John                     |
      | lastName  | Doe                      |
      | email     | john.test.5.doe@test.com |
      | password  | SecurePass123            |
    And I submit the registration form
    Then I should be registered successfully
    And I should see the contact list page

  @smoke @negative @registration @mine
  Scenario: Registration with existing email
    When I enter registration details with an existing email
    Then I should see an error message indicating email already exists

  @smoke @negative @registration
  Scenario: Registration with invalid email format
    When I enter an invalid email format "notanemail"
    And I fill other registration fields correctly
    And I submit the registration form
    Then I should see an email validation error

  @smoke @negative @registration
  Scenario: Registration with missing required fields
    When I leave required fields empty
    And I submit the registration form
    Then I should see validation errors for required fields

