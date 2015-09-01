package com.unal.larim;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.unal.larim.DataSource.ParticipantContent;
import com.unal.larim.LN.Util;


public class SelectorParticipantFragment extends Fragment {
    private static final String TAG = SelectorParticipantFragment.class.getSimpleName();
    private ListView recList;
    private String filter;
    private int layout = android.R.layout.simple_list_item_activated_1;
    private String[] from = new String[]{ParticipantContent.column_type};
    private int[] to = new int[]{android.R.id.text1};

    public SelectorParticipantFragment() {
        filter = "all";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_selector, container, false);
        Context context = rootView.getContext();
        recList = (ListView) rootView.findViewById(R.id.listParticipantSelector);
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(
                ParticipantContent.buildParticipantUri(ParticipantContent.column_type, filter),
                null, null, null, null);
        String mat[][] = Util.imprimirLista(cursor);
        String arr[] = new String[mat.length];
        for (int i = 0; i < mat.length; i++) {
            String cad = mat[i][0] + "";
            if (cad.equals("null")) {
                arr[i] = "Normal";
            } else {
                arr[i] = mat[i][0] + "";
            }
        }
        recList.setAdapter(new ArrayAdapter<String>(context, layout, arr));
        recList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*TODO:you must implement the fragment manager */
//                ParticipantFragment pf = ParticipantFragment.newInstance(
//                        ParticipantContent.TYPE_LOCAL_ORGANIZING_COMMITTEE);
//                getFragmentManager().beginTransaction()
//                        .replace(R.id.participant_fragment, pf)
//                        .show(getFragmentManager().findFragmentById(R.id.participant_fragment))
//                        .commit();
            }
        });
        cursor.close();
        return rootView;
    }

}
