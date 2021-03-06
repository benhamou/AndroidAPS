package info.nightscout.androidaps.plugins.NSClientInternal.acks;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.nightscout.androidaps.MainApp;
import io.socket.client.Ack;

/**
 * Created by mike on 21.02.2016.
 */
public class NSUpdateAck implements Ack {
    private static Logger log = LoggerFactory.getLogger(NSUpdateAck.class);
    public boolean result = false;
    public String _id = null;
    public String action;
    public void call(Object...args) {
        JSONObject response = (JSONObject)args[0];
        if (response.has("result"))
            try {
                if (response.getString("result").equals("success"))
                    result = true;
                else if (response.getString("result").equals("Missing _id")) {
                    result = true;
                    log.debug("Internal error: Missing _id returned on dbUpdate ack");
                }
                MainApp.bus().post(this);
            } catch (JSONException e) {
            }
    }

    public NSUpdateAck(String action, String _id) {
        super();
        this.action = action;
        this._id = _id;
    }
}
