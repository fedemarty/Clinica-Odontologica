import dao.BD;
import dao.OdontologoDAOH2;
import model.Odontologo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.OdontologoService;

/**
 * CLASE NUEVA - TESTS PARA ODONTOLOGO SPRINT 1 EXAMEN
 * 
 * Tests JUnit según requerimientos:
 * - Paciente: guardar y buscarPorID; listar; actualizar; eliminar; opcionalmente buscarPorString
 * - Odontólogo: guardar y listar; buscarPorID; eliminar; opcionalmente actualizar
 * 
 * Cubre todos los métodos CRUD implementados por estudiantes
 * Patrón DADO-CUANDO-ENTONCES para claridad
 */
public class OdontologoTestService {
    
    /**
     * TEST REQUERIDO - Guardar Odontólogo
     * Valida CRUD: inserción con ID auto-generado
     * Patrón DADO-CUANDO-ENTONCES
     */
    @Test
    public void guardarYBuscarOdontologo(){
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
}