package com.universidad.dao;

import com.universidad.database.AccesoDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDAO {

    public void registrarMatriculaBD(String codigoEst, String nombreEst, String nombreCurso, double monto, String estado) {
        String sql = "INSERT INTO matricula_registro (codigo_estudiante, nombre_estudiante, curso, monto, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection cn = AccesoDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, codigoEst);
            ps.setString(2, nombreEst);
            ps.setString(3, nombreCurso);
            ps.setDouble(4, monto);
            ps.setString(5, estado);
            ps.executeUpdate();
            System.out.println("[DAO] Matrícula guardada en MySQL.");
        } catch (SQLException e) {
            System.err.println("[DAO ERROR] No se pudo guardar matrícula: " + e.getMessage());
        }
    }

    public List<Object[]> listarMatriculasBD() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT codigo_estudiante, nombre_estudiante, curso, monto, estado FROM matricula_registro";
        try (Connection cn = AccesoDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getString("codigo_estudiante"),
                    rs.getString("nombre_estudiante"),
                    rs.getString("curso"),
                    "S/ " + rs.getDouble("monto"),
                    rs.getString("estado")
                });
            }
        } catch (SQLException e) {
            System.err.println("[DAO ERROR] No se pudo listar matrículas: " + e.getMessage());
        }
        return lista;
    }
}