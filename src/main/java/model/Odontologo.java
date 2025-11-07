package com.clinicaodontologica.entity;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Entidad Odontologo - JPA Entity
 * Migración de model.Odontologo a Spring Boot + JPA
 * 
 * - id, nombre, apellido, matricula
 * - matricula: dato clave de auditoría (UNIQUE)
 * - Requisito para asignación de turnos (futuro)
 * 
 * Mantiene constructores duales según patrón establecido
 */
@Entity
@Table(name = "odontologos")
public class Odontologo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;           // ID auto-generado por JPA
    
    @Column(nullable = false, length = 100)
    private String nombre;        // Identidad profesional
    
    @Column(nullable = false, length = 100)
    private String apellido;      // Identidad profesional  
    
    @Column(nullable = false, unique = true, length = 50)
    private String matricula;     // Dato clave de auditoría - UNIQUE en BD

    // Constructor vacío - Requerido por JPA
    public Odontologo() {
    }

    // Constructor completo - Para objetos recuperados de BD
    public Odontologo(Long id, String nombre, String apellido, String matricula) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
    }

    // Constructor sin ID - Para inserción de nuevos odontólogos
    public Odontologo(String nombre, String apellido, String matricula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        return "Odontologo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", matricula='" + matricula + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Odontologo that = (Odontologo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}