package com.unal.larim;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.unal.larim.Data.Sponsor;
import com.unal.larim.LN.LinnaeusDatabase;
import com.unal.larim.LN.SponsorsRecyclerViewAdapter;
import com.unal.larim.LN.Util;

import java.util.ArrayList;

public class SponsorsActivity extends Activity {

    public static String table_name = "sponsor";
    public static String column_names[] = new String[]{"name", "icon", "website", "type"};

    private RecyclerView listSponsor;
    private RecyclerView listOrganizators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager2 =
                new GridLayoutManager(getApplicationContext(), 2);
       /*
       LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager llm2 = new LinearLayoutManager(this);
        llm2.setOrientation(LinearLayoutManager.VERTICAL);
        */
        listSponsor = (RecyclerView) findViewById(R.id.listSponsors);
        listOrganizators = (RecyclerView) findViewById(R.id.listOrganizators);
        listSponsor.setLayoutManager(gridLayoutManager);
        listOrganizators.setLayoutManager(gridLayoutManager2);
        LinnaeusDatabase lb = new LinnaeusDatabase(getApplicationContext());
        SQLiteDatabase db = openOrCreateDatabase(LinnaeusDatabase.DATABASE_NAME,
                MODE_PRIVATE, null);
        SponsorsRecyclerViewAdapter adapter = new SponsorsRecyclerViewAdapter(initializeData(db, "0"), this);
        listSponsor.setAdapter(adapter);
        SponsorsRecyclerViewAdapter adapter2 = new SponsorsRecyclerViewAdapter(initializeData(db, "1"), this);
        listOrganizators.setAdapter(adapter2);
    }

    private ArrayList<Sponsor> initializeData(SQLiteDatabase db, String filter) {
        Cursor c = db.query(table_name, column_names, column_names[3] + "=?", new String[]{filter}, null, null, null);
        String[][] mat = Util.imprimirLista(c);
        ArrayList<Sponsor> sponsors = new ArrayList<>();
        for (int i = 0; i < mat.length; i++) {
            int icon = this.getResources().getIdentifier("drawable/" + mat[i][2], null, this.getPackageName());
            sponsors.add(new Sponsor(mat[i][1], icon, mat[i][3]));
        }
        c.close();
        db.close();
        Util.log("Sponsors size", sponsors.size() + "");
        return sponsors;
    }

}
