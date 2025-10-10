package dao;

import model.Odontologo;

import java.sql.*;
import java.util.List;

/**
 * 
 * DAO completo para gestión de Odontólogos en H2 Database
 * Implementación de patrón DAO según arquitectura definida
 * 
 * CRUD COMPLETO:
 * - guardar, buscar, actualizar, eliminar, buscarTodos, buscarPorMatricula
 */
public class OdontologoDAOH2 implements iDao<Odontologo> {
    private static final String SQL_SELECT_ONE = "SELECT * FROM ODONTOLOGOS WHERE ID=?";

    /**
     * CRUD - GUARDAR ODONTOLOGO
     * Registra nuevo odontólogo con matrícula para habilitarlo en turnos
     * Matrícula es UNIQUE en base de datos (restricción de integridad)
     */
    @Override
    public Odontologo guardar(Odontologo odontologo) {
        Connection connection = null;
        try {
            connection = BD.getConnection();
            
            // INSERT sin ID - H2 auto-genera la clave primaria
            PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO ODONTOLOGOS(NOMBRE, APELLIDO, MATRICULA) VALUES(?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
            
            // Parámetros según modelo de negocio
            ps.setString(1, odontologo.getNombre());
            ps.setString(2, odontologo.getApellido());
            ps.setString(3, odontologo.getMatricula()); // Dato clave de auditoría
            
            ps.executeUpdate();
            
            // Recuperar ID generado por H2
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                odontologo.setId(rs.getInt(1));
            }
            
            // Logging para trazabilidad
            System.out.println("Odontologo guardado: " + odontologo.getNombre());
            return odontologo;
            
        } catch (Exception e) {
            System.out.println("Error al guardar odontologo: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public Odontologo buscar(Integer id) {
        Connection connection = null;
        Odontologo odontologo = null;
        try {
            connection = BD.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ONE);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                odontologo = new Odontologo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
            
        } catch (Exception e) {
            System.out.println("Error al buscar odontologo: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        System.out.println("odontologo encontrado");
        return odontologo;
    }

    @Override
    public void eliminar(Integer id) {
        Connection connection = null;
        try {
            connection = BD.getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM ODONTOLOGOS WHERE ID=?");
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Odontologo eliminado con ID: " + id);
            } else {
                System.out.println("No se encontró odontologo con ID: " + id);
            }
            
        } catch (Exception e) {
            System.out.println("Error al eliminar odontologo: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }

    @Override
    public void actualizar(Odontologo odontologo) {
        Connection connection = null;
        try {
            connection = BD.getConnection();
            PreparedStatement ps = connection.prepareStatement(
                "UPDATE ODONTOLOGOS SET NOMBRE=?, APELLIDO=?, MATRICULA=? WHERE ID=?");
            
            ps.setString(1, odontologo.getNombre());
            ps.setString(2, odontologo.getApellido());
            ps.setString(3, odontologo.getMatricula());
            ps.setInt(4, odontologo.getId());
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Odontologo actualizado: " + odontologo.getNombre());
            } else {
                System.out.println("No se encontró odontologo con ID: " + odontologo.getId());
            }
            
        } catch (Exception e) {
            System.out.println("Error al actualizar odontologo: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }

    /**
     * CRUD - BUSCAR POR MATRICULA (buscarGenerico)
     * Busca odontólogo por matrícula - dato clave de auditoría
     * Funcionalidad importante para verificar habilitaciones
     */
    @Override
    public Odontologo buscarGenerico(String matricula) {
        Connection connection = null;
        Odontologo odontologo = null;
        try {
            connection = BD.getConnection();
            
            // SELECT por matrícula (parámetro genérico String)
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM ODONTOLOGOS WHERE MATRICULA=?");
            ps.setString(1, matricula);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                // Construcción del objeto desde ResultSet
                odontologo = new Odontologo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
            
        } catch (Exception e) {
            System.out.println("Error al buscar odontologo por matricula: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        System.out.println("Búsqueda por matricula completada");
        return odontologo;
    }

    @Override
    public List<Odontologo> buscarTodos() {
        Connection connection = null;
        List<Odontologo> odontologos = new java.util.ArrayList<>();
        try {
            connection = BD.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM ODONTOLOGOS");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Odontologo odontologo = new Odontologo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                odontologos.add(odontologo);
            }
            
        } catch (Exception e) {
            System.out.println("Error al listar odontologos: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        System.out.println("Se encontraron " + odontologos.size() + " odontologos");
        return odontologos;
    }
}