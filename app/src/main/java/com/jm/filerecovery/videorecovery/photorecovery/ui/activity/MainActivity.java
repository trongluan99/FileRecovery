package com.jm.filerecovery.videorecovery.photorecovery.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.ads.control.AdmobHelp;
import com.ads.control.Rate;
import com.jm.filerecovery.videorecovery.photorecovery.BuildConfig;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.databinding.ActivityMainBinding;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto.adapter.PhotoRestoredAdapter;
import com.jm.filerecovery.videorecovery.photorecovery.utilts.FileUtils;
import com.jm.filerecovery.videorecovery.photorecovery.utilts.TotalMemoryStorageTaskUtils;
import com.jm.filerecovery.videorecovery.photorecovery.utilts.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 1234;
    private List<Callable<Void>> callables = new ArrayList<>();

    private PhotoRestoredAdapter photoRestoredAdapter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#66000000"));
        }
        intView();
        intEvent();

    }

    public void intView() {
        AdmobHelp.getInstance().loadBanner(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            intDataImage();
            initMemoryData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initMemoryData() {
        new TotalMemoryStorageTaskUtils((useMemory, totalMemory) -> {
            binding.tvMemory.setText(FileUtils.longToSizeText(totalMemory));
            binding.tvUsed.setText(FileUtils.longToSizeText(useMemory));
            binding.tvFree.setText(FileUtils.longToSizeText(totalMemory - useMemory));
            int progress = (int) (100.0f * useMemory / totalMemory);
            binding.circularProgress.setProgress(progress, 100L);
            binding.tvPercent.setText(progress + "%");
        }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    public void intDataImage() {
        String data = getString(R.string.see_all);
        SpannableString span = new SpannableString(data);
        ClickableSpan ClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(new Intent(MainActivity.this, FileRecoveredActivity.class));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setColor(Color.parseColor("#EA1763"));
            }
        };
        span.setSpan(
                ClickableSpan,
                0,
                span.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        binding.tvSeeAll.setText(span);
        binding.tvSeeAll.setMovementMethod(LinkMovementMethod.getInstance());

        File fileDirectory = new File(Utils.getPathSave(this, getString(R.string.restore_folder_path_photo)));
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
        }
        File[] lstFilePhoto = fileDirectory.listFiles() != null ? fileDirectory.listFiles() : new File[0];
        binding.tvNumberImage.setText(lstFilePhoto.length + "");

        photoRestoredAdapter = new PhotoRestoredAdapter(lstFilePhoto, file -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri photoURI = FileProvider.getUriForFile(this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    file);
            intent.setDataAndType(photoURI, "image/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(intent, getString(R.string.view_using)));
        });
        binding.rcvPhoto.setAdapter(photoRestoredAdapter);
    }

    public void intEvent() {
        binding.ivImageClick.setOnClickListener(this);
        binding.ivAudioClick.setOnClickListener(this);
        binding.ivVideoClick.setOnClickListener(this);
        binding.ivHisClick.setOnClickListener(this);
        binding.ivMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_more:
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_his_click:
                startActivity(new Intent(MainActivity.this, FileRecoveredActivity.class));
                break;
            case R.id.iv_image_click:
                try {
                    requestPermissionAll(() -> {
                        ScanFilesActivity.start(MainActivity.this, 0);
                        return null;
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.iv_audio_click:
                try {
                    requestPermissionAll(() -> {
                        ScanFilesActivity.start(MainActivity.this, 2);
                        return null;
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.iv_video_click:
                try {
                    requestPermissionAll(() -> {
                        ScanFilesActivity.start(MainActivity.this, 1);
                        return null;
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2296) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    for (Callable callable : callables) {
                        try {
                            callable.call();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    callables.clear();
                } else {
                    Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.lvAds:

                return true;

            default:
                return super.onOptionsItemSelected(item);
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


    @Override
    public void onBackPressed() {
        Rate.Show(this, 1);
    }

}
