package GamePieceMap;

import Player.PlayerID;

public class GamePiece {
    private PlayerID player;
    private TypeOfPiece pieceType;

    public GamePiece(PlayerID player, TypeOfPiece pieceType){
        this.player = player;
        this.pieceType = pieceType;
    }

    public PlayerID getPlayer(){
        return player;
    }

    public TypeOfPiece getPieceType(){
        return pieceType;
    }

    @Override
    public String toString() {
        return "" + player + "'s " + pieceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GamePiece gamePiece = (GamePiece) o;

        if (player != gamePiece.player) return false;
        return pieceType == gamePiece.pieceType;
    }

    @Override
    public int hashCode() {
        int result = player != null ? player.hashCode() : 0;
        result = 31 * result + (pieceType != null ? pieceType.hashCode() : 0);
        return result;
    }
}