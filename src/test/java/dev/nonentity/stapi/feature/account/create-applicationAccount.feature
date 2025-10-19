@helper
Feature: Creating a new application account.

  Scenario:
    * def payload = { name: '#(name)', alias: '#(alias)' }
    Given request payload
    And path "/api/v1/application"
    And method post

