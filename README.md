# CEN3031---Class-Project
This is the final class project for CEN3031 at the University of Florida.

The idea is that this code implements a board game called TigerIsland&trade;. 

The code will have to interact with a server which will serve as the game arbiter, and there needs 
to be an ongoing interaction with the server thoughout the game.

Please refer to this repo's [Wiki](https://github.com/gaboth22/CEN3031---Class-Project/wiki) for coding standards.


# How to run the code?

You should be able to just clone the repo. Open the project with IntelliJ IDEA and click run. All the jars you need should be in the project.

# What will happen if you run it?

It will fail since it immediately tries to connect to the tournament server. So you need to either lunch the tournament server ([Get it here](https://github.com/MatthewBregg/TigerIsland))

If you don't wanna do that, you can use our TestServer. This server run locally, and once you run it, everything you paste into that terminal window will be sent to the client. To run it:

```
#inside of our repo folder

cd TestServer
make
make run
```

Then everything you past there will be sent to our program. Run the server prior to running the program. Also, you need to change our program to connect to the local server on ip 127.0.0.1, port 8000.

# How to update our porgram's info so that it connects to a different IP address, and so on?

Just open the file called tournament.txt under /src and then just edit the fields there. Add not spaced in between the colon and the actual data, and keep the tags that tell you the type of data you're inputing. The file looks like this:

```
IP:<any_ip_address>
PORT:<any_port>
TOURNAME_PASS:<tournament_password>
USERNAME:<team_username>
PASS:<team_password>
```
So you just need to edit the data after the colon. DO NOT ADD SPACES IN BETWEEN THE COLON AND THE DATA. 

# Running the program with GUI

You can run our program with a GUI, it's not the best, but it helps you visualize the game. To do this, in the main method. After gameOne, and gameTwo have been initialzed, add gameOne.runWithGui(), and gameTwo.runWithGui(). It should look something like this:

```java
 33       Game gameOne = new Game(PlayerID.PLAYER_ONE, new ProfitablePlayGeneration());
 34       Game gameTwo = new Game(PlayerID.PLAYER_TWO, new ProfitablePlayGeneration());
 35       //You will add lines 36 and 37
 36       gameOne.runWithGui();     
 37       gameTwo.rinWithGui();
```

# Some architectural insight

The GameBoard (GameBoardImpl) is the lowest level, and all the rules are checekd there with some helper classes to delegate some of the responsibility.

Then Game holds an instance of the GameBoard, and holds and instance of Steve, which is our AI (not really though). Steve uses a class called ProfitablePlayGeneration which generates play.

ProfitablePlayGeneration tries a sequence of plays, and returns the first play that is not null.

ProfitablePlayGeneration strategy:

For tiles:
  1) We first try to nuke the other team, if they have reached any settlement to a size five or greater.
     NukingTilePlacementPhaseMaker attempts to generate a tile placement that does this.
     
  2) If we can't readily nuke a tile, we setup a tile so that we can nuke on the next turn.
    SettingTileForNextTurnTilePlacementPhaseMaker attempts to do this.
    
  3) If none of the above succeeded, we then attemp to place a tile next to one of out settlements so that we can expand it.
    StrategicTilePlacement handles this.
    
  4) If none of the above suceeded, we then place a tile on a random unnocupied location around the whole board, with the          volcano pointing inwards.
  SimplePlayGenerator handles this.
  
  
For pieces:
  1) We attemp to place a totoro by checking if we can do so.
    TotoroLocationHelper handles this.
  
  2) We attempt to place a Tiger by checking if we can do so.
    TigerLocationHelper handles this.
    
  3) We do a founding that actually extends one of our settlements, by founding adjacent to one of our settlements.
    StrategicSettlementExpansion handles this.
    
  4) We do an actual expnasion on one of our settlements.
    ExpansionHelper handles this.
    
  5) If everything else failed, we put a villager so that it 'block' one of the other player's settlements.
     FoundSettlementHelper handles this.
     
  6) If everything else failed we put a villager in 'safe' place, which is just finding an unoccupied level one hex on the    board, and founding there.
  
  
# Low level architecure

All the interaction with the server is handled by GameMaster with the help of serveral parsers that can be found under the Parsers package under GameMaster. The GameMaster unser a GameClient, ServerClient in this case that we use to talk to the server.

Our plays are called TilePlacementPhase, and BuildPhase. These classes hold information pertaining an actual play (The data that does into the GameBoard class). The GameBoard provides two functions for doing so. There is doTilePlacementPhase(), and doBuildPhase(). There are functions that are equivalent to these that the GameBoard exposes, but for plays that come from the server, which we don't check rules on.

There is also the possibility to run visual tests, by going to TestMain, and changing the text inside of some the .log files with simulate a game. It asks for enter to continue to each play.

# NOTE
If you are on Windows, three tests will fail, which pertain to the Debug class. This is fine.
