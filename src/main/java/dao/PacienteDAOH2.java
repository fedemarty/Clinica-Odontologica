package dao;

import model.Domicilio;
import model.Paciente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * DAO (Data Access Object) para la gestión de Pacientes en base de datos H2
 * Implementa patrón DAO según requerimientos Sprint 1 - Examen
 * 
 * Métodos implementados:
 * - buscarPacientePorId (completo)
 * - actualizarPaciente (implementado)
 * - eliminarPaciente (implementado)
 * - buscarTodos (implementado como listarPacientes)
 * - buscarPorString (implementado como buscarPorEmail)
 */
public class PacienteDAOH2 implements iDao<Paciente>{
    // Consultas SQL constantes para operaciones CRUD
    private static final String SQL_SELECT_ONE=" SELECT * FROM PACIENTES WHERE ID=?";


    /**
     * guardar
     * Guarda un nuevo paciente en la base de datos H2
     * Incluye asociación con Domicilio según diseño del Arquitecto
     */
    @Override
    public Paciente guardar(Paciente paciente) {
        Connection connection = null;
        try {
            // Conexión a H2 Database
            connection = BD.getConnection();
            
            // PreparedStatement para evitar SQL Injection
            PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO PACIENTES(NOMBRE, APELLIDO, NUMEROCONTACTO, FECHAINGRESO, DOMICILIO_ID, EMAIL) VALUES(?,?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
            
            // Seteo de parámetros según modelo Paciente
            ps.setString(1, paciente.getNombre());
            ps.setString(2, paciente.getApellido());
            ps.setInt(3, paciente.getNumeroContacto());
            ps.setDate(4, java.sql.Date.valueOf(paciente.getFechaIngreso()));
            ps.setInt(5, paciente.getDomicilio().getId()); // Relación con Domicilio
            ps.setString(6, paciente.getEmail());
            
            ps.executeUpdate();
            
            // Recuperar ID generado automáticamente por H2
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                paciente.setId(rs.getInt(1));
            }
            
            // Logging básico para trazabilidad (requerimiento Sprint 1)
            System.out.println("Paciente guardado: " + paciente.getNombre());
            return paciente;
            
        } catch (Exception e) {
            // Manejo básico de excepciones
            System.out.println("Error al guardar paciente: " + e.getMessage());
        } finally {
            // Cierre de conexión para evitar leaks
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public Paciente buscar(Integer id) {
        Connection connection=null;
        Paciente paciente= null;
        Domicilio domicilio= null;
        try{
            connection=BD.getConnection();
            //statement mundo java a sql
            Statement statement= connection.createStatement();
            PreparedStatement ps_select_one= connection.prepareStatement(SQL_SELECT_ONE);
            ps_select_one.setInt(1,id);
            //ResultSet mundo bdd a java
            ResultSet rs= ps_select_one.executeQuery();
            DomicilioDAOH2 daoAux= new DomicilioDAOH2();
            while(rs.next()){
                domicilio=daoAux.buscar(rs.getInt(6));
                paciente= new Paciente(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getDate(5).toLocalDate(),domicilio,rs.getString(7));
            }
        }catch (Exception e){
            e.getMessage();
        }
        System.out.println("paciente encontrado");
        return paciente;
    }

    /**
     * eliminarPaciente
     * Elimina un paciente de la base de datos por ID
     * Requerimiento: mantener datos consistentes
     */
    @Override
    public void eliminar(Integer id) {
        Connection connection = null;
        try {
            connection = BD.getConnection();
            
            // DELETE básico por ID
            PreparedStatement ps = connection.prepareStatement("DELETE FROM PACIENTES WHERE ID=?");
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            
            // Verificación de éxito de la operación
            if (filasAfectadas > 0) {
                System.out.println("Paciente eliminado con ID: " + id);
            } else {
                System.out.println("No se encontró paciente con ID: " + id);
            }
            
        } catch (Exception e) {
            System.out.println("Error al eliminar paciente: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }

    /**
     * actualizarPaciente
     * Actualiza todos los campos de un paciente existente
     * Mantiene relación con Domicilio
     */
    @Override
    public void actualizar(Paciente paciente) {
        Connection connection = null;
        try {
            connection = BD.getConnection();
            
            // UPDATE completo de todos los campos
            PreparedStatement ps = connection.prepareStatement(
                "UPDATE PACIENTES SET NOMBRE=?, APELLIDO=?, NUMEROCONTACTO=?, FECHAINGRESO=?, DOMICILIO_ID=?, EMAIL=? WHERE ID=?");
            
            // Seteo de todos los parámetros del paciente
            ps.setString(1, paciente.getNombre());
            ps.setString(2, paciente.getApellido());
            ps.setInt(3, paciente.getNumeroContacto());
            ps.setDate(4, java.sql.Date.valueOf(paciente.getFechaIngreso()));
            ps.setInt(5, paciente.getDomicilio().getId()); // Mantener relación
            ps.setString(6, paciente.getEmail());
            ps.setInt(7, paciente.getId()); // Condición WHERE
            
            int filasAfectadas = ps.executeUpdate();
            
            // Feedback de la operación
            if (filasAfectadas > 0) {
                System.out.println("Paciente actualizado: " + paciente.getNombre());
            } else {
                System.out.println("No se encontró paciente con ID: " + paciente.getId());
            }
            
        } catch (Exception e) {
            System.out.println("Error al actualizar paciente: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }

    /**
     * buscarPorString (implementado como buscarPorEmail)
     * Busca paciente por email - funcionalidad para confirmaciones y recordatorios
     * Incluye carga de Domicilio asociado
     */
    @Override
    public Paciente buscarGenerico(String email) {
        Connection connection = null;
        Paciente paciente = null;
        try {
            connection = BD.getConnection();
            
            // Búsqueda por email (parámetro String genérico)
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM PACIENTES WHERE EMAIL=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            // DAO auxiliar para cargar domicilio (relación)
            DomicilioDAOH2 daoAux = new DomicilioDAOH2();
            
            while (rs.next()) {
                // Carga del domicilio relacionado
                Domicilio domicilio = daoAux.buscar(rs.getInt(6));
                
                // Construcción del objeto Paciente completo
                paciente = new Paciente(rs.getInt(1), rs.getString(2), rs.getString(3),
                                      rs.getInt(4), rs.getDate(5).toLocalDate(),
                                      domicilio, rs.getString(7));
            }
            
        } catch (Exception e) {
            System.out.println("Error al buscar paciente por email: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        System.out.println("Búsqueda por email completada");
        return paciente;
    }

    /**
     * buscarTodos (expuesto como listarPacientes en Service)
     * Lista todos los pacientes de la base de datos
     * Incluye carga de domicilios para cada paciente
     */
    @Override
    public List<Paciente> buscarTodos() {
        Connection connection = null;
        List<Paciente> pacientes = new java.util.ArrayList<>();
        try {
            connection = BD.getConnection();
            
            // SELECT de todos los pacientes
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM PACIENTES");
            ResultSet rs = ps.executeQuery();
            DomicilioDAOH2 daoAux = new DomicilioDAOH2();
            
            // Iteración y construcción de lista
            while (rs.next()) {
                // Carga de domicilio para cada paciente
                Domicilio domicilio = daoAux.buscar(rs.getInt(6));
                
                // Construcción del objeto Paciente
                Paciente paciente = new Paciente(rs.getInt(1), rs.getString(2), rs.getString(3),
                                               rs.getInt(4), rs.getDate(5).toLocalDate(),
                                               domicilio, rs.getString(7));
                pacientes.add(paciente);
            }
            
        } catch (Exception e) {
            System.out.println("Error al listar pacientes: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        // Log del resultado
        System.out.println("Se encontraron " + pacientes.size() + " pacientes");
        return pacientes;
    }
}
