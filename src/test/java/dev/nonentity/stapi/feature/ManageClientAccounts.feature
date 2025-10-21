Feature: Managing client accounts.

  @client_account @created
  Scenario: Creating a new client account.
    Given request
      """
      {
        "title": "Test Client",
        "description": "Client created during the tests.",
        "alias": "TEST",
        "scopes": [
          "CLIENT_MANAGEMENT",
          "SEND_MESSAGE"
        ]
      }
      """
    And path "/api/v1/clients"
    When method post
    Then status 400
    And match response.type == "CreateClientAccount"
    * match karate.jsonPath(response, "$.violations[?(@.field=='clientSecret')].messages") == [["must not be blank"]]

    Given request
      """
      {
        "title": "Test Client",
        "description": "Client created during the tests.",
        "alias": "TEST",
        "clientSecret": "password",
        "scopes": [
          "CLIENT_MANAGEMENT",
          "SEND_MESSAGE"
        ]
      }
      """
    And path "/api/v1/clients"
    When method post
    Then status 200
    And match response contains { clientId: "#present", clientSecret: "#present", createdAt: "#present", updatedAt: "#present" }
    And match response.title == "Test Client"
    And match response.description == "Client created during the tests."
    And match response.alias == "TEST"
    And match response.scopes contains only ["CLIENT_MANAGEMENT", "SEND_MESSAGE"]

    Given request
      """
      {
        "title": "Test Client",
        "description": "Client created during the tests.",
        "alias": "TEST",
        "clientSecret": "password",
        "scopes": [
          "CLIENT_MANAGEMENT",
          "SEND_MESSAGE"
        ]
      }
      """
    And path "/api/v1/clients"
    When method post
    Then status 400
    And match response.type == "CreateClientAccount"
    * match karate.jsonPath(response, "$.violations[?(@.field=='alias')].messages") == [["already exists"]]

