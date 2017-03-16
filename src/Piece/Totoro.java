package Piece;

import Player.PlayerID;

public class Totoro implements Piece {
    private PlayerID ownerId;

    public Totoro(PlayerID ownerId) {
        this.ownerId = ownerId;
    }

    public PlayerID getOwnerId() {
        return this.ownerId;
    }
}
