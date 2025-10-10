import dao.BD;
import dao.OdontologoDAOH2;
import model.Odontologo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.OdontologoService;

/**
 * TESTS PARA ODONTOLOGO
 * 
 * Tests JUnit según requerimientos:
 * - Paciente: guardar y buscarPorID; listar; actualizar; eliminar; opcionalmente buscarPorString
 * - Odontólogo: guardar y listar; buscarPorID; eliminar; opcionalmente actualizar
 *
 * Patrón DADO-CUANDO-ENTONCES para claridad
 */
public class OdontologoTestService {

    private void imprimirSeparador(String testName) {
        System.out.println("\n==============================================");
        System.out.println("COMIENZA TEST DE: " + testName);
        System.out.println("==============================================");
    }
    /**
     * TEST - Guardar Odontólogo
     * Valida CRUD: inserción con ID auto-generado
     * Patrón DADO-CUANDO-ENTONCES
     */
    @Test
    public void guardarYBuscarOdontologo(){
        imprimirSeparador("guardarYBuscarOdontologo");

        // DADO - Configuración inicial de BD y servicio
        BD.crearTablas();
        OdontologoService odontologoService = new OdontologoService(new OdontologoDAOH2());
        
        // CUANDO - Guardar nuevo odontologo con matrícula única
        Odontologo nuevoOdontologo = new Odontologo("Dr. Carlos", "Martinez", "MAT003");
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(nuevoOdontologo);
        
        // ENTONCES - Verificar que se guardó correctamente con ID asignado
        Assertions.assertNotNull(odontologoGuardado);
        Assertions.assertNotNull(odontologoGuardado.getId());
        System.out.println("Odontologo guardado: " + odontologoGuardado.toString());
    }
    
    @Test
    public void buscarOdontologoPorId(){
        imprimirSeparador("buscarOdontologoPorId");

        // DADO
        BD.crearTablas();
        OdontologoService odontologoService = new OdontologoService(new OdontologoDAOH2());
        
        // CUANDO - Buscar odontologo existente (datos de prueba)
        Odontologo odontologo = odontologoService.buscarOdontologoPorId(1);
        
        // ENTONCES
        Assertions.assertNotNull(odontologo);
        Assertions.assertEquals("Dr. Juan", odontologo.getNombre());
        System.out.println("Odontologo encontrado: " + odontologo.toString());
    }
    
    @Test
    public void listarOdontologos(){
        imprimirSeparador("listarOdontologos");

        // DADO
        BD.crearTablas();
        OdontologoService odontologoService = new OdontologoService(new OdontologoDAOH2());
        
        // CUANDO
        var odontologos = odontologoService.buscarOdontologos();
        
        // ENTONCES
        Assertions.assertTrue(odontologos.size() >= 2); // Los 2 de prueba
        System.out.println("Odontologos encontrados: " + odontologos.size());
        for (Odontologo odt : odontologos) {
            System.out.println("- " + odt.toString());
        }
    }
    
    @Test
    public void eliminarOdontologo(){
        imprimirSeparador("eliminarOdontologo");

        // DADO
        BD.crearTablas();
        OdontologoService odontologoService = new OdontologoService(new OdontologoDAOH2());
        
        // CUANDO - Eliminar odontologo
        odontologoService.eliminarOdontologo(2);
        
        // Verificar que no existe
        Odontologo odontologoEliminado = odontologoService.buscarOdontologoPorId(2);
        
        // ENTONCES
        Assertions.assertNull(odontologoEliminado);
        System.out.println("Odontologo eliminado correctamente");
    }
    
    @Test
    public void buscarOdontologoPorMatricula(){
        imprimirSeparador("buscarOdontologoPorMatricula");

        // DADO
        BD.crearTablas();
        OdontologoService odontologoService = new OdontologoService(new OdontologoDAOH2());
        
        // CUANDO
        Odontologo odontologo = odontologoService.buscarOdontologoPorMatricula("MAT001");
        
        // ENTONCES
        Assertions.assertNotNull(odontologo);
        Assertions.assertEquals("Dr. Juan", odontologo.getNombre());
        System.out.println("Odontologo por matricula: " + odontologo.toString());
    }

    // TESTS NEGATIVOS - CASOS LÍMITE Y ERRORES 
    
