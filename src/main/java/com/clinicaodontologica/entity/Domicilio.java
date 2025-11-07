package com.clinicaodontologica.entity;

import jakarta.persistence.*;

/**
 * Entidad JPA que representa un domicilio en el sistema de clínica odontológica
 * Mantiene compatibilidad con la estructura de BD original
 */
@Entity
@Table(name = "DOMICILIOS")
public class Domicilio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    
    @Column(name = "CALLE", nullable = false, length = 255)
    private String calle;
    
    @Column(name = "NUMERO", nullable = false)
    private Integer numero;
    
    @Column(name = "LOCALIDAD", nullable = false, length = 255)
    private String localidad;
    
    @Column(name = "PROVINCIA", nullable = false, length = 255)
    private String provincia;

    // Constructor con ID para recuperación de BD
    public Domicilio(Integer id, String calle, Integer numero, String localidad, String provincia) {
        this.id = id;
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    // Constructor sin ID para nuevas inserciones  
    public Domicilio(String calle, Integer numero, String localidad, String provincia) {
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    // Constructor por defecto requerido por JPA
    public Domicilio() {
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    @Override
    public String toString() {
        return "Domicilio{" +
                "id=" + id +
                ", calle='" + calle + '\'' +
                ", numero=" + numero +
                ", localidad='" + localidad + '\'' +
                ", provincia='" + provincia + '\'' +
                '}';
    }
}