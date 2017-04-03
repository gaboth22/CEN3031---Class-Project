package GameMasterTest.ServerCommTest;

import GameMaster.ServerComm.Parsers.TileInformationParser;
import org.junit.Assert;
import org.junit.Test;

public class TileInformationParserTest {
    private String message = "MAKE YOUR MOVE IN GAME <gid> WITHIN <timemove> SECOND: MOVE <#> PLACE ROCKY+GRASS";

    @Test
    public void theParserShouldProvideTheCorrectTileInformation() {
        String parsedTileInfo = TileInformationParser.getTile(message);
        Assert.assertEquals("ROCKY+GRASS", parsedTileInfo);
    }
}
