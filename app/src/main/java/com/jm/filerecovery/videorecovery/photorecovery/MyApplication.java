package com.jm.filerecovery.videorecovery.photorecovery;

import android.content.res.Resources;
import android.os.Build;

import com.ads.control.admob.Admob;
import com.ads.control.ads.ITGAd;
import com.ads.control.application.AdsMultiDexApplication;
import com.ads.control.config.AdjustConfig;
import com.ads.control.config.ITGAdConfig;
import com.jm.filerecovery.videorecovery.photorecovery.model.LanguageModel;
import com.jm.filerecovery.videorecovery.photorecovery.ui.activity.SplashActivity;
import com.jm.filerecovery.videorecovery.photorecovery.utils.SystemUtil;
import com.mbridge.msdk.MBridgeConstans;
import com.mbridge.msdk.MBridgeSDK;
import com.mbridge.msdk.out.MBridgeSDKFactory;

import java.util.ArrayList;
import java.util.List;


public class MyApplication extends AdsMultiDexApplication {

    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    public String ADJUST_TOKEN = "isb4bnoaf18g";

    public static void setInstance(MyApplication instance) {
        MyApplication.instance = instance;
    }

    public void onCreate() {
        super.onCreate();
        configAds();
        if (instance == null) {
            setInstance(MyApplication.this);
        }
    }

    private void configAds() {
        String environment = ITGAdConfig.ENVIRONMENT_DEVELOP;
        if(BuildConfig.build_debug){
            environment = ITGAdConfig.ENVIRONMENT_DEVELOP;
        } else {
            environment = ITGAdConfig.ENVIRONMENT_PRODUCTION;
        }
        itgAdConfig = new ITGAdConfig(this, ITGAdConfig.PROVIDER_ADMOB, environment);
        itgAdConfig.setIdAdResume(getResources().getString(R.string.admob_open_app_resume));
        listTestDevice.add("33BE2250B43518CCDA7DE426D04EE231");
        itgAdConfig.setListDeviceTest(listTestDevice);

        AdjustConfig adjustConfig = new AdjustConfig(true, ADJUST_TOKEN);
        itgAdConfig.setAdjustConfig(adjustConfig);
        itgAdConfig.setFacebookClientToken(getResources().getString(R.string.facebook_client_token));
        ITGAd.getInstance().init(this, itgAdConfig, false);
        Admob.getInstance().setDisableAdResumeWhenClickAds(true);
        Admob.getInstance().setOpenActivityAfterShowInterAds(true);
        com.ads.control.admob.AppOpenManager.getInstance().disableAppResumeWithActivity(SplashActivity.class);
        com.ads.control.admob.AppOpenManager.getInstance().disableAppResumeWithActivity(TutorialScreenITGActivity.class);

        MBridgeSDK sdk = MBridgeSDKFactory.getMBridgeSDK();
        sdk.setConsentStatus(this, MBridgeConstans.IS_SWITCH_ON);

        Admob.getInstance().setAppLovin(true);
        Admob.getInstance().setColony(true);
        Admob.getInstance().setFan(true);
        Admob.getInstance().setVungle(true);
    }


