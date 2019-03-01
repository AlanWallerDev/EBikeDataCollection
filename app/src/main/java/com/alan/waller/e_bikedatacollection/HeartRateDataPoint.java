package com.alan.waller.e_bikedatacollection;

public class HeartRateDataPoint {

    int heartRate;
    long timestamp;

    public HeartRateDataPoint(int heartRate, long timestamp) {
        this.heartRate = heartRate;
        this.timestamp = timestamp;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
