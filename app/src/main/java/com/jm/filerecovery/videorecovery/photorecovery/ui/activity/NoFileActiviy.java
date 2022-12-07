package com.jm.filerecovery.videorecovery.photorecovery.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;



import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.utils.Utils;


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

        Toolbar ctrToolbar = findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Utils.getHeightStatusBar(this) > 0) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) ctrToolbar.getLayoutParams();
            params.setMargins(0, Utils.getHeightStatusBar(this), 0, 0);
            ctrToolbar.setLayoutParams(params);
        }
        Utils.setStatusBarHomeTransparent(this);

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
//        AdmobUtils.getInstance().loadNativeActivity(NoFileActiviy.this);
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
//        AdmobUtils.getInstance().showInterstitialAd(this, () -> finish());
    }
}
