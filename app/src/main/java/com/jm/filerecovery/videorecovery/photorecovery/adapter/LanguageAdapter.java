package com.jm.filerecovery.videorecovery.photorecovery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.model.LanguageModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {

    private Context mContext;
    private List<LanguageModel> languageModelList;
    private ItemClickListener mItemClickListener;

    public LanguageAdapter(Context mContext, List<LanguageModel> languageModelList, ItemClickListener mItemClickListener) {
        this.mContext = mContext;
        this.languageModelList = languageModelList;
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_language, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binData(languageModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return languageModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img_language;
        TextView tv_title_language;
        CheckBox checkbox_language;
        ConstraintLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            img_language = itemView.findViewById(R.id.img_language);
            tv_title_language = itemView.findViewById(R.id.tv_title_language);
            checkbox_language = itemView.findViewById(R.id.checkbox_language);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5, 10, 5, 10);
            itemView.setLayoutParams(lp);
        }
        public void binData(LanguageModel languageModel) {
            if (languageModel != null) {
                img_language.setImageDrawable(mContext.getResources().getDrawable(languageModel.getIcon()));
                img_language.setBorderColor(mContext.getResources().getColor(R.color.color_9E9E9E));
                tv_title_language.setText(languageModel.getName());
                checkbox_language.setChecked(languageModel.getState() == 1);
                container.setOnClickListener(v -> mItemClickListener.onClickItem(getAdapterPosition()));
                checkbox_language.setOnClickListener(v -> mItemClickListener.onClickItem(getAdapterPosition()));
            }
        }
    }

    public interface ItemClickListener {
        void onClickItem(int position);
    }
}
