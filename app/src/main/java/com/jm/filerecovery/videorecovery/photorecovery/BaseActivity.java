package com.jm.filerecovery.videorecovery.photorecovery;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ads.control.ads.AperoAd;
import com.ads.control.ads.wrapper.ApInterstitialAd;

public abstract class BaseActivity extends AppCompatActivity {
    protected static ApInterstitialAd mInterstitialAdTutorial = null;
    protected static ApInterstitialAd mInterstitialAdClickHome = null;
//    protected static ApInterstitialAd mInterstitialAdItem = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TuanPA38","BaseActivity onCreate mInterstitialAdTutorial = "+mInterstitialAdTutorial+" mInterstitialAdClickHome = "+mInterstitialAdClickHome);
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

//    protected void loadInterItem(){
//        Log.d("TuanPA38","BaseActivity loadInterItem");
//        if(mInterstitialAdItem == null){
//            mInterstitialAdItem = AperoAd.getInstance().getInterstitialAds(this,getResources().getString(R.string.admob_inter_click_item));
//        }
//    }
}
