package com.unal.larim;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.unal.larim.LN.Util;


public class DetailConference extends ActionBarActivity {

    private TextView textHour;
    private TextView textPaperName;
    private TextView textPlace;
    private TextView textAutor;
    private TextView textChairman;
    private WebView pdfConference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_conference);
        textHour = (TextView) findViewById(R.id.textHour);
        textPaperName = (TextView) findViewById(R.id.textPaperName);
        textPlace = (TextView) findViewById(R.id.textPlace);
        textAutor = (TextView) findViewById(R.id.textAutor);
        textChairman = (TextView) findViewById(R.id.textChairman);
        pdfConference = (WebView) findViewById(R.id.pdfConference);
        /*Bundle Handle here*/
        pdfConference.loadUrl("https://www.google.com.co/");
    }

    public void schedule(View v) {
        Util.addEventToCalendar(this, "2015-06-20", "Evento Nuevo", "Charla Astronomia", "Salon 202 Auditorio A");
    }


}
