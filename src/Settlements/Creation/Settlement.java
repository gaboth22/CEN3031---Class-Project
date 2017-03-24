package Settlements.Creation;

import GamePieceMap.GamePiece;
import GamePieceMap.TypeOfPiece;
import Location.Location;
import Movement.AdjacentLocationArrayGetter.AdjacentLocationArrayGetter;
import Player.Player;
import Player.PlayerID;
import Settlements.SettlementException.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Settlement {

    Map<Location, GamePiece> settlementLocations;
    int numberOfHexesInSettlement;
    boolean totoroSanctuary;
    boolean tigerSanctuary;
    PlayerID playerID;

    public Settlement() {
        settlementLocations = new HashMap<Location, GamePiece>();
        numberOfHexesInSettlement = 0;
        totoroSanctuary = false;
        tigerSanctuary = false;
    }

    public void addPieceToSettlement(Location location, GamePiece gamePiece) throws SettlementException {
        if(numberOfHexesInSettlement == 0) {
            this.playerID = gamePiece.getPlayer();
        }
        else{
            checkPiecePlacement(location, gamePiece);
        }
        addPieceToHashSet(location, gamePiece);
    }

    private void addPieceToHashSet(Location location, GamePiece gamePiece) {
        settlementLocations.put(location, gamePiece);
        numberOfHexesInSettlement++;
        if(gamePiece.getPieceType() == TypeOfPiece.TOTORO) {
            totoroSanctuary = true;
        }
        if(gamePiece.getPieceType() == TypeOfPiece.TIGER) {
            tigerSanctuary = true;
        }
    }

    private void checkPiecePlacement(Location location, GamePiece gamePiece) throws SettlementException {
        if(!pieceOfCorrectPlayer(gamePiece)) {
            String errorString = "A GamePiece with player " + gamePiece.getPlayer() + " cannot be added to a settlement owned by " + this.playerID + ".";
            throw new IncorrectPlayerException(errorString);
        }

        if(locationIsInSettlement(location)) {
            String errorString = "The GamePiece is already in the settlement";
            throw new PieceAlreadyInSettlementException(errorString);
        }

        if(!locationIsAdjacentToSettlement(location)) {
            String errorString = "A GamePiece added to a settlement must be adjacent to that settlement";
            throw new NotAdjacentToSettlementException(errorString);
        }
    }

    private boolean pieceOfCorrectPlayer(GamePiece gamePiece) {
        if(gamePiece.getPlayer() == this.playerID) {
            return true;
        }
        return false;
    }

    private boolean locationIsAdjacentToSettlement(Location locationToAdd) {
        Location[] locations = AdjacentLocationArrayGetter.getArrayOfAdjacentLocationsTo(locationToAdd);
        for(int i = 0; i < locations.length; i++) {
            if(locationIsInSettlement(locations[i])) {
                return true;
            }
        }
        return false;
    }

    public boolean locationIsInSettlement(Location locationToAdd) {
        if(settlementLocations.containsKey(locationToAdd)) {
            return true;
        }
        return false;
    }

    public int getNumberOfHexesInSettlement() {
        return numberOfHexesInSettlement;
    }

    public boolean hasTotoroSanctuary() {
        return totoroSanctuary;
    }

    public boolean hasTigerSanctuary() {
        return tigerSanctuary;
    }

    public PlayerID getSettlementOwner() {
        return playerID;
    }

    public TypeOfPiece getTypeOfPieceAt(Location location) {
        return settlementLocations.get(location).getPieceType();
    }

}
