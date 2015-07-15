package com.unal.larim;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.unal.larim.LN.Notice;
import com.unal.larim.LN.QuickstartPreferences;
import com.unal.larim.LN.RegistrationIntentService;
import com.unal.larim.LN.Util;


public class MainActivity extends ActionBarActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
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

    public void sitio(View v) {
        final String[] items = {this.getString(R.string.web_site), this.getString(R.string.title_activity_news)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        final Activity act = this;
        builder.setTitle(this.getString(R.string.main_menu_5)).setItems(items,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            Util.irA("http://larim.unal.edu.co/", act);
                        }
                        if (item == 1) {
                            noticias(null);
                        }
                    }
                });

        builder.setNegativeButton(this.getText(R.string.cancel_dialog), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public void sitio2(View v) {
        Util.irA("http://www.cccartagena.com", this);
    }

    public void cronograma(View v) {
        Intent cron = new Intent(this, Cronograma.class);
        startActivity(cron);
    }

    public void detalles(View v) {
        Intent participantes = new Intent(this, userListActivity.class);
        startActivity(participantes);
        //Toast.makeText(getApplicationContext(),"Aun no implementado",Toast.LENGTH_SHORT).show();
    }


    public void sponsors(View view) {
        startActivity(new Intent(this, SponsorsActivity.class));
    }

    public void noticias(View view) {
        Intent news = new Intent(this, NewsActivity.class);
        news.putExtra("notice", new Notice("Desde MainAct", "Contenidos", false, "1"));
        startActivity(news);
    }
}
