# 🚀 GUÍA PRÁCTICA - Cómo Ejecutar y Demostrar el Proyecto

## 📋 PREPARACIÓN ANTES DEL EXAMEN

### ✅ Checklist Pre-Examen

```
[ ] Java 17 instalado y funcionando
[ ] Maven instalado
[ ] Proyecto compila sin errores
[ ] Postman instalado (opcional)
[ ] Navegador web (Chrome, Firefox, Edge)
[ ] Esta guía impresa o en pantalla secundaria
```

---

## 🎬 PASO A PASO PARA EJECUTAR

### 1️⃣ Abrir Terminal/PowerShell

**Windows:**
- Presiona `Windows + R`
- Escribe `powershell`
- Enter

**Navega a la carpeta del proyecto:**
```powershell
cd "C:\Users\Administrator\Desktop\UP\Microservicios\Clase9-ClinicaOdontologica"
```

---

### 2️⃣ Compilar el Proyecto

```bash
mvn clean compile
```

**¿Qué hace este comando?**
- `clean`: Borra archivos compilados anteriores
- `compile`: Compila todo el código Java

**Resultado esperado:**
```
[INFO] BUILD SUCCESS
[INFO] Total time: X.XXX s
```

**Si hay errores:**
- Verifica que estás en la carpeta correcta
- Asegúrate de tener Java 17: `java -version`
- Verifica Maven: `mvn -version`

---

### 3️⃣ Ejecutar la Aplicación

```bash
mvn spring-boot:run
```

**¿Qué hace este comando?**
- Inicia Spring Boot
- Levanta servidor Tomcat en puerto 8080
- Crea las tablas en H2
- Carga datos iniciales

**Resultado esperado:**
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.5.6)

