package com.unal.larim.GUI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.unal.larim.Data.Sponsor;
import com.unal.larim.LN.Util;
import com.unal.larim.R;

public class InformationActivity extends AppCompatActivity {

    public static Sponsor information;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        information = (Sponsor) bundle.getSerializable(getString(R.string.ARG_TAG_INFORMATION));
        setContentView(R.layout.activity_information);
        manageToolbar();
    }

    private void manageToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(information.name + "");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_information, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.ItemWEB:
                Util.irA(information.url, this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
