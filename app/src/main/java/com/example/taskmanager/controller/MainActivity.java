package com.example.taskmanager.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import com.example.taskmanager.R;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements AddTaskDialogFragment.InsertTaskListener,
PagerMainFragment.DeleteTaskListener{

    private static String TAG = "MAIN_ACTIVITY_LIFE_CYCLE";
    private Fragment mGeneralFragment;

    protected Fragment createLoginFragment() {
        return LoginFragment.newInstance();
    }

    protected Fragment createMainFragment() {
        return MainFragment.newInstance();
    }

    protected Fragment createTaskDetailFragment(UUID taskUUID) {
        return TaskDetailFragment.newInstance(taskUUID);
    }

    protected Fragment createUsersFragment() {
        return UsersFragment.newInstance();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Log.d(TAG, "onDialogPositiveClick: ");
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager()
                .findFragmentByTag(String.valueOf(R.string.main_frag_tag));
        mainFragment.notifyDataSetUpdate();
    }

    @Override
    public void onDeleteTask(PagerMainFragment pagerMainFragment) {
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager()
                .findFragmentByTag(String.valueOf(R.string.main_frag_tag));
        mainFragment.notifyDataSetUpdate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: ");

        FragmentManager fragmentManager = getSupportFragmentManager();

        mGeneralFragment = getVisibleFragment(fragmentManager);


        if (mGeneralFragment == null) {
            mGeneralFragment = createLoginFragment();
            fragmentManager.
                    beginTransaction()
                    .replace(R.id.main_activity_container, mGeneralFragment, "LOGIN")
                    .commit();
        } else if (mGeneralFragment instanceof LoginFragment
                && mGeneralFragment.getTag().equals("LOGIN")) {
            fragmentManager.
                    beginTransaction()
                    .replace(R.id.main_activity_container, mGeneralFragment)
                    .commit();
        } else if (mGeneralFragment instanceof MainFragment
                && mGeneralFragment.getTag().equals("MAIN")) {
            fragmentManager.
                    beginTransaction()
                    .replace(R.id.main_activity_container, mGeneralFragment)
                    .commit();
        }
    }

    //Get Current displaying Frag-------------------------------------------------------------------

    public Fragment getVisibleFragment(FragmentManager fragmentManager) {
        Log.d("EXEC", "getVisibleFragment: ");
        List<Fragment> fragmentList = fragmentManager.getFragments();
        Log.d("EXEC", "getVisibleFragment: " + fragmentList.toString());
        if (fragmentList != null) {
            for (Fragment fragment : fragmentList) {
                if (fragment != null){
                    Log.d("EXEC", "getVisibleFragmentTag: " + fragment.getTag());
                    return fragment;
                }
            }
        }
        return null;
    }

}