    public LanguageModel getLanguage() {
        LanguageModel languageModel = null;
        String lang = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            lang = Resources.getSystem().getConfiguration().getLocales().get(0).getLanguage();
        } else {
            lang = Resources.getSystem().getConfiguration().locale.getLanguage();
        }
        String key = "";
        if (!SystemUtil.getLanguageApp().contains(lang)) {
            key = "";
        } else {
            key = lang;
        }
        for (LanguageModel model : getListLanguageApp()) {
            if (key.equals(model.getId())) {
                languageModel = model;
                break;
            }
        }
        return languageModel;
    }

    public List<LanguageModel> getListLanguageApp() {
        ArrayList<LanguageModel> lists = new ArrayList();
        lists.add(new LanguageModel("Arabic", "ar", false, R.drawable.img_language_ar));
        lists.add(new LanguageModel("Czech", "cs", false, R.drawable.img_languages_czech));
        lists.add(new LanguageModel("German", "de", false, R.drawable.img_language_de));
        lists.add(new LanguageModel("Spanish", "es", false, R.drawable.img_language_spanish));
        lists.add(new LanguageModel("Filipino", "fil", false, R.drawable.img_languages_filipino));
        lists.add(new LanguageModel("French", "fr", false, R.drawable.img_languages_french));
        lists.add(new LanguageModel("Hindi", "hi", false, R.drawable.img_languages_hindi));
        lists.add(new LanguageModel("Croatian", "hr", false, R.drawable.img_languages_croatian));
        lists.add(new LanguageModel("Indonesian", "in", false, R.drawable.img_language_indo));
        lists.add(new LanguageModel("Italian", "it", false, R.drawable.img_language_it));
        lists.add(new LanguageModel("Japanese", "ja", false, R.drawable.img_language_japan));
        lists.add(new LanguageModel("Korean", "ko", false, R.drawable.img_language_korean));
        lists.add(new LanguageModel("Malayalam", "ml", false, R.drawable.img_languages_malayalam));
        lists.add(new LanguageModel("Malay", "ms", false, R.drawable.img_language_ms));
        lists.add(new LanguageModel("Polish", "pl", false, R.drawable.img_languages_polish));
        lists.add(new LanguageModel("Portuguese", "pt", false, R.drawable.img_language_pt));
        lists.add(new LanguageModel("Russian", "ru", false, R.drawable.img_language_ru));
        lists.add(new LanguageModel("Serbian", "sr", false, R.drawable.img_languages_serbian));
        lists.add(new LanguageModel("Swedish", "sv", false, R.drawable.img_languages_swedish));
        lists.add(new LanguageModel("Thai", "th", false, R.drawable.img_languages_thai));
        lists.add(new LanguageModel("Turkish", "tr", false, R.drawable.img_langguages_turkish));
        lists.add(new LanguageModel("Vietnamese", "vi", false, R.drawable.img_language_vi));
        lists.add(new LanguageModel("Dutch", "nl", false, R.drawable.img_languages_dutch));
        lists.add(new LanguageModel("English", "en", false, R.drawable.img_language_en));
        lists.add(new LanguageModel("Chinese", "zh", false, R.drawable.img_language_china));
        lists.add(new LanguageModel("Chinese (Hong Kong)", "zh-HK", false, R.drawable.img_languages_hong_kong));
        lists.add(new LanguageModel("Taiwan", "zh-TW", false, R.drawable.img_languages_taiwan));
        return lists;
    }

//    @NonNull
//    @Override
//    public Locale getDefaultLanguage(@NonNull Context context) {
//        String currentLanguage = context.getResources().getConfiguration().locale.getLanguage();
//        if ("en".equals(currentLanguage)) {
//            return context.getResources().getConfiguration().locale;
//        } else if ("pt".equals(currentLanguage)) {
//            return context.getResources().getConfiguration().locale;
//        } else if ("vi".equals(currentLanguage)) {
//            return context.getResources().getConfiguration().locale;
//        } else if ("ru".equals(currentLanguage)) {
//            return context.getResources().getConfiguration().locale;
//        } else if ("fr".equals(currentLanguage)) {
//            return context.getResources().getConfiguration().locale;
//        } else if ("ar".equals(currentLanguage)) {
//            return context.getResources().getConfiguration().locale;
//        } else if ("es".equals(currentLanguage)) {
//            return context.getResources().getConfiguration().locale;
//        } else if ("de".equals(currentLanguage)) {
//            return context.getResources().getConfiguration().locale;
//        } else if ("hi".equals(currentLanguage)) {
//            return context.getResources().getConfiguration().locale;
//        } else if ("in".equals(currentLanguage)) {
//            return context.getResources().getConfiguration().locale;
//        } else if ("it".equals(currentLanguage)) {
//            return context.getResources().getConfiguration().locale;
//        } else if ("ja".equals(currentLanguage)) {
//            return context.getResources().getConfiguration().locale;
//        } else if ("ko".equals(currentLanguage)) {
//            return context.getResources().getConfiguration().locale;
//        } else if ("ml".equals(currentLanguage)) {
//            return context.getResources().getConfiguration().locale;
//        } else if ("th".equals(currentLanguage)) {
//            return context.getResources().getConfiguration().locale;
//        } else if ("zh".equals(currentLanguage)) {
//            return context.getResources().getConfiguration().locale;
//        } else if ("zh-rHK".equals(currentLanguage)) {
//            return context.getResources().getConfiguration().locale;
//        } else if ("zh-rTW".equals(currentLanguage)) {
//            return context.getResources().getConfiguration().locale;
//        }
//        return Locale.ENGLISH;
//    }
}