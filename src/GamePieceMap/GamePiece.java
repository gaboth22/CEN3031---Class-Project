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
}