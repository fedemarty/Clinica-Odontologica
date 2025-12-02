package com.clinicaodontologica.test;

import com.clinicaodontologica.entity.Odontologo;
import com.clinicaodontologica.entity.Paciente;
import com.clinicaodontologica.entity.Turno;
import com.clinicaodontologica.service.OdontologoService;
import com.clinicaodontologica.service.PacienteService;
import com.clinicaodontologica.service.TurnoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * TESTS UNITARIOS PARA TURNO - Spring Boot
 * Cobertura completa de operaciones CRUD con patrón DADO-CUANDO-ENTONCES
 * 
 * Tests Positivos: Operaciones exitosas con datos válidos
 * Tests Negativos: Validaciones de reglas de negocio y datos inválidos
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class TurnoTestService {
    
    @Autowired
    private TurnoService turnoService;
    
    @Autowired
    private PacienteService pacienteService;
    
    @Autowired
    private OdontologoService odontologoService;
    
    private void imprimirSeparador(String testName) {
        System.out.println("\n==============================================");
        System.out.println("COMIENZA TEST DE: " + testName);
        System.out.println("==============================================");
    }
    
    // ========================================
    // TESTS POSITIVOS - Operaciones exitosas
    // ========================================
    
    @Test
    public void buscarTurno(){
        imprimirSeparador("buscarTurno - TEST POSITIVO");
        
        //DADO - Spring Boot auto-inicializa BD con DatosIniciales (3 turnos)
        
        //CUANDO
        Optional<Turno> turno = turnoService.buscarTurnoPorId(1);
        System.out.println("Turno encontrado: " + turno.orElse(null));
        
        //ENTONCES
        Assertions.assertTrue(turno.isPresent());
        Assertions.assertEquals(1, turno.get().getId());
    }
    
    @Test
    public void listarTurnos(){
        imprimirSeparador("listarTurnos - TEST POSITIVO");
        
        //DADO - DatosIniciales ya creó 3 turnos de prueba
        
        //CUANDO
        List<Turno> turnos = turnoService.listarTurnos();
        System.out.println("Cantidad de turnos encontrados: " + turnos.size());
        for (Turno t : turnos) {
            System.out.println("- Turno ID: " + t.getId() + 
                             " | Paciente: " + t.getPaciente().getNombre() + 
                             " | Odontólogo: " + t.getOdontologo().getNombre() +
                             " | Fecha: " + t.getFechaHora());
        }
        
        //ENTONCES
        Assertions.assertTrue(turnos.size() >= 3); // Mínimo los 3 de prueba
    }
    
    @Test
    public void guardarTurno(){
        imprimirSeparador("guardarTurno - TEST POSITIVO");
        
        //DADO - Usar paciente y odontólogo existentes de DatosIniciales
        Paciente paciente = pacienteService.buscarPacientePorId(1);
        Odontologo odontologo = odontologoService.buscarOdontologoPorId(1);
        Assertions.assertTrue(paciente != null);
        Assertions.assertTrue(odontologo != null);
        
        LocalDateTime fechaHoraTurno = LocalDateTime.of(2025, 12, 1, 14, 30);
        Turno turnoNuevo = new Turno(paciente, odontologo, fechaHoraTurno);
        
        //CUANDO
        Turno turnoGuardado = turnoService.guardarTurno(turnoNuevo);
        System.out.println("Turno guardado: " + turnoGuardado.getId());
        
        //ENTONCES
        Assertions.assertTrue(turnoGuardado != null);
        Assertions.assertTrue(turnoGuardado.getId() != null);
        Assertions.assertEquals(paciente.getId(), turnoGuardado.getPaciente().getId());
        Assertions.assertEquals(odontologo.getId(), turnoGuardado.getOdontologo().getId());
    }
    
    @Test
    public void actualizarTurno(){
        imprimirSeparador("actualizarTurno - TEST POSITIVO");
        
        //DADO - Buscar turno existente
        Optional<Turno> turnoExistente = turnoService.buscarTurnoPorId(1);
        Assertions.assertTrue(turnoExistente.isPresent());
        
        Turno turno = turnoExistente.get();
        LocalDateTime nuevaFecha = LocalDateTime.of(2025, 12, 15, 16, 0);
        turno.setFechaHora(nuevaFecha);
        
        //CUANDO
        Turno turnoActualizado = turnoService.actualizarTurno(turno);
        System.out.println("Turno actualizado con nueva fecha: " + turnoActualizado.getFechaHora());
        
        //ENTONCES
        Assertions.assertTrue(turnoActualizado != null);
        Assertions.assertEquals(nuevaFecha, turnoActualizado.getFechaHora());
    }
    
    @Test
    public void eliminarTurno(){
        imprimirSeparador("eliminarTurno - TEST POSITIVO");
        
        //DADO - Crear un turno para eliminar
        Paciente paciente = pacienteService.buscarPacientePorId(2);
        Odontologo odontologo = odontologoService.buscarOdontologoPorId(2);
        
        Turno turnoParaEliminar = new Turno(paciente, odontologo, LocalDateTime.now());
        Turno turnoGuardado = turnoService.guardarTurno(turnoParaEliminar);
        Assertions.assertTrue(turnoGuardado != null);
        Integer idTurno = turnoGuardado.getId();
        
        //CUANDO
        turnoService.eliminarTurno(idTurno);
        System.out.println("Turno eliminado con ID: " + idTurno);
        
        //ENTONCES - El turno ya no debe existir
        Optional<Turno> turnoEliminado = turnoService.buscarTurnoPorId(idTurno);
        Assertions.assertTrue(turnoEliminado.isEmpty());
    }
    
    // ========================================
    // TESTS NEGATIVOS - Validaciones y errores
    // ========================================
    
    @Test
    public void buscarTurnoInexistente(){
        imprimirSeparador("buscarTurnoInexistente - TEST NEGATIVO");
        
        //DADO
        Integer idInexistente = 999;
        
        //CUANDO
        Optional<Turno> turno = turnoService.buscarTurnoPorId(idInexistente);
        
        //ENTONCES
        Assertions.assertTrue(turno.isEmpty()); // No debe existir
    }
    
    @Test
    public void buscarTurnoIdInvalido(){
        imprimirSeparador("buscarTurnoIdInvalido - TEST NEGATIVO");
        
        //DADO
        Integer idInvalido = -1;
        
        //CUANDO & ENTONCES
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            turnoService.buscarTurnoPorId(idInvalido);
        });
    }
    
    @Test
    public void guardarTurnoNull(){
        imprimirSeparador("guardarTurnoNull - TEST NEGATIVO");
        
        //DADO
        Turno turnoNull = null;
        
        //CUANDO & ENTONCES
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            turnoService.guardarTurno(turnoNull);
        });
    }
    
    @Test
    public void guardarTurnoSinPaciente(){
        imprimirSeparador("guardarTurnoSinPaciente - TEST NEGATIVO");
        
        //DADO
        Odontologo odontologo = odontologoService.buscarOdontologoPorId(1);
        Turno turnoSinPaciente = new Turno(null, odontologo, LocalDateTime.now());
        
        //CUANDO & ENTONCES
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            turnoService.guardarTurno(turnoSinPaciente);
        });
    }
    
    @Test
    public void guardarTurnoSinOdontologo(){
        imprimirSeparador("guardarTurnoSinOdontologo - TEST NEGATIVO");
        
        //DADO
        Paciente paciente = pacienteService.buscarPacientePorId(1);
        Turno turnoSinOdontologo = new Turno(paciente, null, LocalDateTime.now());
        
        //CUANDO & ENTONCES
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            turnoService.guardarTurno(turnoSinOdontologo);
        });
    }
    
    @Test
    public void guardarTurnoSinFechaHora(){
        imprimirSeparador("guardarTurnoSinFechaHora - TEST NEGATIVO");
        
        //DADO
        Paciente paciente = pacienteService.buscarPacientePorId(1);
        Odontologo odontologo = odontologoService.buscarOdontologoPorId(1);
        Turno turnoSinFecha = new Turno(paciente, odontologo, null);
        
        //CUANDO & ENTONCES
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            turnoService.guardarTurno(turnoSinFecha);
        });
    }
    
    @Test
    public void actualizarTurnoIdInvalido(){
        imprimirSeparador("actualizarTurnoIdInvalido - TEST NEGATIVO");
        
        //DADO
        Turno turnoSinId = new Turno();
        turnoSinId.setId(null);
        
        //CUANDO & ENTONCES
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            turnoService.actualizarTurno(turnoSinId);
        });
    }
    
    @Test
    public void actualizarTurnoInexistente(){
        imprimirSeparador("actualizarTurnoInexistente - TEST NEGATIVO");
        
        //DADO
        Paciente paciente = pacienteService.buscarPacientePorId(1);
        Odontologo odontologo = odontologoService.buscarOdontologoPorId(1);
        
        Turno turnoInexistente = new Turno(paciente, odontologo, LocalDateTime.now());
        turnoInexistente.setId(999); // ID que no existe
        
        //CUANDO & ENTONCES
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            turnoService.actualizarTurno(turnoInexistente);
        });
    }
    
    @Test
    public void eliminarTurnoIdInvalido(){
        imprimirSeparador("eliminarTurnoIdInvalido - TEST NEGATIVO");
        
        //DADO
        Integer idInvalido = -1;
        
        //CUANDO & ENTONCES
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            turnoService.eliminarTurno(idInvalido);
        });
    }
    
    @Test
    public void eliminarTurnoInexistente(){
        imprimirSeparador("eliminarTurnoInexistente - TEST NEGATIVO");
        
        //DADO
        Integer idInexistente = 888;
        
        //CUANDO & ENTONCES
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            turnoService.eliminarTurno(idInexistente);
        });
    }
}
