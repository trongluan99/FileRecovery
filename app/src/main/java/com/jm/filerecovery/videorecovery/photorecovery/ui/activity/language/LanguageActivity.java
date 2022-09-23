package com.jm.filerecovery.videorecovery.photorecovery.ui.activity.language;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ads.control.AdmobUtils;
import com.ads.control.SharePreferenceUtils;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.adapter.LanguageAdapter;
import com.jm.filerecovery.videorecovery.photorecovery.model.LanguageModel;
import com.jm.filerecovery.videorecovery.photorecovery.ui.activity.IntroduceActivity;
import com.jm.filerecovery.videorecovery.photorecovery.ui.activity.SettingActivity;
import com.jm.filerecovery.videorecovery.photorecovery.utilts.GlobalAppCache;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;


public class LanguageActivity extends AppCompatActivity {
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
        recyclerLanguage = findViewById(R.id.rcv_language);
        imgSaveLanguage = findViewById(R.id.img_save_language);
        ButterKnife.bind(this);
        fromSplashActivity = getIntent().getBooleanExtra("SplashActivity", false);
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

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerLanguage.setLayoutManager(mLayoutManager);
        recyclerLanguage.setAdapter(languageAdapter);
        if (isDuplicate) {
            recyclerLanguage.scrollToPosition(currentPosition);
        }
        imgSaveLanguage.setOnClickListener(it -> {
            if (!checkManipulationAct && !fromSplashActivity) {
                finish();
            } else {
                SharePreferenceUtils.getInstance(this).setSelectedLanguage(true);
                SharePreferenceUtils.getInstance(this).setSaveLanguage(languageModelList.get(currentPosition).getId());
                SharePreferenceUtils.getInstance(this).saveLanguageIndex(currentPosition);
                setLocale(languageModelList.get(currentPosition).getId());
            }
        });
        if (!SharePreferenceUtils.getInstance(this).getPurchase()) {
            if (!fromSplashActivity)
                AdmobUtils.getInstance().showInterstitialAd(LanguageActivity.this, () -> {

                });
            AdmobUtils.getInstance().loadNativeActivityLanguage(this);
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void setLocale(String lang) {
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
        Intent intent = new Intent(this, IntroduceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

        try {
            finishAffinity();
        } catch (NullPointerException e) {

        }
    }


}
