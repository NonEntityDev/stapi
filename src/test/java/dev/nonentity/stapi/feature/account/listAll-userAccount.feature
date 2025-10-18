@helper
Feature: Listing all user accounts available.

  Scenario:
    Given path "/api/v1/user"
    And method get
    Then status 200