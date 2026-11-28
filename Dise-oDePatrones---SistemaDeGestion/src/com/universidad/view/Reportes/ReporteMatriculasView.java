package com.universidad.view.Reportes;

import com.universidad.controller.MatriculaController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class ReporteMatriculasView extends JInternalFrame {
    private final DefaultTableModel modeloTabla;
    private final MatriculaController controller = new MatriculaController();

    public ReporteMatriculasView() {
        super("Reporte Consolidado de Matrículas", true, true, true, true);
        setSize(620, 350);
        setLayout(new BorderLayout(10, 10));

        String[] columnas = {"Código", "Estudiante", "Curso", "Monto Pagado", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modeloTabla);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnActualizar = new JButton("Actualizar Reporte");
        JButton btnExportar = new JButton("Exportar a CSV");
        panelBotones.add(btnActualizar);
        panelBotones.add(btnExportar);

        add(panelBotones, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        cargarDatos();

        btnActualizar.addActionListener(e -> cargarDatos());
        btnExportar.addActionListener(e -> exportarCSV());
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        for (Object[] fila : controller.obtenerReporteMatriculas()) {
            modeloTabla.addRow(fila);
        }
    }

    private void exportarCSV() {
        try (FileWriter fw = new FileWriter("Reporte_Matriculas_UTP.csv")) {
            fw.write("Codigo,Estudiante,Curso,Monto,Estado\n");
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                fw.write(modeloTabla.getValueAt(i, 0) + "," +
                         modeloTabla.getValueAt(i, 1) + "," +
                         modeloTabla.getValueAt(i, 2) + "," +
                         modeloTabla.getValueAt(i, 3) + "," +
                         modeloTabla.getValueAt(i, 4) + "\n");
            }
            JOptionPane.showMessageDialog(this, "Reporte exportado como 'Reporte_Matriculas_UTP.csv'");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al exportar: " + ex.getMessage());
        }
    }
}