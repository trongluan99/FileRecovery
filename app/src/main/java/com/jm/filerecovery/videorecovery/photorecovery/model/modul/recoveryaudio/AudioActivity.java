package com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ads.control.ads.AperoAd;
import com.ads.control.ads.AperoAdCallback;
import com.ads.control.ads.AperoInitCallback;
import com.ads.control.ads.wrapper.ApAdError;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo.Model.VideoEntity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo.VideoActivity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo.task.RecoverVideoAsyncTask;
import com.jm.filerecovery.videorecovery.photorecovery.ui.InviteWatchAdsActivity;
import com.jm.filerecovery.videorecovery.photorecovery.ui.activity.RestoreResultActivity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.Model.AudioEntity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.adapter.FileAudioAdapter;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.task.RecoverAudioAsyncTask;
import com.jm.filerecovery.videorecovery.photorecovery.ui.activity.ScanFilesActivity;
import com.jm.filerecovery.videorecovery.photorecovery.utils.ButtonRestore;
import com.jm.filerecovery.videorecovery.photorecovery.utils.Utils;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by deepshikha on 20/3/17.
 */

public class AudioActivity extends AppCompatActivity {
    int int_position;
    RecyclerView recyclerView;
    FileAudioAdapter fileAudioAdapter;
    TextView txt_recovery_now;
    ArrayList<AudioEntity> mList = new ArrayList<AudioEntity>();
    RecoverAudioAsyncTask mRecoverPhotosAsyncTask;
    AppCompatImageView imgBack;
    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view_files);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        intView();
        intData();
        initAds();
        initStatusBar();
        restore = true;
    }

    private void initAds() {
        FrameLayout frameLayout = findViewById(R.id.fl_adplaceholder);
        ShimmerFrameLayout shimmerFrameLayout = findViewById(R.id.shimmer_container_native);
        AperoAd.getInstance().loadNativeAd(this, getResources().getString(R.string.admob_native_recovery_item), R.layout.custom_native_no_media, frameLayout, shimmerFrameLayout);
    }

    private void initStatusBar() {
        try {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(uiOptions);
        } catch (Exception e) {

        }
    }

    public void intView() {
        imgBack = findViewById(R.id.img_back);
        txtTitle = findViewById(R.id.txt_recovery);
        txtTitle.setText(getString(R.string.audio_recovery));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        txt_recovery_now = (TextView) findViewById(R.id.txt_recovery_now);
        recyclerView = (RecyclerView) findViewById(R.id.gv_folder);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new AudioActivity.GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void intData() {
        int_position = getIntent().getIntExtra("value", 0);
        if (ScanFilesActivity.mAlbumAudio != null && ScanFilesActivity.mAlbumAudio.size() > int_position)
            mList.addAll((ArrayList<AudioEntity>) ScanFilesActivity.mAlbumAudio.get(int_position).getListPhoto().clone());
        fileAudioAdapter = new FileAudioAdapter(this, mList);
        recyclerView.setAdapter(fileAudioAdapter);
        txt_recovery_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArrayList<AudioEntity> tempList = fileAudioAdapter.getSelectedItem();
                if (tempList.size() == 0) {
                    Toast.makeText(AudioActivity.this, "Cannot restore, all items are unchecked!", Toast.LENGTH_LONG).show();
                } else {

                    AperoAdCallback adCallback = new AperoAdCallback() {
                        @Override
                        public void onNextAction() {
                            super.onNextAction();
                        }

                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                            restoreFile();
                        }

                        @Override
                        public void onAdFailedToShow(@Nullable ApAdError adError) {
                            super.onAdFailedToShow(adError);
                            restoreFile();
                        }

                        @Override
                        public void onAdFailedToLoad(@Nullable ApAdError adError) {
                            super.onAdFailedToLoad(adError);
                            restoreFile();
                        }
                    };
                    AperoAd.getInstance().setInitCallback(new AperoInitCallback() {
                        @Override
                        public void initAdSuccess() {
                            AperoAd.getInstance().loadSplashInterstitialAds(AudioActivity.this, getResources().getString(R.string.admob_inter_recovery), 5000, 0, true, adCallback);
                        }
                    });

                }

            }
        });
    }

    boolean restore = true;
    private void restoreFile() {
        if(!restore) return;
        final ArrayList<AudioEntity> tempList = fileAudioAdapter.getSelectedItem();
        mRecoverPhotosAsyncTask = new RecoverAudioAsyncTask(AudioActivity.this, fileAudioAdapter.getSelectedItem(), new RecoverAudioAsyncTask.OnRestoreListener() {
            @Override
            public void onComplete() {
                Intent intent = new Intent(getApplicationContext(), RestoreResultActivity.class);
                intent.putExtra("value", tempList.size());
                intent.putExtra("type", 2);
                startActivity(intent);
                fileAudioAdapter.setAllImagesUnseleted();
                fileAudioAdapter.notifyDataSetChanged();
            }
        });
        mRecoverPhotosAsyncTask.execute();
        restore = false;
    }
    @Override
    protected void onResume() {
        super.onResume();
        restore = true;
    }


    public boolean SDCardCheck() {
        File[] storages = ContextCompat.getExternalFilesDirs(this, null);
        if (storages.length <= 1 || storages[0] == null || storages[1] == null) {
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("AdmobHelper", " requestCode =" + requestCode + " resultCode = " + resultCode);
        if (requestCode == 10900 && resultCode == 888) {
            final ArrayList<AudioEntity> tempList = fileAudioAdapter.getSelectedItem();
            mRecoverPhotosAsyncTask = new RecoverAudioAsyncTask(AudioActivity.this, fileAudioAdapter.getSelectedItem(), new RecoverAudioAsyncTask.OnRestoreListener() {
                @Override
                public void onComplete() {
                    Intent intent = new Intent(getApplicationContext(), RestoreResultActivity.class);
                    intent.putExtra("value", tempList.size());
                    intent.putExtra("type", 2);
                    startActivity(intent);
                    fileAudioAdapter.setAllImagesUnseleted();
                    fileAudioAdapter.notifyDataSetChanged();
                }
            });
            mRecoverPhotosAsyncTask.execute();
        }
    }

    //    private void showDalogConfirmDelete() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(DuplicateActivity.this);
//        builder.setTitle(getString(R.string.delete_title));
//        builder.setMessage(getString(R.string.are_you_sure_to_delete));
//
//        String positiveText = getString(android.R.string.ok);
//        builder.setPositiveButton(positiveText,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (!sharedPreferences.getString("sdCardUri", "").equals("")) {
//                            deleteFiles();
//                        } else if (SDCardCheck()) {
//
//                            SDcardFilesDialog();
//                        } else {
//
//                            deleteFiles();
//                        }
//
//                        dialog.dismiss();
//                    }
//                });
//
//        String negativeText = getString(android.R.string.cancel);
//        builder.setNegativeButton(negativeText,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // negative button logic
//                    }
//                });
//
//        AlertDialog dialog = builder.create();
//        // display dialog
//        dialog.show();
//    }
//    private void SDcardFilesDialog() {
//
//        final Dialog main_dialog2 = new ProgressDialog(this);
//        main_dialog2.requestWindowFeature(1);
//        main_dialog2.setCancelable(false);
//        main_dialog2.setCanceledOnTouchOutside(false);
//        main_dialog2.show();
//        main_dialog2.setContentView(R.layout.sdcard_dialog);
//        ((Button) main_dialog2.findViewById(R.id.ok_sd)).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (main_dialog2 != null) {
//                    main_dialog2.dismiss();
//                }
//                fileSearch();
//            }
//        });
//    }
    public void fileSearch() {
        startActivityForResult(new Intent("android.intent.action.OPEN_DOCUMENT_TREE"), 100);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column
            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
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
    }
}
