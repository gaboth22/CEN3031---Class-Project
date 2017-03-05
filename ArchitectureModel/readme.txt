After some thought, I noticed that the only tings common to all tiles, is that they will all have a volcano.
So, we can use the volcano as our pivot point to orient the tile on the board.

Basically the position of the volcano determines the orientation of the  tile. 
From what I saw, there will be 8 possible orientations, which I decided to name based on cardinal points:

N NE E SE S SW W NW - North, Northeast, East, Southeast, South, Southwest, West, and Northwest:

             N
	  NW ^ NE
	W < -|- > E
	  SW v SE
	     S

I believe those are all the positions that the volcano can point to, based on the hex configuration.
The convention would be that we assume that wherever the volcano is poiting, that is the "up" position,
regardless of the actual orientation of the piece. Then the lower left terrain will be leftTerrain, and the
lower right terrain will be rightTerrain:

			          Volcano
					  

		leftTerrain			rightTerrain
