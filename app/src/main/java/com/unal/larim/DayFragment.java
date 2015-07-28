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

import com.unal.larim.Data.Session;
import com.unal.larim.LN.ExpandableSesionAdapter;
import com.unal.larim.LN.LinnaeusDatabase;
import com.unal.larim.LN.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by JuanCamilo on 26/07/2015.
 */
public class DayFragment extends Fragment {

    public static String table_names[] = new String[]{"session", "code", "conference"};
    public static String column_names[] = new String[]{"date", "initials", "description", "title",
            "place", "hour"};
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
        String date = df.format(today);
        currentDay.setText(getString(R.string.today) + " " + date);

        Bundle bundle = this.getArguments();
        int selected = bundle.getInt(ARG_SECTION_NUMBER);

            /*TODO: replace with database data*/
        LinnaeusDatabase lb = new LinnaeusDatabase(rootView.getContext());
        SQLiteDatabase db = rootView.getContext().openOrCreateDatabase(LinnaeusDatabase.DATABASE_NAME,
                rootView.getContext().MODE_PRIVATE, null);
        ExpandableSesionAdapter adapter = new ExpandableSesionAdapter(initializeData(db));
        adapter.setInflater(
                (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE),
                getActivity());
        sections.setAdapter(adapter);
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

    private ArrayList<Session> initializeData(SQLiteDatabase db) {
        Cursor c = db.query(Util.toString(table_names), column_names, column_names[3] + "=?", new String[]{"0"}, null, null, null);
        String[][] mat = Util.imprimirLista(c);
        ArrayList<Session> sesions = new ArrayList<>();
        for (int i = 0; i < mat.length; i++) {
            // sesions.add(new Notice(mat[i][1], mat[i][2], mat[i][3], mat[i][0],mat[i][4]));
        }
        c.close();
        db.close();
        Util.log("Sesiones size", sesions.size() + "");
        return sesions;
    }

}
