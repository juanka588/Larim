package com.unal.larim;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.unal.larim.DataSource.ParticipantContent;
import com.unal.larim.LN.Util;

public class ParticipantActivity extends AppCompatActivity {
    private static String TAG = ParticipantActivity.class.getSimpleName();
    private SearchView sv;
    private SimpleCursorAdapter simpleCursorAdapter;
    private String[] from = new String[]{ParticipantContent.column_name};
    private int[] to = new int[]{android.R.id.text1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant);
        ParticipantFragment pf = ParticipantFragment.newInstance(
                ParticipantContent.TYPE_NORMAL);
//        SelectorParticipantFragment pf2 = new SelectorParticipantFragment();
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.replace(R.id.participant_fragment, pf);
//        ft.replace(R.id.participant_selector_fragment, pf2);
//        ft.show(getFragmentManager().findFragmentById(R.id.participant_fragment));
//        ft.show(getFragmentManager().findFragmentById(R.id.participant_selector_fragment));
//        ft.addToBackStack(null);
//        ft.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_participant, menu);
        sv = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_busqueda));
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ParticipantContent.buildParticipantUri("_"),
                null, null, null, null);
        simpleCursorAdapter = new SimpleCursorAdapter(
                getApplicationContext(), android.R.layout.simple_list_item_1, cursor,
                from, to, 0);
        sv.setSuggestionsAdapter(simpleCursorAdapter);
        sv.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                Cursor c = simpleCursorAdapter.getCursor();
                String cad = c.getColumnName(1);
                Util.log(TAG, cad);
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Cursor c = simpleCursorAdapter.getCursor();
                if (c.moveToPosition(position)) {
                    String cad = c.getString(1);
                    Util.log(TAG, cad);
                    Util.enviar(ParticipantActivity.this, cad, "", "", "");
                }
                return false;
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
                return false;
            }
        });

        return true;
    }

    private void populateAdapter(String arg0) {
        if (arg0.equals("")) {
            arg0 = "_";
        }
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(
                ParticipantContent.buildParticipantUri(arg0.toLowerCase()),
                null, null, null, null);
        simpleCursorAdapter.changeCursorAndColumns(cursor, from, to);
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
