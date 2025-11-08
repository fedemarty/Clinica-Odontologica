# 🏥 GUÍA COMPLETA DEL PROYECTO - Clínica Odontológica "Sonrisa Feliz"

## 📚 ÍNDICE
1. [¿Qué es este proyecto?](#qué-es-este-proyecto)
2. [Arquitectura del Sistema](#arquitectura-del-sistema)
3. [Tecnologías Utilizadas](#tecnologías-utilizadas)
4. [Estructura del Proyecto](#estructura-del-proyecto)
5. [Explicación Capa por Capa](#explicación-capa-por-capa)
6. [Flujo Completo de una Operación](#flujo-completo-de-una-operación)
7. [Cómo Funciona Cada Módulo](#cómo-funciona-cada-módulo)
8. [Base de Datos](#base-de-datos)
9. [APIs REST](#apis-rest)
10. [Preguntas Frecuentes de Examen](#preguntas-frecuentes-de-examen)

---

## 🎯 ¿QUÉ ES ESTE PROYECTO?

### Contexto del Problema
La clínica "Sonrisa Feliz" crecía y su libreta de papel ya no era suficiente:
- ❌ Citas superpuestas
- ❌ Teléfonos mal anotados  
- ❌ Confirmaciones perdidas
- ❌ Sin métricas ni reportes

### Solución Implementada
Un **sistema web** que permite:
- ✅ Registrar pacientes con sus domicilios
- ✅ Gestionar odontólogos con su matrícula profesional
- ✅ Agendar turnos relacionando paciente + odontólogo + fecha/hora
- ✅ Hacer operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
- ✅ Acceder desde navegador web
- ✅ Tener trazabilidad con logs

---

## 🏗️ ARQUITECTURA DEL SISTEMA

### ¿Qué es una Arquitectura?
Es la forma en que organizamos el código del proyecto. Imagina que estás construyendo una casa:
- 🏠 **Cimientos** = Base de datos
- 🧱 **Paredes** = Lógica de negocio (Services)
- 🚪 **Puertas** = APIs (Controllers)
- 🎨 **Decoración** = Vistas HTML

### Nuestro Patrón: Arquitectura en Capas (MVC)

```
┌─────────────────────────────────────┐
│      CAPA DE PRESENTACIÓN           │  ← Lo que ve el usuario
│   (HTML + JavaScript + Bootstrap)   │
└────────────┬────────────────────────┘
             │ HTTP Requests
┌────────────▼────────────────────────┐
│      CAPA DE CONTROLADORES          │  ← Recibe peticiones web
│      (PacienteController, etc)      │
└────────────┬────────────────────────┘
             │ Llama a servicios
┌────────────▼────────────────────────┐
│      CAPA DE SERVICIOS              │  ← Lógica de negocio
│   (PacienteService, validaciones)   │
└────────────┬────────────────────────┘
             │ Usa repositories
┌────────────▼────────────────────────┐
│      CAPA DE DATOS                  │  ← Acceso a base de datos
│    (Repositories + Spring Data)     │
└────────────┬────────────────────────┘
             │
┌────────────▼────────────────────────┐
│       BASE DE DATOS H2              │  ← Almacenamiento
│    (Tablas: pacientes, turnos...)   │
└─────────────────────────────────────┘
```

### ¿Por qué en capas?

1. **Organización:** Cada cosa en su lugar
2. **Mantenibilidad:** Si hay un error, sé dónde buscarlo
3. **Escalabilidad:** Puedo cambiar una capa sin afectar las otras
4. **Trabajo en equipo:** Varios desarrolladores pueden trabajar en capas diferentes

---

## 🛠️ TECNOLOGÍAS UTILIZADAS

### 1. **Java 17** 
- **¿Qué es?** Lenguaje de programación orientado a objetos
- **¿Por qué?** Robusto, multiplataforma, muy usado en empresas
- **En este proyecto:** Toda la lógica está escrita en Java

### 2. **Spring Boot 3.5.6**
- **¿Qué es?** Framework (conjunto de herramientas) que facilita crear aplicaciones web
- **¿Por qué?** 
  - Configura automáticamente muchas cosas
  - Incluye servidor web (Tomcat)
  - Facilita la inyección de dependencias
- **En este proyecto:** Es la base de todo el backend

### 3. **Maven**
- **¿Qué es?** Gestor de dependencias y construcción del proyecto
- **¿Por qué?** Descarga librerías automáticamente, compila el proyecto
- **Archivo clave:** `pom.xml` (lista todas las librerías que necesitamos)

### 4. **H2 Database**
- **¿Qué es?** Base de datos en memoria/archivo
- **¿Por qué?** Fácil de usar, no necesita instalación separada
- **En este proyecto:** Guarda pacientes, odontólogos y turnos
- **Ubicación:** `~/clinicaFeliz` (en tu carpeta de usuario)

### 5. **JPA/Hibernate**
- **¿Qué es?** ORM (Object-Relational Mapping) - traduce objetos Java a tablas SQL
- **¿Por qué?** No tenemos que escribir SQL manualmente
- **En este proyecto:** Las anotaciones `@Entity`, `@Table`, etc.

### 6. **Spring Data JPA**
- **¿Qué es?** Facilita el acceso a datos
- **¿Por qué?** Métodos CRUD automáticos sin escribir código
- **En este proyecto:** Los `Repository` interfaces

### 7. **Log4j**
- **¿Qué es?** Librería para registrar eventos (logging)
- **¿Por qué?** Podemos ver qué hace el sistema en tiempo real
- **En este proyecto:** Los mensajes que aparecen en consola

### 8. **Bootstrap 5**
- **¿Qué es?** Framework CSS para diseño web
- **¿Por qué?** Hace que las páginas se vean profesionales sin mucho esfuerzo
- **En este proyecto:** Toda la interfaz visual (botones, tablas, formularios)

---

## 📁 ESTRUCTURA DEL PROYECTO

```
Clase9-ClinicaOdontologica/
│
├── pom.xml                          ← Configuración de Maven (dependencias)
│
├── src/
│   ├── main/
│   │   ├── java/com/clinicaodontologica/
│   │   │   │
│   │   │   ├── ClinicaOdontologicaApplication.java  ← Punto de entrada (main)
│   │   │   │
│   │   │   ├── entity/              ← ENTIDADES (Modelos de datos)
│   │   │   │   ├── Paciente.java
│   │   │   │   ├── Odontologo.java
│   │   │   │   ├── Turno.java
│   │   │   │   └── Domicilio.java
│   │   │   │
│   │   │   ├── repository/          ← REPOSITORIES (Acceso a BD)
│   │   │   │   ├── PacienteRepository.java
│   │   │   │   ├── OdontologoRepository.java
│   │   │   │   ├── TurnoRepository.java
│   │   │   │   └── DomicilioRepository.java
│   │   │   │
│   │   │   ├── service/             ← SERVICIOS (Lógica de negocio)
│   │   │   │   ├── PacienteService.java
│   │   │   │   ├── OdontologoService.java
│   │   │   │   └── TurnoService.java
│   │   │   │
│   │   │   ├── controller/          ← CONTROLADORES (APIs REST)
│   │   │   │   ├── PacienteController.java
│   │   │   │   ├── OdontologoController.java
│   │   │   │   └── TurnoController.java
│   │   │   │
│   │   │   └── config/              ← CONFIGURACIÓN
│   │   │       └── DatosIniciales.java  ← Carga datos de prueba
│   │   │
│   │   └── resources/
│   │       ├── application.properties   ← Configuración de Spring Boot
│   │       └── static/                  ← Vistas HTML
│   │           ├── index.html
│   │           ├── pacientes.html
│   │           ├── odontologos.html
│   │           └── turnos.html
│   │
│   └── test/                        ← Tests unitarios (JUnit)
│
└── target/                          ← Carpeta generada al compilar
```

---

## 🔍 EXPLICACIÓN CAPA POR CAPA

### 1️⃣ CAPA DE ENTIDADES (Entity)

**¿Qué son?** Clases Java que representan las tablas de la base de datos.

#### Ejemplo: Paciente.java

```java
@Entity                              // ← Le dice a JPA que es una tabla
@Table(name = "PACIENTES")           // ← Nombre de la tabla en BD
public class Paciente {
    
    @Id                              // ← Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ← Auto-incremento
    private Integer id;
    
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    
    @OneToOne                        // ← Relación 1 a 1 con Domicilio
    @JoinColumn(name = "DOMICILIO_ID")
    private Domicilio domicilio;
    
    // Constructores, getters, setters...
}
```

**Anotaciones importantes:**
- `@Entity`: Esta clase es una tabla
- `@Table`: Nombre de la tabla en la BD
- `@Id`: Campo clave primaria
- `@GeneratedValue`: Se genera automáticamente
- `@Column`: Configuración de la columna
- `@OneToOne`, `@ManyToOne`: Relaciones entre tablas

**Relaciones en el proyecto:**
- **Paciente → Domicilio:** Uno a Uno (@OneToOne)
- **Turno → Paciente:** Muchos a Uno (@ManyToOne)
- **Turno → Odontólogo:** Muchos a Uno (@ManyToOne)

---

### 2️⃣ CAPA DE REPOSITORIES

**¿Qué son?** Interfaces que permiten hacer operaciones CRUD sin escribir SQL.

#### Ejemplo: PacienteRepository.java

```java
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    
    // Métodos heredados automáticamente de JpaRepository:
    // - save(Paciente p)        → Guardar/actualizar
    // - findById(Integer id)    → Buscar por ID
    // - findAll()               → Listar todos
    // - deleteById(Integer id)  → Eliminar por ID
    // - existsById(Integer id)  → Verificar si existe
    
    // Métodos personalizados (Spring Data los implementa automáticamente):
    Optional<Paciente> findByEmail(String email);
    boolean existsByEmail(String email);
}
```

**¿Cómo funciona la "magia"?**
- Spring Data JPA lee el nombre del método (`findByEmail`)
- Identifica el campo (`Email`)
- Genera automáticamente el SQL: `SELECT * FROM PACIENTES WHERE EMAIL = ?`

**Convención de nombres:**
- `findBy...` → Buscar
- `existsBy...` → Verificar existencia
- `countBy...` → Contar
- `deleteBy...` → Eliminar

---

### 3️⃣ CAPA DE SERVICIOS (Service)

**¿Qué son?** Clases que contienen la lógica de negocio y validaciones.

#### Ejemplo: PacienteService.java

```java
@Service                             // ← Spring lo gestiona automáticamente
public class PacienteService {
    
    @Autowired                       // ← Inyección de dependencias
    private PacienteRepository pacienteRepository;
    
    public Paciente guardarPaciente(Paciente paciente) {
        // VALIDACIONES DE NEGOCIO
        if (pacienteRepository.existsByEmail(paciente.getEmail())) {
            System.out.println("Error: Email duplicado");
            return null;
        }
        
        // DELEGACIÓN AL REPOSITORY
        return pacienteRepository.save(paciente);
    }
    
    public List<Paciente> buscarPacientes() {
        return pacienteRepository.findAll();
    }
    
    // Más métodos...
}
```

**¿Por qué existen los Services?**
1. **Validaciones:** Verificar que los datos sean correctos antes de guardar
2. **Lógica de negocio:** Reglas específicas de la clínica
3. **Reutilización:** Un mismo servicio puede usarse desde varios controladores
4. **Transacciones:** Gestionar operaciones múltiples

**Ejemplo de validación:**
- Email debe ser único
- Matrícula de odontólogo es obligatoria
- No se puede crear turno sin paciente y odontólogo

---

### 4️⃣ CAPA DE CONTROLADORES (Controller)

**¿Qué son?** Clases que exponen APIs REST (endpoints web).

#### Ejemplo: PacienteController.java

```java
@RestController                      // ← Es un controlador REST
@RequestMapping("/api/pacientes")    // ← Ruta base: /api/pacientes
@CrossOrigin(origins = "*")          // ← Permite acceso desde cualquier origen
public class PacienteController {
    
    @Autowired
    private PacienteService pacienteService;
    
    // POST /api/pacientes → Crear nuevo paciente
    @PostMapping
    public ResponseEntity<Paciente> crearPaciente(@RequestBody Paciente paciente) {
        Paciente guardado = pacienteService.guardarPaciente(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }
    
    // GET /api/pacientes → Listar todos
    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        List<Paciente> pacientes = pacienteService.buscarPacientes();
        return ResponseEntity.ok(pacientes);
    }
    
    // GET /api/pacientes/5 → Buscar paciente con ID 5
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPaciente(@PathVariable Integer id) {
        Paciente paciente = pacienteService.buscarPacientePorId(id);
        if (paciente != null) {
            return ResponseEntity.ok(paciente);
        }
        return ResponseEntity.notFound().build();
    }
    
    // PUT /api/pacientes/5 → Actualizar paciente con ID 5
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizarPaciente(
        @PathVariable Integer id, 
        @RequestBody Paciente paciente
    ) {
        paciente.setId(id);
        Paciente actualizado = pacienteService.actualizarPaciente(paciente);
        return ResponseEntity.ok(actualizado);
    }
    
    // DELETE /api/pacientes/5 → Eliminar paciente con ID 5
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Integer id) {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}
```

**Anotaciones HTTP:**
- `@GetMapping` → Obtener datos (SELECT)
- `@PostMapping` → Crear datos (INSERT)
- `@PutMapping` → Actualizar datos (UPDATE)
- `@DeleteMapping` → Eliminar datos (DELETE)

**ResponseEntity:**
- `ResponseEntity.ok()` → Código HTTP 200 (éxito)
- `ResponseEntity.created()` → Código HTTP 201 (creado)
- `ResponseEntity.notFound()` → Código HTTP 404 (no encontrado)
- `ResponseEntity.noContent()` → Código HTTP 204 (sin contenido)

---

### 5️⃣ CAPA DE PRESENTACIÓN (HTML + JavaScript)

**¿Qué es?** Las páginas web que ve el usuario.

#### Estructura de una vista (pacientes.html)

```html
<!-- 1. ENCABEZADO: Librerías CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

<!-- 2. FORMULARIO: Para crear/editar pacientes -->
<form id="pacienteForm">
    <input type="text" id="nombre" required>
    <input type="text" id="apellido" required>
    <input type="email" id="email" required>
    <button type="submit">Guardar</button>
</form>

<!-- 3. TABLA: Lista de pacientes -->
<table class="table">
    <tbody id="tablaPacientes">
        <!-- Se llena dinámicamente con JavaScript -->
    </tbody>
</table>

<!-- 4. JAVASCRIPT: Comunicación con el backend -->
<script>
    const API_URL = 'http://localhost:8080/api/pacientes';
    
    // Cargar pacientes al iniciar
    function cargarPacientes() {
        fetch(API_URL)                       // ← Llama al backend
            .then(response => response.json())  // ← Convierte respuesta a JSON
            .then(pacientes => {                // ← Con los datos...
                // Generar HTML dinámicamente
                const html = pacientes.map(p => `
                    <tr>
                        <td>${p.nombre}</td>
                        <td>${p.apellido}</td>
                        <td>
                            <button onclick="editar(${p.id})">Editar</button>
                            <button onclick="eliminar(${p.id})">Eliminar</button>
                        </td>
                    </tr>
                `).join('');
                document.getElementById('tablaPacientes').innerHTML = html;
            });
    }
    
    // Guardar paciente
    document.getElementById('pacienteForm').addEventListener('submit', function(e) {
        e.preventDefault();
        
        const paciente = {
            nombre: document.getElementById('nombre').value,
            apellido: document.getElementById('apellido').value,
            email: document.getElementById('email').value
        };
        
        fetch(API_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(paciente)
        })
        .then(response => {
            if (response.ok) {
                alert('Paciente guardado');
                cargarPacientes();  // Recargar lista
            }
        });
    });
    
    // Eliminar paciente
    function eliminar(id) {
        if (confirm('¿Está seguro?')) {
            fetch(`${API_URL}/${id}`, { method: 'DELETE' })
                .then(() => cargarPacientes());
        }
    }
</script>
```

**Flujo JavaScript → Backend:**

1. **Usuario llena formulario** → JavaScript captura datos
2. **JavaScript hace `fetch()`** → Envía petición HTTP al backend
3. **Controller recibe petición** → Llama al Service
4. **Service ejecuta lógica** → Llama al Repository
5. **Repository guarda en BD** → Devuelve resultado
6. **Backend responde** → JavaScript recibe respuesta
7. **JavaScript actualiza HTML** → Usuario ve el cambio

---

## 🔄 FLUJO COMPLETO DE UNA OPERACIÓN

### Ejemplo: "Guardar un nuevo paciente"

```
1. USUARIO
   ↓ Llena formulario en pacientes.html y hace clic en "Guardar"
   
2. JAVASCRIPT (Frontend)
   ↓ Captura datos del formulario
   ↓ Crea objeto JSON: { nombre: "Juan", apellido: "Pérez", ... }
   ↓ Hace fetch() POST a http://localhost:8080/api/pacientes
   
3. SPRING BOOT (recibe petición HTTP)
   ↓ Busca controlador con @RequestMapping("/api/pacientes")
   ↓ Encuentra PacienteController
   
4. PACIENTE CONTROLLER
   ↓ Método crearPaciente() recibe el JSON
   ↓ Convierte JSON a objeto Paciente (automático con @RequestBody)
   ↓ Llama a pacienteService.guardarPaciente(paciente)
   
5. PACIENTE SERVICE
   ↓ Ejecuta validaciones:
   ↓ - ¿Email ya existe? → pacienteRepository.existsByEmail()
   ↓ Si pasa validaciones:
   ↓ Llama a pacienteRepository.save(paciente)
   
6. PACIENTE REPOSITORY (Spring Data JPA)
   ↓ Convierte objeto Paciente a SQL INSERT
   ↓ Ejecuta: INSERT INTO PACIENTES (nombre, apellido...) VALUES (...)
   
7. H2 DATABASE
   ↓ Guarda el registro en la tabla PACIENTES
   ↓ Genera ID automáticamente
   ↓ Devuelve el paciente guardado con su ID
   
8. RESPUESTA (vuelve por el mismo camino)
   Repository → Service → Controller
   ↓ Controller crea ResponseEntity con código 201 (CREATED)
   ↓ Spring convierte objeto Paciente a JSON
   
9. JAVASCRIPT (Frontend)
   ↓ Recibe respuesta con el paciente guardado
   ↓ Muestra mensaje "Paciente guardado exitosamente"
   ↓ Llama a cargarPacientes() para actualizar la tabla
   
10. USUARIO
    ↓ Ve el nuevo paciente en la lista
```

---

## 🗄️ BASE DE DATOS

### Diagrama de Tablas

```
┌─────────────────────┐
│     DOMICILIOS      │
├─────────────────────┤
│ id (PK)            │
│ calle              │
│ numero             │
│ localidad          │
│ provincia          │
└─────────────────────┘
          ▲
          │ 1:1
          │
┌─────────────────────┐
│     PACIENTES       │
├─────────────────────┤
│ id (PK)            │
│ nombre             │
│ apellido           │
│ email (UNIQUE)     │
│ numero_contacto    │
│ fecha_ingreso      │
│ domicilio_id (FK)  │
└─────────────────────┘
          ▲
          │ N:1
          │
┌─────────────────────┐         ┌─────────────────────┐
│       TURNOS        │    N:1  │   ODONTOLOGOS       │
├─────────────────────┤◄────────├─────────────────────┤
│ id (PK)            │         │ id (PK)            │
│ paciente_id (FK)   │         │ nombre             │
│ odontologo_id (FK) │         │ apellido           │
│ fecha_hora         │         │ matricula (UNIQUE) │
└─────────────────────┘         └─────────────────────┘
```

### ¿Cómo se crean estas tablas?

**Opción 1: Automático (lo que usamos)**
```properties
# En application.properties
spring.jpa.hibernate.ddl-auto=create-drop
```
- Al iniciar: Hibernate crea las tablas
- Al cerrar: Hibernate las elimina
- Útil para desarrollo

**Opción 2: Script SQL manual**
```sql
CREATE TABLE pacientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    ...
);
```

### Datos Iniciales

Se cargan automáticamente con la clase `DatosIniciales.java`:

```java
@Component
public class DatosIniciales implements ApplicationRunner {
    
    @Override
    public void run(ApplicationArguments args) {
        // Crear 2 domicilios
        Domicilio d1 = new Domicilio("Siempre Viva", 723, "Springfield", "USA");
        domicilioRepository.save(d1);
        
        // Crear 2 pacientes
        Paciente p1 = new Paciente("Homero", "Simpson", ...);
        pacienteRepository.save(p1);
        
        // Crear 2 odontólogos
        Odontologo o1 = new Odontologo("Dr. Juan", "Rodriguez", "MP-1001");
        odontologoRepository.save(o1);
        
        // Crear 3 turnos
        Turno t1 = new Turno(p1, o1, LocalDateTime.of(2025, 11, 10, 10, 0));
        turnoRepository.save(t1);
    }
}
```

---

## 🌐 APIs REST

### ¿Qué es REST?

**REST** = Representational State Transfer

Es un estilo de arquitectura para crear servicios web. Características:
- Usa HTTP (GET, POST, PUT, DELETE)
- Trabaja con recursos (pacientes, turnos...)
- Responde con JSON
- Stateless (sin estado entre peticiones)

### Endpoints del Proyecto

#### 📊 PACIENTES

| Método | Endpoint | Descripción | Ejemplo Body |
|--------|----------|-------------|--------------|
| GET | `/api/pacientes` | Listar todos | - |
| GET | `/api/pacientes/5` | Buscar por ID | - |
| POST | `/api/pacientes` | Crear nuevo | `{"nombre":"Juan","apellido":"Pérez",...}` |
| PUT | `/api/pacientes/5` | Actualizar ID 5 | `{"nombre":"Juan","apellido":"García",...}` |
| DELETE | `/api/pacientes/5` | Eliminar ID 5 | - |

#### 🦷 ODONTÓLOGOS

| Método | Endpoint | Descripción | Ejemplo Body |
|--------|----------|-------------|--------------|
| GET | `/api/odontologos` | Listar todos | - |
| GET | `/api/odontologos/3` | Buscar por ID | - |
| POST | `/api/odontologos` | Crear nuevo | `{"nombre":"María","matricula":"MP-2000"}` |
| PUT | `/api/odontologos/3` | Actualizar ID 3 | `{"nombre":"María Elena",...}` |
| DELETE | `/api/odontologos/3` | Eliminar ID 3 | - |

#### 📅 TURNOS

| Método | Endpoint | Descripción | Ejemplo Body |
|--------|----------|-------------|--------------|
| GET | `/api/turnos` | Listar todos | - |
| GET | `/api/turnos/1` | Buscar por ID | - |
| POST | `/api/turnos` | Crear nuevo | `{"paciente":{"id":1},"odontologo":{"id":2},...}` |
| PUT | `/api/turnos/1` | Actualizar ID 1 | `{"fechaHora":"2025-11-15T16:00:00"}` |
| DELETE | `/api/turnos/1` | Eliminar ID 1 | - |

### Ejemplo de Petición con Postman/cURL

**Crear un paciente:**
```bash
POST http://localhost:8080/api/pacientes
Content-Type: application/json

{
  "nombre": "Ana",
  "apellido": "García",
  "email": "ana@gmail.com",
  "numeroContacto": "555-1234",
  "fechaIngreso": "2025-11-07",
  "domicilio": {
    "calle": "Av. Principal",
    "numero": 100,
    "localidad": "Buenos Aires",
    "provincia": "CABA"
  }
}
```

**Respuesta:**
```json
{
  "id": 3,
  "nombre": "Ana",
  "apellido": "García",
  "email": "ana@gmail.com",
  "numeroContacto": "555-1234",
  "fechaIngreso": "2025-11-07",
  "domicilio": {
    "id": 3,
    "calle": "Av. Principal",
    "numero": 100,
    "localidad": "Buenos Aires",
    "provincia": "CABA"
  }
}
```

---

## ❓ PREGUNTAS FRECUENTES DE EXAMEN

### 1. **¿Qué es Spring Boot y por qué lo usaste?**

**Respuesta:**
Spring Boot es un framework de Java que facilita la creación de aplicaciones empresariales. Lo usé porque:
- **Autoconfiguración:** Configura automáticamente Tomcat, JPA, etc.
- **Embedded server:** No necesito instalar un servidor aparte
- **Starter dependencies:** Incluye todas las librerías necesarias
- **Estándar de industria:** Es lo que usan las empresas

### 2. **¿Qué patrón de arquitectura usaste?**

**Respuesta:**
Usé **arquitectura en capas (MVC)**:
- **Model:** Entidades JPA (Paciente, Odontólogo, Turno)
- **View:** HTML con Bootstrap
- **Controller:** REST Controllers que exponen APIs
- **Service:** Lógica de negocio y validaciones
- **Repository:** Acceso a datos con Spring Data JPA

Esto permite **separación de responsabilidades** - cada capa tiene un propósito específico.

### 3. **¿Qué es JPA y Hibernate?**

**Respuesta:**
- **JPA** (Java Persistence API): Es una especificación (contrato) de cómo mapear objetos Java a tablas SQL
- **Hibernate:** Es la implementación de JPA que usamos
- **Beneficio:** No escribimos SQL, trabajamos con objetos Java

Ejemplo:
```java
// En lugar de:
"INSERT INTO PACIENTES (nombre, apellido) VALUES (?, ?)"

// Hacemos:
pacienteRepository.save(paciente);
```

### 4. **¿Qué es la inyección de dependencias?**

**Respuesta:**
Es un patrón donde Spring crea y gestiona los objetos por nosotros.

```java
@Service
public class PacienteService {
    @Autowired  // ← Spring inyecta automáticamente el repository
    private PacienteRepository pacienteRepository;
}
```

**Ventajas:**
- No uso `new PacienteRepository()` manualmente
- Spring gestiona el ciclo de vida
- Facilita testing (puedo inyectar mocks)
- Bajo acoplamiento

### 5. **¿Por qué usas Service entre Controller y Repository?**

**Respuesta:**
El Service contiene la **lógica de negocio**:
- **Validaciones:** Email único, matrícula obligatoria
- **Reglas de negocio:** Un turno necesita paciente Y odontólogo
- **Transacciones:** Si algo falla, hacer rollback
- **Reutilización:** Varios controllers pueden usar el mismo service

**Controller:** Recibe peticiones HTTP
**Service:** Decide QUÉ hacer
**Repository:** Hace el acceso a BD

### 6. **¿Qué es REST y por qué lo usas?**

**Respuesta:**
REST es un estilo arquitectónico para APIs web:
- Usa métodos HTTP estándar (GET, POST, PUT, DELETE)
- Trabaja con recursos (pacientes, turnos)
- Responde con JSON
- Stateless (sin sesiones)

**Lo uso porque:**
- Es estándar de industria
- Cualquier cliente puede consumirlo (web, móvil, Postman)
- Fácil de entender y documentar

### 7. **¿Qué diferencia hay entre @OneToOne y @ManyToOne?**

**Respuesta:**

**@OneToOne:** Uno a uno
```java
// Un paciente tiene UN domicilio
// Un domicilio pertenece a UN paciente
@OneToOne
private Domicilio domicilio;
```

**@ManyToOne:** Muchos a uno
```java
// Muchos turnos pueden tener el MISMO paciente
// Un turno tiene UN paciente
@ManyToOne
private Paciente paciente;
```

**@OneToMany:** Uno a muchos (no lo usé explícitamente)
```java
// Un paciente puede tener MUCHOS turnos
@OneToMany(mappedBy = "paciente")
private List<Turno> turnos;
```

### 8. **¿Cómo funciona la validación en tu proyecto?**

**Respuesta:**
Tengo validaciones en la capa de **Service**:

```java
public Paciente guardarPaciente(Paciente paciente) {
    // Validar email único
    if (pacienteRepository.existsByEmail(paciente.getEmail())) {
        System.out.println("Error: Email duplicado");
        return null;
    }
    
    // Si pasa validación, guardar
    return pacienteRepository.save(paciente);
}
```

También uso validaciones **HTML5** en el frontend:
```html
<input type="email" required>  <!-- Email válido y obligatorio -->
```

### 9. **¿Qué es @Transactional y cuándo lo usarías?**

**Respuesta:**
`@Transactional` asegura que varias operaciones se hagan como una unidad:
- **Todo o nada:** Si algo falla, se deshace todo (rollback)
- **Consistencia:** La BD no queda en estado inconsistente

Ejemplo (no lo usé explícitamente, pero Spring Data lo usa internamente):
```java
@Transactional
public void crearTurnoCompleto(...) {
    pacienteRepository.save(paciente);      // 1. Guardar paciente
    odontologoRepository.save(odontologo);  // 2. Guardar odontólogo
    turnoRepository.save(turno);            // 3. Guardar turno
    
    // Si falla el paso 3, se deshacen 1 y 2
}
```

### 10. **¿Cómo probaste tu aplicación?**

**Respuesta:**
De tres formas:

1. **Vistas HTML:** Interfaz visual para probar CRUD completo
2. **Postman:** Probar APIs REST directamente
3. **Consola H2:** Verificar datos en la base de datos

También tengo **tests unitarios** con JUnit (estructura básica creada).

### 11. **¿Qué es CORS y por qué lo configuraste?**

**Respuesta:**
**CORS** = Cross-Origin Resource Sharing

Es una política de seguridad del navegador. Por defecto, JavaScript en `pagina.com` no puede llamar APIs en `api.com`.

En mi proyecto uso `@CrossOrigin(origins = "*")` para **permitir** que mis HTML (en `localhost:8080`) puedan llamar a las APIs (en el mismo servidor).

En producción, especificaría dominios concretos por seguridad.

### 12. **¿Cómo escalarías este proyecto?**

**Respuesta:**
Para hacer el sistema más robusto:

1. **DTOs** (Data Transfer Objects): Separar lo que se guarda en BD de lo que se envía al frontend
2. **Exception Handling:** Manejo global de errores con `@ControllerAdvice`
3. **Validaciones con @Valid:** Usar Bean Validation (`@NotNull`, `@Size`, etc.)
4. **Paginación:** No cargar todos los pacientes de una vez
5. **Seguridad:** Implementar Spring Security para login
6. **Testing:** Tests unitarios e integración completos
7. **Docker:** Containerizar la aplicación
8. **Base de datos real:** Migrar de H2 a PostgreSQL/MySQL

### 13. **¿Qué es el patrón Repository?**

**Respuesta:**
Es un patrón que **abstrae el acceso a datos**. En lugar de escribir SQL, uso métodos Java.

**Ventajas:**
- **Desacoplamiento:** Si cambio de BD (H2 → MySQL), solo cambio configuración
- **Reusabilidad:** `findAll()` se puede usar en muchos lugares
- **Testing:** Puedo crear mocks fácilmente
- **Mantenibilidad:** Código más limpio

### 14. **¿Por qué usas H2 y no MySQL?**

**Respuesta:**
H2 es ideal para **desarrollo y aprendizaje**:
- ✅ No necesita instalación separada
- ✅ Embebida en la aplicación
- ✅ Consola web incluida
- ✅ Rápida

Para **producción**, migraría a PostgreSQL o MySQL porque:
- Mejor rendimiento con muchos usuarios
- Más funcionalidades
- Mejor para backups
- Más estable

### 15. **¿Qué hace el archivo application.properties?**

**Respuesta:**
Configura Spring Boot:

```properties
# Nombre de la aplicación
spring.application.name=clinica-odontologica

# Puerto del servidor
server.port=8080

# Configuración de BD
spring.datasource.url=jdbc:h2:~/clinicaFeliz
spring.datasource.username=sa
spring.datasource.password=sa

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=create-drop  # Crear/eliminar tablas
spring.jpa.show-sql=true                   # Mostrar SQL en consola

# Habilitar consola H2
spring.h2.console.enabled=true
```

---

## 🎓 CONSEJOS PARA LA DEFENSA

### Antes del Examen:

1. **Ejecuta el proyecto:**
   ```bash
   mvn spring-boot:run
   ```
   
2. **Ten abiertas estas URLs:**
   - http://localhost:8080 (página principal)
   - http://localhost:8080/pacientes.html
   - http://localhost:8080/h2-console

3. **Prepara Postman con peticiones de ejemplo**

4. **Repasa los logs en consola** - muestra que entiendes qué hace el sistema

### Durante la Defensa:

1. **Muestra primero la interfaz visual:**
   - "Aquí el usuario puede registrar pacientes..."
   - Crea un paciente en vivo
   - Muestra que aparece en la tabla

2. **Explica la arquitectura:**
   - "El formulario envía datos al Controller..."
   - "El Controller llama al Service..."
   - "El Service valida y usa el Repository..."
   - "El Repository guarda en H2..."

3. **Abre la consola H2:**
   - Muestra la tabla PACIENTES
   - Ejecuta un SELECT para mostrar el paciente recién creado
   - "Esto demuestra que los datos se persisten correctamente"

4. **Muestra el código:**
   - Abre PacienteController
   - Explica un endpoint (ej: POST)
   - "Aquí recibo el JSON y llamo al service"
   - Muestra las anotaciones (@PostMapping, @RequestBody)

5. **Si preguntan por algo específico:**
   - No inventes
   - Si no sabes, di: "No lo implementé pero sé que se podría hacer con..."
   - Muestra disposición a aprender

### Frases Útiles:

- "Usé arquitectura en capas para **separar responsabilidades**"
- "Spring Boot me permite **autoconfiguración**, no tengo que configurar Tomcat manualmente"
- "JPA **mapea automáticamente** mis clases Java a tablas SQL"
- "Los Services contienen la **lógica de negocio** y validaciones"
- "REST me permite que **cualquier cliente** consuma mis APIs"

---

## 📚 RECURSOS ADICIONALES

### Documentación Oficial:
- Spring Boot: https://spring.io/projects/spring-boot
- Spring Data JPA: https://spring.io/projects/spring-data-jpa
- Bootstrap: https://getbootstrap.com

### Para Aprender Más:
- Spring Boot Tutorial: https://www.baeldung.com/spring-boot
- JPA Tutorial: https://www.baeldung.com/learn-jpa-hibernate
- REST API Design: https://restfulapi.net

---

## ✅ CHECKLIST FINAL

Antes de entregar/defender, verifica:

- [ ] El proyecto compila sin errores (`mvn clean compile`)
- [ ] La aplicación se ejecuta (`mvn spring-boot:run`)
- [ ] Se pueden crear pacientes desde la interfaz
- [ ] Se pueden crear odontólogos desde la interfaz
- [ ] Se pueden crear turnos desde la interfaz
- [ ] Los datos persisten (aparecen después de recargar)
- [ ] La consola H2 funciona (http://localhost:8080/h2-console)
- [ ] Las APIs REST responden (probar con Postman)
- [ ] Los logs muestran las operaciones
- [ ] Entiendes cada capa del proyecto
- [ ] Puedes explicar el flujo de una operación
- [ ] Conoces las tecnologías usadas

---

## 🎯 RESUMEN EJECUTIVO

**¿Qué hiciste?**
Un sistema web de gestión de clínica odontológica con Spring Boot.

**¿Qué tecnologías usaste?**
Java 17, Spring Boot, JPA/Hibernate, H2, Bootstrap, Maven.

**¿Qué se puede hacer?**
Gestionar pacientes, odontólogos y turnos con operaciones CRUD completas.

**¿Cómo está organizado?**
Arquitectura en capas: Entity → Repository → Service → Controller → View.

**¿Qué aprendiste?**
- Arquitectura de aplicaciones empresariales
- Desarrollo de APIs REST
- Persistencia con JPA
- Inyección de dependencias
- Desarrollo Full-Stack básico

---

**¡Mucha suerte en tu defensa! 🍀**

Si durante el examen te preguntan algo que no está en esta guía, recuerda:
1. Mantén la calma
2. Explica lo que SÍ sabes
3. Reconoce lo que no implementaste
4. Muestra voluntad de aprender

**¡Vas a hacerlo genial!** 💪
