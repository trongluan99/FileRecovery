package com.lubuteam.sellsource.filerecovery.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.lubuteam.sellsource.filerecovery.BuildConfig;
import com.lubuteam.sellsource.filerecovery.R;
import com.lubuteam.sellsource.filerecovery.databinding.ActivityHistoryBinding;
import com.lubuteam.sellsource.filerecovery.model.modul.recoveryphoto.adapter.FileRestoredAdapter;
import com.lubuteam.sellsource.filerecovery.utilts.Utils;

import java.io.File;

public class HistoryActivity extends AppCompatActivity {

    private ActivityHistoryBinding binding;
    private FileRestoredAdapter fileRestoredAdapter;
    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#66000000"));
        }
        intView();
        initData();
        initListener();
    }

    private void intView() {
        setTypeDisplay(0);
    }

    private void initData() {

    }

    private void setTypeDisplay(int type) {
        this.type = type;
        binding.tvImage.setBackgroundResource(R.drawable.bg_type_history_unselect);
        binding.tvAudio.setBackgroundResource(R.drawable.bg_type_history_unselect);
        binding.tvVideo.setBackgroundResource(R.drawable.bg_type_history_unselect);
        binding.tvImage.setTextColor(Color.BLACK);
        binding.tvAudio.setTextColor(Color.BLACK);
        binding.tvVideo.setTextColor(Color.BLACK);
        String pathSave = "";
        String dataType = "*/*";
        if (type == 0) {
            /*image*/
            binding.tvImage.setBackgroundResource(R.drawable.bg_type_history_selected);
            binding.tvImage.setTextColor(Color.WHITE);
            pathSave = getString(R.string.restore_folder_path_photo);
            dataType = "image/*";
        } else if (type == 1) {
            /*audio*/
            binding.tvAudio.setBackgroundResource(R.drawable.bg_type_history_selected);
            binding.tvAudio.setTextColor(Color.WHITE);
            pathSave = getString(R.string.restore_folder_path_audio);
            dataType = "audio/*";
        } else if (type == 2) {
            /*video*/
            binding.tvVideo.setBackgroundResource(R.drawable.bg_type_history_selected);
            binding.tvVideo.setTextColor(Color.WHITE);
            pathSave = getString(R.string.restore_folder_path_video);
            dataType = "video/*";
        }
        File fileDirectory = new File(Utils.getPathSave(this, pathSave));
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
        }
        File[] lstFile = fileDirectory.listFiles() != null ? fileDirectory.listFiles() : new File[0];
        String finalDataType = dataType;
        fileRestoredAdapter = new FileRestoredAdapter(lstFile, type, file -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri photoURI = FileProvider.getUriForFile(this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    file);
            intent.setDataAndType(photoURI, finalDataType);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(intent, getString(R.string.view_using)));
        });
        binding.rcvData.setAdapter(fileRestoredAdapter);
        if (fileRestoredAdapter.lstData.length == 0) {
            binding.viewEmpty.setVisibility(View.VISIBLE);
            binding.rcvData.setVisibility(View.GONE);
        } else {
            binding.viewEmpty.setVisibility(View.GONE);
            binding.rcvData.setVisibility(View.VISIBLE);
        }
    }

    private void initListener() {
        binding.ivBack.setOnClickListener(v -> finish());
        binding.tvImage.setOnClickListener(v -> setTypeDisplay(0));
        binding.tvAudio.setOnClickListener(v -> setTypeDisplay(1));
        binding.tvVideo.setOnClickListener(v -> setTypeDisplay(2));
        binding.ivDelete.setOnClickListener(v -> {
            if (fileRestoredAdapter.lstData.length == 0)
                return;
            String title = "";
            if (type == 0) {
                title = getString(R.string.image);
            } else if (type == 1) {
                title = getString(R.string.audio);
            } else if (type == 2) {
                title = getString(R.string.view_using);
            }
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.app_name))
                    .setMessage(getString(R.string.request_delete_all, title))
                    .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .setPositiveButton(R.string.ok, (dialog, which) -> {
                        for (int i = 0; i < fileRestoredAdapter.lstData.length; i++) {
                            try {
                                fileRestoredAdapter.lstData[i].delete();
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }
                        setTypeDisplay(type);
                        dialog.dismiss();
                    })
                    .show();
        });
    }


}
