package com.jm.filerecovery.videorecovery.photorecovery.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SystemUtil {
    private static Locale myLocale;

    public static boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    // Load lại ngôn ngữ đã lưu và thay đổi chúng
    public static void setLocale(Context context) {
        String language = getPreLanguage(context);
        if (language.equals("")) {
            Configuration config = new Configuration();
            Locale locale = Locale.getDefault();
            Locale.setDefault(locale);
            config.locale = locale;
            context.getResources()
                    .updateConfiguration(config, context.getResources().getDisplayMetrics());
        } else {
            changeLang(language, context);
        }
    }

    // method phục vụ cho việc thay đổi ngôn ngữ.
    public static void changeLang(String lang, Context context) {
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        saveLocale(context, lang);
        Locale.setDefault(myLocale);
        Configuration config = new Configuration();
        config.locale = myLocale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    public static void saveLocale(Context context, String lang) {
        setPreLanguage(context, lang);
    }

    @SuppressLint("ObsoleteSdkInt")
    public static String getPreLanguage(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE);
        Locale.getDefault().getDisplayLanguage();
        String lang;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            lang = Resources.getSystem().getConfiguration().getLocales().get(0).getLanguage();
        } else {
            lang = Resources.getSystem().getConfiguration().locale.getLanguage();
        }
        if (!getLanguageApp().contains(lang)) {
            return preferences.getString("KEY_LANGUAGE", "en");
        } else {
            return preferences.getString("KEY_LANGUAGE", lang);
        }
    }

    public static void setPreLanguage(Context context, String language) {
        if (language == null || language.equals("")) {
            return;
        } else {
            SharedPreferences preferences = context.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE);
            preferences.edit().putString("KEY_LANGUAGE", language).apply();
        }
    }

    public static List<String> getLanguageApp() {
        List<String> languages = new ArrayList<>();
        languages.add("ar"); // Arabic
        languages.add("hi"); // Hindi
        languages.add("es"); // Spanish
        languages.add("cs"); // Czech
        languages.add("de"); // Germany
        languages.add("en"); // English
        languages.add("es"); // Spanish
        languages.add("fil");// Filipino
        languages.add("fr"); // French
        languages.add("hi"); // Hindi
        languages.add("hr"); // Croatian
        languages.add("in"); // indonesian
        languages.add("it"); // italian
        languages.add("ja"); // japanese
        languages.add("ko"); // korean
        languages.add("ms"); // Malay
        languages.add("nl"); // Dutch
        languages.add("pl"); // Polish
        languages.add("pt"); // Portugal
        languages.add("ru"); // Russian
        languages.add("sr"); // Serbian
        languages.add("sv"); // Swedish
        languages.add("tr"); // Turkish
        languages.add("vi"); // Vietnamese
        languages.add("ml"); // Malayalam
        languages.add("th"); // Thai
        languages.add("zh"); // Chinese
        languages.add("zh-HK"); // Hong Kong
        languages.add("zh-TW"); // Taiwan
        return languages;
    }
}