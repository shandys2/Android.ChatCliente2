package com.example.clienteandroid2.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Mensaje {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "texto")
    public String texto;

    @ColumnInfo(name = "usuario")
    public String usuario;


}