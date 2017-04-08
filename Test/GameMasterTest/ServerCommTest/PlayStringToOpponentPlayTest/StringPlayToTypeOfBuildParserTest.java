package GameMasterTest.ServerCommTest.PlayStringToOpponentPlayTest;

import GameMaster.ServerComm.Parsers.PlayStringToOpponentPlay.StringPlayToTypeOfBuildParser;
import Play.BuildPhase.BuildType;
import org.junit.Assert;
import org.junit.Test;

public class StringPlayToTypeOfBuildParserTest {

    private String testString;
    private BuildType actualType;

    @Test
    public void foundSettlementStringShouldReturnBuildType() {
        givenATestString("FOUNDED SETTLEMENT AT 0 0 1");
        whenIGetABuildPhase();
        thenMyBuildTypeShouldBe(BuildType.FOUND);
    }

    @Test
    public void expandSettlementStringShouldReturnBuildType() {
        givenATestString("EXPANDED SETTLEMENT AT 0 0 1 TERRAIN");
        whenIGetABuildPhase();
        thenMyBuildTypeShouldBe(BuildType.EXPAND);
    }

    @Test
    public void placeTotoroStringShouldReturnBuildType() {
        givenATestString("BUILT TOTORO SANCTUARY AT 0 0 1");
        whenIGetABuildPhase();
        thenMyBuildTypeShouldBe(BuildType.PLACE_TOTORO);
    }

    @Test
    public void placeTigerStringShouldReturnBuildType() {
        givenATestString("BUILT TIGER PLAYGROUND AT 0 0 1");
        whenIGetABuildPhase();
        thenMyBuildTypeShouldBe(BuildType.PLACE_TIGER);
    }

    private void thenMyBuildTypeShouldBe(BuildType expectedType) {
        Assert.assertEquals(expectedType, actualType);
    }

    private void whenIGetABuildPhase() {
        actualType = StringPlayToTypeOfBuildParser.getBuildType(testString);
    }

    private void givenATestString(String stringToParse) {
        this.testString = stringToParse;
    }



    //"FOUNDED SETTLEMENT AT 0 0 1"
    //"EXPANDED SETTLEMENT AT 0 0 1 TERRAIN"
    // "BUILT TOTORO SANCTUARY AT 0 0 1"
    // "BUILT TIGER PLAYGROUND AT 0 0 1"
}
