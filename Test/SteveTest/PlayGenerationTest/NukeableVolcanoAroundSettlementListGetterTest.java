package SteveTest.PlayGenerationTest;

import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Player.PlayerID;
import Settlements.Creation.Settlement;
import Steve.PlayGeneration.SmartTilePlacer.NukeableVolcanoAroundSettlementListGetter;
import Terrain.Terrain.Terrain;
import TileMap.Hexagon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NukeableVolcanoAroundSettlementListGetterTest {
    private NukeableVolcanoAroundSettlementListGetter getter;
    private Settlement validSettlement;
    private Map<Location, Hexagon> validHexMap;

    @Before
    public void initializeInstances() throws Exception {
        getter = new NukeableVolcanoAroundSettlementListGetter();
        validSettlement = new Settlement();
        validHexMap = new HashMap<Location, Hexagon>();


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
                        Terrain.LAKE)
        };

        for(Hexagon hex : hexesOnBoard) {
            validHexMap.put(hex.getTerrainLocation(), hex);
        }

        GamePiece villager = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        validSettlement.markPieceInSettlement(new Location(0, 1), villager);
        validSettlement.markPieceInSettlement(new Location(-1, 1), villager);
    }

    @Test
    public void listGetterShouldGetTheRightList() {
        List<Location> nukeableVolcanoList = getter.getList(validSettlement, validHexMap);
        Assert.assertEquals(2, nukeableVolcanoList.size());
    }
}
