package Cucumber.FoundSettlement;

import GameBoard.GameBoardImpl;
import GamePieceMap.*;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildPhaseException;
import Play.BuildPhase.BuildType;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementType;
import Player.PlayerID;
import Tile.Tile.Tile;
import Tile.Tile.TileImpl;
import Terrain.Terrain.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.Arrays;
import static junit.framework.TestCase.assertEquals;

public class FoundSettlement {
    GameBoardImpl gameBoard;

    @Given("^the active player is in their build phase$")
    public void playerInBuildPhase() throws Exception{
        gameBoard = new GameBoardImpl();
    }

    @When("^player tries to found on a hex that is not level one$")
    public void setUpBoardForAttemptsToFoundOnNotLevelOne() throws Exception{
        TilePlacementPhase tilePlacementPhase1
                = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateTileForSimplePlacement1());
        tilePlacementPhase1.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);

        TilePlacementPhase tilePlacementPhase2
                = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateTileForNukingLevelUp());
        tilePlacementPhase2.setTilePlacementType(TilePlacementType.NUKE);

        gameBoard.doTilePlacementPhase(tilePlacementPhase1);
        gameBoard.doTilePlacementPhase(tilePlacementPhase2);
    }

    @Then("^foundation failed due to hex not being level one$")
    public void playerAttemptToFoundNotOnLevelOneShouldFail() throws Exception{
        BuildPhase buildPhase1 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(0,1));
        buildPhase1.setBuildType(BuildType.FOUND);

        BuildPhase buildPhase2 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(0,1));
        buildPhase2.setBuildType(BuildType.FOUND);

        boolean exceptionCaught1 = false;
        boolean exceptionCaught2 = false;

        try{
            gameBoard.doBuildPhase(buildPhase1);
        }
        catch(BuildPhaseException e){
            exceptionCaught1 = true;
        }

        try{
            gameBoard.doBuildPhase(buildPhase2);
        }
        catch(BuildPhaseException e){
            exceptionCaught2 = true;
        }

        assertEquals(true, exceptionCaught1);
        assertEquals(true, exceptionCaught2);

    }

    @When("^player tries to found on a hex with a volcano$")
    public void setUpBoardForTriesToFoundOnAHexWithAVolcano() throws Exception {
        TilePlacementPhase tilePlacementPhase1
                = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateTileForSimplePlacement1());
        tilePlacementPhase1.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);

        gameBoard.doTilePlacementPhase(tilePlacementPhase1);
    }

    @Then("^foundation fails due to hex being a volcano$")
    public void foundationFailsDueToHexBeingAVolcano() throws Exception {
        BuildPhase buildPhase1 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(0,0));
        buildPhase1.setBuildType(BuildType.FOUND);

        BuildPhase buildPhase2 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(1,1));
        buildPhase2.setBuildType(BuildType.FOUND);

        boolean exceptionCaught1 = false;
        boolean exceptionCaught2 = false;

        try{
            gameBoard.doBuildPhase(buildPhase1);
        }
        catch(BuildPhaseException e){
            exceptionCaught1 = true;
        }

        try{
            gameBoard.doBuildPhase(buildPhase2);
        }
        catch(BuildPhaseException e){
            exceptionCaught2 = true;
        }

        assertEquals(true, exceptionCaught1);
        assertEquals(true, exceptionCaught2);
    }

    @When("^player tries to found on a hex with a piece already on it$")
    public void setUpBoardForTriesToFoundOnAHexWithAPieceAlreadyOnIt() throws Exception {

        BuildPhase buildPhase1 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(1,0));
        buildPhase1.setBuildType(BuildType.FOUND);

        gameBoard.doBuildPhase(buildPhase1);
    }

    @Then("^foundation fails due to hex having a piece already$")
    public void foundationFailsDueToHexHavingAPieceAlready() throws Exception {
        BuildPhase buildPhase1 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(1,0));
        buildPhase1.setBuildType(BuildType.FOUND);

        boolean exceptionCaught = false;

        try{
            gameBoard.doBuildPhase(buildPhase1);
        }
        catch(BuildPhaseException e ){
            exceptionCaught = true;
        }
        assertEquals(true, exceptionCaught);
    }

    @When("^player tries to found a settlement without a villager$")
    public void setUpBoardForTriesToFoundASettlementWithoutAVillager() throws Exception {

        TilePlacementPhase tilePlacementPhase1
                = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateTileForSimplePlacement1());
        tilePlacementPhase1.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);

        TilePlacementPhase tilePlacementPhase2
                = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateTileForSimplePlacement2());
        tilePlacementPhase2.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);

        TilePlacementPhase tilePlacementPhase3
                = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateTileForSimplePlacement3());
        tilePlacementPhase3.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);

        TilePlacementPhase tilePlacementPhase4
                = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateTileForSimplePlacement4());
        tilePlacementPhase4.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);

        TilePlacementPhase tilePlacementPhase5
                = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateTileForSimplePlacement5());
        tilePlacementPhase5.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);

        TilePlacementPhase tilePlacementPhase6
                = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateTileForSimplePlacement6());
        tilePlacementPhase6.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);

        TilePlacementPhase tilePlacementPhase7
                = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateTileForSimplePlacement7());
        tilePlacementPhase7.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);

        TilePlacementPhase tilePlacementPhase8
                = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateTileForSimplePlacement8());
        tilePlacementPhase8.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);

        TilePlacementPhase tilePlacementPhase9
                = new TilePlacementPhase(PlayerID.PLAYER_ONE, generateTileForSimplePlacement9());
        tilePlacementPhase9.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);

        gameBoard.doTilePlacementPhase(tilePlacementPhase1);
        gameBoard.doTilePlacementPhase(tilePlacementPhase2);
        gameBoard.doTilePlacementPhase(tilePlacementPhase3);
        gameBoard.doTilePlacementPhase(tilePlacementPhase4);
        gameBoard.doTilePlacementPhase(tilePlacementPhase5);
        gameBoard.doTilePlacementPhase(tilePlacementPhase6);
        gameBoard.doTilePlacementPhase(tilePlacementPhase7);
        gameBoard.doTilePlacementPhase(tilePlacementPhase8);
        gameBoard.doTilePlacementPhase(tilePlacementPhase9);


        BuildPhase buildPhase1 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(-1, 4));
        buildPhase1.setBuildType(BuildType.FOUND);

        BuildPhase buildPhase2 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(-1, 3));
        buildPhase2.setBuildType(BuildType.FOUND);

        BuildPhase buildPhase3 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(1, 3));
        buildPhase3.setBuildType(BuildType.FOUND);

        BuildPhase buildPhase4 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(1, 2));
        buildPhase4.setBuildType(BuildType.FOUND);

        BuildPhase buildPhase5 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(-2, 3));
        buildPhase5.setBuildType(BuildType.FOUND);

        BuildPhase buildPhase6 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(-2, 2));
        buildPhase6.setBuildType(BuildType.FOUND);

        BuildPhase buildPhase7 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(0, 1));
        buildPhase7.setBuildType(BuildType.FOUND);

        BuildPhase buildPhase8 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(0, 2));
        buildPhase8.setBuildType(BuildType.FOUND);

        BuildPhase buildPhase9 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(2, 1));
        buildPhase9.setBuildType(BuildType.FOUND);

        BuildPhase buildPhase10 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(2, 0));
        buildPhase10.setBuildType(BuildType.FOUND);

        BuildPhase buildPhase11 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(-3, 1));
        buildPhase11.setBuildType(BuildType.FOUND);

        BuildPhase buildPhase12 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(-3, 2));
        buildPhase12.setBuildType(BuildType.FOUND);

        BuildPhase buildPhase13 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(-1, 0));
        buildPhase13.setBuildType(BuildType.FOUND);

        BuildPhase buildPhase14 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(-1, 1));
        buildPhase14.setBuildType(BuildType.FOUND);

        BuildPhase buildPhase15 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(1, -1));
        buildPhase15.setBuildType(BuildType.FOUND);

        BuildPhase buildPhase16 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(1, 0));
        buildPhase16.setBuildType(BuildType.FOUND);

        BuildPhase buildPhase17 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(-4, 0));
        buildPhase17.setBuildType(BuildType.FOUND);

        BuildPhase buildPhase18 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(-4, 1));
        buildPhase18.setBuildType(BuildType.FOUND);

        BuildPhase buildPhase19 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(-2, -1));
        buildPhase19.setBuildType(BuildType.FOUND);

        BuildPhase buildPhase20 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(-2, 0));
        buildPhase20.setBuildType(BuildType.FOUND);

        gameBoard.doBuildPhase(buildPhase1);
        gameBoard.doBuildPhase(buildPhase2);
        gameBoard.doBuildPhase(buildPhase3);
        gameBoard.doBuildPhase(buildPhase4);
        gameBoard.doBuildPhase(buildPhase5);
        gameBoard.doBuildPhase(buildPhase6);
        gameBoard.doBuildPhase(buildPhase7);
        gameBoard.doBuildPhase(buildPhase8);
        gameBoard.doBuildPhase(buildPhase9);
        gameBoard.doBuildPhase(buildPhase10);
        gameBoard.doBuildPhase(buildPhase11);
        gameBoard.doBuildPhase(buildPhase12);
        gameBoard.doBuildPhase(buildPhase13);
        gameBoard.doBuildPhase(buildPhase14);
        gameBoard.doBuildPhase(buildPhase15);
        gameBoard.doBuildPhase(buildPhase16);
        gameBoard.doBuildPhase(buildPhase17);
        gameBoard.doBuildPhase(buildPhase18);
        gameBoard.doBuildPhase(buildPhase19);
        gameBoard.doBuildPhase(buildPhase20);
    }

    @Then("^foundation fails due to player not having enough pieces$")
    public void foundationFailsDueToPlayerNotHavingEnoughPieces() throws Exception {
        BuildPhase buildPhase21 =
                new BuildPhase(new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER), new Location(0, -2));
        buildPhase21.setBuildType(BuildType.FOUND);

        boolean exceptionCaught = false;
        try{
            gameBoard.doBuildPhase(buildPhase21);
        }
        catch(BuildPhaseException e){
            exceptionCaught = true;
        }

        assertEquals(true, exceptionCaught);
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

    private Tile generateTileForSimplePlacement1() {
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

    private Tile generateTileForSimplePlacement2() {
        Location[] locations = new Location[] {
                new Location(0, 3),
                new Location(1,3),
                new Location(1,2)
        };
        Terrain[] terrains = new Terrain[] {
                Terrain.VOLCANO,
                Terrain.GRASSLANDS,
                Terrain.GRASSLANDS
        };
        return new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));
    }

    private Tile generateTileForSimplePlacement3() {
        Location[] locations = new Location[] {
                new Location(-2, 4),
                new Location(-1,4),
                new Location(-1,3)
        };
        Terrain[] terrains = new Terrain[] {
                Terrain.VOLCANO,
                Terrain.GRASSLANDS,
                Terrain.GRASSLANDS
        };
        return new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));
    }

    private Tile generateTileForSimplePlacement4() {
        Location[] locations = new Location[] {
                new Location(-1, 2),
                new Location(-2,2),
                new Location(-2,3)
        };
        Terrain[] terrains = new Terrain[] {
                Terrain.VOLCANO,
                Terrain.GRASSLANDS,
                Terrain.GRASSLANDS
        };
        return new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));
    }

    private Tile generateTileForSimplePlacement5() {
        Location[] locations = new Location[] {
                new Location(3, 0),
                new Location(2,0),
                new Location(2,1)
        };
        Terrain[] terrains = new Terrain[] {
                Terrain.VOLCANO,
                Terrain.GRASSLANDS,
                Terrain.GRASSLANDS
        };
        return new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));
    }

    private Tile generateTileForSimplePlacement6() {
        Location[] locations = new Location[] {
                new Location(-2, 1),
                new Location(-3,1),
                new Location(-3,2)
        };
        Terrain[] terrains = new Terrain[] {
                Terrain.VOLCANO,
                Terrain.GRASSLANDS,
                Terrain.GRASSLANDS
        };
        return new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));
    }

    private Tile generateTileForSimplePlacement7() {
        Location[] locations = new Location[] {
                new Location(-3, 0),
                new Location(-4,0),
                new Location(-4,1)
        };
        Terrain[] terrains = new Terrain[] {
                Terrain.VOLCANO,
                Terrain.GRASSLANDS,
                Terrain.GRASSLANDS
        };
        return new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));
    }

    private Tile generateTileForSimplePlacement8() {
        Location[] locations = new Location[] {
                new Location(-1, -1),
                new Location(-2,-1),
                new Location(-2,0)
        };
        Terrain[] terrains = new Terrain[] {
                Terrain.VOLCANO,
                Terrain.GRASSLANDS,
                Terrain.GRASSLANDS
        };
        return new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));
    }

    private Tile generateTileForSimplePlacement9() {
        Location[] locations = new Location[] {
                new Location(1, -2),
                new Location(0,-2),
                new Location(0,-1)
        };
        Terrain[] terrains = new Terrain[] {
                Terrain.VOLCANO,
                Terrain.GRASSLANDS,
                Terrain.GRASSLANDS
        };
        return new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));
    }
}