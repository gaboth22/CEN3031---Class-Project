package GamePieceMap;

import Location.Location;
import Player.PlayerID;

import java.util.ArrayList;
import java.util.List;

public class Settlement {

    List<Location> locationList;
    int numberOfHexesInSettlement;
    boolean hasTotoroSanctuary;
    boolean hasTigerSanctuary;
    PlayerID playerID;

    public Settlement() {
        locationList = new ArrayList();
        numberOfHexesInSettlement = 0;
        hasTotoroSanctuary = false;
        hasTigerSanctuary = false;
    }

    public void addPieceToSettlement(Location location, GamePiece gamePiece) {
        if(gamePiece.getPieceType() == TypeOfPiece.VILLAGER) {

        }
    }

    public int getNumberOfHexesInSettlement() {
        return numberOfHexesInSettlement;
    }

    public boolean hasTotoroSanctuary() {
        return hasTotoroSanctuary;
    }

    public boolean hasTigerSanctuary() {
        return hasTigerSanctuary;
    }

}
