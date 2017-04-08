package Settlements;

import Location.Location;
import Terrain.Terrain.Terrain;
import TileMap.Hexagon;

public class LargeHexagonBoard {

    public static Hexagon[] getBoard() {
        //Tile by Tile in each Column Starting from Column -6
        Hexagon[] generatedArray = new Hexagon[]{

                //Tile 1
                new Hexagon(1, new Location(-6,4), 1, Terrain.VOLCANO),
                new Hexagon(1, new Location(-6,3), 1, Terrain.GRASSLANDS),
                new Hexagon(1, new Location(-5,3), 1, Terrain.GRASSLANDS),

                //Tile 2
                new Hexagon(2, new Location(-6,2), 1, Terrain.VOLCANO),
                new Hexagon(2, new Location(-5,2), 1, Terrain.ROCKY),
                new Hexagon(2, new Location(-5,1), 1, Terrain.JUNGLE),

                //Tile 3
                new Hexagon(3, new Location(-5,0), 1, Terrain.JUNGLE),
                new Hexagon(3, new Location(-4,0), 1, Terrain.ROCKY),
                new Hexagon(3, new Location(-4,-1), 1, Terrain.ROCKY),

                //Tile 4
                new Hexagon(4, new Location(-4,3), 1, Terrain.GRASSLANDS),
                new Hexagon(4, new Location(-3,3), 1, Terrain.VOLCANO),
                new Hexagon(4, new Location(-3,2), 1, Terrain.GRASSLANDS),

                //Tile 5 : Other two tiles are covered
                new Hexagon(5, new Location(-3,4), 1, Terrain.LAKE),

                //Tile 6
                new Hexagon(6, new Location(-3,0), 1, Terrain.VOLCANO),
                new Hexagon(6, new Location(-3,-1), 1, Terrain.JUNGLE),

                //Tile 7
                new Hexagon(7, new Location(-2,4), 2, Terrain.VOLCANO),
                new Hexagon(7, new Location(-2,3), 2, Terrain.LAKE),
                new Hexagon(7, new Location(-1,3), 2, Terrain.ROCKY),

                //Tile 8
                new Hexagon(8, new Location(-2,2), 1, Terrain.GRASSLANDS),

                //Tile 9
                new Hexagon(9, new Location(-2,1), 2, Terrain.GRASSLANDS),
                new Hexagon(9, new Location(-2,0), 2, Terrain.GRASSLANDS),

                //Tile 10
                new Hexagon(10, new Location(-2,-1), 2, Terrain.ROCKY),
                new Hexagon(10, new Location(-1,-2), 2, Terrain.VOLCANO),

                //Tile 11
                new Hexagon(11, new Location(-1,1), 3, Terrain.ROCKY),
                new Hexagon(11, new Location(0,0), 3, Terrain.GRASSLANDS),

                //Tile 12
                new Hexagon(12, new Location(-1,0), 4, Terrain.VOLCANO),
                new Hexagon(12, new Location(-1,-1), 4, Terrain.GRASSLANDS),
                new Hexagon(12, new Location(0,-1), 4, Terrain.LAKE),

                //Tile 13
                new Hexagon(13, new Location(0,3), 1, Terrain.GRASSLANDS),
                new Hexagon(13, new Location(0,2), 1, Terrain.VOLCANO),

                //Tile 14
                new Hexagon(14, new Location(0,1), 2, Terrain.LAKE),

                //Tile 15
                new Hexagon(15, new Location(0,-2), 3, Terrain.ROCKY),

                //Tile 16
                new Hexagon(16, new Location(0,-3), 1, Terrain.LAKE),

                //Tile 17
                new Hexagon(17, new Location(1,0), 1, Terrain.ROCKY),

                //Tile 18
                new Hexagon(18, new Location(1,-1), 1, Terrain.JUNGLE),

                //Tile 19
                new Hexagon(19, new Location(1,-2), 2, Terrain.ROCKY)
        };
        return generatedArray;
    }
}
