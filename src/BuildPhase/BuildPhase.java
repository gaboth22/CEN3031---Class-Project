package BuildPhase;

public interface BuildPhase {
    boolean playerInBuildPhase();

    void foundSettlement();

    void expandSettlement();

    void placeTotoro();
}
