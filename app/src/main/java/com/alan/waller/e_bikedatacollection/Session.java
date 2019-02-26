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

    private Long sessionLength;

    //todo: unsure how to store date, i believe timestamp is a long value?
    private String sessionDate;

    Session(@NonNull String subjectName, @NonNull int subjectAge,
            @NonNull double subjectHeight, @NonNull double subjectWeight,
            @NonNull String subjectSex, @NonNull Long sessionLength,
            @NonNull String sessionDate){
        this.subjectAge = subjectAge;
        this.subjectHeight = subjectHeight;
        this.subjectName = subjectName;
        this.subjectSex = subjectSex;
        this.subjectWeight = subjectWeight;
        this.sessionDate = sessionDate;
        this.sessionLength = sessionLength;
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

    public Long getSessionLength() {
        return sessionLength;
    }

    public void setSessionLength(Long sessionLength) {
        this.sessionLength = sessionLength;
    }

    public String getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(String sessionDate) {
        this.sessionDate = sessionDate;
    }


    public String toString(){
        return "Subject: " + subjectName + ", Age: " + subjectAge + ", Weight: " + subjectWeight + ", Height: " + subjectHeight + ", Sex: " + subjectSex + ", Date: " + sessionDate + ", Session Length : " + sessionLength;
    }
}
