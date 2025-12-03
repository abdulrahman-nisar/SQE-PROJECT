Feature: Contact Management Functionality
  As a logged-in user
  I want to manage my contacts
  So that I can keep track of my contact information

  Background:
    Given I am on the login page
    When I enter valid email "john.test.2.doe@test.com" and password "SecurePass123"
    And I click the submit button
    Then I should be logged in successfully

  @smoke @contact
  Scenario: Add new contact with basic details
    When I click add new contact button
    And I enter contact first name "Alice" and last name "Smith"
    And I submit the contact form
    Then the contact should be added successfully
    And I should see the contact in the list

  @smoke @contact
  Scenario: Add new contact with full details
    When I click add new contact button
    And I enter complete contact details:
      | firstName   | Bob                  |
      | lastName    | Johnson              |
      | birthdate   | 1990-05-15          |
      | email       | bob.johnson@test.com |
      | phone       | 5551234567          |
      | street1     | 123 Main St         |
      | street2     | Apt 4B              |
      | city        | New York            |
      | stateProvince | NY                |
      | postalCode  | 10001               |
      | country     | USA                 |
    And I submit the contact form
    Then the contact should be added successfully
    And I should see the contact in the list

  @smoke @contact
  Scenario: View contact details
    When I click add new contact button
    And I enter contact first name "Frank" and last name "Miller"
    And I submit the contact form
    And I click on the first contact in the list
    Then I should see the contact details page
    And all contact information should be displayed correctly

  @smoke @contact
  Scenario: Delete contact
    When I click add new contact button
    And I enter contact first name "Grace" and last name "Hopper"
    And I submit the contact form
    And I click on the first contact in the list
    When I click the delete button
    And I confirm the deletion
    Then the contact should be removed from the list
    And I should be redirected to the contact list page


