@helper
Feature: Reusable feature to create list client accounts.

  Scenario:
    Given path "/api/v1/clients"
    When method get
    Then status 200
