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
    telefono VARCHAR(20)
);

-- Tabla Cliente
CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    gimnasio_id INT NOT NULL,
    FOREIGN KEY (gimnasio_id) REFERENCES gimnasio(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Tabla Entrenador
CREATE TABLE entrenador (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    especialidad VARCHAR(100),
    gimnasio_id INT NOT NULL,
    FOREIGN KEY (gimnasio_id) REFERENCES gimnasio(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Tabla Reserva
CREATE TABLE reserva (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    entrenador_id INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    duracion INT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (entrenador_id) REFERENCES entrenador(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
ALTER TABLE reserva MODIFY duracion INT DEFAULT 60;

-- Tabla Disponibilidad Entrenador
CREATE TABLE disponibilidad_entrenador (
    id INT AUTO_INCREMENT PRIMARY KEY,
    entrenador_id INT NOT NULL,
    dia_semana ENUM('Lunes','Martes','Miércoles','Jueves','Viernes','Sábado','Domingo') NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    FOREIGN KEY (entrenador_id) REFERENCES entrenador(id)
);

-- datos de prueba
INSERT INTO gimnasio (nombre, direccion, telefono) VALUES
('Gimnasio Central', 'Calle Falsa 123', '123456789'),
('Gimnasio Fitness', 'Avenida Siempre Viva 456', '987654321');

INSERT INTO entrenador (nombre, telefono, especialidad, gimnasio_id) VALUES
('Ana Bultrago', '555123456', 'Musculación', 1),
('Dani Gómez', '555987654', 'Yoga', 2);

INSERT INTO cliente (nombre, email, telefono, gimnasio_id) VALUES
('Juan Pérez', 'juan.perez@email.com', '555111222', 1),
('María López', 'maria.lopez@email.com', '555333444', 2);

INSERT INTO disponibilidad_entrenador (entrenador_id, dia_semana, hora_inicio, hora_fin) VALUES
(1, 'Lunes', '08:00', '12:00'),
(1, 'Lunes', '16:00', '20:00'),
(1, 'Martes', '08:00', '12:00'),
(1, 'Martes', '16:00', '20:00'),
(1, 'Miércoles', '08:00', '12:00'),
(1, 'Miércoles', '16:00', '20:00'),
(1, 'Jueves', '08:00', '12:00'),
(1, 'Jueves', '16:00', '20:00'),
(1, 'Viernes', '08:00', '12:00'),
(1, 'Viernes', '16:00', '20:00');
INSERT INTO disponibilidad_entrenador (entrenador_id, dia_semana, hora_inicio, hora_fin) VALUES
(2, 'Lunes', '08:00', '12:00'),
(2, 'Martes', '08:00', '12:00'),
(2, 'Miércoles', '08:00', '12:00'),
(2, 'Jueves', '08:00', '12:00');


INSERT INTO reserva (cliente_id, entrenador_id, fecha, hora, duracion) VALUES
(1, 1, '2025-06-01', '10:00:00', 60),
(2, 2, '2025-06-02', '11:00:00', 60),
(1, 2, '2025-06-03', '09:00:00', 60),
(2, 1, '2025-06-04', '14:00:00', 60);