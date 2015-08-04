package com.unal.larim;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.unal.larim.Data.Notice;
import com.unal.larim.DataSource.NoticeContent;
import com.unal.larim.LN.LinnaeusDatabase;
import com.unal.larim.LN.NewsRecyclerViewAdapter;
import com.unal.larim.LN.Util;

import java.util.ArrayList;


public class NewsActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        RecyclerView recList = (RecyclerView) findViewById(R.id.cardList);
        Bundle b = this.getIntent().getExtras();
        Notice notice = (Notice) b.getSerializable("notice");
        LinnaeusDatabase lb = new LinnaeusDatabase(getApplicationContext());
        SQLiteDatabase db = lb.dataBase;
        if (notice != null) {
            db.beginTransaction();
            ContentValues cv = new ContentValues();
            cv.put(NoticeContent.column_title, notice.title);
            cv.put(NoticeContent.column_content, notice.content);
            cv.put(NoticeContent.column_checked, false);
            cv.put(NoticeContent.column_url, notice.url);
            long retrived = db.insert(NoticeContent.table_name, null, cv);
            Util.log("Fue Exitoso?", retrived + "");
        }
        recList.setHasFixedSize(true);
        recList.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        NewsRecyclerViewAdapter adapter = new NewsRecyclerViewAdapter(initializeData(db), this);
        recList.setAdapter(adapter);

    }

    private ArrayList<Notice> initializeData(SQLiteDatabase db) {
        Cursor c = db.query(NoticeContent.table_name, NoticeContent.column_names,
                null, null, null, null, null);
        String[][] mat = Util.imprimirLista(c);
        ArrayList<Notice> noticias = new ArrayList<>();
        for (int i = 0; i < mat.length; i++) {
            noticias.add(new Notice(mat[i][0], mat[i][1], mat[i][2], mat[i][3], mat[i][4]));
        }
        c.close();
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        Util.log("Noticias size", noticias.size() + "");
        return noticias;
    }
}
