package com.jm.filerecovery.videorecovery.photorecovery;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ads.control.ads.AperoAd;
import com.ads.control.ads.AperoAdCallback;
import com.ads.control.ads.wrapper.ApAdError;
import com.ads.control.ads.wrapper.ApInterstitialAd;
import com.ads.control.ads.wrapper.ApNativeAd;

public abstract class BaseActivity extends AppCompatActivity {
    protected static ApInterstitialAd mInterstitialAdTutorial = null;
    protected static ApInterstitialAd mInterstitialAdClickHome = null;
    protected static ApNativeAd nativeAdViewLanguage = null;
    protected static ApNativeAd nativeAdViewHome = null;
    protected static PreLoadNativeListener preLoadNativeListener = null;
    protected static boolean loadNativeLanguageSuccess = false;
//    protected static ApInterstitialAd mInterstitialAdItem = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TuanPA38","BaseActivity onCreate");
        try {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        } catch (Exception e){

        }
    }

    protected void loadInterTutorial(){
        Log.d("TuanPA38","BaseActivity loadInterTutorial");
        if(mInterstitialAdTutorial == null){
            mInterstitialAdTutorial = AperoAd.getInstance().getInterstitialAds(this,getResources().getString(R.string.admob_inter_tutorial));
        }
    }

    protected void loadInterClickHome(){
        Log.d("TuanPA38","BaseActivity loadInterClickHome");
        if(mInterstitialAdClickHome == null){
            mInterstitialAdClickHome = AperoAd.getInstance().getInterstitialAds(this,getResources().getString(R.string.admob_inter_click_home));
        }
    }

    protected void loadNativeLanguage(){
        Log.d("TuanPA38","BaseActivity loadNativeLanguage");
        if(nativeAdViewLanguage == null){
            if (RemoteConfigUtils.INSTANCE.getOnNativeLanguage().equals("on")) {
                AperoAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.admob_native_language),R.layout.custom_native_full_size, new AperoAdCallback(){
                    @Override
                    public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                        super.onNativeAdLoaded(nativeAd);
                        nativeAdViewLanguage = nativeAd;
                        loadNativeLanguageSuccess = true;
                        if(preLoadNativeListener != null)    preLoadNativeListener.onLoadNativeLanguageSuccess();
                        Log.d("TuanPA38","BaseActivity onNativeAdLoaded nativeAdViewLanguage = "+nativeAdViewLanguage);
                    }

                    @Override
                    public void onAdFailedToLoad(@Nullable ApAdError adError) {
                        super.onAdFailedToLoad(adError);
                        Log.d("TuanPA38","BaseActivity loadNativeLanguage onAdFailedToLoad");
                        if(preLoadNativeListener != null)    preLoadNativeListener.onLoadNativeLanguageFail();
                    }
                });
            }
        }
    }

    protected void loadNativeHome(){
        Log.d("TuanPA38","BaseActivity loadNativeHome");
        if(nativeAdViewHome == null){
            if (RemoteConfigUtils.INSTANCE.getOnNativeLanguage().equals("on")) {
                AperoAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.admob_native_home),R.layout.custom_native_no_media, new AperoAdCallback(){
                    @Override
                    public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                        super.onNativeAdLoaded(nativeAd);
                        nativeAdViewHome = nativeAd;
                        loadNativeLanguageSuccess = true;

                        if(preLoadNativeListener != null)   preLoadNativeListener.onLoadNativeHomeSuccess();
                        Log.d("TuanPA38","BaseActivity onNativeAdLoaded nativeAdViewHome = "+nativeAdViewHome);
                    }

                    @Override
                    public void onAdFailedToLoad(@Nullable ApAdError adError) {
                        super.onAdFailedToLoad(adError);
                        Log.d("TuanPA38","BaseActivity loadNativeHome onAdFailedToLoad");
                        if(preLoadNativeListener != null)  preLoadNativeListener.onLoadNativeHomeFail();
                    }
                });
            }
        }
    }

    public static PreLoadNativeListener getPreLoadNativeListener() {
        return preLoadNativeListener;
    }

    public static void setPreLoadNativeListener(PreLoadNativeListener preLoadNativeListener) {
        BaseActivity.preLoadNativeListener = preLoadNativeListener;
    }

    public interface PreLoadNativeListener {
        void onLoadNativeLanguageSuccess();
        void onLoadNativeLanguageFail();

        void onLoadNativeHomeSuccess();
        void onLoadNativeHomeFail();
    }

}
