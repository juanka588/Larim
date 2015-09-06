package com.unal.larim.GUI;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.unal.larim.Data.Notice;
import com.unal.larim.DataSource.NoticeContent;
import com.unal.larim.Adapters.NewsRecyclerViewAdapter;
import com.unal.larim.LN.Util;
import com.unal.larim.R;

import java.util.ArrayList;


public class NewsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        RecyclerView recList = (RecyclerView) findViewById(R.id.cardList);
        Bundle b = this.getIntent().getExtras();
        Notice notice = (Notice) b.getSerializable(getString(R.string.ARG_NOTICE));
        if (notice != null) {
            ContentValues cv = new ContentValues();
            cv.put(NoticeContent.column_title, notice.title);
            cv.put(NoticeContent.column_content, notice.content);
            cv.put(NoticeContent.column_checked, false);
            cv.put(NoticeContent.column_url, notice.url);
            ContentResolver contentResolver = getContentResolver();
            Uri retrived = contentResolver.insert(NoticeContent.CONTENT_URI, cv);
            Util.log("Fue Exitoso?", retrived.toString() + "");
        }
        recList.setHasFixedSize(true);
        recList.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        NewsRecyclerViewAdapter adapter = new NewsRecyclerViewAdapter(initializeData(), this);
        recList.setAdapter(adapter);

    }

    private ArrayList<Notice> initializeData() {
        ContentResolver contentResolver = getContentResolver();
        Cursor c = contentResolver.query(NoticeContent.CONTENT_URI, null, null, null, null);
        String[][] mat = Util.imprimirLista(c);
        ArrayList<Notice> noticias = new ArrayList<>();
        for (int i = 0; i < mat.length; i++) {
            noticias.add(new Notice(mat[i][0], mat[i][1], mat[i][2], mat[i][3], mat[i][4]));
        }
        c.close();
        Util.log("Noticias size", noticias.size() + "");
        return noticias;
    }
}
