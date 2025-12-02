package com.clinicaodontologica.test;

import com.clinicaodontologica.entity.Odontologo;
import com.clinicaodontologica.entity.Paciente;
import com.clinicaodontologica.entity.Turno;
import com.clinicaodontologica.service.OdontologoService;
import com.clinicaodontologica.service.PacienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

/**
 * TESTS DE INTEGRACIÓN PARA TURNO - Spring Boot
 * 
 * Prueba los endpoints REST completos usando MockMvc
 * Verifica la integración entre Controller → Service → Repository
 * Simula peticiones HTTP reales sin levantar servidor completo
 * 
 * Cobertura:
 * - POST /api/turnos - Crear turno
 * - GET /api/turnos/{id} - Buscar turno por ID
 * - GET /api/turnos - Listar todos los turnos
 * - PUT /api/turnos/{id} - Actualizar turno
 * - DELETE /api/turnos/{id} - Eliminar turno
 * - Validaciones de negocio y errores HTTP
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class TurnoIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private PacienteService pacienteService;
    
    @Autowired
    private OdontologoService odontologoService;
    
    private ObjectMapper objectMapper;
    
    @BeforeEach
    public void setUp() {
        // Configurar ObjectMapper para manejar LocalDateTime
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }
    
    private void imprimirSeparador(String testName) {
        System.out.println("\n==============================================");
        System.out.println("TEST INTEGRACIÓN: " + testName);
        System.out.println("==============================================");
    }
    
    // ========================================
    // POST /api/turnos - Crear turno
    // ========================================
    
    @Test
    public void crearTurno_DeberiaRetornar201_CuandoDatosValidos() throws Exception {
        imprimirSeparador("POST /api/turnos - Crear turno con datos válidos");
        
        //DADO
        Paciente paciente = pacienteService.buscarPacientePorId(1);
        Odontologo odontologo = odontologoService.buscarOdontologoPorId(1);
        
        Turno turnoNuevo = new Turno();
        turnoNuevo.setPaciente(paciente);
        turnoNuevo.setOdontologo(odontologo);
        turnoNuevo.setFechaHora(LocalDateTime.of(2025, 12, 20, 10, 0));
        
        String turnoJson = objectMapper.writeValueAsString(turnoNuevo);
        
        //CUANDO & ENTONCES
        mockMvc.perform(post("/api/turnos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(turnoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.paciente.id").value(paciente.getId()))
                .andExpect(jsonPath("$.odontologo.id").value(odontologo.getId()));
        
        System.out.println("✓ Turno creado exitosamente con status 201");
    }
    
    @Test
    public void crearTurno_DeberiaRetornar400_CuandoSinPaciente() throws Exception {
        imprimirSeparador("POST /api/turnos - Error sin paciente");
        
        //DADO
        Odontologo odontologo = odontologoService.buscarOdontologoPorId(1);
        
        Turno turnoInvalido = new Turno();
        turnoInvalido.setOdontologo(odontologo);
        turnoInvalido.setFechaHora(LocalDateTime.now());
        // Sin paciente
        
        String turnoJson = objectMapper.writeValueAsString(turnoInvalido);
        
        //CUANDO & ENTONCES
        mockMvc.perform(post("/api/turnos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(turnoJson))
                .andExpect(status().isBadRequest());
        
        System.out.println("✓ Validación correcta: Bad Request 400 sin paciente");
    }
    
    @Test
    public void crearTurno_DeberiaRetornar400_CuandoSinOdontologo() throws Exception {
        imprimirSeparador("POST /api/turnos - Error sin odontólogo");
        
        //DADO
        Paciente paciente = pacienteService.buscarPacientePorId(1);
        
        Turno turnoInvalido = new Turno();
        turnoInvalido.setPaciente(paciente);
        turnoInvalido.setFechaHora(LocalDateTime.now());
        // Sin odontólogo
        
        String turnoJson = objectMapper.writeValueAsString(turnoInvalido);
        
        //CUANDO & ENTONCES
        mockMvc.perform(post("/api/turnos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(turnoJson))
                .andExpect(status().isBadRequest());
        
        System.out.println("✓ Validación correcta: Bad Request 400 sin odontólogo");
    }
    
    // ========================================
    // GET /api/turnos/{id} - Buscar turno
    // ========================================
    
    @Test
    public void buscarTurnoPorId_DeberiaRetornar200_CuandoExiste() throws Exception {
        imprimirSeparador("GET /api/turnos/{id} - Buscar turno existente");
        
        //DADO - Turno con ID 1 existe en DatosIniciales
        
        //CUANDO & ENTONCES
        mockMvc.perform(get("/api/turnos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.paciente").exists())
                .andExpect(jsonPath("$.odontologo").exists())
                .andExpect(jsonPath("$.fechaHora").exists());
        
        System.out.println("✓ Turno encontrado exitosamente con status 200");
    }
    
    @Test
    public void buscarTurnoPorId_DeberiaRetornar400_CuandoIdInvalido() throws Exception {
        imprimirSeparador("GET /api/turnos/{id} - Error con ID inválido");
        
        //DADO
        Integer idInvalido = -1;
        
        //CUANDO & ENTONCES
        mockMvc.perform(get("/api/turnos/" + idInvalido))
                .andExpect(status().isBadRequest());
        
        System.out.println("✓ Validación correcta: Bad Request 400 con ID inválido");
    }
    
    @Test
    public void buscarTurnoPorId_DeberiaRetornar400_CuandoNoExiste() throws Exception {
        imprimirSeparador("GET /api/turnos/{id} - Turno no existe");
        
        //DADO
        Integer idInexistente = 999;
        
        //CUANDO & ENTONCES
        mockMvc.perform(get("/api/turnos/" + idInexistente))
                .andExpect(status().isNotFound());
        
        System.out.println("✓ Turno no encontrado retorna Not Found 404");
    }
    
    // ========================================
    // GET /api/turnos - Listar turnos
    // ========================================
    
    @Test
    public void listarTurnos_DeberiaRetornar200_ConListaTurnos() throws Exception {
        imprimirSeparador("GET /api/turnos - Listar todos los turnos");
        
        //DADO - DatosIniciales crea 3 turnos
        
        //CUANDO & ENTONCES
        mockMvc.perform(get("/api/turnos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))));
        
        System.out.println("✓ Lista de turnos obtenida exitosamente");
    }
    
    // ========================================
    // PUT /api/turnos/{id} - Actualizar turno
    // ========================================
    
    @Test
    public void actualizarTurno_DeberiaRetornar200_CuandoDatosValidos() throws Exception {
        imprimirSeparador("PUT /api/turnos/{id} - Actualizar turno existente");
        
        //DADO - Turno existente con ID 1
        Paciente paciente = pacienteService.buscarPacientePorId(1);
        Odontologo odontologo = odontologoService.buscarOdontologoPorId(2); // Cambiar odontólogo
        
        Turno turnoActualizado = new Turno();
        turnoActualizado.setId(1);
        turnoActualizado.setPaciente(paciente);
        turnoActualizado.setOdontologo(odontologo);
        turnoActualizado.setFechaHora(LocalDateTime.of(2025, 12, 25, 15, 30));
        
        String turnoJson = objectMapper.writeValueAsString(turnoActualizado);
        
        //CUANDO & ENTONCES
        mockMvc.perform(put("/api/turnos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(turnoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.odontologo.id").value(odontologo.getId()));
        
        System.out.println("✓ Turno actualizado exitosamente");
    }
    
    @Test
    public void actualizarTurno_DeberiaRetornar400_CuandoIdInvalido() throws Exception {
        imprimirSeparador("PUT /api/turnos/{id} - Error con ID inválido");
        
        //DADO
        Turno turnoInvalido = new Turno();
        turnoInvalido.setId(-1);
        
        String turnoJson = objectMapper.writeValueAsString(turnoInvalido);
        
        //CUANDO & ENTONCES
        mockMvc.perform(put("/api/turnos/-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(turnoJson))
                .andExpect(status().isBadRequest());
        
        System.out.println("✓ Validación correcta: Bad Request 400 con ID inválido");
    }
    
    @Test
    public void actualizarTurno_DeberiaRetornar400_CuandoNoExiste() throws Exception {
        imprimirSeparador("PUT /api/turnos/{id} - Error turno no existe");
        
        //DADO
        Paciente paciente = pacienteService.buscarPacientePorId(1);
        Odontologo odontologo = odontologoService.buscarOdontologoPorId(1);
        
        Turno turnoInexistente = new Turno();
        turnoInexistente.setId(999);
        turnoInexistente.setPaciente(paciente);
        turnoInexistente.setOdontologo(odontologo);
        turnoInexistente.setFechaHora(LocalDateTime.now());
        
        String turnoJson = objectMapper.writeValueAsString(turnoInexistente);
        
        //CUANDO & ENTONCES
        mockMvc.perform(put("/api/turnos/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(turnoJson))
                .andExpect(status().isBadRequest());
        
        System.out.println("✓ Validación correcta: Bad Request 400 turno inexistente");
    }
    
    // ========================================
    // DELETE /api/turnos/{id} - Eliminar turno
    // ========================================
    
    @Test
    public void eliminarTurno_DeberiaRetornar204_CuandoExiste() throws Exception {
        imprimirSeparador("DELETE /api/turnos/{id} - Eliminar turno existente");
        
        //DADO - Crear un turno para eliminar
        Paciente paciente = pacienteService.buscarPacientePorId(2);
        Odontologo odontologo = odontologoService.buscarOdontologoPorId(2);
        
        Turno turnoNuevo = new Turno();
        turnoNuevo.setPaciente(paciente);
        turnoNuevo.setOdontologo(odontologo);
        turnoNuevo.setFechaHora(LocalDateTime.of(2026, 1, 10, 11, 0));
        
        String turnoJson = objectMapper.writeValueAsString(turnoNuevo);
        
        // Crear turno primero
        String response = mockMvc.perform(post("/api/turnos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(turnoJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        Turno turnoCreado = objectMapper.readValue(response, Turno.class);
        Integer idTurno = turnoCreado.getId();
        
        //CUANDO & ENTONCES
        mockMvc.perform(delete("/api/turnos/" + idTurno))
                .andExpect(status().isNoContent());
        
        // Verificar que ya no existe
        mockMvc.perform(get("/api/turnos/" + idTurno))
                .andExpect(status().isNotFound());
        
        System.out.println("✓ Turno eliminado exitosamente con status 204");
    }
    
    @Test
    public void eliminarTurno_DeberiaRetornar400_CuandoIdInvalido() throws Exception {
        imprimirSeparador("DELETE /api/turnos/{id} - Error con ID inválido");
        
        //DADO
        Integer idInvalido = -1;
        
        //CUANDO & ENTONCES
        mockMvc.perform(delete("/api/turnos/" + idInvalido))
                .andExpect(status().isBadRequest());
        
        System.out.println("✓ Validación correcta: Bad Request 400 con ID inválido");
    }
    
    @Test
    public void eliminarTurno_DeberiaRetornar400_CuandoNoExiste() throws Exception {
        imprimirSeparador("DELETE /api/turnos/{id} - Error turno no existe");
        
        //DADO
        Integer idInexistente = 888;
        
        //CUANDO & ENTONCES
        mockMvc.perform(delete("/api/turnos/" + idInexistente))
                .andExpect(status().isBadRequest());
        
        System.out.println("✓ Validación correcta: Bad Request 400 turno inexistente");
    }
    
    // ========================================
    // TESTS ADICIONALES DE VALIDACIÓN
    // ========================================
    
    @Test
    public void crearTurno_DeberiaRetornar400_CuandoSinFechaHora() throws Exception {
        imprimirSeparador("POST /api/turnos - Error sin fecha y hora");
        
        //DADO
        Paciente paciente = pacienteService.buscarPacientePorId(1);
        Odontologo odontologo = odontologoService.buscarOdontologoPorId(1);
        
        Turno turnoSinFecha = new Turno();
        turnoSinFecha.setPaciente(paciente);
        turnoSinFecha.setOdontologo(odontologo);
        // Sin fechaHora
        
        String turnoJson = objectMapper.writeValueAsString(turnoSinFecha);
        
        //CUANDO & ENTONCES
        mockMvc.perform(post("/api/turnos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(turnoJson))
                .andExpect(status().isBadRequest());
        
        System.out.println("✓ Validación correcta: Bad Request 400 sin fecha/hora");
    }
    
    @Test
    public void integrationTest_FlujoCRUDCompleto() throws Exception {
        imprimirSeparador("FLUJO COMPLETO CRUD - Create → Read → Update → Delete");
        
        //DADO
        Paciente paciente = pacienteService.buscarPacientePorId(1);
        Odontologo odontologo = odontologoService.buscarOdontologoPorId(1);
        
        // 1. CREATE
        System.out.println("1. Creando turno...");
        Turno turnoNuevo = new Turno();
        turnoNuevo.setPaciente(paciente);
        turnoNuevo.setOdontologo(odontologo);
        turnoNuevo.setFechaHora(LocalDateTime.of(2025, 12, 30, 9, 0));
        
        String createResponse = mockMvc.perform(post("/api/turnos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(turnoNuevo)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        
        Turno turnoCreado = objectMapper.readValue(createResponse, Turno.class);
        Integer idTurno = turnoCreado.getId();
        System.out.println("   ✓ Turno creado con ID: " + idTurno);
        
        // 2. READ
        System.out.println("2. Leyendo turno...");
        mockMvc.perform(get("/api/turnos/" + idTurno))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idTurno));
        System.out.println("   ✓ Turno leído exitosamente");
        
        // 3. UPDATE
        System.out.println("3. Actualizando turno...");
        turnoCreado.setFechaHora(LocalDateTime.of(2026, 1, 5, 14, 0));
        
        mockMvc.perform(put("/api/turnos/" + idTurno)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(turnoCreado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idTurno));
        System.out.println("   ✓ Turno actualizado exitosamente");
        
        // 4. DELETE
        System.out.println("4. Eliminando turno...");
        mockMvc.perform(delete("/api/turnos/" + idTurno))
                .andExpect(status().isNoContent());
        System.out.println("   ✓ Turno eliminado exitosamente");
        
        // 5. VERIFY DELETE
        System.out.println("5. Verificando eliminación...");
        mockMvc.perform(get("/api/turnos/" + idTurno))
                .andExpect(status().isNotFound());
        System.out.println("   ✓ Turno no existe - flujo CRUD completo exitoso");
    }
}
