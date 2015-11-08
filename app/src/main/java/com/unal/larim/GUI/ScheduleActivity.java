package com.unal.larim.GUI;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.unal.larim.LN.Util;
import com.unal.larim.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ScheduleActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private static final String TAG = ScheduleActivity.class.getSimpleName();
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        manageToolbar();
    }

    private void manageToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getString(R.string.title_activity_schedule));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeViewPager();
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void initializeViewPager() {
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        int day = selectCurrentDay();
        if (day == 6) {
            day = 5;
        }
        mViewPager.setCurrentItem(day);
    }

    public static int selectCurrentDay() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("EEEE", Locale.US);
        String dateString = df.format(date);
        Util.log(TAG, dateString);
        if (dateString.equals("Monday")) {
            return 1;
        }
        if (dateString.equals("Tuesday")) {
            return 2;
        }
        if (dateString.equals("Wednesday")) {
            return 3;
        }
        if (dateString.equals("Thursday")) {
            return 4;
        }
        if (dateString.equals("Friday")) {
            return 5;
        }
        if (dateString.equals("Saturday")) {
            return 6;
        }
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return DayFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 6 total pages.
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getDayTitle(position, getApplicationContext());
        }
    }

    public static CharSequence getDayTitle(int selection, Context c) {
        Locale l = Locale.getDefault();
        switch (selection) {
            case 0:
                return c.getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return c.getString(R.string.title_section2).toUpperCase(l);
            case 2:
                return c.getString(R.string.title_section3).toUpperCase(l);
            case 3:
                return c.getString(R.string.title_section4).toUpperCase(l);
            case 4:
                return c.getString(R.string.title_section5).toUpperCase(l);
            case 5:
                return c.getString(R.string.title_section6).toUpperCase(l);
        }
        return null;
    }

}
