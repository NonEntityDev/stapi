@helper
Feature: Removing an existing application account.

  Scenario:
    * def targetPath = '/api/v1/application/' + clientId
    Given path targetPath
    And method delete
    Then status 200