package Steve;

import GameBoard.GameBoardImpl;
import GameBoard.GameBoardState;
import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.*;
import Steve.PlayGeneration.StrategicTilePlacement;
import Terrain.Terrain.Terrain;
import Location.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class StrategicTilePlacementTest {
    private GameBoardImpl gameBoard;
    private GameBoardState gameBoardState;
    private BiHexTileStructure terrains;

    @Before
    public void initializeInstances() {
        gameBoard = new GameBoardImpl();
        gameBoardState = new GameBoardState(gameBoard.getPlayer(PlayerID.PLAYER_ONE),
                                            gameBoard.getPlayer(PlayerID.PLAYER_TWO),
                                            1,
                                            gameBoard.getPlacedHexagons(),
                                            gameBoard.getGamePieceMap(),
                                            gameBoard.getPlaceableLocations(),
                                            gameBoard.getNukeableVolcanoLocations());

        Terrain[] terrain = new Terrain[]{Terrain.GRASSLANDS, Terrain.JUNGLE};
        terrains = new BiHexTileStructure(terrain);
    }

    @Test
    public void shouldReturnATilePlacementAdjacentToOnlySettlement() throws Exception {
        Location locationToPlaceSettlement = new Location(1, 0);

        GamePiece standardVillage = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhase;
        buildPhase = new BuildPhase(standardVillage, locationToPlaceSettlement);
        buildPhase.setBuildType(BuildType.FOUND);
        gameBoard.doBuildPhase(buildPhase);

        TilePlacementPhase tilePlacementPhase;
        tilePlacementPhase = StrategicTilePlacement.makeAStrategicTilePlacement(gameBoardState, gameBoard.getPlayer(PlayerID.PLAYER_ONE), terrains);

        Location[] locationsTileShouldBePlaced = new Location[]{new Location(2, 1), new Location(1,1), new Location(2,0)};
        assert tilePlacementPhase != null;
        assertEquals(locationsTileShouldBePlaced, tilePlacementPhase.getTileToPlace().getArrayOfTerrainLocations());
    }

    @Test
    public void shouldReturnNullDueToThereBeingNoSettlementOnTheMap() {
        TilePlacementPhase tilePlacementPhase;
        tilePlacementPhase = StrategicTilePlacement.makeAStrategicTilePlacement(gameBoardState, gameBoard.getPlayer(PlayerID.PLAYER_ONE), terrains);

        assertEquals(null, tilePlacementPhase);
    }
}
