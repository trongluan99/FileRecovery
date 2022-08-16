package com.jm.filerecovery.videorecovery.photorecovery.ui.activity;

import android.content.DialogInterface;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;


import com.ads.control.funtion.UtilsApp;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.utilts.SharePreferenceUtils;
import com.jm.filerecovery.videorecovery.photorecovery.utilts.Utils;

import java.util.Locale;



public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    TextView tvDir,tvLanguage;
    RelativeLayout rlMoreApp,rlRate,rlShare,rlPolicy,rlLanguage;
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
    public void intView(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.setting));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tvDir = (TextView)findViewById(R.id.tvDir);
        tvLanguage = (TextView)findViewById(R.id.tvLanguage);
        rlMoreApp = (RelativeLayout)findViewById(R.id.rlMoreApp);
        rlRate = (RelativeLayout)findViewById(R.id.rlRate);
        rlShare = (RelativeLayout)findViewById(R.id.rlShare);
        rlPolicy = (RelativeLayout)findViewById(R.id.rlPolicy);
        rlLanguage = (RelativeLayout)findViewById(R.id.rlLanguage);
    }
    public void intData(){
      //  tvDir.setText(Utils.getPathSave());
        tvLanguage.setText(getResources().getStringArray(R.array.arrLanguage)[SharePreferenceUtils.getInstance(SettingActivity.this).getLanguageIndex()]);

    }
    public void intEvent(){
        rlMoreApp.setOnClickListener(this);
        rlRate.setOnClickListener(this);
        rlShare.setOnClickListener(this);
        rlPolicy.setOnClickListener(this);
        rlLanguage.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.rlMoreApp:
                UtilsApp.OpenBrower(SettingActivity.this, getString(R.string.link_more_app));
                break;
            case R.id.rlLanguage:
                showDialogLanguage();

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
    public void showDialogLanguage() {
        final String [] arrLanguage = getResources().getStringArray(R.array.arrLanguage) ;
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);

        builder.setTitle(R.string.language_setting);
        String[] items = getResources().getStringArray(R.array.arrLanguage);

        builder.setSingleChoiceItems(items, SharePreferenceUtils.getInstance(SettingActivity.this).getLanguageIndex(),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvLanguage.setText(arrLanguage[which]);
                        setSaveLanguage(which);
                        dialog.dismiss();
                    }
                });



        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        dialog = builder.create();
        // display dialog
        dialog.show();
    }
    public void setSaveLanguage(int index){
        if(index==0) {
            SharePreferenceUtils.getInstance(SettingActivity.this).saveLanguageIndex(0);
            setLocale("en");
        }else if(index==1) {
            SharePreferenceUtils.getInstance(SettingActivity.this).saveLanguageIndex(1);
            setLocale("pt");
        } else if(index==2){
            SharePreferenceUtils.getInstance(SettingActivity.this).saveLanguageIndex(2);
            setLocale("vi");
        }else if(index==3){
            SharePreferenceUtils.getInstance(SettingActivity.this).saveLanguageIndex(3);
            setLocale("ru");
        }else if(index==4){
            SharePreferenceUtils.getInstance(SettingActivity.this).saveLanguageIndex(4);
            setLocale("fr");
        }else if(index==5){
            SharePreferenceUtils.getInstance(SettingActivity.this).saveLanguageIndex(5);
            setLocale("ar");
        }else if(index==6){
            SharePreferenceUtils.getInstance(SettingActivity.this).saveLanguageIndex(6);
            setLocale("es");
        }

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
