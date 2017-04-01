package GUI;

import GUI.PhaseToGuiAdapter.PhaseToGuiAdapter;
import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.PlayerID;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import Tile.Tile.TileImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class PhaseToGuiAdapterTest {
    private PhaseToGuiAdapter adapter;
    private String generatedString;

    @Before
    public void initializeInstance() {
        adapter = new PhaseToGuiAdapter();
        generatedString = null;
    }

    @Test
    public void generatedStringShouldBeCorrectForP1Build() {
        BuildPhase phase = givenIHaveABuildPhase(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER, 0, 0);
        whenIDoTheAdaption(phase);
        thenThenGeneratedStringShouldBe("1 piece vi 0 0");
    }

    private BuildPhase givenIHaveABuildPhase(PlayerID pid, TypeOfPiece pieceType, int locX, int loxY) {

        GamePiece piece = new GamePiece(pid, pieceType);
        Location loc = new Location(locX, loxY);

        return new BuildPhase(piece, loc);
    }

    private void whenIDoTheAdaption(Object phase) {
        generatedString = adapter.adapt(phase);
    }


    private void thenThenGeneratedStringShouldBe(String str) {
        Assert.assertEquals(str, generatedString);
    }

    @Test
    public void generatedStringShouldBeCorrectForP2Build() {
        BuildPhase phase = givenIHaveABuildPhase(PlayerID.PLAYER_TWO, TypeOfPiece.TIGER, 1, 0);
        whenIDoTheAdaption(phase);
        thenThenGeneratedStringShouldBe("2 piece ti 1 0");
    }

    @Test
    public void generatedStringShouldBeCorrectForP1TilePlacement() {
        TilePlacementPhase phase = givenIHaveATilePlacementPhase(PlayerID.PLAYER_ONE, Terrain.JUNGLE, Terrain.ROCKY);
        whenIDoTheAdaption(phase);
        thenThenGeneratedStringShouldBe("1 tile v j r 0 0 0 1 1 0");
    }

    private TilePlacementPhase givenIHaveATilePlacementPhase(PlayerID pid, Terrain t2, Terrain t3) {
        Terrain[] ters = {Terrain.VOLCANO, t2, t3};
        Location[] locs = {new Location(0, 0), new Location(0, 1), new Location(1, 0)};
        Tile tile = new TileImpl(Arrays.asList(ters), Arrays.asList(locs));
        return new TilePlacementPhase(pid, tile);
    }

    @Test
    public void generatedStringShouldBeCorrectForP2TilePlacement() {
        TilePlacementPhase phase = givenIHaveATilePlacementPhase(PlayerID.PLAYER_TWO, Terrain.GRASSLANDS, Terrain.LAKE);
        whenIDoTheAdaption(phase);
        thenThenGeneratedStringShouldBe("2 tile v g l 0 0 0 1 1 0");
    }
}
