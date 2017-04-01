Feature: FirstTilePlacement

  Scenario: On the first turn of the game, player tries to place tile not at the center of table
    Given it is the first turn
    When the active player tries to place a tile incorrectly
    Then then the insertion should not be valid
