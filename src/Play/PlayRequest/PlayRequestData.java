package Play.PlayRequest;

import Sender.SenderData.SenderData;

public class PlayRequestData implements SenderData<String> {
    public PlayRequestData() {
    }


    public String getData() {
        /*
            Returns null since it won't be actually used
            The sole purpose of whoever sends this data
            is to trigger a callback on the AI to generate
            a play
        */
        return null;
    }
}
