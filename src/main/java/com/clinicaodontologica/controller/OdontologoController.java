package com.clinicaodontologica.controller;

import com.clinicaodontologica.entity.Odontologo;
import com.clinicaodontologica.service.OdontologoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gestión de Odontólogos
 * Expone endpoints HTTP para operaciones CRUD
 */
@RestController
@RequestMapping("/api/odontologos")
@CrossOrigin(origins = "*")
public class OdontologoController {
    
    private static final Logger logger = Logger.getLogger(OdontologoController.class);
    
    @Autowired
    private OdontologoService odontologoService;
    
    /**
     * POST /api/odontologos - Crear nuevo odontólogo
     */
    @PostMapping
    public ResponseEntity<Odontologo> crearOdontologo(@RequestBody Odontologo odontologo) {
        try {
            Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);
            return ResponseEntity.status(HttpStatus.CREATED).body(odontologoGuardado);
        } catch (IllegalArgumentException e) {
            logger.error("Error al crear odontólogo: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error inesperado al crear odontólogo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/odontologos/{id} - Buscar odontólogo por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologo(@PathVariable Integer id) {
        try {
            Odontologo odontologo = odontologoService.buscarOdontologoPorId(id);
            if (odontologo != null) {
                return ResponseEntity.ok(odontologo);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            logger.error("Error al buscar odontólogo: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error inesperado al buscar odontólogo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/odontologos - Listar todos los odontólogos
     */
    @GetMapping
    public ResponseEntity<List<Odontologo>> listarOdontologos() {
        try {
            List<Odontologo> odontologos = odontologoService.buscarOdontologos();
            return ResponseEntity.ok(odontologos);
        } catch (Exception e) {
            logger.error("Error al listar odontólogos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * PUT /api/odontologos/{id} - Actualizar odontólogo existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Odontologo> actualizarOdontologo(@PathVariable Integer id, @RequestBody Odontologo odontologo) {
        try {
            odontologo.setId(id);
            Odontologo odontologoActualizado = odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.ok(odontologoActualizado);
        } catch (IllegalArgumentException e) {
            logger.error("Error al actualizar odontólogo: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error inesperado al actualizar odontólogo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * DELETE /api/odontologos/{id} - Eliminar odontólogo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOdontologo(@PathVariable Integer id) {
        try {
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.error("Error al eliminar odontólogo: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error inesperado al eliminar odontólogo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
