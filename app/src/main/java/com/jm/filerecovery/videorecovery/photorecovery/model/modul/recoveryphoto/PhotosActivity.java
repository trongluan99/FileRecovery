package com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ads.control.ads.AperoAd;
import com.ads.control.ads.AperoAdCallback;
import com.ads.control.ads.AperoInitCallback;
import com.ads.control.ads.wrapper.ApAdError;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.jm.filerecovery.videorecovery.photorecovery.BaseActivity;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.AudioActivity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.Model.AudioEntity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.task.RecoverAudioAsyncTask;
import com.jm.filerecovery.videorecovery.photorecovery.ui.InviteWatchAdsActivity;
import com.jm.filerecovery.videorecovery.photorecovery.ui.activity.RestoreResultActivity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto.Model.PhotoEntity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto.adapter.FilePhotoAdapter;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto.task.RecoverPhotosAsyncTask;
import com.jm.filerecovery.videorecovery.photorecovery.ui.activity.ScanFilesActivity;
import com.jm.filerecovery.videorecovery.photorecovery.utils.ButtonRestore;
import com.jm.filerecovery.videorecovery.photorecovery.utils.Utils;

import java.io.File;
import java.util.ArrayList;


public class PhotosActivity extends BaseActivity implements FilePhotoAdapter.OnClickItem{
    int int_position;
    RecyclerView recyclerView;
    FilePhotoAdapter filePhotoAdapter;
    TextView txt_recovery_now;
    ArrayList<PhotoEntity> mList = new ArrayList<PhotoEntity>();
    RecoverPhotosAsyncTask mRecoverPhotosAsyncTask;
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
        restore = true;
    }

    private void initAds() {
        FrameLayout frameLayout = findViewById(R.id.fl_adplaceholder);
        ShimmerFrameLayout shimmerFrameLayout = findViewById(R.id.shimmer_container_native);
        AperoAd.getInstance().loadNativeAd(this, getResources().getString(R.string.admob_native_recovery_item), R.layout.custom_native_no_media, frameLayout, shimmerFrameLayout);
    }

    public void intView() {
        imgBack = findViewById(R.id.img_back);
        txtTitle = findViewById(R.id.txt_recovery);
        txtTitle.setText(getString(R.string.photo_recovery));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        txt_recovery_now = (TextView) findViewById(R.id.txt_recovery_now);
        recyclerView = (RecyclerView) findViewById(R.id.gv_folder);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new PhotosActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void intData() {
        int_position = getIntent().getIntExtra("value", 0);
        if (ScanFilesActivity.mAlbumPhoto != null && ScanFilesActivity.mAlbumPhoto.size() > int_position)
            mList.addAll((ArrayList<PhotoEntity>) ScanFilesActivity.mAlbumPhoto.get(int_position).getListPhoto().clone());
        filePhotoAdapter = new FilePhotoAdapter(this, mList);
        filePhotoAdapter.setOnClickItem(this);
        recyclerView.setAdapter(filePhotoAdapter);
        txt_recovery_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArrayList<PhotoEntity> tempList = filePhotoAdapter.getSelectedItem();
                if (tempList.size() == 0) {
                    Toast.makeText(PhotosActivity.this, "Cannot restore, all items are unchecked!", Toast.LENGTH_LONG).show();
                } else {
                    AperoAdCallback adCallback = new AperoAdCallback() {
                        @Override
                        public void onNextAction() {
                            super.onNextAction();
                            Log.d("TuanPA38", " onNextAction ");
                        }

                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                            restoreFile();
                            Log.d("TuanPA38", " onAdClosed ");
                        }

                        @Override
                        public void onAdFailedToShow(@Nullable ApAdError adError) {
                            super.onAdFailedToShow(adError);
                            restoreFile();
                            Log.d("TuanPA38", " onAdFailedToShow ");
                        }

                        @Override
                        public void onAdFailedToLoad(@Nullable ApAdError adError) {
                            super.onAdFailedToLoad(adError);
                            restoreFile();
                            Log.d("TuanPA38", " onAdFailedToLoad ");
                        }
                    };
                    AperoAd.getInstance().setInitCallback(new AperoInitCallback() {
                        @Override
                        public void initAdSuccess() {
                            AperoAd.getInstance().loadSplashInterstitialAds(PhotosActivity.this, getResources().getString(R.string.admob_inter_recovery), 5000, 0, true, adCallback);
                        }
                    });

                }

            }
        });
    }
    boolean restore = true;
    private void restoreFile() {
        if(!restore) return;
        final ArrayList<PhotoEntity> tempList = filePhotoAdapter.getSelectedItem();
        mRecoverPhotosAsyncTask = new RecoverPhotosAsyncTask(PhotosActivity.this, filePhotoAdapter.getSelectedItem(), new RecoverPhotosAsyncTask.OnRestoreListener() {
            @Override
            public void onComplete() {
                Intent intent = new Intent(getApplicationContext(), RestoreResultActivity.class);
                intent.putExtra("value", tempList.size());
                intent.putExtra("type", 0);
                startActivity(intent);
                filePhotoAdapter.setAllImagesUnseleted();
                filePhotoAdapter.notifyDataSetChanged();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TuanPA38", " requestCode =" + requestCode + " resultCode = " + resultCode);
        if (requestCode == 10900 && resultCode == 888) {
            final ArrayList<PhotoEntity> tempList = filePhotoAdapter.getSelectedItem();
            mRecoverPhotosAsyncTask = new RecoverPhotosAsyncTask(PhotosActivity.this, filePhotoAdapter.getSelectedItem(), new RecoverPhotosAsyncTask.OnRestoreListener() {
                @Override
                public void onComplete() {
                    Intent intent = new Intent(getApplicationContext(), RestoreResultActivity.class);
                    intent.putExtra("value", tempList.size());
                    intent.putExtra("type", 0);
                    startActivity(intent);
                    filePhotoAdapter.setAllImagesUnseleted();
                    filePhotoAdapter.notifyDataSetChanged();
                }
            });
            mRecoverPhotosAsyncTask.execute();
        }
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

    @Override
    public void onClick() {
        final ArrayList<PhotoEntity> tempList = filePhotoAdapter.getSelectedItem();
        if(tempList.size()>0){
            txt_recovery_now.setBackground(getResources().getDrawable(R.drawable.bg_result));
        } else {
            txt_recovery_now.setBackground(getResources().getDrawable(R.drawable.bg_result_off));
        }
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
    public void onBackPressed() {
        super.onBackPressed();

    }
}
