package com.universidad.view;

import com.universidad.controller.MatriculaController;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private final JTextField txtUsuario = new JTextField("Adrian Santisteban", 15);
    private final JComboBox<String> cboRol = new JComboBox<>(new String[]{"COORDINADOR", "DOCENTE", "INVITADO"});
    private final MatriculaController controller = new MatriculaController();

    public LoginView() {
        setTitle("Acceso al Sistema Académico UTP");
        setSize(380, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1; add(txtUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Rol de Acceso:"), gbc);
        gbc.gridx = 1; add(cboRol, gbc);

        JButton btnIngresar = new JButton("Ingresar al Sistema");
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        add(btnIngresar, gbc);

        btnIngresar.addActionListener(e -> {
            String usuario = txtUsuario.getText().trim();
            String rol = (String) cboRol.getSelectedItem();
            if (usuario.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un nombre de usuario.");
                return;
            }
            controller.registrarInicioSesion(usuario, rol);
            new PrincipalView(usuario, rol).setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}