Feature: Game creation and deletion
  Scenario: Game creation
    When A call to create a Game with name "Test" is made
    Then A new Game is created
    And Its information is returned

  Scenario: Retrieve previously created Game
    Given There is a previously created Game
    When A call to retrieve a Game is made
    Then Its information is returned

  Scenario: Delete previously created Game
    Given There is a previously created Game
    When A call to delete that Game is made
    Then It's not possible to retrieve it anymore

  Scenario: Create and add Player into Game
    Given A call to create a Player with name "First" and id "1234" is made
    And A call to create a Game with name "We have players" is made
    When A call to add the Player with id "1234" into this Game
    Then The game information contains the Player with id "1234"

  Scenario: Add a new Deck into previously created Game
    Given There is an previously created Game
    And There is a card Deck available
    When A call to add a Dack into a Game is made
    Then The Game's deck size goes to "52"

  Scenario: Deal cards to the first Player
    Given There is an previously created Game
    And That Game has a deck size of "52" cards
    And That Game has at least "1" Player
    When A call to deal "3" cards to the first Player is made
    Then The Game's deck size goes to "49"
    And The Player has "3" cards for that Game

  Scenario: Remove the first Player from previously created Game
    Given There is an previously created Game
    When A call to remove the first Player from that Game is made
    Then The Game information does not include that Player anymore
    And The Game's deck size goes to "52"

  Scenario: Delete previously created Game
    Given There is a previously created Game
    When A call to delete that Game is made
    Then It's not possible to retrieve it anymore

  Scenario: Try to retrieve a Game that doesn't exists
    Given A call to retrieve a Game with id "1234" is made
    Then A status code "404" is returned