package com.example.clienteandroid2.network;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.clienteandroid2.MainActivity;
import com.example.clienteandroid2.database.AppRepository;
import com.example.clienteandroid2.database.Mensaje;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

public class Conexion {

    HandlerThread htEnvioMensaje;
    HandlerThread htRecibirMensaje;
    HandlerThread htConexion;
    Handler hEnvio;
    Handler hRecepcion;
    Handler hConexion;
    Handler hPrincipal;
    MainActivity mainActivity;

    Socket socket;
    BufferedReader br;
    BufferedWriter bw;
    private final String IP;
    private final int PUERTO;

    private AppRepository appRepository;

    public Conexion(MainActivity mainActivity) {
        IP="10.0.2.2";
        PUERTO=5556;
        appRepository=new AppRepository(mainActivity.getApplication());

        this.mainActivity=mainActivity;
        hPrincipal=new Handler();

        htEnvioMensaje= new HandlerThread("EnviarMsg");
        htEnvioMensaje.start();
        hEnvio=new Handler(htEnvioMensaje.getLooper());

        htRecibirMensaje= new HandlerThread("RecibirMsg");
        htRecibirMensaje.start();
        hRecepcion=new Handler(htRecibirMensaje.getLooper());

        htConexion= new HandlerThread("Conexion");
        htConexion.start();
        hConexion=new Handler(htConexion.getLooper());

        iniciarConexion();
    }

    public void iniciarConexion(){

        hConexion.post(new Runnable() {
            @Override
            public void run() {
                try {
                    socket= new Socket(IP,PUERTO);
                    br= new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    bw= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    bw.write("ANDROIDE");
                    bw.newLine();
                    bw.flush();
                    Log.i("ANTES DEL RECIBIR","ANTES");
                    recibirMensajes();
                    Log.i("DESPUES DEL RECIBIR","DESPUES");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void recibirMensajes(){
        hRecepcion.post(new Runnable() {
            @Override
            public void run() {
                String line;
                LiveData<List<Mensaje>> mensajes = (LiveData<List<Mensaje>>) appRepository.getAllMessages();

                Log.i("JH",mensajes.toString());

                try{

                    while((line= br.readLine())!=null){
                        String finalLine = line;
                        hPrincipal.post(new Runnable() {
                            @Override
                            public void run() {
                                mainActivity.mostrarMensaje(finalLine);
                            }
                        });
                    }
                }catch (IOException e){
                        e.printStackTrace();
                }
            }
        });
    }

    public void enviarMensaje(String msg){
        hEnvio.post(new Runnable() {
            @Override
            public void run() {
                Mensaje mensaje = new Mensaje();
                mensaje.usuario="ANDROID-USER";
                mensaje.texto=msg;
                appRepository.insert(mensaje);

               try {
                    bw.write(msg);
                    bw.newLine();
                    bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
