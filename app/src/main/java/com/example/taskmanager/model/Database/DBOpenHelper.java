package com.example.taskmanager.model.Database;

import android.content.Context;

import androidx.annotation.Nullable;

import com.example.taskmanager.model.DaoMaster;

public class DBOpenHelper extends DaoMaster.OpenHelper {
    public static final String NAME = "Task_User.db";

    public DBOpenHelper(@Nullable Context context) {
        super(context, NAME);
    }
}
