package Cucumber;

import GameBoard.*;
import Location.Location;
import Movement.*;
import Play.TilePlacementPhase.TilePlacementPhase;
import Player.PlayerID;
import TileBuilder.TileBuilder;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import Tile.Tile.Tile;
import org.junit.Assert;

public class FirstTileBadPlacement {
    private GameBoard gameBoard;
    private TileBuilder builder;
    private TilePlacementPhase placementPhase;
    private Location origin;
    private Tile firstTile;
    private Movement movement;
    private boolean insertionFailed;

    @Given("^it's the first turn$")
    public void InitializeGameboardAgain() {
        insertionFailed = false;
        builder = new TileBuilder();
        origin = new Location(0, 0);
        gameBoard = new GameBoardImpl();
        movement = new AxialMovement();
        Assert.assertEquals(1, gameBoard.getCurrentTurn());
    }

    @When("^the active player tries to place a tile incorrectly$")
    public void placeFirstTileAgain() throws Exception {
        Location notOrigin = new Location(1,2);
        firstTile = builder.getTileWithLocations(notOrigin,
                                                movement.up(notOrigin),
                                                movement.upRight(notOrigin));
        placementPhase = new TilePlacementPhase(PlayerID.PLAYER_ONE, firstTile);

        try {
            gameBoard.doTilePlacementPhase(placementPhase);
        }
        catch(Exception e) {
            insertionFailed = true;
        }
    }

    @Then("^then the insertion should not be valid$")
    public void theInsertionShouldBeInvalid() {
        Assert.assertTrue(insertionFailed);
    }
}