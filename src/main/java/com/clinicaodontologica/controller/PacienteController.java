package com.clinicaodontologica.controller;

import com.clinicaodontologica.entity.Paciente;
import com.clinicaodontologica.service.PacienteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gestión de Pacientes
 * Expone endpoints HTTP para operaciones CRUD
 */
@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "*")
public class PacienteController {
    
    private static final Logger logger = Logger.getLogger(PacienteController.class);
    
    @Autowired
    private PacienteService pacienteService;
    
    /**
     * POST /api/pacientes - Crear nuevo paciente
     */
    @PostMapping
    public ResponseEntity<Paciente> crearPaciente(@RequestBody Paciente paciente) {
        try {
            Paciente pacienteGuardado = pacienteService.guardarPaciente(paciente);
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteGuardado);
        } catch (IllegalArgumentException e) {
            logger.error("Error al crear paciente: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error inesperado al crear paciente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/pacientes/{id} - Buscar paciente por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPaciente(@PathVariable Integer id) {
        try {
            Paciente paciente = pacienteService.buscarPacientePorId(id);
            if (paciente != null) {
                return ResponseEntity.ok(paciente);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            logger.error("Error al buscar paciente: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error inesperado al buscar paciente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/pacientes - Listar todos los pacientes
     */
    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        try {
            List<Paciente> pacientes = pacienteService.buscarPacientes();
            return ResponseEntity.ok(pacientes);
        } catch (Exception e) {
            logger.error("Error al listar pacientes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * PUT /api/pacientes/{id} - Actualizar paciente existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizarPaciente(@PathVariable Integer id, @RequestBody Paciente paciente) {
        try {
            paciente.setId(id);
            Paciente pacienteActualizado = pacienteService.actualizarPaciente(paciente);
            return ResponseEntity.ok(pacienteActualizado);
        } catch (IllegalArgumentException e) {
            logger.error("Error al actualizar paciente: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error inesperado al actualizar paciente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * DELETE /api/pacientes/{id} - Eliminar paciente
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Integer id) {
        try {
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.error("Error al eliminar paciente: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error inesperado al eliminar paciente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
