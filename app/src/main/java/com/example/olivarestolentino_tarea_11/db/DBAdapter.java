package com.example.olivarestolentino_tarea_11.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.olivarestolentino_tarea_11.modelo.*;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DBAdapter {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DB_Tarea_11";
    
    
    //CLIENTE STRINGS
    private static final String TABLE_CLIENTE = "table_cliente";

    private static final String CLIENTE_ID = "idCliente";// INTEGER PRIMARY KEY AUTOINCREMENT
    private static final String CLIENTE_NOMBRE = "nombre";// TEXT NOT NULL
    private static final String CLIENTE_SALDO = "saldo";// REAL DEFAULT 0
    private static final String CLIENTE_LIMITECREDITO = "limiteCredito";// REAL DEFAULT 0
    private static final String CLIENTE_DESCUENTO = "descuento";// REAL DEFAULT 0

    //SENTENCIA CREAR TABLA CLIENTE
    private static final String CREATE_TABLE_CLIENTE = "CREATE TABLE " + TABLE_CLIENTE +
            "(" +
            CLIENTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CLIENTE_NOMBRE + " TEXT NOT NULL, " +
            CLIENTE_SALDO + " REAL DEFAULT 0, " +
            CLIENTE_LIMITECREDITO + " REAL DEFAULT 0, " +
            CLIENTE_DESCUENTO + " REAL DEFAULT 0" +
            ")";
    //---------------------------------------------------



    //DIRECCION STRINGS
    private static final String TABLE_DIRECCION = "table_direccion";

    private static final String DIRECCION_ID = "idDireccion";// INTEGER PRIMARY KEY AUTOINCREMENT
    private static final String DIRECCION_NUMERO = "numero";// TEXT
    private static final String DIRECCION_CALLE = "calle";// TEXT NOT NULL
    private static final String DIRECCION_COMUNA = "comuna";// TEXT
    private static final String DIRECCION_CIUDAD = "ciudad";// TEXT
    private static final String DIRECCION_IDCLIENTE = "idCliente";// INTEGER NOT NULL

    //SENTENCIA CREAR TABLA DIRECCION
    private static final String CREATE_TABLE_DIRECCION = "CREATE TABLE " + TABLE_DIRECCION +
            "(" +
            DIRECCION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DIRECCION_NUMERO + " TEXT, " +
            DIRECCION_CALLE + " TEXT NOT NULL, " +
            DIRECCION_COMUNA + " TEXT, " +
            DIRECCION_CIUDAD + " TEXT, " +
            DIRECCION_IDCLIENTE + " INTEGER NOT NULL" +
            ")";
    //---------------------------------------------------




    private DatabaseHelper databasehelper;
    private SQLiteDatabase db; //objeto de base de datos
    private static Context context;

    public DBAdapter(Context context){
        this.context = context;
        databasehelper = new DatabaseHelper(context);
    }

    // important class
    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_TABLE_CLIENTE);
            db.execSQL(CREATE_TABLE_DIRECCION);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIRECCION);

            db.execSQL(CREATE_TABLE_CLIENTE);
            db.execSQL(CREATE_TABLE_DIRECCION);
        }
    }

    public DBAdapter open() throws SQLiteException{
        try {
            db = databasehelper.getWritableDatabase();// nos permite escribir y leer en la base de datos
        }catch (SQLiteException e){
            Toast.makeText(context, "Error al abrir la base de datos", Toast.LENGTH_SHORT).show();
        }
        return this;
    }

    public boolean isCreated(){
        if (db != null){
            return db.isOpen();
        }
        return false;
    }

    public boolean isOpen(){
        if (db == null){
            return false;
        }
        return db.isOpen();
    }

    public void close(){
        databasehelper.close();
        db.close();
    }

