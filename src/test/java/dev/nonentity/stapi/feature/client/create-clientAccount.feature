@helper
Feature: Reusable feature to create a client account.

  Scenario:
    * path "/api/v1/clients"
    * request payload
    * method post
    * status 200