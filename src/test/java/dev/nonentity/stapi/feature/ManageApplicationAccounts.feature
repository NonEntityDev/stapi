Feature: Managing application accounts.

  Background:
    * def applicationAccounts = call read("account/listAll-applicationAccount.feature")
    * def ids = karate.map(applicationAccounts.response, function(entry) { return entry.clientId })
    * karate.forEach(ids, function(id) { karate.call("account/remove-applicationAccount.feature", { clientId: id }); })

  @application_account @create
  Scenario: Create a new application account.
    Given request
      """
      {
        "name": "Email Watcher"
      }
      """
    And path "/api/v1/application"
    When method post
    Then status 400
    And match response.type == "CreateApplicationAccount"
    * match karate.jsonPath(response, "$.violations[?(@.field=='alias')].messages") == [["must not be null"]]

    Given request
      """
      {
        "name": "Email Watcher",
        "alias": "email_watcher"
      }
      """
    And path "/api/v1/application"
    When method post
    Then status 200
    * match response contains { clientId: "#present", clientSecret: "#present", createdAt: "#present", updatedAt: "#present" }
    * match response.name == "Email Watcher"
    * match response.alias == "email_watcher"
    * match response.enabled == true

    Given request
      """
      {
        "name": "Email Watcher",
        "alias": "email_watcher"
      }
      """
    And path "/api/v1/application"
    When method post
    Then status 400
    * match response["type"] == "CreateApplicationAccount"
    * match karate.jsonPath(response, "$.violations[?(@.field=='alias')].messages") == [["already exists"]]

  @application_account @retrieve
  Scenario: Retrieving application account by id.
    * def appAccount = call read("account/create-applicationAccount.feature") { name: "LinkedIN Watcher", alias: "linkedin_watcher" }
    * match appAccount.responseStatus == 200
    Given path "/api/v1/application/" + appAccount.response.clientId
    When method get
    Then status 200
    And match response ==
    """
    {
      clientId: #(appAccount.response.clientId),
      name: #(appAccount.response.name),
      alias: #(appAccount.response.alias),
      enabled: #(appAccount.response.enabled),
      clientSecret: '#notpresent',
      createdAt: '#present',
      updatedAt: '#present'
    }
    """

    Given path "/api/v1/application/b2c1d203-8cf5-409d-afcc-d7247921af90"
    When method get
    Then status 404

  @application_account @list
  Scenario: Retrieving all application accounts available
    Given path "/api/v1/application"
    And method get
    Then status 200
    And match response == []

    * def appB = call read("account/create-applicationAccount.feature") { name: "Application B", alias: "application_b" }
    * match appB.responseStatus == 200

    * def appA = call read("account/create-applicationAccount.feature") { name: "Application A", alias: "application_a" }
    * match appA.responseStatus == 200

    Given path "/api/v1/application"
    When method get
    Then status 200
    And match response[0].clientId == appA.response.clientId
    And match response[0].clientSecret == "#notpresent"
    And match response[1].clientId == appB.response.clientId
    And match response[1].clientSecret == "#notpresent"
