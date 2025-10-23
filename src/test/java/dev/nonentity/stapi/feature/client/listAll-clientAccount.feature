@helper
Feature: Reusable feature to create list client accounts.

  Scenario:
    * path "/api/v1/clients"
    * method get
    * status 200