...
Started ClinicaOdontologicaApplication in X.XXX seconds
==============================================
🏥 INICIALIZANDO DATOS DE PRUEBA
==============================================
✅ Datos persistidos correctamente
📊 Pacientes: 2
🦷 Odontólogos: 2
🏠 Domicilios: 2
📅 Turnos: 3
==============================================
```

**⚠️ IMPORTANTE:** 
- NO cierres esta ventana mientras demuestras
- Si cierras la aplicación, perderás los datos
- Para detener: `Ctrl + C`

---

### 4️⃣ Verificar que Funciona

Abre tu navegador y ve a:
```
http://localhost:8080
```

**Deberías ver:**
- Página principal con título "Sonrisa Feliz"
- Tres tarjetas: Pacientes, Odontólogos, Turnos
- Diseño con gradiente morado

**Si no funciona:**
- ¿El servidor está corriendo? Mira la terminal
- ¿Puerto 8080 ocupado? Cambia en `application.properties`
- ¿Firewall bloqueando? Temporalmente desactívalo

---

## 🎭 DEMOSTRACIÓN EN EL EXAMEN

### Opción 1: Demostración con Interfaz Visual (Recomendado)

#### A. Gestión de Pacientes

1. **Ir a Pacientes:**
   ```
   http://localhost:8080/pacientes.html
   ```

2. **Mostrar lista inicial:**
   - "Aquí vemos los 2 pacientes que se cargaron automáticamente"
   - "Homero Simpson y Marge Simpson"

3. **Crear un nuevo paciente:**
   ```
   Nombre: María
   Apellido: González
   Email: maria.gonzalez@email.com
   Número Contacto: 11-5555-1234
   Fecha Ingreso: [Hoy]
   
   Domicilio:
   Calle: Av. Corrientes
   Número: 1500
   Localidad: CABA
   Provincia: Buenos Aires
   ```

4. **Hacer clic en "Guardar Paciente"**

5. **Mostrar resultado:**
   - "Aparece el mensaje 'Paciente guardado exitosamente'"
   - "Y automáticamente se agrega a la tabla"
   - "Vemos que se le asignó un ID (3)"

6. **Editar el paciente:**
   - Click en botón amarillo "Editar"
   - Cambiar email a: `maria.g@email.com`
   - Guardar
   - "El paciente se actualiza en la tabla"

7. **Eliminar:**
   - Click en botón rojo "Eliminar"
   - Confirmar
   - "El paciente desaparece de la lista"

**Mientras demuestras, explica:**
- "El formulario envía datos JSON al backend"
- "El Controller recibe la petición"
- "El Service valida los datos"
- "El Repository guarda en la base de datos"

---

#### B. Gestión de Odontólogos

1. **Ir a Odontólogos:**
   ```
   http://localhost:8080/odontologos.html
   ```

2. **Crear nuevo odontólogo:**
   ```
   Nombre: Laura
   Apellido: Martínez
   Matrícula: MP-3000
   ```

3. **Guardar y explicar:**
   - "La matrícula es UNIQUE - no pueden existir dos iguales"
   - "Es un requisito de auditoría de la clínica"

4. **Intentar duplicar matrícula (opcional):**
   - Crear otro con matrícula MP-3000
   - "Vemos que el sistema rechaza la operación"

---

#### C. Gestión de Turnos

1. **Ir a Turnos:**
   ```
   http://localhost:8080/turnos.html
   ```

2. **Mostrar turnos existentes:**
   - "Hay 3 turnos pre-cargados"
   - "Cada turno vincula un paciente con un odontólogo"

3. **Crear nuevo turno:**
   ```
   Paciente: [Seleccionar Homero Simpson]
   Odontólogo: [Seleccionar Laura Martínez]
   Fecha y Hora: [Seleccionar fecha futura, ej: 15/11/2025 14:00]
   ```

4. **Explicar las relaciones:**
   - "El turno tiene una relación @ManyToOne con Paciente"
   - "Y otra relación @ManyToOne con Odontólogo"
   - "En la BD se guardan como claves foráneas"

---

### Opción 2: Demostración con APIs REST (Avanzado)

Si te piden probar con Postman o cURL:

#### Crear Paciente (POST)

**Postman:**
```
Method: POST
URL: http://localhost:8080/api/pacientes
Headers: Content-Type: application/json
Body (raw, JSON):
{
  "nombre": "Carlos",
  "apellido": "Ruiz",
  "email": "carlos.ruiz@email.com",
  "numeroContacto": "11-4444-5555",
  "fechaIngreso": "2025-11-07",
  "domicilio": {
    "calle": "San Martín",
    "numero": 850,
    "localidad": "La Plata",
    "provincia": "Buenos Aires"
  }
}
```

**Respuesta esperada:**
```json
{
  "id": 4,
  "nombre": "Carlos",
  "apellido": "Ruiz",
  "email": "carlos.ruiz@email.com",
  ...
}
```

#### Listar Pacientes (GET)

```
Method: GET
URL: http://localhost:8080/api/pacientes
```

**Respuesta: Array con todos los pacientes**

#### Buscar Paciente por ID (GET)

```
Method: GET
URL: http://localhost:8080/api/pacientes/1
```

#### Actualizar Paciente (PUT)

```
Method: PUT
URL: http://localhost:8080/api/pacientes/1
Body: {JSON con datos actualizados}
```

#### Eliminar Paciente (DELETE)

```
Method: DELETE
URL: http://localhost:8080/api/pacientes/1
```

---

### Opción 3: Demostración con Consola H2

Si te piden ver la base de datos directamente:

1. **Abrir consola H2:**
   ```
   http://localhost:8080/h2-console
   ```

2. **Configurar conexión:**
   ```
   JDBC URL: jdbc:h2:~/clinicaFeliz
   User Name: sa
   Password: sa
   ```

3. **Click en "Connect"**

4. **Ejecutar consultas SQL:**

**Ver todos los pacientes:**
```sql
SELECT * FROM PACIENTES;
```

**Ver turnos con sus relaciones:**
```sql
SELECT 
    T.ID as TURNO_ID,
    P.NOMBRE || ' ' || P.APELLIDO as PACIENTE,
    O.NOMBRE || ' ' || O.APELLIDO as ODONTOLOGO,
    T.FECHA_HORA
FROM TURNOS T
JOIN PACIENTES P ON T.PACIENTE_ID = P.ID
JOIN ODONTOLOGOS O ON T.ODONTOLOGO_ID = O.ID;
```

**Contar registros:**
```sql
SELECT 
    (SELECT COUNT(*) FROM PACIENTES) as TOTAL_PACIENTES,
    (SELECT COUNT(*) FROM ODONTOLOGOS) as TOTAL_ODONTOLOGOS,
    (SELECT COUNT(*) FROM TURNOS) as TOTAL_TURNOS;
