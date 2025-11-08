package com.clinicaodontologica.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad JPA que representa un turno en el sistema de clínica odontológica
 * Relaciona un Paciente con un Odontólogo en una fecha y hora específica
 */
@Entity
@Table(name = "TURNOS")
public class Turno {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "PACIENTE_ID", nullable = false)
    private Paciente paciente;
    
    @ManyToOne
    @JoinColumn(name = "ODONTOLOGO_ID", nullable = false)
    private Odontologo odontologo;
    
    @Column(name = "FECHA_HORA", nullable = false)
    private LocalDateTime fechaHora;

    // Constructor con ID para recuperación de BD
    public Turno(Integer id, Paciente paciente, Odontologo odontologo, LocalDateTime fechaHora) {
        this.id = id;
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fechaHora = fechaHora;
    }

    // Constructor sin ID para nuevas inserciones
    public Turno(Paciente paciente, Odontologo odontologo, LocalDateTime fechaHora) {
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fechaHora = fechaHora;
    }

    // Constructor por defecto requerido por JPA
    public Turno() {
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Odontologo getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(Odontologo odontologo) {
        this.odontologo = odontologo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return "Turno{" +
                "id=" + id +
                ", paciente=" + (paciente != null ? paciente.getNombre() + " " + paciente.getApellido() : "null") +
                ", odontologo=" + (odontologo != null ? odontologo.getNombre() + " " + odontologo.getApellido() : "null") +
                ", fechaHora=" + fechaHora +
                '}';
    }
}
