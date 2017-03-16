public class BuildPhaseImplementation implements BuildPhase {

    private GameBoard gameBoard;

    public boolean playerInBuildPhase(){
        //return Player.getStatus()
        return true;
    }

    public void foundSettlement(){
        //gameBoard.foundSettlement();
    }

    public void expandSettlement() {
        //gameBoard.appendMeepleToSettlement();
    }

    public void placeTotoro(){
        //gameBoard.appendTotoroToSettlement();
    }
}
