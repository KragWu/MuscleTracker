Feature: Testing a REST API

  Scenario: Login Failed
    Given A new User
    When Try to connect first time
    Then received error message unknown user