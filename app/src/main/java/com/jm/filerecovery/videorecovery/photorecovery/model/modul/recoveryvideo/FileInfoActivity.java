package com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.ads.control.ads.AperoAd;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo.Model.VideoEntity;
import com.jm.filerecovery.videorecovery.photorecovery.ui.activity.RestoreResultActivity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo.task.RecoverOneVideosAsyncTask;
import com.jm.filerecovery.videorecovery.photorecovery.utils.Utils;

import java.io.File;
import java.text.DateFormat;



import static java.security.AccessController.getContext;

public class FileInfoActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnOpen, btnShare, btnRestore;
    TextView tvDate, tvSize, tvType;
    ImageView ivVideo;
    VideoEntity videoEntity;
    Toolbar toolbar;
    RecoverOneVideosAsyncTask mRecoverOneVideosAsyncTask;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_info);
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

        FrameLayout frameLayout = findViewById(R.id.fl_adplaceholder);
        ShimmerFrameLayout shimmerFrameLayout = findViewById(R.id.shimmer_container_native);
        AperoAd.getInstance().loadNativeAd(this, getResources().getString(R.string.admob_native_recovery_item), R.layout.custom_native_full_size, frameLayout, shimmerFrameLayout);

        try {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(uiOptions);
        } catch (Exception e) {

        }
    }

    public void intView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.restore_photo));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        btnOpen = (Button) findViewById(R.id.btnOpen);
        btnShare = (Button) findViewById(R.id.btnShare);
        btnRestore = (Button) findViewById(R.id.btnRestore);

        tvDate = (TextView) findViewById(R.id.tvDate);
        tvSize = (TextView) findViewById(R.id.tvSize);
        tvType = (TextView) findViewById(R.id.tvType);
        ivVideo = (ImageView) findViewById(R.id.ivVideo);

    }

    public void intData() {
        Intent i = getIntent();
        videoEntity = (VideoEntity)i.getSerializableExtra("ojectVideo");
        tvDate.setText(DateFormat.getDateInstance().format(videoEntity.getLastModified())+"  "+ videoEntity.getTimeDuration());
        tvSize.setText(Utils.formatSize(videoEntity.getSizePhoto()));
        tvType.setText(videoEntity.getTypeFile());

        Glide.with(this)
                .load("file://" + videoEntity.getPathPhoto())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .centerCrop()
                .error(R.drawable.ic_error)
                .into(ivVideo);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


    }

    public void intEvent() {
        btnOpen.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        btnRestore.setOnClickListener(this);
        ivVideo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnOpen:
                openFile(videoEntity.getPathPhoto());
                break;
            case R.id.btnShare:
                shareVideo(videoEntity.getPathPhoto());
                break;
            case R.id.btnRestore:
              //  showDalogConfirmRestore();
                mRecoverOneVideosAsyncTask = new RecoverOneVideosAsyncTask(FileInfoActivity.this, videoEntity, new RecoverOneVideosAsyncTask.OnRestoreListener() {
                    @Override
                    public void onComplete() {
                        Intent intent = new Intent(getApplicationContext(), RestoreResultActivity.class);
                        intent.putExtra("value", 1);
                        startActivity(intent);
                        finish();

                    }
                });
                mRecoverOneVideosAsyncTask.execute();
                break;
            case R.id.ivVideo:
                openFile(videoEntity.getPathPhoto());
                break;
            default:
                break;
        }
    }

    public void cancleUIUPdate() {
        if (this.mRecoverOneVideosAsyncTask != null && this.mRecoverOneVideosAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            this.mRecoverOneVideosAsyncTask.cancel(true);
            this.mRecoverOneVideosAsyncTask = null;
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean SDCardCheck() {
        File[] storages = ContextCompat.getExternalFilesDirs(this, null);
        if (storages.length <= 1 || storages[0] == null || storages[1] == null) {
            return false;
        }
        return true;
    }

    public void fileSearch() {
        startActivityForResult(new Intent("android.intent.action.OPEN_DOCUMENT_TREE"), 100);
    }

    public void openFile(String path) {
        try {
            Intent createChooser;
            if (Build.VERSION.SDK_INT < 24) {
                Intent intent2 = new Intent("android.intent.action.VIEW");
                intent2.setDataAndType(Uri.fromFile(new File(path)), "video/*");
                createChooser = Intent.createChooser(intent2, "Complete action using");
            } else {
                File file = new File(path);
                Intent intent4 = new Intent("android.intent.action.VIEW");
                Uri contentUri2 = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
                grantUriPermission(getPackageName(), contentUri2, 1);
                intent4.setType("*/*");
                intent4.setData(contentUri2);
                intent4.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                createChooser = Intent.createChooser(intent4, "Complete action using");
            }
            this.startActivity(createChooser);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void shareVideo(String path) {
        try {
            Uri fileUri = FileProvider.getUriForFile(
                    this, this.getPackageName() +
                            ".provider",
                    new File(path)
            );

            Intent Shareintent = new Intent()
                    .setAction(Intent.ACTION_SEND)
                    .setType("video/*")
                    .setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    .putExtra(Intent.EXTRA_STREAM, fileUri);
            this.startActivity(Intent.createChooser(Shareintent, ""));
        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cancleUIUPdate();
    }

    @TargetApi(21)
    private static boolean checkIfSDCardRoot(Uri uri) {
        return isExternalStorageDocument(uri) && isRootUri(uri) && !isInternalStorage(uri);
    }

    @RequiresApi(api = 21)
    private static boolean isRootUri(Uri uri) {
        return DocumentsContract.getTreeDocumentId(uri).endsWith(":");
    }

    @RequiresApi(api = 21)
    public static boolean isInternalStorage(Uri uri) {
        return isExternalStorageDocument(uri) && DocumentsContract.getTreeDocumentId(uri).contains("primary");
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == -1) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            boolean f = false;
            if (data != null) {
                Uri uri = data.getData();
                if (Build.VERSION.SDK_INT >= 19 && getContext() != null) {
                    getContentResolver().takePersistableUriPermission(uri, 3);
                }
                if (checkIfSDCardRoot(uri)) {
                    editor.putString("sdCardUri", uri.toString());
                    editor.putBoolean("storagePermission", true);
                    f = true;
                } else {
                    Toast.makeText(this, "Please Select Right SD Card.", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("storagePermission", false);
                    editor.putString("sdCardUri", "");
                }
            } else {
                Toast.makeText(this, "Please Select Right SD Card.", Toast.LENGTH_SHORT).show();
                editor.putString("sdCardUri", "");
            }
            if (editor.commit()) {
                editor.apply();
                if (f) {
                    mRecoverOneVideosAsyncTask = new RecoverOneVideosAsyncTask(FileInfoActivity.this, videoEntity, new RecoverOneVideosAsyncTask.OnRestoreListener() {
                        @Override
                        public void onComplete() {
                            Intent intent = new Intent(getApplicationContext(), RestoreResultActivity.class);
                            intent.putExtra("value", 1);
                            intent.putExtra("type", 1);
                            startActivity(intent);
                            finish();

                        }
                    });
                    mRecoverOneVideosAsyncTask.execute();
                }
            }
        }
        if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                finish();
            }

        }
    }
}
