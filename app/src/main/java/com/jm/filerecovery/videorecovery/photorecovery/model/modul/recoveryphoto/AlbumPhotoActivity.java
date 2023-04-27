package com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ads.control.ads.ITGAd;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.jm.filerecovery.videorecovery.photorecovery.BaseActivity;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto.adapter.GroupPhotoHorizontalAdapter;
import com.jm.filerecovery.videorecovery.photorecovery.ui.activity.ScanFilesActivity;


public class AlbumPhotoActivity extends BaseActivity implements GroupPhotoHorizontalAdapter.OnClickItemListener, BaseActivity.PreLoadNativeListener {
    @Override
    public void onClickItem(int position) {
        Log.d("TuanPA38", " AlbumPhotoActivity onClickItem1");
//        ITGAdCallback adCallback = new ITGAdCallback() {
//            @Override
//            public void onNextAction() {
//                super.onNextAction();
//                Intent intent = new Intent(getApplicationContext(), PhotosActivity.class);
//                intent.putExtra("value", position);
//                startActivity(intent);
//            }
//        };
//        ITGAd.getInstance().setInitCallback(new AperoInitCallback() {
//            @Override
//            public void initAdSuccess() {
//                ITGAd.getInstance().loadSplashInterstitialAds(AlbumPhotoActivity.this, getResources().getString(R.string.admob_inter_click_item), 5000, 0, true, adCallback);
//            }
//        });
    }

    RecyclerView recyclerView;
    GroupPhotoHorizontalAdapter groupPhotoHorizontalAdapter;
    Toolbar toolbar;
    FrameLayout frameLayout;
    ShimmerFrameLayout shimmerFrameLayout;
    boolean populateNativeAdView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_horizontal);
//        Toolbar ctrToolbar = findViewById(R.id.toolbar);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Utils.getHeightStatusBar(this) > 0) {
//            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) ctrToolbar.getLayoutParams();
//            params.setMargins(0, Utils.getHeightStatusBar(this), 0, 0);
//            ctrToolbar.setLayoutParams(params);
//        }
//        Utils.setStatusBarHomeTransparent(this);
        intView();
        intData();
        initAds();
    }

    private void initAds() {
        frameLayout = findViewById(R.id.fl_adplaceholder);
        shimmerFrameLayout = findViewById(R.id.shimmer_container_native);
        // Begin: Add Ads
        if (!populateNativeAdView) {
            if (nativeAdViewListItem != null) {
                ITGAd.getInstance().populateNativeAdView(this, nativeAdViewListItem, frameLayout, shimmerFrameLayout);
                populateNativeAdView = true;
            }
        }
        // End
    }

    @Override
    public void onLoadNativeSuccess() {
        // Begin: Add Ads
        if (!populateNativeAdView) {
            if (nativeAdViewListItem != null) {
                ITGAd.getInstance().populateNativeAdView(this, nativeAdViewListItem, frameLayout, shimmerFrameLayout);
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

    public void intView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.photo_recovery));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.gv_folder);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(2), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void intData() {
        groupPhotoHorizontalAdapter = new GroupPhotoHorizontalAdapter(AlbumPhotoActivity.this, ScanFilesActivity.mAlbumPhoto, AlbumPhotoActivity.this);
        recyclerView.setAdapter(groupPhotoHorizontalAdapter);
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

    /**
     * Converting dp to pixel
     */
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
