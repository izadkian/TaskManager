package com.example.taskmanager.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder> implements
        Filterable {

    private List<Task> mTaskList = new ArrayList<>();
    private List<Task> mOriginalTaskList = new ArrayList<>();
    private OnItemClickListener listener;
    private OnShareClickListener shareListener;


    public TaskRecyclerAdapter() {

    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public interface OnShareClickListener {
        void onShareClick(View shareView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnShareClickListener(OnShareClickListener shareListener){
        this.shareListener = shareListener;
    }


    public void setTaskList(List<Task> taskList) {
        mTaskList = taskList;
        mOriginalTaskList = taskList;
    }

    public Task getCurrentItemTask(int position) {
        return mTaskList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public MaterialCardView mMaterialCardView;
        public TextView mRecyclerTaskNameTv;
        public TextView mRecyclerTaskDescTv;
        public TextView mRecyclerTaskStateTv;
        public TextView mThumbTv;
        public ImageView mShareIv;


        public ViewHolder(View ItemView) {
            super(ItemView);
            mMaterialCardView = (MaterialCardView) ItemView.findViewById(R.id.recycler_view_card);
            mRecyclerTaskNameTv = (TextView) ItemView.findViewById(R.id.recycler_taskname_tv);
            mRecyclerTaskDescTv = (TextView) ItemView.findViewById(R.id.recycler_taskdesc_tv);
            mRecyclerTaskStateTv = (TextView) ItemView.findViewById(R.id.recycler_taskstate_tv);
            mThumbTv = (TextView) ItemView.findViewById(R.id.thumb_tv);
            mShareIv = (ImageView) ItemView.findViewById(R.id.share_iv);

            ItemView.setOnClickListener(view -> {

                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(ItemView, position);
                    }
                }

            });

            mShareIv.setOnClickListener(view -> {
                if (shareListener!= null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        shareListener.onShareClick(ItemView, position);
                    }
                }
            });

        }
    }

    @NonNull
    @Override
    public TaskRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View ItemTaskView = layoutInflater.inflate(R.layout.recyclerview_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(ItemTaskView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskRecyclerAdapter.ViewHolder viewHolder, int position) {
        Task bindTask = mTaskList.get(position);

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

        TextView bindRecyclerTaskNameTv = viewHolder.mRecyclerTaskNameTv;
        bindRecyclerTaskNameTv.setText(bindTask.getTaskName());

        TextView bindRecyclerTaskDescTv = viewHolder.mRecyclerTaskDescTv;
        bindRecyclerTaskDescTv.setText(bindTask.getDescription());

        TextView bindRecyclerTaskStateTv = viewHolder.mRecyclerTaskStateTv;
        bindRecyclerTaskStateTv.setText(bindTask.getTaskState().

                toString());

        TextView bindRecyclerTaskThumb = viewHolder.mThumbTv;
        bindRecyclerTaskThumb.setText(bindTask.getTaskName().

                toUpperCase().

                substring(0, 1));

    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mTaskList = (List<Task>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Task> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = mOriginalTaskList;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }

    protected List<Task> getFilteredResults(String constraint) {
        List<Task> results = new ArrayList<>();

        for (Task taskItem : mTaskList) {
            if (taskItem.getTaskName().toLowerCase().contains(constraint)
                    || taskItem.getDescription().toLowerCase().contains(constraint)
                    || taskItem.getDateTime().toLowerCase().contains(constraint)
                    || taskItem.getTime().toLowerCase().contains(constraint)) {
                results.add(taskItem);
            }
        }
        return results;
    }

}




