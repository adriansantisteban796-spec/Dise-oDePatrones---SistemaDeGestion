package com.universidad.patrones.Estructurales.proxy;

import java.util.List;

public interface IHistorialService {
    List<String> consultarHistorial(String codigoEstudiante);
}
