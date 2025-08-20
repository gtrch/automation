Feature: Login

  Background: Login screen
    And location permission is granted

  Scenario: User logs in with valid credentials
    When the user enters valid credentials
    And taps the login button
    Then the user should be logged in