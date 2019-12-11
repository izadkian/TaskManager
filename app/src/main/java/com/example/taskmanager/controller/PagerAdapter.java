package com.example.taskmanager.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.content.Context;

import com.example.taskmanager.R;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return PagerMainFragment.newInstance(0);
        } else if (position == 1) {
            return PagerMainFragment.newInstance(1);
        } else {
            return PagerMainFragment.newInstance(2);
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return mContext.getString(R.string.to_do_tab);
            case 1:
                return mContext.getString(R.string.doing_tab);
            case 2:
                return mContext.getString(R.string.done_tab);
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

}
