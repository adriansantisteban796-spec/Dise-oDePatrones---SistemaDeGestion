package com.universidad.view;

import com.universidad.controller.MatriculaController;
import javax.swing.*;
import java.awt.*;

public class MatriculaView extends JInternalFrame {
    private final JTextField txtCodigo = new JTextField(15);
    private final JTextField txtNombre = new JTextField(15);
    private final JComboBox<String> cboCurso = new JComboBox<>(new String[]{
        "Diseño de Patrones", "Base de Datos II", "Arquitectura de Software"
    });
    private final JCheckBox chkDeuda = new JCheckBox("Tiene deuda pendiente");
    private final MatriculaController controller = new MatriculaController();

    public MatriculaView() {
        super("Gestión de Matrícula (Facade + Adapter)", true, true, true, true);
        setSize(450, 300);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Código Estudiante:"), gbc);
        gbc.gridx = 1; add(txtCodigo, gbc);

        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Nombre Completo:"), gbc);
        gbc.gridx = 1; add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Curso a Matricular:"), gbc);
        gbc.gridx = 1; add(cboCurso, gbc);

        gbc.gridx = 1; gbc.gridy = 3; add(chkDeuda, gbc);

        JButton btnMatricular = new JButton("Confirmar Matrícula");
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(btnMatricular, gbc);

        btnMatricular.addActionListener(e -> procesarMatricula());
    }

    private void procesarMatricula() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String curso = (String) cboCurso.getSelectedItem();
        boolean deuda = chkDeuda.isSelected();

        if (codigo.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean exito = controller.registrarMatricula(codigo, nombre, deuda, curso, 10);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Matrícula registrada con éxito para " + nombre, "Confirmación", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo matricular (verifique deudas o vacantes).", "Error de Negocio", JOptionPane.ERROR_MESSAGE);
        }
    }
}