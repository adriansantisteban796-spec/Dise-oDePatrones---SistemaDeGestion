package com.universidad.patrones.Estructurales.proxy;

import java.util.List;

public class HistorialServiceProxy implements IHistorialService {
    private final IHistorialService servicioReal;
    private final String rolUsuario;

    public HistorialServiceProxy(String rolUsuario) {
        this.servicioReal = new HistorialServiceImpl();
        this.rolUsuario = rolUsuario;
    }

    @Override
    public List<String> consultarHistorial(String codigoEstudiante) {
        if ("COORDINADOR".equalsIgnoreCase(rolUsuario) || "DOCENTE".equalsIgnoreCase(rolUsuario)) {
            System.out.println("[PROXY SUCCESS] Acceso permitido para el rol: " + rolUsuario);
            return servicioReal.consultarHistorial(codigoEstudiante);
        }
        System.out.println("[PROXY ALERTA] Acceso denegado. El rol '" + rolUsuario + "' no tiene permisos para ver historiales.");
        return null;
    }
}