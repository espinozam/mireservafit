# mireservafit

Aplicación web para gestionar reservas de sesiones de entrenamiento en un gimnasio.

## Descripción

Esta aplicación web permite a clientes y entrenadores gestionar reservas de sesiones, consultar disponibilidad, y administrar usuarios. Incluye áreas para clientes, entrenadores y administración.

---

## Características principales

- **Panel de administración** Gestión de entrenadores, clientes y reservas.
- **Registro y login** para clientes y entrenadores.
- **Gestión de reservas**: crear, editar, eliminar y consultar reservas.
- **Panel de cliente**: ver y gestionar reservas propias.
- **Panel de entrenador**: ver y gestionar reservas asignadas.
- **Gestión de gimnasios** y disponibilidad de entrenadores.
- **Frontend** Usando HTML y JavaScript puro.
- **Backend en Java con Servlets** y conexión a base de datos MySQL.

---

## Estructura del proyecto

mireservafit/
├── backend/                        # Lógica del servidor en Java (Servlets y modelos)
│   ├── Bbdd.java                   # Clase para la conexión con la base de datos
│   ├── Reserva*.java               # Servlets para operaciones CRUD de reservas
│   ├── Cliente*.java               # Servlets para operaciones con clientes
│   ├── Entrenador*.java            # Servlets para operaciones con entrenadores
│   └── ...                         # Otros modelos y controladores
│
├── frontend/                       # Interfaz de usuario
│   ├── index.html                  # Página principal
│   ├── cliente.html                # Área privada del cliente
│   ├── entrenadorArea.html         # Área privada del entrenador
│   ├── entrenadores.html           # Página pública de entrenadores disponibles
│   ├── admin.html                  # Área de administración del sistema
│   ├── js/                         # Scripts JavaScript del frontend
│   │   ├── clienteArea.js          # Lógica para la interacción del cliente con sus reservas
│   │   ├── entrenadorArea.js       # Gestión de reservas del entrenador
│   │   ├── reservas.js             # Funciones comunes para crear, actualizar y eliminar reservas
│   │   ├── entrenadores.js         # Carga y visualización de entrenadores en la página pública
│   │   └── config.js               # Configuración general, como URLs de la API
│   └── ...                         # Otros recursos (CSS, imágenes, etc.)
│
├── database/                       # Scripts de base de datos
│   └── schema.sql                  # Script SQL para crear las tablas necesarias

---

## Requisitos

- **Java JDK 17** o superior
- **Eclipse IDE for Enterprise Java and Web Developers** (o cualquier IDE compatible con proyectos Jakarta EE)
- **Apache Tomcat v10.1** (o compatible con Jakarta EE 10)
- **MySQL Server** (recomendado MySQL 8 o superior)
- **mysql-connector-j-9.2.0.jar** (añadir al classpath del proyecto)
- **servlet-api.jar** (incluido en la carpeta `lib` de tu instalación de Tomcat `C:\apache-tomcat-10.1.28\lib`)
- Navegador web moderno (Chrome, Firefox, Edge, etc.)

---

## Instalación y configuración

1. **Clona el repositorio**

git clone https://github.com/tuusuario/mireservafit.git

2. **Configura la base de datos**  
- Crea una base de datos MySQL.
- Ejecuta el script `database/schema.sql` para crear las tablas y datos de prueba.

3. **Configura la conexión en `Bbdd.java`**  
- Ajusta los parámetros de conexión (`usuario`, `contraseña`, `url`).

4. **Despliega el backend**  
- Compila los servlets y despliega en un servidor compatible con Jakarta EE (Tomcat recomendado).
> **Nota:**  
> Para compilar y ejecutar los archivos `.java` del backend, primero debes crear manualmente las clases y servlets en tu proyecto Java Web usando un IDE como **Eclipse IDE for Enterprise Java and Web Developers**.  
> Luego, copia y pega el código proporcionado en los archivos `.java` correspondientes dentro de tu estructura de proyecto en Eclipse.  
> Esto es necesario porque Eclipse gestiona los paquetes, rutas y configuración del proyecto, y no es suficiente con solo copiar los archivos al sistema de archivos.

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





