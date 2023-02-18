package com.jm.filerecovery.videorecovery.photorecovery;
import com.ads.control.admob.Admob;
import com.ads.control.ads.AperoAd;
import com.ads.control.ads.AperoAdConfig;
import com.ads.control.application.AdsMultiDexApplication;
import com.jm.filerecovery.videorecovery.photorecovery.ui.activity.IntroduceActivity;
import com.jm.filerecovery.videorecovery.photorecovery.ui.activity.SplashActivity;


public class MyApplication extends AdsMultiDexApplication {

    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

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
        aperoAdConfig.setMediationProvider(AperoAdConfig.PROVIDER_ADMOB);
        aperoAdConfig.setVariant(BuildConfig.build_debug);
        aperoAdConfig.setIdAdResume(getResources().getString(R.string.admob_open_app_resume));
        listTestDevice.add("33BE2250B43518CCDA7DE426D04EE231");
        aperoAdConfig.setListDeviceTest(listTestDevice);
        AperoAd.getInstance().init(this, aperoAdConfig, false);
        Admob.getInstance().setDisableAdResumeWhenClickAds(true);
        Admob.getInstance().setOpenActivityAfterShowInterAds(true);
        com.ads.control.admob.AppOpenManager.getInstance().disableAppResumeWithActivity(SplashActivity.class);
        com.ads.control.admob.AppOpenManager.getInstance().disableAppResumeWithActivity(IntroduceActivity.class);
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