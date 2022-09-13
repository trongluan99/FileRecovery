package com.jm.filerecovery.videorecovery.photorecovery.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.jm.filerecovery.videorecovery.photorecovery.R;

public class InviteWatchAdsActivity extends AppCompatActivity {
    private RewardedInterstitialAd rewardedInterstitialAd;
    private String TAG = "MainActivity";
    private boolean gameOver;
    private boolean isLoadingAds;
    private CountDownTimer countDownTimer;
    private int coinCount;
    private long timeRemaining;
    private boolean isRewarded;
    private int GAME_OVER_REWARD = 1;
    private Button btnYes;
    private Button btnNo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_watch_ads);
        btnYes= findViewById(R.id.btn_yes);
        btnNo= findViewById(R.id.btn_no);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            finish();
            }
        });
        if (rewardedInterstitialAd == null && !isLoadingAds) {
            loadRewardedInterstitialAd();
        }
        createTimer(6);
    }

    private void loadRewardedInterstitialAd() {
        if (rewardedInterstitialAd == null && !isRewarded) {
            isLoadingAds = true;
            AdRequest adRequest = new AdRequest.Builder().build();
            RewardedInterstitialAd.load(this, getResources().getString(R.string.admob_full_reward), adRequest, new RewardedInterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull RewardedInterstitialAd ad) {
                            super.onAdLoaded(ad);
                            rewardedInterstitialAd = ad;
                            isLoadingAds = false;
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            super.onAdFailedToLoad(loadAdError);
                            isLoadingAds = false;
                            rewardedInterstitialAd = null;
                        }
                    }
            );
        }
    }

    private void createTimer(long time) {
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(time *1000,50) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished / 1000 + 1;
                btnYes.setText(getResources().getString(R.string.ads_after)+" "+timeRemaining+" "+getResources().getString(R.string.second));

            }

            @Override
            public void onFinish() {
                addCoins(GAME_OVER_REWARD);
                btnYes.setVisibility(View.VISIBLE);
                gameOver = true;
                if (rewardedInterstitialAd == null) {
                    setResult(888);
                    finish();
                    Log.d("AdmobHelper", "The game is over but the rewarded interstitial ad wasn't ready yet.");
                } else {
                    showRewardedVideo();
                    Log.d("AdmobHelper", "The rewarded interstitial ad is ready.");
                }
            }
        };
        countDownTimer.start();
    }

    private void showRewardedVideo() {
        if (rewardedInterstitialAd == null) {
            Log.d("AdmobHelper", "The rewarded interstitial ad wasn't ready yet.");
            return;
        }
        rewardedInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                super.onAdFailedToShowFullScreenContent(adError);
                rewardedInterstitialAd = null;
            }

            @Override
            public void onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent();
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent();
                rewardedInterstitialAd = null;
                loadRewardedInterstitialAd();
                if(isRewarded) {
                    setResult(888);
                } else {
                    setResult(777);
                }
                finish();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });
        if(rewardedInterstitialAd !=null){
            rewardedInterstitialAd.show(this, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    isRewarded = true;
                }
            });
        }
    }

    private void addCoins(int game_over_reward) {

    }

    public void loadAd() {
        // Use the test ad unit ID to load an ad.
        RewardedInterstitialAd.load(InviteWatchAdsActivity.this, "ca-app-pub-3940256099942544/5354046379",
                new AdRequest.Builder().build(),  new RewardedInterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(RewardedInterstitialAd ad) {
                        Log.d(TAG, "Ad was loaded.");
                        rewardedInterstitialAd = ad;
                    }
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        Log.d(TAG, loadAdError.toString());
                        rewardedInterstitialAd = null;
                    }
                });
    }
}
