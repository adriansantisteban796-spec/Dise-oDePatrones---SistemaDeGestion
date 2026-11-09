package com.universidad.patrones.Estructurales.proxy;

import java.util.ArrayList;
import java.util.List;

public class HistorialServiceImpl implements IHistorialService {
    @Override 
    public List<String> consultarHistorial(String codigoEstudiante){
        System.out.println("[REAL SUBJECT] Accediendo a BD para consultar historial de: " +codigoEstudiante);
        List<String> historial = new ArrayList<>();
        historial.add("2026-1: Diseño de patrones - Nota: 18");
        historial.add("2026-1: Arquitectura de Software - Nota: 16");
        return historial;
    }
}
