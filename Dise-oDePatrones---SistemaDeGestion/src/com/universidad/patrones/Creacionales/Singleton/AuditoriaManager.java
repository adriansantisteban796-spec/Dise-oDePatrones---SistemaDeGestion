package com.universidad.patrones.Creacionales.Singleton;

import java.util.Date;

public class AuditoriaManager {
    private static AuditoriaManager instancia;

    private AuditoriaManager(){
        System.out.println("Inicializando Gestor Unificado de Auditoria");
    }

    public static synchronized AuditoriaManager getInstancia(){
        if (instancia == null) {
            instancia = new AuditoriaManager();
        }
        return instancia;
    }
    public void registrarEvento(String usuario, String detalleAccion){
        System.out.println("(AUDITORÍA SINGLETON) [" + new Date() + "] Usuario: " + usuario + " | Evento: " + detalleAccion);
    }
}
