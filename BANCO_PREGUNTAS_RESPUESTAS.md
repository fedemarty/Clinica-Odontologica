# 🎓 BANCO DE PREGUNTAS Y RESPUESTAS - Defensa Técnica

## 📚 ÍNDICE DE PREGUNTAS

### Arquitectura y Patrones
1. [¿Qué patrón de arquitectura usaste?](#arquitectura-1)
2. [¿Por qué usaste arquitectura en capas?](#arquitectura-2)
3. [Explica el patrón MVC en tu proyecto](#arquitectura-3)
4. [¿Qué es el patrón Repository?](#arquitectura-4)
5. [¿Qué diferencia hay entre DAO y Repository?](#arquitectura-5)

### Spring Boot y Framework
6. [¿Qué es Spring Boot?](#spring-1)
7. [¿Por qué usaste Spring Boot y no Spring tradicional?](#spring-2)
8. [¿Qué es la inyección de dependencias?](#spring-3)
9. [¿Qué significa @Autowired?](#spring-4)
10. [¿Qué es el contenedor IoC de Spring?](#spring-5)
11. [¿Qué son los estereotipos (@Service, @Repository, @Controller)?](#spring-6)

### JPA e Hibernate
12. [¿Qué es JPA?](#jpa-1)
13. [¿Qué diferencia hay entre JPA e Hibernate?](#jpa-2)
14. [¿Qué es ORM?](#jpa-3)
15. [Explica las anotaciones @Entity, @Table, @Id](#jpa-4)
16. [¿Qué hace @GeneratedValue?](#jpa-5)
17. [¿Qué es un EntityManager?](#jpa-6)

### Relaciones JPA
18. [Explica @OneToOne](#relaciones-1)
19. [Explica @ManyToOne](#relaciones-2)
20. [¿Qué es FetchType (LAZY vs EAGER)?](#relaciones-3)
21. [¿Qué es cascade en JPA?](#relaciones-4)

### Spring Data JPA
22. [¿Qué es Spring Data JPA?](#springdata-1)
23. [¿Cómo funciona JpaRepository?](#springdata-2)
24. [¿Cómo genera Spring Data los métodos?](#springdata-3)
25. [¿Qué es Query Methods?](#springdata-4)

### REST y APIs
26. [¿Qué es REST?](#rest-1)
27. [¿Qué diferencia hay entre @Controller y @RestController?](#rest-2)
28. [Explica los métodos HTTP (GET, POST, PUT, DELETE)](#rest-3)
29. [¿Qué es @RequestBody y @ResponseBody?](#rest-4)
30. [¿Qué es @PathVariable?](#rest-5)
31. [¿Qué significa RESTful?](#rest-6)
32. [¿Qué es CORS?](#rest-7)

### Validaciones y Manejo de Errores
33. [¿Dónde implementaste las validaciones?](#validacion-1)
34. [¿Qué es Bean Validation (@Valid)?](#validacion-2)
35. [¿Cómo manejas excepciones?](#validacion-3)
36. [¿Qué es @ControllerAdvice?](#validacion-4)

### Base de Datos
37. [¿Por qué usaste H2?](#bd-1)
38. [¿Qué diferencia hay entre H2 y MySQL?](#bd-2)
39. [¿Qué significa ddl-auto=create-drop?](#bd-3)
40. [¿Cómo se crean las tablas?](#bd-4)

### Configuración
41. [¿Para qué sirve application.properties?](#config-1)
42. [¿Qué es pom.xml?](#config-2)
43. [¿Qué es Maven?](#config-3)

### Testing
44. [¿Qué tests implementaste?](#testing-1)
45. [¿Qué es JUnit?](#testing-2)
46. [¿Cómo probarías el Service sin BD?](#testing-3)

### Frontend
47. [¿Cómo comunica el frontend con el backend?](#frontend-1)
48. [¿Qué es fetch() en JavaScript?](#frontend-2)
49. [¿Por qué usaste Bootstrap?](#frontend-3)

### Mejoras y Escalabilidad
50. [¿Qué mejoras le harías?](#mejoras-1)
51. [¿Cómo implementarías seguridad?](#mejoras-2)
52. [¿Qué son los DTOs?](#mejoras-3)
53. [¿Cómo escalarías el sistema?](#mejoras-4)

---

## 📖 PREGUNTAS Y RESPUESTAS DETALLADAS

<a name="arquitectura-1"></a>
### 1. ¿Qué patrón de arquitectura usaste?

**Respuesta:**
Usé **arquitectura en capas** (Layered Architecture) con el patrón MVC como base. El proyecto está dividido en 5 capas principales:

1. **Presentación (View):** HTML + JavaScript + Bootstrap
2. **Controller:** APIs REST que reciben peticiones HTTP
3. **Service:** Lógica de negocio y validaciones
4. **Repository:** Acceso a datos con Spring Data JPA
5. **Entity:** Modelos de dominio que se mapean a la BD

Cada capa tiene responsabilidades específicas y solo se comunica con la capa directamente inferior, lo que permite:
- Separación de preocupaciones (Separation of Concerns)
- Mantenibilidad (cambios en una capa no afectan otras)
- Testabilidad (puedo probar cada capa independientemente)
- Escalabilidad (puedo optimizar capas específicas)

---

<a name="arquitectura-2"></a>
### 2. ¿Por qué usaste arquitectura en capas?

**Respuesta:**
Por cuatro razones principales:

**1. Organización:** Es más fácil encontrar y modificar código cuando cada cosa tiene su lugar específico.

**2. Mantenibilidad:** Si necesito cambiar la base de datos de H2 a MySQL, solo modifico la configuración y los repositories. El resto del código no se ve afectado.

**3. Trabajo en equipo:** Varios desarrolladores pueden trabajar simultáneamente en capas diferentes sin conflictos. Uno en el frontend, otro en services, otro en la BD.

**4. Testing:** Puedo hacer tests unitarios de cada capa independientemente. Por ejemplo, puedo testear el Service sin necesitar una base de datos real.

Es el estándar en aplicaciones empresariales porque ha demostrado funcionar bien en proyectos grandes y complejos.

---

<a name="arquitectura-3"></a>
### 3. Explica el patrón MVC en tu proyecto

**Respuesta:**
MVC significa Model-View-Controller:

**Model (Modelo):** Son mis entidades JPA (`Paciente`, `Odontologo`, `Turno`). Representan los datos del dominio y cómo se persisten en la base de datos.

**View (Vista):** Son los archivos HTML (`pacientes.html`, `odontologos.html`, `turnos.html`). Es lo que ve el usuario final. En mi caso, JavaScript se encarga de actualizar dinámicamente estas vistas.

**Controller (Controlador):** Son mis `@RestController` (`PacienteController`, etc.). Reciben las peticiones HTTP del usuario, procesan la solicitud llamando a los Services, y devuelven respuestas en formato JSON.

**Flujo MVC en mi proyecto:**
```
Usuario interactúa con View (HTML)
        ↓
View envía petición HTTP a Controller
        ↓
Controller procesa y llama a Service/Model
        ↓
Service ejecuta lógica y usa Repository
        ↓
Model interactúa con la BD
        ↓
Respuesta vuelve por las mismas capas
        ↓
View se actualiza con los datos
```

---

<a name="arquitectura-4"></a>
### 4. ¿Qué es el patrón Repository?

**Respuesta:**
Es un patrón de diseño que **abstrae el acceso a los datos**. Actúa como una colección en memoria, pero en realidad los datos están en la base de datos.

**Ventajas:**

**1. Desacoplamiento:** El código de negocio no sabe si los datos vienen de MySQL, PostgreSQL, o una API externa. Solo sabe que puede hacer `repository.save()`.

**2. Centralización:** Todo el acceso a datos está en un solo lugar. Si necesito optimizar consultas, sé exactamente dónde buscar.

**3. Testabilidad:** Puedo crear un repository falso (mock) para testing sin necesitar una BD real.

**Ejemplo en mi proyecto:**
```java
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    Optional<Paciente> findByEmail(String email);
}
```

No escribo implementación, Spring Data JPA la genera automáticamente. Es como decirle: "Dame un paciente por email" y Spring sabe cómo hacerlo.

---

<a name="arquitectura-5"></a>
### 5. ¿Qué diferencia hay entre DAO y Repository?

**Respuesta:**
Ambos abstraen el acceso a datos, pero con filosofías diferentes:

**DAO (Data Access Object):**
- Más cercano a la base de datos
- Piensa en términos de tablas y SQL
- Métodos como `insert()`, `update()`, `delete()`
- Ejemplo: `pacienteDAO.insert(paciente)` → piensas en INSERT SQL

**Repository:**
- Más cercano al dominio de negocio
- Piensa en colecciones de objetos
- Métodos como `save()`, `findAll()`, `existsBy...()`
- Ejemplo: `pacienteRepository.save(paciente)` → piensas en guardar un objeto

**En mi proyecto:**
Originalmente (Sprint 1 con DAO), teníamos `PacienteDAOH2`. Al migrar a Spring Boot, evolucionamos a `PacienteRepository` que es más moderno y expresivo. Repository es el estándar actual de Spring.

---

<a name="spring-1"></a>
### 6. ¿Qué es Spring Boot?

**Respuesta:**
Spring Boot es un framework que facilita crear aplicaciones Java listas para producción de forma rápida.

**Características principales:**

**1. Autoconfiguración:** Configura automáticamente componentes basándose en las dependencias. Si detecta H2 en el classpath, configura una fuente de datos automáticamente.

**2. Servidor embebido:** Incluye Tomcat, no necesito instalarlo por separado. Mi aplicación es un JAR ejecutable.

**3. Starter dependencies:** Paquetes pre-configurados. Con `spring-boot-starter-web` obtengo todo lo necesario para REST APIs.

**4. Sin XML:** Configuración con anotaciones Java, no XMLs complicados.

**5. Production-ready:** Incluye métricas, health checks, y logs listos para producción.

**Ventaja vs Spring tradicional:**
- Spring tradicional: 100 líneas de XML de configuración
- Spring Boot: Una anotación `@SpringBootApplication` y listo

Es el estándar de facto para aplicaciones Java modernas.

---

<a name="spring-3"></a>
### 8. ¿Qué es la inyección de dependencias?

**Respuesta:**
Es un patrón donde **no creamos objetos manualmente**, sino que el framework (Spring) los crea y los "inyecta" donde se necesitan.

**Sin inyección de dependencias:**
```java
public class PacienteService {
    private PacienteRepository repository = new PacienteRepositoryImpl();
    // Problema: Estoy acoplado a la implementación específica
}
```

**Con inyección de dependencias:**
```java
@Service
public class PacienteService {
    @Autowired
    private PacienteRepository repository;
    // Spring crea el repository y lo inyecta automáticamente
}
```

**Ventajas:**

**1. Bajo acoplamiento:** PacienteService no sabe qué implementación de Repository usa. Spring decide.

**2. Testing fácil:** Puedo inyectar un mock en los tests:
```java
PacienteService service = new PacienteService();
service.setRepository(mockRepository);  // Inyecto un falso
```

**3. Configuración centralizada:** Cambio la implementación en un solo lugar (configuración de Spring), no en cada clase.

**4. Gestión de ciclo de vida:** Spring decide cuándo crear y destruir objetos.

---

<a name="spring-4"></a>
### 9. ¿Qué significa @Autowired?

**Respuesta:**
`@Autowired` le dice a Spring: **"Busca un bean de este tipo y asígnalo aquí automáticamente"**.

**Ejemplo:**
```java
@Service
public class PacienteService {
    @Autowired  // ← Spring busca un bean de tipo PacienteRepository
    private PacienteRepository pacienteRepository;
}
```

**¿Cómo funciona?**

1. Spring escanea todas las clases con `@Repository`, `@Service`, `@Controller`
2. Crea instancias (beans) de cada una
3. Cuando ve `@Autowired`, busca un bean que coincida con el tipo
4. Lo inyecta automáticamente

**Tipos de inyección:**

**Por campo (lo que uso):**
```java
@Autowired
private PacienteRepository repository;
```

**Por constructor (recomendado para producción):**
```java
public PacienteService(PacienteRepository repository) {
    this.repository = repository;
}
```

**Por setter:**
```java
@Autowired
public void setRepository(PacienteRepository repository) {
    this.repository = repository;
}
```

La inyección por constructor es mejor porque garantiza que el objeto nunca esté en estado inválido (sin dependencias).

---

<a name="spring-6"></a>
### 11. ¿Qué son los estereotipos (@Service, @Repository, @Controller)?

**Respuesta:**
Son anotaciones que marcan el **rol** de una clase en la arquitectura y le dicen a Spring que las gestione.

**@Service:**
- Marca clases con lógica de negocio
- Ejemplo: `PacienteService`
- Semántica: "Esta clase tiene operaciones de negocio"

**@Repository:**
- Marca clases de acceso a datos
- Ejemplo: `PacienteRepository`
- Semántica: "Esta clase interactúa con la BD"
- Bonus: Spring traduce excepciones SQL a DataAccessException

**@Controller:**
- Marca clases que manejan peticiones web
- Ejemplo: `PacienteController`
- Devuelve vistas (HTML, JSP)

**@RestController:**
- Combinación de `@Controller` + `@ResponseBody`
- Devuelve datos (JSON, XML)
- Lo que uso en mi proyecto

**¿Por qué usar estereotipos y no solo @Component?**

**1. Claridad:** Al leer `@Service`, sé que es lógica de negocio
**2. Semántica:** Comunica la intención del diseño
**3. Funcionalidad:** `@Repository` traduce excepciones
**4. AOP:** Puedo aplicar comportamiento a todas las clases con cierto estereotipo

---

<a name="jpa-1"></a>
### 12. ¿Qué es JPA?

**Respuesta:**
**JPA** = Java Persistence API

Es una **especificación** (conjunto de interfaces y reglas) que define cómo mapear objetos Java a tablas relacionales.

**Conceptos clave:**

**1. Especificación, no implementación:**
- JPA dice QUÉ hacer
- Hibernate, EclipseLink dicen CÓMO hacerlo
- Es como un contrato que diferentes proveedores implementan

**2. ORM (Object-Relational Mapping):**
```java
// En Java
Paciente paciente = new Paciente("Juan", "Pérez");
entityManager.persist(paciente);

// JPA lo traduce a SQL
INSERT INTO PACIENTES (nombre, apellido) VALUES ('Juan', 'Pérez');
```

**3. Beneficios:**
- No escribo SQL manualmente
- El código es independiente de la BD específica
- Cambio de MySQL a PostgreSQL sin cambiar código Java
- Menos errores (no más comillas mal cerradas en SQL)

**4. Anotaciones principales:**
- `@Entity`: Esta clase es una tabla
- `@Table`: Nombre de la tabla
- `@Id`: Clave primaria
- `@Column`: Configuración de columna
- `@OneToMany`, `@ManyToOne`: Relaciones

**En mi proyecto:**
Uso JPA con implementación Hibernate vía Spring Boot. Las anotaciones en `Paciente.java`, `Turno.java` son JPA estándar.

---

<a name="jpa-2"></a>
### 13. ¿Qué diferencia hay entre JPA e Hibernate?

**Respuesta:**

| Aspecto | JPA | Hibernate |
|---------|-----|-----------|
| Tipo | Especificación (interfaces) | Implementación (código real) |
| Analogía | Interfaz Java | Clase que implementa la interfaz |
| Código | `@Entity`, `@Id`, `EntityManager` | `SessionFactory`, `Session` |
| Vendor | Sun/Oracle | Red Hat |

**Relación:**
- JPA define las reglas
- Hibernate las implementa
- Puedo cambiar Hibernate por EclipseLink sin cambiar mi código JPA

**Ejemplo:**
```java
// Código JPA estándar (portátil)
@Entity
public class Paciente {
    @Id
    private Integer id;
}

// Código específico de Hibernate (no portátil)
@org.hibernate.annotations.Cache
public class Paciente { ... }
```

**En mi proyecto:**
Uso anotaciones JPA para mantener el código portátil. Spring Boot automáticamente usa Hibernate como proveedor JPA.

**Ventaja:**
Si en el futuro quiero cambiar a otro proveedor JPA, solo cambio la dependencia en `pom.xml`, no el código.

---

<a name="jpa-3"></a>
### 14. ¿Qué es ORM?

**Respuesta:**
**ORM** = Object-Relational Mapping (Mapeo Objeto-Relacional)

Es una técnica que **traduce entre dos mundos**:
- **Mundo Orientado a Objetos** (Java)
- **Mundo Relacional** (SQL/Tablas)

**El problema que resuelve:**

**Programadores piensan en objetos:**
```java
Paciente p = new Paciente();
p.setNombre("Juan");
p.getDomicilio().setCalle("Av. Corrientes");
```

**Bases de datos piensan en tablas:**
```sql
PACIENTES: id, nombre, apellido, domicilio_id
DOMICILIOS: id, calle, numero
```

**ORM hace el puente:**
```java
// Escribo esto
paciente.setNombre("Juan");
repository.save(paciente);

// ORM lo traduce a esto automáticamente
UPDATE PACIENTES SET NOMBRE = 'Juan' WHERE ID = 1;
```

**Ventajas:**
- Trabajo con objetos naturales en Java
- No mezclo SQL con lógica de negocio
- Cambio de BD sin reescribir queries
- Previene SQL injection

**Desventajas:**
- Curva de aprendizaje
- Queries complejas pueden ser menos eficientes
- "Magia" que hay que entender

**En mi proyecto:**
JPA/Hibernate es mi ORM. Cada `@Entity` se mapea a una tabla automáticamente.

---

<a name="jpa-4"></a>
### 15. Explica las anotaciones @Entity, @Table, @Id

**Respuesta:**

**@Entity:**
Marca que esta clase Java es una entidad persistente (se guarda en BD).

```java
@Entity  // ← JPA gestiona esta clase
public class Paciente {
    // Por defecto, la tabla se llama "Paciente" o "paciente"
}
```

**@Table:**
Personaliza el nombre de la tabla y otras configuraciones.

```java
@Table(name = "PACIENTES")  // ← Nombre explícito de la tabla
```

Sin `@Table`, JPA usa el nombre de la clase. Lo uso porque quiero control sobre los nombres en la BD.

**@Id:**
Marca la clave primaria.

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;
```

**Cada entidad DEBE tener un @Id**. Es como decirle a JPA: "Este campo identifica únicamente cada registro".

**Ejemplo completo:**
```java
@Entity                           // Esta clase se persiste
@Table(name = "PACIENTES")        // En la tabla PACIENTES
public class Paciente {
    
    @Id                           // Clave primaria
    @GeneratedValue(              // Se genera automáticamente
        strategy = GenerationType.IDENTITY  // Usando AUTO_INCREMENT de la BD
    )
    private Integer id;
    
    @Column(                      // Configuración de columna
        name = "NOMBRE",          // Nombre en BD
        nullable = false,         // NOT NULL
        length = 255              // VARCHAR(255)
    )
    private String nombre;
}
```

**Traducción a SQL:**
```sql
CREATE TABLE PACIENTES (
    id INT AUTO_INCREMENT PRIMARY KEY,
    NOMBRE VARCHAR(255) NOT NULL
);
```

---

<a name="relaciones-1"></a>
### 18. Explica @OneToOne

**Respuesta:**
`@OneToOne` define una relación uno a uno entre dos entidades.

**En mi proyecto: Paciente ↔ Domicilio**

```java
@Entity
public class Paciente {
    @OneToOne
    @JoinColumn(name = "DOMICILIO_ID")
    private Domicilio domicilio;
}
```

**Significado:**
- **Un** paciente tiene **un** domicilio
- **Un** domicilio pertenece a **un** paciente
- En la tabla PACIENTES hay una columna DOMICILIO_ID (foreign key)

**En la base de datos:**
```sql
PACIENTES
├── id: 1
├── nombre: "Juan"
└── domicilio_id: 10  ← Apunta al domicilio

DOMICILIOS
├── id: 10
├── calle: "Av. Corrientes"
└── numero: 1500
```

**En código:**
```java
// Crear relación
Paciente p = new Paciente();
Domicilio d = new Domicilio("Av. Corrientes", 1500);
p.setDomicilio(d);

// Acceder
String calle = p.getDomicilio().getCalle();  // "Av. Corrientes"
```

**@JoinColumn:**
Especifica el nombre de la columna FK en la base de datos.

---

<a name="relaciones-2"></a>
### 19. Explica @ManyToOne

**Respuesta:**
`@ManyToOne` define una relación muchos a uno.

**En mi proyecto: Turno → Paciente**

```java
@Entity
public class Turno {
    @ManyToOne
    @JoinColumn(name = "PACIENTE_ID", nullable = false)
    private Paciente paciente;
    
    @ManyToOne
    @JoinColumn(name = "ODONTOLOGO_ID", nullable = false)
    private Odontologo odontologo;
}
```

**Significado:**
- **Muchos** turnos pueden tener el **mismo** paciente
- **Muchos** turnos pueden tener el **mismo** odontólogo
- **Un** turno tiene **un** paciente y **un** odontólogo

**Ejemplo en la vida real:**
```
Paciente "Juan Pérez" tiene:
├── Turno #1: 10/11/2025 10:00 con Dr. García
├── Turno #2: 15/11/2025 14:00 con Dr. García
└── Turno #3: 20/11/2025 16:00 con Dra. López
```

**En la base de datos:**
```sql
TURNOS
├── id: 1, paciente_id: 5, odontologo_id: 2, fecha_hora: ...
├── id: 2, paciente_id: 5, odontologo_id: 2, fecha_hora: ...
└── id: 3, paciente_id: 5, odontologo_id: 3, fecha_hora: ...
      ↑ Mismo paciente en todos
```

**Direccionalidad:**
- `@ManyToOne` en Turno significa: "Tengo una referencia a un Paciente"
- Si quisiera la inversa (`@OneToMany` en Paciente), sería:
```java
public class Paciente {
    @OneToMany(mappedBy = "paciente")
    private List<Turno> turnos;
}
```

---

<a name="springdata-1"></a>
### 22. ¿Qué es Spring Data JPA?

**Respuesta:**
Spring Data JPA es una capa sobre JPA que **reduce aún más el código** necesario para acceso a datos.

**Sin Spring Data JPA (JPA puro):**
```java
public class PacienteRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;
    
    public Paciente save(Paciente p) {
        if (p.getId() == null) {
            entityManager.persist(p);
            return p;
        } else {
            return entityManager.merge(p);
        }
    }
    
    public Optional<Paciente> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Paciente.class, id));
    }
    
    public List<Paciente> findAll() {
        return entityManager
            .createQuery("SELECT p FROM Paciente p", Paciente.class)
            .getResultList();
    }
    
    // Y muchos métodos más...
}
```

**Con Spring Data JPA:**
```java
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    // ¡Listo! Ya tengo save(), findById(), findAll(), delete(), etc.
}
```

**Spring Data JPA proporciona automáticamente:**
- `save(T entity)` - Guardar/actualizar
- `findById(ID id)` - Buscar por ID
- `findAll()` - Listar todos
- `delete(T entity)` - Eliminar
- `existsById(ID id)` - Verificar existencia
- `count()` - Contar registros
- Y muchos más...

**Además, puedo agregar métodos personalizados:**
```java
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    Optional<Paciente> findByEmail(String email);
    // Spring genera: SELECT * FROM PACIENTES WHERE EMAIL = ?
}
```

**Beneficios:**
- **Menos código:** No implemento nada
- **Menos errores:** Código generado es confiable
- **Consistencia:** Todos los repositories funcionan igual
- **Productividad:** Me enfoco en lógica de negocio

---

<a name="springdata-3"></a>
### 24. ¿Cómo genera Spring Data los métodos?

**Respuesta:**
Spring Data JPA usa **convenciones de nombres** para generar queries automáticamente.

**Estructura del nombre:**
```
find + By + Campo + Operación
```

**Ejemplos en mi proyecto:**

```java
// findByEmail
Optional<Paciente> findByEmail(String email);
// Genera: SELECT * FROM PACIENTES WHERE EMAIL = ?

// existsByEmail
boolean existsByEmail(String email);
// Genera: SELECT COUNT(*) > 0 FROM PACIENTES WHERE EMAIL = ?

// findByMatricula
Optional<Odontologo> findByMatricula(String matricula);
// Genera: SELECT * FROM ODONTOLOGOS WHERE MATRICULA = ?
```

**Palabras clave soportadas:**

| Keyword | SQL equivalente | Ejemplo |
|---------|-----------------|---------|
| `And` | `AND` | `findByNombreAndApellido` |
| `Or` | `OR` | `findByNombreOrApellido` |
| `Between` | `BETWEEN` | `findByFechaIngresodetween` |
| `LessThan` | `<` | `findByEdadLessThan` |
| `GreaterThan` | `>` | `findByEdadGreaterThan` |
| `Like` | `LIKE` | `findByNombreLike` |
| `OrderBy` | `ORDER BY` | `findByApellidoOrderByNombre` |
| `Top` / `First` | `LIMIT` | `findTop3ByOrderByFechaIngresoDesc` |

**Proceso interno:**

1. Spring escanea el nombre del método
2. Identifica las partes: `find` + `By` + `Email`
3. Analiza el tipo de retorno: `Optional<Paciente>`
4. Genera JPQL: `SELECT p FROM Paciente p WHERE p.email = :email`
5. Hibernate traduce a SQL específico de la BD

**Si el nombre no sigue la convención:**
Spring lanza excepción al arrancar la aplicación:
```
Unable to derive query from method name...
```

**Alternativa - Query manual:**
```java
@Query("SELECT p FROM Paciente p WHERE p.email = :email")
Optional<Paciente> buscarPorCorreo(@Param("email") String email);
```

---

<a name="rest-1"></a>
### 26. ¿Qué es REST?

**Respuesta:**
**REST** = REpresentational State Transfer

Es un **estilo arquitectónico** para diseñar APIs web basado en el protocolo HTTP.

**Principios REST:**

**1. Cliente-Servidor:**
Frontend (cliente) separado del backend (servidor).

**2. Stateless (Sin estado):**
Cada petición es independiente. El servidor no guarda información de sesión.

```java
// Cada petición lleva toda la info necesaria
GET /api/pacientes/5
Authorization: Bearer token123
```

**3. Recursos:**
Todo es un recurso identificado por una URL.

```
/api/pacientes          → Colección de pacientes
/api/pacientes/5        → Paciente específico con ID 5
/api/pacientes/5/turnos → Turnos del paciente 5
```

**4. Métodos HTTP estándar:**

| Método | Operación | Ejemplo |
|--------|-----------|---------|
| GET | Leer | `GET /api/pacientes` - Lista |
| POST | Crear | `POST /api/pacientes` - Nuevo |
| PUT | Actualizar completo | `PUT /api/pacientes/5` |
| PATCH | Actualizar parcial | `PATCH /api/pacientes/5` |
| DELETE | Eliminar | `DELETE /api/pacientes/5` |

**5. Representaciones (JSON/XML):**
Los recursos se transfieren en formato JSON o XML.

```json
{
  "id": 5,
  "nombre": "Juan",
  "apellido": "Pérez"
}
```

**6. HATEOAS (Hypermedia):**
Las respuestas incluyen links a recursos relacionados (no lo implementé, es avanzado).

**Ventajas de REST:**

- **Simplicidad:** Usa HTTP que todo el mundo entiende
- **Escalabilidad:** Stateless permite balanceo de carga fácil
- **Independiente del lenguaje:** Cualquier cliente puede consumirlo
- **Cacheable:** Respuestas GET pueden cachearse
- **Estándar de industria:** Todas las empresas lo usan

**En mi proyecto:**
Todos mis Controllers exponen APIs REST siguiendo estos principios.

---

<a name="rest-3"></a>
### 28. Explica los métodos HTTP (GET, POST, PUT, DELETE)

**Respuesta:**

**GET - Obtener datos**
- **Propósito:** Leer/consultar información
- **Idempotente:** Llamarlo múltiples veces no cambia nada
- **Cacheable:** Las respuestas pueden cachearse
- **Body:** No lleva cuerpo

```java
@GetMapping("/api/pacientes")
public List<Paciente> listar() { ... }

@GetMapping("/api/pacientes/{id}")
public Paciente buscar(@PathVariable Integer id) { ... }
```

**POST - Crear nuevo recurso**
- **Propósito:** Crear una nueva entidad
- **No idempotente:** Cada llamada crea un nuevo registro
- **Body:** Lleva los datos del recurso a crear

```java
@PostMapping("/api/pacientes")
public Paciente crear(@RequestBody Paciente paciente) { ... }
```

**Petición:**
```http
POST /api/pacientes
Content-Type: application/json

{
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan@email.com"
}
```

**Respuesta:**
```http
HTTP/1.1 201 Created
Location: /api/pacientes/10

{
  "id": 10,
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan@email.com"
}
```

**PUT - Actualizar recurso existente (completo)**
- **Propósito:** Reemplazar completamente un recurso
- **Idempotente:** Llamarlo múltiples veces produce el mismo resultado
- **Body:** Lleva TODOS los datos del recurso

```java
@PutMapping("/api/pacientes/{id}")
public Paciente actualizar(@PathVariable Integer id, @RequestBody Paciente paciente) {
    paciente.setId(id);
    return pacienteService.actualizarPaciente(paciente);
}
```

**DELETE - Eliminar recurso**
- **Propósito:** Eliminar una entidad
- **Idempotente:** Eliminar algo ya eliminado devuelve el mismo resultado
- **Body:** No lleva cuerpo

```java
@DeleteMapping("/api/pacientes/{id}")
public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
    pacienteService.eliminarPaciente(id);
    return ResponseEntity.noContent().build();  // 204 No Content
}
```

**PATCH - Actualizar parcial (no lo implementé)**
- **Propósito:** Actualizar solo algunos campos
- **Body:** Solo los campos a actualizar

```java
PATCH /api/pacientes/5
{
  "email": "nuevo@email.com"  // Solo actualiza email
}
```

**Códigos de respuesta HTTP:**

| Código | Significado | Cuándo |
|--------|-------------|--------|
| 200 OK | Éxito | GET, PUT exitoso |
| 201 Created | Creado | POST exitoso |
| 204 No Content | Sin contenido | DELETE exitoso |
| 400 Bad Request | Petición inválida | Datos incorrectos |
| 404 Not Found | No encontrado | ID inexistente |
| 500 Internal Error | Error del servidor | Excepción no manejada |

---

<a name="rest-7"></a>
### 32. ¿Qué es CORS?

**Respuesta:**
**CORS** = Cross-Origin Resource Sharing (Compartir Recursos Entre Orígenes)

Es una **política de seguridad del navegador** que controla qué sitios pueden acceder a tu API.

**El problema (Same-Origin Policy):**

Por defecto, JavaScript en `sitio-a.com` **NO puede** hacer peticiones a `sitio-b.com`:

```javascript
// Ejecutando en http://sitio-a.com
fetch('http://sitio-b.com/api/datos')  // ❌ Bloqueado por CORS
```

**¿Por qué existe?**
Seguridad. Evita que un sitio malicioso robe tus datos de otro sitio donde tienes sesión.

**La solución (CORS):**

El servidor (`sitio-b.com`) puede decir: **"Permito peticiones de sitio-a.com"**

```java
@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "http://sitio-a.com")  // ← Permite este origen
public class PacienteController { ... }
```

**En mi proyecto:**

```java
@CrossOrigin(origins = "*")  // ← Permite TODOS los orígenes
```

**¿Por qué `origins = "*"`?**
Porque mi frontend y backend están en el **mismo servidor** (localhost:8080), pero durante desarrollo es útil permitir todo.

**En producción, especificaría dominios concretos:**
```java
@CrossOrigin(origins = {
    "https://clinica-frontend.com",
    "https://app.clinica.com"
})
```

**Headers CORS:**

Cuando hago una petición, el navegador envía:
```http
OPTIONS /api/pacientes
Origin: http://localhost:3000
```

El servidor responde:
```http
Access-Control-Allow-Origin: *
Access-Control-Allow-Methods: GET, POST, PUT, DELETE
Access-Control-Allow-Headers: Content-Type
```

Si el navegador ve que el origen está permitido, hace la petición real.

**Sin CORS configurado:**
```
Access to fetch at 'http://localhost:8080/api/pacientes' from origin 
'http://localhost:3000' has been blocked by CORS policy.
```

---

<a name="validacion-1"></a>
### 33. ¿Dónde implementaste las validaciones?

**Respuesta:**
Implementé validaciones en **dos capas**:

**1. Frontend (HTML5 + JavaScript):**
Validaciones básicas de UX para feedback inmediato al usuario.

```html
<input type="email" required>  
<!-- Valida formato email y que no esté vacío -->

<input type="text" minlength="3" maxlength="50">
<!-- Valida longitud -->
```

**Ventajas:**
- Feedback instantáneo (sin ir al servidor)
- Mejor experiencia de usuario
- Reduce carga en el servidor

**Limitación:**
- No es segura (el usuario puede desactivar JavaScript)
- Solo para UX, no para seguridad

**2. Backend (Service Layer):**
Validaciones de negocio críticas que SÍ son seguras.

```java
@Service
public class PacienteService {
    public Paciente guardarPaciente(Paciente paciente) {
        // Validación 1: Email único
        if (pacienteRepository.existsByEmail(paciente.getEmail())) {
            System.out.println("Error: Email duplicado");
            return null;
        }
        
        // Validación 2: Campos obligatorios
        if (paciente.getNombre() == null || paciente.getNombre().trim().isEmpty()) {
            System.out.println("Error: Nombre es obligatorio");
            return null;
        }
        
        // Si pasa validaciones, guardar
        return pacienteRepository.save(paciente);
    }
}
```

**Validaciones implementadas:**

- **Paciente:**
  - Email único (no duplicados)
  - Nombre y apellido obligatorios
  - ID válido para actualizar/eliminar

- **Odontólogo:**
  - Matrícula obligatoria
  - Matrícula única (auditoría)
  - Nombre obligatorio

- **Turno:**
  - Paciente obligatorio (no null, ID válido)
  - Odontólogo obligatorio
  - Fecha/hora obligatoria

**¿Por qué en Service y no en Controller?**

- **Reutilización:** Si tengo varios controllers, todos usan el mismo service con las mismas validaciones
- **Lógica de negocio:** Las reglas pertenecen al dominio, no a la capa web
- **Testing:** Puedo testear validaciones sin levantar servidor web

---

<a name="bd-1"></a>
### 37. ¿Por qué usaste H2?

**Respuesta:**
Usé H2 por ser ideal para **desarrollo y aprendizaje**:

**Ventajas:**

**1. Sin instalación:**
No necesito instalar MySQL, PostgreSQL, ni configurar usuarios.

**2. Embebida:**
Se incluye dentro de la aplicación. Todo está en el JAR.

**3. Consola web:**
Tiene una interfaz visual en el navegador para ejecutar SQL.

**4. Compatible con estándares:**
Usa SQL estándar, migraría fácil a otra BD.

**5. Rápida:**
Para desarrollo y testing es muy veloz.

**6. Modo archivo o memoria:**
Puedo elegir:
```properties
# En archivo (persiste al cerrar)
spring.datasource.url=jdbc:h2:~/clinicaFeliz

# En memoria (se borra al cerrar)
spring.datasource.url=jdbc:h2:mem:testdb
```

**Desventajas (por eso no es para producción):**

**1. Menos robusta:**
No está diseñada para miles de usuarios concurrentes.

**2. Menos funcionalidades:**
MySQL/PostgreSQL tienen más opciones de optimización.

**3. Rendimiento limitado:**
Con datos masivos es más lenta.

**4. Sin herramientas avanzadas:**
MySQL tiene MySQL Workbench, PostgreSQL tiene pgAdmin.

**Para producción migrariaía a:**

**PostgreSQL (recomendado):**
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
</dependency>
```

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/clinica
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

**¿Cambios en el código? NINGUNO.** Esa es la belleza de JPA - el código es portable.

---

<a name="mejoras-1"></a>
### 50. ¿Qué mejoras le harías?

**Respuesta:**
Identifico mejoras en varias áreas:

**1. VALIDACIONES MÁS ROBUSTAS:**

Usar Bean Validation:
```java
@Entity
public class Paciente {
    @NotNull(message = "Nombre es obligatorio")
    @Size(min = 2, max = 50, message = "Nombre debe tener entre 2 y 50 caracteres")
    private String nombre;
    
    @Email(message = "Email inválido")
    @NotNull
    private String email;
}

@PostMapping
public Paciente crear(@Valid @RequestBody Paciente paciente) {
    // @Valid valida automáticamente
}
```

**2. MANEJO GLOBAL DE EXCEPCIONES:**

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ErrorDTO(ex.getMessage()));
    }
    
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorDTO> handleDuplicate(DuplicateEmailException ex) {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(new ErrorDTO(ex.getMessage()));
    }
}
```

**3. DTOs (Data Transfer Objects):**

Separar lo que se guarda de lo que se envía:
```java
// Entity (BD)
@Entity
public class Paciente {
    private String password;  // No quiero enviarlo al frontend
}

// DTO (API)
public class PacienteDTO {
    private Integer id;
    private String nombre;
    private String email;
    // Sin password
}
```

**4. SEGURIDAD CON SPRING SECURITY:**

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/pacientes/**").hasRole("RECEPCIONISTA")
                .requestMatchers("/api/odontologos/**").hasRole("ADMIN")
            )
            .httpBasic();
        return http.build();
    }
}
```

**5. PAGINACIÓN:**

Para no cargar 10,000 pacientes de una vez:
```java
@GetMapping
public Page<Paciente> listar(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "20") int size
) {
    return pacienteService.buscarTodos(PageRequest.of(page, size));
}
```

**6. TESTING COMPLETO:**

```java
@SpringBootTest
public class PacienteServiceTest {
    
    @MockBean
    private PacienteRepository repository;
    
    @Autowired
    private PacienteService service;
    
    @Test
    public void guardar_EmailDuplicado_DeberiaFallar() {
        when(repository.existsByEmail("test@test.com")).thenReturn(true);
        
        Paciente p = new Paciente();
        p.setEmail("test@test.com");
        
        Paciente result = service.guardarPaciente(p);
        assertNull(result);
    }
}
```

**7. DOCUMENTACIÓN API CON SWAGGER:**

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
</dependency>
```

Genera documentación automática en: `http://localhost:8080/swagger-ui.html`

**8. LOGGING MEJORADO:**

Reemplazar `System.out.println()` con SLF4J:
```java
@Service
public class PacienteService {
    private static final Logger log = LoggerFactory.getLogger(PacienteService.class);
    
    public Paciente guardar(Paciente p) {
        log.info("Guardando paciente: {}", p.getEmail());
        // ...
        log.debug("Paciente guardado con ID: {}", p.getId());
    }
}
```

**9. BASE DE DATOS DE PRODUCCIÓN:**

Migrar a PostgreSQL con connection pool:
```properties
spring.datasource.url=jdbc:postgresql://db.clinica.com:5432/clinica
spring.datasource.hikari.maximum-pool-size=10
spring.jpa.hibernate.ddl-auto=validate  # No crear/eliminar tablas
```

**10. DOCKER PARA DEPLOYMENT:**

```dockerfile
FROM openjdk:17-slim
COPY target/clinica.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## 🎯 RESUMEN DE RESPUESTAS RÁPIDAS

Si te hacen preguntas cortas, estas son respuestas de 30 segundos:

**"¿Qué es Spring Boot?"**
> Framework Java que simplifica crear aplicaciones web con autoconfiguración y servidor embebido.

**"¿Qué es JPA?"**
> Especificación para mapear objetos Java a tablas SQL sin escribir SQL manualmente.

**"¿Por qué arquitectura en capas?"**
> Para separar responsabilidades, facilitar testing y mantenimiento.

**"¿Qué es inyección de dependencias?"**
> Spring crea y gestiona objetos automáticamente sin que yo use `new`.

**"¿Por qué H2?"**
> Ideal para desarrollo: sin instalación, embebida, con consola web.

**"¿Qué es REST?"**
> Estilo arquitectónico para APIs web usando HTTP y JSON.

**"¿Qué hace @Autowired?"**
> Le dice a Spring que inyecte automáticamente una dependencia.

**"¿Qué diferencia hay entre @Entity y @Table?"**
> @Entity marca que es una clase persistente, @Table personaliza el nombre de la tabla.

**"¿Qué es @ManyToOne?"**
> Relación donde muchas entidades apuntan a una (muchos turnos → un paciente).

**"¿Cómo probaste el proyecto?"**
> Con las vistas HTML, Postman para APIs, y consola H2 para verificar datos.

---

## ✅ CHECKLIST FINAL DE PREPARACIÓN

Antes de tu defensa, verifica que puedas responder:

- [ ] ¿Qué problema resuelve tu proyecto?
- [ ] ¿Qué es Spring Boot y por qué lo usaste?
- [ ] ¿Qué es JPA/Hibernate?
- [ ] ¿Cómo está organizado tu código? (capas)
- [ ] ¿Qué es inyección de dependencias?
- [ ] ¿Qué significa @Entity, @Service, @RestController?
- [ ] ¿Cómo se relacionan Paciente, Turno, Odontólogo?
- [ ] ¿Qué es REST?
- [ ] ¿Qué validaciones implementaste?
- [ ] ¿Por qué usaste H2?
- [ ] ¿Qué mejoras le harías?
- [ ] ¿Cómo probarías el sistema sin interfaz gráfica?
- [ ] ¿Qué pasa cuando el usuario crea un paciente? (flujo completo)

---

**¡Mucha suerte en tu defensa! 🍀💪**

Recuerda: **Es imposible saberlo TODO**. Lo importante es demostrar que entiendes los conceptos fundamentales y que puedes aprender lo que no sabes.

**¡Vas a hacerlo genial!** ⭐
