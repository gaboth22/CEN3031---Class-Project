package Tile.TileFactory;

import Tile.Tile.Tile;
import Tile.Tile.TileImpl;
import Tile.TileInformationGenerator.TileInformationGenerator;

public class TileFactory {
    public Tile makeTile(TileInformationGenerator tileInformationGenerator) {
        return new TileImpl();
    }
}
