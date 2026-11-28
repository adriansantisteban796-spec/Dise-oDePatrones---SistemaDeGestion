package com.universidad.view.Reportes;


import com.universidad.controller.MatriculaController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReporteAuditoriaView extends JInternalFrame {
    private final DefaultTableModel modeloTabla;
    private final MatriculaController controller = new MatriculaController();

    public ReporteAuditoriaView() {
        super("Reporte de Auditoría y Seguridad (Singleton)", true, true, true, true);
        setSize(620, 320);
        setLayout(new BorderLayout(10, 10));

        String[] columnas = {"Hora", "Usuario / Código", "Módulo", "Detalle del Evento"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modeloTabla);

        JButton btnRefrescar = new JButton("Refrescar Logs de Auditoría");
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(btnRefrescar);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        cargarLogs();
        btnRefrescar.addActionListener(e -> cargarLogs());
    }

    private void cargarLogs() {
        modeloTabla.setRowCount(0);
        for (Object[] fila : controller.obtenerReporteAuditoria()) {
            modeloTabla.addRow(fila);
        }
    }
}