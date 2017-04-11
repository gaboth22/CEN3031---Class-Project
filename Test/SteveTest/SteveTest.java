package SteveTest;

import GameBoard.GameBoardState;
import Player.PlayerID;
import Steve.*;
import TileBuilder.TileBuilder;
import org.junit.Before;

public class SteveTest {

    private GameBoardState gameBoardState;
    private TileBuilder builder;

    private Steve steve;
    private PlayGeneratorTestDouble playGeneratorTestDouble;

    private Object phaseReceivedFromSteve;

    @Before
    public void initializeInstances() {

        /*
            Dummy GameBoard to just have an instance of it
         */
        gameBoardState = new GameBoardState(
                null,
                null,
                0,
                null,
                null,
                null,
                null,
                null);

        builder = new TileBuilder();

        steve = new Steve();
        steve.playAs(PlayerID.PLAYER_ONE);

        playGeneratorTestDouble = new PlayGeneratorTestDouble();

    }

    //TODO: write tests for Steve

}