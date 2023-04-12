package com.jm.filerecovery.videorecovery.photorecovery;

import android.os.Bundle;
import android.util.Log;
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
    protected static ApNativeAd nativeAdViewLanguageHighFloor = null;

    protected static ApNativeAd nativeAdViewHome = null;

    protected static ApNativeAd nativeAdViewTutorial = null;

    protected static ApNativeAd nativeAdViewScan = null;
    protected static ApNativeAd nativeAdViewScanHigh = null;

    protected static ApNativeAd nativeAdViewRecoveryItem = null;
    protected static ApNativeAd nativeAdViewRecoveryItemHigh = null;


    protected static PreLoadNativeListener preLoadNativeListener = null;

    //    protected static ApInterstitialAd mInterstitialAdItem = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TuanPA38", "BaseActivity onCreate");
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
        Log.d("TuanPA38", "BaseActivity loadInterTutorial");
        if (RemoteConfigUtils.INSTANCE.getOnInterTutorial().equals("on")) {
            if (mInterstitialAdTutorial == null) {
                mInterstitialAdTutorial = ITGAd.getInstance().getInterstitialAds(this, getResources().getString(R.string.admob_inter_tutorial));
            }
        }
    }

    protected void loadInterClickHome() {
        Log.d("TuanPA38", "BaseActivity loadInterClickHome");
        if (RemoteConfigUtils.INSTANCE.getOnInterClickHome().equals("on")) {
            if (mInterstitialAdClickHome == null) {
                mInterstitialAdClickHome = ITGAd.getInstance().getInterstitialAds(this, getResources().getString(R.string.admob_inter_click_home));
            }
        }
    }

    protected void loadNativeLanguage() {
        Log.d("TuanPA38", "BaseActivity loadNativeLanguage");
        if (RemoteConfigUtils.INSTANCE.getOnNativeLanguage().equals("on")) {
            if (RemoteConfigUtils.INSTANCE.getOnNativeLanguageHigh().equals("on")) {
                if (nativeAdViewLanguageHighFloor == null) {
                    ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.admob_native_language_high_floor), R.layout.custom_native_full_size, new ITGAdCallback() {
                        @Override
                        public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                            super.onNativeAdLoaded(nativeAd);
                            nativeAdViewLanguageHighFloor = nativeAd;
                            if (preLoadNativeListener != null)
                                preLoadNativeListener.onLoadNativeLanguageSuccess();
                            Log.d("TuanPA38", "BaseActivity onNativeAdLoaded nativeAdViewLanguage = " + nativeAdViewLanguageHighFloor);
                        }

                        @Override
                        public void onAdFailedToLoad(@Nullable ApAdError adError) {
                            super.onAdFailedToLoad(adError);
                            Log.d("TuanPA38", "BaseActivity loadNativeLanguage onAdFailedToLoad");
                            if (preLoadNativeListener != null)
                                preLoadNativeListener.onLoadNativeLanguageFail();
                        }
                    });
                }

                if (nativeAdViewLanguage == null) {
                    ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.admob_native_language), R.layout.custom_native_full_size, new ITGAdCallback() {
                        @Override
                        public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                            super.onNativeAdLoaded(nativeAd);
                            nativeAdViewLanguage = nativeAd;
                            if (preLoadNativeListener != null)
                                preLoadNativeListener.onLoadNativeLanguageSuccess();
                            Log.d("TuanPA38", "BaseActivity onNativeAdLoaded nativeAdViewLanguage = " + nativeAdViewLanguage);
                        }

                        @Override
                        public void onAdFailedToLoad(@Nullable ApAdError adError) {
                            super.onAdFailedToLoad(adError);
                            Log.d("TuanPA38", "BaseActivity loadNativeLanguage onAdFailedToLoad");
                            if (preLoadNativeListener != null)
                                preLoadNativeListener.onLoadNativeLanguageFail();
                        }
                    });
                }
            } else {
                if (nativeAdViewLanguage == null) {
                    ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.admob_native_language), R.layout.custom_native_full_size, new ITGAdCallback() {
                        @Override
                        public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                            super.onNativeAdLoaded(nativeAd);
                            nativeAdViewLanguage = nativeAd;
                            if (preLoadNativeListener != null)
                                preLoadNativeListener.onLoadNativeLanguageSuccess();
                            Log.d("TuanPA38", "BaseActivity onNativeAdLoaded nativeAdViewLanguage = " + nativeAdViewLanguage);
                        }

                        @Override
                        public void onAdFailedToLoad(@Nullable ApAdError adError) {
                            super.onAdFailedToLoad(adError);
                            Log.d("TuanPA38", "BaseActivity loadNativeLanguage onAdFailedToLoad");
                            if (preLoadNativeListener != null)
                                preLoadNativeListener.onLoadNativeLanguageFail();
                        }
                    });
                }
            }
        }
    }

    protected void loadNativeScan() {
        Log.d("TuanPA38", "BaseActivity loadNativeLanguage");
        if (RemoteConfigUtils.INSTANCE.getOnNativeScan().equals("on")) {
            if (RemoteConfigUtils.INSTANCE.getOnNativeScanHigh().equals("on")) {
                if (nativeAdViewScanHigh == null) {
                    ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.admob_native_scan_high), R.layout.custom_native_full_size, new ITGAdCallback() {
                        @Override
                        public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                            super.onNativeAdLoaded(nativeAd);
                            nativeAdViewScanHigh = nativeAd;
                            if (preLoadNativeListener != null)
                                preLoadNativeListener.onLoadNativeLanguageSuccess();
                            Log.d("TuanPA38", "BaseActivity onNativeAdLoaded nativeAdViewLanguage = " + nativeAdViewScanHigh);
                        }

                        @Override
                        public void onAdFailedToLoad(@Nullable ApAdError adError) {
                            super.onAdFailedToLoad(adError);
                            Log.d("TuanPA38", "BaseActivity loadNativeLanguage onAdFailedToLoad");
                            if (preLoadNativeListener != null)
                                preLoadNativeListener.onLoadNativeLanguageFail();
                        }
                    });
                }

                if (nativeAdViewScan == null) {
                    ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.admob_native_scan), R.layout.custom_native_full_size, new ITGAdCallback() {
                        @Override
                        public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                            super.onNativeAdLoaded(nativeAd);
                            nativeAdViewScan = nativeAd;
                            if (preLoadNativeListener != null)
                                preLoadNativeListener.onLoadNativeLanguageSuccess();
                            Log.d("TuanPA38", "BaseActivity onNativeAdLoaded nativeAdViewLanguage = " + nativeAdViewScan);
                        }

                        @Override
                        public void onAdFailedToLoad(@Nullable ApAdError adError) {
                            super.onAdFailedToLoad(adError);
                            Log.d("TuanPA38", "BaseActivity loadNativeLanguage onAdFailedToLoad");
                            if (preLoadNativeListener != null)
                                preLoadNativeListener.onLoadNativeLanguageFail();
                        }
                    });
                }
            } else {
                if (nativeAdViewScan == null) {
                    ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.admob_native_scan), R.layout.custom_native_full_size, new ITGAdCallback() {
                        @Override
                        public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                            super.onNativeAdLoaded(nativeAd);
                            nativeAdViewScan = nativeAd;
                            if (preLoadNativeListener != null)
                                preLoadNativeListener.onLoadNativeLanguageSuccess();
                            Log.d("TuanPA38", "BaseActivity onNativeAdLoaded nativeAdViewLanguage = " + nativeAdViewScan);
                        }

                        @Override
                        public void onAdFailedToLoad(@Nullable ApAdError adError) {
                            super.onAdFailedToLoad(adError);
                            Log.d("TuanPA38", "BaseActivity loadNativeLanguage onAdFailedToLoad");
                            if (preLoadNativeListener != null)
                                preLoadNativeListener.onLoadNativeLanguageFail();
                        }
                    });
                }
            }
        }
    }

    protected void loadNativeRecovery() {
        Log.d("TuanPA38", "BaseActivity loadNativeLanguage");
        if (RemoteConfigUtils.INSTANCE.getOnNativeRecoveryItem().equals("on")) {
            if (RemoteConfigUtils.INSTANCE.getOnNativeRecoveryItemHigh().equals("on")) {
                if (nativeAdViewRecoveryItemHigh == null) {
                    ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.admob_native_recovery_item_high), R.layout.custom_native_full_size, new ITGAdCallback() {
                        @Override
                        public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                            super.onNativeAdLoaded(nativeAd);
                            nativeAdViewRecoveryItemHigh = nativeAd;
                            if (preLoadNativeListener != null)
                                preLoadNativeListener.onLoadNativeLanguageSuccess();
                            Log.d("TuanPA38", "BaseActivity onNativeAdLoaded nativeAdViewLanguage = " + nativeAdViewRecoveryItemHigh);
                        }

                        @Override
                        public void onAdFailedToLoad(@Nullable ApAdError adError) {
                            super.onAdFailedToLoad(adError);
                            Log.d("TuanPA38", "BaseActivity loadNativeLanguage onAdFailedToLoad");
                            if (preLoadNativeListener != null)
                                preLoadNativeListener.onLoadNativeLanguageFail();
                        }
                    });
                }

                if (nativeAdViewRecoveryItem == null) {
                    ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.admob_native_recovery_item), R.layout.custom_native_full_size, new ITGAdCallback() {
                        @Override
                        public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                            super.onNativeAdLoaded(nativeAd);
                            nativeAdViewRecoveryItem = nativeAd;
                            if (preLoadNativeListener != null)
                                preLoadNativeListener.onLoadNativeLanguageSuccess();
                            Log.d("TuanPA38", "BaseActivity onNativeAdLoaded nativeAdViewLanguage = " + nativeAdViewRecoveryItem);
                        }

                        @Override
                        public void onAdFailedToLoad(@Nullable ApAdError adError) {
                            super.onAdFailedToLoad(adError);
                            Log.d("TuanPA38", "BaseActivity loadNativeLanguage onAdFailedToLoad");
                            if (preLoadNativeListener != null)
                                preLoadNativeListener.onLoadNativeLanguageFail();
                        }
                    });
                }
            } else {
                if (nativeAdViewRecoveryItem == null) {
                    ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.admob_native_recovery_item), R.layout.custom_native_full_size, new ITGAdCallback() {
                        @Override
                        public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                            super.onNativeAdLoaded(nativeAd);
                            nativeAdViewRecoveryItem = nativeAd;
                            if (preLoadNativeListener != null)
                                preLoadNativeListener.onLoadNativeLanguageSuccess();
                            Log.d("TuanPA38", "BaseActivity onNativeAdLoaded nativeAdViewLanguage = " + nativeAdViewRecoveryItem);
                        }

                        @Override
                        public void onAdFailedToLoad(@Nullable ApAdError adError) {
                            super.onAdFailedToLoad(adError);
                            Log.d("TuanPA38", "BaseActivity loadNativeLanguage onAdFailedToLoad");
                            if (preLoadNativeListener != null)
                                preLoadNativeListener.onLoadNativeLanguageFail();
                        }
                    });
                }
            }
        }
    }

    protected void loadNativeHome() {
        Log.d("TuanPA38", "BaseActivity loadNativeHome");
        if (RemoteConfigUtils.INSTANCE.getOnNativeHome().equals("on")) {
            if (nativeAdViewHome == null) {
                ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.admob_native_home), R.layout.custom_native_no_media, new ITGAdCallback() {
                    @Override
                    public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                        super.onNativeAdLoaded(nativeAd);
                        nativeAdViewHome = nativeAd;
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeHomeSuccess();
                        Log.d("TuanPA38", "BaseActivity onNativeAdLoaded nativeAdViewHome = " + nativeAdViewHome);
                    }

                    @Override
                    public void onAdFailedToLoad(@Nullable ApAdError adError) {
                        super.onAdFailedToLoad(adError);
                        Log.d("TuanPA38", "BaseActivity loadNativeHome onAdFailedToLoad");
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeHomeFail();
                    }
                });
            }
        }
    }

    protected void loadNativeTutorial() {
        Log.d("TuanPA38", "BaseActivity loadNativeHome");
        if (RemoteConfigUtils.INSTANCE.getOnNativeTutorial().equals("on")) {
            if (nativeAdViewTutorial == null) {
                ITGAd.getInstance().loadNativeAdResultCallback(this, getResources().getString(R.string.admob_native_tutorial), R.layout.custom_native_no_media_purple, new ITGAdCallback() {
                    @Override
                    public void onNativeAdLoaded(@NonNull ApNativeAd nativeAd) {
                        super.onNativeAdLoaded(nativeAd);
                        nativeAdViewTutorial = nativeAd;
                        if (preLoadNativeListener != null)
                            preLoadNativeListener.onLoadNativeTutorial();
                        Log.d("TuanPA38", "BaseActivity onNativeAdLoaded nativeAdViewTutorial = " + nativeAdViewTutorial);
                    }

                    @Override
                    public void onAdFailedToLoad(@Nullable ApAdError adError) {
                        super.onAdFailedToLoad(adError);
                        Log.d("TuanPA38", "BaseActivity nativeAdViewTutorial onAdFailedToLoad");
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
