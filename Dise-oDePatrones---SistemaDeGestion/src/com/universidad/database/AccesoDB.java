package com.universidad.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccesoDB {
    private static Connection conexion = null;

    // Constructor privado (Singleton)
    private AccesoDB() {}

    public static Connection getConnection() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            try {
                String url = "jdbc:mysql://localhost:3306/sistema_gestion_academica?useSSL=false&serverTimezone=UTC";
                String usuario = "root";
                String clave = ""; // Coloca tu contraseña de MySQL Workbench/XAMPP si tienes una

                Class.forName("com.mysql.cj.jdbc.Driver");
                conexion = DriverManager.getConnection(url, usuario, clave);
                System.out.println("[DB SINGLETON] Conexión exitosa a MySQL: sistema_gestion_academica");
            } catch (ClassNotFoundException e) {
                throw new SQLException("No se encontró el driver JDBC de MySQL: " + e.getMessage());
            }
        }
        return conexion;
    }
}