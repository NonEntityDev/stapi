@helper
Feature: Listing all application accounts available.

  Scenario:
    Given path "/api/v1/application"
    And method get
    Then status 200