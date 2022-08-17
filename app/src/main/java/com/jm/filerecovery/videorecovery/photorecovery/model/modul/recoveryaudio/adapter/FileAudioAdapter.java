package com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.Model.AudioEntity;
import com.jm.filerecovery.videorecovery.photorecovery.utilts.Utils;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;

public class FileAudioAdapter extends RecyclerView.Adapter<FileAudioAdapter.MyViewHolder>{
    Context context;
    ArrayList<AudioEntity> audioEntities = new ArrayList<>();
    BitmapDrawable placeholder;
    public FileAudioAdapter(Context context, ArrayList<AudioEntity> mList) {
        this.context = context;
        this.audioEntities = mList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        protected AppCompatCheckBox ivChecked;
        protected TextView tvTitle,tvSize,tvDate;
        RelativeLayout album_card;
        public MyViewHolder(View view) {
            super(view);
            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.tvSize = (TextView) view.findViewById(R.id.tvSize);
            this.tvDate = (TextView) view.findViewById(R.id.tvType);
            ivChecked = (AppCompatCheckBox) view.findViewById(R.id.cbSelect);
            album_card = (RelativeLayout)view.findViewById(R.id.album_card);

        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_audio, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final AudioEntity audioEntity = audioEntities.get(position);
        holder.tvDate.setText(DateFormat.getDateInstance().format(audioEntity.getLastModified()));
        holder.tvSize.setText(Utils.formatSize(audioEntity.getSizePhoto()));
        holder.tvTitle.setText(Utils.getFileTitle(audioEntity.getPathPhoto()));
        holder.ivChecked.setChecked(audioEntity.getIsCheck());



        holder.ivChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(audioEntity.getIsCheck()){
                    holder.ivChecked.setChecked(false);
                    audioEntity.setIsCheck(false);
                }else {
                    holder.ivChecked.setChecked(true);
                    audioEntity.setIsCheck(true);
                }
            }
        });
        holder.album_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    openFile(new File(audioEntity.getPathPhoto()));
                }catch (Exception e){

                }
            }
        });


    }
    @Override
    public int getItemCount() {
        return audioEntities.size();
    }
    public ArrayList<AudioEntity> getSelectedItem() {
        ArrayList<AudioEntity> arrayList = new ArrayList();
        if (this.audioEntities != null) {
            for (int i = 0; i < this.audioEntities.size(); i++) {
                if ((this.audioEntities.get(i)).getIsCheck()) {
                    arrayList.add(this.audioEntities.get(i));
                }
            }
        }
        return arrayList;
    }

    public void setAllImagesUnseleted() {
        if (this.audioEntities != null) {
            for (int i = 0; i < this.audioEntities.size(); i++) {
                if ((this.audioEntities.get(i)).getIsCheck()) {
                    (this.audioEntities.get(i)).setIsCheck(false);
                }
            }
        }
    }

    public void openFile(File file ){
        Intent createChooser;
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        if (file.exists()) {
            if (Build.VERSION.SDK_INT < 24) {
                intent.setDataAndType(Uri.fromFile(file), "audio/*");
            } else {
                Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
                context.grantUriPermission(context.getPackageName(), contentUri, 1);
                intent.setDataAndType(contentUri, "audio/*");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            createChooser = Intent.createChooser(intent, "Complete action using");
            context.startActivity(createChooser);
        }
    }
}
