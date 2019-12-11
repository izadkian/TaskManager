package com.example.taskmanager.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.example.taskmanager.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_TASK_DATE = "Extra Task Date";
    private android.widget.DatePicker mDatePicker;

    public DatePickerFragment() {
        // Required empty public constructor
    }

    public static DatePickerFragment newInstance() {

        Bundle args = new Bundle();

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View dialogView = LayoutInflater.from(getActivity())
                .inflate(R.layout.date_picker, null);

        mDatePicker = dialogView.findViewById(R.id.date_picker);

        initDatePicker();

        return new AlertDialog.Builder(getActivity())
                .setTitle("Date of Task")
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> sendResult())
                .setView(dialogView)
                .create();
    }

    private void sendResult() {
        int year = mDatePicker.getYear();
        int month = mDatePicker.getMonth();
        int day = mDatePicker.getDayOfMonth();

        GregorianCalendar calendar = new GregorianCalendar(year, month, day);
        Date date = calendar.getTime();
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TASK_DATE, date);

        Fragment fragment = getTargetFragment();
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }

    private void initDatePicker() {
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDatePicker.init(year, month, day, null);
    }
}
