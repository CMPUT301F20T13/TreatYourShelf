package com.cmput301f20t13.treatyourshelf.data;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Notification {

    final String TAG = "NOTIFICATION TAG";

    String TOPIC;

    private JSONObject outgoingNotification;

    public Notification(String title, String message, String user) {
        TOPIC = "/topics/" + user; //topic must match with what the receiver subscribed to

        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();
        try {
            notifcationBody.put("title", title);
            notifcationBody.put("message", message);

            notification.put("to", TOPIC);
            notification.put("data", notifcationBody);
        } catch (JSONException e) {
            Log.e(TAG, "onCreate: " + e.getMessage());
        }
        outgoingNotification = notification;
    }

    public JSONObject getNotification() {
        return outgoingNotification;
    }

    public void setNotification(JSONObject notification) {
        this.outgoingNotification = notification;
    }
}
