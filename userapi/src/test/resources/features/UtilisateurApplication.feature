Feature: Connexion
  Scenario: User unknow try to connect
    Given A new user
    When He try to connect at application
    Then He get the message "user unknown"