package com.hasbrain.areyouandroiddev;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by Khanh Ultron on 12/18/2015.
 */
public class PostViewActivity extends AppCompatActivity {
    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);

        String url = getIntent().getStringExtra("url");
        if (TextUtils.isEmpty(url))
            finish();
        else {
            wv = (WebView) findViewById(R.id.web_view);
            wv.getSettings().setBuiltInZoomControls(true);
            wv.getSettings().setDisplayZoomControls(false);

            wv.setWebChromeClient(new WebChromeClient());
            wv.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    setTitle(view.getTitle());
                }
            });
            wv.loadUrl(url);
        }
    }

    @Override
    public void onBackPressed() {
        if (wv != null && wv.canGoBack()) {
            wv.goBack();
        } else
            super.onBackPressed();
    }
}
