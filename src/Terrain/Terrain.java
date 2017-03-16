package Terrain;

import Piece.Piece;

public abstract class Terrain {
    private TerrainLocation location;
    protected boolean hasPiece;
    private Piece piece;
    protected boolean isVolcano;

    public Terrain() {
        this.isVolcano = false;
        this.hasPiece = false;
    }

    public Terrain(TerrainLocation location) {
        this.isVolcano = false;
        this.location = location;
    }

    public TerrainLocation getLocation() {
        return this.location;
    }

    public boolean isOccupied() {
        return this.hasPiece;
    }

    public void removePiece() {
        this.hasPiece = false;
    }

    public void setPiece(Piece piece) throws InhabitableTerrainException {
        this.piece = piece;
        this.hasPiece = true;
    }

    public boolean isVolcano() {
        return this.isVolcano;
    }

    public Piece getPiece() throws EmptyTerrainException {
        if(!this.hasPiece)
            throw new EmptyTerrainException();

        return this.piece;
    }

    public void resetLocation(TerrainLocation newLocation) {
        this.location = newLocation;
    }
}
