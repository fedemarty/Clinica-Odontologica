package com.clinicaodontologica.service;

import com.clinicaodontologica.entity.Turno;
import com.clinicaodontologica.repository.TurnoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestión de Turnos
 * Capa de lógica de negocio con validación y delegación a Repository
 */
@Service
public class TurnoService {
    
    private static final Logger logger = Logger.getLogger(TurnoService.class);
    
    @Autowired
    private TurnoRepository turnoRepository;
    
    /**
     * Guardar un nuevo turno
     * @param turno Turno a guardar
     * @return Turno guardado con ID generado
     */
    public Turno guardarTurno(Turno turno) {
        // Validaciones de negocio
        if (turno == null) {
            logger.error("Error al guardar turno: turno es null");
            throw new IllegalArgumentException("El turno no puede ser null");
        }
        
        if (turno.getPaciente() == null || turno.getPaciente().getId() == null) {
            logger.error("Error al guardar turno: paciente es requerido");
            throw new IllegalArgumentException("El paciente es requerido");
        }
        
        if (turno.getOdontologo() == null || turno.getOdontologo().getId() == null) {
            logger.error("Error al guardar turno: odontólogo es requerido");
            throw new IllegalArgumentException("El odontólogo es requerido");
        }
        
        if (turno.getFechaHora() == null) {
            logger.error("Error al guardar turno: fecha y hora son requeridas");
            throw new IllegalArgumentException("La fecha y hora son requeridas");
        }
        
        Turno turnoGuardado = turnoRepository.save(turno);
        logger.info("Turno guardado: " + turnoGuardado);
        return turnoGuardado;
    }
    
    /**
     * Buscar turno por ID
     * @param id ID del turno
     * @return Optional con el turno si existe
     */
    public Optional<Turno> buscarTurnoPorId(Integer id) {
        if (id == null || id <= 0) {
            logger.error("Error al buscar turno: ID inválido " + id);
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }
        
        Optional<Turno> turno = turnoRepository.findById(id);
        if (turno.isPresent()) {
            logger.info("Turno encontrado: " + turno.get());
        } else {
            logger.warn("Turno no encontrado con ID: " + id);
        }
        return turno;
    }
    
    /**
     * Listar todos los turnos
     * @return Lista de todos los turnos
     */
    public List<Turno> listarTurnos() {
        List<Turno> turnos = turnoRepository.findAll();
        logger.info("Total de turnos listados: " + turnos.size());
        return turnos;
    }
    
    /**
     * Actualizar un turno existente
     * @param turno Turno con datos actualizados
     * @return Turno actualizado
     */
    public Turno actualizarTurno(Turno turno) {
        if (turno == null || turno.getId() == null || turno.getId() <= 0) {
            logger.error("Error al actualizar turno: ID inválido o null");
            throw new IllegalArgumentException("El turno debe tener un ID válido");
        }
        
        if (!turnoRepository.existsById(turno.getId())) {
            logger.error("Error al actualizar turno: no existe con ID " + turno.getId());
            throw new IllegalArgumentException("No existe turno con ID: " + turno.getId());
        }
        
        // Validaciones de campos requeridos
        if (turno.getPaciente() == null || turno.getPaciente().getId() == null) {
            logger.error("Error al actualizar turno: paciente es requerido");
            throw new IllegalArgumentException("El paciente es requerido");
        }
        
        if (turno.getOdontologo() == null || turno.getOdontologo().getId() == null) {
            logger.error("Error al actualizar turno: odontólogo es requerido");
            throw new IllegalArgumentException("El odontólogo es requerido");
        }
        
        if (turno.getFechaHora() == null) {
            logger.error("Error al actualizar turno: fecha y hora son requeridas");
            throw new IllegalArgumentException("La fecha y hora son requeridas");
        }
        
        Turno turnoActualizado = turnoRepository.save(turno);
        logger.info("Turno actualizado: " + turnoActualizado);
        return turnoActualizado;
    }
    
    /**
     * Eliminar un turno por ID
     * @param id ID del turno a eliminar
     */
    public void eliminarTurno(Integer id) {
        if (id == null || id <= 0) {
            logger.error("Error al eliminar turno: ID inválido " + id);
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }
        
        if (!turnoRepository.existsById(id)) {
            logger.error("Error al eliminar turno: no existe con ID " + id);
            throw new IllegalArgumentException("No existe turno con ID: " + id);
        }
        
        turnoRepository.deleteById(id);
        logger.info("Turno eliminado con ID: " + id);
    }
}
