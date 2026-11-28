package com.universidad.view;

import javax.swing.*;
import java.awt.*;
import com.universidad.view.Reportes.ReporteAuditoriaView;
import com.universidad.view.Reportes.ReporteMatriculasView;

public class PrincipalView extends JFrame {
    private final JDesktopPane desktopPane;

    public PrincipalView(String usuarioLogueado, String rol) {
        setTitle("Sistema de Gestión Académica UTP | Usuario: " + usuarioLogueado + " | Rol: " + rol);
        setSize(1000, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        desktopPane = new JDesktopPane();
        desktopPane.setBackground(new Color(45, 52, 64));
        setContentPane(desktopPane);

        JMenuBar menuBar = new JMenuBar();

        // Menú 1: Archivo
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemCerrarSesion = new JMenuItem("Cambiar Usuario / Rol (Login)");
        itemCerrarSesion.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(itemCerrarSesion);
        menuArchivo.add(itemSalir);

        // Menú 2: Procesos Académicos (Formularios)
        JMenu menuProcesos = new JMenu("Procesos Académicos");
        JMenuItem itemMatricula = new JMenuItem("1. Registrar Matrícula (Facade + Adapter)");
        itemMatricula.addActionListener(e -> abrirVentana(new MatriculaView()));

        JMenuItem itemProgramacion = new JMenuItem("2. Programar Curso y Plan (Factory + Builder)");
        itemProgramacion.addActionListener(e -> abrirVentana(new ProgramacionCursoView()));

        JMenuItem itemEstados = new JMenuItem("3. Estados y Auditoría (State + Memento + Observer)");
        itemEstados.addActionListener(e -> abrirVentana(new AuditoriaEstadoView()));

        menuProcesos.add(itemMatricula);
        menuProcesos.add(itemProgramacion);
        menuProcesos.add(itemEstados);

        // Menú 3: Consultas y Seguridad
        JMenu menuConsultas = new JMenu("Consultas");
        JMenuItem itemHistorial = new JMenuItem("4. Historial Académico (Proxy)");
        itemHistorial.addActionListener(e -> abrirVentana(new HistorialView(rol)));
        menuConsultas.add(itemHistorial);

        // Menú 4: Reportes (Tablas JTable)
        JMenu menuReportes = new JMenu("Reportes");
        JMenuItem itemRepMatriculas = new JMenuItem("Reporte de Matrículas Registradas");
        itemRepMatriculas.addActionListener(e -> abrirVentana(new ReporteMatriculasView()));

        JMenuItem itemRepAuditoria = new JMenuItem("Reporte de Auditoría del Sistema");
        itemRepAuditoria.addActionListener(e -> abrirVentana(new ReporteAuditoriaView()));

        menuReportes.add(itemRepMatriculas);
        menuReportes.add(itemRepAuditoria);

        menuBar.add(menuArchivo);
        menuBar.add(menuProcesos);
        menuBar.add(menuConsultas);
        menuBar.add(menuReportes);
        setJMenuBar(menuBar);
    }

    private void abrirVentana(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
}