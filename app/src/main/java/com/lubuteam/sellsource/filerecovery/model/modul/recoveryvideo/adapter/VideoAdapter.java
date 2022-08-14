package com.lubuteam.sellsource.filerecovery.model.modul.recoveryvideo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lubuteam.sellsource.filerecovery.R;
import com.lubuteam.sellsource.filerecovery.model.SquareImageView;
import com.lubuteam.sellsource.filerecovery.model.modul.recoveryvideo.FileInfoActivity;
import com.lubuteam.sellsource.filerecovery.model.modul.recoveryvideo.Model.VideoModel;
import com.lubuteam.sellsource.filerecovery.utilts.Utils;

import java.text.DateFormat;
import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder>{
    Context context;
    ArrayList<VideoModel> listPhoto = new ArrayList<>();
    public VideoAdapter(Context context, ArrayList<VideoModel> mList) {
        this.context = context;
        this.listPhoto = mList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  tvDate;
        TextView tvSize;
        TextView tvType;
        SquareImageView ivPhoto;
        AppCompatCheckBox cbSelected;
        RelativeLayout album_card;
        public MyViewHolder(View view) {
            super(view);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvSize = (TextView) view.findViewById(R.id.tvSize);
            tvType = (TextView) view.findViewById(R.id.tvType);
            ivPhoto = (SquareImageView) view.findViewById(R.id.iv_image);
            cbSelected =(AppCompatCheckBox) view.findViewById(R.id.cbSelected);
            album_card = (RelativeLayout)view.findViewById(R.id.album_card);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_video, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final VideoModel imageData = listPhoto.get(position);
        holder.tvDate.setText(DateFormat.getDateInstance().format(imageData.getLastModified())+"  "+imageData.getTimeDuration());
        holder.cbSelected.setChecked(imageData.getIsCheck());
        holder.tvSize.setText(Utils.formatSize(imageData.getSizePhoto()));
        holder.tvType.setText(imageData.getTypeFile());


        try {
            Glide.with(context)
                    .load("file://" + imageData.getPathPhoto())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .centerCrop()
                    .error(R.drawable.ic_error)
                    .into(holder.ivPhoto);
        } catch (Exception e){
            //do nothing
            Toast.makeText(context, "Exception: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        holder.album_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, FileInfoActivity.class);
                i.putExtra("ojectVideo", imageData);
                context.startActivity(i);
            }
        });
        holder.cbSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( holder.cbSelected.isChecked()){
                    imageData.setIsCheck(true);
                }else{
                    imageData.setIsCheck(false);
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return listPhoto.size();
    }
    public ArrayList<VideoModel> getSelectedItem() {
        ArrayList<VideoModel> arrayList = new ArrayList();
        if (this.listPhoto != null) {
            for (int i = 0; i < this.listPhoto.size(); i++) {
                if ((this.listPhoto.get(i)).getIsCheck()) {
                    arrayList.add(this.listPhoto.get(i));
                }
            }
        }
        return arrayList;
    }

    public void setAllImagesUnseleted() {
        if (this.listPhoto != null) {
            for (int i = 0; i < this.listPhoto.size(); i++) {
                if ((this.listPhoto.get(i)).getIsCheck()) {
                    (this.listPhoto.get(i)).setIsCheck(false);
                }
            }
        }
    }
}
