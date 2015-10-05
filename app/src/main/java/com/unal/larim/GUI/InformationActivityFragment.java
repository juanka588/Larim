package com.unal.larim.GUI;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.unal.larim.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class InformationActivityFragment extends Fragment {
    private TextView title;
    private WebView content;
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
        content = (WebView) rootView.findViewById(R.id.contentInformationView);
        image = (ImageView) rootView.findViewById(R.id.imageInformation);
        title.setText(InformationActivity.information.name);
        content.loadData(InformationActivity.information.content, "text/html", "utf-8");
        content.setBackgroundColor(Color.parseColor("#00000000"));
        image.setImageResource(InformationActivity.information.icon);
        return rootView;
    }
}
