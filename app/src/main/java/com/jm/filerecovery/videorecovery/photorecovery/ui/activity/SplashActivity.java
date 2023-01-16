package com.jm.filerecovery.videorecovery.photorecovery.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


import com.ads.control.admob.Admob;
import com.ads.control.ads.AperoAd;
import com.ads.control.ads.AperoAdCallback;
import com.ads.control.ads.AperoInitCallback;
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
        loadInterTutorial();
    }

    private void loadingRemoteConfig() {
        new CountDownTimer(5000, 100) {
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
        if (RemoteConfigUtils.INSTANCE.getOnInterLunch().equals("on")) {
            Log.d(TAG, " checkRemoteConfigResult getOnInterSplash == on");
            AperoAdCallback adCallback = new AperoAdCallback() {
                @Override
                public void onNextAction() {
                    super.onNextAction();
                    Log.d(TAG, "onNextAction");
                    if (Admob.getInstance().getmInterstitialSplash() != null) {
                        Log.d(TAG, "onAdImpression");
                        SharePreferenceUtils.getInstance(SplashActivity.this).saveLastTimeShowInter(System.currentTimeMillis());
                    }
                    if (splashActivity) moveIntroduceActivity();
                }
            };
            AperoAd.getInstance().setInitCallback(new AperoInitCallback() {
                @Override
                public void initAdSuccess() {
                    AperoAd.getInstance().loadSplashInterstitialAds(SplashActivity.this, getResources().getString(R.string.admob_inter_splash), 10000, 500, true, adCallback);
                }
            });

        } else {
            if (splashActivity) moveIntroduceActivity();
        }
    }

    protected void onStop() {
        super.onStop();
        splashActivity = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        splashActivity = true;
//        if (!adsDisplayed && appOpenAd != null || isLoadFailed) {
//            moveIntroduceActivity();
//        }
    }

    @Override
    protected void onDestroy() {
        splashActivity = false;
        super.onDestroy();
    }

    public void moveIntroduceActivity() {
        splashActivity = false;
        if(!SharePreferenceUtils.getInstance(this).getSelectedLanguage()) {
            Intent intent = new Intent(SplashActivity.this, LanguageActivity.class);
            intent.putExtra("SplashActivity",true);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Locale myLocale = new Locale(SharePreferenceUtils.getInstance(this).getSaveLanguage());
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            Intent intent = new Intent(SplashActivity.this, IntroduceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        finish();
    }
}
