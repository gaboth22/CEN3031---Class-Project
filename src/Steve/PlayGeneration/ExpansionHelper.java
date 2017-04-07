package Steve.PlayGeneration;

import Player.*;
import Location.Location;
import Settlements.Creation.Settlement;
import TileMap.*;
import Terrain.Terrain.*;
import Settlements.Expansion.SettlementExpansion;
import GamePieceMap.GamePieceMap;

import java.util.*;

public class ExpansionHelper {
    static private Terrain [] terrainTypes = {Terrain.GRASSLANDS, Terrain.JUNGLE, Terrain.LAKE, Terrain.ROCKY};

    static public void expansionChoice(Map<Location,Hexagon> hexes, Player currentPlayer, GamePieceMap pieces) {
        Settlement expansionCandidate;
        List<Location> locationsForExpansion;
        List<Settlement> playerSettlements = currentPlayer.getListOfSettlements();
        for (int i = 0; i < playerSettlements.size(); i ++) {
            expansionCandidate = playerSettlements.get(i);
            for (int j = 0; j < terrainTypes.length; j++) {
                locationsForExpansion = SettlementExpansion.findLocationsToExpandInto(hexes, expansionCandidate, pieces, terrainTypes[i]);
                if (locationsForExpansion.size() <= 0) {
                    continue;
                }
                if (SettlementExpansion.numVillagersRequiredToExpansion(hexes, locationsForExpansion) <= currentPlayer.getVillagerCount()) {
                    //create BuildPhase
                    return;
                }
            }
        }
    }

    static public boolean canExpand(Player currentPlayer) {
        if (currentPlayer.getVillagerCount() > 0 && currentPlayer.getListOfSettlements().size() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

}
