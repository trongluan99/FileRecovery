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
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ads.control.ads.ITGAd;
import com.ads.control.ads.ITGAdCallback;
import com.ads.control.ads.wrapper.ApAdError;
import com.jm.filerecovery.videorecovery.photorecovery.BaseActivity;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.RemoteConfigUtils;
import com.jm.filerecovery.videorecovery.photorecovery.utils.SharePreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class IntroduceActivity extends BaseActivity {

    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 1234;
    private List<Callable<Void>> callables = new ArrayList<>();
    ActivityResultLauncher<Intent> mGetPermission;
    int step = 0;
    TextView title_step;
    TextView txt_Start;
    TextView txt_intro;
    TextView txt_intro_1;
    TextView txt_intro_2;
    TextView txt_intro_note;
    private boolean activity;

    //    private ApInterstitialAd mInterstitialAd = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        title_step = findViewById(R.id.title_step);
        txt_intro = findViewById(R.id.txt_intro);
        txt_intro_1 = findViewById(R.id.txt_intro_1);
        txt_intro_2 = findViewById(R.id.txt_intro_2);
        txt_intro_note = findViewById(R.id.txt_intro_note);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#66000000"));
        }
        mGetPermission = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()) {
                        showStep();
                    }
                }
            }
        });
        activity = true;
        loadInterClickHome();
    }

    @Override
    protected void onResume() {
        super.onResume();
        activity = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        activity = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = false;
    }

    public void onStartClick(View view) {
        try {
            requestPermissionAll(() -> {
                showStep();
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showStep() {
        if (step == 0) {
            txt_intro.setVisibility(View.INVISIBLE);
            txt_intro_1.setVisibility(View.VISIBLE);
            txt_intro_2.setVisibility(View.INVISIBLE);
            txt_intro_note.setVisibility(View.INVISIBLE);
            step = 1;
            title_step.setText(getResources().getString(R.string.step1));
            txt_Start.setText(getResources().getString(R.string.next));
        } else if (step == 1) {
            txt_intro.setVisibility(View.INVISIBLE);
            txt_intro_1.setVisibility(View.INVISIBLE);
            txt_intro_2.setVisibility(View.VISIBLE);
            txt_intro_note.setVisibility(View.INVISIBLE);
            step = 2;
            title_step.setText(getResources().getString(R.string.step2));
        } else if (step == 2) {
            txt_intro.setVisibility(View.INVISIBLE);
            txt_intro_1.setVisibility(View.INVISIBLE);
            txt_intro_2.setVisibility(View.INVISIBLE);
            txt_intro_note.setVisibility(View.VISIBLE);
            step = 3;
            title_step.setText(getResources().getString(R.string.step_note));
            txt_Start.setText(getResources().getString(R.string.let_start));
        } else if (step == 3) {
            if (RemoteConfigUtils.INSTANCE.getOnInterIntroduce().equals("on")) {
                Log.d("TuanPA38", " checkRemoteConfigResult getOnInterIntroduce == on");
                if (mInterstitialAdTutorial != null) {
                    if (mInterstitialAdTutorial.isReady()) {
                        ITGAd.getInstance().forceShowInterstitial(this, mInterstitialAdTutorial, new ITGAdCallback() {
                            @Override
                            public void onNextAction() {
                                Log.i("TuanPA38", "onNextAction: start content and finish main");
                                if (activity) {
                                    startActivity(new Intent(IntroduceActivity.this, MainActivity.class));
                                    finish();
                                    activity = false;
                                }
                            }

                            @Override
                            public void onAdFailedToShow(@Nullable ApAdError adError) {
                                super.onAdFailedToShow(adError);
                                Log.i("TuanPA38", "onAdFailedToShow:" + adError.getMessage());
                                if (activity) {
                                    startActivity(new Intent(IntroduceActivity.this, MainActivity.class));
                                    finish();
                                    activity = false;
                                }
                            }

                        }, true);
                    } else {
                        if (activity) {
                            startActivity(new Intent(IntroduceActivity.this, MainActivity.class));
                            finish();
                            activity = false;
                        }
                    }
                } else {
                    if (activity) {
                        startActivity(new Intent(IntroduceActivity.this, MainActivity.class));
                        finish();
                        activity = false;
                    }
                }

            } else {
                if (activity) {
                    startActivity(new Intent(IntroduceActivity.this, MainActivity.class));
                    finish();
                    activity = false;
                }
            }
            step = 4;
        } else {
            if (activity) {
                startActivity(new Intent(IntroduceActivity.this, MainActivity.class));
                finish();
                activity = false;
            }
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
                    mGetPermission.launch(intent);
                    SharePreferenceUtils.getInstance(IntroduceActivity.this).saveShowFullAds(true);
                } catch (Exception e) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    mGetPermission.launch(intent);
                    SharePreferenceUtils.getInstance(IntroduceActivity.this).saveShowFullAds(true);
                }
            }
        } else {
            askPermissionStorage(callable);
        }
    }

}
