package com.unal.larim.GUI;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.unal.larim.Data.Participant;
import com.unal.larim.DataSource.CountryContent;
import com.unal.larim.DataSource.ParticipantContent;
import com.unal.larim.LN.Util;
import com.unal.larim.R;

public class DetailParticipantActivity extends AppCompatActivity {

    public static final String PARTICIPANT_ARG = "participant";
    private static final String TAG = DetailParticipantActivity.class.getSimpleName();
    private TextView textName;
    private TextView textEmail;
    private TextView textType;
    private TextView textCountry;
    private TextView textInstitution;
    private TextView textHelpType;
    private TextView textPaper;
    private Participant participant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_participant);
        Bundle b = getIntent().getExtras();
        participant = (Participant) b.getSerializable(PARTICIPANT_ARG);

        Util.log(TAG, participant + "");
        textName = (TextView) findViewById(R.id.textDetailPartName);
        textEmail = (TextView) findViewById(R.id.textDetailPartEmail);
        textType = (TextView) findViewById(R.id.textDetailPartType);
        textCountry = (TextView) findViewById(R.id.textDetailPartCountry);
        textInstitution = (TextView) findViewById(R.id.textDetailPartIntitution);
        textHelpType = (TextView) findViewById(R.id.textDetailPartHelpType);
        textPaper = (TextView) findViewById(R.id.textDetailPartPaper);

        textName.setText(getString(R.string.name) + " " + participant.getName());
        textType.setText(getString(R.string.type) + " " + ParticipantContent.getTypeString(participant.getType()));
        textEmail.setText(getString(R.string.email) + " " + participant.getEmail());
        String countryID = participant.getCountry();
        String countryDesc = getCountryDescription();
        textCountry.setText(getString(R.string.country) + "\n" + countryID + "\n" + countryDesc);
        if (participant.getInstitution() == null) {
            textInstitution.setVisibility(View.GONE);
        } else {
            textInstitution.setText(getString(R.string.institution) + " " + participant.getInstitution());
        }
        if (participant.getHelpType() == null) {
            textHelpType.setVisibility(View.GONE);
        } else {
            textHelpType.setText(getString(R.string.help_type) + " ");
        }
        if (participant.getPaperID() == -1) {
            textPaper.setVisibility(View.GONE);
        } else {
            textPaper.setText(getString(R.string.paper) + " ");
        }
    }

    private String getCountryDescription() {
        ContentResolver c = getContentResolver();
        Cursor cursor = c.query(CountryContent.buildCountryUri(participant.getCountry()),
                null, null, null, null);
        String mat[][] = Util.imprimirLista(cursor);
        Util.log(TAG, Util.toString(mat));
        String cad = mat[0][2];
        return cad;
    }

    public void email(View v) {
        Util.enviar(v.getContext(), participant.getEmail(), "", "", "");
    }
}
