package com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ads.control.ads.AperoAd;
import com.ads.control.ads.AperoAdCallback;
import com.ads.control.ads.AperoInitCallback;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.AlbumAudioActivity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.AudioActivity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo.adapter.GroupVideoHorizontalAdapter;
import com.jm.filerecovery.videorecovery.photorecovery.ui.activity.ScanFilesActivity;
import com.jm.filerecovery.videorecovery.photorecovery.utils.Utils;


public class AlbumVideoActivity extends AppCompatActivity implements GroupVideoHorizontalAdapter.OnClickItemListener {
    @Override
    public void onClickItem(int position) {
//
//        AperoAdCallback adCallback = new AperoAdCallback() {
//            @Override
//            public void onNextAction() {
//                super.onNextAction();
//                Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
//                intent.putExtra("value", position);
//                startActivity(intent);
//            }
//        };
//        AperoAd.getInstance().setInitCallback(new AperoInitCallback() {
//            @Override
//            public void initAdSuccess() {
//                AperoAd.getInstance().loadSplashInterstitialAds(AlbumVideoActivity.this, getResources().getString(R.string.admob_inter_click_item), 5000, 0, true, adCallback);
//            }
//        });
    }

    RecyclerView recyclerView;
    GroupVideoHorizontalAdapter groupVideoHorizontalAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_horizontal);
        Toolbar ctrToolbar = findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Utils.getHeightStatusBar(this) > 0) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) ctrToolbar.getLayoutParams();
            params.setMargins(0, Utils.getHeightStatusBar(this), 0, 0);
            ctrToolbar.setLayoutParams(params);
        }
        Utils.setStatusBarHomeTransparent(this);
        intView();
        intData();
        initAds();
        initStatusBar();
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

    private void initAds() {
        FrameLayout frameLayout = findViewById(R.id.fl_adplaceholder);
        ShimmerFrameLayout shimmerFrameLayout = findViewById(R.id.shimmer_container_native);
        AperoAd.getInstance().loadNativeAd(this, getResources().getString(R.string.admob_native_list_item), R.layout.custom_native_no_media, frameLayout, shimmerFrameLayout);
    }

    public void intView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.video_recovery));
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
        groupVideoHorizontalAdapter = new GroupVideoHorizontalAdapter(AlbumVideoActivity.this, ScanFilesActivity.mAlbumVideo, AlbumVideoActivity.this);
        recyclerView.setAdapter(groupVideoHorizontalAdapter);
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
