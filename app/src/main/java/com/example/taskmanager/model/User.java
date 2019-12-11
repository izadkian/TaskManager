package com.example.taskmanager.model;

import android.os.Parcelable;

import com.example.taskmanager.model.Database.greenDao.UuidConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

import java.util.UUID;

import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "User")
public class User {
    @Id(autoincrement = true)
    private Long _id;
    @Property(nameInDb = "user_id")
    @Index(unique = true)
    @Convert(converter = UuidConverter.class, columnType = String.class)
    private UUID mUserID;
    @Property(nameInDb = "user_name")
    private String mUserName;
    @Property(nameInDb = "user_password")
    private String mUserPassword;
    @Property(nameInDb = "user_date")
    private String mDate;
    @Property(nameInDb = "is_user_admin")
    private boolean isUserAdmin;

    public User() {
        mUserID = UUID.randomUUID();
    }

    public User(UUID uuid) {
        mUserID = uuid;
    }

    @Generated(hash = 328289108)
    public User(Long _id, UUID mUserID, String mUserName, String mUserPassword,
                String mDate, boolean isUserAdmin) {
        this._id = _id;
        this.mUserID = mUserID;
        this.mUserName = mUserName;
        this.mUserPassword = mUserPassword;
        this.mDate = mDate;
        this.isUserAdmin = isUserAdmin;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getUserPassword() {
        return mUserPassword;
    }

    public void setUserPassword(String userPassword) {
        mUserPassword = userPassword;
    }

    public UUID getUserID() {
        return mUserID;
    }

    public void setUserID(UUID userID) {
        mUserID = userID;
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public UUID getMUserID() {
        return this.mUserID;
    }

    public void setMUserID(UUID mUserID) {
        this.mUserID = mUserID;
    }

    public String getMUserName() {
        return this.mUserName;
    }

    public void setMUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getMUserPassword() {
        return this.mUserPassword;
    }

    public void setMUserPassword(String mUserPassword) {
        this.mUserPassword = mUserPassword;
    }

    public boolean getIsUserAdmin() {
        return this.isUserAdmin;
    }

    public void setIsUserAdmin(boolean isUserAdmin) {
        this.isUserAdmin = isUserAdmin;
    }

    public String getMDate() {
        return this.mDate;
    }

    public void setMDate(String mDate) {
        this.mDate = mDate;
    }


}
