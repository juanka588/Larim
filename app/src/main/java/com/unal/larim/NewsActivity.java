package com.unal.larim;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.unal.larim.LN.NewsRecyclerViewAdapter;
import com.unal.larim.LN.Notice;

import java.util.ArrayList;


public class NewsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        RecyclerView recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        NewsRecyclerViewAdapter adapter = new NewsRecyclerViewAdapter(initializeData());
        recList.setAdapter(adapter);

    }

    private ArrayList<Notice> initializeData() {
        ArrayList<Notice> noticias = new ArrayList<>();
        noticias.add(new Notice("Emma Wilson", "23 years old", "contenido 1"));
        noticias.add(new Notice("Lavery Maiss", "25 years old", "contenido 2"));
        noticias.add(new Notice("Lillie Watts", "35 years old", "contenido 3"));
        return noticias;
    }

}
