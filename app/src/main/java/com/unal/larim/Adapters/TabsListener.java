package com.unal.larim.Adapters;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * Created by JuanCamilo on 14/05/2015.
 */
public class TabsListener<T extends Fragment> implements ActionBar.TabListener {

    private Fragment fragment;
    private final String tag;

    public TabsListener(Activity activity, String tag, Class<T> cls) {
        this.tag = tag;
        fragment = Fragment.instantiate(activity, cls.getName());
    }

    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.replace(android.R.id.content, fragment, tag);
    }

    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.remove(fragment);
    }

    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

}
