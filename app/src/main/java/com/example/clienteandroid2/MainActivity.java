package com.example.clienteandroid2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.clienteandroid2.database.MensajeViewModel;
import com.example.clienteandroid2.network.Conexion;

public class MainActivity extends AppCompatActivity {

    Button btn;
    private MensajeViewModel mMensajeViewModel;

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Conexion conexion= new Conexion(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final WordListAdapter adapter = new WordListAdapter(new WordListAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mMensajeViewModel = new ViewModelProvider(this).get(MensajeViewModel.class);

        mMensajeViewModel.getAllMessages().observe(this, words -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(words);
        });


        btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conexion.enviarMensaje("mensaje para enviar");
            }
        });

    }

    public void mostrarMensaje(String msg){
        Log.i("MENSAJE",msg);
    }
}