```

---

## 💡 EXPLICACIONES MIENTRAS DEMUESTRAS

### Cuando crees un Paciente:

**Di esto:**
> "Cuando hago click en Guardar, JavaScript captura los datos del formulario y hace una petición POST a la API REST en `/api/pacientes`. El Controller recibe el JSON, lo convierte automáticamente en un objeto Paciente, y llama al Service. El Service valida que el email no esté duplicado usando `existsByEmail()`, y si todo está bien, llama al Repository. Spring Data JPA convierte la llamada `save()` en un INSERT SQL, Hibernate ejecuta la consulta en H2, y la base de datos devuelve el paciente con su ID generado automáticamente. La respuesta viaja de vuelta por las mismas capas, y JavaScript actualiza la tabla HTML."

**Versión corta:**
> "El formulario envía JSON al backend, Spring Boot valida y guarda en la base de datos, y JavaScript actualiza la vista."

---

### Cuando muestres el código:

**Abre en el editor:**

1. **Entidad (Paciente.java):**
   - "Esta clase representa la tabla PACIENTES"
   - Mostrar anotaciones: `@Entity`, `@Table`, `@Id`
   - "JPA mapea automáticamente esto a SQL"

2. **Repository (PacienteRepository.java):**
   - "Esta interfaz extiende JpaRepository"
   - "No tengo que escribir implementación"
   - "Spring Data genera el código automáticamente"

3. **Service (PacienteService.java):**
   - "Aquí están las validaciones de negocio"
   - Mostrar: `existsByEmail()`
   - "Esto previene emails duplicados"

4. **Controller (PacienteController.java):**
   - "Estos son los endpoints REST"
   - Mostrar: `@PostMapping`, `@GetMapping`
   - "Cada método maneja una operación HTTP"

---

## 🎯 PREGUNTAS QUE PUEDEN HACERTE (Y RESPUESTAS)

### "¿Por qué usaste Spring Boot?"

**Respuesta:**
> "Spring Boot automatiza la configuración. No tengo que configurar manualmente Tomcat, JPA, o las conexiones de base de datos. Con unas pocas anotaciones (`@SpringBootApplication`, `@Entity`), tengo toda la infraestructura lista. Además, es el estándar de la industria para aplicaciones Java empresariales."

---

### "Explica el flujo de crear un paciente"

**Respuesta (usa el diagrama):**
> "El usuario llena el formulario HTML → JavaScript hace fetch() POST → Spring Boot recibe en PacienteController → Controller llama a PacienteService → Service valida (email único) → Service llama a PacienteRepository → Repository ejecuta save() → JPA genera INSERT SQL → H2 guarda en disco → Respuesta vuelve con el ID generado → JavaScript actualiza la tabla."

---

### "¿Qué es JPA?"

**Respuesta:**
> "Java Persistence API. Es una especificación que define cómo mapear objetos Java a tablas relacionales. En lugar de escribir SQL, trabajo con objetos. Por ejemplo, `pacienteRepository.save(paciente)` genera automáticamente el INSERT. Hibernate es la implementación de JPA que estoy usando."

---

### "¿Qué son las anotaciones @Entity, @Service, @RestController?"

**Respuesta:**
> "`@Entity` le dice a JPA que esta clase es una tabla. `@Service` marca una clase como lógica de negocio y la gestiona Spring. `@RestController` marca una clase como controlador REST que responde JSON. Son metadatos que Spring lee para configurar automáticamente la aplicación."

---

### "¿Por qué necesitas la capa Service?"

**Respuesta:**
> "El Service contiene la lógica de negocio y las validaciones. Por ejemplo, verificar que el email sea único antes de guardar. Separo esto del Controller porque la lógica de negocio puede usarse desde múltiples lugares y facilita el testing. El Controller solo recibe peticiones HTTP, el Service decide QUÉ hacer."

---

### "Muéstrame cómo se relacionan Turno, Paciente y Odontólogo en la BD"

**Respuesta (ve a H2 console):**
> "En la tabla TURNOS, vemos las columnas PACIENTE_ID y ODONTOLOGO_ID. Estas son claves foráneas. Un turno tiene @ManyToOne con Paciente, lo que significa que muchos turnos pueden tener el mismo paciente. En Java es `@ManyToOne private Paciente paciente`, y JPA lo traduce a la foreign key en SQL."

**Ejecuta:**
```sql
SELECT * FROM TURNOS;
-- Muestra los IDs foráneos

SELECT T.*, P.NOMBRE, O.MATRICULA 
FROM TURNOS T
JOIN PACIENTES P ON T.PACIENTE_ID = P.ID
JOIN ODONTOLOGOS O ON T.ODONTOLOGO_ID = O.ID;
-- Muestra el JOIN
```

---

### "¿Cómo harías para paginar los resultados?"

**Respuesta:**
> "Spring Data JPA tiene soporte para paginación. Cambiaría el método del Repository a:
> ```java
> Page<Paciente> findAll(Pageable pageable);
> ```
> Y en el Controller:
> ```java
> @GetMapping
> public Page<Paciente> listar(
>     @RequestParam(defaultValue = "0") int page,
>     @RequestParam(defaultValue = "10") int size
> ) {
>     return pacienteService.buscarPacientes(
>         PageRequest.of(page, size)
>     );
> }
> ```
> Esto devolvería 10 pacientes por página."

---

### "¿Qué mejoras le harías al proyecto?"

**Respuesta:**
> "Varias cosas:
> 1. **DTOs** para separar lo que se guarda de lo que se envía
> 2. **@Valid** con Bean Validation para validaciones automáticas
> 3. **@ControllerAdvice** para manejo global de excepciones
> 4. **Spring Security** para autenticación
> 5. **Tests unitarios e integración** más completos
> 6. **Swagger/OpenAPI** para documentar las APIs
> 7. Migrar a **PostgreSQL** para producción
> 8. **Docker** para containerizar la app"

---

## 🆘 RESOLUCIÓN DE PROBLEMAS

### Problema: "Port 8080 already in use"

**Causa:** Otro programa usa el puerto 8080

**Solución 1:** Cambiar puerto en `application.properties`:
```properties
server.port=8081
```

**Solución 2:** Detener el proceso que usa 8080:
```powershell
# Ver qué usa el puerto
netstat -ano | findstr :8080

