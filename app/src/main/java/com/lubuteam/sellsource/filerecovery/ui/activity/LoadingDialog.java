package com.lubuteam.sellsource.filerecovery.ui.activity;

import android.app.Dialog;
import android.content.Context;

import com.lubuteam.sellsource.filerecovery.R;

public class LoadingDialog extends Dialog {
    private Context mContext;

    public LoadingDialog(Context activity) {
        super(activity, R.style.Theme_AppCompat_DayNight_Dialog);
        this.mContext = activity;
        requestWindowFeature(1);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.layout_loading_dialog);

    }
}