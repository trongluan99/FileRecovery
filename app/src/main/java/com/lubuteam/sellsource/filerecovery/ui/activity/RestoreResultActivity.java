package com.lubuteam.sellsource.filerecovery.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ads.control.AdmobHelp;
import com.ads.control.Rate;
import com.lubuteam.sellsource.filerecovery.R;



public class RestoreResultActivity extends AppCompatActivity {

    Toolbar toolbar;
    int type=0;
    String mName="";
    String path="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_result);

        intView();
        intData();
        AdmobHelp.getInstance().loadNative(RestoreResultActivity.this);

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
        Rate.Show(this,1);


    }
}
