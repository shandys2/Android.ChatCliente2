package com.example.clienteandroid2.database;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MensajeViewModel extends AndroidViewModel {

    private AppRepository mRepository;

    private final LiveData<List<Mensaje>> mAllMessages;

    public MensajeViewModel (Application application) {
        super(application);
        mRepository = new AppRepository(application);
        mAllMessages = mRepository.getAllMessages();
    }

    public LiveData<List<Mensaje>> getAllMessages() { return mAllMessages; }

    public void insert(Mensaje msg) { mRepository.insert(msg); }
}