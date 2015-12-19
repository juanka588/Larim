package com.unal.larim.Services;

import android.content.Context;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.unal.larim.DataSource.ParticipantContent;
import com.unal.larim.LN.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by JuanCamilo on 05/10/2015.
 */
public class JSONParser {

    private static final String TAG = JSONParser.class.getSimpleName();
    private static String JSONURL = "http://larim.unal.edu.co/index.php?id=1889&type=121" +
            "&tx_unallarim2016_larimupdate[action]=getUpdates&tx_unallarim2016_larimupdate[controller]" +
            "=News&tx_unallarim2016_larimupdate[dateTime]=";
    private static String consultedDate = "0";
    private final OkHttpClient client = new OkHttpClient();
    private Context mContext;

    public JSONParser(Context context) {
        this.mContext = context;
    }

    public void parseJSON() {
        String url = JSONURL + consultedDate;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    showErrorDialog();
                    throw new IOException("Error " + TAG + ":" + response);
                } else {
                    String cad = response.body().string();
                    try {
                        getTempObject(cad);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    /***
     * @param JSONString
     * @throws JSONException
     */
    private void getTempObject(String JSONString) throws JSONException {
        JSONObject object = new JSONObject(JSONString);
        JSONArray participants = object.getJSONArray("registered");
        int size = participants.length();
        Util.log(TAG, "size: " + size);
        for (int i = 0; i < size; i++) {
            JSONObject participant = participants.getJSONObject(i);
            int id = participant.getInt(ParticipantContent._ID);
            String name = participant.getString(ParticipantContent.column_name);
            String email = participant.getString(ParticipantContent.column_email);
            String institution = participant.getString(ParticipantContent.column_institution);
            String country = participant.getString(ParticipantContent.column_country_code);
            Util.log(TAG, name);
        }

    }

    private void showErrorDialog() {
        Toast.makeText(mContext, "No hubo respuesta exitosa", Toast.LENGTH_SHORT).show();
    }

}

