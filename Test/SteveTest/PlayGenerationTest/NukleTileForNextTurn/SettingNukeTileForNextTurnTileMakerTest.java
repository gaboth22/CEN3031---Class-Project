package SteveTest.PlayGenerationTest.NukleTileForNextTurn;

import Location.Location;
import Player.Player;
import Player.PlayerID;
import Settlements.Creation.Settlement;
import Steve.BiHexTileStructure;
import Steve.PlayGeneration.NukeTileSetter.SettingNukeTileForNextTurnTileMaker;
import Terrain.Terrain.Terrain;
import TileMap.Hexagon;
import org.junit.Before;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingNukeTileForNextTurnTileMakerTest {

    private PlayerID activePlayer;
    private SettingNukeTileForNextTurnTileMaker tileMaker;
    private BiHexTileStructure tileToPlace;

    @Before
    public void initializeInstances() throws Exception {
        activePlayer = PlayerID.PLAYER_ONE;
        Player playerOne = new Player(activePlayer);
        Player playerTwo = new Player(PlayerID.PLAYER_TWO);
        List<Settlement> playerOneSettlements = new ArrayList<>();
        List<Settlement> playerTwoSettlements = new ArrayList<>();

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
    }

}
