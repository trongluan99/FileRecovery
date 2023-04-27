package com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ads.control.ads.ITGAd;
import com.ads.control.ads.ITGAdCallback;
import com.ads.control.ads.ITGInitCallback;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.RemoteConfigUtils;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.AudioActivity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.Model.AudioEntity;
import com.jm.filerecovery.videorecovery.photorecovery.utils.Utils;

import java.util.ArrayList;

public class FileAudioGridAdapter extends RecyclerView.Adapter<FileAudioGridAdapter.SingleItemRowHolder> {

    private ArrayList<AudioEntity> itemsList;
    private Context mContext;
    int size;
    int postion;

    public FileAudioGridAdapter(Context context, ArrayList<AudioEntity> itemsList, int mPostion) {
        this.itemsList = itemsList;
        this.mContext = context;
        postion = mPostion;
        if (itemsList.size() >= 5) {
            size = 5;
        } else {
            size = itemsList.size();
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

        AudioEntity singleItem = itemsList.get(i);
        holder.tvTitle.setText(Utils.getFileTitle(singleItem.getPathPhoto()));
        holder.album_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ITGAdCallback adCallback = new ITGAdCallback() {
                    @Override
                    public void onNextAction() {
                        super.onNextAction();
                        Intent intent = new Intent(mContext, AudioActivity.class);
                        intent.putExtra("value", postion);
                        mContext.startActivity(intent);
                    }
                };
                if (RemoteConfigUtils.INSTANCE.getOnInterClickItem().equals("on")) {
                    ITGAd.getInstance().setInitCallback(new ITGInitCallback() {
                        @Override
                        public void initAdSuccess() {
                            ITGAd.getInstance().loadSplashInterstitialAds(mContext, mContext.getResources().getString(R.string.admob_inter_click_item), 5000, 0, true, adCallback);
                        }
                    });
                } else {
                    Intent intent = new Intent(mContext, AudioActivity.class);
                    intent.putExtra("value", postion);
                    mContext.startActivity(intent);
                }
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

            album_card = (CardView) view.findViewById(R.id.album_card);

            tvTitle = (TextView) view.findViewById(R.id.tvTitle);

        }

    }

}