package com.jm.filerecovery.videorecovery.photorecovery;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.ads.control.ads.ITGAd;
import com.ads.control.ads.ITGAdCallback;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.jm.filerecovery.videorecovery.photorecovery.ui.activity.MainActivity;
import com.jm.filerecovery.videorecovery.photorecovery.utils.SharePreferenceUtils;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class TutorialScreenITGActivity extends BaseActivity implements View.OnClickListener, BaseActivity.PreLoadNativeListener {
    private static final String TAG = "TutorialScreenITGActivi";
    private DotsIndicator dots_indicator;
    private ArrayList<ImageView> imgSelect = new ArrayList<>();
    private ArrayList<ImageView> imgTabs = new ArrayList<>();
    private TextView img_next_skip;
    private ImageView img_next;
    private ImageView img_select_1;
    private ImageView img_select_2;
    private ImageView img_select_3;
    private ImageView img_tab_1;
    private ImageView img_tab_2;
    private ImageView img_tab_3;
    private IntroViewPagerITGAdapter introViewPagerITGAdapter;
    private LinearLayout lnl_skip;
    private int tab = 0;
    private ViewPager2 vpg_intro;
    private boolean activity;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 1234;
    private List<Callable<Void>> callables = new ArrayList<>();
    boolean populateNativeAdView = false;
    FrameLayout frameLayout;
    ShimmerFrameLayout shimmerFrameLayout;
    View layout_native;
    ActivityResultLauncher<Intent> mGetPermission;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getIdLayout());
        initView();
        initData();
        setPreLoadNativeListener(this);
        activity = true;
        loadInterClickHome();
        initAds();
        mGetPermission = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()) {
//                        showStep();
                    }
                }
            }
        });

    }

    public void initAds() {
        frameLayout = findViewById(R.id.fl_adplaceholder);
        shimmerFrameLayout = findViewById(R.id.shimmer_container_native);
        layout_native = findViewById(R.id.layout_native);

        // Begin: Add Ads
        if (!populateNativeAdView) {
            if (nativeAdViewTutorialHigh != null) {
                Log.d(TAG, "nativeAdViewTutorialHigh: ");
                ITGAd.getInstance().populateNativeAdView(this, nativeAdViewTutorialHigh, frameLayout, shimmerFrameLayout);
                populateNativeAdView = true;
            }else{
                if (nativeAdViewTutorial != null) {
                    Log.d(TAG, "nativeAdViewTutorial: ");
                    ITGAd.getInstance().populateNativeAdView(this, nativeAdViewTutorial, frameLayout, shimmerFrameLayout);
                    populateNativeAdView = true;
                }
            }
        }
        // End
    }

    public int getIdLayout() {
        return R.layout.activity_screen_intro;
    }

    public static void openIntro(Activity activity) {
        activity.startActivity(new Intent(activity, TutorialScreenITGActivity.class));
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void initView() {
        this.lnl_skip = (LinearLayout) findViewById(R.id.lnl_skip);
        this.img_tab_1 = (ImageView) findViewById(R.id.img_tab_1);
        this.img_select_1 = (ImageView) findViewById(R.id.img_select_1);
        this.img_tab_2 = (ImageView) findViewById(R.id.img_tab_2);
        this.img_select_2 = (ImageView) findViewById(R.id.img_select_2);
        this.img_tab_3 = (ImageView) findViewById(R.id.img_tab_3);
        this.img_select_3 = (ImageView) findViewById(R.id.img_select_3);
        this.dots_indicator = (DotsIndicator) findViewById(R.id.dots_indicator);
        this.imgTabs.add(this.img_tab_1);
        this.imgTabs.add(this.img_tab_2);
        this.imgTabs.add(this.img_tab_3);
        this.imgSelect.add(this.img_select_1);
        this.imgSelect.add(this.img_select_2);
        this.imgSelect.add(this.img_select_3);
        this.img_next_skip = (TextView) findViewById(R.id.img_next_skip);
        this.img_next = (ImageView) findViewById(R.id.img_next);
        ViewPager2 viewPager2 = (ViewPager2) findViewById(R.id.vpg_intro);
        this.vpg_intro = viewPager2;
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            public void onPageSelected(int i) {
                super.onPageSelected(i);
                TutorialScreenITGActivity.this.selectTab(i);
            }
        });
//        setUpStatusBar();
        this.lnl_skip.setOnClickListener(this);
        this.img_tab_1.setOnClickListener(this);
        this.img_tab_2.setOnClickListener(this);
        this.img_tab_3.setOnClickListener(this);
        this.img_next.setOnClickListener(this);
        this.img_next_skip.setOnClickListener(this);
