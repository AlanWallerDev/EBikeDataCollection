package com.alan.waller.e_bikedatacollection;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;
import java.util.List;

@Dao
public interface SessionDao {
    @Insert
    void insert(Session session);

    @Delete
    void delete(Session session);

    @Query("SELECT * FROM Session ORDER BY sid ASC")
    LiveData<List<Session>> getAll();

    @Query("SELECT * FROM Session WHERE")

    @Update
    void update(Session session);
}
