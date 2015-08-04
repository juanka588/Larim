package com.unal.larim;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.unal.larim.Data.Conference;
import com.unal.larim.DataSource.ConferenceContent;
import com.unal.larim.LN.ExpandableSesionAdapter;
import com.unal.larim.LN.LinnaeusDatabase;
import com.unal.larim.LN.Util;

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
    private static final String ARG_SECTION_NUMBER = "section_number";

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
        dayTitle = (TextView) rootView.findViewById(R.id.textDayTitle);
        currentDay = (TextView) rootView.findViewById(R.id.textCurrentDate);
        dayDate = (TextView) rootView.findViewById(R.id.textDayDate);

        sections = (ExpandableListView) rootView.findViewById(R.id.expandableSections);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date today = Calendar.getInstance().getTime();
        Util.log("fecha de hoy en int", today.toString());
        String date = df.format(today);
        currentDay.setText(getString(R.string.today) + " " + date);

        Bundle bundle = this.getArguments();
        int selected = bundle.getInt(ARG_SECTION_NUMBER);
        LinnaeusDatabase lb = new LinnaeusDatabase(rootView.getContext());
        SQLiteDatabase db = lb.dataBase;
        ExpandableSesionAdapter adapter = new ExpandableSesionAdapter(initializeData(db));
        adapter.setInflater(
                (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE),
                getActivity());
        sections.setAdapter(adapter);
        db.close();
        int suma = 0;
        /*TODO: configurar suma para que cuadren los dias*/
        if (bundle != null) {
            suma = selected - 1;
            switch (selected - 1) {
                case 0:
                    dayTitle.setText(R.string.title_section1);
                    break;
                case 1:
                    dayTitle.setText(R.string.title_section2);
                    break;
                case 2:
                    dayTitle.setText(R.string.title_section3);
                    break;
                case 3:
                    dayTitle.setText(R.string.title_section4);
                    break;
                case 4:
                    dayTitle.setText(R.string.title_section5);
                    break;
                case 5:
                    dayTitle.setText(R.string.title_section6);
                    break;
            }
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_YEAR, suma);
        dayDate.setText(df.format(calendar.getTime()));
        return rootView;
    }

    private ArrayList<List> initializeData(SQLiteDatabase db) {
        Cursor c = db.query(ConferenceContent.table_name_conference + " a inner join " +
                        ConferenceContent.table_name_code
                        + " b on a." + ConferenceContent.column_code_id
                        + "=b._id inner join " + ConferenceContent.table_name_chairman
                        + " c on c._id=a."
                        + ConferenceContent.column_chairman_id, ConferenceContent.column_names,
                /*TODO: utilize date filter*/
                null,//selection
                null,//args
                null,//group by
                null,//having
                ConferenceContent.column_hour//order by
        );
        String[][] mat = Util.imprimirLista(c);
        ArrayList<List> hours = new ArrayList<>();
        ArrayList<Conference> conferences = new ArrayList<>();
        Conference conference;
        String hour = mat[0][3], currentHour;
        for (int i = 0; i < mat.length; i++) {
            currentHour = mat[i][3];
            if (!hour.equals(currentHour)) {
                hours.add(conferences);
                conferences = new ArrayList<>();
                hour = currentHour;
            }
            conference = new Conference(mat[i][0], mat[i][1], mat[i][2], currentHour, mat[i][4],
                    mat[i][5], mat[i][6], mat[i][7], mat[i][8]);
            conferences.add(conference);

        }
        hours.add(conferences);
        c.close();
        Util.log("hours size", hours.size() + "");
        return hours;
    }
}
