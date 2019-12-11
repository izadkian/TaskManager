package com.example.taskmanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.taskmanager.model.Database.greenDao.EnumConverter;
import com.example.taskmanager.model.Database.greenDao.UuidConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

import java.util.UUID;

import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "Task")
public class Task implements Parcelable {
    @Id(autoincrement = true)
    private Long _id;
    @Property(nameInDb = "task_uuid")
    @Index(unique = true)
    @Convert(converter = UuidConverter.class, columnType = String.class)
    private UUID mTaskID;
    @Property(nameInDb = "task_name")
    private String mTaskName;
    @Property(nameInDb = "task_desc")
    private String mDescription;
    @Property(nameInDb = "task_state")
    @Convert(converter = EnumConverter.class, columnType = String.class)
    private StateEnum mTaskState;
    @Property(nameInDb = "task_date")
    private String mDateTime;
    @Property
    private String mTime;
    @Property(nameInDb = "user_id")
    @Convert(converter = UuidConverter.class, columnType = String.class)
    private UUID mUserID;

    //Constructors----------------------------------------------------------------------------------
    public Task() {
        mTaskID = UUID.randomUUID();
    }

    public Task(UUID uuid) {
        mTaskID = uuid;
    }

    public Task(Parcel in) {
        this._id = in.readLong();
        this.mTaskID = UUID.fromString(in.readString());
        this.mTaskName = in.readString();
        this.mDescription = in.readString();
        this.mTaskState = enumFromString(in.readString());
        this.mDateTime = in.readString();
        this.mTime = in.readString();
        this.mUserID = UUID.fromString(in.readString());
    }

    //Parcelation-----------------------------------------------------------------------------------
    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(_id);
        parcel.writeString(mTaskID.toString());
        parcel.writeString(mTaskName);
        parcel.writeString(mDescription);
        parcel.writeString(mTaskState.toString());
        parcel.writeString(mDateTime);
        parcel.writeString(mTime);
        parcel.writeString(mUserID.toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //----------------------------------------------------------------------------------------------
    @Generated(hash = 168910690)
    public Task(Long _id, UUID mTaskID, String mTaskName, String mDescription,
                StateEnum mTaskState, String mDateTime, String mTime, UUID mUserID) {
        this._id = _id;
        this.mTaskID = mTaskID;
        this.mTaskName = mTaskName;
        this.mDescription = mDescription;
        this.mTaskState = mTaskState;
        this.mDateTime = mDateTime;
        this.mTime = mTime;
        this.mUserID = mUserID;
    }

    public UUID getTaskID() {
        return mTaskID;
    }

    public void setTaskID(UUID taskID) {
        mTaskID = taskID;
    }

    public String getTaskName() {
        return mTaskName;
    }

    public void setTaskName(String taskName) {
        mTaskName = taskName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public StateEnum getTaskState() {
        return mTaskState;
    }

    public void setTaskState(StateEnum taskState) {
        mTaskState = taskState;
    }

    public String getDateTime() {
        return mDateTime;
    }

    public void setDateTime(String dateTime) {
        mDateTime = dateTime;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
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

    public void set_id(long _id) {
        this._id = _id;
    }

    public UUID getMTaskID() {
        return this.mTaskID;
    }

    public void setMTaskID(UUID mTaskID) {
        this.mTaskID = mTaskID;
    }

    public String getMTaskName() {
        return this.mTaskName;
    }

    public void setMTaskName(String mTaskName) {
        this.mTaskName = mTaskName;
    }

    public String getMDescription() {
        return this.mDescription;
    }

    public void setMDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public StateEnum getMTaskState() {
        return this.mTaskState;
    }

    public void setMTaskState(StateEnum mTaskState) {
        this.mTaskState = mTaskState;
    }

    public String getMDateTime() {
        return this.mDateTime;
    }

    public void setMDateTime(String mDateTime) {
        this.mDateTime = mDateTime;
    }

    public UUID getMUserID() {
        return this.mUserID;
    }

    public void setMUserID(UUID mUserID) {
        this.mUserID = mUserID;
    }

    public String getMTime() {
        return this.mTime;
    }

    public void setMTime(String mTime) {
        this.mTime = mTime;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public StateEnum enumFromString(String state) {
        StateEnum stateEnum = null;

        switch (state) {
            case "TO_DO":
                stateEnum = StateEnum.TO_DO;
            case "Doing":
                stateEnum = StateEnum.DOING;
            case "Done":
                stateEnum = StateEnum.DONE;
        }

        return stateEnum;
    }

    @NonNull
    @Override
    public String toString() {
        return "Task{" + "\n" +
                this._id + "\n" +
                this.mTaskID.toString() + "\n" +
                this.mTaskName + "\n" +
                this.mDescription + "\n" +
                this.mTaskState + "\n" +
                this.mDateTime + "\n" +
                this.mTime + "\n" +
                this.mUserID + "\n" +
                "}";
    }
}
