package com.lubuteam.sellsource.filerecovery.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ads.control.AdmobHelp;
import com.lubuteam.sellsource.filerecovery.R;


public class NoFileActiviy extends AppCompatActivity implements View.OnClickListener {
    @Override
    public void onClick(View view) {

    }

    Toolbar toolbar;

    // CardView cvImage,cvAudio,cvVideo,cvDoc,cvOther;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  Utils.setLocale(this);
        setContentView(R.layout.activity_no_file_activiy);
        intView();
        intEvent();
        intData();
    }

    public void intView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle(getString(R.string.app_name));
    }

    public void intEvent() {
    }

    public void intData() {
        AdmobHelp.getInstance().loadNative(NoFileActiviy.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AdmobHelp.getInstance().showInterstitialAd(this, () -> finish());


    }
}
