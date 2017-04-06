package GameMaster.ServerComm.Parsers;

import Play.TilePlacementPhase.TilePlacementPhase;
import Tile.Tile.Tile;

public class TilePlacementPhaseToStringConverter {

    public static String getStringConversion(TilePlacementPhase phase) {

        Tile tileToPlace = phase.getTileToPlace();
        String rightTerrain = TerrainToStringConverter.getString(tileToPlace.getArrayOfTerrains()[2]);
        String leftTerrain = TerrainToStringConverter.getString(tileToPlace.getArrayOfTerrains()[1]);
        String PLUS_SIGN = "\\+";

        String serverTile = leftTerrain + PLUS_SIGN.replace("\\", "") + rightTerrain;

        int volcanoX = tileToPlace.getArrayOfTerrainLocations()[0].getX();
        int volcanoY = tileToPlace.getArrayOfTerrainLocations()[0].getY();

        int serverX = volcanoY;
        int serverY = ExtraCoordinateGenerator.generate(volcanoX, volcanoY);
        int serverZ = volcanoX;

        int serverOrientation = TileOrientationForServerGetter.getOrientation(tileToPlace);

        //TODO: double check whether spaces are needed -- Not clear from networking protocol
        return serverTile + " AT " + serverX + " " + serverY + " " + serverZ + " " + serverOrientation;
    }
}
