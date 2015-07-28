package com.unal.larim;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.unal.larim.LN.LinnaeusDatabase;
import com.unal.larim.Data.Participant;
import com.unal.larim.LN.ParticipantRecyclerViewAdapter;
import com.unal.larim.LN.Util;

import java.util.ArrayList;


public class ParticipantActivity extends ActionBarActivity {

    public static String table_names[] = new String[]{"registered", "paper", "country"};
    public static String column_names[] = new String[]{"last_name", "first_name",
            "email", "institution", "title", "keywords", "pdf", "code", "type"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant);
        RecyclerView recList = (RecyclerView) findViewById(R.id.cardListParticipant);
        LinnaeusDatabase lb = new LinnaeusDatabase(getApplicationContext());
        SQLiteDatabase db = openOrCreateDatabase(LinnaeusDatabase.DATABASE_NAME,
                MODE_PRIVATE, null);
        recList.setHasFixedSize(true);
        recList.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        ParticipantRecyclerViewAdapter adapter = new ParticipantRecyclerViewAdapter(initializeData(db), this);
        recList.setAdapter(adapter);
    }

    private ArrayList<Participant> initializeData(SQLiteDatabase db) {
        String query = "SELECT " + Util.toString(column_names) + " FROM " + table_names[0] + " a inner join " +
                table_names[1] + " b on a.paper=b._id " +
                "inner join " + table_names[2] + " c on a.country=c._id";
        Cursor c = db.rawQuery(query + " where type =?", new String[]{"0"});
        //TODO: use inner join to populate views
        //db.query(table_names[0], column_names, column_names[3] + "=?", new String[]{"0"}, null, null, null);
        String[][] mat = Util.imprimirLista(c);
        ArrayList<Participant> participants = new ArrayList<>();
        for (int i = 0; i < mat.length; i++) {
            participants.add(new Participant(mat[i][0], mat[i][1], mat[i][2], mat[i][3], mat[i][4],
                    mat[i][5], mat[i][6], mat[i][7], mat[i][8]));
        }
        c.close();
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
