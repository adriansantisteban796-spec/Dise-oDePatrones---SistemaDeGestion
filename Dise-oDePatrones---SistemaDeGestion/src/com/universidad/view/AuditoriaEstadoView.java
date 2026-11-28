package com.universidad.view;

import com.universidad.controller.MatriculaController;
import javax.swing.*;
import java.awt.*;

public class AuditoriaEstadoView extends JInternalFrame {
    private final JTextField txtCodigoAlumno = new JTextField("U25316356", 12);
    private final JLabel lblEstadoActual = new JLabel("SOLICITADA");
    private final JTextArea txtLog = new JTextArea(6, 32);
    private final MatriculaController controller = new MatriculaController();

    public AuditoriaEstadoView() {
        super("Ciclo de Vida y Trazabilidad (State + Memento + Observer)", true, true, true, true);
        setSize(480, 310);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Código Alumno a Notificar:"), gbc);
        gbc.gridx = 1; add(txtCodigoAlumno, gbc);

        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Estado Actual de Matrícula:"), gbc);
        lblEstadoActual.setFont(new Font("Arial", Font.BOLD, 13));
        lblEstadoActual.setForeground(new Color(0, 128, 0));
        gbc.gridx = 1; add(lblEstadoActual, gbc);

        JButton btnAvanzar = new JButton("Avanzar Estado y Guardar Snapshot");
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        add(btnAvanzar, gbc);

        txtLog.setEditable(false);
        gbc.gridy = 3;
        add(new JScrollPane(txtLog), gbc);

        btnAvanzar.addActionListener(e -> {
            String cod = txtCodigoAlumno.getText().trim();
            String anterior = controller.obtenerEstadoActual();
            String nuevo = controller.avanzarEstadoYRespaldar(cod);
            lblEstadoActual.setText(nuevo);
            txtLog.append("Snapshot guardado: [" + anterior + "] -> Nuevo estado: [" + nuevo + "]\nNotificación enviada a " + cod + "\n---\n");
        });
    }
}