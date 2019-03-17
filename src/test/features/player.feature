Feature: Player creation and retrieval
  Scenario: Player creation
    When A call to create a Player with name "Lucky" is made
    Then Player's information is returned with code "201"

  Scenario: Player retrieval
    When A call to retrieve the previously created Player is made
    Then Player's information is returned with code "200"