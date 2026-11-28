package com.universidad.view;

import com.universidad.controller.MatriculaController;
import javax.swing.*;
import java.awt.*;

public class ProgramacionCursoView extends JInternalFrame {
    private final JComboBox<String> cboModalidad = new JComboBox<>(new String[]{"Presencial", "Virtual"});
    private final JTextField txtCarrera = new JTextField("Ingeniería de Sistemas", 15);
    private final JTextField txtCreditos = new JTextField("200", 8);
    private final JCheckBox chkTesis = new JCheckBox("Requiere Tesis", true);
    private final JTextArea txtSalida = new JTextArea(4, 30);
    private final MatriculaController controller = new MatriculaController();

    public ProgramacionCursoView() {
        super("Programación Curricular (Factory Method + Builder)", true, true, true, true);
        setSize(480, 320);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Modalidad de Dictado:"), gbc);
        gbc.gridx = 1; add(cboModalidad, gbc);

        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Carrera:"), gbc);
        gbc.gridx = 1; add(txtCarrera, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Créditos Totales:"), gbc);
        gbc.gridx = 1; add(txtCreditos, gbc);

        gbc.gridx = 1; gbc.gridy = 3; add(chkTesis, gbc);

        JButton btnConstruir = new JButton("Generar Plan y Modalidad");
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(btnConstruir, gbc);

        txtSalida.setEditable(false);
        gbc.gridy = 5;
        add(new JScrollPane(txtSalida), gbc);

        btnConstruir.addActionListener(e -> {
            try {
                String mod = (String) cboModalidad.getSelectedItem();
                String carrera = txtCarrera.getText().trim();
                int cred = Integer.parseInt(txtCreditos.getText().trim());
                boolean tesis = chkTesis.isSelected();

                String resultado = controller.programarCursoYPlan(mod, carrera, cred, tesis);
                txtSalida.setText(resultado);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese un número válido de créditos.");
            }
        });
    }
}