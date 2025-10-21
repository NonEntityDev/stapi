@helper
Feature: Reusable feature to delete an existing client account.

  Scenario:
    Given path "/api/v1/clients/" + clientId
    When method delete
    Then status 200