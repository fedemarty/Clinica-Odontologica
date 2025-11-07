package com.clinicaodontologica.test;

import com.clinicaodontologica.entity.Odontologo;
import com.clinicaodontologica.service.OdontologoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * TESTS PARA ODONTOLOGO - Spring Boot
 * Migración de tests originales a @SpringBootTest
 * Mantiene lógica DADO-CUANDO-ENTONCES original
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class OdontologoTestService {
    
    @Autowired
    private OdontologoService odontologoService;
    
    private void imprimirSeparador(String testName) {
        System.out.println("\n==============================================");
        System.out.println("COMIENZA TEST DE: " + testName);
        System.out.println("==============================================");
    }
    
    // TEST POSITIVO
    @Test
    public void buscarOdontologo(){
        imprimirSeparador("buscarOdontologo - TEST POSITIVO");
        
        //DADO - Spring Boot auto-inicializa BD con DatosIniciales
        
        //CUANDO
        Odontologo odontologo = odontologoService.buscarOdontologoPorId(1);
        System.out.println("datos encontrados: " + odontologo.toString());
        
        //ENTONCES
        Assertions.assertTrue(odontologo != null);
    }
    
    @Test
    public void listarOdontologos(){
        imprimirSeparador("listarOdontologos - TEST POSITIVO");
        
        //DADO - DatosIniciales ya creó odontólogos de prueba
        
        //CUANDO
        var odontologos = odontologoService.buscarOdontologos();
        System.out.println("Cantidad de odontólogos encontrados: " + odontologos.size());
        for (Odontologo o : odontologos) {
            System.out.println("- " + o.toString());
        }
        
        //ENTONCES
        Assertions.assertTrue(odontologos.size() >= 2); // Mínimo los 2 de prueba
    }
    
    @Test
    public void guardarOdontologo(){
        imprimirSeparador("guardarOdontologo - TEST POSITIVO");
        
        //DADO
        Odontologo odontologoNuevo = new Odontologo("Lisa", "Simpson", "MAT-TEST-001");
        
        //CUANDO
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologoNuevo);
        System.out.println("Odontólogo guardado: " + odontologoGuardado.toString());
        
        //ENTONCES
        Assertions.assertTrue(odontologoGuardado != null);
        Assertions.assertTrue(odontologoGuardado.getId() != null);
    }
    
    @Test
    public void buscarOdontologoPorMatricula(){
        imprimirSeparador("buscarOdontologoPorMatricula - TEST POSITIVO");
        
        //DADO - Ya tenemos MP-1001 en DatosIniciales
        
        //CUANDO
        Odontologo odontologo = odontologoService.buscarOdontologoPorMatricula("MP-1001");
        System.out.println("Odontólogo encontrado por matrícula: " + odontologo.toString());
        
        //ENTONCES
        Assertions.assertTrue(odontologo != null);
        Assertions.assertEquals("MP-1001", odontologo.getMatricula());
    }
    
    // TESTS NEGATIVOS
    @Test
    public void buscarOdontologoInexistente(){
        imprimirSeparador("buscarOdontologoInexistente - TEST NEGATIVO");
        
        //DADO
        Integer idInexistente = 999;
        
        //CUANDO
        Odontologo odontologo = odontologoService.buscarOdontologoPorId(idInexistente);
        
        //ENTONCES
        Assertions.assertTrue(odontologo == null);
    }
    
    @Test
    public void buscarOdontologoIdInvalido(){
        imprimirSeparador("buscarOdontologoIdInvalido - TEST NEGATIVO");
        
        //DADO
        Integer idInvalido = -1;
        
        //CUANDO
        Odontologo odontologo = odontologoService.buscarOdontologoPorId(idInvalido);
        
        //ENTONCES
        Assertions.assertTrue(odontologo == null);
    }
    
    @Test
    public void guardarOdontologoSinNombre(){
        imprimirSeparador("guardarOdontologoSinNombre - TEST NEGATIVO");
        
        //DADO
        Odontologo odontologoInvalido = new Odontologo(null, "Apellido", "MAT-INVALID");
        
        //CUANDO
        Odontologo resultado = odontologoService.guardarOdontologo(odontologoInvalido);
        
        //ENTONCES
        Assertions.assertTrue(resultado == null); // No debe guardar sin nombre
    }
    
    @Test
    public void guardarOdontologoSinMatricula(){
        imprimirSeparador("guardarOdontologoSinMatricula - TEST NEGATIVO");
        
        //DADO
        Odontologo odontologoInvalido = new Odontologo("Nombre", "Apellido", null);
        
        //CUANDO
        Odontologo resultado = odontologoService.guardarOdontologo(odontologoInvalido);
        
        //ENTONCES
        Assertions.assertTrue(resultado == null); // No debe guardar sin matrícula
    }
    
    @Test
    public void guardarOdontologoMatriculaDuplicada(){
        imprimirSeparador("guardarOdontologoMatriculaDuplicada - TEST NEGATIVO");
        
        //DADO - Ya existe MP-1001
        Odontologo odontologoDuplicado = new Odontologo("Otro", "Doctor", "MP-1001");
        
        //CUANDO
        Odontologo resultado = odontologoService.guardarOdontologo(odontologoDuplicado);
        
        //ENTONCES
        Assertions.assertTrue(resultado == null); // No debe guardar matrícula duplicada
    }
    
    @Test
    public void actualizarOdontologo(){
        imprimirSeparador("actualizarOdontologo - TEST POSITIVO");
        
        //DADO
        Odontologo odontologoExistente = odontologoService.buscarOdontologoPorId(1);
        Assertions.assertTrue(odontologoExistente != null);
        
        String nuevoApellido = "UPDATED";
        odontologoExistente.setApellido(nuevoApellido);
        
        //CUANDO
        Odontologo odontologoActualizado = odontologoService.actualizarOdontologo(odontologoExistente);
        
        //ENTONCES
        Assertions.assertTrue(odontologoActualizado != null);
        Assertions.assertEquals(nuevoApellido, odontologoActualizado.getApellido());
    }
    
    @Test
    public void eliminarOdontologo(){
        imprimirSeparador("eliminarOdontologo - TEST POSITIVO");
        
        //DADO - Crear un odontólogo para eliminar
        Odontologo odontologoParaEliminar = new Odontologo("Para", "Eliminar", "MAT-DELETE");
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologoParaEliminar);
        Assertions.assertTrue(odontologoGuardado != null);
        
        //CUANDO
        odontologoService.eliminarOdontologo(odontologoGuardado.getId());
        
        //ENTONCES
        Odontologo odontologoBuscado = odontologoService.buscarOdontologoPorId(odontologoGuardado.getId());
        Assertions.assertTrue(odontologoBuscado == null);
    }
}