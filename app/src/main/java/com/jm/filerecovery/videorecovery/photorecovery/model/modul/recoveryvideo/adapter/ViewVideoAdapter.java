package com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.model.SquareImageView;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo.Model.VideoModel;
import com.jm.filerecovery.videorecovery.photorecovery.utilts.Utils;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;

public class ViewVideoAdapter extends RecyclerView.Adapter<ViewVideoAdapter.MyViewHolder>{
    Context context;
    ArrayList<VideoModel> listPhoto = new ArrayList<>();
    BitmapDrawable placeholder;
    public ViewVideoAdapter(Context context, ArrayList<VideoModel> mList) {
        this.context = context;
        this.listPhoto = mList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvSize;
        ImageView ivPhoto;
        CardView album_card;
        public MyViewHolder(View view) {
            super(view);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvSize = (TextView) view.findViewById(R.id.tvSize);
            ivPhoto = (SquareImageView) view.findViewById(R.id.iv_image);
            album_card = (CardView)view.findViewById(R.id.album_card);
        }
    }
    @Override
    public ViewVideoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_photo, parent, false);
        return new ViewVideoAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final ViewVideoAdapter.MyViewHolder holder, int position) {
        final VideoModel imageData = listPhoto.get(position);
        holder.tvDate.setText(DateFormat.getDateInstance().format(imageData.getLastModified()));
        holder.tvSize.setText(Utils.formatSize(imageData.getSizePhoto()));

        try {
            Glide.with(context)
                    .load("file://" + imageData.getPathPhoto())
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
        holder.album_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Uri fileUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", new File(imageData.getPathPhoto()));
                openFile(imageData.getPathPhoto());
            }
        });


    }
    public void openFile(String path){
        Intent createChooser;


        if(Build.VERSION.SDK_INT<24){
            Intent intent2 = new Intent("android.intent.action.VIEW");
            intent2.setDataAndType(Uri.fromFile(new File(path)), "video/*");
            createChooser = Intent.createChooser(intent2, "Complete action using");
        }else{
            File file = new File(path);
            Intent intent4 = new Intent("android.intent.action.VIEW");
            Uri contentUri2 = FileProvider.getUriForFile(context, context.getPackageName() + ".provider",file );
            context.grantUriPermission(context.getPackageName(), contentUri2, 1);
            intent4.setType("video/*");
            if (Build.VERSION.SDK_INT < 24) {
                contentUri2 = Uri.fromFile(file);
            }
            intent4.setData(contentUri2);
            intent4.setFlags(1);
            createChooser = Intent.createChooser(intent4, "Complete action using");
        }
        context.startActivity(createChooser);
    }
    @Override
    public int getItemCount() {
        return listPhoto.size();
    }

}
