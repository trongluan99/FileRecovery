//package com.jm.filerecovery.videorecovery.photorecovery.utils;
//
//import android.app.Activity;
//import android.app.Application;
//import android.os.Bundle;
//import android.util.Log;
//
//import androidx.lifecycle.LifecycleObserver;
//import androidx.lifecycle.OnLifecycleEvent;
//import androidx.lifecycle.ProcessLifecycleOwner;
//import com.google.android.gms.ads.AdError;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.FullScreenContentCallback;
//import com.google.android.gms.ads.LoadAdError;
//import com.google.android.gms.ads.appopen.AppOpenAd;
//import com.jm.filerecovery.videorecovery.photorecovery.ui.activity.SplashActivity;
//
//import java.util.Date;
//
//import static androidx.lifecycle.Lifecycle.Event.ON_START;
//
//public class AppOpenManager implements Application.ActivityLifecycleCallbacks, LifecycleObserver {
//
//    private static final String LOG_TAG = "AppOpenManager";
//    private AppOpenAd appOpenAd = null;
//    private static boolean isShowingAd = false;
//    private long loadTimeAdsAfter4Hours = 0;
//    private long timeLoad = 0;
//    private long TimeReload = 30 * 1000;
//    private AppOpenAd.AppOpenAdLoadCallback loadCallback;
//    private final AdsApplication myApplication;
//    private Activity currentActivity;
//    private boolean isLoadingAd = false;
//    /**
//     * Constructor
//     */
//    public AppOpenManager(AdsApplication myApplication) {
//        this.myApplication = myApplication;
//        this.myApplication.registerActivityLifecycleCallbacks(this);
//        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
//    }
//
//    @OnLifecycleEvent(ON_START)
//    public void onStart() {
//        if (currentActivity instanceof SplashActivity) {
//            if (!isAdAvailable()) {
//                Log.d(LOG_TAG, "Application -------->  appOpenAd ===>>> " + appOpenAd);
//                fetchAd();
//            }
//        } else {
//            showOpenAds();
//        }
//        Log.d(LOG_TAG, "onStart");
//    }
//
//    private void showOpenAds() {
////        if (SharePreferenceUtils.getInstance(currentActivity).getPurchase()) return;
//        Log.d("AppOpenManager", " showOpenAds");
//        if ((timeLoad + TimeReload) < System.currentTimeMillis()) {
//            Log.d("AppOpenManager", " (timeLoad + TimeReload) < System.currentTimeMillis() ");
//            showAdIfAvailable();
//            timeLoad = System.currentTimeMillis();
//        } else {
//            Log.d("AppOpenManager", " (timeLoad + TimeReload) > System.currentTimeMillis() ");
//        }
//    }
//
//    /**
//     * Shows the ad if one isn't already showing.
//     */
//    public void showAdIfAvailable() {
//        if (!isShowingAd && isAdAvailable()) {
//            boolean isShowInter = SharePreferenceUtils.getInstance(myApplication).getShowFullAds();
//            Log.d(LOG_TAG, "Will show ad.");
//            FullScreenContentCallback fullScreenContentCallback =
//                    new FullScreenContentCallback() {
//                        @Override
//                        public void onAdDismissedFullScreenContent() {
//                            // Set the reference to null so isAdAvailable() returns false.
//                            AppOpenManager.this.appOpenAd = null;
//                            isShowingAd = false;
//                            fetchAd();
//                        }
//
//                        @Override
//                        public void onAdFailedToShowFullScreenContent(AdError adError) {
//                        }
//
//                        @Override
//                        public void onAdShowedFullScreenContent() {
//                            isShowingAd = true;
//                        }
//                    };
//
//            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
//            if (currentActivity != null && !isShowInter) {
//                if (currentActivity instanceof SplashActivity) {
//                    Log.d("AppOpenManager", " currentActivity instanceof SplashActivity");
//                } else {
//                    Log.d("AppOpenManager", "  appOpenAd.show(currentActivity)");
//                    appOpenAd.show(currentActivity);
//                }
//            }
//
//        } else {
//            Log.d(LOG_TAG, "Can not show ad.");
//            fetchAd();
//        }
//    }
//
//    /**
//     * Creates and returns ad request.
//     */
//    private AdRequest getAdRequest() {
//        return new AdRequest.Builder().build();
//    }
//
//    private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
//        long dateDifference = (new Date()).getTime() - this.loadTimeAdsAfter4Hours;
//        long numMilliSecondsPerHour = 3600000;
//        return (dateDifference < (numMilliSecondsPerHour * numHours));
//    }
//
//    /**
//     * Request an ad
//     */
//    public void fetchAd() {
////        if (SharePreferenceUtils.getInstance(currentActivity).getPurchase()) return;
//        if (isAdAvailable()|| isLoadingAd) {
//            return;
//        }
//
//        loadCallback =
//                new AppOpenAd.AppOpenAdLoadCallback() {
//                    /**
//                     * Called when an app open ad has loaded.
//                     *
//                     * @param ad the loaded app open ad.
//                     */
//                    @Override
//                    public void onAdLoaded(AppOpenAd ad) {
//                        AppOpenManager.this.appOpenAd = ad;
//                        AppOpenManager.this.isLoadingAd = false;
//                        AppOpenManager.this.loadTimeAdsAfter4Hours = (new Date()).getTime();
//                    }
//
//                    /**
//                     * Called when an app open ad has failed to load.
//                     *
//                     * @param loadAdError the error.
//                     */
//                    @Override
//                    public void onAdFailedToLoad(LoadAdError loadAdError) {
//                        // Handle the error.
//                    }
//
//                };
//        isLoadingAd = true;
//        AdRequest request = getAdRequest();
//        AppOpenAd.load(
//                myApplication, myApplication.getString(com.ads.control.R.string.admob_open_app), request,
//                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
//    }
//
//    /**
//     * Utility method that checks if ad exists and can be shown.
//     */
//    public boolean isAdAvailable() {
//        return appOpenAd != null
//                && wasLoadTimeLessThanNHoursAgo(4);
//    }
//
//    @Override
//    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//    }
//
//    @Override
//    public void onActivityStarted(Activity activity) {
//        currentActivity = activity;
//    }
//
//    @Override
//    public void onActivityResumed(Activity activity) {
//        currentActivity = activity;
//    }
//
//    @Override
//    public void onActivityStopped(Activity activity) {
//    }
//
//    @Override
//    public void onActivityPaused(Activity activity) {
//    }
//
//    @Override
//    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
//    }
//
//    @Override
//    public void onActivityDestroyed(Activity activity) {
//        currentActivity = null;
//    }
//}
