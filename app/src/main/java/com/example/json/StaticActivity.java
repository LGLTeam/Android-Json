package com.example.json;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;

public class StaticActivity {
    private static RequestNetwork requestNetwork;
    private static RequestNetwork.RequestListener _ha_request_listener;
    private static HashMap<String, Object> hashmap = new HashMap<>();
    private static Intent intent = new Intent();

    public static void init(final Context context, Activity activity){
        initialize(context, activity);
        initializeLogic();
    }

    public static void initialize(final Context context, Activity activity) {
        requestNetwork = new RequestNetwork(activity);
        _ha_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _response = _param2;
                hashmap = new Gson().fromJson(_response, new TypeToken<HashMap<String, Object>>(){}.getType());
                if ("3".equals(hashmap.get("versi").toString())) {
                    Toast toast = Toast.makeText(context.getApplicationContext(),
                            "Loading mod menu",
                            Toast.LENGTH_SHORT);
                    //Call to your mod menu here
                    toast.show();
                }
                else {
                    //Version expired, open link
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/"));
                    context.startActivity(intent);
                }
            }

            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
            }
        };
    }
    private static void initializeLogic() {
        //Json format {"versi":"3"}
        requestNetwork.startRequestNetwork(RequestNetworkController.GET, "https://pastebin.com/raw/nRW9Lbyg", "", _ha_request_listener);
    }
}