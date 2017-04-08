package SteveTest.PlayGenerationTest;

import GameBoard.GameBoardState;
import GamePieceMap.GamePiece;
import GamePieceMap.GamePieceMap;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.Player;
import Player.PlayerID;
import Settlements.Creation.Settlement;
import Steve.BiHexTileStructure;
import Steve.PlayGeneration.SmartTilePlacer.NukingTileMaker;
import Steve.PlayGeneration.SmartTilePlacer.NukingTilePlacementPhaseMaker;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import TileMap.Hexagon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NukingTilePlacementPhaseMakerTest {

    private GameBoardState gameBoardState;
    private PlayerID activePlayer;
    private BiHexTileStructure tileToPlace;
    private NukingTilePlacementPhaseMaker phaseMaker;

    @Before
    public void initializeInstances() throws Exception {
        activePlayer = PlayerID.PLAYER_ONE;
        Player playerOne = new Player(activePlayer);
        Player playerTwo = new Player(PlayerID.PLAYER_TWO);
        List<Settlement> playerOneSettlements = new ArrayList<>();
        List<Settlement> playerTwoSettlements = new ArrayList<>();
        phaseMaker = new NukingTilePlacementPhaseMaker();

        tileToPlace = new BiHexTileStructure(new Terrain[]{Terrain.ROCKY, Terrain.LAKE});

        Map<Location, Hexagon> hexMap = new HashMap<Location, Hexagon>();

        Hexagon[] hexesOnBoard = new Hexagon[] {
                new Hexagon(1,
                        new Location(0, 0),
                        1,
                        Terrain.VOLCANO),
                new Hexagon(1,
                        new Location(-1, 1),
                        1,
                        Terrain.LAKE),
                new Hexagon(1,
                        new Location(-1, 0),
                        1,
                        Terrain.JUNGLE),


                new Hexagon(2,
                        new Location(-1, 2),
                        1,
                        Terrain.VOLCANO),
                new Hexagon(2,
                        new Location(0, 2),
                        1,
                        Terrain.GRASSLANDS),
                new Hexagon(2,
                        new Location(0, 1),
                        1,
                        Terrain.LAKE),


                new Hexagon(3,
                        new Location(-3, 2),
                        1,
                        Terrain.VOLCANO),
                new Hexagon(3,
                        new Location(-2, 2),
                        1,
                        Terrain.GRASSLANDS),
                new Hexagon(3,
                        new Location(-2, 1),
                        1,
                        Terrain.JUNGLE)
        };

        for(Hexagon hex : hexesOnBoard) {
            hexMap.put(hex.getTerrainLocation(), hex);
        }

        GamePiece villager = new GamePiece(PlayerID.PLAYER_TWO, TypeOfPiece.VILLAGER);

        playerTwoSettlements.add(new Settlement());
        playerTwoSettlements.get(0).markPieceInSettlement(new Location(0, 1), villager);
        playerTwoSettlements.get(0).markPieceInSettlement(new Location(-1, 1), villager);
        playerTwoSettlements.get(0).markPieceInSettlement(new Location(0, 2), villager);
        playerTwoSettlements.get(0).markPieceInSettlement(new Location(-2, 2), villager);

        playerOne.setListOfSettlements(playerOneSettlements);
        playerTwo.setListOfSettlements(playerTwoSettlements);


        gameBoardState = new GameBoardState(
                playerOne,
                playerTwo,
                2,
                hexMap,
                new GamePieceMap(),
                new ArrayList<Location>(),
                new ArrayList<Location>()
        );
    }

    @Test
    public void nukingTIlePlacementPhaseMakerShouldMakeACorrectPhase() {
        TilePlacementPhase generated = phaseMaker.getTilePlacement(gameBoardState, activePlayer, tileToPlace);
        Tile generatedTile = generated.getTileToPlace();
        Location[] locs = generatedTile.getArrayOfTerrainLocations();
        Settlement p2Set = gameBoardState.getPlayerTwo().getListOfSettlements().get(0);
        Assert.assertTrue(p2Set.locationIsInSettlement(locs[1]) || p2Set.locationIsInSettlement(locs[2]));
    }
}
