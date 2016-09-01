package com.unal.larim.GUI;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
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
    private static final long INIT_DATE = 1475366410;
    private static final long DAY_LENGTH = 86400;
    private TextView dayTitle;
    private TextView currentDay;
    private TextView dayDate;
    private ExpandableListView sections;
    private int selected;


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment newInstance(int sectionNumber) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public DayFragment() {
    }

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
        currentDay.setText(new StringBuilder().append(getString(R.string.today)).append(" ")
                .append(date).toString());
        Bundle bundle = this.getArguments();
        selected = bundle.getInt(ARG_SECTION_NUMBER);
        if (selected == 6) {
            selected = 5;
        }
        ExpandableSessionAdapter adapter = new ExpandableSessionAdapter(initializeData());
        adapter.setInflater(
                (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE),
                getActivity());
        sections.setAdapter(adapter);
        dayTitle.setText(ScheduleActivity.getDayTitle(selected, context));
        Calendar calendar = Calendar.getInstance();
        long sum = INIT_DATE + DAY_LENGTH * selected;
        Date day = new Date(sum * 1000);
        calendar.setTime(day);
        dayDate.setText(df.format(calendar.getTime()));
        return rootView;
    }

    private List<List> initializeData() {
        String condition = new StringBuilder().append(ConferenceContent.column_date).append(">=? AND ")
                .append(ConferenceContent.column_date).append("<=?").toString();
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor c = contentResolver.query(ConferenceContent.CONTENT_URI,
                null, condition,
                new String[]{String.valueOf(INIT_DATE + DAY_LENGTH * selected),
                        String.valueOf(INIT_DATE + DAY_LENGTH * (selected + 1))},
                ConferenceContent.column_date);
        String[][] mat = Util.imprimirLista(c);
        List<List> hours = new ArrayList<>();
        List<Conference> conferences = new ArrayList<>();
        Conference conference;
        if (mat.length > 0) {
            String hour = new StringBuilder().append(mat[0][3]).append("-").append(mat[0][4]).toString();
            String currentHour;
            for (int i = 0; i < mat.length; i++) {
                currentHour = mat[i][3] + "-" + mat[i][4];
                if (!hour.equals(currentHour)) {
                    hours.add(conferences);
                    conferences = new ArrayList<>();
                    hour = currentHour;
                }
                conference = new Conference(Long.parseLong(mat[i][0]), mat[i][1], mat[i][2],
                        currentHour, mat[i][5], Long.parseLong(mat[i][6]), mat[i][7], mat[i][8], mat[i][9], mat[i][11]);
                conferences.add(conference);
            }
            hours.add(conferences);
        }
        c.close();
        return hours;
    }
}