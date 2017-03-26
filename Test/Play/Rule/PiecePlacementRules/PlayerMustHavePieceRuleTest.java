package Play.Rule.PiecePlacementRules;

import GamePieceMap.TypeOfPiece;
import Play.Rule.PlacementRuleException.InvalidPiecePlacementRuleException;
import Player.Player;
import Player.PlayerID;
import org.junit.Before;
import org.junit.Test;

public class PlayerMustHavePieceRuleTest {
    private Player player;

    @Before
    public void initializeInstances(){
        player = new Player(PlayerID.PLAYER_ONE);
    }

    @Test(expected = InvalidPiecePlacementRuleException.class)
    public void ruleShouldFailSincePlayerIsOutOfVillagers() throws InvalidPiecePlacementRuleException{
        givenThePlayerRunsOutOfVillagers();
        whenTheRuleIsAppliedFor(TypeOfPiece.VILLAGER);
    }

    private void givenThePlayerRunsOutOfVillagers() {
        int maxVillagerCount = player.getVillagerCount();
        for(int i = 0; i < maxVillagerCount; i++) {
            player.decrementVillagerCount();
        }
    }

    private void whenTheRuleIsAppliedFor(TypeOfPiece piece) throws InvalidPiecePlacementRuleException {
        PlayerMustHavePieceRule.applyRule(player, piece);
    }

    @Test
    public void ruleShouldPassSincePlayerHasOneVillager() throws InvalidPiecePlacementRuleException {
        givenThePlayerHasOneVillagerLeft();
        whenTheRuleIsAppliedFor(TypeOfPiece.VILLAGER);
    }

    private void givenThePlayerHasOneVillagerLeft() {
        int villagersToRemove = player.getVillagerCount() - 1;
        for(int i = 0; i < villagersToRemove; i++) {
            player.decrementVillagerCount();
        }
    }

    @Test(expected = InvalidPiecePlacementRuleException.class)
    public void ruleShouldFailSincePlayerIsOutOfTigers() throws InvalidPiecePlacementRuleException {
        givenThePlayerRunsOutOfTigers();
        whenTheRuleIsAppliedFor(TypeOfPiece.TIGER);
    }

    private void givenThePlayerRunsOutOfTigers() {
        int maxTigerCount = player.getTigerCount();
        for(int i = 0; i < maxTigerCount; i++) {
            player.decrementTigerCount();
        }
    }

    @Test
    public void ruleShouldPassSincePlayerHasOneTiger() throws InvalidPiecePlacementRuleException {
        givenThePlayerHasOneTigerLeft();
        whenTheRuleIsAppliedFor(TypeOfPiece.TIGER);
    }

    private void givenThePlayerHasOneTigerLeft() {
        int tigersToRemove = player.getTigerCount() - 1;
        for(int i = 0; i < tigersToRemove; i++) {
            player.decrementTigerCount();
        }
    }

    @Test(expected = InvalidPiecePlacementRuleException.class)
    public void ruleShouldFailSincePlayerIsOutOfTotoro() throws InvalidPiecePlacementRuleException {
        givenThePlayerRunsOutOfTotoro();
        whenTheRuleIsAppliedFor(TypeOfPiece.TOTORO);
    }

    private void givenThePlayerRunsOutOfTotoro() {
        int maxTotoroCount = player.getTotoroCount();
        for(int i = 0; i < maxTotoroCount; i++) {
            player.decrementTotoroCount();
        }
    }

    @Test
    public void ruleShouldPassSincePlayerHasOneTotoro() throws InvalidPiecePlacementRuleException {
        givenThePlayerHasOneTotoroLeft();
        whenTheRuleIsAppliedFor(TypeOfPiece.TOTORO);
    }

    private void givenThePlayerHasOneTotoroLeft() {
        int totoroToRemove = player.getTotoroCount() - 1;
        for(int i = 0; i < totoroToRemove; i++) {
            player.decrementTotoroCount();
        }
    }
}
