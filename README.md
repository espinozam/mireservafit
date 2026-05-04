# MiReservaFit

Aplicación web para la **gestión de reservas de entrenamientos personales**, orientada a digitalizar y optimizar la organización de sesiones entre clientes y entrenadores.

## Tabla de contenidos

- [Sobre el proyecto](#sobre-el-proyecto)
- [Funcionalidades](#funcionalidades)
- [Arquitectura](#arquitectura)
- [Tecnologías](#tecnologías)
- [Diagramas](#diagramas)
- [Instalación](#instalación)
- [Documentación técnica](#documentación-técnica)
- [API REST (Swagger)](#api-rest-swagger)
- [Licencia](#licencia)
- [Autor](#autor)

## Sobre el proyecto

**MiReservaFit** es una plataforma web que permite gestionar reservas de entrenamientos personales de forma centralizada, evitando problemas habituales como:

- Solapamientos de horarios
- Falta de organización
- Uso de herramientas no especializadas (WhatsApp, notas, agendas)

El sistema proporciona una solución digital eficiente, accesible desde navegador y diseñada para mejorar la experiencia tanto de clientes como entrenadores.

## Funcionalidades

### Autenticación y usuarios
- Registro y login con contraseñas cifradas (BCrypt)
- Control de acceso por roles (CLIENTE / ENTRENADOR)
- Gestión de sesión mediante HttpSession

### Gestión de reservas
- Creación, listado y cancelación de reservas
- Validación de solapamientos
- Restricción por horario laboral
- Cada cliente gestiona únicamente sus reservas

### Entrenadores
- Agenda semanal
- Visualización de sesiones
- Estadísticas básicas de actividad

## Arquitectura

El sistema sigue una arquitectura **cliente-servidor en 3 capas**, que garantiza mantenibilidad y escalabilidad:

- **Frontend (Presentación)** → Angular
- **Backend (Lógica de negocio)** → Java + Spring Boot
- **Base de datos (Persistencia)** → MySQL

Esta estructura permite separar responsabilidades y facilitar la evolución del sistema.

## Tecnologías

| Capa | Tecnología | Descripción |
|------|-----------|------------|
| Backend | Java 21 / Spring Boot | API REST y lógica de negocio |
| Frontend | Angular | SPA con TypeScript |
| Base de datos | MySQL | Persistencia relacional |
| ORM | Hibernate / JPA | Mapeo objeto-relacional |
| Seguridad | BCrypt | Cifrado de contraseñas |
| Contenedores | Docker | Entorno reproducible |

## Diagramas

El proyecto incluye documentación UML:

- Diagrama de casos de uso
- Diagrama de clases
- Diagramas de secuencia
- Diagrama de arquitectura

Ubicación: "./docs/uml/"

## Instalación

### Opción 1: GitHub Codespaces

1. Abrir el repositorio en Codespaces
2. Ejecutar backend:

```bash
cd backend
mvn spring-boot:run
```

3. Ejecutar frontend:

```bash
cd frontend
ng serve
```

### Opción 2: Instalación local con Docker

1. Clonar repositorio:

```bash
git clone https://github.com/espinozam/mireservafit.git
cd mireservafit
```

2. Iniciar base de datos:

```bash
docker compose up -d
```

3. Ejecutar backend:

```bash
cd backend
mvn spring-boot:run
```

4. Ejecutar frontend:

```bash
cd frontend
ng serve
```

La aplicación se servirá en http://localhost:4200 y consumirá la API REST del backend en http://localhost:8080.

## Documentación técnica

Incluye:

* Arquitectura del sistema
* Diagramas UML
* Diseño de base de datos
* Análisis de requisitos

Disponible en /docs

## API REST (Swagger)

Incluye:

* Endpoints REST
* Modelos de datos
* Validaciones

Disponible en - http://localhost:8080/swagger-ui/index.html

## Licencia

* 💻 Código: MIT License
* 📄 Documentación: CC BY 4.0

## Autor

**Edwin Espinoza Mercado**

📧 [eespinozamercado@cifpfbmoll.eu](mailto:eespinozamercado@cifpfbmoll.eu)

GitHub [@espinozam](https://github.com/espinozam)
