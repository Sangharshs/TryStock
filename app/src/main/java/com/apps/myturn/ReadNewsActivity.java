package com.apps.myturn;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ReadNewsActivity extends AppCompatActivity {
    ImageButton goBack;
    ProgressBar progressBar;
    WebView webView;
    SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_news);

        goBack = findViewById(R.id.goBackButton);
        progressBar = findViewById(R.id.progressBar);
        webView = findViewById(R.id.loadnewsWebView);

        String news_url = getIntent().getStringExtra("url");



        webView.setWebChromeClient( new MyWebChromeClient());
        webView.setWebViewClient( new webClient());
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(news_url);
        pDialog = new SweetAlertDialog(ReadNewsActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading News...");
        pDialog.show();

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public class MyWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int newProgress) {

        }
    }

    public class webClient extends WebViewClient {
        public boolean  shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pDialog.dismissWithAnimation();
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();

        }else
        {
            super.onBackPressed();
        }
    }
}