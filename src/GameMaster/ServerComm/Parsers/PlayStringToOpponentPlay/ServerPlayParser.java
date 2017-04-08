package GameMaster.ServerComm.Parsers.PlayStringToOpponentPlay;

import GameBoard.GameBoardState;
import Play.BuildPhase.BuildPhase;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementType;
import Player.PlayerID;
import Tile.Tile.Tile;
import Location.Location;
import TileMap.Hexagon;

import java.util.Map;

public class ServerPlayParser {

    static final int CUT_FROM_THIS_INDEX = 7;

    public static TilePlacementPhase getServerTilePlacement(String playFromServer, GameBoardState gameBoardState, PlayerID playerID) {

        Tile tileToPlace = StringPlayToTileParser.getTileFromPlay(playFromServer);
        TilePlacementPhase opponentPlacementPhase = new TilePlacementPhase(playerID, tileToPlace);
        opponentPlacementPhase.setTilePlacementType(setPlayTypeAsNukeOrSimple(tileToPlace, gameBoardState));

        return opponentPlacementPhase;
    }

    private static TilePlacementType setPlayTypeAsNukeOrSimple(Tile tile, GameBoardState gameBoardState) {

        Location volcanoLocation = tile.getArrayOfTerrainLocations()[0];
        Map<Location, Hexagon> hexMap = gameBoardState.getPlacedHexagons();

        if(hexMap.containsKey(volcanoLocation)) {
            return TilePlacementType.NUKE;
        }
        else {
            return TilePlacementType.SIMPLE_PLACEMENT;
        }
    }

    public static BuildPhase getServerPiecePlacement(String playFromServer, GameBoardState gameBoardState, PlayerID playerID) {
        //TODO: parse and return valid build phase
        //PLACED GRASS+ROCK AT 1 0 0 5

        String buildPlay = StringCutter.cutFirstNSubStringsFromStringSplitBySpace(playFromServer, CUT_FROM_THIS_INDEX);

        BuildPhase buildPhase = StringPlayToBuildPhaseParser.getBuildPhase(buildPlay, gameBoardState, playerID);

        return buildPhase;
    }
}
