package Player;

public class Player {
    private static final int MAX_VILLAGER_COUNT = 20;
    private static final int MAX_TOTORO_COUNT = 3;
    private static final int MAX_TIGER_COUNT = 2;
    private PlayerID id;
    private int villagerCount;
    private int totoroCount;
    private int tigerCount;

    public Player(PlayerID id) {
        this.id = id;
        this.villagerCount = new Integer(MAX_VILLAGER_COUNT);
        this.totoroCount = new Integer(MAX_TOTORO_COUNT);
        this.tigerCount = new Integer(MAX_TIGER_COUNT);
    }

    public PlayerID getID() {
        return this.id;
    }

    public void decrementVillagerCount() {
        this.villagerCount--;
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
}
