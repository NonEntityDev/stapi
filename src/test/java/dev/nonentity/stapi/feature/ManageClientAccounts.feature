Feature: Managing client accounts.

  Background:
    * configure headers = { Content-Type: 'application/json', Accept: 'application/json' }
    * def clientAccounts = call read("client/listAll-clientAccount.feature")
    * def idsToDelete = karate.map(clientAccounts.response, function(entry) { return entry.clientId; })
    * karate.forEach(idsToDelete, function(id) { karate.call("client/remove-clientAccount.feature", { clientId: id }); })

  @client_account @list
  Scenario: Retrieving all client accounts available.
    Given path "/api/v1/clients"
    When method get
    Then status 200
    And match response == []

    * def clientPayloadB =
      """
      {
        title: "Test Client B",
        description: "Client created during tests.",
        alias: "client.b",
        clientSecret: "password",
        scopes: ["client.all"],
        parameters: [
          { name: "TEMPLATE", value: "Insert Mustache template here." }
        ]
      }
      """
    * def clientB = call read("client/create-clientAccount.feature") { payload: #(clientPayloadB) }

    * def clientPayloadA =
      """
      {
        title: "Test Client A",
        description: "Client created during tests.",
        alias: "client.a",
        clientSecret: "password",
        scopes: ["client.all"],
        parameters: [
          { name: "TEMPLATE", value: "Insert Mustache template here." }
        ]
      }
      """
    * def clientA = call read("client/create-clientAccount.feature") { payload: #(clientPayloadA) }

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
        title: "Test Client",
        description: "Client created during the tests.",
        alias: "TEST",
        scopes: [
          "message.send",
          "client.all"
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
        title: "Test Client",
        description: "Client created during the tests.",
        alias: "TEST",
        clientSecret: "password",
        scopes: [
          "message.send",
          "client.all"
        ],
        parameters: [
          { "name": "TEMPLATE", "value": "Insert mustache template here." },
          { "name": "TEMPLATE", "value": "Another parameter to insert mustache template." }
        ]
      }
      """
    And path "/api/v1/clients"
    When method post
    Then status 400
    And match response.type == "CreateClientAccount"
    * match karate.jsonPath(response, "$.violations[?(@.field=='parameters')].messages") == [["duplicated parameters: TEMPLATE"]]

    Given request
      """
      {
        title: "Test Client",
        description: "Client created during the tests.",
        alias: "TEST",
        clientSecret: "password",
        scopes: [
          "message.send",
          "client.all"
        ],
        parameters: [
          { "name": "TEMPLATE", "value": "Insert mustache template here." }
        ]
      }
      """
    And path "/api/v1/clients"
    When method post
    Then status 200
    And match response contains { clientId: "#present", clientSecret: "#notpresent", createdAt: "#present", updatedAt: "#present" }
    And match response.title == "Test Client"
    And match response.description == "Client created during the tests."
    And match response.alias == "TEST"
    And match response.scopes contains only ["message.send", "client.all"]
    And match response.parameters == [{ "name": "TEMPLATE", "value": "Insert mustache template here.", "modifiedAt": "#present" }]

    Given request
      """
      {
        title: "Test Client",
        description: "Client created during the tests.",
        alias: "TEST",
        clientSecret: "password",
        scopes: [
          "message.send",
          "client.all"
        ],
        parameters: [
          { "name": "TEMPLATE", "value": "Insert mustache template here." }
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
    * def clientPayload =
      """
      {
        title: "Test Client",
        description: "Client created during tests.",
        alias: "TEST_REMOVAL",
        clientSecret: "password",
        scopes: ["client.all"],
        parameters: [
          { name: "TEMPLATE", value: "Insert Mustache template here." }
        ]
      }
      """
    * def client = call read("client/create-clientAccount.feature") { payload: #(clientPayload) }

    Given path "/api/v1/clients/" + client.response.clientId
    When method delete
    Then status 200
    And match response.clientId == client.response.clientId

    Given path "/api/v1/clients/" + client.response.clientId
    When method delete
    Then status 404
    And match response.message == "Client account not found."

  @client_account @update
  Scenario: Updating existing client account.
    * def clientPayload =
      """
      {
        title: "Test Client",
        description: "Client created during tests.",
        alias: "TEST_UPDATE",
        clientSecret: "password",
        scopes: ["client.all"],
        parameters: [
          { name: "TEMPLATE", value: "Insert Mustache template here." }
        ]
      }
      """
    * def client = call read("client/create-clientAccount.feature") { payload: #(clientPayload) }

    Given request
      """
      {
        title: "Updated Test Client",
        description: "Client updated during tests.",
        alias: ""
      }
      """
    And path "/api/v1/clients/" + client.response.clientId
    When method put
    Then status 400
    And match response.type == "UpdateClientAccount"
    * match karate.jsonPath(response, "$.violations[?(@.field=='alias')].messages") == [["length must be between 3 and 40"]]

    Given request
      """
      {
        title: "Updated Test Client",
        description: "Client updated during tests.",
        alias: "TEST_UPDATED",
        parameters: [
          { name: "TEMPLATE", value: "Insert Mustache template here." },
          { name: "TEMPLATE", value: "Another template parameter." }
        ]
      }
      """
    And path "/api/v1/clients/" + client.response.clientId
    When method put
    Then status 400
    And match response.type == "UpdateClientAccount"
    * match karate.jsonPath(response, "$.violations[?(@.field=='parameters')].messages") == [["duplicated parameters: TEMPLATE"]]

    Given request
      """
      {
        title: "Updated Test Client",
        description: "Client updated during tests.",
        alias: "TEST_UPDATED",
        parameters: [
          { name: "TEMPLATE", value: "Insert Mustache template here." }
        ]
      }
      """
    And path "/api/v1/clients/" + client.response.clientId
    When method put
    Then status 200
    And match response.clientId == client.response.clientId
    And match response.title == "Updated Test Client"
    And match response.description == "Client updated during tests."
    And match response.alias == "TEST_UPDATED"
    And match response.parameters == [{ name: "TEMPLATE", value: "Insert Mustache template here.", modifiedAt: "#present" }]
    And match response contains { clientSecret: "#notpresent", createdAt: "#present", updatedAt: "#present" }

    * def anotherClientPayload =
      """
      {
        title: "Test Client",
        description: "Client created during tests.",
        alias: "UPDATE",
        clientSecret: "password",
        scopes: ["client.all"],
        parameters: [
          { name: "TEMPLATE", value: "Insert Mustache template here." }
        ]
      }
      """
    * def anotherClient = call read("client/create-clientAccount.feature") { payload: #(anotherClientPayload) }

    Given request
      """
      {
        title: "Updated Test Client",
        description: "Client updated during tests.",
        alias: "UPDATE",
        parameters: [
          { name: "TEMPLATE", value: "Insert Mustache template here." }
        ]
      }
      """
    And path "/api/v1/clients/" + client.response.clientId
    When method put
    Then status 400
    And match response.type == "UpdateClientAccount"
    * match karate.jsonPath(response, "$.violations[?(@.field=='alias')].messages") == [["already exists"]]

    Given request
      """
      {
        title: "Updated Test Client",
        description: "Client updated during tests.",
        alias: "NOT_FOUND",
        parameters: [
          { name: "TEMPLATE", value: "Insert Mustache template here." }
        ]
      }
      """
    And path "/api/v1/clients/0e12e4ed-c3df-47c1-bf66-155fa00d3f8d"
    When method put
    Then status 404
    And match response.message == "Client account not found."

  @client_account @update_credentials
  Scenario: Updating client account credentials.
    * def clientPayload =
      """
      {
        title: "Test Client A",
        description: "Client created during tests.",
        alias: "client.a",
        clientSecret: "password",
        scopes: ["client.all"],
        parameters: [
          { name: "TEMPLATE", value: "Insert Mustache template here." }
        ]
      }
      """
    * def client = call read("client/create-clientAccount.feature") { payload: #(clientPayload) }

    Given request
      """
      {
        scopes: [],
        secret: "password"
      }
      """
    And path "/api/v1/clients/" + client.response.clientId + "/credentials"
    When method put
    Then status 400
    And match response.type == "UpdateClientAccountCredentials"
    * match karate.jsonPath(response, "$.violations[?(@.field=='scopes')].messages") == [["must not be empty"]]

    Given request
      """
      {
        scopes: ["SEND_MESSAGE"],
        secret: "updatedPassword"
      }
      """
    And path "/api/v1/clients/" + client.response.clientId + "/credentials"
    When method put
    Then status 200
    And match response.clientId == client.response.clientId
    And match response contains { scopes: ["SEND_MESSAGE"], clientSecret: "#notpresent" }

    Given request
      """
      {
        scopes: ["SEND_MESSAGE"],
        secret: "updatedPassword"
      }
      """
    And path "/api/v1/clients/0e12e4ed-c3df-47c1-bf66-155fa00d3f8d/credentials"
    When method put
    Then status 404
    And match response.message == "Client account not found."