//    CRUD CLIENTE

    public long InsertarCliente(Cliente cliente){
        ContentValues values = new ContentValues();
        values.put(CLIENTE_NOMBRE, cliente.getNombre());
        values.put(CLIENTE_SALDO, cliente.getSaldo().toString());
        values.put(CLIENTE_LIMITECREDITO, cliente.getLimiteCredito().toString());
        values.put(CLIENTE_DESCUENTO, cliente.getDescuento().toString());
        return db.insert(TABLE_CLIENTE, null, values);
    }


    public int ActualizarCliente(Cliente cliente){
        ContentValues values = new ContentValues();
        values.put(CLIENTE_NOMBRE, cliente.getNombre());
        values.put(CLIENTE_SALDO, cliente.getSaldo());
        values.put(CLIENTE_LIMITECREDITO, cliente.getLimiteCredito());
        values.put(CLIENTE_DESCUENTO, cliente.getDescuento());
        return db.update(TABLE_CLIENTE + "", values, CLIENTE_ID + " = " + cliente.getIdCliente(), null);
    }

    public Cliente GetClientePorId(int id){
        Cliente cliente = null;
        if (id > 0){
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CLIENTE + " WHERE " + CLIENTE_ID + " = " + id, null);
            if (cursor.moveToFirst()){
                cliente = new Cliente();
                cliente.setIdCliente(cursor.getInt(0));
                cliente.setNombre(cursor.getString(1));
                cliente.setSaldo(cursor.getDouble(2));
                cliente.setLimiteCredito(cursor.getDouble(3));
                cliente.setDescuento(cursor.getDouble(4));
            }
        }
        return cliente;
    }

    public ArrayList<Cliente> GetClienteArrayList(){
        ArrayList<Cliente> clientes = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + TABLE_CLIENTE;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()){
                do{
                    Cliente cliente = new Cliente();
                    cliente.setIdCliente(cursor.getInt(0));
                    cliente.setNombre(cursor.getString(1));
                    cliente.setSaldo(cursor.getDouble(2));
                    cliente.setLimiteCredito(cursor.getDouble(3));
                    cliente.setDescuento(cursor.getDouble(4));
                    clientes.add(cliente);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return clientes;
        }catch (Exception e){
            return null;
        }
    }

    public int EliminarCliente(int id){
        int r = db.delete(TABLE_CLIENTE + "", CLIENTE_ID + " = " + id, null);// este metodo devuelve el numero de elrmentos eliminados
        return r;
    }



    // CRUD DIRECCION

    public long InsertarDireccion(Direccion direccion){
        ContentValues values = new ContentValues();
        values.put(DIRECCION_NUMERO, direccion.getNumero());
        values.put(DIRECCION_CALLE, direccion.getCalle());
        values.put(DIRECCION_COMUNA, direccion.getComuna());
        values.put(DIRECCION_CIUDAD, direccion.getCiudad());
        values.put(DIRECCION_IDCLIENTE, direccion.getIdCliente());
        return db.insert(TABLE_DIRECCION, null, values);
    }

    public int ActualizarDireccion(Direccion direccion){
        ContentValues values = new ContentValues();
        values.put(DIRECCION_NUMERO, direccion.getNumero());
        values.put(DIRECCION_CALLE, direccion.getCalle());
        values.put(DIRECCION_COMUNA, direccion.getComuna());
        values.put(DIRECCION_CIUDAD, direccion.getCiudad());
        values.put(DIRECCION_IDCLIENTE, direccion.getIdCliente());
        return db.update(TABLE_DIRECCION + "", values, DIRECCION_ID + " = " + direccion.getIdDireccion(), null);
    }

    public Direccion GetDireccionPorId(int id){
        Direccion direccion = null;
        if (id > 0){
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DIRECCION + " WHERE " + DIRECCION_ID + " = " + id, null);
            if (cursor.moveToFirst()){
                direccion = new Direccion();
                direccion.setIdDireccion(cursor.getInt(0));
                direccion.setNumero(cursor.getString(1));
                direccion.setCalle(cursor.getString(2));
                direccion.setComuna(cursor.getString(3));
                direccion.setCiudad(cursor.getString(4));
                direccion.setIdCliente(cursor.getInt(5));
            }
        }
        return direccion;
    }

    public ArrayList<Direccion> GetDireccionArrayList(){
        ArrayList<Direccion> direcciones = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + TABLE_DIRECCION;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()){
                do{
                    Direccion direccion = new Direccion();
                    direccion.setIdDireccion(cursor.getInt(0));
                    direccion.setNumero(cursor.getString(1));
                    direccion.setCalle(cursor.getString(2));
                    direccion.setComuna(cursor.getString(3));
                    direccion.setCiudad(cursor.getString(4));
                    direccion.setIdCliente(cursor.getInt(5));
                    direcciones.add(direccion);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return direcciones;
        }catch (Exception e){
            return null;
        }
    }

    public int EliminarDireccion(int id){
        int r = db.delete(TABLE_DIRECCION + "", DIRECCION_ID + " = " + id, null);
        return r;
    }




}