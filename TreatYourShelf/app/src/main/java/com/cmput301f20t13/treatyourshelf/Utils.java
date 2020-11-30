package com.cmput301f20t13.treatyourshelf;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cmput301f20t13.treatyourshelf.data.Notification;
import com.cmput301f20t13.treatyourshelf.services.MySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    final private static String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private static String serverKey = "key=" + "AAAAIs92MZI:APA91bGaR3MuNNgg3Nnzz_5VjXo9o4TookkHTuNV1WMmDjxXMoEZnGalTgesvnrK2MUix3SfZ_Lh2rEMdby5GcgsntOtQfqbWKMXsNoIkuX7ekK5MrzLfCUm-jYt0K-_GEGmL6FNYrWe";
    final private static String contentType = "application/json";

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static CircularProgressDrawable circularProgressDrawableFactory(Context context) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.setColorSchemeColors(context.getResources().getColor(R.color.colorPrimary, null));
        circularProgressDrawable.start();
        return circularProgressDrawable;
    }

    public static String capitalizeString(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    public static void sendNotification(JSONObject notification, Context context) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("test", "onResponse: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Test", "onErrorResponse: Didn't work");
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static String emailStripper(String email) {
        return email.replaceAll("[^a-zA-Z0-9]", "");
    }
}
