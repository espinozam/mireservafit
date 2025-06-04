-- Eliminar base de datos si ya existe
DROP DATABASE IF EXISTS mireservafit_db;

-- Crear nueva base de datos
CREATE DATABASE mireservafit_db;
USE mireservafit_db;

-- Tabla Gimnasio
CREATE TABLE gimnasio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(20),
    email VARCHAR(100) -- Añadido para coincidir con la clase Gimnasio
);

-- Tabla Cliente
CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(20), -- Añadido para coincidir con la clase Cliente/Persona
    nombre VARCHAR(100) NOT NULL,
    apellido1 VARCHAR(100), -- Añadido para coincidir con la clase Cliente/Persona
    apellido2 VARCHAR(100), -- Añadido para coincidir con la clase Cliente/Persona
    edad INT,               -- Añadido para coincidir con la clase Cliente/Persona
    email VARCHAR(100) NOT NULL,
    contrasena VARCHAR(100), -- Añadido para coincidir con la clase Cliente/Persona
    direccion VARCHAR(255),  -- Añadido para coincidir con la clase Cliente
    telefono VARCHAR(20),
    gimnasio_id INT,
    FOREIGN KEY (gimnasio_id) REFERENCES gimnasio(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

-- Tabla Entrenador
CREATE TABLE entrenador (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(20), -- Añadido para coincidir con la clase Entrenador/Persona
    nombre VARCHAR(100) NOT NULL,
    apellido1 VARCHAR(100), -- Añadido para coincidir con la clase Entrenador/Persona
    apellido2 VARCHAR(100), -- Añadido para coincidir con la clase Entrenador/Persona
    edad INT,               -- Añadido para coincidir con la clase Entrenador/Persona
    email VARCHAR(100),
    contrasena VARCHAR(100),
    telefono VARCHAR(20),
    especialidad VARCHAR(100),
    gimnasio_id INT,
    FOREIGN KEY (gimnasio_id) REFERENCES gimnasio(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

-- Tabla Reserva
CREATE TABLE reserva (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    entrenador_id INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    duracion INT DEFAULT 60,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (entrenador_id) REFERENCES entrenador(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Tabla Disponibilidad Entrenador
CREATE TABLE disponibilidad_entrenador (
    id INT AUTO_INCREMENT PRIMARY KEY,
    entrenador_id INT NOT NULL,
    dia_semana ENUM('Lunes','Martes','Miércoles','Jueves','Viernes','Sábado','Domingo') NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    FOREIGN KEY (entrenador_id) REFERENCES entrenador(id)
);

-- Datos de prueba

-- Gimnasios
INSERT INTO gimnasio (nombre, direccion, telefono, email) VALUES
('Gimnasio Central', 'Calle Falsa 123', '123456789', 'central@gym.com'),
('Gimnasio Fitness', 'Avenida Siempre Viva 456', '987654321', 'fitness@gym.com');

-- Entrenadores (uno con gimnasio, uno sin gimnasio)
INSERT INTO entrenador (dni, nombre, apellido1, apellido2, edad, email, contrasena, telefono, especialidad, gimnasio_id) VALUES
('11111111A', 'Ana', 'Bultrago', '', 35, 'ana@gym.com', 'Jyjbbjvjj1', '555123456', 'Musculación', 1),
('22222222B', 'Dani', 'Gómez', '', 29, 'dani@gym.com', 'Gvgyyjgsññ1', '555987654', 'Yoga', 2),
('33333333C', 'Entrenador Libre', '', '', 40, 'libre@gym.com', 'Crcuunkdtg', '555000111', 'Pilates', NULL);

-- Clientes (uno con gimnasio, uno sin gimnasio)
INSERT INTO cliente (dni, nombre, apellido1, apellido2, edad, email, contrasena, direccion, telefono, gimnasio_id) VALUES
('44444444D', 'Juan Pérez', 'Pérez', '', 28, 'juan.perez@email.com', 'Ncnffvhnz', 'Calle Uno', '555111222', 1),
('55555555E', 'María López', 'López', '', 31, 'maria.lopez@email.com', 'Ncnffyneun', 'Calle Dos', '555333444', 2),
('66666666F', 'Cliente Independiente', '', '', 22, 'indep@correo.com', 'Rhrkkzeuvh', 'Calle Libre', '555999888', NULL);

-- Disponibilidad de entrenadores
INSERT INTO disponibilidad_entrenador (entrenador_id, dia_semana, hora_inicio, hora_fin) VALUES
(1, 'Lunes', '08:00', '12:00'),
(1, 'Lunes', '16:00', '20:00'),
(1, 'Martes', '08:00', '12:00'),
(1, 'Martes', '16:00', '20:00'),
(2, 'Miércoles', '08:00', '12:00'),
(2, 'Jueves', '08:00', '12:00'),
(3, 'Viernes', '10:00', '14:00'); -- Entrenador sin gimnasio

-- Reservas (solo para clientes y entrenadores con gimnasio)
INSERT INTO reserva (cliente_id, entrenador_id, fecha, hora, duracion) VALUES
(1, 1, '2025-06-01', '10:00:00', 60),
(2, 2, '2025-06-02', '11:00:00', 60),
(1, 2, '2025-06-03', '09:00:00', 60),
(2, 1, '2025-06-04', '14:00:00', 60);