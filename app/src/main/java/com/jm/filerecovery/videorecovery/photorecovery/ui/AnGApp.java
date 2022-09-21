package com.jm.filerecovery.videorecovery.photorecovery.ui;


import android.content.Context;

import androidx.annotation.NonNull;

import com.ads.control.AdsApplication;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.jm.filerecovery.videorecovery.photorecovery.utilts.AppOpenManager;

import java.util.Locale;


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

    @NonNull
    @Override
    public Locale getDefaultLanguage(@NonNull Context context) {
        String currentLanguage = context.getResources().getConfiguration().locale.getLanguage();
        if ("en".equals(currentLanguage)) {
            return context.getResources().getConfiguration().locale;
        } else if ("pt".equals(currentLanguage)) {
            return context.getResources().getConfiguration().locale;
        } else if ("vi".equals(currentLanguage)) {
            return context.getResources().getConfiguration().locale;
        } else if ("ru".equals(currentLanguage)) {
            return context.getResources().getConfiguration().locale;
        } else if ("fr".equals(currentLanguage)) {
            return context.getResources().getConfiguration().locale;
        } else if ("ar".equals(currentLanguage)) {
            return context.getResources().getConfiguration().locale;
        } else if ("es".equals(currentLanguage)) {
            return context.getResources().getConfiguration().locale;
        } else if ("de".equals(currentLanguage)) {
            return context.getResources().getConfiguration().locale;
        } else if ("hi".equals(currentLanguage)) {
            return context.getResources().getConfiguration().locale;
        } else if ("in".equals(currentLanguage)) {
            return context.getResources().getConfiguration().locale;
        } else if ("it".equals(currentLanguage)) {
            return context.getResources().getConfiguration().locale;
        } else if ("ja".equals(currentLanguage)) {
            return context.getResources().getConfiguration().locale;
        } else if ("ko".equals(currentLanguage)) {
            return context.getResources().getConfiguration().locale;
        } else if ("ml".equals(currentLanguage)) {
            return context.getResources().getConfiguration().locale;
        } else if ("th".equals(currentLanguage)) {
            return context.getResources().getConfiguration().locale;
        } else if ("zh".equals(currentLanguage)) {
            return context.getResources().getConfiguration().locale;
        }
        return Locale.ENGLISH;
    }
}