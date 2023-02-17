package com.jm.filerecovery.videorecovery.photorecovery.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ads.control.ads.AperoAd;
import com.jm.filerecovery.videorecovery.photorecovery.BaseActivity;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.adapter.LanguageAdapter;
import com.jm.filerecovery.videorecovery.photorecovery.model.LanguageModel;
import com.jm.filerecovery.videorecovery.photorecovery.ui.activity.IntroduceActivity;
import com.jm.filerecovery.videorecovery.photorecovery.utils.GlobalAppCache;
import com.jm.filerecovery.videorecovery.photorecovery.RemoteConfigUtils;
import com.jm.filerecovery.videorecovery.photorecovery.utils.SharePreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;


public class LanguageActivity extends BaseActivity {
    RecyclerView recyclerLanguage;
    ImageView imgSaveLanguage;
    List<LanguageModel> languageModelList = new ArrayList<>();
    LanguageAdapter languageAdapter;
    private int currentPosition;
    private String currentLanguage;
    private boolean fromSplashActivity;
    /// có thao tác với view không
    private boolean checkManipulationAct = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        ButterKnife.bind(this);
        recyclerLanguage = findViewById(R.id.rcv_language);
        imgSaveLanguage = findViewById(R.id.img_save_language);
        fromSplashActivity = getIntent().getBooleanExtra("SplashActivity", false);
        Log.d("TuanPA38", "LanguageActivity fromSplashActivity = " + fromSplashActivity);
        initLanguage();
        initAds();
    }

    private void initAds() {
        if (RemoteConfigUtils.INSTANCE.getOnNativeLanguage().equals("on")) {
            AperoAd.getInstance().loadNativeAd(this, getResources().getString(R.string.admob_native_language), R.layout.custom_native_full_size);
        }
    }

    private void initLanguage() {
        languageModelList = GlobalAppCache.getInstance(this).getLanguageModelList();

        /// nếu đã chọn đc nn rồi thì ko cần lấy nn mặc định nữa
        if (!SharePreferenceUtils.getInstance(this).getSelectedLanguage()) {
            // lấy ra language default
            currentLanguage = Locale.getDefault().getLanguage();
            Log.d("TuanPA38", "LanguageActivity currentLanguage = " + currentLanguage);
        } else {
            currentLanguage = GlobalAppCache.getInstance(this)
                    .getLanguageModelList().get(SharePreferenceUtils.getInstance(this).getLanguageIndex()).getId();
        }
        boolean isDuplicate = false;
        for (int i = 0; i < languageModelList.size(); i++) {
            if (languageModelList.get(i).getId().equals(currentLanguage)) {
                languageModelList.get(i).setState(1);
                isDuplicate = true;
                currentPosition = i;
            } else {
                languageModelList.get(i).setState(0);
            }
        }
        if (!isDuplicate) {
            languageModelList.get(0).setState(1);
            currentPosition = 0;
        }
        languageAdapter = new LanguageAdapter(this, languageModelList, position -> {
            if (currentPosition != position) {
                checkManipulationAct = true;
            }
            Log.d("TuanPA38", "LanguageActivity onClick position = " + position + " Id: " + languageModelList.get(position).getId());
            languageModelList.get(currentPosition).setState(0);
            languageModelList.get(position).setState(1);
            currentPosition = position;
            languageAdapter.notifyDataSetChanged();
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerLanguage.setLayoutManager(mLayoutManager);
        recyclerLanguage.setAdapter(languageAdapter);
        if (isDuplicate) {
            recyclerLanguage.scrollToPosition(currentPosition);
        }
        imgSaveLanguage.setOnClickListener(it -> {
            if (!checkManipulationAct && !fromSplashActivity) {
                finish();
            } else {
                boolean showIntro = false;
                if (!SharePreferenceUtils.getInstance(this).getSelectedLanguage()) {
                    showIntro = true;
                } else {
                    showIntro = false;
                }
                SharePreferenceUtils.getInstance(this).setSelectedLanguage(true);
                SharePreferenceUtils.getInstance(this).setSaveLanguage(languageModelList.get(currentPosition).getId());
                SharePreferenceUtils.getInstance(this).saveLanguageIndex(currentPosition);
                setLocale(languageModelList.get(currentPosition).getId(), showIntro);
            }
        });
    }

    public void setLocale(String lang, boolean showIntro) {
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
        if (showIntro) {
            Intent intent = new Intent(this, IntroduceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        finish();

        try {
            finishAffinity();
        } catch (NullPointerException e) {

        }
    }
}
