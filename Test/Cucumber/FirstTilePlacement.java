package Cucumber;

import GameBoard.*;
import Location.Location;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.PlayerID;
import TileBuilder.TileBuilder;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import Tile.Tile.Tile;
import org.junit.Assert;

public class FirstTilePlacement {
    private GameBoard gameBoard;
    private TileBuilder builder;
    private TilePlacementPhase placementPhase;
    private Location origin;
    private Tile firstTile;

    @Given("^it is the first turn$")
    public void InitializeGameboard() {
        builder = new TileBuilder();
        origin = new Location(0, 0);
        gameBoard = new GameBoardImpl();
        Assert.assertEquals(1, gameBoard.getCurrentTurn());
    }

    @When("^the active player places a tile")
    public void placeFirstTile() throws Exception {
        firstTile = builder.getTileAtOrigin();
        placementPhase = new TilePlacementPhase(PlayerID.PLAYER_ONE, firstTile);
        gameBoard.doTilePlacementPhase(placementPhase);
    }

    @Then("^the tile should be placed at center of table")
    public void tileShouldBeAtCenterOfTable() throws Exception {
        Assert.assertTrue(gameBoard.hasTileAt(firstTile.getArrayOfTerrainLocations()));
    }
}