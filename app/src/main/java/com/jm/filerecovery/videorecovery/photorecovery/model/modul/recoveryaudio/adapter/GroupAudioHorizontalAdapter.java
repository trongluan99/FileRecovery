package com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.Model.AlbumAudio;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.Model.AudioEntity;

import java.util.ArrayList;

public class GroupAudioHorizontalAdapter extends RecyclerView.Adapter<GroupAudioHorizontalAdapter.MyViewHolder>{
    Context context;
    ArrayList<AlbumAudio> al_menu = new ArrayList<>();
    OnClickItemListener mOnClickItemListener;
    public GroupAudioHorizontalAdapter(Context context, ArrayList<AlbumAudio> mList, OnClickItemListener onClickItemListener) {
        this.context = context;
        this.al_menu = mList;
        mOnClickItemListener = onClickItemListener;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_foldersize;
        protected RecyclerView recycler_view_list;

        OnClickItemListener onClickItemListener;
        public MyViewHolder(View view,OnClickItemListener onClickItemListener) {
            super(view);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);
            tv_foldersize = (TextView) view.findViewById(R.id.tvDate);
            this.onClickItemListener =onClickItemListener;
            view.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            onClickItemListener.onClickItem(getAdapterPosition());
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_audio, parent, false);
        return new MyViewHolder(itemView,mOnClickItemListener);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_foldersize.setText(al_menu.get(position).getListPhoto().size()+" Audio");
        ArrayList<AudioEntity> singleSectionItems = al_menu.get(position).getListPhoto();
        FileAudioGridAdapter fileAudioGridAdapter = new FileAudioGridAdapter(context, singleSectionItems,position);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 1);
        holder.recycler_view_list.setLayoutManager(mLayoutManager);
        holder.recycler_view_list.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(context,10), true));
        holder.recycler_view_list.setAdapter(fileAudioGridAdapter);



    }

    @Override
    public int getItemCount() {
        return al_menu.size();
    }

    public interface OnClickItemListener{
        void onClickItem(int position);


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

    private int dpToPx(Context c,int dp) {
        Resources r = c.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
