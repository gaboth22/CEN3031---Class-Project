package GameMaster.ServerComm.Parsers.PlayStringToOpponentPlay;

import GameBoard.GameBoardState;
import GameMaster.ServerComm.Parsers.BasicParser;
import GamePieceMap.GamePiece;
import GamePieceMap.GamePieceMap;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Player.*;
import Settlements.Helper.AdjacentLocationsToSettlement;
import Terrain.Terrain.Terrain;
import Settlements.Creation.Settlement;
import TileMap.Hexagon;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

public class BuildInfoToBuildPhase {

    static final int FOUND_X_IND = 5;
    static final int FOUND_Y_IND = 3;

    public static BuildPhase getFoundPhase(String play, PlayerID playerID) {
        //e.g. "FOUNDED SETTLEMENT AT 0 0 1"
        int x = BasicParser.getIntegerFromStringAtIndex(play, FOUND_X_IND);
        int y = BasicParser.getIntegerFromStringAtIndex(play, FOUND_Y_IND);
        Location location = new Location(x,y);

        GamePiece piece = new GamePiece(playerID, TypeOfPiece.VILLAGER);
        BuildPhase toReturn = new BuildPhase(piece, location);
        toReturn.setBuildType(BuildType.FOUND);
        return toReturn;
    }

    static final int EXPAND_X_IND = 5;
    static final int EXPAND_Y_IND = 3;
    static final int EXPAND_TERRAIN_IND = 6;

    public static BuildPhase getExpandPhase(String play, GameBoardState gameBoardState, PlayerID playerID) {
        //e.g "EXPANDED SETTLEMENT AT 0 0 1 TERRAIN"

        int x = BasicParser.getIntegerFromStringAtIndex(play, EXPAND_X_IND);
        int y = BasicParser.getIntegerFromStringAtIndex(play, EXPAND_Y_IND);
        Location locationOfSettlementToExpand = new Location(x,y);

        Player playerToExtract = getPlayerFromGameState(gameBoardState, playerID);
        Settlement settlement = getSettlementAtLocation(playerToExtract, locationOfSettlementToExpand);

        String terrainString = BasicParser.getSubstringFromStringAtIndex(play, EXPAND_TERRAIN_IND);
        Terrain terrainToExpandTo = TileStringToTerrain.getTerrain(terrainString);
        Location locationOfTerrain = getLocationOfTerrain(gameBoardState, settlement, terrainToExpandTo);

        GamePiece gamePiece = new GamePiece(playerID, TypeOfPiece.VILLAGER);

        BuildPhase expandBuildPhase = new BuildPhase(gamePiece, locationOfTerrain, settlement);
        expandBuildPhase.setBuildType(BuildType.EXPAND);
        return expandBuildPhase;
    }

    private static Location getLocationOfTerrain(GameBoardState gameBoardState, Settlement settlement, Terrain terrain) {

        GamePieceMap pieceMap = gameBoardState.getGamePieceMap();
        Map<Location, Hexagon> hexagonMap = gameBoardState.getPlacedHexagons();

        Location[] locationsAdjacentToSettlement = AdjacentLocationsToSettlement.getLocationsAdjacentToSettlement(settlement);

        for(Location location : locationsAdjacentToSettlement) {

            if(!pieceMap.isThereAPieceAt(location) && hexagonMap.containsKey(location)) {
                if(hexagonMap.get(location).getTerrain() == terrain) {
                    return location;
                }
            }
        }

        // Would this be the correct type of exception?
        throw new InvalidParameterException("The Terrain " + terrain.name() + " is not adjacent to the given settlement");
    }

    private static Player getPlayerFromGameState(GameBoardState gameBoardState, PlayerID playerID) {
        if(playerID == PlayerID.PLAYER_ONE) {
            return gameBoardState.getPlayerOne();
        }
        else {
            return gameBoardState.getPlayerTwo();
        }
    }

    private static Settlement getSettlementAtLocation(Player extractedPlayer, Location locationToCheck) {
        List<Settlement> settlementList = extractedPlayer.getListOfSettlements();
        for(int i = 0; i < settlementList.size(); i++) {
            if(settlementList.get(i).locationIsInSettlement(locationToCheck)) {
                return settlementList.get(i);
            }
        }

        return new Settlement();
    }
}
