@helper
Feature: Removing an existing user account.

  Scenario:
    * print userAccountId
    * def targetPath = '/api/v1/user/' + userAccountId
    Given path targetPath
    And method delete
    Then status 200