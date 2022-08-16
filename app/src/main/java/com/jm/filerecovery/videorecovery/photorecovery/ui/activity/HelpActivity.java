package com.jm.filerecovery.videorecovery.photorecovery.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.jm.filerecovery.videorecovery.photorecovery.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class HelpActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 1234;
    private List<Callable<Void>> callables = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#66000000"));
        }
    }

    public void onStartClick(View view) {
        try {
            requestPermissionAll(() -> {
                startActivity(new Intent(HelpActivity.this, MainActivity.class));
                finish();
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    for (Callable callable : callables) {
                        try {
                            callable.call();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    callables.clear();
                }
                break;
        }
    }

    public void askPermissionStorage(Callable<Void> callable) throws Exception {
        this.callables.clear();
        this.callables.add(callable);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                                , Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_STORAGE);
            } else {
                callable.call();
            }
        } else {
            callable.call();
        }
    }

    private void requestPermissionAll(Callable<Void> callable) throws Exception {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                callable.call();
            } else {
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                    startActivityForResult(intent, MY_PERMISSIONS_REQUEST_STORAGE);
                } catch (Exception e) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivityForResult(intent, MY_PERMISSIONS_REQUEST_STORAGE);
                }
            }
        } else {
            askPermissionStorage(callable);
        }
    }
}
