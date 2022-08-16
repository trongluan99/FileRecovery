package com.jm.filerecovery.videorecovery.photorecovery.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;


import com.ads.control.AdmobHelp;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.utilts.SharePreferenceUtils;

import java.util.Locale;



public class SplashActivity extends AppCompatActivity {

    Handler mHandler;
    Runnable r ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLocale();
        setContentView(R.layout.activity_splash);
        AdmobHelp.getInstance().init(this);
        mHandler =new Handler();
        r =  new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, HelpActivity.class));
                finish();


            }
        };

        mHandler.postDelayed(r, 4000);
    }

    @Override
    protected void onDestroy() {
        if(mHandler!=null&&r!=null)
            mHandler.removeCallbacks(r);
        super.onDestroy();
    }

    public void setLocale() {
        int index = SharePreferenceUtils.getInstance(this).getLanguageIndex();
        String language ="en";
        if(index==0){
            language ="en";
        } else if(index==1){
            language ="pt";
        }else if(index==2) {
            language = "vi";
        }else if(index==3) {
            language = "ru";
        }else if(index==4) {
            language = "fr";
        }else if(index==5) {
            language = "ar";
        }else if(index==6) {
            language = "es";
        }else if(index==7) {
            language = "in";
        }

        if (SharePreferenceUtils.getInstance(this).getFirstRun()) {
            language = Locale.getDefault().getLanguage();
            if (language.equalsIgnoreCase("en")) {
                SharePreferenceUtils.getInstance(this).saveLanguageIndex(0);
            }
            if (language.equalsIgnoreCase("pt")) {
                SharePreferenceUtils.getInstance(this).saveLanguageIndex(1);
            }
            if (language.equalsIgnoreCase("vi")) {
                SharePreferenceUtils.getInstance(this).saveLanguageIndex(2);
            }
            if (language.equalsIgnoreCase("ru")) {
                SharePreferenceUtils.getInstance(this).saveLanguageIndex(3);
            }
            if (language.equalsIgnoreCase("fr")) {
                SharePreferenceUtils.getInstance(this).saveLanguageIndex(4);
            }
            if (language.equalsIgnoreCase("ar")) {
                SharePreferenceUtils.getInstance(this).saveLanguageIndex(5);
            }
            if (language.equalsIgnoreCase("es")) {
                SharePreferenceUtils.getInstance(this).saveLanguageIndex(6);
            }
            if (language.equalsIgnoreCase("in")) {
                SharePreferenceUtils.getInstance(this).saveLanguageIndex(7);
            }

        }

        Locale myLocale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

}
