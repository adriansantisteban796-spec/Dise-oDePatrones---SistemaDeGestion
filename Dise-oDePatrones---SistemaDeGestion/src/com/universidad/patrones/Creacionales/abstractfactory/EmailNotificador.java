package com.universidad.patrones.Creacionales.abstractfactory;

public class EmailNotificador implements INotificador {
    @Override 
    public void enviar(String mensaje){
        System.out.println("Enviando Email Institucional: " +mensaje);
    }
}
