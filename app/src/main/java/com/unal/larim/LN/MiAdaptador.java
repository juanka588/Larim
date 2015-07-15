package com.unal.larim.LN;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.unal.larim.R;

import java.util.ArrayList;

public class MiAdaptador extends BaseAdapter {

    private final Activity actividad;
    private final String[] lista;
    private final String[] lista2;
    private final String[] urls;

    public MiAdaptador(Activity actividad, ArrayList<String> titulos,
                       ArrayList<String> subtitulos, ArrayList<String> URLS) {
        super();
        this.actividad = actividad;
        lista = new String[titulos.size()];
        titulos.toArray(lista);
        lista2 = new String[subtitulos.size()];
        subtitulos.toArray(lista2);
        urls = new String[URLS.size()];
        URLS.toArray(urls);
    }

    public MiAdaptador(Activity actividad, String[] titulos, String[] subtitulos, String[] URLS) {
        super();
        this.actividad = actividad;
        this.lista = titulos;
        this.lista2 = subtitulos;
        this.urls = URLS;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = actividad.getLayoutInflater();
            view = inflater.inflate(R.layout.elemento_lista, parent, false);
        }
        TextView textView = (TextView) view.findViewById(R.id.titulo);
        ImageView imageView = (ImageView) view.findViewById(R.id.icono);
        if (lista[position] != null) {
            textView.setText(Util.toCammelCase(lista[position].toLowerCase()));
            textView.setHint(Util.toCammelCase(lista[position].toLowerCase()));
        }
        imageView.setImageResource(R.drawable.ic_launcher);
        if (lista2[position] != null) {
            String cad = lista2[position];
            cad = cad.substring(0, cad.length() - 4);
            int id = actividad.getResources().getIdentifier(
                    "com.unal.larim:drawable/" + cad, null, null);
            imageView.setImageResource(id);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.irA(urls[position], actividad);
            }
        });
        return view;
    }

    public int getCount() {
        return lista.length;
    }

    public Object getItem(int arg0) {
        return lista[arg0];
    }

    public long getItemId(int position) {
        return position;
    }
}