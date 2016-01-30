package com.unal.larim.GUI;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.unal.larim.Data.Paper;
import com.unal.larim.Data.Participant;
import com.unal.larim.DataSource.CountryContent;
import com.unal.larim.DataSource.ParticipantContent;
import com.unal.larim.LN.Util;
import com.unal.larim.R;

public class DetailParticipantActivity extends AppCompatActivity {

    public static final String PARTICIPANT_ARG = "participant";
    private static final String TAG = DetailParticipantActivity.class.getSimpleName();
    private ImageView participantPhoto;
    private TextView textName;
    private TextView textEmail;
    private TextView textType;
    private TextView textCountry;
    private TextView textInstitution;
    private TextView textPaper;
    private WebView navigator;
    private Participant participant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_participant);
        Bundle b = getIntent().getExtras();
        participant = (Participant) b.getSerializable(PARTICIPANT_ARG);

        Util.log(TAG, participant + "");
        participantPhoto = (ImageView) findViewById(R.id.photoPart);
        textName = (TextView) findViewById(R.id.textDetailPartName);
        textEmail = (TextView) findViewById(R.id.textDetailPartEmail);
        textType = (TextView) findViewById(R.id.textDetailPartType);
        textCountry = (TextView) findViewById(R.id.textDetailPartCountry);
        textInstitution = (TextView) findViewById(R.id.textDetailPartIntitution);
        textPaper = (TextView) findViewById(R.id.textDetailPartPaper);
        navigator = (WebView) findViewById(R.id.resumeView);

        /*TODO:replace with an service of image*/
        Resources res = getResources();
        int img = participant.getImage();
        Util.log(TAG, "selected image: " + img);
        Bitmap src = BitmapFactory.decodeResource(res, img);
        if (src == null) {
            participantPhoto.setImageResource(R.drawable.no_image);
        }else {
            RoundedBitmapDrawable dr =
                    RoundedBitmapDrawableFactory.create(res, src);
            dr.setCornerRadius(Math.max(src.getWidth(), src.getHeight()) / 2.0f);
            participantPhoto.setImageDrawable(dr);
        }
        textName.setText(getString(R.string.name) + " " + participant.getName());
        textType.setText(getString(R.string.type) + " " + ParticipantContent.getTypeString(participant.getType()));
        textEmail.setText(getString(R.string.email) + " " + participant.getEmail());
        String countryID = participant.getCountry();
        String countryDesc = getCountryDescription();
        textCountry.setText(getString(R.string.country) + "\n" + countryID + "\n" + countryDesc);
        if (participant.getInstitution() == null||participant.getInstitution().equals("-")) {
            textInstitution.setVisibility(View.GONE);
        } else {
            textInstitution.setText(getString(R.string.institution) + " " + participant.getInstitution());
        }
        Paper paper= ParticipantContent.getPaper(participant.getID(), getApplicationContext());
        if (paper==null) {
            textPaper.setVisibility(View.GONE);
        } else {
            textPaper.setText(getString(R.string.paper) + paper.toString());
        }
        navigator.getSettings().setJavaScriptEnabled(true);
        navigator.getSettings().setPluginState(WebSettings.PluginState.ON);
        navigator.setWebViewClient(new WebViewClient() {
            // evita que los enlaces se abran fuera nuestra app en el navegador
            // de android
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

        });
        final String resume = participant.getResume();
        if (!resume.equals("null")) {
            navigator.loadData(resume, "text/html", "UTF8");
            participantPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Util.irA(resume,DetailParticipantActivity.this);
                }
            });
        } else {
            navigator.setVisibility(View.GONE);
        }
    }

    private String getCountryDescription() {
        ContentResolver c = getContentResolver();
        Cursor cursor = c.query(CountryContent.buildCountryUri(participant.getCountry()),
                null, null, null, null);
        String mat[][] = Util.imprimirLista(cursor);
        Util.log(TAG, Util.toString(mat));
        String cad = mat[0][3];
        return cad;
    }

    public void email(View v) {
        Util.enviar(v.getContext(), participant.getEmail(), "", "", "");
    }
}
