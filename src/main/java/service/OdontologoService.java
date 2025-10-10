package service;

import dao.iDao;
import model.Odontologo;

import java.util.List;

/**
 * CLASE NUEVA - TAREA ESTUDIANTES SPRINT 1 EXAMEN
 * 
 * Service para lógica de negocio de Odontólogos
 * Métodos CRUD equivalentes a PacienteService
 * Validaciones específicas para odontólogos (matrícula obligatoria)
 * 
 * Preparado para migración a MVC en Sprint 2
 * Base para sistema de asignación de turnos en Sprint 3
 */
public class OdontologoService {
    private iDao<Odontologo> odontologoiDao;

    public OdontologoService(iDao<Odontologo> odontologoiDao) {
        this.odontologoiDao = odontologoiDao;
    }

    /**
     * LÓGICA DE NEGOCIO - Guardar Odontólogo
     * Validaciones específicas: nombre y matrícula obligatorios
     * Matrícula es requisito para habilitación en turnos
     */
    public Odontologo guardarOdontologo(Odontologo odontologo) {
        // Validación de negocio: nombre obligatorio
        if (odontologo.getNombre() == null || odontologo.getNombre().trim().isEmpty()) {
            System.out.println("Error: Nombre del odontologo es obligatorio");
            return null;
        }
        // Validación crítica: matrícula obligatoria (dato clave de auditoría)
        if (odontologo.getMatricula() == null || odontologo.getMatricula().trim().isEmpty()) {
            System.out.println("Error: Matricula del odontologo es obligatoria");
            return null;
        }
        // Delegación al DAO
        return odontologoiDao.guardar(odontologo);
    }

    public Odontologo buscarOdontologoPorId(Integer id) {
        if (id == null || id <= 0) {
            System.out.println("Error: ID debe ser válido");
            return null;
        }
        return odontologoiDao.buscar(id);
    }

    public List<Odontologo> buscarOdontologos() {
        return odontologoiDao.buscarTodos();
    }

    public void actualizarOdontologo(Odontologo odontologo) {
        if (odontologo.getId() == null) {
            System.out.println("Error: ID del odontologo no puede ser nulo");
            return;
        }
        if (odontologo.getNombre() == null || odontologo.getNombre().trim().isEmpty()) {
            System.out.println("Error: Nombre del odontologo es obligatorio");
            return;
        }
        odontologoiDao.actualizar(odontologo);
    }

    public void eliminarOdontologo(Integer id) {
        if (id == null || id <= 0) {
            System.out.println("Error: ID debe ser válido");
            return;
        }
        odontologoiDao.eliminar(id);
    }

    public Odontologo buscarOdontologoPorMatricula(String matricula) {
        if (matricula == null || matricula.trim().isEmpty()) {
            System.out.println("Error: Matricula no puede estar vacía");
            return null;
        }
        return odontologoiDao.buscarGenerico(matricula);
    }
}