package com.clinicaodontologica.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entidad JPA que representa un paciente en el sistema de clínica odontológica
 * Incluye relación @OneToOne con Domicilio vía clave foránea DOMICILIO_ID
 */
@Entity
@Table(name = "PACIENTES")
public class Paciente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    
    @Column(name = "NOMBRE", nullable = false, length = 255)
    private String nombre;
    
    @Column(name = "APELLIDO", nullable = false, length = 255)
    private String apellido;
    
    @Column(name = "NUMERO_CONTACTO", length = 50)
    private String numeroContacto;
    
    @Column(name = "EMAIL", nullable = false, length = 255)
    private String email;
    
    @Column(name = "FECHA_INGRESO", nullable = false)
    private LocalDate fechaIngreso;
    
    @OneToOne
    @JoinColumn(name = "DOMICILIO_ID")
    private Domicilio domicilio;

    // Constructor con ID para recuperación de BD
    public Paciente(Integer id, String nombre, String apellido, String numeroContacto, 
                   String email, LocalDate fechaIngreso, Domicilio domicilio) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroContacto = numeroContacto;
        this.email = email;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
    }

    // Constructor sin ID para nuevas inserciones
    public Paciente(String nombre, String apellido, String numeroContacto, 
                   String email, LocalDate fechaIngreso, Domicilio domicilio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroContacto = numeroContacto;
        this.email = email;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
    }

    // Constructor por defecto requerido por JPA
    public Paciente() {
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

    public String getNumeroContacto() {
        return numeroContacto;
    }

    public void setNumeroContacto(String numeroContacto) {
        this.numeroContacto = numeroContacto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public String toString() {
        return "Paciente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", numeroContacto='" + numeroContacto + '\'' +
                ", email='" + email + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", domicilio=" + domicilio +
                '}';
    }
}