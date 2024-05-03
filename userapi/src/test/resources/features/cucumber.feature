Feature: Testing a REST API

  Scenario: Login Failed
    Given A new User
    When Try to connect
    Then received error message unknown user

  Scenario: Register Succeed
    Given A new User
    When Try to register
    Then received succeed register

  Scenario: Login Succeed
    Given A new User
    When Try to connect
    Then received succeed login

  Scenario: Register failed
    Given A user already registered
    When Try to register
    Then received failed register

  Scenario: Logout Succeed
    Given A user already login
    When Try to logout
    Then received succeed logout