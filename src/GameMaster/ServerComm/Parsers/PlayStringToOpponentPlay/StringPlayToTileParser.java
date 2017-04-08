package GameMaster.ServerComm.Parsers.PlayStringToOpponentPlay;

import GameMaster.ServerComm.Parsers.BasicParser;
import GameMaster.ServerComm.Parsers.Split;
import Tile.Tile.Tile;
import Location.Location;
import Tile.TileFactory.TileFactory;
import Tile.TileInformationGenerator.TileInformationGenerator;
import Terrain.Terrain.Terrain;

import java.security.InvalidParameterException;

public class StringPlayToTileParser {

    static final int TILE_INDEX = 1;

    /*
     * Dave's Z is our X
     * Dave's X is our Y
     */
    static final int X_IND = 5;
    static final int Y_IND = 3;

    static final int ORIENTATION_IND = 6;

    public static Tile getTileFromPlay(String play) {

        Location volcanoLocation = getLocationFromPlay(play);
        int orientation = getOrientationFromPlay(play);
        Terrain[] terrains = getTerrainFromPlay(play);

        TileInformationGenerator tig = new TileFromOrientationAndTerrain(orientation, volcanoLocation, terrains);

        return makeTile(tig);
    }

    private static Tile makeTile(TileInformationGenerator tig) {
        TileFactory tf = TileFactory.getTileFactory();
        Tile toPlace;

        try{
            toPlace = tf.makeTile(tig);
        }
        catch(Exception ex) {
            throw new InvalidParameterException("Attempted to create Tile with impossible parameters: " +
                    ex.getMessage());
        }
        return toPlace;
    }

    private static Location getLocationFromPlay(String play) {
        int x = BasicParser.getIntegerFromStringAtIndex(play, X_IND);
        int y = BasicParser.getIntegerFromStringAtIndex(play, Y_IND);
        return new Location(x,y);
    }

    private static int getOrientationFromPlay(String play) {
        return BasicParser.getIntegerFromStringAtIndex(play, ORIENTATION_IND);
    }

    private static Terrain[] getTerrainFromPlay(String play) {

        String tileInfo = BasicParser.getSubstringFromStringAtIndex(play, TILE_INDEX);
        String[] tiles = Split.byPlus(tileInfo);

        Terrain[] terrains = new Terrain[tiles.length];
        for(int i = 0; i < tiles.length; i++) {
            terrains[i] = TileStringToTerrain.getTerrain(tiles[i]);
        }

        return terrains;
    }


}