# Matar el proceso (reemplaza PID con el número que viste)
taskkill /PID [PID] /F
```

---

### Problema: "Cannot find symbol: class ..."

**Causa:** Error de compilación, falta importar algo

**Solución:**
```bash
# Limpiar y recompilar
mvn clean compile
```

---

### Problema: La página no carga

**Checklist:**
1. ¿El servidor está corriendo? → Mira la consola
2. ¿La URL es correcta? → `http://localhost:8080`
3. ¿Usaste `https`? → Debe ser `http`
4. ¿Firewall bloqueando? → Desactiva temporalmente
5. ¿Otro navegador funciona? → Prueba Chrome/Firefox

---

### Problema: Los datos no se guardan

**Checklist:**
1. ¿Aparece error en consola? → Lee el mensaje
2. ¿La validación falla? → Email duplicado, campos vacíos
3. ¿Abres JavaScript Console? → `F12` en el navegador
4. ¿La API responde? → Prueba con Postman

---

### Problema: "No se encontró paciente con ID X"

**Causa:** Intentas buscar/editar/eliminar un ID que no existe

**Solución:**
- Ve a H2 console
- Ejecuta: `SELECT ID FROM PACIENTES;`
- Usa un ID que exista

---

## 📝 NOTAS FINALES

### Antes de entregar/defender:

✅ **Practica la demostración al menos 2 veces**
- Primera vez: siguiendo esta guía
- Segunda vez: sin mirar la guía

✅ **Prepara tu entorno:**
- Proyecto compilado
- Postman con requests guardados
- H2 console configurada
- URLs en favoritos del navegador

✅ **Ten a mano:**
- Esta guía impresa o en segundo monitor
- Diagrama de arquitectura
- Lista de endpoints

✅ **Sé honesto:**
- Si no sabes algo, admítelo
- Muestra lo que SÍ sabes bien
- Demuestra voluntad de aprender

---

## 🎬 SCRIPT DE DEMOSTRACIÓN (5 minutos)

**Minuto 0-1: Introducción**
> "Buenos días/tardes. Implementé un sistema de gestión para la clínica 'Sonrisa Feliz'. Permite gestionar pacientes, odontólogos y turnos. Usé Spring Boot con arquitectura en capas, JPA para persistencia, y H2 como base de datos. La interfaz es con HTML y Bootstrap."

**Minuto 1-2: Mostrar interfaz principal**
> "Esta es la página principal [navegar]. Tengo tres módulos. Voy a demostrar pacientes."

**Minuto 2-3: Crear paciente**
> "Lleno el formulario... [crear paciente]. Al hacer click, JavaScript envía JSON al backend, Spring Boot valida y guarda en H2. Y aparece en la tabla con su ID."

**Minuto 3-4: Mostrar código**
> "Aquí está el Controller [abrir PacienteController]. Este método recibe POST. Llama al Service [abrir], que valida email único y llama al Repository [abrir], que extiende JpaRepository y genera SQL automáticamente."

**Minuto 4-5: H2 Console**
> "Y aquí está en la base de datos [abrir H2, ejecutar SELECT]. Vemos el paciente que acabamos de crear con todos sus datos."

---

## ✨ PALABRAS DE ÁNIMO

Has trabajado mucho en este proyecto. Conoces la arquitectura, las tecnologías, y el flujo de datos. 

**Recuerda:**
- Es imposible saber TODO
- Está bien decir "no lo implementé pero sé que se podría hacer con..."
- Tu claridad al explicar vale más que memorizar todo
- La práctica te dará confianza

**Respira, sonríe, y demuestra lo que sabes.** 

**¡Vas a hacerlo increíble!** 🌟💪

---

**Última verificación antes del examen:**
```bash
# 1. Compilar
mvn clean compile

# 2. Ejecutar
mvn spring-boot:run

# 3. Abrir navegador
http://localhost:8080

# 4. Hacer una prueba rápida
# Crear un paciente, ver que aparece en la tabla

# 5. ¡Listo para defender!
```

🍀 **¡MUCHA SUERTE!** 🍀
