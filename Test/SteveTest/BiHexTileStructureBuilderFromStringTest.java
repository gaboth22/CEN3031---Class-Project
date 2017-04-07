package SteveTest;

import Steve.BiHexTileStructure;
import Steve.BiHexTileStructureBuilderFromString;
import Terrain.Terrain.Terrain;
import org.junit.Assert;
import org.junit.Test;

import java.security.InvalidParameterException;

public class BiHexTileStructureBuilderFromStringTest {
    private String terrainInfoAsString;
    private BiHexTileStructure created;

    @Test
    public void biHexShouldConstructedAsExpected() {
        givenTheTerrainStringIs("LAKE+ROCK");
        whenICreateTheBiHexTileStructure();
        thenTheBiHexTileStructureShouldHaveTerrains(Terrain.LAKE, Terrain.ROCKY);
    }

    private void givenTheTerrainStringIs(String tString) {
        terrainInfoAsString = tString;
    }

    private void whenICreateTheBiHexTileStructure() {
        created = BiHexTileStructureBuilderFromString.getBiHexFromString(terrainInfoAsString);
    }

    private void thenTheBiHexTileStructureShouldHaveTerrains(Terrain expectedLeft, Terrain expectedRight) {
        Assert.assertEquals(expectedLeft, created.getLeftTerrain());
        Assert.assertEquals(expectedRight, created.getRightTerrain());
    }

    @Test
    public void biHexShouldBeConstructredAsExpectedWithADifferentString() {
        givenTheTerrainStringIs("JUNGLE+GRASS");
        whenICreateTheBiHexTileStructure();
        thenTheBiHexTileStructureShouldHaveTerrains(Terrain.JUNGLE, Terrain.GRASSLANDS);
    }

    @Test(expected = InvalidParameterException.class)
    public void builderShouldThrowUponUnexpectedString() {
        givenTheTerrainStringIs("JUNGLE+PEPE");
        whenICreateTheBiHexTileStructure();
    }

}
