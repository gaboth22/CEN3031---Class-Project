package Steve.PlayGeneration.SmartTilePlacer;

import Location.Location;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Settlements.Creation.Settlement;
import Terrain.Terrain.Terrain;
import TileMap.Hexagon;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

public class NukeableVolcanoValidator {

    public boolean isNukeableVolcano(
            Location locOfVolcano,
            Settlement s,
            Map<Location, Hexagon> hexMap) {

        if(hexMap.containsKey(locOfVolcano) && hexMap.get(locOfVolcano).getTerrain() != Terrain.VOLCANO) {
            Terrain passedTerrain = hexMap.get(locOfVolcano).getTerrain();
            String errorMessage = "Attempting to find nukeable volcano, but passed location of " + passedTerrain;
            throw new InvalidParameterException(errorMessage);
        }

        Location[] adjacents = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(locOfVolcano);
        List<Integer> nukeAbleLocationIndices = PairOfLocationsAroundVolcanoListGetter.get(hexMap, adjacents);

        if(nukeAbleLocationIndices.size() == 0)
            return false;

        boolean foundAtLeastOneNukeableIndex = false;

        for(int i = 0; i < nukeAbleLocationIndices.size(); i += 2) {

            Location nuke1 = adjacents[nukeAbleLocationIndices.get(i)];
            Location nuke2 = adjacents[nukeAbleLocationIndices.get(i+ 1)];
            boolean isNukeableIndex = true;

            if (!foundAtLeastOneNukeableIndex && !s.locationIsInSettlement(nuke1) && !s.locationIsInSettlement(nuke2))
                isNukeableIndex = false;

            if (!foundAtLeastOneNukeableIndex && !locationsBelongToTwoDifferentTiles(nuke1, nuke2, hexMap))
                isNukeableIndex = false;

            if (!foundAtLeastOneNukeableIndex && !areAllLocationsOnSameLevel(locOfVolcano, nuke1, nuke2, hexMap))
                isNukeableIndex = false;

            if(isNukeableIndex)
                foundAtLeastOneNukeableIndex = true;
        }

        return foundAtLeastOneNukeableIndex;
    }

    private boolean locationsBelongToTwoDifferentTiles(
            Location l1,
            Location l2,
            Map<Location, Hexagon> hexMap) {

        return LocationsBelongToTwoDifferentTiles.check(l1, l2, hexMap);
    }

    private boolean areAllLocationsOnSameLevel(
            Location l1,
            Location l2,
            Location l3,
            Map<Location, Hexagon> hexMap) {

        return AllLocationsOnLevelOne.check(l1, l2, l3, hexMap);
    }
}
