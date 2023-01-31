package com.jm.filerecovery.videorecovery.photorecovery.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.model.SquareImageView;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.Model.AudioEntity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo.Model.VideoEntity;
import com.jm.filerecovery.videorecovery.photorecovery.utils.Utils;

import java.util.ArrayList;

public class ItemAudioSelectAdapter extends RecyclerView.Adapter<ItemAudioSelectAdapter.MyViewHolder>{
    Context context;
    ArrayList<AudioEntity> audioEntities = new ArrayList<>();
    BitmapDrawable placeholder;
    public ItemAudioSelectAdapter(Context context, ArrayList<AudioEntity> mList) {
        this.context = context;
        this.audioEntities = mList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView mediaThumb;
        TextView tvNameFile;
        public MyViewHolder(View view) {
            super(view);
            mediaThumb = view.findViewById(R.id.mediaThumb);
            tvNameFile = view.findViewById(R.id.tv_name_file);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_choosed, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final AudioEntity audioEntity = audioEntities.get(position);
        try {
            holder.tvNameFile.setText(Utils.getFileTitle(audioEntity.getPathPhoto()));
            holder.tvNameFile.setVisibility(View.VISIBLE);
            holder.mediaThumb.setImageResource(R.drawable.audio);
        } catch (Exception e){
            //do nothing
            Toast.makeText(context, "Exception: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    public int getItemCount() {
        return audioEntities.size();
    }

    public void setAudioEntities(ArrayList<AudioEntity> audios) {
        this.audioEntities = audios;
    }

}

