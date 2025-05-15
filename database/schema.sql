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
