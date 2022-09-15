package com.ads.control;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.akexorcist.localizationactivity.ui.LocalizationApplication;
import com.google.android.gms.ads.MobileAds;

public abstract class AdsApplication extends LocalizationApplication {

    private static AppOpenManager appOpenManager;

    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(
                this,
                initializationStatus -> {
                });
        appOpenManager = new AppOpenManager(this);
    }

    @Override
    protected void attachBaseContext(@NonNull Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
