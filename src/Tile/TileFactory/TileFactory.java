package Tile.TileFactory;

import Tile.Tile.Tile;
import Tile.Tile.TimeImpl;
import Tile.TileInformationGenerator.TileInformationGenerator;

public class TileFactory {
    public Tile makeTile(TileInformationGenerator tileInformationGenerator) {
        return new TimeImpl();
    }
}
