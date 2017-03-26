Feature: TilePlacement

  Scenario: On the first turn of the game, player places the tile in the center of the table.
    Given it is the first turn
    When the active player places a tile
    Then the tile should be placed at center of table
