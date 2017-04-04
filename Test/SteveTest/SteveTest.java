package SteveTest;

import GameBoard.GameBoardState;
import GameBoard.GameBoardStateSenderData;
import GameBoard.PhasePublisherData.BuildPhaseSenderData;
import GameBoard.PhasePublisherData.TilePlacementPhaseSenderData;
import GameBoard.PlayAck.PlayAck;
import GameBoard.PlayAck.PlayAckSenderData;
import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.PlayerID;
import Sender.Sender;
import Sender.SenderData.SenderData;
import Steve.*;
import Receiver.Receiver;
import TileBuilder.TileBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import Tile.Tile.Tile;

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
                null);

        builder = new TileBuilder();

        steve = new Steve();
        steve.playAs(PlayerID.PLAYER_ONE);

        playGeneratorTestDouble = new PlayGeneratorTestDouble();

    }

    //TODO: write tests for Steve

}