package com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.AudioActivity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.Model.AudioModel;
import com.jm.filerecovery.videorecovery.photorecovery.utilts.Utils;

import java.util.ArrayList;

public class SectionListAudioAdapter extends RecyclerView.Adapter<SectionListAudioAdapter.SingleItemRowHolder> {

    private ArrayList<AudioModel> itemsList;
    private Context mContext;
    int size;
    int postion;
    public SectionListAudioAdapter(Context context, ArrayList<AudioModel> itemsList, int mPostion) {
        this.itemsList = itemsList;
        this.mContext = context;
        postion = mPostion;
        if(itemsList.size()>=5){
            size=5;
        }else{
            size =itemsList.size();
        }
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_audio_album, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        AudioModel singleItem = itemsList.get(i);
        holder.tvTitle.setText(Utils.getFileTitle(singleItem.getPathPhoto()));
        holder.album_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AudioActivity.class);
                intent.putExtra("value",postion);
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {



        CardView album_card;
        TextView tvTitle;

        public SingleItemRowHolder(View view) {
            super(view);

            album_card = (CardView)view.findViewById(R.id.album_card);

            tvTitle = (TextView)view.findViewById(R.id.tvTitle);

        }

    }

}