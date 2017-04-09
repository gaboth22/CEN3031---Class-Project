package SteveTest.PlayGenerationTest;

import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Player.PlayerID;
import Settlements.Creation.Settlement;
import Steve.PlayGeneration.SmartTilePlacer.NukeableVolcanoValidator;
import Terrain.Terrain.Terrain;
import TileMap.Hexagon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class NukeableVolcanoValidatorTest {
    private NukeableVolcanoValidator validator;
    private Settlement validSettlement;
    private Map<Location, Hexagon> validHexMap;
    private Map<Location, Hexagon> invalidHexMap;
    private Hexagon[] hexesOnBoard;

    @Before
    public void initializeInstances() throws Exception {
        validator = new NukeableVolcanoValidator();
        validSettlement = new Settlement();
        validHexMap = new HashMap<Location, Hexagon>();
        invalidHexMap = new HashMap<Location, Hexagon>();

        hexesOnBoard = new Hexagon[] {
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

        hexesOnBoard = new Hexagon[] {
                new Hexagon(1,
                        new Location(0, 0),
                        1,
                        Terrain.VOLCANO),
                new Hexagon(1,
                        new Location(-1, 1),
                        1,
                        Terrain.LAKE),
                new Hexagon(1,
                        new Location(0, 1),
                        1,
                        Terrain.JUNGLE),

                new Hexagon(2,
                        new Location(-1, 3),
                        1,
                        Terrain.VOLCANO),
                new Hexagon(2,
                        new Location(-1, 2),
                        1,
                        Terrain.GRASSLANDS),
                new Hexagon(2,
                        new Location(0, 2),
                        1,
                        Terrain.LAKE)
        };

        for(Hexagon hex : hexesOnBoard) {
            invalidHexMap.put(hex.getTerrainLocation(), hex);
        }
    }

    @Test
    public void validatorShouldReturnThatVolcanoesAreNukeable() {
        Assert.assertTrue(validator.isNukeableVolcano(new Location(0, 0), validSettlement, validHexMap));
    }

    @Test
    public void validatorShouldReturnThatVolcanoIsNukeableForAnotherValidVolcano() {
        Assert.assertTrue(validator.isNukeableVolcano(new Location(-1, 2), validSettlement, validHexMap));
    }

    @Test
    public void validatorShouldReturnThatVolcanoIsNotNukeableForEmptySettlement() {
        Assert.assertFalse(validator.isNukeableVolcano(new Location(0, 0), new Settlement(), validHexMap));
    }

    @Test
    public void validatorShouldReturnThatVolcanoIsNotNukeableForEmptyHexMap() {
        Assert.assertFalse(validator.isNukeableVolcano(new Location(0, 0), validSettlement, new HashMap<Location, Hexagon>()));
    }

    @Test
    public void validatorShouldReturnFalseWhenBothSettlementAndMapAreEmpty() {
        Assert.assertFalse(validator.isNukeableVolcano(new Location(0, 0),new Settlement() , new HashMap<Location, Hexagon>()));
    }

    @Test
    public void validatorShouldReturnFalseWhenVolcanoIsNotNukeable() {
        Assert.assertFalse(validator.isNukeableVolcano(new Location(0, 0), validSettlement, invalidHexMap));
    }

    @Test
    public void validatorShouldReturnGalseWhenAnotherVolcanoIsNotNukeable() {
        Assert.assertFalse(validator.isNukeableVolcano(new Location(-1, 3), validSettlement, invalidHexMap));
    }

    @Test(expected = InvalidParameterException.class)
    public void validatorShouldThrowIfPassedLocationIsNotAVolcano() {
        validator.isNukeableVolcano(new Location(-1, 1), validSettlement, validHexMap);
    }
}
