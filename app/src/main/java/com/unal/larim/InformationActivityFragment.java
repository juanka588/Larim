package com.unal.larim;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class InformationActivityFragment extends Fragment {
    private TextView title;
    private TextView content;
    private ImageView image;

    public InformationActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_information, container, false);
        title = (TextView) rootView.findViewById(R.id.textInformationTitle);
        content = (TextView) rootView.findViewById(R.id.textContentInformation);
        image = (ImageView) rootView.findViewById(R.id.imageInformation);
        title.setText(InformationActivity.information.name);
        content.setText(Html.fromHtml(InformationActivity.information.content));
        image.setImageResource(InformationActivity.information.icon);
        return rootView;
    }
}
