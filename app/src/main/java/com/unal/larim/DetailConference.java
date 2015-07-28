package com.unal.larim;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.unal.larim.Data.Conference;
import com.unal.larim.LN.Util;

import java.net.SocketImpl;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DetailConference extends ActionBarActivity {

    private TextView textDate;
    private TextView textHour;
    private TextView textPaperName;
    private TextView textPlace;
    private TextView textAutor;
    private TextView textChairman;
    private WebView pdfConference;
    private Conference conference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_conference);
        textDate = (TextView) findViewById(R.id.textDate);
        textHour = (TextView) findViewById(R.id.textHour);
        textPaperName = (TextView) findViewById(R.id.textPaperName);
        textPlace = (TextView) findViewById(R.id.textPlace);
        textAutor = (TextView) findViewById(R.id.textAutor);
        textChairman = (TextView) findViewById(R.id.textChairman);
        pdfConference = (WebView) findViewById(R.id.pdfConference);
        /*TODO:Bundle Handle here*/
        Bundle b = this.getIntent().getExtras();
        conference = (Conference) b.getSerializable("conference");
        pdfConference.loadUrl("http://docs.google.com/gview?embedded=true&url=" +
                conference.author.paperUrls.get(0));
        pdfConference.getSettings().setJavaScriptEnabled(true);
        pdfConference.getSettings().setPluginState(WebSettings.PluginState.ON);
        pdfConference.getSettings().setBuiltInZoomControls(true);
        pdfConference.setWebViewClient(new WebViewClient() {
            // evita que los enlaces se abran fuera nuestra app en el navegador
            // de android
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

        });
        textHour.setText(getString(R.string.hour) + " " + conference.hour);
        textPaperName.setText(getString(R.string.paper) + " " + conference.author.papers.get(0));
        textPlace.setText(getString(R.string.place) + " " + conference.place);
        textAutor.setText(getString(R.string.author) + " " + conference.author.toString());
        textChairman.setText(getString(R.string.chairman) + " " + conference.chairman);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        textDate.setText(getString(R.string.date) + " " + df.format(conference.date.getTime()).toString());
    }

    public void schedule(View v) {
        int hour, minute;
        hour = Integer.parseInt(conference.hour.split(":")[0]);
        minute = Integer.parseInt(conference.hour.split(":")[1]);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(conference.date);
        calendar.add(Calendar.MONTH, -1);
        Util.addEventToCalendar(this, df.format(calendar.getTime()).toString(), hour, minute, conference.title,
                conference.session, conference.place);
    }


}
