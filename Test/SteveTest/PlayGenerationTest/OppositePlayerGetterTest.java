package SteveTest.PlayGenerationTest;

import GameBoard.GameBoardState;
import GamePieceMap.GamePieceMap;
import Player.Player;
import Player.PlayerID;
import Steve.PlayGeneration.SmartTilePlacer.OppositePlayerGetter;
import TileMap.HexagonMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class OppositePlayerGetterTest {
    private OppositePlayerGetter opPlayerGetter;
    private GameBoardState gState;
    private PlayerID activePlayerId;
    private Player generatedOppositePlayer;

    @Before
    public void initializeInstances() {
        opPlayerGetter = new OppositePlayerGetter();
        gState = new GameBoardState(new Player(PlayerID.PLAYER_ONE),
                                    new Player(PlayerID.PLAYER_TWO),
                                    2,
                                    new HashMap<>(),
                                    new GamePieceMap(),
                                    new ArrayList<>(),
                                    new ArrayList<>(),
                                    new HexagonMap());
    }

    @Test
    public void shouldGetCorrectOppositePlayerFromPlayerOne() {
        givenTheActivePlayerIdIs(PlayerID.PLAYER_ONE);
        whenIGetTheOppositePlayer();
        thenTheOppositePlayerShouldBe(PlayerID.PLAYER_TWO);
    }

    private void givenTheActivePlayerIdIs(PlayerID pid) {
        activePlayerId = pid;
    }

    private void whenIGetTheOppositePlayer() {
        generatedOppositePlayer = opPlayerGetter.getOtherPlayerFromGameBoardState(activePlayerId, gState);
    }

    private void thenTheOppositePlayerShouldBe(PlayerID expectedOpPlayerId) {
        Assert.assertEquals(expectedOpPlayerId, generatedOppositePlayer.getID());
    }

    @Test
    public void shouldGetCorrectOppositePlayerFromPlayerTwo() {
        givenTheActivePlayerIdIs(PlayerID.PLAYER_TWO);
        whenIGetTheOppositePlayer();
        thenTheOppositePlayerShouldBe(PlayerID.PLAYER_ONE);
    }
}
