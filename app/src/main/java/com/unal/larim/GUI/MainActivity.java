package com.unal.larim.GUI;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.unal.larim.DataSource.NoticeContent;
import com.unal.larim.DataSource.SponsorContent;
import com.unal.larim.LN.QuickstartPreferences;
import com.unal.larim.R;
import com.unal.larim.Services.RegistrationIntentService;


public class MainActivity extends Activity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String WELCOME_IDENTIFIER = "3";
    private final String TAG = MainActivity.this.getClass().getSimpleName();
    public static final boolean DEBUG = true;
    public static int NOTIFICATION_COUNTER;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = (FloatingActionButton) findViewById(R.id.fab);
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
        int newItems = NoticeContent.getUnreadNews(getApplicationContext());
        if (newItems == 0) {
            fab.setBackgroundTintList(getResources().getColorStateList(R.color.darkblue));
        } else {
            fab.setBackgroundTintList(getResources().getColorStateList(R.color.darkyellow));
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

    public void map(View v) {
        Intent map = new Intent(this, MapsActivity.class);
        startActivity(map);
    }

    public void site(View v) {
        Intent audi = new Intent(this, AuditoriumActivity.class);
        startActivity(audi);
    }

    public void events(View v) {
        Intent events = new Intent(this, EventsActivity.class);
        startActivity(events);
    }

    public void welcome(View v) {
        Intent info = new Intent(this, InformationActivity.class);
        info.putExtra(getString(R.string.ARG_TAG_INFORMATION),
                SponsorContent.getSponsors(getApplicationContext(), WELCOME_IDENTIFIER).get(0));
        startActivity(info);

    }

    public void program(View v) {
        Intent cron = new Intent(this, ScheduleActivity.class);
        startActivity(cron);
    }

    public void details(View v) {
        Intent participants = new Intent(this, ParticipantActivity.class);
        startActivity(participants);
    }


    public void sponsors(View view) {
        startActivity(new Intent(this, SponsorsActivity.class));
    }

    public void news(View view) {
        Intent news = new Intent(this, NewsActivity.class);
//        int r = (int) (Math.random() * 100);
//        news.putExtra("notice", new Notice("1", "Desde Main Activity " + r, "Contenidos " + r,
//                true, "http://www.google.com"));
        startActivity(news);
    }
}
