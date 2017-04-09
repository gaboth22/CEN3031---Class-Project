package SteveTest.PlayGenerationTest;

import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Player.PlayerID;
import Settlements.Creation.Settlement;
import Steve.BiHexTileStructure;
import Steve.PlayGeneration.SmartTilePlacer.NukingTileMaker;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import TileMap.Hexagon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

public class NukingTileMakerTest {

    private NukingTileMaker tileMaker;
    private Map<Location, Hexagon> hexMap;
    private Settlement nukeableSettlement;
    private Location ofVolcano;
    private BiHexTileStructure terrains;

    @Before
    public void initializeInstances() throws Exception {
        tileMaker = new NukingTileMaker();
        hexMap = new HashMap<Location, Hexagon>();
        nukeableSettlement = new Settlement();
        terrains = new BiHexTileStructure(new Terrain[]{Terrain.LAKE, Terrain.ROCKY});

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
            hexMap.put(hex.getTerrainLocation(), hex);
        }

        GamePiece villager = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        nukeableSettlement.markPieceInSettlement(new Location(0, 1), villager);
        nukeableSettlement.markPieceInSettlement(new Location(-1, 1), villager);
    }

    @Test
    public void nukingTileMakeShouldProduceAValidNukingTile() {
        givenThereVolcanoIsAt(new Location(0, 0));
        Tile nukingTile = tileMaker.makeTile(hexMap, nukeableSettlement, ofVolcano, terrains);

        Location[] locationsInTile = nukingTile.getArrayOfTerrainLocations();
        Assert.assertTrue(locationsInTile[0].equals(ofVolcano));
        Assert.assertTrue(
                nukeableSettlement.locationIsInSettlement(locationsInTile[1]) ||
                          nukeableSettlement.locationIsInSettlement(locationsInTile[2])
        );
    }

    private void givenThereVolcanoIsAt(Location loc) {
        ofVolcano = loc;
    }

    @Test
    public void nukingTileShouldProduceAValidNukingTileForADifferentPivotVolcano() {
        givenThereVolcanoIsAt(new Location(-1, 2));
        Tile nukingTile = tileMaker.makeTile(hexMap, nukeableSettlement, ofVolcano, terrains);

        Location[] locationsInTile = nukingTile.getArrayOfTerrainLocations();
        Assert.assertTrue(locationsInTile[0].equals(ofVolcano));
        Assert.assertTrue(
                nukeableSettlement.locationIsInSettlement(locationsInTile[1]) ||
                nukeableSettlement.locationIsInSettlement(locationsInTile[2])
        );
    }
}
