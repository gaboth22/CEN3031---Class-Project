package Cucumber.TilePlacement.SimpleTilePlacementFeature;

import GameBoard.*;
import Location.Location;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Play.TilePlacementPhase.TilePlacementPhase;
import Play.TilePlacementPhase.TilePlacementType;
import Player.PlayerID;
import TileBuilder.TileBuilder;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import Tile.Tile.Tile;

import java.util.Set;

public class SimpleTilePlacement {
    private GameBoard gameBoard;
    private TilePlacementPhase placementPhase;
    private Tile secondTile;
    private Set<Location> locationsInMap;
    private TileBuilder tileBuilder;

    @Given("^tiles have already been placed on board$")
    public void initializeBoard() throws Exception {
        gameBoard = new GameBoardImpl();
        tileBuilder = new TileBuilder();
    }

    @When("^a player tries a simple placement$")
    public void simplePlaceTile() throws Exception {
        secondTile = tileBuilder.getTileWithLocations(
                new Location(1 ,1),
                new Location(2, 1),
                new Location(2, 0)
        );
        placementPhase = new TilePlacementPhase(PlayerID.PLAYER_ONE, secondTile);
        placementPhase.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(placementPhase);
    }

    @Then("^the tile should touch other already placed tile on at least one edge$")
    public void tileShouldBeAdjacent() throws Exception {
        Location[] secondTileLocations = secondTile.getArrayOfTerrainLocations();
        for (int i = 0; i < secondTileLocations.length; i++) {
            if (firstTileLocationIsAdjacent(secondTileLocations[i]))
                return;
        }

        throw new Exception(":(");
    }

    private boolean firstTileLocationIsAdjacent(Location locationInSecondTile) {
        Location[] firstTileLocations = ((GameBoardImpl) gameBoard).getFirstTile().getArrayOfTerrainLocations();
        Location[] adjacents = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(locationInSecondTile);
        for(int i = 0; i < firstTileLocations.length; i++) {
            for(int j = 0; j < adjacents.length; j++) {
                if(firstTileLocations[i].equals(adjacents[j]))
                    return true;
            }
        }

        return false;
    }
}
