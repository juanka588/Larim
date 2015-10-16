package com.unal.larim.GUI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.unal.larim.LN.Util;
import com.unal.larim.R;


public class WebActivity extends AppCompatActivity {

    private String URL = "";
    private WebView browser;
    private boolean appBarEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle b = getIntent().getExtras();
            URL = b.getString(getString(R.string.ARG_WEB_PAGE));
            appBarEnabled = b.getBoolean(getString(R.string.ARG_NAV_BAR));
            if (!appBarEnabled) {
                supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        browser = (WebView) findViewById(R.id.webView1);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setBuiltInZoomControls(true);
        browser.setWebViewClient(new WebViewClient() {
            // evita que los enlaces se abran fuera nuestra app en el navegador
            // de android
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

        });
        browser.loadUrl(URL);
        if (!Util.isOnline(this)) {
            Util.notificarRed(this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("URL", browser.getUrl());
        Log.e("pagina actual", "" + browser.getUrl());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        URL = savedInstanceState.getString("URL");
        browser.loadUrl(URL);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        menu.getItem(3).setTitle(URL);
        menu.getItem(3).setTitleCondensed(URL);
        return super.onCreateOptionsMenu(menu);
    }

    public void goBack(View v) {
        goBack();
    }

    public void goBack() {
        if (browser.canGoBack()) {
            browser.goBack();
        }
    }

    public void forward() {
        if (browser.canGoForward()) {
            browser.goForward();
        }
    }

    public void reload() {
        browser.reload();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ItemVolver:
                goBack();
                return true;
            case R.id.ItemRefresh:
                reload();
                return true;
            case R.id.ItemForward:
                forward();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
