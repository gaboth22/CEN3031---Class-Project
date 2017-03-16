package Piece;

import Player.PlayerID;

public class Meeple implements Piece {
    private PlayerID ownerId;

    public Meeple(PlayerID ownerId) {
        this.ownerId = ownerId;
    }

    public PlayerID getOwnerId() {
        return this.ownerId;
    }
}
