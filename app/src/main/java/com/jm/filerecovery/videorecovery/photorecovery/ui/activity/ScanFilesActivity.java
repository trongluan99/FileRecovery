package com.jm.filerecovery.videorecovery.photorecovery.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.os.EnvironmentCompat;

import com.ads.control.ads.ITGAd;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.jm.filerecovery.videorecovery.photorecovery.BaseActivity;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.databinding.ActivityScanningBinding;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.AlbumAudioActivity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.Model.AlbumAudio;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.Model.AudioEntity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto.AlbumPhotoActivity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto.Model.AlbumPhoto;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto.Model.PhotoEntity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo.AlbumVideoActivity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo.Model.AlbumVideo;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo.Model.VideoEntity;
import com.jm.filerecovery.videorecovery.photorecovery.utils.Utils;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScanFilesActivity extends BaseActivity implements BaseActivity.PreLoadNativeListener {

    public static ArrayList<AlbumAudio> mAlbumAudio = new ArrayList<>();
    public static ArrayList<AlbumVideo> mAlbumVideo = new ArrayList<>();
    public static ArrayList<AlbumPhoto> mAlbumPhoto = new ArrayList<>();
    private ScanAsyncTask mScanAsyncTask;
    int typeScan = 0;
    private ActivityScanningBinding binding;

    FrameLayout frameLayout;
    ShimmerFrameLayout shimmerFrameLayout;
    boolean populateNativeAdView = false;


    public static void start(Context context, int type) {
        Intent intent = new Intent(context, ScanFilesActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScanningBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        Toolbar ctrToolbar = findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Utils.getHeightStatusBar(this) > 0) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) ctrToolbar.getLayoutParams();
            params.setMargins(0, Utils.getHeightStatusBar(this), 0, 0);
            ctrToolbar.setLayoutParams(params);
        }
        Utils.setStatusBarHomeTransparent(this);

        setSupportActionBar(ctrToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        intView();
    }

    private void intView() {
        Log.d("TuanPA38", "ScanFilesActivity intView");
        frameLayout = findViewById(R.id.fl_adplaceholder);
        shimmerFrameLayout = findViewById(R.id.shimmer_container_native);

        // Begin: Add Ads
        if (!populateNativeAdView) {
            if (nativeAdViewScan != null) {
                ITGAd.getInstance().populateNativeAdView(this, nativeAdViewScan, frameLayout, shimmerFrameLayout);
                populateNativeAdView = true;
            }
        }

        loadNativeListItem();
        // End


        int type = getIntent().getIntExtra("type", 0);
        scanType(type);
        binding.buttonScanNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeScan == 0) {
                    if (mAlbumPhoto.size() == 0) {
                        Intent intent = new Intent(getApplicationContext(), NoFileActiviy.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), AlbumPhotoActivity.class);
                        startActivity(intent);
                    }
                    finish();
                }
                if (typeScan == 1) {
                    if (mAlbumVideo.size() == 0) {
                        Intent intent = new Intent(getApplicationContext(), NoFileActiviy.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), AlbumVideoActivity.class);
                        startActivity(intent);
                    }
                    finish();
                }
                if (typeScan == 2) {
                    if (mAlbumAudio.size() == 0) {
                        Intent intent = new Intent(getApplicationContext(), NoFileActiviy.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), AlbumAudioActivity.class);
                        startActivity(intent);
                    }
                    finish();
                }
            }
        });
    }

    @Override
    public void onLoadNativeSuccess() {
        // Begin: Add Ads
        if (!populateNativeAdView) {
            if (nativeAdViewScan != null) {
                ITGAd.getInstance().populateNativeAdView(this, nativeAdViewScan, frameLayout, shimmerFrameLayout);
                populateNativeAdView = true;
            }
        }

        // End
    }

    @Override
    public void onLoadNativeFail() {
        frameLayout.removeAllViews();

    }

    @Override
    public void onLoadNativeLanguageSuccess() {

    }

    @Override
    public void onLoadNativeLanguageFail() {

    }

    @Override
    public void onLoadNativeHomeSuccess() {

    }

    @Override
    public void onLoadNativeHomeFail() {

    }

    @Override
    public void onLoadNativeTutorial() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
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
            Toast.makeText(ScanFilesActivity.this, getString(R.string.scan_wait), Toast.LENGTH_LONG).show();
        } else {
            mAlbumAudio.clear();
            mAlbumPhoto.clear();
            mAlbumVideo.clear();
            binding.tvStatus.setText(getString(R.string.analyzing));
            mScanAsyncTask = new ScanAsyncTask(Type);
            mScanAsyncTask.execute();
        }
    }

    public class ScanAsyncTask extends AsyncTask<Void, Integer, Void> {
        ArrayList<PhotoEntity> listPhoto = new ArrayList<>();
        ArrayList<VideoEntity> listVideo = new ArrayList<>();
        ArrayList<AudioEntity> listAudio = new ArrayList<>();
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
            binding.buttonScanNext.setVisibility(View.VISIBLE);
            binding.imgDone.cancelAnimation();
        }

        @SuppressLint("StringFormatInvalid")
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            binding.tvStatus.setText(getString(R.string.files, values[0]));
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
                                listPhoto.add(new PhotoEntity(fileArr[i].getPath(), file.lastModified(), file_size));
                                number = number + 1;
                                publishProgress(number);

                            }

                        } else {
                            File file = new File(fileArr[i].getPath());
                            int file_size = Integer.parseInt(String.valueOf(file.length()));
                            if (file_size > 50000) {
                                listPhoto.add(new PhotoEntity(fileArr[i].getPath(), file.lastModified(), file_size));
                                number = number + 1;
                                publishProgress(number);

                            }
                        }


                    }
                }
            }

            if (listPhoto.size() != 0 && !temp.contains(Utils.getPathSave(ScanFilesActivity.this, getString(R.string.restore_folder_path_photo)))) {
                AlbumPhoto albumPhoto = new AlbumPhoto();
                albumPhoto.setStr_folder(temp);
                albumPhoto.setLastModified(new File(temp).lastModified());
                Collections.sort(listPhoto, new Comparator<PhotoEntity>() {
                    @Override
                    public int compare(PhotoEntity lhs, PhotoEntity rhs) {

                        return Long.valueOf(rhs.getLastModified()).compareTo(lhs.getLastModified());
                    }
                });
                albumPhoto.setListPhoto((ArrayList<PhotoEntity>) listPhoto.clone());
                mAlbumPhoto.add(albumPhoto);
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
                            listVideo.add(new VideoEntity(fileArr[i].getPath(), file.lastModified(), file.length(), type, Utils.convertDuration(duration)));
                            number = number + 1;
                            publishProgress(number);

                        }


                    }
                }

                if (listVideo.size() != 0 && !temp.contains(Utils.getPathSave(ScanFilesActivity.this, getString(R.string.restore_folder_path_video)))) {
                    AlbumVideo albumVideo = new AlbumVideo();
                    albumVideo.setStr_folder(temp);
                    albumVideo.setLastModified(new File(temp).lastModified());
                    Collections.sort(listVideo, new Comparator<VideoEntity>() {
                        @Override
                        public int compare(VideoEntity lhs, VideoEntity rhs) {

                            return Long.valueOf(rhs.getLastModified()).compareTo(lhs.getLastModified());
                        }
                    });
                    albumVideo.setListPhoto((ArrayList<VideoEntity>) listVideo.clone());
                    mAlbumVideo.add(albumVideo);
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
                            listAudio.add(new AudioEntity(fileArr[i].getPath(), file.lastModified(), file_size));
                            number = number + 1;
                            publishProgress(number);

                        }


                    }

                }
            }

            if (listAudio.size() != 0 && !temp.contains(Utils.getPathSave(ScanFilesActivity.this, getString(R.string.restore_folder_path_audio)))) {
                AlbumAudio albumAudio = new AlbumAudio();
                albumAudio.setStr_folder(temp);
                albumAudio.setLastModified(new File(temp).lastModified());
                Collections.sort(listAudio, new Comparator<AudioEntity>() {
                    @Override
                    public int compare(AudioEntity lhs, AudioEntity rhs) {

                        return Long.valueOf(rhs.getLastModified()).compareTo(lhs.getLastModified());
                    }
                });
                albumAudio.setListPhoto((ArrayList<AudioEntity>) listAudio.clone());
                mAlbumAudio.add(albumAudio);
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
    public void onBackPressed() {
        if (this.mScanAsyncTask != null && this.mScanAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            Toast.makeText(ScanFilesActivity.this, getString(R.string.scan_wait), Toast.LENGTH_LONG).show();
        } else {
            super.onBackPressed();
        }

    }


}
