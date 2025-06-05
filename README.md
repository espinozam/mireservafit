# mireservafit

Aplicación web para gestionar reservas de sesiones de entrenamiento en un gimnasio.

## Descripción

mireservafit es una aplicación web que permite a clientes y entrenadores gestionar reservas de sesiones, consultar disponibilidad, y administrar usuarios y gimnasios. Incluye áreas diferenciadas para clientes, entrenadores y administración.

---

## Características principales

- **Registro y login** para clientes y entrenadores.
- **Gestión de reservas**: crear, editar, eliminar y consultar reservas.
- **Panel de cliente**: ver y gestionar reservas propias.
- **Panel de entrenador**: ver y gestionar reservas asignadas.
- **Gestión de entrenadores y clientes** (solo administración).
- **Gestión de gimnasios** y disponibilidad de entrenadores.
- **Frontend sin frameworks CSS** (HTML y JavaScript puro).
- **Backend en Java con Servlets** y conexión a base de datos MySQL.

---

## Estructura del proyecto

mireservafit/ │ ├── backend/ │ ├── Bbdd.java │ ├── ReservaListServlet.java │ ├── ReservaClienteServlet.java │ ├── ReservaEntrenadorServlet.java │ ├── ReservaAddServlet.java │ ├── ReservaUpdateServlet.java │ ├── ReservaDeleteServlet.java │ ├── EntrenadorListServlet.java │ ├── EntrenadorIdServlet.java │ ├── ClienteListServlet.java │ └── ...otros servlets y modelos │ ├── frontend/ │ ├── index.html │ ├── cliente.html │ ├── entrenadorArea.html │ ├── entrenadores.html │ ├── js/ │ │ ├── clienteArea.js │ │ ├── entrenadorArea.js │ │ ├── reservas.js │ │ ├── entrenadores.js │ │ └── config.js │ └── ...otros recursos │ ├── database/ │ └── schema.sql │ └──

---

## Instalación y configuración

1. **Clona el repositorio**

git clone https://github.com/tuusuario/mireservafit.git

2. **Configura la base de datos**  
- Crea una base de datos MySQL.
- Ejecuta el script `database/schema.sql` para crear las tablas y datos de prueba.

3. **Configura la conexión en `Bbdd.java`**  
- Ajusta los parámetros de conexión (`usuario`, `contraseña`, `url`) según tu entorno.

4. **Despliega el backend**  
- Compila los servlets y despliega en un servidor compatible con Jakarta EE (Tomcat recomendado).

5. **Abre el frontend**  
- Accede a los archivos HTML desde un navegador o configúralos en un servidor web.

---

## Documentación de los principales servlets

### ReservaListServlet

Permite obtener una tabla HTML con todas las reservas o solo las de un entrenador.

- **URL:** `/ReservaListServlet`
- **Método:** `GET`
- **Parámetros:**
  - `emailEntrenador` (opcional): Si se incluye, filtra solo las reservas de ese entrenador.
- **Respuesta:** Tabla HTML con reservas y botones de editar/eliminar.

### ReservaClienteServlet

Devuelve las reservas de un cliente específico.

- **URL:** `/ReservaClienteServlet`
- **Método:** `GET`
- **Parámetros:**
  - `email`: Email del cliente.
- **Respuesta:** Tabla HTML con reservas del cliente.

### ReservaEntrenadorServlet

Devuelve las reservas asignadas a un entrenador.

- **URL:** `/ReservaEntrenadorServlet`
- **Método:** `GET`
- **Parámetros:**
  - `email`: Email del entrenador.
- **Respuesta:** Tabla HTML con reservas del entrenador.

### ReservaAddServlet / ReservaUpdateServlet / ReservaDeleteServlet

Permiten crear, actualizar y eliminar reservas respectivamente.

- **ReservaAddServlet**:  
  - **POST** con parámetros: `fecha`, `hora`, `cliente_id`, `entrenador_id`
- **ReservaUpdateServlet**:  
  - **POST** con parámetros: `id`, `fecha`, `hora`, `cliente_id`, `entrenador_id`
- **ReservaDeleteServlet**:  
  - **POST** con parámetro: `id`

### EntrenadorListServlet

Devuelve la lista de entrenadores en formato HTML.

- **URL:** `/EntrenadorListServlet`
- **Método:** `GET`

### EntrenadorIdServlet

Devuelve el id de un entrenador a partir de su email.

- **URL:** `/EntrenadorIdServlet`
- **Método:** `GET`
- **Parámetros:** `email`

---

## Frontend

- **clienteArea.js**: Lógica del área privada de cliente.
- **entrenadorArea.js**: Lógica del área privada de entrenador.
- **reservas.js**: Funciones generales de gestión de reservas.
- **entrenadores.js**: Funciones de gestión de entrenadores (admin).
- **config.js**: Configuración global (ej: `baseURL`).

---

## Créditos

Desarrollado por Edwin Espinoza Mercado.  
Colaboración de Juan Diaz ([Lefty616](https://github.com/Lefty616)).  
Con ayuda de ChatGPT y Microsoft Copilot.

