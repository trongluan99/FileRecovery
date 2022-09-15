package com.jm.filerecovery.videorecovery.photorecovery.utilts;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.widget.AppCompatTextView;

import com.jm.filerecovery.videorecovery.photorecovery.R;

public class ButtonRestore extends AppCompatTextView {

    private Context context;

    public ButtonRestore(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        m14486a();
    }

    private void m14486a() {
        Animation loadAnimation = AnimationUtils.loadAnimation(this.context, R.anim.anim_button_continue);
        loadAnimation.setRepeatMode(-1);
        startAnimation(loadAnimation);
    }
}
