package com.example.olivarestolentino_tarea_11.modelo;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Cliente implements Serializable {

    int idCliente; //primary key
    String nombre;
    Double saldo, limiteCredito, descuento;

    public Cliente() {
    }

    public Cliente(String nombre, Double saldo, Double limiteCredito, Double descuento) {
        this.nombre = nombre;
        this.saldo = saldo;
        this.limiteCredito = limiteCredito;
        this.descuento = descuento;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(Double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }
}
