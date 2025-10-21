Feature: Managing client accounts.

  Background:
    * def clientAccounts = call read("client/listAll-clientAccount.feature")
    * def idsToDelete = karate.map(clientAccounts.response, function(entry) { return entry.clientId; })
    * karate.forEach(idsToDelete, function(id) { karate.call("client/remove-clientAccount.feature", { clientId: id }); })

  @client_account @list
  Scenario: Retrieving all client accounts available.
    Given path "/api/v1/clients"
    When method get
    Then status 200
    And match response == []

    * def clientB = call read("client/create-clientAccount.feature") { title: "Test Client B", description: "Client created during the tests.", alias: "client.b", clientSecret: "password", scope: "MANAGE_CLIENTS" }
    * match clientB.responseStatus == 200

    * def clientA = call read("client/create-clientAccount.feature") { title: "Test Client A", description: "Client created during the tests.", alias: "client.a", clientSecret: "password", scope: "MANAGE_CLIENTS" }
    * match clientA.responseStatus == 200

    Given path "/api/v1/clients"
    When method get
    Then status 200
    And match response[0].clientId == clientA.response.clientId
    And match response[0] contains { clientSecret: "#notpresent" }
    And match response[1].clientId == clientB.response.clientId
    And match response[1] contains { clientSecret: "#notpresent" }

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

  @client_account @remove
  Scenario: Removing a client account.
    * def client = call read("client/create-clientAccount.feature") { title: "Test Client", description: "Client created during the tests.", alias: "TEST_REMOVAL", clientSecret: "password", scope: "MANAGE_CLIENTS" }
    * match client.responseStatus == 200

    Given path "/api/v1/clients/" + client.response.clientId
    When method delete
    Then status 200
    And match response.clientId == client.response.clientId

    Given path "/api/v1/clients/" + client.response.clientId
    When method delete
    Then status 404
    And match response.message == "Client account not found."
