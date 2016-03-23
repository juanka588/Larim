package com.unal.larim.GUI;

import android.app.Fragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unal.larim.Adapters.ParticipantRecyclerViewAdapter;
import com.unal.larim.Data.Participant;
import com.unal.larim.DataSource.ParticipantContent;
import com.unal.larim.LN.Util;
import com.unal.larim.R;

import java.util.ArrayList;


public class ParticipantFragment extends Fragment {

    private static final String TAG = ParticipantFragment.class.getSimpleName();
    public static final String ARG_PARTICIPANT_FILTER = "filter";
    public RecyclerView recList;
    public String filter;


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
        recList = (RecyclerView) rootView.findViewById(R.id.cardListParticipant);
        recList.setHasFixedSize(true);
        recList.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager llm = new LinearLayoutManager(rootView.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        populateAdapter(filter);
        return rootView;
    }

    public void populateAdapter(String filter) {
        this.filter = filter;
        ParticipantRecyclerViewAdapter adapter =
                new ParticipantRecyclerViewAdapter(initializeData(filter), getActivity());
        recList.setAdapter(adapter);
    }

    public ArrayList<Participant> initializeData(String filter) {
        Util.log(TAG,filter);
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = contentResolver.query(
                ParticipantContent.buildParticipantUri(ParticipantContent.column_type, filter),
                null, null, null, null);
        String mat[][] = Util.imprimirLista(cursor);
        ArrayList<Participant> participants = new ArrayList<>();
        for (int i = 0; i < mat.length; i++) {
            participants.add(new Participant(mat[i][0], mat[i][1], mat[i][2], mat[i][3],
                    mat[i][4], mat[i][5], mat[i][6],Long.parseLong(mat[i][7])));
        }
        cursor.close();
        return participants;
    }

    public ArrayList<Participant> initializeData(Cursor cursor) {
        String mat[][] = Util.imprimirLista(cursor);
        ArrayList<Participant> participants = new ArrayList<>();
        for (int i = 0; i < mat.length; i++) {
            participants.add(new Participant(mat[i][0], mat[i][1], mat[i][2], mat[i][3],
                    mat[i][4], mat[i][5], mat[i][6],Long.parseLong(mat[i][7])));
        }
        cursor.close();
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
