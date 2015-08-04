package com.unal.larim;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.unal.larim.Data.Conference;
import com.unal.larim.Data.Paper;
import com.unal.larim.Data.Participant;
import com.unal.larim.DataSource.PaperSource;
import com.unal.larim.DataSource.ParticipantSource;
import com.unal.larim.LN.LinnaeusDatabase;
import com.unal.larim.LN.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DetailConference extends ActionBarActivity {

    public static final String TAG_CONFERENCE = "conference";
    private TextView textDate;
    private TextView textHour;
    private TextView textPaperName;
    private TextView textPlace;
    private TextView textAutor;
    private TextView textChairman;
    private WebView pdfConference;
    private Conference conference;
    private Paper paper;
    private Participant author;
    private SQLiteDatabase database;

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
        LinnaeusDatabase ln = new LinnaeusDatabase(getApplicationContext());
        database = ln.getReadableDatabase();
        Bundle b = this.getIntent().getExtras();
        conference = (Conference) b.getSerializable(TAG_CONFERENCE);
        paper = getPaperFromID(conference.paperID);
        pdfConference.loadUrl("http://docs.google.com/gview?embedded=true&url=" +
                paper.pdfURL);
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
        textPaperName.setText(getString(R.string.paper) + " " + paper.title);
        textPlace.setText(getString(R.string.place) + " " + conference.place);
        author = getAuthorFromID(paper.participantID);
        textAutor.setText(getString(R.string.author) + " " + author);
        textChairman.setText(getString(R.string.chairman) + " " + conference.chairman);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        textDate.setText(getString(R.string.date) + " " + df.format(conference.date.getTime()).toString());
        database.close();

    }

    private Paper getPaperFromID(String id) {
        Cursor cursor = database.query(PaperSource.table_name, PaperSource.column_names,
                BaseColumns._ID + "=?", new String[]{id}, null, null, null);
        String mat[][] = Util.imprimirLista(cursor);
        Paper paper = null;
        for (int i = 0; i < mat.length; i++) {
            paper = new Paper(mat[i][0], mat[i][1], mat[i][2], mat[i][3]);
        }
        cursor.close();
        return paper;
    }

    private Participant getAuthorFromID(String id) {
        Cursor cursor = database.query(ParticipantSource.table_name_participant + " a inner join " +
                        ParticipantSource.table_name_country + " b on a." + ParticipantSource.table_name_country
                        + "=b." + BaseColumns._ID,
                ParticipantSource.column_names,
                "a." + BaseColumns._ID + "=?", new String[]{id}, null, null, null);
        String mat[][] = Util.imprimirLista(cursor);
        Participant participant = null;
        for (int i = 0; i < mat.length; i++) {
            participant = new Participant(mat[i][0], mat[i][1], mat[i][2], mat[i][3]);
        }
        cursor.close();
        return participant;
    }

    public void schedule(View v) {
        int beginHour, beginMinute, endHour, endMinute;
        String begin = conference.hour.split("-")[0];
        String end = conference.hour.split("-")[1];
        beginHour = Integer.parseInt(begin.split(":")[0]);
        beginMinute = Integer.parseInt(begin.split(":")[1]);
        endHour = Integer.parseInt(begin.split(":")[0]);
        endMinute = Integer.parseInt(begin.split(":")[1]);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(conference.date);
        calendar.add(Calendar.MONTH, -1);
        Util.addEventToCalendar(this, df.format(calendar.getTime()).toString(), beginHour,
                beginMinute, endHour, endMinute, conference.title,
                conference.description, conference.place);
    }


}
