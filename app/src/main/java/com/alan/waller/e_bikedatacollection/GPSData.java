package com.alan.waller.e_bikedatacollection;

import android.arch.persistence.room.*;
import android.support.annotation.*;
import android.util.Log;

@Entity
public class GPSData {

    @PrimaryKey(autoGenerate = true)
    private int gid;

    private long timestamp;

    private String data;

    GPSData(@NonNull long timestamp, @NonNull String data){
        this.timestamp = timestamp;
        this.data = data;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String toString(){
        return "Timestamp: " + timestamp + ", Data: " + data;
    }
}
