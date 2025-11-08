package com.clinicaodontologica.controller;

import com.clinicaodontologica.entity.Turno;
import com.clinicaodontologica.service.TurnoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gestión de Turnos
 * Expone endpoints HTTP para operaciones CRUD
 */
@RestController
@RequestMapping("/api/turnos")
@CrossOrigin(origins = "*")
public class TurnoController {
    
    private static final Logger logger = Logger.getLogger(TurnoController.class);
    
    @Autowired
    private TurnoService turnoService;
    
    /**
     * POST /api/turnos - Crear nuevo turno
     */
    @PostMapping
    public ResponseEntity<Turno> crearTurno(@RequestBody Turno turno) {
        try {
            Turno turnoGuardado = turnoService.guardarTurno(turno);
            return ResponseEntity.status(HttpStatus.CREATED).body(turnoGuardado);
        } catch (IllegalArgumentException e) {
            logger.error("Error al crear turno: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error inesperado al crear turno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/turnos/{id} - Buscar turno por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarTurno(@PathVariable Integer id) {
        try {
            Optional<Turno> turno = turnoService.buscarTurnoPorId(id);
            return turno.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            logger.error("Error al buscar turno: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error inesperado al buscar turno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/turnos - Listar todos los turnos
     */
    @GetMapping
    public ResponseEntity<List<Turno>> listarTurnos() {
        try {
            List<Turno> turnos = turnoService.listarTurnos();
            return ResponseEntity.ok(turnos);
        } catch (Exception e) {
            logger.error("Error al listar turnos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * PUT /api/turnos/{id} - Actualizar turno existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Turno> actualizarTurno(@PathVariable Integer id, @RequestBody Turno turno) {
        try {
            turno.setId(id);
            Turno turnoActualizado = turnoService.actualizarTurno(turno);
            return ResponseEntity.ok(turnoActualizado);
        } catch (IllegalArgumentException e) {
            logger.error("Error al actualizar turno: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error inesperado al actualizar turno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * DELETE /api/turnos/{id} - Eliminar turno
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTurno(@PathVariable Integer id) {
        try {
            turnoService.eliminarTurno(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.error("Error al eliminar turno: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error inesperado al eliminar turno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
