package com.unal.larim.GUI;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.unal.larim.Adapters.ExpandableSessionAdapter;
import com.unal.larim.Data.Conference;
import com.unal.larim.DataSource.ConferenceContent;
import com.unal.larim.LN.Util;
import com.unal.larim.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by JuanCamilo on 26/07/2015.
 */
public class DayFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";
    private static final String TAG = DayFragment.class.getSimpleName();

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DayFragment newInstance(int sectionNumber) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public DayFragment() {
    }

    private TextView dayTitle;
    private TextView currentDay;
    private TextView dayDate;
    private ExpandableListView sections;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cronograma, container, false);
        Context context = rootView.getContext();

        dayTitle = (TextView) rootView.findViewById(R.id.textDayTitle);
        currentDay = (TextView) rootView.findViewById(R.id.textCurrentDate);
        dayDate = (TextView) rootView.findViewById(R.id.textDayDate);
        sections = (ExpandableListView) rootView.findViewById(R.id.expandableSections);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date today = Calendar.getInstance().getTime();
        String date = df.format(today);
        currentDay.setText(getString(R.string.today) + " " + date);
        int todayInt = Schedule.selectCurrentDay();
        Bundle bundle = this.getArguments();
        int selected = bundle.getInt(ARG_SECTION_NUMBER);
        if (selected == 6) {
            selected = 5;
        }
        ExpandableSessionAdapter adapter = new ExpandableSessionAdapter(initializeData());
        adapter.setInflater(
                (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE),
                getActivity());
        sections.setAdapter(adapter);
        dayTitle.setText(Schedule.getDayTitle(selected, context));
        int sum = selected - todayInt;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_YEAR, sum);
        dayDate.setText(df.format(calendar.getTime()));
        return rootView;
    }

    private ArrayList<List> initializeData() {
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor c = contentResolver.query(ConferenceContent.CONTENT_URI,
                null, null, null, ConferenceContent.column_hour_start);
         /*TODO: utilize date filter*/
        String[][] mat = Util.imprimirLista(c);
        ArrayList<List> hours = new ArrayList<>();
        ArrayList<Conference> conferences = new ArrayList<>();
        Conference conference;
        if (mat.length > 0) {
            String hour = mat[0][3] + "-" + mat[0][4];
            String currentHour;
            for (int i = 0; i < mat.length; i++) {
                currentHour = mat[i][3] + "-" + mat[i][4];
                if (!hour.equals(currentHour)) {
                    hours.add(conferences);
                    conferences = new ArrayList<>();
                    hour = currentHour;
                }
                conference = new Conference(mat[i][0], mat[i][1], mat[i][2], currentHour, mat[i][5],
                        mat[i][6], mat[i][7], mat[i][8], mat[i][9]);
                conferences.add(conference);
            }
            hours.add(conferences);
        }
        c.close();
        return hours;
    }
}
