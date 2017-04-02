package Play.Rule.PiecePlacementRules;

import GameBoard.*;
import GamePieceMap.*;
import GamePieceMap.TypeOfPiece;
import Play.BuildPhase.BuildPhase;
import Location.*;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Player.Player;
import Player.PlayerID;
import TileMap.*;
import org.junit.Test;

public class PlayerMustHavePieceRuleTest {
    private Player player;
    private TileMap tileMap;
    private GamePieceMap gamePieceMap;
    private BuildPhase buildPhase;

    @Test
    public void playerHasEnoughPiecesForFoundationTest(){
        GameBoardImpl gameBoard = new GameBoardImpl();
        GamePiece gamePiece = new GamePiece(PlayerID.PLAYER_ONE,TypeOfPiece.VILLAGER);
        BuildPhase buildPhase = new BuildPhase(gamePiece, new Location(1,0));
        PlayerMustHavePieceRule.applyRule(gameBoard);

    }

    @Test(expected = InvalidPiecePlacementRuleException.class)
    public void playerDoesNotHaveEnoughPiecesForFoundationTest(){


    }

    @Test
    public void playerHasEnoughPiecesForExpansionTest(){


    }

    @Test(expected = InvalidPiecePlacementRuleException.class)
    public void playerDoesNotHaveEnoughPiecesForExpansionTest(){


    }

    @Test
    public void playerHasEnoughPiecesForTotoroTest(){


    }

    @Test(expected = InvalidPiecePlacementRuleException.class)
    public void playerDoesNotHaveEnoughPiecesForTotoroTest(){


    }

    @Test
    public void playerHasEnoughPiecesForTigerTest(){


    }

    @Test(expected = InvalidPiecePlacementRuleException.class)
    public void playerDoesNotHaveEnoughPiecesForTigerTest(){


    }
}
