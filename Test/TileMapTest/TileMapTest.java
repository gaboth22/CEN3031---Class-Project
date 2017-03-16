package TileMapTest;

import Terrain.TerrainLocation;
import TileMap.*;
import Tile.*;
import org.junit.Test;

public class TileMapTest {

    TileMap testMap = new TileMap();

    Tile tileToInsert;

    void givenThatYouHaveATileToInsert(){
        TerrainLocation volcanoLocation;
    }

    void whenYouPlaceTheTile() {

    }

    void theTileShouldBePlaced() {

    }

    @Test
    void testThatTheFirstTileIsPlacedAtTheOrigin(){
        testMap.placeTile(tileToInsert);
        givenThatYouHaveATileToInsert();
        whenYouPlaceTheTile();
        theTileShouldBePlaced();
    }
}
