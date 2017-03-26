Feature: FirstTilePlacement

    Scenario: On a turn other than the first, player simply places tile on lowest level
      Given tiles have already been placed on board
      When a player tries a simple placement
      Then the tile should touch other already placed tile on at least one edge