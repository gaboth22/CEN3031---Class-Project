package Steve.PlayGeneration;

import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Player.*;
import Location.Location;
import Settlements.Creation.Settlement;
import TileMap.*;
import Terrain.Terrain.*;
import Settlements.Expansion.SettlementExpansion;
import GamePieceMap.*;

import java.util.*;

public class ExpansionHelper {
    static private Terrain [] terrainTypes = {Terrain.GRASSLANDS, Terrain.JUNGLE, Terrain.LAKE, Terrain.ROCKY};

    static public BuildPhase expansionChoice(Map<Location,Hexagon> hexes, Player currentPlayer, GamePieceMap pieces) {
        Settlement expansionCandidate;
        List<Location> locationsForExpansion;
        List<Settlement> playerSettlements = currentPlayer.getListOfSettlements();
        for (int i = 0; i < playerSettlements.size(); i ++) {
            expansionCandidate = playerSettlements.get(i);

            if(!expansionCandidate.hasTotoroSanctuary()) {
                for (int j = 0; j < terrainTypes.length; j++) {
                    locationsForExpansion = SettlementExpansion.findLocationsToExpandInto(hexes, expansionCandidate, pieces, terrainTypes[j]);
                    if (locationsForExpansion.size() <= 0) {
                        continue;
                    }
                    if (SettlementExpansion.numVillagersRequiredToExpansion(hexes, locationsForExpansion) <= currentPlayer.getVillagerCount()) {
                        if(notExpandingIntoOtherOwnedSettlement(locationsForExpansion, pieces, expansionCandidate, currentPlayer)){
                            //create BuildPhase

                            BuildPhase buildPhase
                                    = new BuildPhase(new GamePiece(currentPlayer.getID(), TypeOfPiece.VILLAGER), locationsForExpansion.get(0), expansionCandidate);
                            buildPhase.setBuildType(BuildType.EXPAND);
                            return buildPhase;
                        }

                    }
                }
            }
        }
        return null;
    }

    static public boolean canExpand(Player currentPlayer) {
        if (currentPlayer.getVillagerCount() > 0 && currentPlayer.getListOfSettlements().size() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    static private boolean notExpandingIntoOtherOwnedSettlement(
            List<Location> locationsForExpansion, GamePieceMap gamePieceMap, Settlement expansionCandidate, Player currentPlayer){

        Set<Location> locationsInExpansionCandidate = expansionCandidate.getSetOfLocationsInSettlement();
        for(int i = 0; i < locationsForExpansion.size(); i++){
            Location[] adjacents = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(locationsForExpansion.get(i));
            for(int j = 0; j < adjacents.length; j++){

                if(locationsInExpansionCandidate.contains(adjacents[j])){
                    continue;
                }

                if(gamePieceMap.isThereAPieceAt(adjacents[j])){
                    if(gamePieceMap.getPieceOwnerIdAtLocation(adjacents[j]) == currentPlayer.getID()){
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
