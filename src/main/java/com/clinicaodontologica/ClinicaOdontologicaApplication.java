package com.clinicaodontologica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot
 * Clínica Odontológica "Sonrisa Feliz"
 * 
 * Migración del sistema DAO a Spring Boot + JPA
 * Mantiene la arquitectura de 3 capas: Entity-Repository-Service
 */
@SpringBootApplication
public class ClinicaOdontologicaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinicaOdontologicaApplication.class, args);
        System.out.println("==============================================");
        System.out.println("🦷 Clínica Odontológica 'Sonrisa Feliz' INICIADA");
        System.out.println("==============================================");
        System.out.println("📋 Sistema de gestión con Spring Boot + JPA");
        System.out.println("🏥 Acceso H2 Console: http://localhost:8080/h2-console");
        System.out.println("🔧 JDBC URL: jdbc:h2:~/clinicaFeliz");
        System.out.println("==============================================");
    }
}