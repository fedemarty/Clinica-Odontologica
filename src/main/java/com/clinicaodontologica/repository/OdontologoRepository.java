package com.clinicaodontologica.repository;

import com.clinicaodontologica.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository para Odontologo - Spring Data JPA
 * Reemplaza OdontologoDAOH2
 * 
 * Hereda métodos CRUD automáticamente + métodos personalizados
 * Mantiene funcionalidad de búsqueda por matrícula (buscarGenerico)
 */
@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo, Integer> {
    
    /**
     * Buscar odontólogo por matrícula - Equivale a buscarGenerico(String matricula)
     * Dato clave de auditoría según requerimientos de negocio
     * 
     * Spring Data JPA genera automáticamente la implementación
     */
    Optional<Odontologo> findByMatricula(String matricula);
    
    /**
     * Verificar si existe odontólogo con matrícula específica
     * Útil para validaciones de duplicados
     */
    boolean existsByMatricula(String matricula);
    
}