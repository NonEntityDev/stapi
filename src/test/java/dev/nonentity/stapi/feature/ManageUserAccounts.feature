Feature: Managing user accounts.

  Background:
    * configure url = "http://localhost:8080"

  @user_account
  Scenario: Creating a valid user account.
    # GIVE I request to create a new user account without providing valid details for all the required fields
    Given request
      """
      {
        "fullName": "Administrator",
        "password": "password"
      }
      """
    And path "/api/v1/user"
    When method post
    # THEN the request is rejected
    Then status 400
    # AND the response reports that the login field of CreateUserAccount request can't be null
    * match response["type"] == "CreateUserAccount"
    * match karate.jsonPath(response, "$.violations[?(@.field=='login')].messages") == [["must not be null"]]

    # WHEN I request to create a new user account providing valid details for all required fields
    Given request
      """
      {
        "fullName": "Administrator",
        "login": "admin",
        "password": "password"
      }
      """
    And path "/api/v1/user"
    And method post
    # THEN the user account is successfully created
    Then status 200
    # AND the response contains the non-sensible user account details
    * match response contains { id: "#present" }
    * match response["fullName"] == "Administrator"
    * match response["login"] == "admin"
    * match response["enabled"] == true
    # AND there is no password or secret exposed in the response.
    * match response !contains { password: "#present" }
    * match response !contains { secret: "#present" }

    # WHEN I request to create a new user account using a login that already exists
    Given request
      """
      {
        "fullName": "Administrator Again",
        "login": "admin",
        "password": "differentPassword"
      }
      """
    And path "/api/v1/user"
    And method post
    # THEN the request is rejected
    Then status 400
    # AND the response reports that the login already exists
    * match response["type"] == "CreateUserAccount"
    * match karate.jsonPath(response, "$.violations[?(@.field=='login')].messages") == [["already exists"]]
