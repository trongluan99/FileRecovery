package com.jm.filerecovery.videorecovery.photorecovery.ui;


import android.content.Context;

import androidx.annotation.NonNull;

import com.ads.control.AdsApplication;

import java.util.Locale;


public class App extends AdsApplication {

    public void onCreate() {
        super.onCreate();
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
        }
        return Locale.ENGLISH;
    }
}