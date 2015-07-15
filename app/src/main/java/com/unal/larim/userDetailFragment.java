package com.unal.larim;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.unal.larim.LN.Participant;
import com.unal.larim.LN.ParticipantsContent;

/**
 * A fragment representing a single user detail screen.
 * This fragment is either contained in a {@link userListActivity}
 * in two-pane mode (on tablets) or a {@link userDetailActivity}
 * on handsets.
 */
public class userDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Participant mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public userDetailFragment() {
    }

    private TextView full_name;
    private TextView email;
    private TextView institution;
    private ListView paperList;
    private WebView pdfView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = ParticipantsContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            email = (TextView) rootView.findViewById(R.id.user_email);
            institution = (TextView) rootView.findViewById(R.id.user_institution);
            paperList = (ListView) rootView.findViewById(R.id.expandableListDetails);
            full_name = ((TextView) rootView.findViewById(R.id.user_detail));
            full_name.setText(mItem.toString());
            email.setText(mItem.email);
            institution.setText(mItem.institution);
            pdfView = (WebView) rootView.findViewById(R.id.pdfView);
            pdfView.getSettings().setJavaScriptEnabled(true);
            pdfView.getSettings().setBuiltInZoomControls(true);
            pdfView.setWebViewClient(new WebViewClient() {
                // evita que los enlaces se abran fuera nuestra app en el navegador
                // de android
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }

            });
            paperList.setAdapter(new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_list_item_activated_1,
                    android.R.id.text1,
                    mItem.papers));
            paperList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String cad = mItem.papers.get(position).split(";")[0];
                    pdfView.loadUrl(cad);
                }
            });
        }

        return rootView;
    }
}
