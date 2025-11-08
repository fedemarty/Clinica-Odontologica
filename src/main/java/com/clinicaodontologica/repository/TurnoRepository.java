package com.clinicaodontologica.repository;

import com.clinicaodontologica.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository para Turno usando Spring Data JPA
 * Extiende JpaRepository para operaciones CRUD automáticas
 */
@Repository
public interface TurnoRepository extends JpaRepository<Turno, Integer> {
}
