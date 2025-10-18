Feature: Managing application accounts.

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
