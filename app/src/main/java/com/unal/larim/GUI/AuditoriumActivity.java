package com.unal.larim.GUI;

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
import android.widget.ImageView;

import com.unal.larim.Adapters.AuditoriumRecyclerViewAdapter;
import com.unal.larim.DataSource.VenueContent;
import com.unal.larim.LN.Util;
import com.unal.larim.Listeners.OnSwipeTouchListener;
import com.unal.larim.R;


public class AuditoriumActivity extends AppCompatActivity {

    private static final String TAG = AuditoriumActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private RecyclerView list;
    private static final String CARTAGENA_URL = "http://cccartagena.com/";
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AuditoriumRecyclerViewAdapter adapter;
    private ImageView toolbarImage;
    /***
     * state=0 means venue 1 swipe right allowed
     * state=1 means venue 2 swipe left allowed
     */
    private static int state = 0;

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
        adapter = new AuditoriumRecyclerViewAdapter(VenueContent.getVenue(getApplicationContext(), state), this);

        Util.log(TAG, adapter.getItemCount() + "");
        list.setAdapter(adapter);
        list.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeRight() {
                if (state == 0) {
                    Util.log(TAG, "right swipe");
                    state = 1;
                    adapter = new AuditoriumRecyclerViewAdapter(VenueContent.getVenue(getApplicationContext(),
                            state), AuditoriumActivity.this);
                    list.setAdapter(adapter);
                    toolbarImage.setImageResource(R.drawable.ucartagena2);
                }
            }

            public void onSwipeLeft() {
                if (state == 1) {
                    Util.log(TAG, "left swipe");
                    state = 0;
                    adapter = new AuditoriumRecyclerViewAdapter(VenueContent.getVenue(getApplicationContext(),
                            state), AuditoriumActivity.this);
                    list.setAdapter(adapter);
                    toolbarImage.setImageResource(R.drawable.ccci);
                }
            }

        });
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
        toolbarImage = (ImageView) findViewById(R.id.toolbar_background);
        if (state == 0) {
            toolbarImage.setImageResource(R.drawable.ccci);
        } else {
            toolbarImage.setImageResource(R.drawable.ucartagena2);
        }
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
                Util.irA(CARTAGENA_URL, this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}