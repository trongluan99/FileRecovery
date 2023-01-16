package com.jm.filerecovery.videorecovery.photorecovery.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;


import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.utils.GlobalAppCache;
import com.jm.filerecovery.videorecovery.photorecovery.utils.SharePreferenceUtils;
import com.jm.filerecovery.videorecovery.photorecovery.utils.Utils;
import com.jm.filerecovery.videorecovery.photorecovery.utils.UtilsApp;

import java.util.Locale;


public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    TextView tvDir, tvLanguage;
    RelativeLayout rlMoreApp, rlRate, rlShare, rlPolicy, rlLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar ctrToolbar = findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Utils.getHeightStatusBar(this) > 0) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) ctrToolbar.getLayoutParams();
            params.setMargins(0, Utils.getHeightStatusBar(this), 0, 0);
            ctrToolbar.setLayoutParams(params);
        }
        Utils.setStatusBarHomeTransparent(this);
        intView();
        intData();
        intEvent();
    }

    public void intView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.setting));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tvDir = (TextView) findViewById(R.id.tvDir);
        tvLanguage = (TextView) findViewById(R.id.tvLanguage);
        rlMoreApp = (RelativeLayout) findViewById(R.id.rlMoreApp);
        rlRate = (RelativeLayout) findViewById(R.id.rlRate);
        rlShare = (RelativeLayout) findViewById(R.id.rlShare);
        rlPolicy = (RelativeLayout) findViewById(R.id.rlPolicy);
        rlLanguage = (RelativeLayout) findViewById(R.id.rlLanguage);
    }

    public void intData() {
        int index = SharePreferenceUtils.getInstance(SettingActivity.this).getLanguageIndex();
        tvLanguage.setText(GlobalAppCache.getInstance(SettingActivity.this)
                .getLanguageModelList().get(index).getName());
    }

    public void intEvent() {
        rlMoreApp.setOnClickListener(this);
        rlRate.setOnClickListener(this);
        rlShare.setOnClickListener(this);
        rlPolicy.setOnClickListener(this);
        rlLanguage.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.rlMoreApp:
                UtilsApp.OpenBrower(SettingActivity.this, getString(R.string.link_more_app));
                break;
            case R.id.rlLanguage:
                openLanguagesAct();

                break;
            case R.id.rlRate:
                UtilsApp.RateApp(this);
                break;
            case R.id.rlShare:
                UtilsApp.shareApp(this);
                break;
            case R.id.rlPolicy:
                UtilsApp.OpenBrower(SettingActivity.this, getString(R.string.link_policy));
                break;
            default:
                break;
        }
    }

    private void openLanguagesAct() {
        Intent intent = new Intent(this, LanguageActivity.class);
        startActivity(intent);
    }


    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, SplashActivity.class);
        startActivity(refresh);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
