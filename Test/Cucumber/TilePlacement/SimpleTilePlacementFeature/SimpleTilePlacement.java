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

public class SimpleTilePlacement {
    private GameBoard gameBoard;
    private TileBuilder tileBuilder;
    private TilePlacementPhase placementPhase1;
    private TilePlacementPhase placementPhase2;
    private Location origin;
    private Tile firstTile;
    private Tile secondTile;

    @Given("^tiles have already been placed on board$")
    public void initializeBoard() throws Exception {
        tileBuilder = new TileBuilder();
        firstTile = tileBuilder.getTileAtOrigin();
        origin = new Location(0, 0);
        gameBoard = new GameBoardImpl();
        placementPhase1 = new TilePlacementPhase(PlayerID.PLAYER_ONE, firstTile);
        placementPhase1.setTilePlacementType(TilePlacementType.FIRST_PLACEMENT);
        gameBoard.doTilePlacementPhase(placementPhase1);
    }

    @When("^a player tries a simple placement$")
    public void simplePlaceTile() throws Exception {
        secondTile = tileBuilder.getAdjacentTile(firstTile);
        placementPhase2 = new TilePlacementPhase(PlayerID.PLAYER_ONE, secondTile);
        placementPhase2.setTilePlacementType(TilePlacementType.SIMPLE_PLACEMENT);
        gameBoard.doTilePlacementPhase(placementPhase2);
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
        Location[] firstTileLocations = firstTile.getArrayOfTerrainLocations();
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
