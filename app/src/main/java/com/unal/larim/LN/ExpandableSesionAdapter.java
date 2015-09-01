package com.unal.larim.LN;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.unal.larim.Data.Conference;
import com.unal.larim.DetailConference;
import com.unal.larim.R;

import java.util.ArrayList;
import java.util.List;

public class ExpandableSesionAdapter extends
        BaseExpandableListAdapter {

    public int parentsSize;
    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<List> hours;
    private ArrayList<Conference> child;

    public ExpandableSesionAdapter(ArrayList<List> hours) {
        this.hours = hours;
        this.parentsSize = hours.size() - 1;
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        /*each child represents a paper or conference withs his keywords*/
        child = (ArrayList<Conference>) hours.get(groupPosition);
        final Conference conference = child.get(childPosition);
        TextView textView = null;
        TextView textView2 = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.itemsub, parent, false);
        }
        textView = (TextView) convertView.findViewById(R.id.textItem);
        textView.setText(Util.toCammelCase(conference.title));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent details = new Intent(activity, DetailConference.class);
                details.putExtra(activity.getString(R.string.TAG_CONFERENCE),
                        conference);
                activity.startActivity(details);
            }
        });
        textView2 = (TextView) convertView.findViewById(R.id.textItemSub);
        textView2.setText(conference.place);
        return convertView;

    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grupo, parent, false);
        }
        Conference conference = ((Conference) getChild(groupPosition, 0));
        if (conference != null) {
            String cad = conference.hour;
            CheckedTextView ctv = ((CheckedTextView) convertView);
            ctv.setText("\t\t" + cad + "\n");
            ctv.setChecked(isExpanded);
        }
        return convertView;

    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Conference> group = (ArrayList<Conference>) hours.get(groupPosition);
        if (group != null) {
            if (childPosition < group.size()) {
                return (Conference) group.get(childPosition);
            }
        }
        return null;

    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        int totalGroupPosition = 0;
        for (int i = 0; i < groupPosition; i++) {
            totalGroupPosition += hours.get(i).size();
        }
        return totalGroupPosition + childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return (hours.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return hours.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return hours.size();
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
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
