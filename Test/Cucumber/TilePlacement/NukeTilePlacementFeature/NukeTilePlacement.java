package Cucumber.TilePlacement.NukeTilePlacementFeature;

import GameBoard.BuildPhaseHelper;
import GameBoard.GameBoardImpl;
import GamePieceMap.*;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementPhaseException;
import Play.TilePlacementPhase.TilePlacementType;
import Player.Player;
import Player.PlayerID;
import Settlements.Creation.Settlement;
import Settlements.SettlementException.SettlementException;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import Tile.Tile.TileImpl;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class NukeTilePlacement {
    private GameBoardImpl gameBoard;

    @Given("^player is in Tile Placement Phase$")
    public void playerInTilePlacementPhase() throws Exception{
        gameBoard = new GameBoardImpl();
    }

    @When("^tiles being nuked do not match volcano to nuking tile's volcano$")
    public void setUpBoardForVolcanoMismatchFailingCase() throws Exception{
        TilePlacementPhase tilePlacementPhase1
                = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateTileForSimplePlacement());
        tilePlacementPhase1.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhase1);
    }

    @Then("^nuke should fail due to no matching volcano$")
    public void doTilePlacementPhaseForVolcanoMismatchCase() throws Exception{
        boolean exceptionCaught = false;
        try{
            TilePlacementPhase tilePlacementPhase2
                    = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateTileForNukingNotSameVolcano());
            tilePlacementPhase2.setTilePlacementType(TilePlacementType.NUKE);
            gameBoard.doTilePlacementPhase(tilePlacementPhase2);
        }
        catch(TilePlacementPhaseException e){
            exceptionCaught = true;
        }
        assertEquals(true, exceptionCaught);
    }

    @When("^only one tile would be nuked by placement$")
    public void setUpBoardForNukeOnlyOneTileFailingCase() throws Exception{
        TilePlacementPhase tilePlacementPhase1
                = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateTileForSimplePlacement());
        tilePlacementPhase1.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhase1);
    }

    @Then("^nuke should fail due to fully covering one tile$")
    public void doTilePlacementPhaseForOneTileFailingCase() throws Exception{
        boolean exceptionCaught = false;
        try{
            TilePlacementPhase tilePlacementPhase2
                    = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateTileForSimplePlacement());
            tilePlacementPhase2.setTilePlacementType(TilePlacementType.NUKE);
            gameBoard.doTilePlacementPhase(tilePlacementPhase2);
        }
        catch(TilePlacementPhaseException e){
            exceptionCaught = true;
        }
        assertEquals(true, exceptionCaught);
    }

    @When("^multiple tiles being nuked have different levels$")
    public void setUpBoardForNukeDifferentHeightsCase() throws Exception{
        TilePlacementPhase tilePlacementPhase1
                = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateTileForSimplePlacement());
        tilePlacementPhase1.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhase1);

        TilePlacementPhase tilePlacementPhase2
                = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateTileForNukingLevelUp());
        tilePlacementPhase2.setTilePlacementType(TilePlacementType.NUKE);
        gameBoard.doTilePlacementPhase(tilePlacementPhase2);

    }

    @Then("^nuke should fail due to different heights$")
    public void doTilePlacementPhaseForNukeDifferentHeightsCase() throws Exception{
        boolean exceptionCaught = false;
        try{
            TilePlacementPhase tilePlacementPhase2
                    = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateTileForNukingDifferentLevels());
            tilePlacementPhase2.setTilePlacementType(TilePlacementType.NUKE);
            gameBoard.doTilePlacementPhase(tilePlacementPhase2);
        }
        catch(TilePlacementPhaseException e){
            exceptionCaught = true;
        }
        assertEquals(true, exceptionCaught);
    }

    @When("^tiles below nuking tile have settlements that would be wiped out$")
    public void setUpBoardForSettlementWipeout() throws Exception{
        TilePlacementPhase tilePlacementPhase1
                = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateTileForSimplePlacement());
        tilePlacementPhase1.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhase1);

        BuildPhase buildPhase1
                = new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(0,1));
        buildPhase1.setBuildType(BuildType.FOUND);

        BuildPhase buildPhase2
                = new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(1,0));
        buildPhase2.setBuildType(BuildType.FOUND);

        gameBoard.doBuildPhase(buildPhase1);
        gameBoard.doBuildPhase(buildPhase2);

    }

    @Then("^nuke should fail due to settlements being wiped out$")
    public void doTilePlacementPhaseForSettlementWipeOut() throws Exception{
        boolean exceptionCaught = false;
        try{
            TilePlacementPhase tilePlacementPhase2
                    = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateTileForNukingLevelUp());
            tilePlacementPhase2.setTilePlacementType(TilePlacementType.NUKE);
            gameBoard.doTilePlacementPhase(tilePlacementPhase2);
        }
        catch(TilePlacementPhaseException e){
            exceptionCaught = true;
        }
        assertEquals(true, exceptionCaught);
    }

    @When("^tiles below nuking tile have settlements that would not be wiped out$")
    public void setUpBoardForNotSettlementWipeout() throws Exception{
        TilePlacementPhase tilePlacementPhase1
                = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateTileForSimplePlacement());
        tilePlacementPhase1.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(tilePlacementPhase1);

        BuildPhase buildPhase1
                = new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(0,1));
        buildPhase1.setBuildType(BuildType.FOUND);

        BuildPhase buildPhase2
                = new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(1,0));
        buildPhase2.setBuildType(BuildType.FOUND);

        BuildPhase buildPhase3
                = new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(0,2));
        buildPhase3.setBuildType(BuildType.FOUND);

        gameBoard.doBuildPhase(buildPhase1);
        gameBoard.doBuildPhase(buildPhase2);
        gameBoard.doBuildPhase(buildPhase3);

    }

    @Then("^nuke should succeed due to settlements not completely being wiped out$")
    public void doTilePlacementPhaseForNoSettlementWipeout() throws Exception{
        boolean exceptionCaught = false;
        try{
            TilePlacementPhase tilePlacementPhase2
                    = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateTileForNukingLevelUp());
            tilePlacementPhase2.setTilePlacementType(TilePlacementType.NUKE);
            gameBoard.doTilePlacementPhase(tilePlacementPhase2);
        }
        catch(TilePlacementPhaseException e){
            exceptionCaught = true;
        }
        assertEquals(false, exceptionCaught);
    }


    private Tile generateTileForNukingNotSameVolcano() {
        Location[] locations = new Location[] {
                new Location(1, 0),
                new Location(0,0),
                new Location(0,1)
        };
        Terrain[] terrains = new Terrain[] {
                Terrain.VOLCANO,
                Terrain.GRASSLANDS,
                Terrain.GRASSLANDS
        };
        return new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));
    }

    private Tile generateTileForNukingLevelUp() {
        Location[] locations = new Location[] {
                new Location(0, 0),
                new Location(0,1),
                new Location(1,0)
        };
        Terrain[] terrains = new Terrain[] {
                Terrain.VOLCANO,
                Terrain.GRASSLANDS,
                Terrain.GRASSLANDS
        };
        return new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));
    }

    private Tile generateTileForNukingDifferentLevels() {
        Location[] locations = new Location[] {
                new Location(0, 0),
                new Location(1,0),
                new Location(1,-1)
        };
        Terrain[] terrains = new Terrain[] {
                Terrain.VOLCANO,
                Terrain.GRASSLANDS,
                Terrain.GRASSLANDS
        };
        return new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));
    }

    private Tile generateTileForSimplePlacement() {
        Location[] locations = new Location[] {
                new Location(1, 1),
                new Location(0,1),
                new Location(0,2)
        };
        Terrain[] terrains = new Terrain[] {
                Terrain.VOLCANO,
                Terrain.GRASSLANDS,
                Terrain.GRASSLANDS
        };
        return new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));
    }

}


