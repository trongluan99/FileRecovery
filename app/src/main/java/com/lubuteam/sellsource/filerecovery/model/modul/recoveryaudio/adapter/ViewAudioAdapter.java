package com.lubuteam.sellsource.filerecovery.model.modul.recoveryaudio.adapter;

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

import com.lubuteam.sellsource.filerecovery.R;
import com.lubuteam.sellsource.filerecovery.model.modul.recoveryaudio.Model.AudioModel;
import com.lubuteam.sellsource.filerecovery.utilts.Utils;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;

public class ViewAudioAdapter extends RecyclerView.Adapter<ViewAudioAdapter.MyViewHolder>{
    Context context;
    ArrayList<AudioModel> listPhoto = new ArrayList<>();
    BitmapDrawable placeholder;
    public ViewAudioAdapter(Context context, ArrayList<AudioModel> mList) {
        this.context = context;
        this.listPhoto = mList;
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
            ivChecked.setVisibility(View.GONE);
            album_card = (RelativeLayout)view.findViewById(R.id.album_card);
        }
    }
    @Override
    public ViewAudioAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_audio, parent, false);
        return new ViewAudioAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final ViewAudioAdapter.MyViewHolder holder, int position) {
        final AudioModel imageData = listPhoto.get(position);
        holder.tvDate.setText(DateFormat.getDateInstance().format(imageData.getLastModified()));
        holder.tvSize.setText(Utils.formatSize(imageData.getSizePhoto()));
        holder.tvTitle.setText(Utils.getFileTitle(imageData.getPathPhoto()));

        holder.album_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    openFile(new File(imageData.getPathPhoto()));
                }catch (Exception e){

                }

            }
        });


    }
    @Override
    public int getItemCount() {
        return listPhoto.size();
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
