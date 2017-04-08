package GameMasterTest.ServerCommTest.PlayStringToOpponentPlayTest;

import GameBoard.GameBoardState;
import GameMaster.ServerComm.Parsers.PlayStringToOpponentPlay.BuildInfoToBuildPhase;
import GamePieceMap.*;
import GamePieceMap.TypeOfPiece;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Player.*;
import Location.Location;
import Settlements.Creation.Settlement;
import Settlements.Creation.SettlementCreator;
import Settlements.LargeHexagonBoard;
import Terrain.Terrain.Terrain;
import TileMap.Hexagon;
import org.junit.Assert;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildInfoToBuildPhaseTest {

    private String testString;
    private BuildPhase generatedBuildPhase;
    private PlayerID playerMakingPlay;

    private GameBoardState gameBoardState;

    private void andIHaveAGameBoardState() throws Exception {

        Map<Location, Hexagon> placedHexagons = getPlacedHexagons();
        GamePieceMap pieceMap = getPiecesOnBoard();
        Player playerOne = getPlayerOne(pieceMap);


        gameBoardState = new GameBoardState(playerOne,
                null,
                0,
                placedHexagons,
                pieceMap,
                null,
                null);
    }

    private Player getPlayerOne(GamePieceMap pieceMap) {
        Player playerOne = new Player(PlayerID.PLAYER_ONE);
        playerOne.setListOfSettlements(getPlayerOneListOfSettlements(pieceMap));
        return playerOne;
    }

    private List<Settlement> getPlayerOneListOfSettlements(GamePieceMap pieceMap) {
        List<Settlement> settlements = new ArrayList<>(2);
        settlements.add(SettlementCreator.getSettlementAt(pieceMap, new Location(-2,2)));
        settlements.add(SettlementCreator.getSettlementAt(pieceMap, new Location(0,3)));
        return settlements;
    }

    private Map<Location, Hexagon> getPlacedHexagons() {

        Map<Location, Hexagon> placedHexagons = new HashMap<Location, Hexagon>();

        Hexagon[] largeBoard = LargeHexagonBoard.getBoard();
        for(int i = 0; i < largeBoard.length; i++) {
            placedHexagons.put(largeBoard[i].getTerrainLocation(), largeBoard[i]);
        }

        return placedHexagons;
    }

    private GamePieceMap getPiecesOnBoard() throws Exception {

        GamePieceMap pieceMap = new GamePieceMap();
        pieceMap.insertAPieceAt(new Location(-1,1), new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER));
        pieceMap.insertAPieceAt(new Location(-2,2), new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER));
        pieceMap.insertAPieceAt(new Location(-3,2), new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.TIGER));
        pieceMap.insertAPieceAt(new Location(-4,4), new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER));
        pieceMap.insertAPieceAt(new Location(-5,4), new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER));

        pieceMap.insertAPieceAt(new Location(-5, 2), new GamePiece(PlayerID.PLAYER_TWO, TypeOfPiece.VILLAGER));

        pieceMap.insertAPieceAt(new Location(0,3), new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER));

        return pieceMap;
    }

    //e.g. "FOUNDED SETTLEMENT AT 0 0 1"
    @Test
    public void foundSettlementStringShouldReturnFoundSettlementBuildPhase() {
        givenThatIHaveAStringToParse("FOUNDED SETTLEMENT AT 0 0 1");
        andIHaveAPlayerID(PlayerID.PLAYER_ONE);
        whenIGenerateAFoundBuildPhase();
        thenAValidFoundBuildPhaseShouldBeReturned();
    }

    private void thenAValidFoundBuildPhaseShouldBeReturned() {
        Assert.assertEquals(BuildType.FOUND, generatedBuildPhase.getBuildType());
        Assert.assertEquals(TypeOfPiece.VILLAGER, generatedBuildPhase.getTypeOfPieceToPlace());
        Assert.assertEquals(playerMakingPlay, generatedBuildPhase.getPlayerID());
        Assert.assertEquals(new Location(1,0), generatedBuildPhase.getLocationToPlacePieceOn());
    }

    //e.g "EXPANDED SETTLEMENT AT 0 0 1 TERRAIN"
    @Test
    public void expansionStringShouldReturnExpansionBuildPhase() throws Exception {
        givenThatIHaveAStringToParse("EXPANDED SETTLEMENT AT 2 <NOT_USED> -2 LAKE");
        andIHaveAPlayerID(PlayerID.PLAYER_ONE);
        andIHaveAGameBoardState();
        whenIGenerateAExpandBuildPhase();
        thenAValidExpandBuildPhaseShouldBeReturned();
    }

    private void whenIGenerateAExpandBuildPhase() {
        this.generatedBuildPhase = BuildInfoToBuildPhase.getExpandPhase(testString, gameBoardState, playerMakingPlay);
    }

    private void thenAValidExpandBuildPhaseShouldBeReturned() {
        Assert.assertEquals(BuildType.EXPAND, generatedBuildPhase.getBuildType());
        Assert.assertEquals(TypeOfPiece.VILLAGER, generatedBuildPhase.getTypeOfPieceToPlace());
        Assert.assertEquals(playerMakingPlay, generatedBuildPhase.getPlayerID());

        Terrain terrainInMap = gameBoardState.getPlacedHexagons().get(generatedBuildPhase.getLocationToPlacePieceOn()).getTerrain();
        Assert.assertEquals(Terrain.LAKE, terrainInMap);
    }

    @Test(expected = InvalidParameterException.class)
    public void invalidExpansionShouldThrowAnError() throws Exception {
        givenThatIHaveAStringToParse("EXPANDED SETTLEMENT AT 2 <NOT_USED> -2 ROCK");
        andIHaveAPlayerID(PlayerID.PLAYER_ONE);
        andIHaveAGameBoardState();
        whenIGenerateAExpandBuildPhase();
    }

    //e.g "BUILT TOTORO SANCTUARY AT 0 0 1"

    private void givenThatIHaveAStringToParse(String stringToParse) {
        this.testString = stringToParse;
    }

    private void andIHaveAPlayerID(PlayerID playerMakingPlay) {
        this.playerMakingPlay = playerMakingPlay;
    }

    private void whenIGenerateAFoundBuildPhase() {
        this.generatedBuildPhase = BuildInfoToBuildPhase.getFoundPhase(testString, playerMakingPlay);
    }
}
