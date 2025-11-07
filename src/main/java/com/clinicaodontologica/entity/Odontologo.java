package com.clinicaodontologica.entity;

import jakarta.persistence.*;

/**
 * Entidad JPA que representa un odontólogo en el sistema de clínica odontológica
 * Incluye restricción UNIQUE en matrícula para cumplimiento de auditoría
 */
@Entity
@Table(name = "ODONTOLOGOS")
public class Odontologo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    
    @Column(name = "NOMBRE", nullable = false, length = 255)
    private String nombre;
    
    @Column(name = "APELLIDO", nullable = false, length = 255)
    private String apellido;
    
    @Column(name = "MATRICULA", nullable = false, unique = true, length = 50)
    private String matricula;

    // Constructor con ID para recuperación de BD
    public Odontologo(Integer id, String nombre, String apellido, String matricula) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
    }

    // Constructor sin ID para nuevas inserciones
    public Odontologo(String nombre, String apellido, String matricula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
    }

    // Constructor por defecto requerido por JPA
    public Odontologo() {
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}