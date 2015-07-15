package com.unal.larim;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.unal.larim.LN.LinnaeusDatabase;
import com.unal.larim.LN.NewsRecyclerViewAdapter;
import com.unal.larim.LN.Notice;
import com.unal.larim.LN.Util;

import java.util.ArrayList;


public class NewsActivity extends ActionBarActivity {

    public static String table_name = "notice";
    public static String column_names[] = new String[]{"_id", "title", "content", "checked"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        RecyclerView recList = (RecyclerView) findViewById(R.id.cardList);
        Bundle b = this.getIntent().getExtras();
        Notice notice = (Notice) b.getSerializable("notice");
        LinnaeusDatabase lb = new LinnaeusDatabase(getApplicationContext());
        SQLiteDatabase db = openOrCreateDatabase(LinnaeusDatabase.DATABASE_NAME,
                MODE_WORLD_READABLE, null);
        if (notice != null) {
            ContentValues cv = new ContentValues();
            cv.put(column_names[1], notice.title);
            cv.put(column_names[2], notice.content);
            cv.put(column_names[3], false);
            long retrived = db.insert(table_name, null, cv);
            Util.log("Fue Exitoso?", retrived + "");
        }
        recList.setHasFixedSize(true);
        recList.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        NewsRecyclerViewAdapter adapter = new NewsRecyclerViewAdapter(initializeData(db), getApplicationContext());
        recList.setAdapter(adapter);

    }

    private ArrayList<Notice> initializeData(SQLiteDatabase db) {
        Cursor c = db.query(table_name, column_names, column_names[3] + "=?", new String[]{"0"}, null, null, null);
        String[][] mat = Util.imprimirLista(c);
        ArrayList<Notice> noticias = new ArrayList<>();
        for (int i = 0; i < mat.length; i++) {
            noticias.add(new Notice(mat[i][1], mat[i][2], mat[i][3], mat[i][0]));
        }
        c.close();
        db.close();
        Util.log("Noticias size", noticias.size() + "");
        return noticias;
    }
}
