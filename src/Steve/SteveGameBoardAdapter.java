package Steve;

import GameBoard.Proxy.*;
import Receiver.Receiver;
import Sender.Sender;

public class SteveGameBoardAdapter {
    public void Adapt(Steve steve, Proxy gameBoardProxy) {

        Sender steveDoBuildPhaseRequestSender = steve.getDoBuildPhaseRequestSender();
        Sender steveDoTilePlacementPhaseRequestSender = steve.getDoTilePlacementPhaseRequestSender();
        Sender steveGetGameBoardStateRequestSender = steve.getGetGameBoardStateRequestSender();

        Receiver gameBoardProxyDoBuildPhaseRequestReceiver= gameBoardProxy.getDoBuildPhaseRequestReceiver();
        Receiver gameBoardProxyDoTilePlacePhaseRequestReceiver = gameBoardProxy.getDoTilePlacePhaseRequestReceiver();
        Receiver gameBoardProxyGameBoardStateRequestReceiver = gameBoardProxy.getGameBoardStateRequestReceiver();

        steveDoBuildPhaseRequestSender.subscribe(gameBoardProxyDoBuildPhaseRequestReceiver);
        steveDoTilePlacementPhaseRequestSender.subscribe(gameBoardProxyDoTilePlacePhaseRequestReceiver);
        steveGetGameBoardStateRequestSender.subscribe(gameBoardProxyGameBoardStateRequestReceiver);

        Receiver steveGameBoardAckReceiver = steve.getGameBoardAckReceiver();
        Receiver steveGameBoardStateReceiver = steve.getGameBoardStateReceiver();

        Sender gameBoardProxyGameBoardAckSender= gameBoardProxy.getGameBoardAckSender();
        Sender gameBoardProxyGameBoardStateSender = gameBoardProxy.getGameBoardStateSender();

        gameBoardProxyGameBoardStateSender.subscribe(steveGameBoardStateReceiver);
        gameBoardProxyGameBoardAckSender.subscribe(steveGameBoardAckReceiver);
    }
}