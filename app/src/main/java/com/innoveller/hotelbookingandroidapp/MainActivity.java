package com.innoveller.hotelbookingandroidapp;

import static androidx.core.app.ActivityCompat.finishAffinity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("On Create......");
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        String appUserToken = UniqueIDHelper.getAppUserToken(getApplicationContext());
        String agentId = getString(R.string.agent_id);

        webView = findViewById(R.id.wv_id);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);

        int delaySec = getIntent().getIntExtra("delaySec", 4000);
        System.out.println("Delay Sec : " + delaySec);

        webView.addJavascriptInterface(new WebAppInterface(this, appUserToken), "AndroidInterface");
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://hotel-aggregator-dev.herokuapp.com/agents/" + agentId + "/androidwebview/start");

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
        }, delaySec);

        System.out.println("App User Token : " + appUserToken);
    }

    @Override
    public void onBackPressed() {
        System.out.println("get original url : " + webView.getOriginalUrl());
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }
    }

    private class WebAppInterface {
        private final Activity activity;
        private final String appUserToken;

        public WebAppInterface(Activity activity, String appUserToken) {
            this.activity = activity;
            this.appUserToken = appUserToken;
        }

        // Method to retrieve app user id, accessible from JavaScript
        @JavascriptInterface
        public String getAppUserToken() {
            return this.appUserToken;
        }

        @JavascriptInterface
        public void restartActivity(){
            System.out.println("Call restart activity....");

            Intent intent = new Intent(activity, MainActivity.class);
            intent.putExtra("delaySec", 7000);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            activity.finish();
        }
    }
}