package GameMaster.Game;

import Receiver.Receiver;
import Sender.Sender;
import Steve.Steve;

public class StevePlayRequester extends Thread {
    private Sender requestSender;
    private boolean requestTilePlacement;
    private boolean requestBuild;
    private String tileInfo;

    public StevePlayRequester(Steve steve) {
        Receiver stevesGeneratePlayRequestReceiver = steve.getGeneratePlayRequestReceiver();
        requestSender = new Sender();
        requestSender.subscribe(stevesGeneratePlayRequestReceiver);
        requestTilePlacement = false;
        requestBuild = false;
        tileInfo = null;
    }

    @Override
    public void run() {
        while(true) {
            if(requestTilePlacement) {
                if (tileInfo != null) {
                    //TODO: Parse the string and build the SendData to send to Steve
                    requestTilePlacement = false;
                    tileInfo = null;
                }
            }
            else if(requestBuild) {
                //TODO: Parse the string and build the SendData to send to Steve
                requestBuild = false;
            }
        }
    }

    public void requestTilePlacement(String tileInfo) {
        requestTilePlacement = true;
        this.tileInfo = tileInfo;
    }

    public void requestBuild() {
        requestBuild = true;
    }
}
