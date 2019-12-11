package com.example.taskmanager.utility;

import android.content.Context;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskUserRepository;
import com.example.taskmanager.model.User;

import java.util.List;
import java.util.UUID;

public class RepoUtils {

    public static List<Task> getAllTask(Context context) {
        return TaskUserRepository.getInstance(context).getTasksFromRepo();
    }

    public static List<Task> getToDoTask(Context context) {
        return TaskUserRepository.getInstance(context).getToDoTasksFromRepo();
    }

    public static List<Task> getDoingTask(Context context) {
        return TaskUserRepository.getInstance(context).getDoingTasksFromRepo();
    }

    public static List<Task> getDoneTask(Context context) {
        return TaskUserRepository.getInstance(context).getDoneTasksFromRepo();
    }

    public static List<Task> getAllToDoTask(Context context) {
        return TaskUserRepository.getInstance(context).getAllToDoTasksFromRepo();
    }

    public static List<Task> getAllDoingTask(Context context) {
        return TaskUserRepository.getInstance(context).getAllDoingTasksFromRepo();
    }

    public static List<Task> getAllDoneTask(Context context) {
        return TaskUserRepository.getInstance(context).getAllDoneTasksFromRepo();
    }

    public static Task getSingleTask(Context context, UUID taskID) {
        return TaskUserRepository.getInstance(context).getSingleTaskFromRepo(taskID);
    }

    public static void insertTask(Context context, Task task) {
        TaskUserRepository.getInstance(context).insertTask(task);
    }

    public static void deleteTask(Context context, Task task) {
        TaskUserRepository.getInstance(context).deleteTask(task);
    }

    public static void updateTask(Context context, Task task) {
        TaskUserRepository.getInstance(context).updateTask(task);
    }

    public static void insertUser(Context context, User user) {
        TaskUserRepository.getInstance(context).insertUser(user);
    }

    public static void deleteUser(Context context, User user) {
        TaskUserRepository.getInstance(context).deleteUser(user);
    }

    public static User getCurrentUser(Context context) {
        return TaskUserRepository.getInstance(context).getCurrentUser();
    }

    public static void setCurrentUser(Context context, User user) {
        TaskUserRepository.getInstance(context).setCurrentUser(user);
    }

    public static List<User> getAllUsers(Context context) {
        return TaskUserRepository.getInstance(context).getUsersFromRepo();
    }

    public static User isUserExist(Context context, String name, String pass) {
        return TaskUserRepository.getInstance(context).getUserByNamePassFromRepo(name, pass);
    }

    public static Boolean isAdmin(Context context) {
        return TaskUserRepository.getInstance(context).getCurrentUser().getIsUserAdmin();
    }

}
