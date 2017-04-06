package GameMasterTest.ServerCommTest;

import GameMaster.ServerComm.Parsers.BuildTypeToStringForOutgoingPlay;
import Play.BuildPhase.BuildType;
import org.junit.Assert;
import org.junit.Test;

public class BuildTypeToStringForOutgoingPlayTest {
    @Test
    public void stringShouldBeCorrectForSettlementFoundation() {
        String foundationAsString = BuildTypeToStringForOutgoingPlay.getString(BuildType.FOUND);
        Assert.assertEquals("FOUND SETTLEMENT", foundationAsString);
    }

    @Test
    public void stringShouldBeCorrectForSettlementExpansion() {
        String expansionAsString = BuildTypeToStringForOutgoingPlay.getString(BuildType.EXPAND);
        Assert.assertEquals("EXPAND SETTLEMENT", expansionAsString);
    }

    @Test
    public void stringShouldBeCorrectForTotoroSanctuary() {
        String totoroSanctuaryAsString = BuildTypeToStringForOutgoingPlay.getString(BuildType.PLACE_TOTORO);
        Assert.assertEquals("BUILD TOTORO SANCTUARY", totoroSanctuaryAsString);
    }

    @Test
    public void stringShouldBeCorrectForTigerPlayground() {
        String tigerPlayground = BuildTypeToStringForOutgoingPlay.getString(BuildType.PLACE_TIGER);
        Assert.assertEquals("BUILD TIGER PLAYGROUND", tigerPlayground);
    }
}
