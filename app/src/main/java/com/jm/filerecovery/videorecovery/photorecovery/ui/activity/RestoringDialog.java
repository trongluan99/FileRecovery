package com.jm.filerecovery.videorecovery.photorecovery.ui.activity;

import android.app.Dialog;
import android.content.Context;

import com.jm.filerecovery.videorecovery.photorecovery.R;

public class RestoringDialog extends Dialog {
    private Context mContext;

    public RestoringDialog(Context activity) {
        super(activity, R.style.Theme_AppCompat_DayNight_Dialog);
        this.mContext = activity;
        requestWindowFeature(1);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.layout_loading_dialog);

    }
}