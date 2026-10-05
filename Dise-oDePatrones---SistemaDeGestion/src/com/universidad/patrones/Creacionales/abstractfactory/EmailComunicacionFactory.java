package com.universidad.patrones.Creacionales.abstractfactory;

public class EmailComunicacionFactory implements ComunicacionFactory {
    @Override 
    public INotificador crearNotificador(){
        return new EmailNotificador();
    }

    @Override 
    public IFormateador crearFormateador(){
        return new EmailFormateador();
    }
}
