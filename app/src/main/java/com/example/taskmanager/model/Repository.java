package com.example.taskmanager.model;

import java.util.List;

public class Repository {

    private static Repository instance;
    private List<Task> mToDoTaskList;
    private List<Task> mDoingTaskList;
    private List<Task> mDoneTaskList;
    private int mCurrentOrientation;

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public List<Task> getToDoTaskList() {
        return mToDoTaskList;
    }

    public void setToDoTaskList(List<Task> toDoTaskList) {
        mToDoTaskList = toDoTaskList;
    }

    public List<Task> getDoingTaskList() {
        return mDoingTaskList;
    }

    public void setDoingTaskList(List<Task> doingTaskList) {
        mDoingTaskList = doingTaskList;
    }

    public List<Task> getDoneTaskList() {
        return mDoneTaskList;
    }

    public void setDoneTaskList(List<Task> doneTaskList) {
        mDoneTaskList = doneTaskList;
    }

    public int getCurrentOrientation() {
        return mCurrentOrientation;
    }

    public void setCurrentOrientation(int currentOrientation) {
        mCurrentOrientation = currentOrientation;
    }
}
