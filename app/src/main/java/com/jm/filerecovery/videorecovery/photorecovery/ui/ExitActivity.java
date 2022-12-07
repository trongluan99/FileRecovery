package com.jm.filerecovery.videorecovery.photorecovery.ui;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.utils.SharePreferenceUtils;

import java.util.Locale;

public class ExitActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thank_you);

        Locale myLocale;
        String language = SharePreferenceUtils.getInstance(this).getSaveLanguage();
        Log.d("ServiceManager","onCreate language = "+language);
        if(language.equals("zh_CN")){
            myLocale = new Locale("zh","CN");
        } else if(language.equals("zh_TW")){
            myLocale = new Locale("zh","TW");
        } else {
            myLocale =new Locale(language);
        }
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        TextView textView = findViewById(R.id.thankYou_tv_content);
        textView.setText(getResources().getString(R.string.text_des_thank));
        new Handler().postDelayed(() -> {
            finish();
            try{
                finishAffinity();
            } catch (NullPointerException e ){

            }
        }, 3000);
    }
}
