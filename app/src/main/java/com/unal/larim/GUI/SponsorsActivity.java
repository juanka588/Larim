package com.unal.larim.GUI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.unal.larim.Adapters.SponsorsRecyclerViewAdapter;
import com.unal.larim.Data.Sponsor;
import com.unal.larim.DataSource.SponsorContent;
import com.unal.larim.R;

import java.util.List;

public class SponsorsActivity extends AppCompatActivity {

    private static final String SPONSOR_FILTER = "1";
    private static final String ORGANIZER_FILTER = "0";

    private Toolbar mToolbar;
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
        manageToolbar();
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

    private List<Sponsor> initializeData(String sponsorFilter) {
        return SponsorContent.getSponsors(getApplicationContext(),sponsorFilter);
    }

    private void manageToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
