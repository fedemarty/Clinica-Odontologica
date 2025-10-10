package service;

import dao.iDao;
import model.Paciente;

import java.util.List;

/**
 * Service para lógica de negocio de Pacientes
 * Actúa como puente entre controladores y DAOs
 * Preparado para migración a MVC en Sprint 2
 * 
 * MÉTODOS COMPLETADOS POR ESTUDIANTES:
 * - actualizarPaciente, eliminarPaciente, buscarPorEmail
 */
public class PacienteService {
    private iDao<Paciente> pacienteiDao;

    public PacienteService(iDao<Paciente> pacienteiDao) {
        this.pacienteiDao = pacienteiDao;
    }
    public Paciente guardarPaciente(Paciente paciente){
        return pacienteiDao.guardar(paciente);
    }
    public Paciente buscarPacientePorId(Integer id){
        return pacienteiDao.buscar(id);
    }
    public List<Paciente> buscarPacientes(){
        return pacienteiDao.buscarTodos();
    }
    
    /**
     * TAREA ESTUDIANTES - actualizarPaciente
     * Lógica de negocio para actualización de pacientes
     * Incluye validaciones básicas
     */
    public void actualizarPaciente(Paciente paciente){
        // Validación de negocio: ID obligatorio para actualización
        if (paciente.getId() == null) {
            System.out.println("Error: ID del paciente no puede ser nulo");
            return;
        }
        // Delegación al DAO
        pacienteiDao.actualizar(paciente);
    }
    
    /**
     * TAREA ESTUDIANTES - eliminarPaciente
     * Lógica de negocio para eliminación por ID
     * Mantiene datos consistentes según requerimiento
     */
    public void eliminarPaciente(Integer id){
        // Validación de negocio: ID válido
        if (id == null || id <= 0) {
            System.out.println("Error: ID debe ser válido");
            return;
        }
        // Delegación al DAO
        pacienteiDao.eliminar(id);
    }
    
    /**
     * TAREA ESTUDIANTES - buscarPorString (implementado como buscarPorEmail)
     * Búsqueda por email para confirmaciones y recordatorios
     * Funcionalidad clave para la clínica
     */
    public Paciente buscarPacientePorEmail(String email){
        // Validación de entrada
        if (email == null || email.trim().isEmpty()) {
            System.out.println("Error: Email no puede estar vacío");
            return null;
        }
        // Delegación al DAO
        return pacienteiDao.buscarGenerico(email);
    }
}
