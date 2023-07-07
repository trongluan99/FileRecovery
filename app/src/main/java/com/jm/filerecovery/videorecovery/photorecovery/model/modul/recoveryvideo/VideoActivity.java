package com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ads.control.ads.ITGAd;
import com.ads.control.ads.ITGAdCallback;
import com.ads.control.ads.ITGInitCallback;
import com.ads.control.ads.wrapper.ApAdError;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.jm.filerecovery.videorecovery.photorecovery.BaseActivity;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.RemoteConfigUtils;
import com.jm.filerecovery.videorecovery.photorecovery.adapter.ItemVideoSelectAdapter;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo.Model.VideoEntity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo.adapter.FileVideoAdapter;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo.task.RecoverVideoAsyncTask;
import com.jm.filerecovery.videorecovery.photorecovery.ui.activity.RestoreResultActivity;
import com.jm.filerecovery.videorecovery.photorecovery.ui.activity.ScanFilesActivity;

import java.util.ArrayList;


/**
 * Created by deepshikha on 20/3/17.
 */

public class VideoActivity extends BaseActivity implements FileVideoAdapter.OnClickItem, BaseActivity.PreLoadNativeListener {
    private static final String TAG = "VideoActivity";
    int int_position;
    RecyclerView recyclerView;
    FileVideoAdapter fileVideoAdapter;
    TextView txt_recovery_now;
    ArrayList<VideoEntity> mList = new ArrayList<VideoEntity>();
    RecoverVideoAsyncTask mRecoverVideoAsyncTask;
    AppCompatImageView imgBack;
    TextView txtTitle;
    RecyclerView mediaPickedListView;
    ArrayList<VideoEntity> tempList = new ArrayList<>();
    ItemVideoSelectAdapter itemSelectAdapter;
    ImageView imgNext;
    ConstraintLayout imagePickedArea;

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
        setPreLoadNativeListener(this);
        if (!populateNativeAdView) {
            if (nativeAdViewRecoveryItemHigh != null) {
                Log.d(TAG, "nativeAdViewRecoveryItemHigh: ");
                ITGAd.getInstance().populateNativeAdView(this, nativeAdViewRecoveryItemHigh, frameLayout, shimmerFrameLayout);
                populateNativeAdView = true;
            } else {
                if (nativeAdViewRecoveryItem != null) {
                    Log.d(TAG, "nativeAdViewRecoveryItem: ");
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
                Log.d(TAG, "nativeAdViewRecoveryItemHigh: ");
                ITGAd.getInstance().populateNativeAdView(this, nativeAdViewRecoveryItemHigh, frameLayout, shimmerFrameLayout);
                populateNativeAdView = true;
            } else {
                if (nativeAdViewRecoveryItem != null) {
                    Log.d(TAG, "nativeAdViewRecoveryItem: ");
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
        imagePickedArea = findViewById(R.id.imagePickedArea);
        imgNext = findViewById(R.id.img_next);
        mediaPickedListView = findViewById(R.id.mediaPickedListView);
        imgBack = findViewById(R.id.img_back);
        txtTitle = findViewById(R.id.txt_recovery);
        txtTitle.setText(getString(R.string.video_recovery));
        imgBack.setOnClickListener(view -> onBackPressed());
        txt_recovery_now = (TextView) findViewById(R.id.txt_recovery_now);
        recyclerView = (RecyclerView) findViewById(R.id.gv_folder);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new VideoActivity.GridSpacingItemDecoration(1, dpToPx(5), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        RecyclerView.LayoutManager mLayoutManagerHorizontal = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        mediaPickedListView.setLayoutManager(mLayoutManagerHorizontal);
    }

    public void intData() {
        int_position = getIntent().getIntExtra("value", 0);
        if (ScanFilesActivity.mAlbumVideo != null && ScanFilesActivity.mAlbumVideo.size() > int_position)
            mList.addAll((ArrayList<VideoEntity>) ScanFilesActivity.mAlbumVideo.get(int_position).getListPhoto().clone());
        fileVideoAdapter = new FileVideoAdapter(this, mList);
        fileVideoAdapter.setOnClickItem(this);
        recyclerView.setAdapter(fileVideoAdapter);

        itemSelectAdapter = new ItemVideoSelectAdapter(this, tempList);

        mediaPickedListView.setAdapter(itemSelectAdapter);
        txt_recovery_now.setVisibility(View.GONE);
        txt_recovery_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArrayList<VideoEntity> tempList = fileVideoAdapter.getSelectedItem();
                if (tempList.size() == 0) {
                    Toast.makeText(VideoActivity.this, "Cannot restore, all items are unchecked!", Toast.LENGTH_LONG).show();
                } else {
                    ITGAdCallback adCallback = new ITGAdCallback() {
                        @Override
                        public void onNextAction() {
                            super.onNextAction();
                            restoreFile();
                        }

                        @Override
                        public void onAdFailedToLoad(@Nullable ApAdError adError) {
                            super.onAdFailedToLoad(adError);
                            restoreFile();
                        }

                        @Override
                        public void onAdFailedToShow(@Nullable ApAdError adError) {
                            super.onAdFailedToShow(adError);
                            restoreFile();
                        }

                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                            restoreFile();
                        }
                    };

                    if (RemoteConfigUtils.INSTANCE.getOnInterRecovery().equals("on")) {
                        ITGAd.getInstance().setInitCallback(new ITGInitCallback() {
                            @Override
                            public void initAdSuccess() {
                                ITGAd.getInstance().loadSplashInterstitialAds(VideoActivity.this, getResources().getString(R.string.admob_inter_recovery), 5000, 0, true, adCallback);
                            }
                        });
                    } else {
                        restoreFile();
                    }
                }

            }
        });

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArrayList<VideoEntity> tempList = fileVideoAdapter.getSelectedItem();
                if (tempList.size() == 0) {
                    Toast.makeText(VideoActivity.this, "Cannot restore, all items are unchecked!", Toast.LENGTH_LONG).show();
                } else {
                    ITGAdCallback adCallback = new ITGAdCallback() {
                        @Override
                        public void onNextAction() {
                            super.onNextAction();
                            restoreFile();
                        }

                        @Override
                        public void onAdFailedToLoad(@Nullable ApAdError adError) {
                            super.onAdFailedToLoad(adError);
                            restoreFile();
                        }

                        @Override
                        public void onAdFailedToShow(@Nullable ApAdError adError) {
                            super.onAdFailedToShow(adError);
                            restoreFile();
                        }

                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                            restoreFile();
                        }
                    };

                    if (RemoteConfigUtils.INSTANCE.getOnInterRecovery().equals("on")) {
                        ITGAd.getInstance().setInitCallback(new ITGInitCallback() {
                            @Override
                            public void initAdSuccess() {
                                ITGAd.getInstance().loadSplashInterstitialAds(VideoActivity.this, getResources().getString(R.string.admob_inter_recovery), 5000, 0, true, adCallback);
                            }
                        });
                    } else {
                        restoreFile();
                    }
                }

            }
        });
    }

    boolean restore = true;

    private void restoreFile() {
        if (!restore) return;
        tempList = fileVideoAdapter.getSelectedItem();
        mRecoverVideoAsyncTask = new RecoverVideoAsyncTask(VideoActivity.this, fileVideoAdapter.getSelectedItem(), new RecoverVideoAsyncTask.OnRestoreListener() {
            @Override
            public void onComplete() {
                Intent intent = new Intent(getApplicationContext(), RestoreResultActivity.class);
                intent.putExtra("value", tempList.size());
                intent.putExtra("type", 1);
                startActivity(intent);
                fileVideoAdapter.setAllImagesUnseleted();
                fileVideoAdapter.notifyDataSetChanged();

                tempList = fileVideoAdapter.getSelectedItem();
                itemSelectAdapter.setVideoEntities(tempList);
                itemSelectAdapter.notifyDataSetChanged();
                imagePickedArea.setVisibility(View.GONE);
            }
        });
        mRecoverVideoAsyncTask.execute();
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
        Log.d("AdmobHelper", " requestCode =" + requestCode + " resultCode = " + resultCode);
        if (requestCode == 10900 && resultCode == 888) {
            tempList = fileVideoAdapter.getSelectedItem();
            mRecoverVideoAsyncTask = new RecoverVideoAsyncTask(VideoActivity.this, fileVideoAdapter.getSelectedItem(), new RecoverVideoAsyncTask.OnRestoreListener() {
                @Override
                public void onComplete() {
                    Intent intent = new Intent(getApplicationContext(), RestoreResultActivity.class);
                    intent.putExtra("value", tempList.size());
                    intent.putExtra("type", 1);
                    startActivity(intent);
                    fileVideoAdapter.setAllImagesUnseleted();
                    fileVideoAdapter.notifyDataSetChanged();

                    tempList = fileVideoAdapter.getSelectedItem();
                    itemSelectAdapter.setVideoEntities(tempList);
                    itemSelectAdapter.notifyDataSetChanged();
                    imagePickedArea.setVisibility(View.GONE);
                }
            });
            mRecoverVideoAsyncTask.execute();
        }
    }

    @Override
    public void onClick() {
        tempList = fileVideoAdapter.getSelectedItem();
        if (tempList.size() > 0) {
            txt_recovery_now.setBackground(getResources().getDrawable(R.drawable.bg_result));
            imagePickedArea.setVisibility(View.VISIBLE);
        } else {
            txt_recovery_now.setBackground(getResources().getDrawable(R.drawable.bg_result_off));
            imagePickedArea.setVisibility(View.GONE);
        }

        itemSelectAdapter.setVideoEntities(tempList);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void cancleUIUPdate() {
        if (this.mRecoverVideoAsyncTask != null && this.mRecoverVideoAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            this.mRecoverVideoAsyncTask.cancel(true);
            this.mRecoverVideoAsyncTask = null;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
