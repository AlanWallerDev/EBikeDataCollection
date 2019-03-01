package com.alan.waller.e_bikedatacollection;

import android.arch.persistence.room.*;
import android.support.annotation.*;
import android.util.Log;

@Entity
public class HeartRate {

    @PrimaryKey(autoGenerate = true)
    private int hid;

    private int heartRate;

    private int timeStamp;

   HeartRate(@NonNull int heartRate, @NonNull int timeStamp){
        this.heartRate = heartRate;
        this.timeStamp = timeStamp;
    }

    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }
}
