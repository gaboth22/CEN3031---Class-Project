package GameBoard.GameBoardStateBuilder;

import GameBoard.*;
import GamePieceMap.GamePieceMap;
import Player.*;
import TileMap.Hexagon;
import Location.Location;

import java.util.List;
import java.util.Map;

public class GameBoardStateBuilderImpl implements GameBoardStateBuilder {

    @Override
    public GameBoardState buildGameBoardState(GameBoard board) {
        Player playerOne = board.getPlayer(PlayerID.PLAYER_ONE);
        Player playerTwo = board.getPlayer(PlayerID.PLAYER_TWO);
        int turnNumber = board.getCurrentTurn();
        Map<Location, Hexagon> hexagonMap = board.getPlacedHexagons();
        List<Location> placeable = board.getPlaceableLocations();
        GamePieceMap pieceMap = board.getGamePieceMap();

        GameBoardState gameBoardState = new GameBoardState(playerOne, playerTwo, turnNumber, hexagonMap, pieceMap, placeable);
        return gameBoardState;
    }
}
