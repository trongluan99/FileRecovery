package com.jm.filerecovery.videorecovery.photorecovery;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class IntroViewPagerITGAdapter extends FragmentStateAdapter {
    private IntroScreenFragment intro1;
    private IntroScreenFragment intro2;
    private IntroScreenFragment intro3;
    private ArrayList<IntroScreenFragment> introScreenFragments = new ArrayList<>();

    public IntroViewPagerITGAdapter(FragmentManager fragmentManager, Lifecycle lifecycle, Context context) {
        super(fragmentManager, lifecycle);
        this.intro1 = IntroScreenFragment.newInstant(R.drawable.bg_splash, context.getResources().getString(R.string.how_to_work), context.getResources().getString(R.string.scan_tip_1));
        this.intro2 = IntroScreenFragment.newInstant(R.drawable.bg_splash, context.getResources().getString(R.string.how_to_work), context.getResources().getString(R.string.scan_tip_2));
        this.intro3 = IntroScreenFragment.newInstant(R.drawable.bg_splash, context.getResources().getString(R.string.how_to_work), context.getResources().getString(R.string.scan_tip_note));
        this.introScreenFragments.add(this.intro1);
        this.introScreenFragments.add(this.intro2);
        this.introScreenFragments.add(this.intro3);
    }

    public Fragment createFragment(int i) {
        return this.introScreenFragments.get(i);
    }

    public int getItemCount() {
        return this.introScreenFragments.size();
    }
}
