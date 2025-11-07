package com.clinicaodontologica.config;

import com.clinicaodontologica.entity.Domicilio;
import com.clinicaodontologica.entity.Odontologo;
import com.clinicaodontologica.entity.Paciente;
import com.clinicaodontologica.repository.DomicilioRepository;
import com.clinicaodontologica.repository.OdontologoRepository;
import com.clinicaodontologica.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Inicializador de datos de prueba - Spring Boot
 * Reemplaza BD.crearTablas() y datos de prueba
 * 
 * Se ejecuta automáticamente al iniciar la aplicación
 * Mantiene los mismos datos de prueba que el sistema original
 */
@Component
public class DatosIniciales implements ApplicationRunner {

    @Autowired
    private DomicilioRepository domicilioRepository;
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Autowired
    private OdontologoRepository odontologoRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Limpiar y crear datos de prueba (similar al patrón DROP/CREATE anterior)
        System.out.println("==============================================");
        System.out.println("🏥 INICIALIZANDO DATOS DE PRUEBA");
        System.out.println("==============================================");
        
        crearDatosIniciales();
        
        System.out.println("✅ Datos persistidos correctamente");
        System.out.println("📊 Pacientes: " + pacienteRepository.count());
        System.out.println("🦷 Odontólogos: " + odontologoRepository.count());
        System.out.println("🏠 Domicilios: " + domicilioRepository.count());
        System.out.println("==============================================");
    }
    
    private void crearDatosIniciales() {
        // Crear domicilios de prueba
        Domicilio domicilio1 = new Domicilio("siempre viva", 723, "Springfield", "USA");
        Domicilio domicilio2 = new Domicilio("Calle falsa", 123, "Springfield", "USA");
        
        domicilio1 = domicilioRepository.save(domicilio1);
        domicilio2 = domicilioRepository.save(domicilio2);
        
        // Crear pacientes de prueba (manteniendo datos originales)
        Paciente paciente1 = new Paciente();
        paciente1.setNombre("Homero");
        paciente1.setApellido("Simpson");
        paciente1.setNumeroContacto("1111111");
        paciente1.setEmail("homer@disney.com");
        paciente1.setFechaIngreso(LocalDate.of(2025, 10, 9));
        paciente1.setDomicilio(domicilio1);
        
        Paciente paciente2 = new Paciente();
        paciente2.setNombre("Marge");
        paciente2.setApellido("Simpson");
        paciente2.setNumeroContacto("11111");
        paciente2.setEmail("marge@fox.com");
        paciente2.setFechaIngreso(LocalDate.of(2025, 8, 8));
        paciente2.setDomicilio(domicilio2);
        
        pacienteRepository.save(paciente1);
        pacienteRepository.save(paciente2);
        
        // Crear odontólogos de prueba (manteniendo datos originales)
        Odontologo odontologo1 = new Odontologo("Dr. Juan", "Rodriguez", "MP-1001");
        Odontologo odontologo2 = new Odontologo("Dra. Maria", "Rodriguez", "MP-1002");
        
        odontologoRepository.save(odontologo1);
        odontologoRepository.save(odontologo2);
    }
}