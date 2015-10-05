package com.unal.larim.GUI;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.unal.larim.Adapters.SponsorsRecyclerViewAdapter;
import com.unal.larim.Data.Sponsor;
import com.unal.larim.DataSource.SponsorContent;
import com.unal.larim.LN.Util;
import com.unal.larim.R;

import java.util.ArrayList;

public class SponsorsActivity extends AppCompatActivity {

    private static final String SPONSOR_FILTER = "1";
    private static final String ORGANIZER_FILTER = "0";

    private RecyclerView listSponsor;
    private RecyclerView listOrganizators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors);
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(getApplicationContext(), 2);
//        StaggeredGridLayoutManager gridLayoutManager =
//                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
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
        SponsorsRecyclerViewAdapter adapter = new SponsorsRecyclerViewAdapter(
                initializeData(SPONSOR_FILTER), this);
        listSponsor.setAdapter(adapter);
        SponsorsRecyclerViewAdapter adapter2 = new SponsorsRecyclerViewAdapter(
                initializeData(ORGANIZER_FILTER), this);
        listOrganizators.setAdapter(adapter2);
    }

    private ArrayList<Sponsor> initializeData(String filter) {
        ContentResolver contentResolver = getContentResolver();
        Cursor c = contentResolver.query(
                SponsorContent.buildSponsorUri(filter),
                null, null, null, null);
        String[][] mat = Util.imprimirLista(c);
        ArrayList<Sponsor> sponsors = new ArrayList<>();
        for (int i = 0; i < mat.length; i++) {
            int icon = this.getResources().getIdentifier("drawable/" + mat[i][1], null,
                    this.getPackageName());
            sponsors.add(new Sponsor(mat[i][0], icon, mat[i][2], mat[i][3]));
        }
        c.close();
        Util.log("Sponsors size", sponsors.size() + "");
        return sponsors;
    }

}
