import org.junit.Assert;
import org.junit.Test;

public class TerrainTest {
    Terrain uut;

    public void GiveThenTerrainHasBeenInitializedWithTerrainType(TerrainType type) {
        uut = new Terrain(type);
    }

    public void TheTerrainTypeShouldBe(TerrainType expectedType) {
        Assert.assertEquals(expectedType, uut.type);
    }

    @Test
    public void TerrainDataShouldBeSetCorrectly() {
        GiveThenTerrainHasBeenInitializedWithTerrainType(TerrainType.ROCKY);
        TheTerrainTypeShouldBe(TerrainType.ROCKY);
    }
}
