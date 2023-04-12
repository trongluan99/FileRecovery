package com.jm.filerecovery.videorecovery.photorecovery.ui.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ads.control.ads.ITGAd;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.jm.filerecovery.videorecovery.photorecovery.BaseActivity;
import com.jm.filerecovery.videorecovery.photorecovery.MyApplication;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.TutorialScreenITGActivity;
import com.jm.filerecovery.videorecovery.photorecovery.adapter.LanguageAdapter;
import com.jm.filerecovery.videorecovery.photorecovery.model.LanguageModel;
import com.jm.filerecovery.videorecovery.photorecovery.ui.IClickLanguage;
import com.jm.filerecovery.videorecovery.photorecovery.utils.SharePreferenceUtils;
import com.jm.filerecovery.videorecovery.photorecovery.utils.SystemUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.ButterKnife;


public class LanguageActivity extends BaseActivity implements BaseActivity.PreLoadNativeListener, IClickLanguage {
    RecyclerView recyclerLanguage;
    ImageView imgSaveLanguage;
    LanguageAdapter languageAdapter;
    private boolean fromSplashActivity;

    private LanguageModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        setPreLoadNativeListener(this);
        ButterKnife.bind(this);
        recyclerLanguage = findViewById(R.id.rcv_language);
        imgSaveLanguage = findViewById(R.id.img_save_language);
        fromSplashActivity = getIntent().getBooleanExtra("SplashActivity", false);
        Log.d("TuanPA38", "LanguageActivity fromSplashActivity = " + fromSplashActivity);
        initLanguage();
        initAds();
        loadNativeTutorial();
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.jm.filerecovery.videorecovery.photorecovery", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }

    boolean populateNativeAdView = false;
    FrameLayout frameLayout;
    ShimmerFrameLayout shimmerFrameLayout;

    private void initAds() {
        frameLayout = findViewById(R.id.fl_adplaceholder);
        shimmerFrameLayout = findViewById(R.id.shimmer_container_native);
        /*if (RemoteConfigUtils.INSTANCE.getOnNativeLanguage().equals("on") && nativeAdViewLanguage != null) {
//            AperoAd.getInstance().loadNativeAd(this, getResources().getString(R.string.admob_native_language), R.layout.custom_native_full_size);
            populateNativeAdView = true;
            AperoAd.getInstance().populateNativeAdView(this, nativeAdViewLanguage, frameLayout, shimmerFrameLayout);
        } else {
            Log.d("TuanPA38", "LanguageActivity initAds nativeAdViewLanguage = " + nativeAdViewLanguage);
        }*/

        // Begin: Add Ads
        if (!populateNativeAdView) {
            if (nativeAdViewLanguageHighFloor != null) {
                ITGAd.getInstance().populateNativeAdView(this, nativeAdViewLanguageHighFloor, frameLayout, shimmerFrameLayout);
                populateNativeAdView = true;
            } else {
                if (nativeAdViewLanguage != null) {
                    ITGAd.getInstance().populateNativeAdView(this, nativeAdViewLanguage, frameLayout, shimmerFrameLayout);
                    populateNativeAdView = true;
                }
            }
        }

        // End
    }

