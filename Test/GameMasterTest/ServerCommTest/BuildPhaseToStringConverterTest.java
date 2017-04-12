package GameMasterTest.ServerCommTest;

import GameMaster.ServerComm.Parsers.BuildPhaseToStringConverter;
import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Play.BuildPhase.BuildPhase;
import Play.BuildPhase.BuildType;
import Player.PlayerID;
import Settlements.Creation.Settlement;
import Terrain.Terrain.Terrain;
import org.junit.Assert;
import org.junit.Test;

        /*
            In these tests : As of networking protocol v1.1
            Our x is the server's z
            Our y is the server's x
            -(our x + our y) is the server's y
         */

public class BuildPhaseToStringConverterTest {
    private String conversion;

    @Test
    public void conversionShouldBeRightForSettlementFoundation() {

        BuildPhase foundation = givenIHaveASettlementFoundingBuildPhase();
        whenIConvertItToString(foundation, null);
        thenTheConvertedStringShouldBe("FOUND SETTLEMENT AT 2 -3 1");

    }

    private BuildPhase givenIHaveASettlementFoundingBuildPhase() {
        GamePiece villager = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        Location toSettleOn =  new Location(1,2);
        BuildPhase foundation = new BuildPhase(villager, toSettleOn);
        foundation.setBuildType(BuildType.FOUND);

        return foundation;
    }

    private void whenIConvertItToString(BuildPhase phase, Terrain expandedOn) {
        conversion = BuildPhaseToStringConverter.getStringConversion(phase, expandedOn);
    }

    private void thenTheConvertedStringShouldBe(String expectedConversion) {
        Assert.assertEquals(expectedConversion, conversion);
    }

    @Test
    public void conversionShouldBeCorrectForSettlementExpansion() throws Exception {

        BuildPhase expansion = givenIHaveSettlementExpandingPhase();
        whenIConvertItToString(expansion, Terrain.JUNGLE);
        thenTheConvertedStringShouldBe("EXPAND SETTLEMENT AT 1 -1 0 JUNGLE");
    }

    private BuildPhase givenIHaveSettlementExpandingPhase() throws Exception {
        GamePiece villager = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER);
        Location toExpandOn =  new Location(-1,-2);
        Settlement s = new Settlement();
        s.markPieceInSettlement(new Location(0, 1), new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.VILLAGER));
        BuildPhase expansion = new BuildPhase(villager, toExpandOn, s);
        expansion.setBuildType(BuildType.EXPAND);

        return expansion;
    }

    @Test
    public void conversionShouldBeCorrectForTotoroSanctuary() {
        BuildPhase totoroSanctuary = givenIHaveATotoroSanctuaryPhase();
        whenIConvertItToString(totoroSanctuary, null);
        String expectedMessage = "BUILD TOTORO SANCTUARY AT -3 0 3";
        thenTheConvertedStringShouldBe(expectedMessage);

    }

    private BuildPhase givenIHaveATotoroSanctuaryPhase() {
        GamePiece villager = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.TOTORO);
        Location toPutSanctuary =  new Location(3,-3);
        BuildPhase totoroSanctuary = new BuildPhase(villager, toPutSanctuary, null);
        totoroSanctuary.setBuildType(BuildType.PLACE_TOTORO);

        return totoroSanctuary;
    }

    @Test
    public void conversionShouldBeCorrectForTigerPlayground() {
        BuildPhase tigerPlayground = givenIHaveATigerPlayGroundPhase();
        whenIConvertItToString(tigerPlayground, null);
        thenTheConvertedStringShouldBe("BUILD TIGER PLAYGROUND AT 0 1 -1");
    }

    private BuildPhase givenIHaveATigerPlayGroundPhase() {
        GamePiece villager = new GamePiece(PlayerID.PLAYER_ONE, TypeOfPiece.TIGER);
        Location toPutPlayground =  new Location(-1,0);
        BuildPhase tigerPlayground = new BuildPhase(villager, toPutPlayground, null);
        tigerPlayground.setBuildType(BuildType.PLACE_TIGER);

        return tigerPlayground;
    }

    @Test
    public void conversionShouldBeUnableToBuildWhenPhaseIsNull() {
        whenIConvertItToString(null, null);
        thenTheConvertedStringShouldBe("UNABLE TO BUILD");
    }
}