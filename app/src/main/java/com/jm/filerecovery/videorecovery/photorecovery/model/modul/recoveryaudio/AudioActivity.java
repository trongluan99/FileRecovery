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
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ads.control.AdmobUtils;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.ui.InviteWatchAdsActivity;
import com.jm.filerecovery.videorecovery.photorecovery.ui.activity.RestoreResultActivity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.Model.AudioEntity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.adapter.FileAudioAdapter;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.task.RecoverAudioAsyncTask;
import com.jm.filerecovery.videorecovery.photorecovery.ui.activity.ScanFilesActivity;
import com.jm.filerecovery.videorecovery.photorecovery.utilts.Utils;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by deepshikha on 20/3/17.
 */

public class AudioActivity extends AppCompatActivity {
    int int_position;
    RecyclerView recyclerView;
    FileAudioAdapter fileAudioAdapter;
    Button btnRestore;
    ArrayList<AudioEntity> mList = new ArrayList<AudioEntity>();
    RecoverAudioAsyncTask mRecoverPhotosAsyncTask;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view_files);
        Toolbar ctrToolbar = findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Utils.getHeightStatusBar(this) > 0) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) ctrToolbar.getLayoutParams();
            params.setMargins(0, Utils.getHeightStatusBar(this), 0, 0);
            ctrToolbar.setLayoutParams(params);
        }
        Utils.setStatusBarHomeTransparent(this);
        intView();
        intData();
        AdmobUtils.getInstance().loadBanner(this);
        AdmobUtils.getInstance().showInterstitialAd(this, () -> {});

    }

    public void intView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.audio_recovery));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnRestore = (Button) findViewById(R.id.btnRestore);
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
        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArrayList<AudioEntity> tempList = fileAudioAdapter.getSelectedItem();
                if (tempList.size() == 0) {
                    Toast.makeText(AudioActivity.this, "Cannot restore, all items are unchecked!", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), InviteWatchAdsActivity.class);
                    intent.putExtra("value", tempList.size());
                    intent.putExtra("type", 2);
                    startActivityForResult(intent, 10900);

//                    mRecoverPhotosAsyncTask = new RecoverAudioAsyncTask(AudioActivity.this, fileAudioAdapter.getSelectedItem(), new RecoverAudioAsyncTask.OnRestoreListener() {
//                        @Override
//                        public void onComplete() {
//                            Intent intent = new Intent(getApplicationContext(), RestoreResultActivity.class);
//                            intent.putExtra("value", tempList.size());
//                            intent.putExtra("type", 2);
//                            startActivity(intent);
//                            fileAudioAdapter.setAllImagesUnseleted();
//                            fileAudioAdapter.notifyDataSetChanged();
//                        }
//                    });
//                    mRecoverPhotosAsyncTask.execute();
                }

            }
        });
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
