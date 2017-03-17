package Terrain;

import Piece.Piece;

public class Volcano extends Terrain {

    public Volcano() {
        super();
        super.isVolcano = true;
    }

    public Volcano(TerrainLocation location){
        super(location);
        super.isVolcano = true; //This is getting a little wacky!
    }

    @Override
    public void setPiece(Piece piece) throws InhabitableTerrainException {
        throw new InhabitableTerrainException();
    }
}
