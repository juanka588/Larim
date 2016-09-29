package com.unal.larim.Services;

import android.content.ContentResolver;
import android.content.ContentValues;
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
import java.util.ArrayList;
import java.util.List;

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
                        getItemsFromJSON(cad);
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
    private void getItemsFromJSON(String JSONString) throws JSONException {
        JSONObject object = new JSONObject(JSONString);
        JSONArray participants = object.getJSONArray("registered");
        int size = participants.length();
        Util.log(TAG, "size: " + size);
        List<ContentValues> cvs = new ArrayList<>();
        ContentValues cv;
        JSONObject participant;
        String name, email, institution, country, type;
        int id;
        for (int i = 0; i < size; i++) {
            participant = participants.getJSONObject(i);
            id = participant.getInt(ParticipantContent._ID);
            name = participant.getString(ParticipantContent.column_name);
            email = participant.getString(ParticipantContent.column_email);
            institution = participant.getString(ParticipantContent.column_institution);
            country = participant.getString(ParticipantContent.column_country_code);
            /**
             * TODO: change for ParticipantContent.column_type
             */
            type = participant.getString("conventions");

            cv = new ContentValues();
            cv.put(ParticipantContent._ID, id);
            cv.put(ParticipantContent.column_name, name);
            cv.put(ParticipantContent.column_email, email);
            cv.put(ParticipantContent.column_institution, institution);
            cv.put(ParticipantContent.column_country_code, country);
            cv.put(ParticipantContent.column_type, type);
            /**
             * temporal fix must ask giovanni
             */
            cv.put(ParticipantContent.column_resume, "");
            cv.put(ParticipantContent.column_image, id);
            cvs.add(cv);
            Util.log(TAG, name);
        }
        ContentResolver cr = mContext.getContentResolver();
        cr.bulkInsert(ParticipantContent.CONTENT_URI, cvs.toArray(new ContentValues[cvs.size()]));

    }

    private void showErrorDialog() {
        Toast.makeText(mContext, "No hubo respuesta exitosa", Toast.LENGTH_SHORT).show();
    }

}

