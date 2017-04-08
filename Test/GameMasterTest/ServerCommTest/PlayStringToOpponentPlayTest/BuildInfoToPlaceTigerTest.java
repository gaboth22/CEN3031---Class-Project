package GameMasterTest.ServerCommTest.PlayStringToOpponentPlayTest;

import GameMaster.ServerComm.Parsers.PlayStringToOpponentPlay.BuildInfoToPlaceTiger;
import GameMaster.ServerComm.Parsers.PlayStringToOpponentPlay.BuildInfoToPlaceTotoro;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Player.PlayerID;
import org.junit.Assert;
import org.junit.Test;

public class BuildInfoToPlaceTigerTest {

    String testString;
    BuildPhase buildPhase;
    PlayerID playerID;

    @Test
    public void placeTotoroShouldCreateValidTotoroPhase() {
        givenIHaveAStringToParse("BUILT TIGER PLAYGROUND AT 0 0 1");
        andIHaveAPlayerID(playerID);
        whenIGenerateABuildPhase();
        thenMyBuildPhaseShouldBeValid();
    }

    private void thenMyBuildPhaseShouldBeValid() {
        Assert.assertEquals(BuildType.PLACE_TIGER, buildPhase.getBuildType());
        Assert.assertEquals(new Location(1,0), buildPhase.getLocationToPlacePieceOn());
        Assert.assertEquals(playerID, buildPhase.getPlayerID());
        Assert.assertEquals(TypeOfPiece.TIGER, buildPhase.getTypeOfPieceToPlace());
    }

    private void givenIHaveAStringToParse(String toParse) {
        this.testString = toParse;
    }

    private void andIHaveAPlayerID(PlayerID player) {
        this.playerID = player;
    }

    private void whenIGenerateABuildPhase() {
        buildPhase = BuildInfoToPlaceTiger.getPlaceTigerPhase(testString, playerID);
    }
}
