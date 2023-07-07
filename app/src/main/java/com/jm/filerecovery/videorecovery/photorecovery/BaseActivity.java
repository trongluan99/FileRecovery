package com.jm.filerecovery.videorecovery.photorecovery;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ads.control.ads.ITGAd;
import com.ads.control.ads.ITGAdCallback;
import com.ads.control.ads.wrapper.ApAdError;
import com.ads.control.ads.wrapper.ApInterstitialAd;
import com.ads.control.ads.wrapper.ApNativeAd;

public abstract class BaseActivity extends AppCompatActivity {
    protected static ApInterstitialAd mInterstitialAdTutorial = null;
    protected static ApInterstitialAd mInterstitialAdClickHome = null;

    protected static ApNativeAd nativeAdViewLanguage = null;
    protected static ApNativeAd nativeAdViewLanguageHigh = null;

    protected static ApNativeAd nativeAdViewHome = null;
    protected static ApNativeAd nativeAdViewHomeHigh = null;

    protected static ApNativeAd nativeAdViewTutorial = null;
    protected static ApNativeAd nativeAdViewTutorialHigh = null;

    protected static ApNativeAd nativeAdViewListItem = null;
    protected static ApNativeAd nativeAdViewListItemHigh = null;

    protected static ApNativeAd nativeAdViewScan = null;
    protected static ApNativeAd nativeAdViewScanHigh = null;

    protected static ApNativeAd nativeAdViewRecoveryItem = null;
    protected static ApNativeAd nativeAdViewRecoveryItemHigh = null;

    protected static ApNativeAd nativeAdViewFinish = null;
    protected static ApNativeAd nativeAdViewFinishHigh = null;

    protected static PreLoadNativeListener preLoadNativeListener = null;

