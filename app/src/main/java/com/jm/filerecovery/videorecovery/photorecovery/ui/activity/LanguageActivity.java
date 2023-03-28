package com.jm.filerecovery.videorecovery.photorecovery.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ads.control.ads.AperoAd;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.jm.filerecovery.videorecovery.photorecovery.BaseActivity;
import com.jm.filerecovery.videorecovery.photorecovery.MyApplication;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.adapter.LanguageAdapter;
import com.jm.filerecovery.videorecovery.photorecovery.model.LanguageModel;
import com.jm.filerecovery.videorecovery.photorecovery.RemoteConfigUtils;
import com.jm.filerecovery.videorecovery.photorecovery.ui.IClickLanguage;
import com.jm.filerecovery.videorecovery.photorecovery.utils.SharePreferenceUtils;
import com.jm.filerecovery.videorecovery.photorecovery.utils.SystemUtil;

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
    }
    boolean populateNativeAdView = false;
    FrameLayout frameLayout;
    ShimmerFrameLayout shimmerFrameLayout;
    private void initAds() {
        frameLayout = findViewById(R.id.fl_adplaceholder);
        shimmerFrameLayout = findViewById(R.id.shimmer_container_native);
        if (RemoteConfigUtils.INSTANCE.getOnNativeLanguage().equals("on") && nativeAdViewLanguage!=null) {
//            AperoAd.getInstance().loadNativeAd(this, getResources().getString(R.string.admob_native_language), R.layout.custom_native_full_size);
            populateNativeAdView = true;
            AperoAd.getInstance().populateNativeAdView(this,nativeAdViewLanguage,frameLayout,shimmerFrameLayout);
        } else {
            Log.d("TuanPA38", "LanguageActivity initAds nativeAdViewLanguage = "+nativeAdViewLanguage );
        }
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

        Log.e("TAG", "setLanguageDefault: " + MyApplication.getInstance().getLanguage());
        if (MyApplication.getInstance().getLanguage() != null && !lists.contains(MyApplication.getInstance().getLanguage())) {
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
            SharePreferenceUtils.getInstance(this).setSelectedLanguage(true);
            SharePreferenceUtils.getInstance(this).setSaveLanguage(model.getId());
            SharePreferenceUtils.getInstance(this).setSaveNameLanguage(model.getName());
            startMainOrTur();
            finish();
        } else {
            Toast.makeText(this, getString(R.string.please_select_language), Toast.LENGTH_SHORT).show();
        }
    }

    private void startMainOrTur() {
        boolean showIntro = !SharePreferenceUtils.getInstance(this).getSelectedLanguage();
        Intent intent;
        if (showIntro) {
            intent = new Intent(this, IntroduceActivity.class);
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
        Log.d("TuanPA38", "LanguageActivity onLoadNativeLanguageSuccess" );
        if(!populateNativeAdView){
            if (RemoteConfigUtils.INSTANCE.getOnNativeLanguage().equals("on") && nativeAdViewLanguage!=null) {
                populateNativeAdView = true;
                AperoAd.getInstance().populateNativeAdView(this,nativeAdViewLanguage,frameLayout,shimmerFrameLayout);
            }
        }
    }

    @Override
    public void onLoadNativeLanguageFail() {
        Log.d("TuanPA38", "LanguageActivity onLoadNativeLanguageFail" );
    }

    @Override
    public void onLoadNativeHomeSuccess() {

    }

    @Override
    public void onLoadNativeHomeFail() {

    }

    @Override
    public void onClick(LanguageModel data) {
        languageAdapter.setSelectLanguage(data);
        model = data;
    }
}
