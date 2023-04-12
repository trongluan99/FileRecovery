package com.jm.filerecovery.videorecovery.photorecovery;

import android.content.Context;

import com.ads.control.ads.ITGAd;
import com.ads.control.ads.wrapper.ApInterstitialAd;

public class AdsConfig {
    public static ApInterstitialAd mInterstitialAdAllHigh = null;

    public static void loadInterAllHigh(Context context) {
        if (RemoteConfigUtils.INSTANCE.getOnInterAllHigh().equals("on")) {
            if (mInterstitialAdAllHigh == null) {
                mInterstitialAdAllHigh = ITGAd.getInstance().getInterstitialAds(context, context.getResources().getString(R.string.admob_inter_all_high_floor));
            }
        }
    }
}
