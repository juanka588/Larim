package com.unal.larim.GUI;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.unal.larim.LN.Util;
import com.unal.larim.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AuditoriumDetailActivityFragment extends Fragment {

    public static String SOURCE = "";
    private WebView webview;

    public AuditoriumDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_auditorium_detail, container, false);
        webview = (WebView) root.findViewById(R.id.detailedImage);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        Util.log("url", SOURCE);
//        webview.loadData(SOURCE, "text/html", "UTF-8");
        webview.loadUrl(SOURCE);
        return root;
    }
}