//        setStatusBarWhite(this);
    }

    public static void setStatusBarWhite(Activity activity) {
        try {
            int i = Build.VERSION.SDK_INT;
            if (i >= 23) {
                activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.white, activity.getTheme()));
            } else if (i >= 21) {
                activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.white));
            }
            activity.getWindow().getDecorView().setSystemUiVisibility(8192);
        } catch (Exception unused) {
        }
    }

    public void selectTab(int i) {
        this.tab = i;
        for (int i2 = 0; i2 < this.imgTabs.size(); i2++) {
            if (i == i2) {
                this.imgTabs.get(i2).setVisibility(View.INVISIBLE);
                this.imgSelect.get(i2).setVisibility(View.VISIBLE);
            } else {
                this.imgTabs.get(i2).setVisibility(View.VISIBLE);
                this.imgSelect.get(i2).setVisibility(View.INVISIBLE);
            }
        }
        this.vpg_intro.setCurrentItem(i);
    }

    public void initData() {
        IntroViewPagerITGAdapter introViewPagerITGAdapter2 = new IntroViewPagerITGAdapter(getSupportFragmentManager(), getLifecycle(), this);
        this.introViewPagerITGAdapter = introViewPagerITGAdapter2;
        this.vpg_intro.setAdapter(introViewPagerITGAdapter2);
        this.dots_indicator.setViewPager2(this.vpg_intro);
    }

    public void onClick(View view) {
        try {
            requestPermissionAll(() -> {
                int id = view.getId();
                if (id == R.id.img_next_skip || id == R.id.img_next) {
                    int i = this.tab;
                    if (i >= 2) {
                        openNextStep();
                    } else {
                        selectTab(i + 1);
                    }
                } else if (id == R.id.lnl_skip) {
                    openNextStep();
                }
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpStatusBar() {
        Window window = getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.color_intro));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void openNextStep() {
        try {
            Log.d("TuanPA38", " checkRemoteConfigResult getOnInterIntroduce == on");
            if (mInterstitialAdTutorial != null) {
                if (mInterstitialAdTutorial.isReady()) {
                    ITGAd.getInstance().forceShowInterstitial(this, mInterstitialAdTutorial, new ITGAdCallback() {
                        @Override
                        public void onNextAction() {
                            super.onNextAction();
                            if (activity) {
                                startActivity(new Intent(TutorialScreenITGActivity.this, MainActivity.class));
                                finish();
                                activity = false;
                            }
                        }
                    });
                } else {
                    if (activity) {
                        startActivity(new Intent(TutorialScreenITGActivity.this, MainActivity.class));
                        finish();
                        activity = false;
                    }
                }
            } else {
                if (activity) {
                    startActivity(new Intent(TutorialScreenITGActivity.this, MainActivity.class));
                    finish();
                    activity = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoadNativeLanguageSuccess() {

    }

    @Override
    public void onLoadNativeLanguageFail() {

    }

    @Override
    public void onLoadNativeHomeSuccess() {

    }

    @Override
    public void onLoadNativeHomeFail() {

    }

    @Override
    public void onLoadNativeTutorial() {
        Log.d("TuanPA38", "LanguageActivity onLoadNativeLanguageSuccess");
        // Begin: Add Ads
        if (!populateNativeAdView) {
            if (nativeAdViewTutorialHigh != null) {
                Log.d(TAG, "nativeAdViewTutorialHigh: ");
                ITGAd.getInstance().populateNativeAdView(this, nativeAdViewTutorialHigh, frameLayout, shimmerFrameLayout);
                populateNativeAdView = true;
            }else{
                if (nativeAdViewTutorial != null) {
                    Log.d(TAG, "nativeAdViewTutorial: ");
                    ITGAd.getInstance().populateNativeAdView(this, nativeAdViewTutorial, frameLayout, shimmerFrameLayout);
                    populateNativeAdView = true;
                }
            }
        }
        // End
    }

    @Override
    public void onLoadNativeSuccess() {

    }

    @Override
    public void onLoadNativeFail() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    for (Callable callable : callables) {
                        try {
                            callable.call();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    callables.clear();
                }
                break;
        }
    }

    public void askPermissionStorage(Callable<Void> callable) throws Exception {
        this.callables.clear();
        this.callables.add(callable);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                                , Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_STORAGE);
            } else {
                callable.call();
            }
        } else {
            callable.call();
        }
    }

    private void requestPermissionAll(Callable<Void> callable) throws Exception {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                callable.call();
            } else {
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                    mGetPermission.launch(intent);
                    SharePreferenceUtils.getInstance(TutorialScreenITGActivity.this).saveShowFullAds(true);
                } catch (Exception e) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    mGetPermission.launch(intent);
                    SharePreferenceUtils.getInstance(TutorialScreenITGActivity.this).saveShowFullAds(true);
                }
            }
        } else {
            askPermissionStorage(callable);
        }
    }
}
