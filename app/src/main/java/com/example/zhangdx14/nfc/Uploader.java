package com.example.zhangdx14.nfc;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ZHANGDX14 on 6/22/2017.
 */

public class Uploader {
    private static final String TAG = "Uploader";
    private static final String URL = "http://smart-pen.duckdns.org:5000/measurements";

    public void upload(Hpen hpen){
        JSONObject hpenJson = new JSONObject();
        JSONArray data = new JSONArray();
        try {
            JSONObject item = new JSONObject();
            item.put("id" , hpen.getId());
            item.put("time_utc", "1494580966.585706");
            item.put("event_type", "'MEASUREMENT'");
            item.put("lat", "34.0");
            item.put("lng", "-115.0");
            item.put("temp", "105.0");
            item.put("light", "76");
            data.put(item);
            JSONObject item2 = new JSONObject();
            item2.put("id" , hpen.getId());
            item2.put("time_utc", "1494580966.585706");
            item2.put("event_type", "INJECTION");
            item2.put("lat", "34.0");
            item2.put("lng", "-115.0");
            data.put(item2);
            hpenJson.put("data", data);
        } catch (JSONException e) {
            Log.d(TAG, "error convert to JSON");
        }

        HttpURLConnection connection = null;

        try {
            URL url = new URL(URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            Writer writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
            writer.write(String.valueOf(hpenJson));
            writer.close();
            int code = connection.getResponseCode();
            if (code != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        URL);
            }
        } catch (IOException e) {
            Log.d(TAG, "error send json data");
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
