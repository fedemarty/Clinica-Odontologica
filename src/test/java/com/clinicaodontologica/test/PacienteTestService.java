package com.clinicaodontologica.test;

import com.clinicaodontologica.entity.Paciente;
import com.clinicaodontologica.entity.Domicilio;
import com.clinicaodontologica.service.PacienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

/**
 * TESTS PARA PACIENTE - Spring Boot
 * Migración de tests originales a @SpringBootTest
 * Mantiene lógica DADO-CUANDO-ENTONCES original
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class PacienteTestService {
    
    @Autowired
    private PacienteService pacienteService;
    
    private void imprimirSeparador(String testName) {
        System.out.println("\n==============================================");
        System.out.println("COMIENZA TEST DE: " + testName);
        System.out.println("==============================================");
    }
    
    // TEST POSITIVO 
    @Test
    public void buscarPaciente(){
        imprimirSeparador("buscarPaciente - TEST POSITIVO");
        
        //DADO - Spring Boot auto-inicializa BD con DatosIniciales
        // No necesitamos BD.crearTablas() - se ejecuta automáticamente
        
        //CUANDO
        Paciente paciente = pacienteService.buscarPacientePorId(1);
        System.out.println("datos encontrados: " + paciente.toString());
        
        //ENTONCES
        Assertions.assertTrue(paciente != null);
    }
    
    @Test
    public void listarPacientes(){
        imprimirSeparador("listarPacientes - TEST POSITIVO");
        
        //DADO - DatosIniciales ya creó pacientes de prueba
        
        //CUANDO
        var pacientes = pacienteService.buscarPacientes();
        System.out.println("Cantidad de pacientes encontrados: " + pacientes.size());
        for (Paciente p : pacientes) {
            System.out.println("- " + p.toString());
        }
        
        //ENTONCES
        Assertions.assertTrue(pacientes.size() >= 2); // Mínimo los 2 de prueba
    }
    
    @Test
    public void guardarPaciente(){
        imprimirSeparador("guardarPaciente - TEST POSITIVO");
        
        //DADO
        Domicilio domicilio = new Domicilio("Calle Test", 123, "Localidad Test", "Provincia Test");
        Paciente pacienteNuevo = new Paciente(
            "Bart", "Simpson", "555-1234", 
            "bart@test.com", LocalDate.now(), domicilio
        );
        
        //CUANDO
        Paciente pacienteGuardado = pacienteService.guardarPaciente(pacienteNuevo);
        System.out.println("Paciente guardado: " + pacienteGuardado.toString());
        
        //ENTONCES
        Assertions.assertTrue(pacienteGuardado != null);
        Assertions.assertTrue(pacienteGuardado.getId() != null);
    }
    
    @Test
    public void buscarPacientePorEmail(){
        imprimirSeparador("buscarPacientePorEmail - TEST POSITIVO");
        
        //DADO - Ya tenemos homer@disney.com en DatosIniciales
        
        //CUANDO
        Paciente paciente = pacienteService.buscarPacientePorEmail("homer@disney.com");
        System.out.println("Paciente encontrado por email: " + paciente.toString());
        
        //ENTONCES
        Assertions.assertTrue(paciente != null);
        Assertions.assertEquals("homer@disney.com", paciente.getEmail());
    }
    
    // TESTS NEGATIVOS
    @Test
    public void buscarPacienteInexistente(){
        imprimirSeparador("buscarPacienteInexistente - TEST NEGATIVO");
        
        //DADO
        Integer idInexistente = 999;
        
        //CUANDO
        Paciente paciente = pacienteService.buscarPacientePorId(idInexistente);
        
        //ENTONCES
        Assertions.assertTrue(paciente == null);
    }
    
    @Test
    public void buscarPacienteIdInvalido(){
        imprimirSeparador("buscarPacienteIdInvalido - TEST NEGATIVO");
        
        //DADO
        Integer idInvalido = -1;
        
        //CUANDO
        Paciente paciente = pacienteService.buscarPacientePorId(idInvalido);
        
        //ENTONCES
        Assertions.assertTrue(paciente == null);
    }
    
    @Test
    public void guardarPacienteDuplicado(){
        imprimirSeparador("guardarPacienteDuplicado - TEST NEGATIVO");
        
        //DADO - Ya existe homer@disney.com
        Domicilio domicilio = new Domicilio("Otra Calle", 456, "Otra Localidad", "Otra Provincia");
        Paciente pacienteDuplicado = new Paciente(
            "Otro", "Nombre", "555-0000", 
            "homer@disney.com", LocalDate.now(), domicilio // Email duplicado
        );
        
        //CUANDO & ENTONCES - Debe lanzar excepción por email duplicado
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            pacienteService.guardarPaciente(pacienteDuplicado);
        });
    }
    
    @Test
    public void actualizarPaciente(){
        imprimirSeparador("actualizarPaciente - TEST POSITIVO");
        
        //DADO
        Paciente pacienteExistente = pacienteService.buscarPacientePorId(1);
        Assertions.assertTrue(pacienteExistente != null);
        
        String nuevoTelefono = "555-UPDATED";
        pacienteExistente.setNumeroContacto(nuevoTelefono);
        
        //CUANDO
        Paciente pacienteActualizado = pacienteService.actualizarPaciente(pacienteExistente);
        
        //ENTONCES
        Assertions.assertTrue(pacienteActualizado != null);
        Assertions.assertEquals(nuevoTelefono, pacienteActualizado.getNumeroContacto());
    }
    
    @Test
    public void eliminarPaciente(){
        imprimirSeparador("eliminarPaciente - TEST POSITIVO");
        
        //DADO - Crear un paciente para eliminar
        Domicilio domicilio = new Domicilio("Calle Eliminar", 999, "Test", "Test");
        Paciente pacienteParaEliminar = new Paciente(
            "Para", "Eliminar", "555-DELETE", 
            "delete@test.com", LocalDate.now(), domicilio
        );
        Paciente pacienteGuardado = pacienteService.guardarPaciente(pacienteParaEliminar);
        Assertions.assertTrue(pacienteGuardado != null);
        
        //CUANDO
        pacienteService.eliminarPaciente(pacienteGuardado.getId());
        
        //ENTONCES
        Paciente pacienteBuscado = pacienteService.buscarPacientePorId(pacienteGuardado.getId());
        Assertions.assertTrue(pacienteBuscado == null);
    }
}