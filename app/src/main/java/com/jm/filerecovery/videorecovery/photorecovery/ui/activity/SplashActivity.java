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
        if (!SharePreferenceUtils.getInstance(this).getSelectedLanguage()) {
            // show tutorial only one
            loadInterTutorial();
        }
    }

    private void loadingRemoteConfig() {
        new CountDownTimer(6500, 100) {
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

        if (RemoteConfigUtils.INSTANCE.getOnOpenHighSplash3Id().equals("on")) {
            Log.d(TAG, "SplashActivity --------> getOnOpenHighSplash3Id");
            AppOpenManager.getInstance().loadSplashOpenHighFloor(SplashActivity.class, this,
                    getResources().getString(R.string.open_lunch_high), getResources().getString(R.string.open_lunch_medium), getResources().getString(R.string.open_lunch_normal), 25000, new AdCallback(){
                        @Override
                        public void onNextAction() {
                            super.onNextAction();
                            if (splashActivity) {
                                moveIntroduceActivity();
                            }
                        }
                    });
        } else if (RemoteConfigUtils.INSTANCE.getOnOpenHighSplash2Id().equals("on")){
            Log.d(TAG, "SplashActivity --------> getOnOpenHighSplash2Id");
            AppOpenManager.getInstance().loadSplashOpenHighFloor(SplashActivity.class, this,
                    getResources().getString(R.string.open_lunch_high), "", getResources().getString(R.string.open_lunch_normal), 25000, new AdCallback(){
                        @Override
                        public void onNextAction() {
                            super.onNextAction();
                            if (splashActivity) {
                                moveIntroduceActivity();
                            }
                        }
                    });
        } else {
            initOpenAdmob();
        }

//        if (RemoteConfigUtils.INSTANCE.getOnOpenLunch().equals("on")) {
//            // load Open
//            initOpenAdmob();
//            loadInterAll();
//            return;
//        }

//        /**
//         *  request load inter lunch
//         */
//        if (RemoteConfigUtils.INSTANCE.getOnInterLunch().equals("on")) {
//            AperoAdCallback adCallback = new AperoAdCallback() {
//                @Override
//                public void onNextAction() {
//                    super.onNextAction();
//                    Log.d(TAG, "onNextAction");
//                    if (Admob.getInstance().getmInterstitialSplash() != null) {
//                        Log.d(TAG, "onAdImpression");
//                        SharePreferenceUtils.getInstance(SplashActivity.this).saveLastTimeShowInter(System.currentTimeMillis());
//                    }
//                    if (splashActivity) moveIntroduceActivity();
//                }
//            };
//            AperoAd.getInstance().setInitCallback(new AperoInitCallback() {
//                @Override
//                public void initAdSuccess() {
//                    AperoAd.getInstance().loadSplashInterstitialAds(SplashActivity.this, getResources().getString(R.string.admob_inter_splash), 10000, 500, true, adCallback);
//                }
//            });
//
//        } else {
//            if (splashActivity) moveIntroduceActivity();
//        }
    }

    /**
     * load 2 id inter
     * ưu tiên load high trước
     * -> load high thành công -> show
     * -> load high thất bại -> tạo 1 timer để lắng nghe xem inter_all load -> load thành công show inter và cancel timer
     */

    private void loadInterAll() {
        if (mInterstitialSplash == null) {
            mInterstitialSplash = AperoAd.getInstance().getInterstitialAds(this, getResources().getString(R.string.admob_inter_splash), new AperoAdCallback() {

                @Override
                public void onInterstitialLoad(@Nullable ApInterstitialAd interstitialAd) {
                    super.onInterstitialLoad(interstitialAd);
                    Log.d(TAG, "loadInterAll onInterstitialLoad");
                    loadInterAllSuccess = SUCCESS;
                }

                @Override
                public void onAdFailedToLoad(@Nullable ApAdError adError) {
                    super.onAdFailedToLoad(adError);
                    Log.d(TAG, "loadInterAll onAdFailedToLoad");
                    loadInterAllSuccess = FAIL;
                }

                @Override
                public void onAdFailedToShow(@Nullable ApAdError adError) {
                    super.onAdFailedToShow(adError);
                    Log.d(TAG, "loadInterAll onAdFailedToShow");
                    loadInterAllSuccess = FAIL;
                }

            });
        }
    }

    private void loadInterHighFloor() {
        if (mInterstitialSplashHigh == null) {
            mInterstitialSplashHigh = AperoAd.getInstance().getInterstitialAds(this, getResources().getString(R.string.admob_inter_splash_high), new AperoAdCallback() {

                @Override
                public void onInterstitialLoad(@Nullable ApInterstitialAd interstitialAd) {
                    super.onInterstitialLoad(interstitialAd);
                    Log.d(TAG, " loadInterHighFloor onInterstitialLoad");
                    handleAdsAfterLoadInterHigh();
                }

                @Override
                public void onAdFailedToLoad(@Nullable ApAdError adError) {
                    super.onAdFailedToLoad(adError);
                    loadInterHighFail = true;
                    handleAdsAfterLoadInterHigh();
                    Log.d(TAG, "loadInterHighFloor onAdFailedToLoad");
                }

                @Override
                public void onAdFailedToShow(@Nullable ApAdError adError) {
                    super.onAdFailedToShow(adError);
                    loadInterHighFail = true;
                    handleAdsAfterLoadInterHigh();
                    Log.d(TAG, "loadInterHighFloor onAdFailedToShow");
                }

            });
        }
    }

    /**
     * load 2 id inter
     * ưu tiên load high trước
     * -> load high thành công -> show
     * -> load high thất bại -> tạo 1 timer để lắng nghe xem inter_all load -> load thành công show inter và cancel timer
     */
    boolean handleAdsAfterLoadInterHigh = false; // param is to call method only once

    private void handleAdsAfterLoadInterHigh() {
        if (handleAdsAfterLoadInterHigh) {
            return;
        }
        handleAdsAfterLoadInterHigh = true;

        if (mInterstitialSplashHigh != null) {
            if (mInterstitialSplashHigh.isReady()) {
                AperoAd.getInstance().forceShowInterstitial(this, mInterstitialSplashHigh, new AperoAdCallback() {
                    @Override
                    public void onNextAction() {
                        Log.i("TuanPA38", " handleAdsAfterLoadInterHigh onNextAction: start content and finish main");
                        if (splashActivity) {
                            moveIntroduceActivity();
                        }
                    }

                    @Override
                    public void onAdFailedToShow(@Nullable ApAdError adError) {
                        super.onAdFailedToShow(adError);
                        Log.i("TuanPA38", "handleAdsAfterLoadInterHigh onAdFailedToShow:" + adError.getMessage());
                        loadInterHighFail = true;
                        listenInterAllLoading();
                    }

                }, true);
            } else {
                listenInterAllLoading();
            }
        } else {
            listenInterAllLoading();
        }
    }

   boolean listenInterAllLoad = false; // đảm bảo phương thức này chỉ thực hiện 1 lần nhưng được gọi ở các sự kiện load lỗi ads high
    private void listenInterAllLoading() {
        if(listenInterAllLoad) return;
        listenInterAllLoad = true;
        // tạo timer
        new CountDownTimer(15000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (loadInterAllSuccess == FAIL) {
                    if (splashActivity) {
                        moveIntroduceActivity();
                    }
                    cancel();
                } else if (loadInterAllSuccess == SUCCESS) {
                    showInterAll();
                    cancel();
                }
            }

            @Override
            public void onFinish() {
                if (splashActivity) {
                    moveIntroduceActivity();
                }
            }
        }.start();
    }

    private void showInterAll() {
        if (mInterstitialSplash != null) {
            if (mInterstitialSplash.isReady()) {
                AperoAd.getInstance().forceShowInterstitial(this, mInterstitialSplash, new AperoAdCallback() {
                    @Override
                    public void onNextAction() {
                        Log.i("TuanPA38", "showInterAll onNextAction: start content and finish main");
                        if (splashActivity) {
                            moveIntroduceActivity();
                        }
                    }

                    @Override
                    public void onAdFailedToShow(@Nullable ApAdError adError) {
                        super.onAdFailedToShow(adError);
                        if (splashActivity) {
                            moveIntroduceActivity();
                        }
                        Log.i("TuanPA38", "showInterAll onAdFailedToShow:" + adError.getMessage());
                    }

                }, true);
            } else {
                if (splashActivity) {
                    moveIntroduceActivity();
                }
            }
        } else {
            if (splashActivity) {
                moveIntroduceActivity();
            }
        }
    }

    private void initOpenAdmob() {
        loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {

            @Override
            public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                SplashActivity.this.appOpenAd = appOpenAd;
                Log.d(TAG, "initOpenAdmob --------> onAppOpenAdLoaded appOpenAd = " + appOpenAd);
                FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        SplashActivity.this.appOpenAd = null;
                        Log.d(TAG, "initOpenAdmob --------> onAdDismissedFullScreenContent");
                        if (splashActivity) {
                            moveIntroduceActivity();
                        }
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        Log.d(TAG, "initOpenAdmob --------> onAdFailedToShowFullScreenContent");
                        if (splashActivity) {
                            moveIntroduceActivity();
                        }
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
                Log.d(TAG, "initOpenAdmob --------> onAppOpenAdLoaded onAdFailedToLoad = " + appOpenAd);
                if (splashActivity) {
                    moveIntroduceActivity();
                }
            }
        };
        AdRequest request = getAdRequest();
        AppOpenAd.load(this, getResources().getString(R.string.open_lunch_normal), request, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
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
