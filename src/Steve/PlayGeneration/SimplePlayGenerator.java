package Steve.PlayGeneration;

import GameBoard.GameBoardState;
import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementType;
import Player.Player;
import Player.PlayerID;
import Steve.BiHexTileStructure;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import Tile.Tile.TileImpl;
import TileMap.Hexagon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class SimplePlayGenerator implements PlayGenerator {

    private Location inhabitableLocationInNewlyCreatedTile;

    public SimplePlayGenerator() {
        inhabitableLocationInNewlyCreatedTile = null;
    }

    @Override
    public TilePlacementPhase generateSafeTilePlay(
            GameBoardState gameBoardState,
            PlayerID activePlayer,
            BiHexTileStructure tileToPlace) {

        Terrain leftTerrain = tileToPlace.getLeftTerrain();
        Terrain rightTerrain = tileToPlace.getRightTerrain();


        Map<Location, Hexagon> hexMap = gameBoardState.getPlacedHexagons();
        List<Location> placeableLocs = gameBoardState.getPlaceableLocations();

        int indexOfAvailable = -1;
        Location[] adjacents = null;
        Location toPutTileOn = null;


        while(indexOfAvailable == -1) {

            int index = ThreadLocalRandom.current().nextInt(placeableLocs.size());

            toPutTileOn = placeableLocs.get(index);

            adjacents = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(toPutTileOn);


            for (int i = 1; i < adjacents.length; i++) {
                if (!hexMap.containsKey(adjacents[i]) && !hexMap.containsKey(adjacents[i - 1])) {
                    indexOfAvailable = i - 1;
                    break;
                }
            }
        }

        Location ofLeftTerrain = adjacents[indexOfAvailable];
        Location ofRightTerrain = adjacents[indexOfAvailable + 1];

        inhabitableLocationInNewlyCreatedTile = ofRightTerrain;

        Location[] locationsOfTile = new Location[]{toPutTileOn, ofLeftTerrain, ofRightTerrain};
        Terrain[] terrainsOfTile = new Terrain[]{Terrain.VOLCANO, leftTerrain, rightTerrain};

        Tile toPlace = new TileImpl(Arrays.asList(terrainsOfTile), Arrays.asList(locationsOfTile));

        TilePlacementPhase safeTilePlacement = new TilePlacementPhase(activePlayer, toPlace);
        safeTilePlacement.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);

        return safeTilePlacement;
    }

    @Override
    public BuildPhase generateSafeBuildPlay(GameBoardState gameBoardState, PlayerID activePlayer) {
        if(inhabitableLocationInNewlyCreatedTile == null)
            return null;

        Player active;

        if(activePlayer == PlayerID.PLAYER_ONE)
            active = gameBoardState.getPlayerOne();
        else
            active = gameBoardState.getPlayerTwo();

        if(active.getVillagerCount() > 0) {
            GamePiece piece = new GamePiece(activePlayer, TypeOfPiece.VILLAGER);
            BuildPhase safeBuildPhase = new BuildPhase(piece, inhabitableLocationInNewlyCreatedTile);
            safeBuildPhase.setBuildType(BuildType.FOUND);
            inhabitableLocationInNewlyCreatedTile = null;
            return safeBuildPhase;
        }
        else
            return null;
    }

    @Override
    public TilePlacementPhase generateTilePlay(GameBoardState gameBoardState, PlayerID activePlayer, BiHexTileStructure tileToPlace) {
        //TODO: generate TilePlacement
        return null;
    }

    @Override
    public BuildPhase generateBuildPlay(GameBoardState gameBoardState, PlayerID activePlayer) {
        //TODO: generate PiecePlacement
        return null;
    }
}
