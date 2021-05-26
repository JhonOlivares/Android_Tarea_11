package com.example.olivarestolentino_tarea_11.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.olivarestolentino_tarea_11.modelo.*;

import java.util.ArrayList;
public class DBHelper {

    private DBAdapter dbAdapter;

    public DBHelper(Context context) {
        dbAdapter = new DBAdapter(context);
    }

    public boolean isOpen(){
        return dbAdapter.isOpen();
    }


    public long InsertarCliente(Cliente cliente){
        long r = 0;
        dbAdapter.open();
        r = dbAdapter.InsertarCliente(cliente);
        dbAdapter.close();
        return r;
    }

    public int ActualizarCliente(Cliente cliente){
        int r = 0;
        dbAdapter.open();
        r = dbAdapter.ActualizarCliente(cliente);
        dbAdapter.close();
        return r;
    }

    public Cliente GetClientePorId(int id){
        dbAdapter.open();
        Cliente cliente = dbAdapter.GetClientePorId(id);
        dbAdapter.close();
        return cliente;
    }

    public ArrayList<Cliente> GetClienteArrayList(){
        dbAdapter.open();
        ArrayList<Cliente> clientes = dbAdapter.GetClienteArrayList();
        dbAdapter.close();
        return clientes;
    }

    public int EliminarCliente(int id){
        dbAdapter.open();
        int resultado = dbAdapter.EliminarCliente(id);
        dbAdapter.close();
        return resultado;
    }


    public long InsertarDireccion(Direccion direccion){
        long r = 0;
        dbAdapter.open();
        r = dbAdapter.InsertarDireccion(direccion);
        dbAdapter.close();
        return r;
    }

    public int ActualizarDireccion(Direccion direccion){
        int r = 0;
        dbAdapter.open();
        r = dbAdapter.ActualizarDireccion(direccion);
        dbAdapter.close();
        return r;
    }

    public Direccion GetDireccionPorId(int id){
        dbAdapter.open();
        Direccion direccion = dbAdapter.GetDireccionPorId(id);
        dbAdapter.close();
        return direccion;
    }

    public ArrayList<Direccion> GetDireccionArrayList(){
        dbAdapter.open();
        ArrayList<Direccion> direcciones = dbAdapter.GetDireccionArrayList();
        dbAdapter.close();
        return direcciones;
    }

    public int EliminarDireccion(int id){
        dbAdapter.open();
        int resultado = dbAdapter.EliminarDireccion(id);
        dbAdapter.close();
        return resultado;
    }






}
