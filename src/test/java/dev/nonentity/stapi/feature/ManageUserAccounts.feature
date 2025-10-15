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

  @user_account
  Scenario: Retrieving user account by id.
    # GIVEN I request to create a new user account providing valid details to all required fields
    Given request
      """
      {
        "fullName": "Application Manager",
        "login": "app.manager",
        "password": "password"
      }
      """
    And path "/api/v1/user"
    And method post
    # THEN the user account is successfully created
    Then status 200
    # AND the response contains the user account id
    * def userAccountId = response["id"]

    # WHEN I request to retrieve an user account using this id
    Given path "/api/v1/user/" + userAccountId
    And method get
    # THEN the request is accepted
    Then status 200
    # AND the response contains the non-sensible details of the user account
    * match response contains { id: "#present" }
    * match response["fullName"] == "Application Manager"
    * match response["login"] == "app.manager"
    * match response["enabled"] == true
    # AND there is no password or secret exposed in the response.
    * match response !contains { password: "#present" }
    * match response !contains { secret: "#present" }

    # WHEN I request to retrieve an user account using an invalid id
    Given path "/api/v1/user/b2c1d203-8cf5-409d-afcc-d7247921af90"
    And method get
    # THEN the request is rejected with a not found status
    Then status 404