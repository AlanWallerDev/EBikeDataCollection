package com.alan.waller.e_bikedatacollection;

import android.arch.persistence.room.*;
import android.support.annotation.*;
import android.util.Log;


@Entity
public class Session {

    @PrimaryKey(autoGenerate = true)
    private int sid;

    private String subjectName;

    private int subjectAge;

    private double subjectHeight;

    private double subjectWeight;

    private String subjectSex;

    private long startTime;

    private long endTime;

    Session(@NonNull String subjectName, @NonNull int subjectAge,
            @NonNull double subjectHeight, @NonNull double subjectWeight,
            @NonNull String subjectSex, @NonNull long startTime,
            @NonNull long endTime){
        this.subjectAge = subjectAge;
        this.subjectHeight = subjectHeight;
        this.subjectName = subjectName;
        this.subjectSex = subjectSex;
        this.subjectWeight = subjectWeight;
        this.endTime = endTime;
        this.startTime = startTime;
        Log.d("Session Object", "Session Created");
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getSubjectAge() {
        return subjectAge;
    }

    public void setSubjectAge(int subjectAge) {
        this.subjectAge = subjectAge;
    }

    public double getSubjectHeight() {
        return subjectHeight;
    }

    public void setSubjectHeight(float subjectHeight) {
        this.subjectHeight = subjectHeight;
    }

    public double getSubjectWeight() {
        return subjectWeight;
    }

    public void setSubjectWeight(float subjectWeight) {
        this.subjectWeight = subjectWeight;
    }

    public String getSubjectSex() {
        return subjectSex;
    }

    public void setSubjectSex(String subjectSex) {
        this.subjectSex = subjectSex;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String toString(){
        return "Subject: " + subjectName + ", Age: " + subjectAge + ", Weight: " + subjectWeight + ", Height: " + subjectHeight + ", Sex: " + subjectSex + ", startTime: " + startTime + ", Session end time: " + endTime;
    }
}
