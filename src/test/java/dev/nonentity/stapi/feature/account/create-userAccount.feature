@helper
Feature: Creating a new user account.

  Scenario:
    * def payload = { fullName: '#(fullName)', login: '#(login)', password: '#(password)' }
    Given request payload
    And path "/api/v1/user"
    And method post

