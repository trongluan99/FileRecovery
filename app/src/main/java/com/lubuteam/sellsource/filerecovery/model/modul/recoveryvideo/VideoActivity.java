package com.lubuteam.sellsource.filerecovery.model.modul.recoveryvideo;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ads.control.AdmobHelp;
import com.lubuteam.sellsource.filerecovery.ui.activity.MainActivity;
import com.lubuteam.sellsource.filerecovery.R;
import com.lubuteam.sellsource.filerecovery.ui.activity.RestoreResultActivity;
import com.lubuteam.sellsource.filerecovery.model.modul.recoveryvideo.Model.VideoModel;
import com.lubuteam.sellsource.filerecovery.model.modul.recoveryvideo.adapter.VideoAdapter;
import com.lubuteam.sellsource.filerecovery.model.modul.recoveryvideo.task.RecoverVideoAsyncTask;

import java.util.ArrayList;


/**
 * Created by deepshikha on 20/3/17.
 */

public class VideoActivity extends AppCompatActivity {
    int int_position;
    RecyclerView recyclerView;
    VideoAdapter adapter;
    Button btnRestore;
    ArrayList<VideoModel> mList = new ArrayList<VideoModel>();
    RecoverVideoAsyncTask mRecoverVideoAsyncTask;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        intView();
        intData();
        AdmobHelp.getInstance().loadBanner(this);

    }

    public void intView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.video_recovery));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnRestore = (Button) findViewById(R.id.btnRestore);
        recyclerView = (RecyclerView) findViewById(R.id.gv_folder);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new VideoActivity.GridSpacingItemDecoration(1, dpToPx(5), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void intData() {
        int_position = getIntent().getIntExtra("value", 0);
        if (MainActivity.mAlbumVideo != null && MainActivity.mAlbumVideo.size() > int_position)
            mList.addAll((ArrayList<VideoModel>) MainActivity.mAlbumVideo.get(int_position).getListPhoto().clone());
        adapter = new VideoAdapter(this, mList);
        recyclerView.setAdapter(adapter);
        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArrayList<VideoModel> tempList = adapter.getSelectedItem();
                if (tempList.size() == 0) {
                    Toast.makeText(VideoActivity.this, "Cannot restore, all items are unchecked!", Toast.LENGTH_LONG).show();
                } else {
                    mRecoverVideoAsyncTask = new RecoverVideoAsyncTask(VideoActivity.this, adapter.getSelectedItem(), new RecoverVideoAsyncTask.OnRestoreListener() {
                        @Override
                        public void onComplete() {
                            Intent intent = new Intent(getApplicationContext(), RestoreResultActivity.class);
                            intent.putExtra("value", tempList.size());
                            intent.putExtra("type", 1);
                            startActivity(intent);
                            adapter.setAllImagesUnseleted();
                            adapter.notifyDataSetChanged();
                        }
                    });
                    mRecoverVideoAsyncTask.execute();
                }

            }
        });
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
        AdmobHelp.getInstance().showInterstitialAd(this, () -> finish());

    }
}
