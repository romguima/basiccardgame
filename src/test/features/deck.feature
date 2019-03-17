Feature: Deck creation and retrieval
  Scenario: Deck creation
    When A call to create a Deck is made
    Then Deck's information is returned with code "201"

  Scenario: Deck retrieval
    When A call to retrieve the previously created Deck is made
    Then Deck's information is returned with code "200"