package com.lubuteam.sellsource.filerecovery.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.os.EnvironmentCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ads.control.AdmobHelp;
import com.ads.control.Rate;
import com.ads.control.funtion.UtilsApp;
import com.airbnb.lottie.LottieAnimationView;
import com.lubuteam.sellsource.filerecovery.R;
import com.lubuteam.sellsource.filerecovery.model.modul.recoveryaudio.AlbumAudioActivity;
import com.lubuteam.sellsource.filerecovery.model.modul.recoveryaudio.Model.AlbumAudio;
import com.lubuteam.sellsource.filerecovery.model.modul.recoveryaudio.Model.AudioModel;
import com.lubuteam.sellsource.filerecovery.model.modul.recoveryphoto.AlbumPhotoActivity;
import com.lubuteam.sellsource.filerecovery.model.modul.recoveryphoto.Model.AlbumPhoto;
import com.lubuteam.sellsource.filerecovery.model.modul.recoveryphoto.Model.PhotoModel;
import com.lubuteam.sellsource.filerecovery.model.modul.recoveryvideo.AlbumVideoActivity;
import com.lubuteam.sellsource.filerecovery.model.modul.recoveryvideo.Model.AlbumVideo;
import com.lubuteam.sellsource.filerecovery.model.modul.recoveryvideo.Model.VideoModel;
import com.lubuteam.sellsource.filerecovery.utilts.Utils;
import com.google.android.material.navigation.NavigationView;
import com.romainpiel.shimmer.Shimmer;
import com.skyfishjy.library.RippleBackground;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    Toolbar toolbar;
    Shimmer shimmer;
    ImageButton btnScan;
    TextView tvNumber;
    LottieAnimationView ivSearch;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 1234;
    private List<Callable<Void>> callables = new ArrayList<>();
    public static ArrayList<AlbumAudio> mAlbumAudio = new ArrayList<>();
    public static ArrayList<AlbumVideo> mAlbumVideo = new ArrayList<>();
    public static ArrayList<AlbumPhoto> mAlbumPhoto = new ArrayList<>();
    ScanAsyncTask mScanAsyncTask;
    RippleBackground rippleBackground;
    private ArrayList<String> arrPermission;
    CardView cvImage, cvAudio, cvVideo, cvSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intDrawer();
        intView();
        intData();
        intEvent();

    }

    public void intDrawer() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));

        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        int id = menuItem.getItemId();
                        switch (id) {
//                            case R.id.nav_view_restored_photos:
//                                Intent intent = new Intent(getApplicationContext(), ViewAudioActivity.class);
//                                startActivity(intent);
//                                break;
                            case R.id.nav_policy:
                                UtilsApp.OpenBrower(MainActivity.this, getString(R.string.link_policy));

                                break;
                            case R.id.nav_share:
                                UtilsApp.shareApp(MainActivity.this);

                                break;

                            case R.id.nav_send:
                                UtilsApp.SendFeedBack(MainActivity.this, getString(R.string.Title_email), getString(R.string.email_feedback));

                                break;
                            default:
                                break;
                        }


                        mDrawerLayout.closeDrawers();


                        return true;
                    }
                });
        AdmobHelp.getInstance().loadBanner(this);


    }

    String[] projection = {MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.MIME_TYPE};

    public void intView() {
        btnScan = (ImageButton) findViewById(R.id.btnScan);
        tvNumber = (TextView) findViewById(R.id.tvNumber);
        ivSearch = (LottieAnimationView) findViewById(R.id.ivSearch);
        rippleBackground = (RippleBackground) findViewById(R.id.im_scan_bg);

        cvImage = (CardView) findViewById(R.id.cvImage);
        cvAudio = (CardView) findViewById(R.id.cvAudio);
        cvVideo = (CardView) findViewById(R.id.cvVideo);
        cvSetting = (CardView) findViewById(R.id.cvSetting);
    }

    public void intData() {
    }

    public void intEvent() {
        cvImage.setOnClickListener(this);
        cvAudio.setOnClickListener(this);
        cvVideo.setOnClickListener(this);
        cvSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cvSetting:
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;

            case R.id.cvImage:
                try {
                    requestPermissionAll(() -> {
                        scanType(0);
                        return null;
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.cvAudio:
                try {
                    requestPermissionAll(() -> {
                        scanType(2);
                        return null;
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.cvVideo:
                try {
                    requestPermissionAll(() -> {
                        scanType(1);
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

    public void scanType(int Type) {
        File fileDirectoryAudio = new File(Utils.getPathSave(this, getString(R.string.restore_folder_path_audio)));
        if (!fileDirectoryAudio.exists()) {
            fileDirectoryAudio.mkdirs();
        }
        File fileDirectoryVideo = new File(Utils.getPathSave(this, getString(R.string.restore_folder_path_video)));
        if (!fileDirectoryVideo.exists()) {
            fileDirectoryVideo.mkdirs();
        }
        File fileDirectoryPhoto = new File(Utils.getPathSave(this, getString(R.string.restore_folder_path_photo)));
        if (!fileDirectoryPhoto.exists()) {
            fileDirectoryPhoto.mkdirs();
        }
        if (this.mScanAsyncTask != null && this.mScanAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            Toast.makeText(MainActivity.this, getString(R.string.scan_wait), Toast.LENGTH_LONG).show();
        } else {
            mAlbumAudio.clear();
            mAlbumPhoto.clear();
            mAlbumVideo.clear();
            tvNumber.setVisibility(View.VISIBLE);
            tvNumber.setText(getString(R.string.analyzing));
            ivSearch.playAnimation();
            rippleBackground.startRippleAnimation();
            mScanAsyncTask = new ScanAsyncTask(Type);
            mScanAsyncTask.execute();

        }
    }

    public class ScanAsyncTask extends AsyncTask<Void, Integer, Void> {
        int typeScan = 0;
        ArrayList<PhotoModel> listPhoto = new ArrayList<>();
        ArrayList<VideoModel> listVideo = new ArrayList<>();
        ArrayList<AudioModel> listAudio = new ArrayList<>();
        int number = 0;

        public ScanAsyncTask(int type) {
            typeScan = type;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            number = 0;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            rippleBackground.stopRippleAnimation();
            ivSearch.pauseAnimation();
            ivSearch.setProgress(0);
            tvNumber.setText("");
            tvNumber.setVisibility(View.INVISIBLE);
            if (typeScan == 0) {
                if (mAlbumPhoto.size() == 0) {
                    Intent intent = new Intent(getApplicationContext(), NoFileActiviy.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), AlbumPhotoActivity.class);
                    startActivity(intent);
                }
            }
            if (typeScan == 1) {
                if (mAlbumVideo.size() == 0) {
                    Intent intent = new Intent(getApplicationContext(), NoFileActiviy.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), AlbumVideoActivity.class);
                    startActivity(intent);

                }
            }
            if (typeScan == 2) {
                if (mAlbumAudio.size() == 0) {
                    Intent intent = new Intent(getApplicationContext(), NoFileActiviy.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), AlbumAudioActivity.class);
                    startActivity(intent);
                }
            }


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tvNumber.setText("Files: " + String.valueOf(values[0]));
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String strArr;
            strArr = Environment.getExternalStorageDirectory().getAbsolutePath();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("root = ");
            stringBuilder.append(strArr);
            //Day la tat ca thu muc trong may
            if (typeScan == 0) {
                try {
                    getSdCardImage();
                    checkFileOfDirectoryImage(strArr, Utils.getFileList(strArr));
                } catch (Exception e) {

                }
                Collections.sort(mAlbumPhoto, new Comparator<AlbumPhoto>() {
                    @Override
                    public int compare(AlbumPhoto lhs, AlbumPhoto rhs) {

                        return Long.valueOf(rhs.getLastModified()).compareTo(lhs.getLastModified());
                    }
                });
            }
            if (typeScan == 1) {
                getSdCardVideo();
                checkFileOfDirectoryVideo(strArr, Utils.getFileList(strArr));
                Collections.sort(mAlbumVideo, new Comparator<AlbumVideo>() {
                    @Override
                    public int compare(AlbumVideo lhs, AlbumVideo rhs) {

                        return Long.valueOf(rhs.getLastModified()).compareTo(lhs.getLastModified());
                    }
                });
            }
            if (typeScan == 2) {
                try {
                    getSdCardAudio();
                    checkFileOfDirectoryAudio(strArr, Utils.getFileList(strArr));
                } catch (Exception e) {

                }
                Collections.sort(mAlbumAudio, new Comparator<AlbumAudio>() {
                    @Override
                    public int compare(AlbumAudio lhs, AlbumAudio rhs) {

                        return Long.valueOf(rhs.getLastModified()).compareTo(lhs.getLastModified());
                    }
                });
            }


            try {

                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        public void checkFileOfDirectoryImage(String temp, File[] fileArr) {

            for (int i = 0; i < fileArr.length; i++) {
                if (fileArr[i].isDirectory()) {
                    String temp_sub = fileArr[i].getPath();
                    File[] mfileArr = Utils.getFileList(fileArr[i].getPath());
                    if (temp_sub != null && mfileArr != null && mfileArr.length > 0)
                        checkFileOfDirectoryImage(temp_sub, mfileArr);
                } else {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(fileArr[i].getPath(), options);
                    if (!(options.outWidth == -1 || options.outHeight == -1)) {
                        if (fileArr[i].getPath().endsWith(".jpg")
                                || fileArr[i].getPath().endsWith(".jpeg")
                                || fileArr[i].getPath().endsWith(".png")
                                || fileArr[i].getPath().endsWith(".gif")) {

                            File file = new File(fileArr[i].getPath());
                            int file_size = Integer.parseInt(String.valueOf(file.length()));
                            if (file_size > 10000) {
                                listPhoto.add(new PhotoModel(fileArr[i].getPath(), file.lastModified(), file_size));
                                number = number + 1;
                                publishProgress(number);

                            }

                        } else {
                            File file = new File(fileArr[i].getPath());
                            int file_size = Integer.parseInt(String.valueOf(file.length()));
                            if (file_size > 50000) {
                                listPhoto.add(new PhotoModel(fileArr[i].getPath(), file.lastModified(), file_size));
                                number = number + 1;
                                publishProgress(number);

                            }
                        }


                    }
                }
            }

            if (listPhoto.size() != 0 && !temp.contains(Utils.getPathSave(MainActivity.this, getString(R.string.restore_folder_path_photo)))) {
                AlbumPhoto obj_model = new AlbumPhoto();
                obj_model.setStr_folder(temp);
                obj_model.setLastModified(new File(temp).lastModified());
                Collections.sort(listPhoto, new Comparator<PhotoModel>() {
                    @Override
                    public int compare(PhotoModel lhs, PhotoModel rhs) {

                        return Long.valueOf(rhs.getLastModified()).compareTo(lhs.getLastModified());
                    }
                });
                obj_model.setListPhoto((ArrayList<PhotoModel>) listPhoto.clone());
                mAlbumPhoto.add(obj_model);
            }
            listPhoto.clear();

        }

        public void getSdCardImage() {

            String[] externalStoragePaths = getExternalStorageDirectories();

            if (externalStoragePaths != null && externalStoragePaths.length > 0) {

                for (String path : externalStoragePaths) {
                    File file = new File(path);
                    if (file.exists()) {
                        File[] subFiles = file.listFiles();
                        checkFileOfDirectoryImage(path, subFiles);
                    }
                }
            }
        }

        public void checkFileOfDirectoryVideo(String temp, File[] fileArr) {
            if (fileArr != null) {
                for (int i = 0; i < fileArr.length; i++) {
                    if (fileArr[i].isDirectory()) {
                        String temp_sub = fileArr[i].getPath();
                        File[] mfileArr = Utils.getFileList(fileArr[i].getPath());
                        if (temp_sub != null && mfileArr != null && mfileArr.length > 0)
                            checkFileOfDirectoryVideo(temp_sub, mfileArr);
                    } else {
                        if (fileArr[i].getPath().endsWith(".3gp")
                                || fileArr[i].getPath().endsWith(".mp4")
                                || fileArr[i].getPath().endsWith(".mkv")
                                || fileArr[i].getPath().endsWith(".flv")) {

                            File file = new File(fileArr[i].getPath());
                            String type = fileArr[i].getPath().substring(fileArr[i].getPath().lastIndexOf(".") + 1);
                            long duration = 0;
                            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                            try {

                                retriever.setDataSource(file.getPath());
                                duration = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
                                retriever.release();
                            } catch (Exception e) {

                            }
                            listVideo.add(new VideoModel(fileArr[i].getPath(), file.lastModified(), file.length(), type, Utils.convertDuration(duration)));
                            number = number + 1;
                            publishProgress(number);

                        }


                    }
                }

                if (listVideo.size() != 0 && !temp.contains(Utils.getPathSave(MainActivity.this, getString(R.string.restore_folder_path_video)))) {
                    AlbumVideo obj_model = new AlbumVideo();
                    obj_model.setStr_folder(temp);
                    obj_model.setLastModified(new File(temp).lastModified());
                    Collections.sort(listVideo, new Comparator<VideoModel>() {
                        @Override
                        public int compare(VideoModel lhs, VideoModel rhs) {

                            return Long.valueOf(rhs.getLastModified()).compareTo(lhs.getLastModified());
                        }
                    });
                    obj_model.setListPhoto((ArrayList<VideoModel>) listVideo.clone());
                    mAlbumVideo.add(obj_model);
                }
                listVideo.clear();
            }


        }

        public void getSdCardVideo() {

            String[] externalStoragePaths = getExternalStorageDirectories();

            if (externalStoragePaths != null && externalStoragePaths.length > 0) {

                for (String path : externalStoragePaths) {
                    File file = new File(path);
                    if (file.exists()) {
                        File[] subFiles = file.listFiles();
                        checkFileOfDirectoryVideo(path, subFiles);
                    }
                }
            }
        }

        public void checkFileOfDirectoryAudio(String temp, File[] fileArr) {

            for (int i = 0; i < fileArr.length; i++) {
                if (fileArr[i].isDirectory()) {
                    String temp_sub = fileArr[i].getPath();
                    File[] mfileArr = Utils.getFileList(fileArr[i].getPath());
                    if (temp_sub != null && mfileArr != null && mfileArr.length > 0)
                        checkFileOfDirectoryAudio(temp_sub, mfileArr);
                } else {

                    if (fileArr[i].getPath().endsWith(".mp3")
                            || fileArr[i].getPath().endsWith(".aac")
                            || fileArr[i].getPath().endsWith(".amr")
                            || fileArr[i].getPath().endsWith(".m4a")
                            || fileArr[i].getPath().endsWith(".ogg")
                            || fileArr[i].getPath().endsWith(".wav")
                            || fileArr[i].getPath().endsWith(".flac")) {

                        File file = new File(fileArr[i].getPath());
                        int file_size = Integer.parseInt(String.valueOf(file.length()));
                        if (file_size > 10000) {
                            listAudio.add(new AudioModel(fileArr[i].getPath(), file.lastModified(), file_size));
                            number = number + 1;
                            publishProgress(number);

                        }


                    }

                }
            }

            if (listAudio.size() != 0 && !temp.contains(Utils.getPathSave(MainActivity.this, getString(R.string.restore_folder_path_audio)))) {
                AlbumAudio obj_model = new AlbumAudio();
                obj_model.setStr_folder(temp);
                obj_model.setLastModified(new File(temp).lastModified());
                Collections.sort(listAudio, new Comparator<AudioModel>() {
                    @Override
                    public int compare(AudioModel lhs, AudioModel rhs) {

                        return Long.valueOf(rhs.getLastModified()).compareTo(lhs.getLastModified());
                    }
                });
                obj_model.setListPhoto((ArrayList<AudioModel>) listAudio.clone());
                mAlbumAudio.add(obj_model);
            }
            listAudio.clear();

        }

        public void getSdCardAudio() {

            String[] externalStoragePaths = getExternalStorageDirectories();

            if (externalStoragePaths != null && externalStoragePaths.length > 0) {

                for (String path : externalStoragePaths) {
                    File file = new File(path);
                    if (file.exists()) {
                        File[] subFiles = file.listFiles();
                        checkFileOfDirectoryAudio(path, subFiles);
                    }
                }
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

    private void showNotFoundDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(getString(R.string.not_found_audio));

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    public String[] getExternalStorageDirectories() {
        List<String> results = new ArrayList();
        if (Build.VERSION.SDK_INT >= 19) {
            File[] externalDirs = getExternalFilesDirs(null);
            if (externalDirs != null && externalDirs.length > 0) {
                for (File file : externalDirs) {
                    if (file != null) {
                        String[] paths = file.getPath().split("/Android");
                        if (paths != null && paths.length > 0) {
                            boolean addPath;
                            String path = paths[0];
                            if (Build.VERSION.SDK_INT >= 21) {
                                addPath = Environment.isExternalStorageRemovable(file);
                            } else {
                                addPath = "mounted".equals(EnvironmentCompat.getStorageState(file));
                            }
                            if (addPath) {
                                results.add(path);
                            }
                        }
                    }
                }
            }
        }

        if (results.isEmpty()) {
            String output = "";
            InputStream is = null;
            try {
                Process process = new ProcessBuilder(new String[0]).command(new String[]{"mount | grep /dev/block/vold"}).redirectErrorStream(true).start();
                process.waitFor();
                is = process.getInputStream();
                byte[] buffer = new byte[1024];
                while (is.read(buffer) != -1) {
                    output = output + new String(buffer);
                }
                is.close();
            } catch (Exception e) {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e2) {

                    }
                }

            }
            if (!output.trim().isEmpty()) {
                String[] devicePoints = output.split(IOUtils.LINE_SEPARATOR_UNIX);
                if (devicePoints.length > 0) {
                    for (String voldPoint : devicePoints) {
                        results.add(voldPoint.split(" ")[2]);
                    }
                }
            }
        }

        String[] storageDirectories = new String[results.size()];
        for (int i = 0; i < results.size(); i++) {
            storageDirectories[i] = (String) results.get(i);
        }
        return storageDirectories;
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
        if (this.mScanAsyncTask != null && this.mScanAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            Toast.makeText(MainActivity.this, getString(R.string.scan_wait), Toast.LENGTH_LONG).show();
        } else {
            Rate.Show(this, 1);
        }

    }

}
