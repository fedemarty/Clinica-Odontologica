package com.clinicaodontologica.repository;

import com.clinicaodontologica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository para Paciente - Spring Data JPA
 * Reemplaza PacienteDAOH2
 * 
 * Hereda métodos CRUD automáticamente + métodos personalizados
 * Mantiene funcionalidad de búsqueda por email (buscarGenerico)
 */
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    
    /**
     * Buscar paciente por email - Equivale a buscarGenerico(String email)
     * Para confirmaciones y recordatorios según requerimientos
     * 
     * Spring Data JPA genera automáticamente la implementación
     */
    Optional<Paciente> findByEmail(String email);
    
    /**
     * Verificar si existe paciente con email específico
     * Útil para validaciones de duplicados en registros
     */
    boolean existsByEmail(String email);
    
}