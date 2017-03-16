Feature: Tile

  Scenario: Generating Legal Terrain
    Given: a game is in session
    When: a player draws a tile
    Then: each hexagon should be of either one of four legal terrains or a volcano