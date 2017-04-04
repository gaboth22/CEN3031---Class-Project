package Play.Rule.PiecePlacementRules;

import GameBoard.*;
import GamePieceMap.*;
import GamePieceMap.TypeOfPiece;
import Movement.AxialMovement;
import Play.BuildPhase.BuildPhase;
import Location.*;
import Play.BuildPhase.BuildType;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Player.Player;
import Player.PlayerID;
import Settlements.Creation.Settlement;
import Settlements.Creation.SettlementCreator;
import Terrain.Terrain.Terrain;
import Tile.Tile.Tile;
import Tile.Tile.TileImpl;
import TileBuilder.TileBuilder;
import TileMap.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class PlayerMustHavePieceRuleTest {
    private Player player;
    private TileMap tileMap;
    private GamePieceMap gamePieceMap;
    private AxialMovement movement;
    private TileBuilder tileBuilder;

    @Before
    public void setUp(){
        movement = new AxialMovement();
        tileBuilder = new TileBuilder();
        gamePieceMap = new GamePieceMap();
        tileMap = new HexagonMap();
        player = new Player(PlayerID.PLAYER_ONE);
    }

    @After
    public void tearDown(){
        gamePieceMap = null;
        tileMap = null;
        player = null;
        movement = null;
        tileBuilder = null;
    }

    @Test
    public void playerHasEnoughPiecesForFoundationTest() throws Exception{
        GamePiece gamePiece = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhase = new BuildPhase(gamePiece, new Location(1,0));
        buildPhase.setBuildType(BuildType.FOUND);
        PlayerMustHavePieceRule.applyRule(player, gamePieceMap, buildPhase, tileMap);
    }

    @Test(expected = InvalidPiecePlacementRuleException.class)
    public void playerDoesNotHaveEnoughPiecesForFoundationTest() throws Exception{
        player.decrementVillagerCount(20);
        GamePiece gamePiece = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhase = new BuildPhase(gamePiece, new Location(1,0));
        buildPhase.setBuildType(BuildType.FOUND);
        PlayerMustHavePieceRule.applyRule(player, gamePieceMap, buildPhase, tileMap);
    }

    @Test
    public void playerHasEnoughPiecesForExpansionTest() throws Exception{
        GameBoardImpl gameBoard = new GameBoardImpl();

        GamePiece gamePieceOne = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhaseOne = new BuildPhase(gamePieceOne, new Location(1,0));
        buildPhaseOne.setBuildType(BuildType.FOUND);

        tileMap.insertTile(gameBoard.getFirstTile());
        tileMap.insertTile(generateTileForSimplePlacement());

        Settlement settlement = SettlementCreator.getSettlementAt(gamePieceMap, new Location(1,0));
        GamePiece gamePieceTwo = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhaseTwo = new BuildPhase(gamePieceTwo, new Location(1,0), settlement);
        buildPhaseTwo.setBuildType(BuildType.EXPAND);

        PlayerMustHavePieceRule.applyRule(player, gamePieceMap, buildPhaseTwo, tileMap);
    }

    private Tile generateTileForSimplePlacement() {
        Location[] locations = new Location[] {
                new Location(1, 1),
                new Location(0,1),
                new Location(0,2)
        };

        Terrain[] terrains = new Terrain[] {
                Terrain.VOLCANO,
                Terrain.GRASSLANDS,
                Terrain.GRASSLANDS
        };

        return new TileImpl(Arrays.asList(terrains), Arrays.asList(locations));
    }

    @Test(expected = InvalidPiecePlacementRuleException.class)
    public void playerDoesNotHaveEnoughPiecesForExpansionTest() throws Exception{
        GameBoardImpl gameBoard = new GameBoardImpl();

        GamePiece gamePieceOne = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        gamePieceMap.insertAPieceAt(new Location(1,0), gamePieceOne);

        tileMap.insertTile(gameBoard.getFirstTile());
        tileMap.insertTile(generateTileForSimplePlacement());

        Settlement settlement = SettlementCreator.getSettlementAt(gamePieceMap, new Location(1,0));
        GamePiece gamePieceTwo = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        BuildPhase buildPhaseTwo = new BuildPhase(gamePieceTwo, new Location(0,1), settlement);
        buildPhaseTwo.setBuildType(BuildType.EXPAND);

        player.decrementVillagerCount(19);
        PlayerMustHavePieceRule.applyRule(player, gamePieceMap, buildPhaseTwo, tileMap);
    }

    @Test
    public void playerHasEnoughPiecesForTotoroTest() throws Exception{
        GamePiece gamePiece = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.TOTORO);
        BuildPhase buildPhase = new BuildPhase(gamePiece, new Location(1,0));
        buildPhase.setBuildType(BuildType.PLACE_TOTORO);
        PlayerMustHavePieceRule.applyRule(player, gamePieceMap, buildPhase, tileMap);

    }

    @Test(expected = InvalidPiecePlacementRuleException.class)
    public void playerDoesNotHaveEnoughPiecesForTotoroTest() throws Exception{
        GamePiece gamePiece = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.TOTORO);
        BuildPhase buildPhase = new BuildPhase(gamePiece, new Location(1,0));
        buildPhase.setBuildType(BuildType.PLACE_TOTORO);
        player.decrementTotoroCount();
        player.decrementTotoroCount();
        player.decrementTotoroCount();
        PlayerMustHavePieceRule.applyRule(player, gamePieceMap, buildPhase, tileMap);

    }

    @Test
    public void playerHasEnoughPiecesForTigerTest() throws Exception{
        GamePiece gamePiece = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.TIGER);
        BuildPhase buildPhase = new BuildPhase(gamePiece, new Location(1,0));
        buildPhase.setBuildType(BuildType.PLACE_TIGER);
        PlayerMustHavePieceRule.applyRule(player, gamePieceMap, buildPhase, tileMap);
    }

    @Test(expected = InvalidPiecePlacementRuleException.class)
    public void playerDoesNotHaveEnoughPiecesForTigerTest() throws Exception{
        GamePiece gamePiece = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.TIGER);
        BuildPhase buildPhase = new BuildPhase(gamePiece, new Location(1,0));
        buildPhase.setBuildType(BuildType.PLACE_TIGER);
        player.decrementTigerCount();
        player.decrementTigerCount();
        PlayerMustHavePieceRule.applyRule(player, gamePieceMap, buildPhase, tileMap);
    }
}
