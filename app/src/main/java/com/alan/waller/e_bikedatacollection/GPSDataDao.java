package com.alan.waller.e_bikedatacollection;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface GPSDataDao {

    @Insert
    void insert(GPSData gpsData);

    @Delete
    void delete(GPSData gpsData);

    @Query("SELECT * FROM GPSData ORDER BY gid ASC")
    LiveData<List<GPSData>> getAll();

    @Query("SELECT * FROM GPSData WHERE timestamp > :start AND timestamp < :end")
    LiveData<List<GPSData>> getSessionGPSData(long start, long end);

    @Update
    void update(GPSData gpsData);

}
