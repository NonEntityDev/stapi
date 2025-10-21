@helper
Feature: Reusable feature to create a client account.

  Scenario:
    Given request
      """
      {
        "title": "#(title)",
        "description": "#(description)",
        "alias": "#(alias)",
        "clientSecret": "#(clientSecret)",
        "scopes": ["#(scope)"]
      }
      """
    And path "/api/v1/clients"
    And method post