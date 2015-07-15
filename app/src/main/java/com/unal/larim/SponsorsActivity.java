package com.unal.larim;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.unal.larim.LN.Sponsor;
import com.unal.larim.LN.SponsorsRecyclerViewAdapter;
import com.unal.larim.LN.Util;

import java.util.ArrayList;

public class SponsorsActivity extends Activity {
    private RecyclerView listSponsor;
    private RecyclerView listOrganizators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager2 =
                new GridLayoutManager(getApplicationContext(), 2);
       /*
       LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager llm2 = new LinearLayoutManager(this);
        llm2.setOrientation(LinearLayoutManager.VERTICAL);
        */
        listSponsor = (RecyclerView) findViewById(R.id.listSponsors);
        listOrganizators = (RecyclerView) findViewById(R.id.listOrganizators);
        listSponsor.setLayoutManager(gridLayoutManager);
        listOrganizators.setLayoutManager(gridLayoutManager2);

        String[] titles = new String[]{"Avianca", "Colciencias"};
        String[] images = new String[]{"avianca", "colciencias"};
        String[] urls = new String[]{"http://www.avianca.com",
                "http://www.colciencias.gov.co"};
        ArrayList<Sponsor> sponsorList = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            int icon = this.getResources().getIdentifier("drawable/" + images[i], null, this.getPackageName());
            Sponsor s = new Sponsor(titles[i], icon, urls[i]);
            sponsorList.add(s);
        }
        SponsorsRecyclerViewAdapter adapter = new SponsorsRecyclerViewAdapter(sponsorList, this);
        listSponsor.setAdapter(adapter);
        String[] titles2 = new String[]{"Observatorio Astronomico Nacional", "Universidad Nacional de Colombia",
                "Academia Colombiana de Ciencias Fisicas y Naturales", "Universidad de los Andes",
                "Universidad de Antioquia", "Universidad de Cartagena", "Universidad Industrial de Santander"};
        String[] images2 = new String[]{"escudo_oan", "un", "acefyn", "uniandes",
                "udea", "cartagena", "uis"};
        String[] urls2 = new String[]{"http://ciencias.bogota.unal.edu.co/oan/", "http://unal.edu.co", "http://www.accefyn.org.co/",
                "http://www.uniandes.edu.co", "http://www.udea.edu.co/",
                "http://www.unicartagena.edu.co/", "http://www.uis.edu.co/"};

        ArrayList<Sponsor> organizatorList = new ArrayList<>();
        for (int i = 0; i < titles2.length; i++) {
            int icon2 = this.getResources().getIdentifier("drawable/" + images2[i], null, this.getPackageName());
            Sponsor s = new Sponsor(titles2[i], icon2, urls2[i]);
            organizatorList.add(s);
        }
        SponsorsRecyclerViewAdapter adapter2 = new SponsorsRecyclerViewAdapter(organizatorList, this);
        listOrganizators.setAdapter(adapter2);
    }

}
