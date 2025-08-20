Feature: Add Beneficiary
  Background: Login screen
    And location permission is granted

  Scenario: Log in and add a new beneficiary with unique data
    Given the user is logged in and starts the Add Beneficiary flow
    When the user fills in the beneficiary form