@helper
Feature: Reusable feature to delete an existing client account.

  Scenario:
    * path "/api/v1/clients/" + clientId
    * method delete
    * status 200