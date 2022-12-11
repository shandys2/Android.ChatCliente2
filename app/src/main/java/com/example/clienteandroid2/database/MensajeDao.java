package com.example.clienteandroid2.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MensajeDao {

    @Query("SELECT * FROM mensaje")
    LiveData<List<Mensaje>> getAll();

    @Insert
    void insert(Mensaje mensaje);


}