package com.example.taskmanager.controller;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.taskmanager.R;
import com.example.taskmanager.model.User;
import com.example.taskmanager.utility.RepoUtils;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class UsersFragment extends Fragment {

    private Context mContext = getContext();
    private RecyclerView mRecyclerView;
    private RecyclerView.ViewHolder mUserViewRecycleItem;
    private FloatingActionButton mUserAddFab;
    private FloatingActionButton mUserDelFab;
    private LinearLayoutManager mLinearLayoutManager;
    private List<User> mUserList;
    private UserRecyclerAdapter mUserRecyclerAdapter = new UserRecyclerAdapter();
    private MaterialCardView mUserMaterialCardView;
    private User mCurrentItemUser;



    //Constructors----------------------------------------------------------------------------------
    public UsersFragment() {
        // Required empty public constructor
    }

    public static UsersFragment newInstance() {

        Bundle args = new Bundle();

        UsersFragment fragment = new UsersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        initUI(view);

        setListeners();

        return view;
    }

    private void setListeners() {
        mUserAddFab.setOnClickListener(view -> {

           final View userDialogView = LayoutInflater.from(getActivity())
                    .inflate(R.layout.add_user_dialog_fragment, null);
            EditText userNameEt = userDialogView.findViewById(R.id.dialog_user_name_et);
            EditText userPassEt = userDialogView.findViewById(R.id.dialog_user_pass_et);
            RadioGroup userRd = userDialogView.findViewById(R.id.user_radio_group);
            User user = new User();

            new MaterialAlertDialogBuilder(getActivity())
                    .setView(userDialogView)
                    .setTitle("Add User")
                    .setPositiveButton("Add", (dialogInterface, i) -> {

                        user.setUserName(userNameEt.getText().toString());
                        user.setUserPassword(userPassEt.getText().toString());

                        int radioButtonID = userRd.getCheckedRadioButtonId();
                        RadioButton radioButton = (RadioButton) userRd
                                .findViewById(radioButtonID);
                        String selectedRadioButton = (String) radioButton.getText();
                        if (selectedRadioButton.equals("Yes"))
                            user.setIsUserAdmin(true);
                        else
                            user.setIsUserAdmin(false);

                        RepoUtils.insertUser(mContext, user);

                        //Update RecyclerView
                        mUserList = RepoUtils.getAllUsers(mContext);
                        mUserRecyclerAdapter.setUserList(mUserList);
                        mUserRecyclerAdapter.notifyDataSetChanged();

                    }).show();
        });

        mUserDelFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int checkedCount = 0;
                for (int i = 0; i < mUserRecyclerAdapter.getItemCount(); i++) {
                    Log.d("ERROR", "onClick: " + mRecyclerView.getLayoutManager().getItemCount()  + " i = " + i);

                    mUserViewRecycleItem = mRecyclerView.findViewHolderForLayoutPosition(i);
                    mUserMaterialCardView =
                            (MaterialCardView) mUserViewRecycleItem.itemView.findViewById(R.id.user_recycler_view_card);
                    if (mUserMaterialCardView.isChecked()) {
                        checkedCount++;
                        mCurrentItemUser = mUserRecyclerAdapter.getCurrentItemUser(i);
                        RepoUtils.deleteUser(getContext(), mCurrentItemUser);

                        //Update RecyclerView
                        mUserList = RepoUtils.getAllUsers(mContext);
                        mUserRecyclerAdapter.setUserList(mUserList);
                        mUserRecyclerAdapter.notifyDataSetChanged();

                    }
                }
                if (checkedCount == 0) {
                    Toast.makeText(getActivity(),
                            "No Action performed. You must select at least one user to delete."
                            , Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void initUI(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.users_recyclerView);
        mUserAddFab = view.findViewById(R.id.user_frag_add_fab);
        mUserDelFab = view.findViewById(R.id.user_frag_del_fab);

        mLinearLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mUserList = RepoUtils.getAllUsers(mContext);
        mUserRecyclerAdapter.setUserList(mUserList);
        mRecyclerView.setAdapter(mUserRecyclerAdapter);
    }

}
