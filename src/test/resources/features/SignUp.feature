Feature: User Sign Up

  Scenario: Successful signup with valid credentials
    Given location permission is granted
    When the user navigates to sign up
    And enters new user information
    And submits the signup form
    And taps the sign up button
    Then a confirmation message should be displayed