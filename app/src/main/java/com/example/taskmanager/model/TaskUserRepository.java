package com.example.taskmanager.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.taskmanager.model.Database.DBOpenHelper;

import java.util.List;
import java.util.UUID;

public class TaskUserRepository {

    private static TaskUserRepository sTaskUserRepository;
    private Context mContext;
    private TaskDao mTaskDao;
    private UserDao mUserDao;
    private User mCurrentUser;

    //Current User----------------------------------------------------------------------------------
    public User getCurrentUser() {
        return mCurrentUser;
    }

    public void setCurrentUser(User currentUser) {
        mCurrentUser = currentUser;
    }

    //Create Database-------------------------------------------------------------------------------
    private TaskUserRepository(Context context) {
        mContext = context.getApplicationContext();
        SQLiteDatabase db = new DBOpenHelper(mContext).getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        mTaskDao = daoSession.getTaskDao();
        mUserDao = daoSession.getUserDao();
    }

    //Repository Singleton--------------------------------------------------------------------------
    public static TaskUserRepository getInstance(Context context) {
        if (sTaskUserRepository == null)
            sTaskUserRepository = new TaskUserRepository(context);

        return sTaskUserRepository;
    }

    //----------------------------------------------------------------------------------------------
    public List<Task> getTasksFromRepo() {
        return mTaskDao.loadAll();
    }

    //----------------------------------------------------------------------------------------------
    public List<Task> getToDoTasksFromRepo() {
        return mTaskDao.queryBuilder()
                .where(TaskDao.Properties.MTaskState.eq(StateEnum.TO_DO),
                        TaskDao.Properties.MUserID.eq(mCurrentUser.getUserID()))
                .list();
    }

    //----------------------------------------------------------------------------------------------
    public List<Task> getDoingTasksFromRepo() {
        return mTaskDao.queryBuilder()
                .where(TaskDao.Properties.MTaskState.eq(StateEnum.DOING),
                        TaskDao.Properties.MUserID.eq(mCurrentUser.getUserID()))
                .list();
    }

    //----------------------------------------------------------------------------------------------
    public List<Task> getDoneTasksFromRepo() {
        return mTaskDao.queryBuilder()
                .where(TaskDao.Properties.MTaskState.eq(StateEnum.DONE),
                        TaskDao.Properties.MUserID.eq(mCurrentUser.getUserID()))
                .list();
    }

    //----------------------------------------------------------------------------------------------
    public Task getSingleTaskFromRepo(UUID uuid) {
        return mTaskDao.queryBuilder()
                .where(TaskDao.Properties.MTaskID.eq(uuid))
                .unique();
    }

    //----------------------------------------------------------------------------------------------
    public int getTaskPosition(UUID uuid) {
        List<Task> tasks = getTasksFromRepo();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTaskID().equals(uuid))
                return i;
        }
        return 0;
    }

    //----------------------------------------------------------------------------------------------
    public void insertTask(Task task) {
        mTaskDao.insert(task);
    }

    //----------------------------------------------------------------------------------------------
    public void updateTask(Task task) {
        mTaskDao.update(task);
    }

    //----------------------------------------------------------------------------------------------
    public void deleteTask(Task task) {
        mTaskDao.delete(task);

    }

    //----------------------------------------------------------------------------------------------
    public List<User> getUsersFromRepo() {
        return mUserDao.loadAll();
    }

    //----------------------------------------------------------------------------------------------
    public User getUserByUuidFromRepo(UUID uuid) {
        return mUserDao.queryBuilder()
                .where(UserDao.Properties.MUserID.eq(uuid))
                .unique();
    }

    //----------------------------------------------------------------------------------------------
    public User getUserByNamePassFromRepo(String name, String pass) {
        return mUserDao.queryBuilder()
                .where(UserDao.Properties.MUserName.eq(name), UserDao.Properties.MUserPassword.eq(pass))
                .unique();
    }

    //----------------------------------------------------------------------------------------------
    public int getUserPosition(UUID uuid) {
        List<User> users = getUsersFromRepo();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserID().equals(uuid))
                return i;
        }

        return 0;
    }

    //----------------------------------------------------------------------------------------------

    public void insertUser(User user) {
        mUserDao.insert(user);
    }

    //----------------------------------------------------------------------------------------------
    public void updateUser(User user) {
        mUserDao.update(user);
    }

    //----------------------------------------------------------------------------------------------
    public void deleteUser(User user) {
        mUserDao.delete(user);
    }

    //Admin Operations------------------------------------------------------------------------------
    public List<Task> getAllToDoTasksFromRepo() {
        return mTaskDao.queryBuilder()
                .where(TaskDao.Properties.MTaskState.eq(StateEnum.TO_DO))
                .list();
    }

    public List<Task> getAllDoingTasksFromRepo() {
        return mTaskDao.queryBuilder()
                .where(TaskDao.Properties.MTaskState.eq(StateEnum.DOING))
                .list();
    }

    public List<Task> getAllDoneTasksFromRepo() {
        return mTaskDao.queryBuilder()
                .where(TaskDao.Properties.MTaskState.eq(StateEnum.DONE))
                .list();
    }
    //----------------------------------------------------------------------------------------------

}
