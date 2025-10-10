import dao.BD;
import dao.PacienteDAOH2;
import model.Paciente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.PacienteService;

/**
 * TESTS PARA PACIENTE 
 * Incluye tests positivos y negativos 
 */
public class PacienteTestService {
    
    private void imprimirSeparador(String testName) {
        System.out.println("\n==============================================");
        System.out.println("COMIENZA TEST DE: " + testName);
        System.out.println("==============================================");
    }
    
    // TEST POSITIVO 
    @Test
    public void buscarPaciente(){
        imprimirSeparador("buscarPaciente - TEST POSITIVO");
        
        //DADO
        BD.crearTablas();
        PacienteService pacienteService= new PacienteService(new PacienteDAOH2());
        //CUANDO
        Paciente paciente= pacienteService.buscarPacientePorId(1);
        System.out.println("datos encontrados: "+paciente.toString());
        //ENTONCES
        Assertions.assertTrue(paciente!=null);
    }
    
    @Test
    public void listarPacientes(){
        imprimirSeparador("listarPacientes - TEST POSITIVO");
        
        //DADO
        BD.crearTablas();
        PacienteService pacienteService= new PacienteService(new PacienteDAOH2());
        //CUANDO
        var pacientes = pacienteService.buscarPacientes();
        //ENTONCES
        Assertions.assertTrue(pacientes.size() >= 2, "Debe haber al menos 2 pacientes de prueba");
        System.out.println("Pacientes encontrados: " + pacientes.size());
        for (Paciente p : pacientes) {
            System.out.println("- " + p.toString());
        }
    }

    // TESTS NEGATIVOS - CASOS LÍMITE Y ERRORES 
    
    /**
     * TEST NEGATIVO - Buscar paciente con ID inválido
     */
    @Test
    public void testBuscarPacienteConIdInvalido(){
        imprimirSeparador("testBuscarPacienteConIdInvalido - TEST NEGATIVO");

        // DADO
        BD.crearTablas();
        PacienteService pacienteService = new PacienteService(new PacienteDAOH2());
        
        // CUANDO - Buscar con IDs inválidos
        Paciente resultado1 = pacienteService.buscarPacientePorId(-1);
        Paciente resultado2 = pacienteService.buscarPacientePorId(0);
        Paciente resultado3 = pacienteService.buscarPacientePorId(999);
        
        // ENTONCES - Debe manejar correctamente
        Assertions.assertNull(resultado1, "ID negativo debe retornar null");
        Assertions.assertNull(resultado2, "ID zero debe retornar null");
        Assertions.assertNull(resultado3, "ID inexistente debe retornar null");
        System.out.println("✅ Manejo correcto de IDs inválidos en Pacientes");
    }
    
    /**
     * TEST NEGATIVO - Eliminar paciente con ID inválido
     */
    @Test
    public void testEliminarPacienteConIdInvalido(){
        imprimirSeparador("testEliminarPacienteConIdInvalido - TEST NEGATIVO");

        // DADO
        BD.crearTablas();
        PacienteService pacienteService = new PacienteService(new PacienteDAOH2());
        
        // CUANDO - Intentar eliminar con IDs inválidos
        // Estas operaciones no deben crashear el sistema
        Assertions.assertDoesNotThrow(() -> {
            pacienteService.eliminarPaciente(-1);
            pacienteService.eliminarPaciente(0);
            pacienteService.eliminarPaciente(999);
        }, "El sistema debe manejar eliminaciones inválidas sin crashear");
        
        System.out.println("✅ Sistema robusto: Maneja eliminaciones de pacientes inválidas sin crashear");
    }
    
    /**
     * TEST NEGATIVO - Buscar paciente por email inválido
     */
    @Test
    public void testBuscarPacientePorEmailInvalido(){
        imprimirSeparador("testBuscarPacientePorEmailInvalido - TEST NEGATIVO");

        // DADO
        BD.crearTablas();
        PacienteService pacienteService = new PacienteService(new PacienteDAOH2());
        
        // CUANDO - Buscar con emails inválidos
        Paciente resultado1 = pacienteService.buscarPacientePorEmail("inexistente@test.com");
        Paciente resultado2 = pacienteService.buscarPacientePorEmail("");
        Paciente resultado3 = pacienteService.buscarPacientePorEmail(null);
        
        // ENTONCES - Debe manejar correctamente casos no encontrados
        Assertions.assertNull(resultado1, "Email inexistente debe retornar null");
        Assertions.assertNull(resultado2, "Email vacío debe retornar null");
        Assertions.assertNull(resultado3, "Email null debe retornar null");
        System.out.println("✅ Búsquedas por email manejan casos inválidos correctamente");
    }
    
    /**
     * TEST NEGATIVO - Actualizar paciente con datos inválidos
     */
    @Test
    public void testActualizarPacienteConDatosInvalidos(){
        imprimirSeparador("testActualizarPacienteConDatosInvalidos - TEST NEGATIVO");

        // DADO
        BD.crearTablas();
        PacienteService pacienteService = new PacienteService(new PacienteDAOH2());
        
        // CUANDO - Intentar actualizar con ID nulo (usando constructor válido)
        Paciente pacienteInvalido = new Paciente("Test", "Apellido", 123456, 
                                                java.time.LocalDate.now(), 
                                                null, "test@test.com");
        pacienteInvalido.setId(null); // ID nulo para test negativo
        
        // ENTONCES - No debe crashear, debe manejar correctamente
        Assertions.assertDoesNotThrow(() -> {
            pacienteService.actualizarPaciente(pacienteInvalido);
        }, "Actualización con ID nulo no debe crashear el sistema");
        
        System.out.println("✅ Actualización de paciente maneja datos inválidos correctamente");
    }
}
