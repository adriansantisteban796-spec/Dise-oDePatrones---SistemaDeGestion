package com.universidad.patrones.Estructurales.adapter;


public class PagoAdapter implements IPagoService {
    private final SistemaPagosExterno pasarelaExterna;

    public PagoAdapter(SistemaPagosExterno pasarelaExterna) {
        this.pasarelaExterna = pasarelaExterna;
    }

    @Override
    public boolean procesarPagoMatricula(String codigoEstudiante, double monto) {
        System.out.println("[ADAPTER] Adaptando la solicitud al formato de la pasarela de pagos...");
        return pasarelaExterna.realizarCobroSoles(codigoEstudiante, monto);
    }
}