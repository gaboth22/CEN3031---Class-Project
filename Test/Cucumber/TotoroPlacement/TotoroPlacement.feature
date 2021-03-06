Feature: Totoro placement
  Scenario: Adding a totoro
    Given an existing settlement of size 5 or more
    When a player places a totoro on an adjacent hex
    Then the totoro should be successfully placed

  Scenario: Totoro placement on a volcano
    Given an existing settlement of size 5 or more
    When a player places a totoro on an adjacent hex that is a volcano
    Then the totoro shouldn't be added to the settlement

  Scenario: Invalid totoro placement
    Given an existing settlement of size 4 or less
    When a player places a totoro adjacent to this settlement
    Then the totoro should not be successfully placed

  Scenario: Joining settlements via a totoro
    Given a player has two settlements, with one settlement having a totoro
    When the player attempts to add a totoro adjacent to both settlements
    Then the totoro should be placed joining both settlements

  Scenario: Adding a totoro to a settlement that has a tiger
    Given a player has a settlement that is a tiger playground and is size 5 or greater
    When the player places a totoro adjacent to that settlement
    Then the totoro should become part of the settlement

  Scenario: Placing a second totoro in the same settlement
    Given a player has a settlement containing a totoro
    When the player adds a second totoro to the same settlement
    Then the totoro should not be added to the settlement