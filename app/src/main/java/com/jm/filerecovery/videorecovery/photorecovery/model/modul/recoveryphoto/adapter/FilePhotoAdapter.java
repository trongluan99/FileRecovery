package com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.model.SquareImageView;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto.Model.PhotoEntity;
import com.jm.filerecovery.videorecovery.photorecovery.utilts.Utils;

import java.text.DateFormat;
import java.util.ArrayList;

public class FilePhotoAdapter extends RecyclerView.Adapter<FilePhotoAdapter.MyViewHolder>{
    Context context;
    ArrayList<PhotoEntity> photoEntities = new ArrayList<>();
    BitmapDrawable placeholder;
    public FilePhotoAdapter(Context context, ArrayList<PhotoEntity> mList) {
        this.context = context;
        this.photoEntities = mList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  tvDate;
        TextView tvSize;
        ImageView ivPhoto;
        ImageView ivChecked;
        public MyViewHolder(View view) {
            super(view);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvSize = (TextView) view.findViewById(R.id.tvSize);
            ivPhoto = (SquareImageView) view.findViewById(R.id.iv_image);
            ivChecked =(ImageView) view.findViewById(R.id.isChecked);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_photo, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final PhotoEntity photoEntity = photoEntities.get(position);
        holder.tvDate.setText(DateFormat.getDateInstance().format(photoEntity.getLastModified()));
        holder.tvSize.setText(Utils.formatSize(photoEntity.getSizePhoto()));
        if(photoEntity.getIsCheck()){
            holder.ivChecked.setVisibility(View.VISIBLE);
        }else{
            holder.ivChecked.setVisibility(View.GONE);
        }

        try {
            Glide.with(context)
                    .load("file://" + photoEntity.getPathPhoto())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .centerCrop()
                    .error(R.drawable.ic_error)
                    .placeholder(placeholder)
                    .into(holder.ivPhoto);
        } catch (Exception e){
            //do nothing
            Toast.makeText(context, "Exception: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(photoEntity.getIsCheck()){
                    holder.ivChecked.setVisibility(View.GONE);
                    photoEntity.setIsCheck(false);
                }else {
                    holder.ivChecked.setVisibility(View.VISIBLE);
                    photoEntity.setIsCheck(true);
                }
            }
        });


    }
    @Override
    public int getItemCount() {
        return photoEntities.size();
    }
    public ArrayList<PhotoEntity> getSelectedItem() {
        ArrayList<PhotoEntity> arrayList = new ArrayList();
        if (this.photoEntities != null) {
            for (int i = 0; i < this.photoEntities.size(); i++) {
                if ((this.photoEntities.get(i)).getIsCheck()) {
                    arrayList.add(this.photoEntities.get(i));
                }
            }
        }
        return arrayList;
    }

    public void setAllImagesUnseleted() {
        if (this.photoEntities != null) {
            for (int i = 0; i < this.photoEntities.size(); i++) {
                if ((this.photoEntities.get(i)).getIsCheck()) {
                    (this.photoEntities.get(i)).setIsCheck(false);
                }
            }
        }
    }
}
