package com.universidad.view;

import com.universidad.controller.MatriculaController;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HistorialView extends JInternalFrame {
    private final JTextField txtCodigo = new JTextField(12);
    private final JTextArea txtResultado = new JTextArea(8, 30);
    private final MatriculaController controller = new MatriculaController();
    private final String rolUsuario;

    public HistorialView(String rolUsuario) {
        super("Consulta de Historial Académico (Patrón Proxy)", true, true, true, true);
        this.rolUsuario = rolUsuario;
        setSize(450, 300);
        setLayout(new BorderLayout(10, 10));

        JPanel panelSuperior = new JPanel();
        panelSuperior.add(new JLabel("Código Alumno:"));
        panelSuperior.add(txtCodigo);
        JButton btnBuscar = new JButton("Consultar");
        panelSuperior.add(btnBuscar);

        txtResultado.setEditable(false);
        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(txtResultado), BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> {
            List<String> historial = controller.consultarHistorial(rolUsuario, txtCodigo.getText().trim());
            txtResultado.setText("");
            if (historial == null) {
                JOptionPane.showMessageDialog(this, "Acceso Denegado por Proxy para el rol: " + rolUsuario, "Seguridad", JOptionPane.ERROR_MESSAGE);
            } else {
                for (String linea : historial) {
                    txtResultado.append(linea + "\n");
                }
            }
        });
    }
}