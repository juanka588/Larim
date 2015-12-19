package com.unal.larim.GUI;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;

import com.unal.larim.Adapters.NewsRecyclerViewAdapter;
import com.unal.larim.Data.Notice;
import com.unal.larim.DataSource.NoticeContent;
import com.unal.larim.LN.Util;
import com.unal.larim.R;

import java.util.ArrayList;


public class NewsActivity extends AppCompatActivity {

    private RecyclerView recList;
    private Toolbar mToolbar;

    private SwipeRefreshLayout refreshLayout;
    public NewsRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        recList = (RecyclerView) findViewById(R.id.cardList);
        if (savedInstanceState == null) {
            handleBundle();
        }
        initializeList();
        manageToolbar();
    }

    private void manageToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getString(R.string.title_activity_news));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeResources(new int[]{
                R.color.darkblue,
                R.color.darkyellow,
                R.color.black
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                refreshData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void refreshData() {
        /*TODO: call server for answer*/
        new FetchNewsTask().execute();
    }

    private void initializeList() {
        recList.setHasFixedSize(true);
        manageSwipeEffects();
        recList.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        adapter = new NewsRecyclerViewAdapter(initializeData(), this);
        recList.setAdapter(adapter);
    }

    /**
     * inserts the new notice into the database when is sent
     * temporarily until service updating
     */
    private void handleBundle() {
        Bundle b = this.getIntent().getExtras();
        Notice notice = (Notice) b.getSerializable(getString(R.string.ARG_NOTICE));
        if (notice != null) {
            ContentValues cv = new ContentValues();
            cv.put(NoticeContent.column_title, notice.title);
            cv.put(NoticeContent.column_content, notice.content);
            cv.put(NoticeContent.column_checked, false);
            cv.put(NoticeContent.column_url, notice.url);
            ContentResolver contentResolver = getContentResolver();
            Uri retrieved = contentResolver.insert(NoticeContent.CONTENT_URI, cv);
        }
    }

    private void manageSwipeEffects() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback
                (0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                ((NewsRecyclerViewAdapter) recList.getAdapter()).delete(position, viewHolder.itemView);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recList);
    }

    private ArrayList<Notice> initializeData() {
        ContentResolver contentResolver = getContentResolver();
        Cursor c = contentResolver.query(NoticeContent.CONTENT_URI, null, null, null, null);
        String[][] mat = Util.imprimirLista(c);
        ArrayList<Notice> notices = new ArrayList<>();
        for (int i = 0; i < mat.length; i++) {
            notices.add(new Notice(mat[i][0], mat[i][1], mat[i][2], mat[i][3], mat[i][4]));
        }
        c.close();
        Util.log("Noticias size", notices.size() + "");
        return notices;
    }

    private class FetchNewsTask extends AsyncTask<Void, Void, ArrayList<Notice>> {

        static final int DURACION = 3 * 1000; // 3 segundos de carga

        @Override
        protected ArrayList doInBackground(Void... params) {
            // Simulación de la carga de items
            try {
                Thread.sleep(DURACION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Util.log("retorno asincrono", "si");
            // Retornar en nuevos elementos para el adaptador
            /*test*/
            ArrayList<Notice> temp = initializeData();
            int r = (int) (Math.random() * 100);
            temp.add(new Notice("1", "New notice from server" + r, "Contenidos " + r,
                    true, "http://www.google.com"));
            return temp;
        }

        @Override
        protected void onPostExecute(ArrayList result) {
            super.onPostExecute(result);

            // Limpiar elementos antiguos
            adapter.notices.clear();
            adapter.notifyDataSetChanged();
            // Añadir elementos nuevos
            adapter.notices.addAll(result);

            // Parar la animación del indicador
            refreshLayout.setRefreshing(false);
        }

    }
}
