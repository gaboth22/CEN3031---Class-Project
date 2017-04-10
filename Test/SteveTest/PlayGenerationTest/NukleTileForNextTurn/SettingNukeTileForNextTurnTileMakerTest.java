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

       //TODO: write this test
    }

}