    //    protected static ApInterstitialAd mInterstitialAdItem = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void loadInterTutorial() {
        if (RemoteConfigUtils.INSTANCE.getOnInterTutorial().equals("on")) {
            if (mInterstitialAdTutorial == null) {
                mInterstitialAdTutorial = ITGAd.getInstance().getInterstitialAds(this, getResources().getString(R.string.admob_inter_tutorial));
            }
        }
    }

    protected void loadInterClickHome() {
        if (RemoteConfigUtils.INSTANCE.getOnInterClickHome().equals("on")) {
            if (mInterstitialAdClickHome == null) {
                mInterstitialAdClickHome = ITGAd.getInstance().getInterstitialAds(this, getResources().getString(R.string.admob_inter_click_home));
            }
        }
    }

    protected void loadNativeLanguage() {
        if (RemoteConfigUtils.INSTANCE.getOnNativeLanguageHigh().equals("on")) {
            if (nativeAdViewLanguageHigh == null) {
                ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.native_language_high), R.layout.custom_native_full_size, new ITGAdCallback() {
                    @Override
                    public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                        super.onNativeAdLoaded(nativeAd);
                        nativeAdViewLanguageHigh = nativeAd;
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeLanguageSuccess();
                    }

                    @Override
                    public void onAdFailedToLoad(@Nullable ApAdError adError) {
                        super.onAdFailedToLoad(adError);
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeLanguageFail();
                    }
                });
            }
        }
        if (RemoteConfigUtils.INSTANCE.getOnNativeLanguage().equals("on")) {
            if (nativeAdViewLanguage == null) {
                ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.admob_native_language), R.layout.custom_native_full_size, new ITGAdCallback() {
                    @Override
                    public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                        super.onNativeAdLoaded(nativeAd);
                        nativeAdViewLanguage = nativeAd;
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeLanguageSuccess();
                    }

                    @Override
                    public void onAdFailedToLoad(@Nullable ApAdError adError) {
                        super.onAdFailedToLoad(adError);
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeLanguageFail();
                    }
                });
            }
        }
    }

    protected void loadNativeScan() {
        if (RemoteConfigUtils.INSTANCE.getOnNativeScanHigh().equals("on")) {
            if (nativeAdViewScanHigh == null) {
                ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.native_scan_high), R.layout.custom_native_full_size, new ITGAdCallback() {
                    @Override
                    public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                        super.onNativeAdLoaded(nativeAd);
                        nativeAdViewScanHigh = nativeAd;
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeSuccess();
                    }

                    @Override
                    public void onAdFailedToLoad(@Nullable ApAdError adError) {
                        super.onAdFailedToLoad(adError);
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeFail();
                    }
                });
            }
        }

        if (RemoteConfigUtils.INSTANCE.getOnNativeScan().equals("on")) {
            if (nativeAdViewScan == null) {
                ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.admob_native_scan), R.layout.custom_native_full_size, new ITGAdCallback() {
                    @Override
                    public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                        super.onNativeAdLoaded(nativeAd);
                        nativeAdViewScan = nativeAd;
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeSuccess();
                    }

                    @Override
                    public void onAdFailedToLoad(@Nullable ApAdError adError) {
                        super.onAdFailedToLoad(adError);
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeFail();
                    }
                });
            }
        }
    }

    protected void loadNativeRecovery() {
        if (RemoteConfigUtils.INSTANCE.getOnNativeRecoveryItemHigh().equals("on")) {
            if (nativeAdViewRecoveryItemHigh == null) {
                ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.native_recovery_item_high), R.layout.custom_native_full_size, new ITGAdCallback() {
                    @Override
                    public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                        super.onNativeAdLoaded(nativeAd);
                        nativeAdViewRecoveryItemHigh = nativeAd;
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeLanguageSuccess();
                    }

                    @Override
                    public void onAdFailedToLoad(@Nullable ApAdError adError) {
                        super.onAdFailedToLoad(adError);
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeLanguageFail();
                    }
                });
            }
        }
        if (RemoteConfigUtils.INSTANCE.getOnNativeRecoveryItem().equals("on")) {
            if (nativeAdViewRecoveryItem == null) {
                ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.admob_native_recovery_item), R.layout.custom_native_full_size, new ITGAdCallback() {
                    @Override
                    public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                        super.onNativeAdLoaded(nativeAd);
                        nativeAdViewRecoveryItem = nativeAd;
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeLanguageSuccess();
                    }

                    @Override
                    public void onAdFailedToLoad(@Nullable ApAdError adError) {
                        super.onAdFailedToLoad(adError);
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeLanguageFail();
                    }
                });
            }
        }
    }

    protected void loadNativeHome() {
        if (RemoteConfigUtils.INSTANCE.getOnNativeHomeHigh().equals("on")) {
            if (nativeAdViewHomeHigh == null) {
                ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.native_home_high), R.layout.custom_native_no_media, new ITGAdCallback() {
                    @Override
                    public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                        super.onNativeAdLoaded(nativeAd);
                        nativeAdViewHomeHigh = nativeAd;
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeHomeSuccess();
                    }

                    @Override
                    public void onAdFailedToLoad(@Nullable ApAdError adError) {
                        super.onAdFailedToLoad(adError);
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeHomeFail();
                    }
                });
            }
        }
        if (RemoteConfigUtils.INSTANCE.getOnNativeHome().equals("on")) {
            if (nativeAdViewHome == null) {
                ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.admob_native_home), R.layout.custom_native_no_media, new ITGAdCallback() {
                    @Override
                    public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                        super.onNativeAdLoaded(nativeAd);
                        nativeAdViewHome = nativeAd;
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeHomeSuccess();
                    }

                    @Override
                    public void onAdFailedToLoad(@Nullable ApAdError adError) {
                        super.onAdFailedToLoad(adError);
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeHomeFail();
                    }
                });
            }
        }
    }

    protected void loadNativeTutorial() {
        if (RemoteConfigUtils.INSTANCE.getOnNativeTutorialHigh().equals("on")) {
            if (nativeAdViewTutorialHigh == null) {
                ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.native_tutorial_high), R.layout.custom_native_no_media_purple, new ITGAdCallback() {
                    @Override
                    public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                        super.onNativeAdLoaded(nativeAd);
                        nativeAdViewTutorialHigh = nativeAd;
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeTutorial();
                    }

                    @Override
                    public void onAdFailedToLoad(@Nullable ApAdError adError) {
                        super.onAdFailedToLoad(adError);

                    }
                });
            }
        }
        if (RemoteConfigUtils.INSTANCE.getOnNativeTutorial().equals("on")) {
            if (nativeAdViewTutorial == null) {
                ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.admob_native_tutorial), R.layout.custom_native_no_media_purple, new ITGAdCallback() {
                    @Override
                    public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                        super.onNativeAdLoaded(nativeAd);
                        nativeAdViewTutorial = nativeAd;
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeTutorial();
                    }

                    @Override
                    public void onAdFailedToLoad(@Nullable ApAdError adError) {
                        super.onAdFailedToLoad(adError);
                    }
                });
            }
        }
    }

    protected void loadNativeListItem() {
        if (RemoteConfigUtils.INSTANCE.getOnNativeListItemHigh().equals("on")) {
            if (nativeAdViewListItemHigh == null) {
                ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.native_list_item_high), R.layout.custom_native_no_media, new ITGAdCallback() {
                    @Override
                    public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                        super.onNativeAdLoaded(nativeAd);
                        nativeAdViewListItemHigh = nativeAd;
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeSuccess();
                    }

                    @Override
                    public void onAdFailedToLoad(@Nullable ApAdError adError) {
                        super.onAdFailedToLoad(adError);
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeFail();
                    }
                });
            }
        }

        if (RemoteConfigUtils.INSTANCE.getOnNativeListItem().equals("on")) {
            if (nativeAdViewListItem == null) {
                ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.admob_native_list_item), R.layout.custom_native_no_media, new ITGAdCallback() {
                    @Override
                    public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                        super.onNativeAdLoaded(nativeAd);
                        nativeAdViewListItem = nativeAd;
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeSuccess();
                    }

                    @Override
                    public void onAdFailedToLoad(@Nullable ApAdError adError) {
                        super.onAdFailedToLoad(adError);
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeFail();
                    }
                });
            }
        }
    }

    protected void loadNativeFinish() {
        if (RemoteConfigUtils.INSTANCE.getOnNativeFinishHigh().equals("on")) {
            if (nativeAdViewFinishHigh == null) {
                ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.native_finish_high), R.layout.custom_native_no_media, new ITGAdCallback() {
                    @Override
                    public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                        super.onNativeAdLoaded(nativeAd);
                        nativeAdViewFinishHigh = nativeAd;
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeSuccess();
                    }

                    @Override
                    public void onAdFailedToLoad(@Nullable ApAdError adError) {
                        super.onAdFailedToLoad(adError);
                    }
                });
            }
        }

        if (RemoteConfigUtils.INSTANCE.getOnNativeResultActivity().equals("on")) {
            if (nativeAdViewFinish == null) {
                ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.admob_native_finish), R.layout.custom_native_no_media, new ITGAdCallback() {
                    @Override
                    public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                        super.onNativeAdLoaded(nativeAd);
                        nativeAdViewFinish = nativeAd;
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeSuccess();
                    }

                    @Override
                    public void onAdFailedToLoad(@Nullable ApAdError adError) {
                        super.onAdFailedToLoad(adError);
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
        void onLoadNativeSuccess();

        void onLoadNativeFail();

        void onLoadNativeLanguageSuccess();

        void onLoadNativeLanguageFail();

        void onLoadNativeHomeSuccess();

        void onLoadNativeHomeFail();

        void onLoadNativeTutorial();
    }

}
