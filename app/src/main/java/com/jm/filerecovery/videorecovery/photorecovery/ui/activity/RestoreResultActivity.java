package com.jm.filerecovery.videorecovery.photorecovery.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;


import com.ads.control.ads.AperoAd;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.jm.filerecovery.videorecovery.photorecovery.BaseActivity;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.utils.Utils;


public class RestoreResultActivity extends BaseActivity {

    Toolbar toolbar;
    int type=0;
    String mName="";
    String path="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_result);

        Toolbar ctrToolbar = findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Utils.getHeightStatusBar(this) > 0) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) ctrToolbar.getLayoutParams();
            params.setMargins(0, Utils.getHeightStatusBar(this), 0, 0);
            ctrToolbar.setLayoutParams(params);
        }
        Utils.setStatusBarHomeTransparent(this);

        intView();
        intData();
        initAds();
    }

    private void initAds() {
        FrameLayout frameLayout = findViewById(R.id.fl_adplaceholder);
        ShimmerFrameLayout shimmerFrameLayout = findViewById(R.id.shimmer_container_native);
        AperoAd.getInstance().loadNativeAd(this, getResources().getString(R.string.admob_native_finish), R.layout.custom_native_no_media, frameLayout, shimmerFrameLayout);
    }

    public void intView(){
        type = getIntent().getIntExtra("type", 0);
        toolbar = findViewById(R.id.toolbar);

        if(type==0){
            mName=getString(R.string.photo_recovery);
            path=getString(R.string.restore_folder_path_photo);
        }
        if(type==1){
            mName = getString(R.string.video_recovery);
            path=getString(R.string.restore_folder_path_video);
        }
        if(type==2){
            mName = getString(R.string.audio_recovery);
            path=getString(R.string.restore_folder_path_audio);
        }
        toolbar.setTitle(mName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    public void intData(){
        int int_position = getIntent().getIntExtra("value", 0);
        TextView tvStatus = (TextView)findViewById(R.id.tvStatus);
        tvStatus.setText(String.valueOf(int_position));
        TextView tvPath = (TextView)findViewById(R.id.tvPath);
        tvPath.setText("File Restored to"+"\n"+"/"+path);
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
        super.onBackPressed();
//        Rate.Show(this,1);

    }
}
