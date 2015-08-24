package com.unal.larim;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unal.larim.Data.Participant;
import com.unal.larim.DataSource.ParticipantContent;
import com.unal.larim.LN.LinnaeusDatabase;
import com.unal.larim.LN.ParticipantRecyclerViewAdapter;
import com.unal.larim.LN.Util;

import java.util.ArrayList;


public class ParticipantFragment extends Fragment {
    private static final String TAG = ParticipantFragment.class.getSimpleName();
    private static final String ARG_PARTICIPANT_FILTER = "filter";
    private RecyclerView recList;
    private String filter;

    public ParticipantFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        try {
            filter = getArguments().getString(ARG_PARTICIPANT_FILTER);
        } catch (Exception e) {
            filter = ParticipantContent.TYPE_NORMAL;
            Util.log(TAG, e.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_participant, container, false);
        Context context = rootView.getContext();
        recList = (RecyclerView) rootView.findViewById(R.id.cardListParticipant);
        LinnaeusDatabase lb = new LinnaeusDatabase(rootView.getContext());
        SQLiteDatabase db = lb.dataBase;
        recList.setHasFixedSize(true);
        recList.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager llm = new LinearLayoutManager(rootView.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        ParticipantRecyclerViewAdapter adapter =
                new ParticipantRecyclerViewAdapter(initializeData(db, context), context);
        recList.setAdapter(adapter);
        return rootView;
    }

    private ArrayList<Participant> initializeData(SQLiteDatabase db, Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(
                ParticipantContent.buildParticipantUri(ParticipantContent.column_type, filter),
                null, null, null, null);
        String mat[][] = Util.imprimirLista(cursor);
        ArrayList<Participant> participants = new ArrayList<>();
        for (int i = 0; i < mat.length; i++) {
            participants.add(new Participant(mat[i][0], mat[i][1], mat[i][2], mat[i][3], mat[i][4]));
        }
        cursor.close();
        db.close();
        return participants;
    }

    public static ParticipantFragment newInstance(String filter) {
        ParticipantFragment pf = new ParticipantFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARTICIPANT_FILTER, filter);
        pf.setArguments(bundle);
        return pf;
    }
}
