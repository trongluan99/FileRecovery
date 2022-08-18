package com.jm.filerecovery.videorecovery.photorecovery.ui;


import com.ads.control.AdsApplication;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.jm.filerecovery.videorecovery.photorecovery.utilts.AppOpenManager;


public class AnGApp extends AdsApplication {
    private static AppOpenManager appOpenManager;

    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        appOpenManager = new AppOpenManager(this);

    }
}