Feature: Testing a REST API

  Scenario: Login Failed
    Given A new User
    When Try to login
    Then received error message unknown user

  Scenario: Register Succeed
    Given A new User
    When Try to register
    Then received success register

  Scenario: Token failed
    Given A user already registered
    When Try to get token
    Then received failure tokenization

  Scenario: Login Succeed
    Given A user already registered
    When Try to login
    Then received success login

  Scenario: Register failed
    Given A user already registered but bad password
    When Try to register
    Then received failure register

  Scenario: Authorized failed
    Given A new User
    When Try to authorize
    Then received failure authorization

  Scenario: Token succeed
    Given A user already login
    When Try to get token
    Then received success tokenization

  Scenario: Authorized succeed
    Given A user already get token
    When Try to authorize
    Then received success authorization

  Scenario: Logout Succeed
    Given A user already get token
    When Try to logout
    Then received success logout
