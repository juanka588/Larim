package com.unal.larim.GUI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.unal.larim.Adapters.SponsorsRecyclerViewAdapter;
import com.unal.larim.DataSource.SponsorContent;
import com.unal.larim.R;

public class EventsActivity extends AppCompatActivity {
    private static final String EVENTS_IDENTIFIER = "4";
    private Toolbar mToolbar;
    private RecyclerView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        manageToolbar();

        GridLayoutManager gridLayoutManager2 =
                new GridLayoutManager(getApplicationContext(), 2);
        listView = (RecyclerView) findViewById(R.id.listEvents);
        listView.setLayoutManager(gridLayoutManager2);
        SponsorsRecyclerViewAdapter adapter = new SponsorsRecyclerViewAdapter(
                SponsorContent.getSponsors(getApplicationContext(), EVENTS_IDENTIFIER), this);
        listView.setAdapter(adapter);
    }

    private void manageToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getString(R.string.title_activity_events));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
