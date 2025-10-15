Feature: Producer sending a message

  Background:
    * configure url = "http://localhost:8080"

  @send_message @negative
  Scenario: Sending a message with malformed details.
    # GIVEN I submit a malformed request to send a message without subject
    Given request
      """
      {
        "source": "email",
        "sender": "admin@domain.com"
      }
      """
    And path "/api/v1/message"
    When method post
    # THEN the response is BAD_REQUEST
    Then status 400
    # AND the response reports sendMessageRequest type is the invalid contract structure
    * match response["type"] == "SendMessageRequest"
    # AND the response contains the violation message for the subject field
    * match karate.jsonPath(response, "$.violations[?(@.field=='subject')].messages") == [["must not be null"]]