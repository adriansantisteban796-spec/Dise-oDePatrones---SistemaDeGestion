package com.universidad.patrones.Estructurales.composite;

import java.util.ArrayList;
import java.util.List;

public class ModuloCompuesto extends ComponenteEstructura {
    private final List<ComponenteEstructura> componentes = new ArrayList<>();

    public ModuloCompuesto(String nombre) { super(nombre); }

    public void agregar(ComponenteEstructura componente) { componentes.add(componente); }

    @Override
    public int obtenerCreditos() {
        int total = 0;
        for (ComponenteEstructura c : componentes) {
            total += c.obtenerCreditos();
        }
        return total;
    }

    @Override
    public void mostrarDetalle(String sangria) {
        System.out.println(sangria + "+ Módulo/Especialidad: " + nombre + " [Total Créditos: " + obtenerCreditos() + "]");
        for (ComponenteEstructura c : componentes) {
            c.mostrarDetalle(sangria + "   ");
        }
    }
}