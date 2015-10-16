package com.unal.larim.GUI;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.unal.larim.Adapters.AuditoriumRecyclerViewAdapter;
import com.unal.larim.Data.AuditoriumPlace;
import com.unal.larim.LN.Util;
import com.unal.larim.R;

import java.util.ArrayList;
import java.util.List;


public class AuditoriumActivity extends AppCompatActivity {
    private static final String TAG = AuditoriumActivity.class.getSimpleName();

    private RecyclerView list;
    private static String url = "http://cccartagena.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditorium);
        list = (RecyclerView) findViewById(R.id.listAuditoriumPlaces);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        list.setLayoutManager(gridLayoutManager);
        AuditoriumRecyclerViewAdapter adapter = new AuditoriumRecyclerViewAdapter(populateData(),
                this);
        Util.log(TAG, adapter.getItemCount() + "");
        list.setAdapter(adapter);
    }

    private List<AuditoriumPlace> populateData() {
        ArrayList<AuditoriumPlace> list = new ArrayList<>();
        String iconsNames[] = new String[]{
                "getsemani",
                "fondoinf",
                "fondoinf",
                "fondoinf",
                "registration_port",
                "audi_getsemani",
                "salle_201",
                "salle_303",
                "salle_304"
        };
        Context c = getApplicationContext();
        String titles[] = new String[]{
                c.getString(R.string.auditorium),
                c.getString(R.string.stage_1),
                c.getString(R.string.stage_2),
                c.getString(R.string.stage_3),
                c.getString(R.string.registration_port),
                c.getString(R.string.auditorium),
                c.getString(R.string.salle_201),
                c.getString(R.string.salle_303),
                c.getString(R.string.salle_304)
        };
        String images[] = new String[]{
                "getsemani",
                "nivel_1",
                "nivel_3",
                "nivel_3",
                "registration_port",
                "audi_getsemani",
                "salle_201",
                "salle_303",
                "salle_304"
        };
        for (int i = 0; i < iconsNames.length; i++) {
            int icon = this.getResources().getIdentifier("drawable/" + iconsNames[i], null,
                    this.getPackageName());
            list.add(new AuditoriumPlace(icon, images[i], titles[i]));
        }
        return list;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_information, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ItemWEB:
                Util.irA(url, this);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}