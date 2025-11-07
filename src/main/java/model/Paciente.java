package com.clinicaodontologica.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entidad Paciente - JPA Entity
 * Migración de model.Paciente a Spring Boot + JPA
 * 
 * Mantiene relación con Domicilio (@OneToOne con CascadeType.ALL)
 * Constructores duales según patrón establecido
 */
@Entity
@Table(name = "pacientes")
public class Paciente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(nullable = false, length = 100)
    private String apellido;
    
    @Column(nullable = false)
    private Integer numeroContacto;
    
    @Column(nullable = false)
    private LocalDate fechaIngreso;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "domicilio_id", referencedColumnName = "id")
    private Domicilio domicilio;
    
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    // Constructor vacío - Requerido por JPA
    public Paciente() {
    }

    // Constructor completo - Para recuperación de BD
    public Paciente(Long id, String nombre, String apellido, Integer numeroContacto, LocalDate fechaIngreso, Domicilio domicilio, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroContacto = numeroContacto;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
        this.email = email;
        this.id = id;
    }

    // Constructor sin ID - Para nuevas inserciones
    public Paciente(String nombre, String apellido, Integer numeroContacto, LocalDate fechaIngreso, Domicilio domicilio, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroContacto = numeroContacto;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
        this.email = email;
    }

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

    public Integer getNumeroContacto() {
        return numeroContacto;
    }

    public void setNumeroContacto(Integer numeroContacto) {
        this.numeroContacto = numeroContacto;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", id=" + id +
                '}';
    }
}
