package com.unal.larim.GUI;

import android.app.Activity;
import android.app.ListFragment;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.unal.larim.DataSource.ParticipantContent;
import com.unal.larim.Interfaces.OnHeadlineSelectedListener;
import com.unal.larim.LN.Util;
import com.unal.larim.R;


public class SelectorParticipantFragment extends ListFragment implements OnHeadlineSelectedListener {
    private static final String TAG = SelectorParticipantFragment.class.getSimpleName();
    private static final String TAG_CURRENT_POS = "position";
    private ListView recList;
    private String filter;
    private int layout = R.layout.simple_list_item_activated_1;
    private String[] from = new String[]{ParticipantContent.column_type};
    private int[] to = new int[]{android.R.id.text1};
    private int currentPosition = 0;
    private String dataMat[][];

    OnHeadlineSelectedListener mCallback;

    public SelectorParticipantFragment() {
        filter = "all";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_selector, container, false);
        final Context context = rootView.getContext();
        recList = (ListView) rootView.findViewById(android.R.id.list);
        populateData(context);
        String arr[] = new String[dataMat.length];
        for (int i = 0; i < dataMat.length; i++) {
            String cad = ParticipantContent.getTypeString(dataMat[i][0] + "");
            arr[i] = cad;
        }
        recList.setAdapter(new ArrayAdapter<String>(context, layout, arr));
        recList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        recList.setItemChecked(currentPosition, true);
        recList.smoothScrollToPosition(currentPosition);
        return rootView;
    }

    public void populateData(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        final Cursor cursor = contentResolver.query(
                ParticipantContent.buildParticipantUri(ParticipantContent.column_type, filter),
                null, null, null, null);
        final String mat[][] = Util.imprimirLista(cursor);
        dataMat = mat;
        cursor.close();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        currentPosition = position;
        if (dataMat[position][0] == null) {
            mCallback.onArticleSelected("-");
        } else {
            mCallback.onArticleSelected(dataMat[position][0]);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(TAG_CURRENT_POS, currentPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState == null) {
            return;
        }
        currentPosition = savedInstanceState.getInt(TAG_CURRENT_POS);
        recList.smoothScrollToPosition(currentPosition);
        recList.setSelection(currentPosition);
        recList.setItemChecked(currentPosition, true);
        recList.setItemsCanFocus(true);
        Util.log(TAG, currentPosition + " restored");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    @Override
    public void onArticleSelected(String filter) {

    }
}
