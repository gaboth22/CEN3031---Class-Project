package Play.Rule.TilePlacementRules;

import Player.PlayerID;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import Location.Location;
import GamePieceMap.*;
import Play.Rule.PlacementRuleException.InvalidTilePlacementRuleException;
import Tile.Tile.TileImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;

import static org.junit.Assert.*;

public class HexesBelowMustNotContainTigerTest {
    private GamePieceMap pieceMap;
    private Terrain[] terrain = {Terrain.VOLCANO, Terrain.JUNGLE, Terrain.GRASSLANDS};
    private Location[] location = {new Location(0,0), new Location(0,1), new Location(1,1)};
    private Tile tileToPlace;

    @Before
    public void setUp() {
        pieceMap = new GamePieceMap();
        tileToPlace = new TileImpl(Arrays.asList(terrain), Arrays.asList(location));
    }

    @After
    public void tearDown() {
        pieceMap = null;
        tileToPlace = null;
    }

    @Test
    public void applyRuleShouldProhibitPlacementTest() {
        boolean thrown = false;
        GamePiece testPiece = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.TIGER);

        try {
            pieceMap.insertAPieceAt(new Location(0,0), testPiece);
            HexesBelowMustNotContainTiger.applyRule(pieceMap, tileToPlace);
        }
        catch (LocationNotEmptyException e) {
        }
        catch (InvalidTilePlacementRuleException e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Test
    public void applyRuleShouldAllowPlacementOverVillagerTest() {
        boolean notThrown = false;
        GamePiece testPiece = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);

        try {
            pieceMap.insertAPieceAt(new Location(0,0), testPiece);
            HexesBelowMustNotContainTiger.applyRule(pieceMap, tileToPlace);
            notThrown = true;
        }
        catch (LocationNotEmptyException e) {
        }
        catch (InvalidTilePlacementRuleException e) {
        }

        assertTrue(notThrown);
    }

    @Test
    public void applyRuleShouldAllowPlacementOverEmptyHexTest() {
        boolean notThrown = false;

        try {
            HexesBelowMustNotContainTiger.applyRule(pieceMap, tileToPlace);
            notThrown = true;
        }
        catch (InvalidTilePlacementRuleException e) {
        }

        assertTrue(notThrown);
    }
}