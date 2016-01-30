package com.unal.larim.GUI;

import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.unal.larim.Adapters.ParticipantRecyclerViewAdapter;
import com.unal.larim.DataSource.ParticipantContent;
import com.unal.larim.Interfaces.OnHeadlineSelectedListener;
import com.unal.larim.LN.Util;
import com.unal.larim.R;

public class ParticipantActivity extends AppCompatActivity implements OnHeadlineSelectedListener {

    private static String TAG = ParticipantActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private SearchView sv;
    private RecyclerView.Adapter mCursorAdapter;
    private ContentResolver contentResolver;

    private int layout = R.layout.search_view_participant;
    private String[] from = new String[]{ParticipantContent.column_name,
            ParticipantContent.column_institution, ParticipantContent.column_country_code};
    private int[] to = new int[]{R.id.textParticipantName, R.id.textParticipantInstitution,
            R.id.textNationality};
    private ParticipantFragment participantFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant);
        participantFragment = (ParticipantFragment)
                getFragmentManager().findFragmentById(R.id.participant_fragment);
        manageToolbar();
    }

    private void manageToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getString(R.string.title_activity_participant));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_participant, menu);
        sv = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_busqueda));
        contentResolver = getContentResolver();
//        Cursor cursor = contentResolver.query(ParticipantContent.buildParticipantUri("_"),
//                null, null, null, null);
        mCursorAdapter = participantFragment.recList.getAdapter();
//                = new CustomCursorSearchAdapter(
//                getApplicationContext(), layout, cursor,
//                from, to, 0);
        //sv.setSuggestionsAdapter(mCursorAdapter);
        sv.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
//                Cursor cursor = (Cursor) sv.getSuggestionsAdapter().getItem(position);
//                String feedName = cursor.getString(1);
//                sv.setQuery(feedName, false);
//                sv.clearFocus();
                return true;
            }

            @Override
            public boolean onSuggestionClick(int position) {
//                Cursor c = mCursorAdapter.getCursor();
//                if (c.moveToPosition(position)) {
//                    String cad = c.getString(1);
//                    Util.log(TAG, cad);
//                    Util.enviar(ParticipantActivity.this, cad, "", "", "");
//                    sv.clearFocus();
//                }
                return true;
            }
        });
        sv.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String arg0) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String arg0) {
                populateAdapter(arg0);
                return true;
            }
        });

        return true;
    }

    private void populateAdapter(String arg0) {
        if (arg0.equals("")) {
            arg0 = "_";
        }
        Cursor cursor = contentResolver.query(
                ParticipantContent.buildParticipantUri(arg0.toLowerCase()),
                null, null, null, null);
        mCursorAdapter = new ParticipantRecyclerViewAdapter(participantFragment.initializeData(cursor)
                , participantFragment.recList.getContext());
        participantFragment.recList.setAdapter(mCursorAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.item_facebook:
                Util.irA("https://www.facebook.com/IAU.LARIM/", this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onArticleSelected(String filter) {
        if (participantFragment != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
            participantFragment.populateAdapter(filter);
        } else {
            // Otherwise, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            ParticipantFragment newFragment = new ParticipantFragment();
            Bundle args = new Bundle();
            args.putString(ParticipantFragment.ARG_PARTICIPANT_FILTER, filter);
            newFragment.setArguments(args);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.participant_fragment, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
        Util.log(TAG, filter + "");
    }
}
