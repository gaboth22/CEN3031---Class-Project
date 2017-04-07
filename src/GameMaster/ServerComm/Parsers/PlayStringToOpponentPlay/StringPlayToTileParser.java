package GameMaster.ServerComm.Parsers.PlayStringToOpponentPlay;

import GameMaster.ServerComm.Parsers.BasicParser;
import Tile.Tile.Tile;

public class StringPlayToTileParser {

    static final int TILE_INDEX = 1;

    /*
     * Dave's Z is our X
     * Dave's X is our Y
     */
    static final int X_IND = 5;
    static final int Y_IND = 4;


    public static Tile getTileFromPlay(String play) {
        String tileInfo = BasicParser.getSubstringFromStringAtIndex(play, TILE_INDEX);

        int x = BasicParser.getIntegerFromStringAtIndex(play, X_IND);
        int y = BasicParser.getIntegerFromStringAtIndex(play, Y_IND);


        return null;
    }


}
