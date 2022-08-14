package com.lubuteam.sellsource.filerecovery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.lubuteam.sellsource.filerecovery.R;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }
    public void onStartClick(View view){
        startActivity(new Intent(HelpActivity.this, MainActivity.class));
        finish();
    }
}
