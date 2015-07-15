package com.unal.larim.LN;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.unal.larim.DetailConference;
import com.unal.larim.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ExpandableSesionAdapter extends
        BaseExpandableListAdapter {

    public int parentsSize;
    private Activity activity;
    private ArrayList<List> childtems;
    private LayoutInflater inflater;
    private ArrayList<String> parentItems, child;

    public ExpandableSesionAdapter(ArrayList<String> parents,
                                   ArrayList<List> children) {

        this.parentItems = parents;
        this.childtems = children;
        this.parentsSize = parents.size() - 1;
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {

        this.inflater = inflater;

        this.activity = activity;

    }

    @SuppressWarnings("unchecked")
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        /*each child represents a paper or conference withs his keywords*/
        child = (ArrayList<String>) childtems.get(groupPosition);

        TextView textView = null;
        TextView textView2 = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.itemsub, parent, false);
        }
        final int posicion = (int) getChildId(groupPosition, childPosition);
        textView = (TextView) convertView.findViewById(R.id.textItem);
        textView.setText(Util.toCammelCase(child.get(0)));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent details = new Intent(activity, DetailConference.class);
                activity.startActivity(details);
            }
        });
        textView2 = (TextView) convertView.findViewById(R.id.textItemSub);
        textView2.setText(child.get(1));
        return convertView;

    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grupo, parent, false);

        }
        String cad = parentItems.get(groupPosition).toUpperCase(
                Locale.getDefault());
        CheckedTextView ctv = ((CheckedTextView) convertView);
        ctv.setText("\t\t" + cad + "\n");
        ctv.setChecked(isExpanded);
        return convertView;

    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;

    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        int totalGroupPosition = 0;
        for (int i = 0; i < groupPosition; i++) {
            totalGroupPosition += childtems.get(i).size();
        }
        return totalGroupPosition + childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ((ArrayList<String>) childtems.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return child;
    }

    @Override
    public int getGroupCount() {
        return parentItems.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
