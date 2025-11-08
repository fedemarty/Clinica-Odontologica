package com.clinicaodontologica.service;

import com.clinicaodontologica.entity.Paciente;
import com.clinicaodontologica.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service para lógica de negocio de Pacientes - Spring Boot
 * Migración de service.PacienteService a Spring Boot + JPA
 * 
 * Actúa como puente entre controladores y Repositories
 * Mantiene todas las validaciones y lógica de negocio original
 */
@Service
public class PacienteService {
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    public Paciente guardarPaciente(Paciente paciente){
        // Validación de duplicados: email único
        if (pacienteRepository.existsByEmail(paciente.getEmail())) {
            System.out.println("Error: Ya existe un paciente con email: " + paciente.getEmail());
            throw new IllegalArgumentException("Ya existe un paciente con el email: " + paciente.getEmail());
        }
        return pacienteRepository.save(paciente);
    }
    
    public Paciente buscarPacientePorId(Integer id){
        if (id == null || id <= 0) {
            System.out.println("Error: ID debe ser válido");
            return null;
        }
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        return paciente.orElse(null);
    }
    
    public List<Paciente> buscarPacientes(){
        return pacienteRepository.findAll();
    }
    
    /**
     * actualizarPaciente
     * Lógica de negocio para actualización de pacientes
     * Incluye validaciones básicas
     */
    public Paciente actualizarPaciente(Paciente paciente){
        // Validación de negocio: ID obligatorio para actualización
        if (paciente.getId() == null) {
            System.out.println("Error: ID del paciente no puede ser nulo");
            return null;
        }
        // Verificar que el paciente existe
        if (!pacienteRepository.existsById(paciente.getId())) {
            System.out.println("Error: No se encontró paciente con ID: " + paciente.getId());
            return null;
        }
        // Delegación al Repository
        return pacienteRepository.save(paciente);
    }
    
    /**
     * eliminarPaciente
     * Lógica de negocio para eliminación por ID
     * Mantiene datos consistentes según requerimiento
     */
    public void eliminarPaciente(Integer id){
        // Validación de negocio: ID válido
        if (id == null || id <= 0) {
            System.out.println("Error: ID debe ser válido");
            return;
        }
        if (pacienteRepository.existsById(id)) {
            pacienteRepository.deleteById(id);
            System.out.println("Paciente eliminado con ID: " + id);
        } else {
            System.out.println("No se encontró paciente con ID: " + id);
        }
    }
    
    /**
     * buscarPorString (implementado como buscarPorEmail)  
     * Búsqueda por email para confirmaciones y recordatorios
     * Funcionalidad clave para la clínica
     */
    public Paciente buscarPacientePorEmail(String email){
        // Validación de entrada
        if (email == null || email.trim().isEmpty()) {
            System.out.println("Error: Email no puede estar vacío");
            return null;
        }
        // Delegación al Repository
        Optional<Paciente> paciente = pacienteRepository.findByEmail(email);
        return paciente.orElse(null);
    }
}