    /**
     * TEST NEGATIVO - Guardar odontólogo sin nombre
     * Valida que el sistema rechace odontólogos con datos inválidos
     */
    @Test
    public void testGuardarOdontologoSinNombre(){
        imprimirSeparador("testGuardarOdontologoSinNombre - TEST NEGATIVO");

        // DADO
        BD.crearTablas();
        OdontologoService odontologoService = new OdontologoService(new OdontologoDAOH2());
        
        // CUANDO - Intentar guardar odontólogo sin nombre
        Odontologo odontologoInvalido = new Odontologo(null, "Apellido", "MAT999");
        Odontologo resultado = odontologoService.guardarOdontologo(odontologoInvalido);
        
        // ENTONCES - Debe rechazar el guardado
        Assertions.assertNull(resultado, "No debería guardar odontólogo sin nombre");
        System.out.println("Validación correcta: Rechazó odontólogo sin nombre");
    }
    
    /**
     * TEST NEGATIVO - Guardar odontólogo sin matrícula
     * Valida que el sistema rechace odontólogos sin matrícula (dato crítico)
     */
    @Test
    public void testGuardarOdontologoSinMatricula(){
        imprimirSeparador("testGuardarOdontologoSinMatricula - TEST NEGATIVO");

        // DADO
        BD.crearTablas();
        OdontologoService odontologoService = new OdontologoService(new OdontologoDAOH2());
        
        // CUANDO - Intentar guardar odontólogo sin matrícula
        Odontologo odontologoInvalido = new Odontologo("Dr. Test", "Sin Matricula", null);
        Odontologo resultado = odontologoService.guardarOdontologo(odontologoInvalido);
        
        // ENTONCES - Debe rechazar el guardado
        Assertions.assertNull(resultado, "No debería guardar odontólogo sin matrícula");
        System.out.println("Validación correcta: Rechazó odontólogo sin matrícula");
    }
    
    /**
     * TEST NEGATIVO - Buscar odontólogo con ID inválido
     * Valida manejo de IDs negativos o zero
     */
    @Test
    public void testBuscarOdontologoConIdInvalido(){
        imprimirSeparador("testBuscarOdontologoConIdInvalido - TEST NEGATIVO");

        // DADO
        BD.crearTablas();
        OdontologoService odontologoService = new OdontologoService(new OdontologoDAOH2());
        
        // CUANDO - Buscar con ID inválido
        Odontologo resultado1 = odontologoService.buscarOdontologoPorId(-1);
        Odontologo resultado2 = odontologoService.buscarOdontologoPorId(0);
        Odontologo resultado3 = odontologoService.buscarOdontologoPorId(999);
        
        // ENTONCES - No debe encontrar nada y manejar correctamente
        Assertions.assertNull(resultado1, "ID negativo debe retornar null");
        Assertions.assertNull(resultado2, "ID zero debe retornar null"); 
        Assertions.assertNull(resultado3, "ID inexistente debe retornar null");
        System.out.println("Manejo correcto de IDs inválidos");
    }
    
    /**
     * TEST NEGATIVO - Eliminar con ID inválido
     * Valida que no crashee al intentar eliminar IDs inválidos
     */
    @Test
    public void testEliminarOdontologoConIdInvalido(){
        imprimirSeparador("testEliminarOdontologoConIdInvalido - TEST NEGATIVO");

        // DADO
        BD.crearTablas();
        OdontologoService odontologoService = new OdontologoService(new OdontologoDAOH2());
        
        // CUANDO - Intentar eliminar con IDs inválidos
        // Estas operaciones no deben crashear el sistema
        Assertions.assertDoesNotThrow(() -> {
            odontologoService.eliminarOdontologo(-1);
            odontologoService.eliminarOdontologo(0);
            odontologoService.eliminarOdontologo(999);
        }, "El sistema debe manejar IDs inválidos sin crashear");
        
        System.out.println("Sistema robusto: Maneja eliminaciones inválidas sin crashear");
    }
    
    /**
     * TEST NEGATIVO - Buscar por matrícula inexistente
     * Valida búsquedas con matrículas que no existen
     */
    @Test
    public void testBuscarPorMatriculaInexistente(){
        imprimirSeparador("testBuscarPorMatriculaInexistente - TEST NEGATIVO");

        // DADO
        BD.crearTablas();
        OdontologoService odontologoService = new OdontologoService(new OdontologoDAOH2());
        
        // CUANDO - Buscar matrículas inexistentes
        Odontologo resultado1 = odontologoService.buscarOdontologoPorMatricula("INEXISTENTE");
        Odontologo resultado2 = odontologoService.buscarOdontologoPorMatricula("");
        Odontologo resultado3 = odontologoService.buscarOdontologoPorMatricula(null);
        
        // ENTONCES - Debe manejar correctamente casos no encontrados
        Assertions.assertNull(resultado1, "Matrícula inexistente debe retornar null");
        Assertions.assertNull(resultado2, "Matrícula vacía debe retornar null");
        Assertions.assertNull(resultado3, "Matrícula null debe retornar null");
        System.out.println("Búsquedas por matrícula manejan casos no encontrados correctamente");
    }
}