package com.clinicaodontologica.service;

import com.clinicaodontologica.entity.Odontologo;
import com.clinicaodontologica.repository.OdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service para lógica de negocio de Odontólogos - Spring Boot
 * Migración de service.OdontologoService a Spring Boot + JPA
 * 
 * Mantiene todas las validaciones y lógica de negocio original
 * Reemplaza DAO por Repository de Spring Data JPA
 */
@Service
public class OdontologoService {
    
    @Autowired
    private OdontologoRepository odontologoRepository;

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
        // Validación de duplicados: matrícula única
        if (odontologoRepository.existsByMatricula(odontologo.getMatricula())) {
            System.out.println("Error: Ya existe un odontologo con matricula: " + odontologo.getMatricula());
            return null;
        }
        // Delegación al Repository
        return odontologoRepository.save(odontologo);
    }

    public Odontologo buscarOdontologoPorId(Integer id) {
        if (id == null || id <= 0) {
            System.out.println("Error: ID debe ser válido");
            return null;
        }
        Optional<Odontologo> odontologo = odontologoRepository.findById(id);
        return odontologo.orElse(null);
    }

    public List<Odontologo> buscarOdontologos() {
        return odontologoRepository.findAll();
    }

    public Odontologo actualizarOdontologo(Odontologo odontologo) {
        if (odontologo.getId() == null) {
            System.out.println("Error: ID del odontologo no puede ser nulo");
            return null;
        }
        if (odontologo.getNombre() == null || odontologo.getNombre().trim().isEmpty()) {
            System.out.println("Error: Nombre del odontologo es obligatorio");
            return null;
        }
        if (odontologo.getMatricula() == null || odontologo.getMatricula().trim().isEmpty()) {
            System.out.println("Error: Matricula del odontologo es obligatoria");
            return null;
        }
        return odontologoRepository.save(odontologo);
    }

    public void eliminarOdontologo(Integer id) {
        if (id == null || id <= 0) {
            System.out.println("Error: ID debe ser válido");
            return;
        }
        if (odontologoRepository.existsById(id)) {
            odontologoRepository.deleteById(id);
            System.out.println("Odontologo eliminado con ID: " + id);
        } else {
            System.out.println("No se encontró odontologo con ID: " + id);
        }
    }

    public Odontologo buscarOdontologoPorMatricula(String matricula) {
        if (matricula == null || matricula.trim().isEmpty()) {
            System.out.println("Error: Matricula no puede estar vacía");
            return null;
        }
        Optional<Odontologo> odontologo = odontologoRepository.findByMatricula(matricula);
        return odontologo.orElse(null);
    }
}