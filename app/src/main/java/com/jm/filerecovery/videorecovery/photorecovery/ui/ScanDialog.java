package com.jm.filerecovery.videorecovery.photorecovery.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;



import com.jm.filerecovery.videorecovery.photorecovery.databinding.DialogScanBinding;


public class ScanDialog extends DialogFragment {

    private DialogScanBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogScanBinding.inflate(LayoutInflater.from(getContext()));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
        );
        WindowManager.LayoutParams windowParams = getDialog().getWindow().getAttributes();
        getDialog().getWindow().setLayout(getWidth(getContext()) / 100 * 90, LinearLayout.LayoutParams.WRAP_CONTENT);
        windowParams.dimAmount = 0.7f;
        getDialog().getWindow().setAttributes(windowParams);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        AdmobUtils.getInstance().loadNativeFragment(getActivity(),binding.getRoot());
    }

    private int getWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

}
