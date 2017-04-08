package GameMasterTest.ServerCommTest.PlayStringToOpponentPlayTest;

import GameMaster.ServerComm.Parsers.PlayStringToOpponentPlay.StringPlayToTileParser;
import org.junit.Assert;
import org.junit.Test;
import Tile.Tile.Tile;
import Terrain.Terrain.Terrain;
import Location.Location;

public class StringPlayToTileParserTest {


    /*
     * NOTE: <x> <y> <z>
     * Dave's z is our x, and Dave's x is our y
     */
    @Test
    public void aValidStringShouldProduceATile() {
        String play = "PLACED GRASS+ROCK AT 1 2 3 5 FOUNDED SETTLEMENT AT 1 0 1";
        Tile testTile = StringPlayToTileParser.getTileFromPlay(play);

        Terrain[] terrains = testTile.getArrayOfTerrains();
        Assert.assertEquals(Terrain.VOLCANO, terrains[0]);
        Assert.assertEquals(Terrain.GRASSLANDS, terrains[1]);
        Assert.assertEquals(Terrain.ROCKY, terrains[2]);

        Location[] locations = testTile.getArrayOfTerrainLocations();
        Assert.assertEquals(new Location(3,1), locations[0]);
    }

}
