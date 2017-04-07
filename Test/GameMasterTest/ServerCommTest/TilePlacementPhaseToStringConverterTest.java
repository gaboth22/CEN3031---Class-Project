package GameMasterTest.ServerCommTest;

import GameMaster.ServerComm.Parsers.TilePlacementPhaseToStringConverter;
import Location.Location;
import Movement.*;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.PlayerID;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import Tile.Tile.TileImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;

public class TilePlacementPhaseToStringConverterTest {
    private Movement mov;
    private String converted;

    @Before
    public void setUp() {
        mov = new AxialMovement();
    }


    @Test
    public void theStringConversionShouldBeCorrect() {
        /*         UP UPRIGHT
            Tile:  V
            Terrains: UP (left) = ROCKY, UPRIGHT (right) LAKE
            Volcano location: 0, 1
            Then string should be:

            ROCK+LAKE AT 1 -1 0 6

            This is because the server's x is our y, the server's z is our x
            and the server's y is -(our x + our y), and the tile configuration
            corresponds to tile orientation number 3
         */

        TilePlacementPhase phase = givenIHaveTheRightTilePlacementPhase();
        whenIGetConvertTheTilePlacementPhaseToString(phase);
        thenTheConversionShouldBe("ROCK+LAKE AT 1 -1 0 6");
    }

    private TilePlacementPhase givenIHaveTheRightTilePlacementPhase() {
        Location ofVolcano = new Location(0, 1);

        Location[] locations =  new Location[] {
                ofVolcano,
                mov.up(ofVolcano),
                mov.upRight(ofVolcano)
        };

        Terrain[] terrains = new Terrain[]{Terrain.VOLCANO, Terrain.ROCKY, Terrain.LAKE};

        Tile tile = new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));

        return new TilePlacementPhase(PlayerID.PLAYER_ONE, tile);
    }

    private void whenIGetConvertTheTilePlacementPhaseToString(TilePlacementPhase phase) {
        converted = TilePlacementPhaseToStringConverter.getStringConversion(phase);
    }

    private void thenTheConversionShouldBe(String expectedConversion) {
        Assert.assertEquals(expectedConversion, converted);
    }

    @Test
    public void theStringConversionShouldBeRightAgain() {
        /*            UPRIGHT
            Tile:  V
                      DOWNRIGHT

            Terrains: UPRIGHT (left) = GRASSLANDS, DOWNRIGHT (right) JUNGLE
            Volcano location: 0, -2
            Then string should be:

            GRASS+JUNGLE AT -2 2 0 1

            This is because the server's x is our y, the server's z is our x
            and the server's y is -(our x + our y), and the tile configuration
            corresponds to tile orientation number 4
         */

        TilePlacementPhase phase = givenIHaveAnotherRightTilePlacementPhase();
        whenIGetConvertTheTilePlacementPhaseToString(phase);
        thenTheConversionShouldBe("GRASS+JUNGLE AT -2 2 0 1");
    }

    private TilePlacementPhase givenIHaveAnotherRightTilePlacementPhase() {
        Location ofVolcano = new Location(0, -2);

        Location[] locations =  new Location[] {
                ofVolcano,
                mov.upRight(ofVolcano),
                mov.downRight(ofVolcano)
        };

        Terrain[] terrains = new Terrain[]{Terrain.VOLCANO, Terrain.GRASSLANDS, Terrain.JUNGLE};

        Tile tile = new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));

        return new TilePlacementPhase(PlayerID.PLAYER_ONE, tile);
    }
}
