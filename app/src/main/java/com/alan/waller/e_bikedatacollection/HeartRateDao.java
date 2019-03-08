package com.alan.waller.e_bikedatacollection;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface HeartRateDao {

    @Insert
    void insert(HeartRate heartRate);

    @Delete
    void delete(HeartRate heartRate);

    @Query("SELECT * FROM HeartRate ORDER BY hid ASC")
    LiveData<List<HeartRate>> getAll();

    @Update
    void update(HeartRate heartRate);
}
