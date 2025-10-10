package model;

import java.util.Objects;

/**
 * CLASE NUEVA - REQUERIMIENTO SPRINT 1 EXAMEN
 * 
 * Entidad Odontologo según especificaciones del Arquitecto:
 * - id, nombre, apellido, matricula
 * - matricula: dato clave de auditoría
 * - Requisito para asignación de turnos (futuro)
 * 
 * TAREA ESTUDIANTES: Implementación completa de la entidad
 */
public class Odontologo {
    // Atributos según requerimientos del negocio
    private Integer id;           // ID auto-generado por H2
    private String nombre;        // Identidad profesional
    private String apellido;      // Identidad profesional  
    private String matricula;     // Dato clave de auditoría - UNIQUE en BD

    // Constructor vacío - Requerido por convenciones Java
    public Odontologo() {
    }

    // Constructor completo - Para objetos recuperados de BD
    public Odontologo(Integer id, String nombre, String apellido, String matricula) {
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