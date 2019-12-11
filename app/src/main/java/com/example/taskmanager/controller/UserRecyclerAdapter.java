package com.example.taskmanager.controller;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanager.R;
import com.example.taskmanager.model.User;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder> {

    private List<User> mUserList = new ArrayList<>();

    public UserRecyclerAdapter() {
    }

    public void setUserList(List<User> userList) {
        mUserList = userList;
    }

    public User getCurrentItemUser(int position) {
        return mUserList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public MaterialCardView mMaterialCardView;
        public TextView mUserIdTv;
        public TextView mUserNameTv;
        public TextView mUserPassTv;
        public TextView mIsUserAdmin;
        public TextView mUserDateTv;


        public ViewHolder(View ItemView) {
            super(ItemView);
            mMaterialCardView = (MaterialCardView) ItemView.findViewById(R.id.user_recycler_view_card);
            mUserIdTv = (TextView) ItemView.findViewById(R.id.user_id_data_tv);
            mUserNameTv = (TextView) ItemView.findViewById(R.id.user_name_data_tv);
            mUserPassTv = (TextView) ItemView.findViewById(R.id.user_pass_data_tv);
            mIsUserAdmin = (TextView) ItemView.findViewById(R.id.user_isadmin_data_tv);
            mUserDateTv = (TextView) ItemView.findViewById(R.id.user_date_tv);

        }
    }

    @NonNull
    @Override
    public UserRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View ItemUserView = layoutInflater.inflate(R.layout.user_recyclerview_layout, parent, false);

        UserRecyclerAdapter.ViewHolder viewHolder = new UserRecyclerAdapter.ViewHolder(ItemUserView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserRecyclerAdapter.ViewHolder viewHolder, int position) {
        User bindUser = mUserList.get(position);

        MaterialCardView bindRecyclerMaterialCV = viewHolder.mMaterialCardView;
        bindRecyclerMaterialCV.setOnLongClickListener(view -> {
            MaterialCardView materialCardView = (MaterialCardView) view;
            materialCardView.setChecked(!materialCardView.isChecked());
            return true;
        });

        if (position % 2 == 1) {
            bindRecyclerMaterialCV.setCardBackgroundColor(Color.parseColor("#DE76AC"));
        } else {
            bindRecyclerMaterialCV.setCardBackgroundColor(Color.parseColor("#CC287D"));
        }

        TextView bindUserID = viewHolder.mUserIdTv;
        bindUserID.setText(bindUser.getUserID().toString());

        TextView bindUserName = viewHolder.mUserNameTv;
        bindUserName.setText(bindUser.getUserName());

        TextView bindUserPass = viewHolder.mUserPassTv;
        bindUserPass.setText(bindUser.getUserPassword());

        TextView bindIsUserAdmin = viewHolder.mIsUserAdmin;
        bindIsUserAdmin.setText(String.valueOf(bindUser.getIsUserAdmin()));

        TextView bindUserDate = viewHolder.mUserDateTv;
        bindUserDate.setText(bindUser.getMDate());

    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

}
