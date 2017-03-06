import org.junit.Assert;
import org.junit.Test;

public class TerrainTest {
    Terrain uut;

    public void givenThenTerrainHasBeenInitializedWithTerrainType(TerrainType type) {
        uut = new Terrain(type);
    }

    public void theTerrainTypeShouldBe(TerrainType expectedType) {
        Assert.assertEquals(expectedType, uut.type);
    }

    @Test
    public void terrainDataShouldBeSetCorrectly() {
        givenThenTerrainHasBeenInitializedWithTerrainType(TerrainType.ROCKY);
        theTerrainTypeShouldBe(TerrainType.ROCKY);
    }
}
