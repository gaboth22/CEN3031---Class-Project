Feature: NukeTilePlacement

  Scenario: Trying to nuke without matching volcano
    Given player is in Tile Placement Phase
    When tiles being nuked do not match volcano to nuking tile's volcano
    Then nuke should fail due to no matching volcano

  Scenario: Trying to nuke only one tile
    Given player is in Tile Placement Phase
    When only one tile would be nuked by placement
    Then nuke should fail due to fully covering one tile

  Scenario: Trying to nuke different height tiles
    Given player is in Tile Placement Phase
    When multiple tiles being nuked have different levels
    Then nuke should fail due to different heights

  Scenario: Trying to wipe out settlement
    Given player is in Tile Placement Phase
    When tiles below nuking tile have settlements that would be wiped out
    Then nuke should fail due to settlements being wiped out

  Scenario: Trying to nuke settlement
    Given player is in Tile Placement Phase
    When tiles below nuking tile have settlements that would not be wiped out
    Then nuke should succeed due to settlements not completely being wiped out