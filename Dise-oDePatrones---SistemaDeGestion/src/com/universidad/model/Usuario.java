package com.universidad.model;

public class Usuario {
    protected int id;
    protected String nombre;
    protected String codigo;


    public Usuario(int id, String nombre, String codigo) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    
}
