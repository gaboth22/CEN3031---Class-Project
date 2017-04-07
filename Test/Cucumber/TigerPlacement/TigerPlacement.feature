Feature: Tiger placement
  Scenario: Successfully placing a tiger
    Given an existing settlement with an adjacent hex on level 3 or above
    When a player attempts to place a tiger adjacent to the settlement on level 3 or greater
    Then the settlement should become a tiger playground

  Scenario: Tiger placement on a volcano
    Given an existing settlement with an adjacent hex on level 3 or above
    When a player attempts to place the tiger on the adjacent settlement but the location is a volcano
    Then the tiger should not be successfully placed

  Scenario: Placing a tiger below level 3
    Given a settlement with no adjacent tile level three or greater
    When a player attempts to join a tiger to that settlement
    Then the settlement should not become a tiger playground

  Scenario: Placing a second tiger within the same settlement
    Given a settlement that already contains a tiger
    When a player attempts to add a tiger to the same settlement
    Then the tiger should not be added to that settlement

  Scenario: Placing a tiger on a settlement that contains a totoro
    Given a settlement that contains a totoro and has an adjacent hex on level 3 or greater
    When a player tries to add a tiger to the settlement containing the totoro
    Then the settlement should contain both a totoro and a tiger

  Scenario: Joining two settlements with a tiger placement
    Given a player has two settlements, with one settlement having a tiger
    When the player attempts to add a tiger adjacent to both settlements
    Then the tiger should be placed joining both settlements