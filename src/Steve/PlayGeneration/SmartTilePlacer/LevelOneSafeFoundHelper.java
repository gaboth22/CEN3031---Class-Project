package Steve.PlayGeneration.SmartTilePlacer;

import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Player.*;
import Location.Location;
import TileMap.*;
import Terrain.Terrain.*;
import GamePieceMap.*;
import java.util.*;

public class LevelOneSafeFoundHelper {

    static public BuildPhase LevelOneSafeFound(Map<Location,Hexagon> hexes, Player currentPlayer, GamePieceMap pieces){
        Set<Location> locations = hexes.keySet();
        Location[] locs = locations.toArray(new Location[locations.size()]);
        for(int i = 0; i < locs.length; i++){
            Hexagon hex = hexes.get(locs[i]);
            if(hex.getHeight() == 1 && hex.getTerrain() != Terrain.VOLCANO && !pieces.isThereAPieceAt(locs[i])){
                BuildPhase buildPhase
                        = new BuildPhase(new GamePiece(currentPlayer.getID(), TypeOfPiece.VILLAGER), locs[i]);
                buildPhase.setBuildType(BuildType.FOUND);
                return buildPhase;
            }
        }
        return null;
    }
}
