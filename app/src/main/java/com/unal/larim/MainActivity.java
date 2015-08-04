package com.unal.larim;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.unal.larim.Data.Notice;
import com.unal.larim.Data.Sponsor;
import com.unal.larim.DataSource.SponsorContent;
import com.unal.larim.LN.LinnaeusDatabase;
import com.unal.larim.LN.QuickstartPreferences;
import com.unal.larim.LN.RegistrationIntentService;
import com.unal.larim.LN.Util;


public class MainActivity extends Activity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String WELCOME_IDENTIFIER = "3";
    private static final String TAG = "MainActivity";
    public static final boolean DEBUG = true;
    public static int notification_counter;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    // Toast.makeText(getApplicationContext(), getString(R.string.gcm_send_message), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.token_error_message), Toast.LENGTH_SHORT).show();
                }
            }
        };
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    public void mapa(View v) {
        Intent map = new Intent(this, MapsActivity.class);
        startActivity(map);
    }

    public void site(View v) {
        Util.irA("http://www.cccartagena.com", this);
    }

    public void welcome(View v) {
        Intent info = new Intent(this, InformationActivity.class);
        info.putExtra(InformationActivity.ARG_TAG, getWelcomeInfo());
        startActivity(info);

    }

    private Sponsor getWelcomeInfo() {
        LinnaeusDatabase ln = new LinnaeusDatabase(getApplicationContext());
        SQLiteDatabase database = ln.dataBase;
        Cursor cursor = database.query(SponsorContent.table_name, SponsorContent.column_names,
                SponsorContent.column_type + "=?", new String[]{WELCOME_IDENTIFIER}, null, null, null);
        String[][] mat = Util.imprimirLista(cursor);
        Sponsor sponsor = null;
        for (int i = 0; i < mat.length; i++) {
            int icon = this.getResources().getIdentifier("drawable/" + mat[i][1], null, this.getPackageName());
            sponsor = new Sponsor(mat[i][0], icon, mat[i][2], mat[i][3]);
        }
        cursor.close();
        database.close();
        return sponsor;
    }

    public void program(View v) {
        Intent cron = new Intent(this, Cronograma.class);
        startActivity(cron);
    }

    public void details(View v) {
        Intent participantes = new Intent(this, ParticipantActivity.class);
        startActivity(participantes);
    }


    public void sponsors(View view) {
        startActivity(new Intent(this, SponsorsActivity.class));
    }

    public void noticias(View view) {
        Intent news = new Intent(this, NewsActivity.class);
        news.putExtra("notice", new Notice("1", "Desde Main Activity", "Contenidos", false, "http://www.google.com"));
        startActivity(news);
    }
}
