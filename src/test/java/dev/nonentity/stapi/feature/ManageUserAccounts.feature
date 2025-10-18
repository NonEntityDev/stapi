Feature: Managing user accounts.

  Background:
    # Wiping out any existing user account before proceed.
    * def userAccounts = call read("account/listAll-userAccount.feature")
    * def ids = karate.map(userAccounts.response, function(entry) { return entry.id })
    * karate.forEach(ids, function(id) { karate.call("account/remove-userAccount.feature", { userAccountId: id }); })

  @user_account @create
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
    And match response.type == "CreateUserAccount"
    * match karate.jsonPath(response, "$.violations[?(@.field=='login')].messages") == [["must not be null"]]

    # WHEN I request to create a new user account providing valid details for all required fields
    Given request
      """
      {
        "fullName": "Administrator",
        "login": "admin@domain.com",
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
    * match response["login"] == "admin@domain.com"
    * match response["enabled"] == true
    # AND there is no password or secret exposed in the response.
    * match response !contains { password: "#present" }
    * match response !contains { secret: "#present" }

    # WHEN I request to create a new user account using a login that already exists
    Given request
      """
      {
        "fullName": "Administrator Again",
        "login": "admin@domain.com",
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

  @user_account @retrieve
  Scenario: Retrieving user account by id.
    # GIVEN I request to create a new user account providing valid details to all required fields
    * def userAccount = call read("account/create-userAccount.feature") { fullName: "admin", login: "admin@domain.com", password: "password" }

    # WHEN I request to retrieve an user account using this id
    Given path "/api/v1/user/" + userAccount.response.id
    And method get
    # THEN the request is accepted
    Then status 200
    # AND the response contains the non-sensible details of the user account
    * match response == userAccount.response
    # AND there is no password or secret exposed in the response.
    * match response !contains { password: "#present" }
    * match response !contains { secret: "#present" }

    # WHEN I request to retrieve an user account using an invalid id
    Given path "/api/v1/user/b2c1d203-8cf5-409d-afcc-d7247921af90"
    And method get
    # THEN the request is rejected with a not found status
    Then status 404

  @user_account @list
  Scenario: Retrieving all user accounts available
    # GIVEN I request to retrieve all user accounts available
    Given path "/api/v1/user"
    And method get
    # THEN the request is accepted
    Then status 200
    # AND the response is an empty list
    * match response == []

    # GIVEN I create an user account with full name ending with B
    * def userB = call read("account/create-userAccount.feature") { fullName: "User B", login: "user.b@domain.com", password: "password" }
    * match userB.responseStatus == 200
    # AND I create an user account with full name ending with A
    * def userA = call read("account/create-userAccount.feature") { fullName: "User A", login: "user.a@domain.com", password: "password" }
    * match userA.responseStatus == 200
    # WHEN I request to retrieve all the user accounts available
    Given path "/api/v1/user"
    When method get
    # THEN the response contains the user account sorted alphabetically
    Then status 200
    * print response
    And match response[0] == userA.response
    And match response[1] == userB.response
    # AND there is no secret or password included in the response
    And match response[0].secret == "#notpresent"
    And match response[1].secret == "#notpresent"
    And match response[0].password == "#notpresent"
    And match response[1].password == "#notpresent"

  @user_account @delete
  Scenario: Removing an user account
    # GIVEN I create an user account
    * def userAccount = call read("account/create-userAccount.feature") { fullName: "Administrator", login: "admin@domain.com", password: "password" }
    * match userAccount.responseStatus == 200
    # AND I retrieve this user account using its id
    Given path "/api/v1/user/" + userAccount.response.id
    And method get
    # THEN the user account details is returned
    Then status 200
    # WHEN I request to delete this user account
    Given path "/api/v1/user/" + userAccount.response.id
    And method delete
    # THEN the request is successfully executed
    Then status 200
    # AND the user account details are available in the response
    And match response == userAccount.response
    # WHEN I try to retrieve this user account using its id
    Given path "/api/v1/user/" + userAccount.response.id
    And method get
    # THEN the user account is not found
    Then status 404
    # WHEN I try to request to delete this same user account
    Given path "/api/v1/user/" + userAccount.response.id
    And method delete
    # THEN the user account is not found
    Then status 404
