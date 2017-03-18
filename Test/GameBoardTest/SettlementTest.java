package GameBoardTest;

import GameBoard.Settlement;
import Piece.Meeple;
import Piece.Totoro;
import Player.PlayerID;
import Terrain.*;
import TerrainTest.TerrainLocationTestDouble;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SettlementTest {

    private Settlement settlement;

    public int getRandomIntInRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public Terrain givenIHaveATerrain() {

        int terrainArbiter = getRandomIntInRange(0,3);
        switch (terrainArbiter) {
            case 0:
                return new GrasslandsTerrain(new TerrainLocationTestDouble(3, 2, 1));
            case 1:
                return new RockyTerrain(new TerrainLocationTestDouble(3, 2, 1));
            case 2:
                return new JungleTerrain(new TerrainLocationTestDouble(3, 2, 1));
            case 3:
                return new LakeTerrain(new TerrainLocationTestDouble(3, 2, 1));
            default:
                return new Volcano();
        }
    }

    public void givenTheSettlementHasBeenInitialized() {
        this.settlement = new Settlement();
    }

    public void theSettlementShouldNotHaveATotoro() {
        Assert.assertFalse(this.settlement.hasTotoro());
    }

    public void whenIAppendATerrainToTheSettlement(Terrain terrainToAppend) {
        this.settlement.append(terrainToAppend);
    }

    public void theSettlementSizeShouldBe(int expectedSize) {
        Assert.assertEquals(expectedSize, this.settlement.size());
    }

    public void whenIAddATotoroToTheTerrain(Terrain terrain) {
        try {
            terrain.setPiece(new Totoro(PlayerID.PLAYER_ONE));
        }

        catch(InhabitableTerrainException e) {
        }
    }

    public void whenIAddAMeepleToTheTerrain(Terrain terrain) {
        try {
            terrain.setPiece(new Meeple(PlayerID.PLAYER_ONE));
        }

        catch(InhabitableTerrainException e) {
        }
    }

    public void whenIClearTheSettlementFromATotoro() {
        this.settlement.clearTotoroAdded();
    }

    public void theSettlementShouldHaveATotoro() {
        Assert.assertTrue(this.settlement.hasTotoro());
    }

    public List<Terrain> givenIAddThreeTerrainsToTheSettlement() {
        Terrain terrain1 = givenIHaveATerrain();
        Terrain terrain2 = givenIHaveATerrain();
        Terrain terrain3 = givenIHaveATerrain();
        whenIAppendATerrainToTheSettlement(terrain1);
        whenIAppendATerrainToTheSettlement(terrain2);
        whenIAppendATerrainToTheSettlement(terrain3);
        return new LinkedList<Terrain>(Arrays.asList(terrain1, terrain2, terrain3));
    }

    public void allTheTerrainsFromTheExternalListShouldBeInTheSettlement(List<Terrain> externalList) {
        int index = 0;
        for(Terrain terrain : this.settlement) {
            Assert.assertEquals(externalList.get(index), terrain);
            index++;
        }
    }

    @Test
    public void assertTheSettlementHasNoTotoroUponInitialization() {
        givenTheSettlementHasBeenInitialized();
        theSettlementShouldNotHaveATotoro();
    }

    @Test
    public void theSettlementSizeShouldIncreaseUponAppendingToIt() {
        Terrain terrain = givenIHaveATerrain();
        givenTheSettlementHasBeenInitialized();
        whenIAppendATerrainToTheSettlement(terrain);
        theSettlementSizeShouldBe(1);
    }

    @Test
    public void thereShouldBeATotoroInTheSettlement() {
        Terrain terrain = givenIHaveATerrain();
        whenIAddATotoroToTheTerrain(terrain);
        givenTheSettlementHasBeenInitialized();
        whenIAppendATerrainToTheSettlement(terrain);
        theSettlementShouldHaveATotoro();
    }

    @Test
    public void thereShouldBeNoTotoroInTheSettlement() {
        Terrain terrain = givenIHaveATerrain();
        whenIAddAMeepleToTheTerrain(terrain);
        givenTheSettlementHasBeenInitialized();
        whenIAppendATerrainToTheSettlement(terrain);
        theSettlementShouldNotHaveATotoro();
    }

    @Test
    public void thereShouldNoLongerBeATotoroInTheSettlement() {
        Terrain terrain = givenIHaveATerrain();
        whenIAddATotoroToTheTerrain(terrain);
        givenTheSettlementHasBeenInitialized();
        whenIAppendATerrainToTheSettlement(terrain);
        whenIClearTheSettlementFromATotoro();
        theSettlementShouldNotHaveATotoro();
    }

    @Test
    public void shouldBeAbleToGetAllTerrainInSettlement() {
        givenTheSettlementHasBeenInitialized();
        List<Terrain> externalTerrainList = givenIAddThreeTerrainsToTheSettlement();
        allTheTerrainsFromTheExternalListShouldBeInTheSettlement(externalTerrainList);
    }

}
