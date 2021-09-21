package com.apps.myturn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
         splash();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public boolean checkInternet() {
        boolean mobileNwInfo;
        ConnectivityManager conxMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        try {
            mobileNwInfo = conxMgr.getActiveNetworkInfo().isConnected();
        } catch (NullPointerException e) {
            mobileNwInfo = false;
        }
        return mobileNwInfo;
    }

    public void splash(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                finish();
            }
        },3000);
    }
}