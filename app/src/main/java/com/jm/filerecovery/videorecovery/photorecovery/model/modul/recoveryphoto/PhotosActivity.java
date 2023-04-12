package com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ads.control.ads.ITGAd;
import com.ads.control.ads.ITGAdCallback;
import com.ads.control.ads.ITGInitCallback;
import com.ads.control.ads.wrapper.ApAdError;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.jm.filerecovery.videorecovery.photorecovery.AdsConfig;
import com.jm.filerecovery.videorecovery.photorecovery.BaseActivity;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.RemoteConfigUtils;
import com.jm.filerecovery.videorecovery.photorecovery.adapter.ItemPhotoSelectAdapter;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto.Model.PhotoEntity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto.adapter.FilePhotoAdapter;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto.task.RecoverPhotosAsyncTask;
import com.jm.filerecovery.videorecovery.photorecovery.ui.activity.RestoreResultActivity;
import com.jm.filerecovery.videorecovery.photorecovery.ui.activity.ScanFilesActivity;

import java.io.File;
import java.util.ArrayList;


public class PhotosActivity extends BaseActivity implements FilePhotoAdapter.OnClickItem, BaseActivity.PreLoadNativeListener {
    int int_position;
    RecyclerView recyclerView;
    FilePhotoAdapter filePhotoAdapter;
    ItemPhotoSelectAdapter itemSelectAdapter;
    TextView txt_recovery_now;
    ArrayList<PhotoEntity> mList = new ArrayList<PhotoEntity>();
    RecoverPhotosAsyncTask mRecoverPhotosAsyncTask;
    AppCompatImageView imgBack;
    TextView txtTitle;
    ConstraintLayout imagePickedArea;
    RecyclerView mediaPickedListView;
    ImageView imgNext;

    ArrayList<PhotoEntity> tempList = new ArrayList<>();

    private boolean populateNativeAdView = false;
    FrameLayout frameLayout;
    ShimmerFrameLayout shimmerFrameLayout;

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
        frameLayout = findViewById(R.id.fl_adplaceholder);
        shimmerFrameLayout = findViewById(R.id.shimmer_container_native);
//        ITGAd.getInstance().loadNativeAd(this, getResources().getString(R.string.admob_native_recovery_item), R.layout.custom_native_no_media, frameLayout, shimmerFrameLayout);

