package com.clinicaodontologica.repository;

import com.clinicaodontologica.entity.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository para Domicilio - Spring Data JPA
 * Reemplaza DomicilioDAOH2 
 * 
 * Hereda automáticamente métodos CRUD:
 * - save() -> guardar()
 * - findById() -> buscar()
 * - deleteById() -> eliminar()
 * - findAll() -> buscarTodos()
 */
@Repository
public interface DomicilioRepository extends JpaRepository<Domicilio, Integer> {
    
    // JpaRepository ya proporciona todos los métodos que necesitábamos
    // No se requieren métodos personalizados para Domicilio
    
}