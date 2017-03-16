package Terrain;

import Piece.Piece;

public class Volcano extends Terrain {

    public Volcano() {
        super();
        super.isVolcano = true;
    }

    @Override
    public void setPiece(Piece piece) throws InhabitableTerrainException {
        throw new InhabitableTerrainException();
    }
}
