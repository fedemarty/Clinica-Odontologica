# Clínica Odontológica - Instrucciones para Agentes IA

## Resumen del Proyecto
Sistema de gestión de clínica dental en Java usando **arquitectura de 3 capas**: patrón Modelo-DAO-Servicio con base de datos H2 en memoria. Proyecto educativo de microservicios enfocado en operaciones CRUD para entidades `Paciente` y `Odontologo`.

## Patrones de Arquitectura

### Estructura Principal
- **Modelos**: `Paciente`, `Odontologo`, `Domicilio` - POJOs simples con constructores duales
- **DAOs**: Implementan interfaz `iDao<T>` con CRUD completo + búsqueda genérica
- **Servicios**: Capa de lógica de negocio con validación de entrada y delegación a DAO
- **Base de Datos**: H2 basada en archivo en `~/clinicaFeliz` con IDs auto-generados

### Relaciones Clave
- `Paciente` → `Domicilio` (composición vía clave foránea DOMICILIO_ID)
- `Odontologo.matricula` tiene restricción UNIQUE (requerimiento de auditoría)
- Todas las entidades usan IDs `Integer` con auto-incremento H2

## Patrones Críticos de Desarrollo

### Convención de Constructores
```java
// Siempre proporcionar ambos patrones:
public Entity(Integer id, String campo1, ...) { } // Para recuperación de BD
public Entity(String campo1, ...) { }             // Para nuevas inserciones
```

### Estándar de Implementación DAO
Cada DAO debe implementar todos los métodos de `iDao<T>`:
- `guardar()` - Retorna entidad con ID generado
- `buscar(Integer id)` - Recuperación de entidad única
- `eliminar(Integer id)` - Validación suave en capa de Servicio
- `actualizar(T entity)` - Actualización completa de entidad
- `buscarGenerico(String param)` - Usado para búsquedas por email/matricula
- `buscarTodos()` - Lista todas las entidades con relaciones cargadas

### Validaciones en Capa de Servicio
**Siempre validar en Servicio antes de delegar al DAO:**
- Campos requeridos nulos/vacíos (nombre, matricula para Odontologo)
- Validación de ID para actualizaciones/eliminaciones (null o <= 0 = inválido)
- Reglas de negocio: matricula es obligatoria para cumplimiento de auditoría

### Inicialización de Base de Datos
- **Siempre llamar `BD.crearTablas()` primero** en tests y métodos main
- Crea esquema con datos de prueba: 2 pacientes, 2 odontólogos, 2 domicilios
- Usa patrón DROP/CREATE - resetea todos los datos en cada ejecución

## Estándares de Testing

### Estructura de Tests (JUnit 5)
```java
@Test
public void nombreMetodo() {
    // DADO - Configuración (BD.crearTablas(), crear servicio)
    // CUANDO - Acción (llamar método bajo prueba)  
    // ENTONCES - Aserciones (verificar resultados)
}
```

### Cobertura de Tests Requerida
- **Tests positivos**: Operaciones CRUD con datos válidos
- **Tests negativos**: IDs inválidos (-1, 0, 999), campos requeridos nulos/vacíos
- **Tests de límites**: Usar `assertDoesNotThrow()` para validación de robustez

## Flujos de Trabajo de Desarrollo

### Construcción y Pruebas
```bash
mvn test          # Ejecutar todos los tests JUnit
mvn compile       # Compilar sin tests
```

### Acceso a Base de Datos
- Conexión: `BD.getConnection()` → H2 JDBC
- Siempre usar try-finally para limpieza de conexiones
- PreparedStatement para todas las consultas (prevención de inyección SQL)

## Convenciones Específicas del Proyecto

### Patrón de Logging
- Logging basado en consola con mensajes descriptivos
- Formato: `"Operación entidad: " + entidad.toString()`
- Logging de errores: `"Error al [operacion]: " + e.getMessage()`

### Términos de Negocio en Español
- `guardar` = save, `buscar` = find, `eliminar` = delete
- `Paciente` tiene `numeroContacto`, `fechaIngreso`, `email`
- `Odontologo` tiene `matricula` (número de licencia profesional)

### Gestión de Conexiones
- String de conexión H2: `jdbc:h2:~/clinicaFeliz`
- Credenciales: sa/sa (base de datos embebida)
- Siempre cerrar conexiones en bloques finally

## Consideraciones Futuras
Este es un sistema fundacional diseñado para expansión de microservicios. Al agregar funcionalidades:
- Mantener la separación de 3 capas
- Preservar los contratos de interfaz DAO
- Mantener validación de negocio en capa de Servicio
- Seguir los patrones de constructores establecidos