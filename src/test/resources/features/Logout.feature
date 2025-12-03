Feature: User Logout Functionality
  As a logged-in user
  I want to logout from the Contact List application
  So that I can end my session securely

  Background:
    Given I am on the login page
    When I enter valid email "john.test.doe@test.com" and password "SecurePass123"
    And I click the submit button
    Then I should be logged in successfully

  @smoke @logout
  Scenario: Successful logout
    When I click the logout button
    Then I should be logged out successfully
    And I should be redirected to the login page
