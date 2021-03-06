package com.unal.larim.GUI;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.unal.larim.Data.Conference;
import com.unal.larim.Data.Paper;
import com.unal.larim.Data.Participant;
import com.unal.larim.DataSource.PaperContent;
import com.unal.larim.DataSource.ParticipantContent;
import com.unal.larim.LN.Util;
import com.unal.larim.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DetailConference extends AppCompatActivity {

    private Toolbar mToolbar;

    private TextView textDate;
    private TextView textHour;
    private TextView textPaperName;
    private TextView textPlace;
    private TextView textAuthor;
    private TextView textChairman;
    private WebView pdfConference;
    private Conference conference;
    private Paper paper;
    private Participant author;
    private Participant chairman;
    private ContentResolver contentResolver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_conference);

        textDate = (TextView) findViewById(R.id.textDate);
        textHour = (TextView) findViewById(R.id.textHour);
        textPaperName = (TextView) findViewById(R.id.textPaperName);
        textPlace = (TextView) findViewById(R.id.textPlace);
        textAuthor = (TextView) findViewById(R.id.textAutor);
        textChairman = (TextView) findViewById(R.id.textChairman);
        pdfConference = (WebView) findViewById(R.id.pdfConference);

        manageToolbar();

        contentResolver = getContentResolver();

        Bundle b = this.getIntent().getExtras();
        conference = b.getParcelable(getString(R.string.TAG_CONFERENCE));
        populateData();
    }

    public void populateData() {
        paper = PaperContent.getPaper(conference.getPaperID(), getApplicationContext());
        pdfConference.loadUrl("http://docs.google.com/gview?embedded=true&url=" +
                paper.getPdfURL());
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
        textHour.setText(getString(R.string.hour) + " " + conference.getHour());
        textPaperName.setText(getString(R.string.paper) + " " + paper.getTitle());
        textPlace.setText(getString(R.string.place) + " " + conference.getPlace());
        author = ParticipantContent.getAuthorFromID(paper.getParticipantID(), getApplicationContext());
        textAuthor.setText(getString(R.string.author) + " " + author);
        chairman = ParticipantContent.getAuthorFromID(conference.getChairmanID(), getApplicationContext());
        textChairman.setText(getString(R.string.chairman) + " " + chairman);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        textDate.setText(getString(R.string.date) + " " + df.format(conference.getDate().getTime()).toString());
    }

    private void manageToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getString(R.string.title_activity_news));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void participant(View v) {
        Intent participant = new Intent(getApplicationContext(), DetailParticipantActivity.class);
        participant.putExtra(DetailParticipantActivity.PARTICIPANT_ARG,
                v.getId() == R.id.textAutor ? author : chairman);
        startActivity(participant);
    }

    public void schedule() {
        int beginHour, beginMinute, endHour, endMinute;
        String begin = conference.getHour().split("-")[0];
        String end = conference.getHour().split("-")[1];
        beginHour = Integer.parseInt(begin.split(":")[0]);
        beginMinute = Integer.parseInt(begin.split(":")[1]);
        endHour = Integer.parseInt(begin.split(":")[0]);
        endMinute = Integer.parseInt(begin.split(":")[1]);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(conference.getDate());
        calendar.add(Calendar.MONTH, -1);
        Util.addEventToCalendar(this, df.format(calendar.getTime()).toString(), beginHour,
                beginMinute, endHour, endMinute, conference.getTitle(),
                conference.getDescription(), conference.getPlace());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_conference_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ItemCalendar:
                schedule();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
