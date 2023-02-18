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


import com.ads.control.admob.Admob;
import com.ads.control.ads.AperoAd;
import com.ads.control.ads.AperoAdCallback;
import com.ads.control.ads.AperoInitCallback;
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
        if (!SharePreferenceUtils.getInstance(this).getSelectedLanguage()) {
            // show tutorial only one
            loadInterTutorial();
        }

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
        if (!SharePreferenceUtils.getInstance(this).getSelectedLanguage()) {
            loadNativeLanguage();
            loadNativeHome();
        } else {
            // load native home only
            loadNativeHome();
        }
        if (RemoteConfigUtils.INSTANCE.getOnOpenLunch().equals("on")) {
            // load Open
            initOpenAdmob();
        } else {
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
    }

    private void initOpenAdmob() {
        loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {

            @Override
            public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                SplashActivity.this.appOpenAd = appOpenAd;
                Log.d(TAG, "SplashActivity --------> onAppOpenAdLoaded appOpenAd = " + appOpenAd);
                FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        SplashActivity.this.appOpenAd = null;
                        Log.d(TAG, "SplashActivity --------> onAdDismissedFullScreenContent");
                        if (splashActivity) {
                            moveIntroduceActivity();
                        }
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        Log.d(TAG, "SplashActivity --------> onAdFailedToShowFullScreenContent");
                        checkRemoteConfigResult222222222222();
                        loadOpenFail = true;
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                    }
                };
                if (splashActivity) {
                    appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
                    appOpenAd.show(SplashActivity.this);
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.d(TAG, "SplashActivity --------> onAppOpenAdLoaded onAdFailedToLoad = " + appOpenAd);
                checkRemoteConfigResult222222222222();
                loadOpenFail = true;
            }
        };

        AdRequest request = getAdRequest();
        AppOpenAd.load(this, getResources().getString(R.string.admob_open_app_lunch), request, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
    }

    boolean loadOpenFail = false;
    private void checkRemoteConfigResult222222222222() {
        Log.d(TAG, " checkRemoteConfigResult222222222222 loadOpenFail == "+loadOpenFail);
        if(loadOpenFail) return;
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
    public AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
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
