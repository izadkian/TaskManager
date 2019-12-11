package com.example.taskmanager.controller;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.taskmanager.R;
import com.example.taskmanager.model.StateEnum;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.utility.RepoUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TaskDetailFragment extends Fragment {

    private Context mContext = getContext();
    private EditText mTaskIdEt;
    private EditText mTaskNameEt;
    private EditText mTaskDescEt;
    private EditText mTaskStateEt;
    private EditText mTaskDateEt;
    private EditText mTaskTimeEt;
    private UUID mUUID;
    private Task mCurrentTask;
    private static final String ARG_TASK_ID = "TaskID";

    public static TaskDetailFragment newInstance(UUID taskUuid) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_ID, taskUuid);
        TaskDetailFragment taskDetailFragment = new TaskDetailFragment();
        taskDetailFragment.setArguments(args);
        return taskDetailFragment;
    }

    public TaskDetailFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);

        initUI(view);

        setListeners();


        return view;
    }

    private void setListeners() {
        mTaskNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCurrentTask.setTaskName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mTaskDescEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCurrentTask.setDescription(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mTaskStateEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.equals("TO_DO"))
                    mCurrentTask.setTaskState(StateEnum.TO_DO);
                else if (charSequence.equals("DOING"))
                    mCurrentTask.setTaskState(StateEnum.DOING);
                else if (charSequence.equals("DONE"))
                    mCurrentTask.setTaskState(StateEnum.DONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mTaskDateEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCurrentTask.setDateTime(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mTaskTimeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCurrentTask.setTime(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initUI(View view) {
        mUUID = (UUID) getArguments().getSerializable(ARG_TASK_ID);
        mCurrentTask = RepoUtils.getSingleTask(mContext, mUUID);

        mTaskIdEt = view.findViewById(R.id.task_id_data_et);
        mTaskIdEt.setText(mUUID.toString());

        mTaskNameEt = view.findViewById(R.id.task_name_data_et);
        mTaskNameEt.setText(mCurrentTask.getTaskName());

        mTaskDescEt = view.findViewById(R.id.task_desc_data_et);
        mTaskDescEt.setText(mCurrentTask.getDescription());

        mTaskStateEt = view.findViewById(R.id.task_state_data_et);
        mTaskStateEt.setText(mCurrentTask.getTaskState().toString());

        mTaskDateEt = view.findViewById(R.id.task_date_data_et);
        mTaskDateEt.setText(mCurrentTask.getDateTime());

        mTaskTimeEt = view.findViewById(R.id.task_time_data_et);
        mTaskTimeEt.setText(mCurrentTask.getTime());
    }

    @Override
    public void onPause() {
        super.onPause();

        try {
            RepoUtils.updateTask(mContext, mCurrentTask);
        } catch (Exception e) {
            Log.e("ERROR", "cannot update crime", e);
        }

    }
}


