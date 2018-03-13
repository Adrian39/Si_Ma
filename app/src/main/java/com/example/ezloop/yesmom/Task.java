package com.example.ezloop.yesmom;

import java.util.Date;

/**
 * Created by adria on 3/4/2018.
 */

public class Task {

    long mID;
    String mName;
    String description;
    //Date dueTime;
    //Date completedTime;
    //int remindTime;
    boolean mCompleted;

    public long getID() {
        return mID;
    }

    public void setID(long id) {
        this.mID = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    public Date getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(Date completedTime) {
        this.completedTime = completedTime;
    }

    public int getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(int remindTime) {
        this.remindTime = remindTime;
    }*/

    public boolean getCompleted(){
        return mCompleted;
    }

    public void setCompleted(int completed) {
        if (completed != 0){
            this.mCompleted = true;
        }
        else {
            this.mCompleted = false;
        }
    }

}
