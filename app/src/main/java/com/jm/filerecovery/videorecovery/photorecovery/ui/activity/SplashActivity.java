package com.jm.filerecovery.videorecovery.photorecovery.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ads.control.AdmobUtils;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.ads.control.SharePreferenceUtils;
import com.jm.filerecovery.videorecovery.photorecovery.ui.activity.language.LanguageActivity;

import java.util.Locale;


public class SplashActivity extends AppCompatActivity {
    private AppOpenAd.AppOpenAdLoadCallback loadCallback;
    private AppOpenAd appOpenAd = null;
    private String TAG = "SplashActivity";
    private long time = 6000;
    boolean splashActivity = false;
    boolean adsDisplayed = false;
    boolean isLoadFailed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AdmobUtils.getInstance().init(this);
        splashActivity = true;
        initOpenAdmob();
        new Handler().postDelayed(() -> {
            if (appOpenAd == null && splashActivity) {
                Log.d(TAG, "SplashActivity --------> go to category activity 6500");
                moveIntroduceActivity();
            } else if (!splashActivity) {
                isLoadFailed = true;
            }
        }, time);

    }

    private void initOpenAdmob() {
        loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {

            @Override
            public void onAdLoaded(@NonNull AppOpenAd ad) {
                appOpenAd = ad;
                Log.d(TAG, "SplashActivity --------> onAppOpenAdLoaded appOpenAd = " + appOpenAd);
                FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        SplashActivity.this.appOpenAd = null;
                        Log.d(TAG, "SplashActivity --------> onAdDismissedFullScreenContent");
                        moveIntroduceActivity();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {

                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        adsDisplayed = true;
                    }
                };
                if (splashActivity & !adsDisplayed) {
                    adsDisplayed = true;
                    appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
                    appOpenAd.show(SplashActivity.this);
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                isLoadFailed = true;
                if (splashActivity) {
                    moveIntroduceActivity();
                }
            }
        };

        AdRequest request = getAdRequest();
        AppOpenAd.load(this, getResources().getString(R.string.admob_open_splash), request, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
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
        if (!adsDisplayed && appOpenAd != null || isLoadFailed) {
            moveIntroduceActivity();
        }
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
            finish();
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
            finish();
        }
    }
}
