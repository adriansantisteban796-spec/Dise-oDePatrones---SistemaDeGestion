package com.universidad.service;

import java.util.Date;

public class AuditoriaService {
    public void registrarAccion(String usuario, String operacion) {
        System.out.println("AUDITORIA [" + new Date() + "] | Usuario: " + usuario + " | Acción: " + operacion);
    }
}
