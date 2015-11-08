package com.unal.larim.GUI;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

    private Toolbar mToolbar;
    private RecyclerView list;
    private static final String url = "http://cccartagena.com/";
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditorium);
        list = (RecyclerView) findViewById(R.id.listAuditoriumPlaces);
        manageToolbar();
        /*
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        */
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(getApplicationContext(), 2);
        list.setLayoutManager(gridLayoutManager);
        AuditoriumRecyclerViewAdapter adapter = new AuditoriumRecyclerViewAdapter(populateData(),
                this);
        Util.log(TAG, adapter.getItemCount() + "");
        list.setAdapter(adapter);
    }

    private void manageToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout.setTitle(getString(R.string.title_activity_auditorium));
//        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white));
//        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white));
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
//                R.drawable.ccci);
//        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(Palette palette) {
//                int mutedColor = palette.getMutedColor(R.attr.colorPrimary);
//                collapsingToolbarLayout.setContentScrimColor(mutedColor);
//            }
//        });
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(this, R.color.darkblue));
//        collapsingToolbarLayout.setStatusBarScrimColor(ContextCompat.getColor(this, R.color.red));
    }

    private List<AuditoriumPlace> populateData() {
        ArrayList<AuditoriumPlace> list = new ArrayList<>();
        String iconsNames[] = new String[]{
                "audi_getsemani",
                "fondoinf",
                "fondoinf",
                "fondoinf",
                "registration_port",
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
                c.getString(R.string.saloon_201),
                c.getString(R.string.saloon_303),
                c.getString(R.string.saloon_304)
        };
        String images[] = new String[]{
                "getsemani.jpg",
                "nivel_1.jpg",
                "nivel_2.jpg",
                "nivel_3.jpg",
                "registration_port.jpg",
                "salle_201.jpg",
                "salle_303.png",
                "salle_304.jpg"
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
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.ItemWEB:
                Util.irA(url, this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}