package com.example.taskmanager.controller;


import android.content.Context;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.taskmanager.R;
import com.example.taskmanager.model.Repository;
import com.example.taskmanager.utility.RepoUtils;
import com.google.android.material.tabs.TabLayout;


public class MainFragment extends Fragment {

    private static String TAG = "MAIN_FRAG_LIFE_CYCLE";
    private Context mContext = getContext();
    private ViewPager mTabLayoutViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private PagerAdapter mPagerAdapter;
    private Repository mRepository = Repository.getInstance();

    public MainFragment() {
        // Required empty public constructor
    }

    //newInstance-----------------------------------------------------------------------------------
    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //set StatePagerAdapter-------------------------------------------------------------------------
    public void setPagerAdapter(Context context, FragmentManager fragmentManager) { //PagerAdapter Constructor
        mPagerAdapter = new PagerAdapter(fragmentManager,
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
                , context);
    }

    //LifeCycle Callbacks---------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        iniUI(view);

        setListeners();

        setPagerAdapter(getContext(), getFragmentManager());
        prepareTabViewPager(mPagerAdapter);

        return view;
    }

    //Initiate User Interface-----------------------------------------------------------------------
    public void iniUI(View view) {

        mToolbar = view.findViewById(R.id.tablayout_toolbar);
        mToolbar.inflateMenu(R.menu.main_frag_toolbar_menu);

        mToolbar.setTitle("Tasks List");

        if (RepoUtils.isAdmin(mContext)) {
            mToolbar.setSubtitle(getString(R.string.main_frag_wellcome)
                    + " Administrator");
        } else {
            mToolbar.setSubtitle(getString(R.string.main_frag_wellcome)
                    + " " + RepoUtils.getCurrentUser(mContext).getUserName());
        }

        mTabLayout = view.findViewById(R.id.main_tab_layout);
        mTabLayoutViewPager = view.findViewById(R.id.view_pager);

    }

    private void setListeners() {

        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_logoff) {

                Toast.makeText(getActivity(), "Sign out was successful.",
                        Toast.LENGTH_SHORT).show();

                Fragment loginFragment = getActivity().getSupportFragmentManager()
                        .findFragmentByTag(getString(R.string.login_fragment_tag));
                if (loginFragment == null) {
                    loginFragment = ((MainActivity) getActivity()).createLoginFragment();
                }
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_activity_container, loginFragment,
                                String.valueOf(R.string.login_fragment_tag))
                        .commit();

                RepoUtils.setCurrentUser(getContext(), null);

            } else if (item.getItemId() == R.id.user_mgmt_menu_item) {

                if (!RepoUtils.isAdmin(mContext)) {
                    Toast.makeText(getActivity(), "Permision denied"
                            , Toast.LENGTH_SHORT).show();

                } else {

                    Fragment usersFragment = getActivity().getSupportFragmentManager()
                            .findFragmentByTag("Users");
                    if (usersFragment == null)
                        usersFragment = ((MainActivity) getActivity()).createUsersFragment();
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_activity_container,
                                    usersFragment, "Users")
                            .addToBackStack("MAIN")
                            .commit();

                }
            }
            return true;
        });

    }

    private void prepareTabViewPager(PagerAdapter pagerAdapter) {
        mTabLayoutViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mTabLayoutViewPager);
    }

    public void notifyDataSetUpdate() {
        mPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
       /* setPagerAdapter(getContext(), getFragmentManager());
        prepareTabViewPager(mPagerAdapter);
        notifyDataSetUpdate();*/
    }

}