    private void initLanguage() {
        languageAdapter = new LanguageAdapter(this, setLanguageDefault(), this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerLanguage.setLayoutManager(mLayoutManager);
        recyclerLanguage.setAdapter(languageAdapter);
    }

    private List<LanguageModel> setLanguageDefault() {
        ArrayList<LanguageModel> lists = new ArrayList();
        String key = SystemUtil.getPreLanguage(this);
        lists.add(new LanguageModel("English", "en", false, R.drawable.img_language_en));
        lists.add(new LanguageModel("Hindi", "hi", false, R.drawable.img_languages_hindi));
        lists.add(new LanguageModel("Spanish", "es", false, R.drawable.img_language_spanish));
        lists.add(new LanguageModel("French", "fr", false, R.drawable.img_languages_french));
        lists.add(new LanguageModel("Portuguese", "pt", false, R.drawable.img_language_portuguese));
        lists.add(new LanguageModel("Korean", "ko", false, R.drawable.img_language_korean));
        lists.add(new LanguageModel("Russian", "ru", false, R.drawable.img_language_ru));
        lists.add(new LanguageModel("Turkish", "tr", false, R.drawable.img_langguages_turkish));


        boolean contains = false;
        if (MyApplication.getInstance().getLanguage() != null) {
            Log.e("TuanPA38", "setLanguageDefault: " + MyApplication.getInstance().getLanguage().getId());
            Log.e("TuanPA38", "setLanguageDefault: " + lists.contains(MyApplication.getInstance().getLanguage()));
            for (int i = 0; i < lists.size(); i++) {
                if (lists.get(i).getId().equals(MyApplication.getInstance().getLanguage().getId())) {
                    contains = true;
                }
            }
        }

        if (MyApplication.getInstance().getLanguage() != null && !contains) {
            Log.e("TuanPA38", "setLanguageDefault: 33333333");
            lists.remove(lists.get(lists.size() - 1));
            lists.add(0, MyApplication.getInstance().getLanguage());
        }

        for (int i = 0; i < lists.size(); i++) {
            if (Objects.equals(key, lists.get(i).getId())) {
                LanguageModel data = lists.get(i);
                data.setState(true);
                lists.remove(lists.get(i));
                lists.add(0, data);
                model = data;
                break;
            }
        }
        return lists;
    }

    public void ivDone(View v) {
        if (!model.getName().equals("")) {
            SystemUtil.setPreLanguage(this, model.getId());
            SystemUtil.setLocale(this);

            SharePreferenceUtils.getInstance(this).setSaveLanguage(model.getId());
            SharePreferenceUtils.getInstance(this).setSaveNameLanguage(model.getName());
            startMainOrTur(model.getId());

            SharePreferenceUtils.getInstance(this).setSelectedLanguage(true);

            finish();
        } else {
            Toast.makeText(this, getString(R.string.please_select_language), Toast.LENGTH_SHORT).show();
        }
    }

    private void startMainOrTur(String lang) {
        Locale myLocale;
        if (lang.equals("zh_CN")) {
            myLocale = new Locale("zh", "CN");
        } else if (lang.equals("zh_TW")) {
            myLocale = new Locale("zh", "TW");
        } else {
            myLocale = new Locale(lang);
        }
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        boolean showIntro = false;
        if (!SharePreferenceUtils.getInstance(this).getSelectedLanguage()) {
            showIntro = true;
        } else {
            showIntro = false;
        }
        Log.d("TuanPA38", " showIntro = " + showIntro);
        Intent intent;
        if (showIntro) {
            intent = new Intent(this, TutorialScreenITGActivity.class);
        } else {
            intent = new Intent(this, SplashActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

        try {
            finishAffinity();
        } catch (NullPointerException e) {

        }
    }


    @Override
    public void onLoadNativeLanguageSuccess() {
        /*Log.d("TuanPA38", "LanguageActivity onLoadNativeLanguageSuccess");
        if (!populateNativeAdView) {
            if (RemoteConfigUtils.INSTANCE.getOnNativeLanguage().equals("on") && nativeAdViewLanguage != null) {
                populateNativeAdView = true;
                AperoAd.getInstance().populateNativeAdView(this, nativeAdViewLanguage, frameLayout, shimmerFrameLayout);
            }
        }*/

        // Begin: Add Ads
        if (!populateNativeAdView) {
            if (nativeAdViewLanguageHighFloor != null) {
                Log.e("XXXXXX", "onLoadNativeSuccess: vao 1");
                ITGAd.getInstance().populateNativeAdView(this, nativeAdViewLanguageHighFloor, frameLayout, shimmerFrameLayout);
                populateNativeAdView = true;
            } else {
                Log.e("XXXXXX", "onLoadNativeSuccess: vao 2");
                if (nativeAdViewLanguage != null) {
                    ITGAd.getInstance().populateNativeAdView(this, nativeAdViewLanguage, frameLayout, shimmerFrameLayout);
                    populateNativeAdView = true;
                }
            }
        }

        // End
    }

    @Override
    public void onLoadNativeLanguageFail() {
        Log.d("TuanPA38", "LanguageActivity onLoadNativeLanguageFail");
    }

    @Override
    public void onLoadNativeHomeSuccess() {

    }

    @Override
    public void onLoadNativeHomeFail() {

    }

    @Override
    public void onLoadNativeTutorial() {

    }

    @Override
    public void onLoadNativeSuccess() {

    }

    @Override
    public void onLoadNativeFail() {

    }

    @Override
    public void onClick(LanguageModel data) {
        languageAdapter.setSelectLanguage(data);
        model = data;
    }
}
