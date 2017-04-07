Feature: Founding a settlement

  Scenario: active player trying to found on level other than one
    Given the active player is in their build phase
    When player tries to found on a hex that is not level one
    Then foundation failed due to hex not being level one

  Scenario: active player trying to found on volcano
    Given the active player is in their build phase
    When player tries to found on a hex with a volcano
    Then foundation fails due to hex being a volcano

  Scenario: active player trying to found over an already placed piece
    Given the active player is in their build phase
    When player tries to found on a hex with a piece already on it
    Then foundation fails due to hex having a piece already

  Scenario: active player trying to found without enough pieces
    Given the active player is in their build phase
    When player tries to found a settlement without a villager
    Then foundation fails due to player not having enough pieces

