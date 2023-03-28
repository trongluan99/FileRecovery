package com.jm.filerecovery.videorecovery.photorecovery.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtils {
    private static SharePreferenceUtils instance;
    private SharedPreferences.Editor editor;
    private Context mContext;
    private SharedPreferences pre;

    private SharePreferenceUtils(Context context) {
        this.mContext = context;
        this.pre = context.getSharedPreferences("recovery_file_share_preference", Context.MODE_PRIVATE);
        this.editor = this.pre.edit();
    }

    public static SharePreferenceUtils getInstance(Context context) {
        if (instance == null) {
            instance = new SharePreferenceUtils(context);
        }
        return instance;
    }

    public boolean getFirstRun() {
        boolean isFirst = pre.getBoolean("first_run_app", true);
        if (isFirst) {
            editor.putBoolean("first_run_app", false);
            editor.commit();
        }
        return isFirst;
    }

    public int getLanguageIndex() {
        return pre.getInt("languageindex", 0);
    }

    public void saveLanguageIndex(int language) {
        editor.putInt("languageindex", language);
        editor.commit();
    }

    public boolean getPurchase() {
        return false;
    }

    public void saveShowFullAds(boolean showFullAds) {
        editor.putBoolean("ShowFullAds", showFullAds);
        editor.commit();
    }

    public boolean getShowFullAds() {
        return pre.getBoolean("ShowFullAds", false);
    }

    public void setSaveLanguage(String language) {
        editor.putString("setSaveLanguage", language);
        editor.commit();
    }

    public String getSaveLanguage() {
        return pre.getString("setSaveLanguage", "en");
    }

    public void setSaveNameLanguage(String language) {
        editor.putString("setSaveNameLanguage", language);
        editor.commit();
    }

    public String getSaveNameLanguage() {
        return pre.getString("setSaveNameLanguage", "English");
    }

    public void setSelectedLanguage(boolean b) {
        editor.putBoolean("setSelectedLanguage", b);
        editor.commit();
    }

    public boolean getSelectedLanguage() {
        return pre.getBoolean("setSelectedLanguage", false);
    }

    public void saveLastTimeShowInter(long timeLoad) {
        editor.putLong("LastTimeShowInter", timeLoad);
        editor.commit();
    }
    public Long getLastTimeShowInter() {
        return pre.getLong("LastTimeShowInter", 0);
    }



    public boolean isStartLang(Context context) {
        return pre.getBoolean("LanguageFirst", false);
    }

}
