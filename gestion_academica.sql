DROP DATABASE IF EXISTS gestion_academica;
CREATE DATABASE gestion_academica CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE gestion_academica;

-- -----------------------------------------------------
-- Tabla: profesor
-- -----------------------------------------------------
CREATE TABLE profesor (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nif VARCHAR(9) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    apellido1 VARCHAR(100) NOT NULL,
    apellido2 VARCHAR(100),
    departamento VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- Tabla: grupo (1:N con profesor como tutor)
-- -----------------------------------------------------
CREATE TABLE grupo (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    curso INT NOT NULL,
    tutor_id INT UNSIGNED,
    CONSTRAINT fk_grupo_profesor
        FOREIGN KEY (tutor_id)
        REFERENCES profesor(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- Tabla: alumno (1:N con grupo)
-- -----------------------------------------------------
CREATE TABLE alumno (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nia VARCHAR(15) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    apellido1 VARCHAR(100) NOT NULL,
    apellido2 VARCHAR(100),
    email VARCHAR(150) NOT NULL UNIQUE,
    grupo_id INT UNSIGNED,
    CONSTRAINT fk_alumno_grupo
        FOREIGN KEY (grupo_id)
        REFERENCES grupo(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- Datos iniciales de ejemplo
-- -----------------------------------------------------
INSERT INTO profesor (nif, nombre, apellido1, apellido2, departamento, email) VALUES
('11111111A', 'Elena', 'Martínez', 'Gómez', 'Desarrollo Web', 'elena.martinez@centro.edu'),
('22222222B', 'Carlos', 'Ruiz', 'Sánchez', 'Bases de Datos', 'carlos.ruiz@centro.edu'),
('33333333C', 'Laura', 'Fernández', 'López', 'Sistemas y Redes', 'laura.fernandez@centro.edu'),
('44444444D', 'Javier', 'Navarro', 'Pérez', 'Ingeniería del Software', 'javier.navarro@centro.edu');

INSERT INTO grupo (codigo, nombre, curso, tutor_id) VALUES
('1DAW', 'Desarrollo de Aplicaciones Web - 1º', 1, 3),
('2DAW', 'Desarrollo de Aplicaciones Web - 2º', 2, 1),
('2DAM', 'Desarrollo de Aplicaciones Multiplataforma - 2º', 2, 2);

INSERT INTO alumno (nia, nombre, apellido1, apellido2, email, grupo_id) VALUES
('20260001', 'Lucía', 'García', 'Moreno', 'lucia.garcia@alumnos.edu', 2),
('20260002', 'Adrián', 'Torres', 'Jiménez', 'adrian.torres@alumnos.edu', 2),
('20260003', 'Sofía', 'Romero', 'Díaz', 'sofia.romero@alumnos.edu', 2),
('20260004', 'Hugo', 'Vázquez', 'Castro', 'hugo.vazquez@alumnos.edu', 3),
('20260005', 'Paula', 'Molina', 'Ortiz', 'paula.molina@alumnos.edu', 3),
('20260006', 'Daniel', 'Serrano', 'Rubio', 'daniel.serrano@alumnos.edu', 1),
('20260007', 'Marta', 'Blanco', 'Suárez', 'marta.blanco@alumnos.edu', 1),
('20260008', 'Alejandro', 'Gil', 'Ramos', 'alejandro.gil@alumnos.edu', NULL);