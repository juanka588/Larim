package com.unal.larim;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.unal.larim.Data.Participant;
import com.unal.larim.DataSource.PaperSource;
import com.unal.larim.DataSource.ParticipantSource;
import com.unal.larim.LN.LinnaeusDatabase;
import com.unal.larim.LN.ParticipantRecyclerViewAdapter;
import com.unal.larim.LN.Util;

import java.util.ArrayList;


public class ParticipantActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant);
        RecyclerView recList = (RecyclerView) findViewById(R.id.cardListParticipant);
        LinnaeusDatabase lb = new LinnaeusDatabase(getApplicationContext());
        SQLiteDatabase db = lb.dataBase;
        recList.setHasFixedSize(true);
        recList.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        ParticipantRecyclerViewAdapter adapter = new ParticipantRecyclerViewAdapter(initializeData(db), this);
        recList.setAdapter(adapter);
    }

    private ArrayList<Participant> initializeData(SQLiteDatabase db) {
        Cursor cursor = db.query(ParticipantSource.table_name_participant + " a inner join " +
                        ParticipantSource.table_name_country + " b on a." + ParticipantSource.table_name_country
                        + "=b." + BaseColumns._ID,
                ParticipantSource.column_names,
                "a." + ParticipantSource.column_type + " IS NULL", null, null, null, null);
        String mat[][] = Util.imprimirLista(cursor);
        ArrayList<Participant> participants = new ArrayList<>();
        for (int i = 0; i < mat.length; i++) {
            participants.add(new Participant(mat[i][0], mat[i][1], mat[i][2], mat[i][3]));
        }
        cursor.close();
        db.close();
        return participants;
    }

    private ArrayList<Participant> initializeData(SQLiteDatabase db, String type) {
        Cursor cursor = db.query(ParticipantSource.table_name_participant + " a inner join " +
                        ParticipantSource.table_name_country + " b on a." + ParticipantSource.table_name_country
                        + "=b." + BaseColumns._ID,
                ParticipantSource.column_names,
                "a." + ParticipantSource.column_type + "=?", new String[]{type}, null, null, null);
        String mat[][] = Util.imprimirLista(cursor);
        ArrayList<Participant> participants = new ArrayList<>();
        for (int i = 0; i < mat.length; i++) {
            participants.add(new Participant(mat[i][0], mat[i][1], mat[i][2], mat[i][3]));
        }
        cursor.close();
        db.close();
        return participants;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_participant, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}