        // Begin: Add Ads
        if (!populateNativeAdView) {
            if (nativeAdViewRecoveryItemHigh != null) {
                Log.e("XXXXXX", "onLoadNativeSuccess: vao 1");
                ITGAd.getInstance().populateNativeAdView(this, nativeAdViewRecoveryItemHigh, frameLayout, shimmerFrameLayout);
                populateNativeAdView = true;
            } else {
                Log.e("XXXXXX", "onLoadNativeSuccess: vao 2");
                if (nativeAdViewRecoveryItem != null) {
                    ITGAd.getInstance().populateNativeAdView(this, nativeAdViewRecoveryItem, frameLayout, shimmerFrameLayout);
                    populateNativeAdView = true;
                }
            }
        }
        // End
    }

    @Override
    public void onLoadNativeSuccess() {
        // Begin: Add Ads
        if (!populateNativeAdView) {
            if (nativeAdViewRecoveryItemHigh != null) {
                Log.e("XXXXXX", "onLoadNativeSuccess: vao 1");
                ITGAd.getInstance().populateNativeAdView(this, nativeAdViewRecoveryItemHigh, frameLayout, shimmerFrameLayout);
                populateNativeAdView = true;
            } else {
                Log.e("XXXXXX", "onLoadNativeSuccess: vao 2");
                if (nativeAdViewRecoveryItem != null) {
                    ITGAd.getInstance().populateNativeAdView(this, nativeAdViewRecoveryItem, frameLayout, shimmerFrameLayout);
                    populateNativeAdView = true;
                }
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

    public void intView() {
        imgNext = findViewById(R.id.img_next);
        imagePickedArea = findViewById(R.id.imagePickedArea);
        mediaPickedListView = findViewById(R.id.mediaPickedListView);
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
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new PhotosActivity.GridSpacingItemDecoration(3, dpToPx(0), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.LayoutManager mLayoutManagerHorizontal = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        mediaPickedListView.setLayoutManager(mLayoutManagerHorizontal);
    }

    public void intData() {
        int_position = getIntent().getIntExtra("value", 0);
        if (ScanFilesActivity.mAlbumPhoto != null && ScanFilesActivity.mAlbumPhoto.size() > int_position)
            mList.addAll((ArrayList<PhotoEntity>) ScanFilesActivity.mAlbumPhoto.get(int_position).getListPhoto().clone());
        filePhotoAdapter = new FilePhotoAdapter(this, mList);
        filePhotoAdapter.setOnClickItem(this);
        recyclerView.setAdapter(filePhotoAdapter);

        itemSelectAdapter = new ItemPhotoSelectAdapter(this, tempList);

        mediaPickedListView.setAdapter(itemSelectAdapter);
        txt_recovery_now.setVisibility(View.GONE);
        txt_recovery_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<PhotoEntity> tempList = filePhotoAdapter.getSelectedItem();
                if (tempList.size() == 0) {
                    Toast.makeText(PhotosActivity.this, "Cannot restore, all items are unchecked!", Toast.LENGTH_LONG).show();
                } else {
                    ITGAdCallback adCallback = new ITGAdCallback() {
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

                    if (AdsConfig.mInterstitialAdAllHigh.isReady()) {
                        ITGAd.getInstance().forceShowInterstitial(PhotosActivity.this, AdsConfig.mInterstitialAdAllHigh, adCallback);
                    } else {
                        if (RemoteConfigUtils.INSTANCE.getOnInterRecovery().equals("on")) {
                            ITGAd.getInstance().setInitCallback(new ITGInitCallback() {
                                @Override
                                public void initAdSuccess() {
                                    ITGAd.getInstance().loadSplashInterstitialAds(PhotosActivity.this, getResources().getString(R.string.admob_inter_recovery), 5000, 0, true, adCallback);
                                }
                            });
                        } else {
                            restoreFile();
                        }
                    }

                }
            }
        });

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<PhotoEntity> tempList = filePhotoAdapter.getSelectedItem();
                if (tempList.size() == 0) {
                    Toast.makeText(PhotosActivity.this, "Cannot restore, all items are unchecked!", Toast.LENGTH_LONG).show();
                } else {
                    ITGAdCallback adCallback = new ITGAdCallback() {
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

                    if (AdsConfig.mInterstitialAdAllHigh.isReady()) {
                        ITGAd.getInstance().forceShowInterstitial(PhotosActivity.this, AdsConfig.mInterstitialAdAllHigh, adCallback);
                    } else {
                        if (RemoteConfigUtils.INSTANCE.getOnInterRecovery().equals("on")) {
                            ITGAd.getInstance().setInitCallback(new ITGInitCallback() {
                                @Override
                                public void initAdSuccess() {
                                    ITGAd.getInstance().loadSplashInterstitialAds(PhotosActivity.this, getResources().getString(R.string.admob_inter_recovery), 5000, 0, true, adCallback);
                                }
                            });
                        } else {
                            restoreFile();
                        }
                    }

                }
            }
        });
    }

    boolean restore = true;

    private void restoreFile() {
        if (!restore) return;
        tempList = filePhotoAdapter.getSelectedItem();
        mRecoverPhotosAsyncTask = new RecoverPhotosAsyncTask(PhotosActivity.this, filePhotoAdapter.getSelectedItem(), new RecoverPhotosAsyncTask.OnRestoreListener() {
            @Override
            public void onComplete() {
                Intent intent = new Intent(getApplicationContext(), RestoreResultActivity.class);
                intent.putExtra("value", tempList.size());
                intent.putExtra("type", 0);
                startActivity(intent);
                filePhotoAdapter.setAllImagesUnSelected();
                filePhotoAdapter.notifyDataSetChanged();

                tempList = filePhotoAdapter.getSelectedItem();
                itemSelectAdapter.setPhotoEntities(tempList);
                itemSelectAdapter.notifyDataSetChanged();
                imagePickedArea.setVisibility(View.GONE);
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
            tempList = filePhotoAdapter.getSelectedItem();
            mRecoverPhotosAsyncTask = new RecoverPhotosAsyncTask(PhotosActivity.this, filePhotoAdapter.getSelectedItem(), new RecoverPhotosAsyncTask.OnRestoreListener() {
                @Override
                public void onComplete() {
                    Intent intent = new Intent(getApplicationContext(), RestoreResultActivity.class);
                    intent.putExtra("value", tempList.size());
                    intent.putExtra("type", 0);
                    startActivity(intent);
                    filePhotoAdapter.setAllImagesUnSelected();
                    filePhotoAdapter.notifyDataSetChanged();

                    tempList = filePhotoAdapter.getSelectedItem();
                    itemSelectAdapter.setPhotoEntities(tempList);
                    itemSelectAdapter.notifyDataSetChanged();
                    imagePickedArea.setVisibility(View.GONE);
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
        tempList = filePhotoAdapter.getSelectedItem();
        Log.d("Duongdx", "szee: " + tempList.size());
        if (tempList.size() > 0) {
            txt_recovery_now.setBackground(getResources().getDrawable(R.drawable.bg_result));
            imagePickedArea.setVisibility(View.VISIBLE);
        } else {
            txt_recovery_now.setBackground(getResources().getDrawable(R.drawable.bg_result_off));
            imagePickedArea.setVisibility(View.GONE);
        }
        itemSelectAdapter.setPhotoEntities(tempList);
        itemSelectAdapter.notifyDataSetChanged();
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
