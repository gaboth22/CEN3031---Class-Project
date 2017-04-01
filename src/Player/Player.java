package Player;

import Settlements.Creation.Settlement;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private static final int MAX_VILLAGER_COUNT = 20;
    private static final int MAX_TOTORO_COUNT = 3;
    private static final int MAX_TIGER_COUNT = 2;
    private PlayerID id;
    private int villagerCount;
    private int totoroCount;
    private int tigerCount;
    private int score;
    private List<Settlement> settlements;

    public Player(PlayerID id) {
        this.id = id;
        this.villagerCount = MAX_VILLAGER_COUNT;
        this.totoroCount = MAX_TOTORO_COUNT;
        this.tigerCount = MAX_TIGER_COUNT;
        this.score = 0;
        this.settlements = new ArrayList<>();
    }

    public Player(Player playerToCopy){
        this.id = playerToCopy.getID();
        this.villagerCount = playerToCopy.getVillagerCount();
        this.tigerCount = playerToCopy.getTigerCount();
        this.villagerCount = playerToCopy.getVillagerCount();
        this.score = playerToCopy.getScore();
        this.settlements = playerToCopy.getListOfSettlements();
    }

    public PlayerID getID() {
        return this.id;
    }

    public List<Settlement> getListOfSettlements() {
        return new ArrayList<>(settlements);
    }

    public void setListOfSettlements(List<Settlement> listOfSettlements) {
        settlements = listOfSettlements;
    }

    public void decrementVillagerCount(int amountToDecrement) {
        this.villagerCount -= amountToDecrement;
    }

    public void decrementTotoroCount() {
            this.totoroCount--;
    }

    public void decrementTigerCount() {
            this.tigerCount--;
    }

    public int getVillagerCount(){
        return villagerCount;
    }

    public int getTotoroCount(){
        return totoroCount;
    }

    public int getTigerCount(){
        return tigerCount;
    }

    public int getScore(){
        return score;
    }

    public void addPoints(int points){
        score += points;
    }
}
