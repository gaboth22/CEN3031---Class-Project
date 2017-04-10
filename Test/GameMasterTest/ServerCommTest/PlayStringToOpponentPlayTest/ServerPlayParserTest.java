package GameMasterTest.ServerCommTest.PlayStringToOpponentPlayTest;

import GameBoard.GameBoardStateBuilder.GameBoardStateBuilder;
import GameBoard.GameBoardStateBuilder.GameBoardStateBuilderImpl;
import GameMaster.ServerComm.Parsers.PlayStringToOpponentPlay.ServerPlayParser;
import GameBoard.*;
import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementType;
import Play.BuildPhase.*;
import Player.PlayerID;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import Location.Location;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ServerPlayParserTest {

    private GameBoard gameBoard;
    private GameBoardState gameBoardState;
    private GameBoardStateBuilder gbsb;
    private String play;

    @Before
    public void setUpGameBoard() throws Exception {
        gameBoard = new GameBoardImpl();

        GamePiece toPlace = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhase = new BuildPhase(toPlace, new Location(1,-1));
        buildPhase.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(buildPhase);

        gbsb = new GameBoardStateBuilderImpl();
        gameBoardState = gbsb.buildGameBoardState(gameBoard);
    }

    @Test
    public void theTilePlacementShouldBeANukeIfTheTileIsPlacedAboveLevelOne() {
        givenIHaveAPlay("PLACED GRASS+ROCK AT 0 0 0 5 FOUNDED SETTLEMENT AT 1 0 1");
        PlayerID playerID = PlayerID.PLAYER_ONE;
        TilePlacementPhase tilePlacementPhase = ServerPlayParser.getServerTilePlacement(play, gameBoardState, playerID);
        Assert.assertEquals(TilePlacementType.NUKE, tilePlacementPhase.getTilePlacementType());
    }

    @Test
    public void theTilePlacementShouldBeASimplePlacementIfTheTileIsPlacedAboveLevelOne() {
        givenIHaveAPlay("PLACED GRASS+ROCK AT 1 0 0 6 FOUNDED SETTLEMENT AT 1 0 1");
        PlayerID playerID = PlayerID.PLAYER_ONE;
        TilePlacementPhase tilePlacementPhase = ServerPlayParser.getServerTilePlacement(play, gameBoardState, playerID);
        Assert.assertEquals(TilePlacementType.SIMPLE_PLACEMENT, tilePlacementPhase.getTilePlacementType());
    }

    @Test
    public void theTilePlacementShouldCreateTheCorrectTile() {
        givenIHaveAPlay("PLACED GRASS+ROCK AT 1 0 0 6 FOUNDED SETTLEMENT AT 1 0 1");
        PlayerID playerID = PlayerID.PLAYER_TWO;
        TilePlacementPhase tilePlacementPhase = ServerPlayParser.getServerTilePlacement(play, gameBoardState, playerID);
        Tile tileToPlace = tilePlacementPhase.getTileToPlace();

        Terrain[] terrains = tileToPlace.getArrayOfTerrains();
        Assert.assertEquals(Terrain.VOLCANO, terrains[0]);
        Assert.assertEquals(Terrain.GRASSLANDS, terrains[1]);
        Assert.assertEquals(Terrain.ROCKY, terrains[2]);

        Location[] locations = tileToPlace.getArrayOfTerrainLocations();
        Assert.assertEquals(new Location(0,1), locations[0]);
        Assert.assertEquals(new Location(0,0), locations[1]);
        Assert.assertEquals(new Location(-1,1), locations[2]);
    }

    @Test
    public void thePlayerShouldBeSetCorrectly() {
        givenIHaveAPlay("PLACED GRASS+ROCK AT 1 0 0 6 FOUNDED SETTLEMENT AT 1 0 1");
        PlayerID playerID = PlayerID.PLAYER_TWO;
        TilePlacementPhase tilePlacementPhase = ServerPlayParser.getServerTilePlacement(play, gameBoardState, playerID);

        Assert.assertEquals(playerID, tilePlacementPhase.getPlayerID());
    }

    @Test
    public void theBuildPhaseShouldBeFound() {
        givenIHaveAPlay("PLACED GRASS+ROCK AT 1 0 0 5 FOUNDED SETTLEMENT AT 1 0 1");
        PlayerID player = PlayerID.PLAYER_ONE;
        BuildPhase buildPhase = ServerPlayParser.getServerPiecePlacement(play, gameBoardState, player);

        Assert.assertEquals(BuildType.FOUND, buildPhase.getBuildType());
    }

    @Test
    public void theBuildPhaseShouldBeExpand() {
        givenIHaveAPlay("PLACED GRASS+ROCK AT 1 0 0 5 EXPANDED SETTLEMENT AT -1 0 1 GRASS");
        PlayerID player = PlayerID.PLAYER_ONE;
        BuildPhase buildPhase = ServerPlayParser.getServerPiecePlacement(play, gameBoardState, player);

        Assert.assertEquals(BuildType.EXPAND, buildPhase.getBuildType());
    }

    @Test
    public void theBuildPhaseShouldBeBuildTotoroSanctuary() {
        givenIHaveAPlay("PLACED GRASS+ROCK AT 1 0 0 5 BUILT TOTORO SANCTUARY AT 0 0 1");
        PlayerID player = PlayerID.PLAYER_ONE;
        BuildPhase buildPhase = ServerPlayParser.getServerPiecePlacement(play, gameBoardState, player);

        Assert.assertEquals(BuildType.PLACE_TOTORO, buildPhase.getBuildType());
    }

    @Test
    public void theBuildPhaseShouldBePlacedTigerPlayground() {
        givenIHaveAPlay("PLACED GRASS+ROCK AT 1 0 0 5 BUILT TOTORO SANCTUARY AT 0 0 1");
        PlayerID player = PlayerID.PLAYER_ONE;
        BuildPhase buildPhase = ServerPlayParser.getServerPiecePlacement(play, gameBoardState, player);

        Assert.assertEquals(BuildType.PLACE_TOTORO, buildPhase.getBuildType());
    }

    private void givenIHaveAPlay(String play) {
        this.play = play;
    }
}
