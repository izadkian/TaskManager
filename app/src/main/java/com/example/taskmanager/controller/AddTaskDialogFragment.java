package com.example.taskmanager.controller;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.taskmanager.model.StateEnum;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.R;
import com.example.taskmanager.utility.RepoUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTaskDialogFragment extends DialogFragment {
    public static final int REQUEST_CODE_DATE_PICKER = 0;
    public static final String TAG_DATE_PICKER = "DatePicker";

    private EditText mTaskNameEditText;
    private EditText mTaskDescEditText;
    private RadioGroup mTaskStateRadioGroup;
    private TextView mDateTextView;
    private TextView mTimeTextView;

    //Callback for updating recyclerview after task insertion---------------------------------------
    private InsertTaskListener mInsertTaskListener;

    public interface InsertTaskListener {

        void onDialogPositiveClick(DialogFragment dialog);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mInsertTaskListener = (InsertTaskListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement InsertTaskListener on Activity");
        }
    }

    //----------------------------------------------------------------------------------------------
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final View dialogView = LayoutInflater.from(getActivity())
                .inflate(R.layout.add_task_dialog_fragment, null);

        initUI(dialogView);
        setListeners();

        return new MaterialAlertDialogBuilder(getActivity())
                .setView(dialogView)
                .setTitle(R.string.add_task_dialog)
                .setPositiveButton(R.string.dialog_add_button, (dialogInterface, i) -> {

                    Context mContext = AddTaskDialogFragment.this.getContext();
                    Task task = new Task();

                    task.setTaskName(mTaskNameEditText.getText().toString());
                    task.setDescription(mTaskDescEditText.getText().toString());

                    int radioButtonID = mTaskStateRadioGroup.getCheckedRadioButtonId();
                    RadioButton radioButton = (RadioButton) mTaskStateRadioGroup
                            .findViewById(radioButtonID);
                    String selectedRadioButton = (String) radioButton.getText();
                    if (selectedRadioButton.equals("To Do"))
                        task.setTaskState(StateEnum.TO_DO);
                    else if (selectedRadioButton.equals("Doing"))
                        task.setTaskState(StateEnum.DOING);
                    else
                        task.setTaskState(StateEnum.DONE);

                    task.setDateTime(mDateTextView.getText().toString());
                    task.setTime(mTimeTextView.getText().toString());

                    task.setUserID(RepoUtils.getCurrentUser(mContext).getUserID());

                    RepoUtils.insertTask(mContext, task);

                    //Callback invocation
                    mInsertTaskListener.onDialogPositiveClick(AddTaskDialogFragment.this);

                })
                .create();
    }

    private void setListeners() {
        mDateTextView.setOnClickListener(view -> {
            DatePickerFragment datePickerFragment = DatePickerFragment.newInstance();
            datePickerFragment.setTargetFragment(AddTaskDialogFragment.this, REQUEST_CODE_DATE_PICKER);
            datePickerFragment.show(getFragmentManager(), TAG_DATE_PICKER);
        });

        mTimeTextView.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            final int hour = calendar.get(Calendar.HOUR_OF_DAY);
            final int min = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                    (timePicker, i, i1) -> mTimeTextView.setText(i + " : " + i1), hour, min, false);
            timePickerDialog.show();
        });
    }

    private void initUI(View dialogView) {
        mTaskNameEditText = dialogView.findViewById(R.id.dialog_task_name_et);
        mTaskDescEditText = dialogView.findViewById(R.id.dialog_task_desc_et);
        mTaskStateRadioGroup = dialogView.findViewById(R.id.radio_group);
        mDateTextView = dialogView.findViewById(R.id.dialog_date_tv);
        mTimeTextView = dialogView.findViewById(R.id.dialog_time_tv);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("EXEC", "onActivityResult: " + "AddTaskDialogFragment");

        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_DATE_PICKER) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_TASK_DATE);

            String simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd").format(date);

            mDateTextView.setText(simpleDateFormat);
        }
    }
}

