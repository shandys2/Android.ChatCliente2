package com.example.clienteandroid2.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AppRepository {

    private MensajeDao mensajeDao;
    private LiveData<List<Mensaje>> allMensajes;

    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mensajeDao = db.mensajeDao();
        allMensajes = mensajeDao.getAll();
    }


    public LiveData<List<Mensaje>> getAllMessages() {
        return allMensajes;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
   public void insert(Mensaje msg) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mensajeDao.insert(msg);
        });
    }
}