package com.jm.filerecovery.videorecovery.photorecovery.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.ads.control.admob.Admob;
import com.ads.control.admob.AppOpenManager;
import com.ads.control.ads.AperoAd;
import com.ads.control.ads.AperoAdCallback;
import com.ads.control.ads.AperoInitCallback;
import com.ads.control.ads.wrapper.ApAdError;
import com.ads.control.ads.wrapper.ApInterstitialAd;
import com.ads.control.funtion.AdCallback;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.jm.filerecovery.videorecovery.photorecovery.BaseActivity;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.RemoteConfigUtils;
import com.jm.filerecovery.videorecovery.photorecovery.utils.SharePreferenceUtils;

import java.util.Locale;


public class SplashActivity extends BaseActivity {
    private AppOpenAd.AppOpenAdLoadCallback loadCallback;
    private AppOpenAd appOpenAd = null;
    private String TAG = "TuanPA38";
    private long time = 6000;
    boolean splashActivity = false;
    boolean adsDisplayed = false;
    boolean isLoadFailed = false;
    private boolean getConfigSuccess = false;
    ApInterstitialAd mInterstitialSplash = null;
    ApInterstitialAd mInterstitialSplashHigh = null;
    private int LOADING = 0;
    private int SUCCESS = 1;
    private int FAIL = 2;
    boolean loadInterHighFail = false;
    int loadInterAllSuccess = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashActivity = true;

        RemoteConfigUtils.INSTANCE.init(() -> {
            Log.d("RemoteConfigUtils", " Remote config fetch complete");
            getConfigSuccess = true;
        });
        loadingRemoteConfig();
    }

    private void loadingRemoteConfig() {
        new CountDownTimer(8000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (getConfigSuccess) {
                    checkRemoteConfigResult();
                    this.cancel();
                }
            }

            @Override
            public void onFinish() {
                if (!getConfigSuccess) {
                    checkRemoteConfigResult();
                }
            }
        }.start();
    }

    private void checkRemoteConfigResult() {
        if (!SharePreferenceUtils.getInstance(this).getSelectedLanguage()) {
            // show tutorial only one
            loadInterTutorial();
        }
        if (!SharePreferenceUtils.getInstance(this).getSelectedLanguage()) {
            loadNativeLanguage();
            loadNativeHome();
        } else {
            // load native home only
            loadNativeHome();
        }
        AppOpenManager.getInstance().loadSplashOpenAndInter(SplashActivity.class,SplashActivity.this,getResources().getString(R.string.open_lunch_high_new),getResources().getString(R.string.admob_inter_splash_new),25000,new AdCallback(){
            @Override
            public void onNextAction() {
                super.onNextAction();
                if (splashActivity) {
                    moveIntroduceActivity();
                }
            }
        });
    }

    protected void onStop() {
        super.onStop();
        splashActivity = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        splashActivity = true;
    }

    @Override
    protected void onDestroy() {
        splashActivity = false;
        super.onDestroy();
    }

    public void moveIntroduceActivity() {
        splashActivity = false;
        if (!SharePreferenceUtils.getInstance(this).getSelectedLanguage()) {
            Intent intent = new Intent(SplashActivity.this, LanguageActivity.class);
            intent.putExtra("SplashActivity", true);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Locale myLocale = new Locale(SharePreferenceUtils.getInstance(this).getSaveLanguage());
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, IntroduceActivity.class);
                    startActivity(intent);
                }
            } else {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
        finish();
    }
}
