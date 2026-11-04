-- Private Zone Wellness: crea la base de datos, el usuario, las tablas y los datos de ejemplo.
-- Se puede ejecutar varias veces: recrea todo desde cero (también reinicia los datos).

SET NAMES utf8mb4;

DROP DATABASE IF EXISTS private_zone;
CREATE DATABASE private_zone CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Credenciales solo para desarrollo local
DROP USER IF EXISTS 'usuario_private_zone'@'localhost';
CREATE USER 'usuario_private_zone'@'localhost' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON private_zone.* TO 'usuario_private_zone'@'localhost';

USE private_zone;

-- ---------- Tablas ----------

CREATE TABLE categoria (
    id BIGINT NOT NULL AUTO_INCREMENT,
    area VARCHAR(10) NOT NULL,
    nombre VARCHAR(80) NOT NULL,
    descripcion VARCHAR(200),
    precio_desde INT NOT NULL,
    icono VARCHAR(40),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE rol (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(80) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_rol_nombre (nombre)
) ENGINE=InnoDB;

CREATE TABLE colaborador (
    id BIGINT NOT NULL AUTO_INCREMENT,
    area VARCHAR(10) NOT NULL,
    nombre VARCHAR(80) NOT NULL,
    rol_id BIGINT,
    estado VARCHAR(10) NOT NULL DEFAULT 'ACTIVO',
    iniciales VARCHAR(4) NOT NULL,
    correo VARCHAR(100),
    password VARCHAR(100),
    PRIMARY KEY (id),
    UNIQUE KEY uk_colaborador_correo (correo),
    CONSTRAINT fk_colaborador_rol FOREIGN KEY (rol_id) REFERENCES rol (id)
) ENGINE=InnoDB;

CREATE TABLE promocion (
    id BIGINT NOT NULL AUTO_INCREMENT,
    etiqueta VARCHAR(60) NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    descripcion VARCHAR(300),
    precio INT NOT NULL,
    precio_anterior INT,
    icono VARCHAR(40) DEFAULT 'bi-stars',
    activa TINYINT(1) NOT NULL DEFAULT 1,
    orden INT DEFAULT 0,
    fecha_inicio DATE,
    fecha_fin DATE,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE producto (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(80) NOT NULL,
    descripcion VARCHAR(200),
    precio INT NOT NULL,
    disponible TINYINT(1) NOT NULL DEFAULT 1,
    para_llevar TINYINT(1) NOT NULL DEFAULT 1,
    imagen_url VARCHAR(300),
    categoria_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_producto_categoria FOREIGN KEY (categoria_id) REFERENCES categoria (id)
) ENGINE=InnoDB;

CREATE TABLE alergeno (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(60) NOT NULL,
    producto_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_alergeno_producto FOREIGN KEY (producto_id) REFERENCES producto (id)
) ENGINE=InnoDB;

CREATE TABLE servicio (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(80) NOT NULL,
    descripcion VARCHAR(200),
    duracion_minutos INT NOT NULL,
    precio INT NOT NULL,
    disponible TINYINT(1) NOT NULL DEFAULT 1,
    categoria_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_servicio_categoria FOREIGN KEY (categoria_id) REFERENCES categoria (id)
) ENGINE=InnoDB;

CREATE TABLE servicio_especialista (
    servicio_id BIGINT NOT NULL,
    colaborador_id BIGINT NOT NULL,
    PRIMARY KEY (servicio_id, colaborador_id),
    CONSTRAINT fk_servesp_servicio FOREIGN KEY (servicio_id) REFERENCES servicio (id),
    CONSTRAINT fk_servesp_colaborador FOREIGN KEY (colaborador_id) REFERENCES colaborador (id)
) ENGINE=InnoDB;

CREATE TABLE cita (
    id BIGINT NOT NULL AUTO_INCREMENT,
    servicio_id BIGINT NOT NULL,
    especialista_id BIGINT,
    nombre VARCHAR(80) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    PRIMARY KEY (id),
    CONSTRAINT fk_cita_servicio FOREIGN KEY (servicio_id) REFERENCES servicio (id),
    CONSTRAINT fk_cita_especialista FOREIGN KEY (especialista_id) REFERENCES colaborador (id)
) ENGINE=InnoDB;

CREATE TABLE reserva_mesa (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(80) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    personas INT NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- ---------- Datos de ejemplo ----------

INSERT INTO categoria (area, nombre, descripcion, precio_desde, icono) VALUES
('CAFE', 'Espresso y métodos', 'Espresso, V60 y prensa francesa con café de altura.', 1800, 'bi-cup-hot'),
('CAFE', 'Desayunos y brunch', 'Gallo pinto de la casa, bowls y tostadas artesanales.', 4500, 'bi-egg-fried'),
('CAFE', 'Repostería artesanal', 'Queques, croissants y postres horneados cada mañana.', 2200, 'bi-cake2'),
('SALON', 'Corte y Estilismo', 'Cortes, peinados y asesoría según tu tipo de cabello.', 8000, 'bi-scissors'),
('SALON', 'Barbería y Peluquería', 'Corte clásico, fade, perfilado y arreglo de barba.', 6500, 'bi-person-badge'),
('SALON', 'Uñas', 'Manicura, pedicura, gel y diseños personalizados.', 7000, 'bi-stars'),
('SALON', 'Tratamientos Faciales', 'Limpieza profunda, hidratación y cuidado de la piel.', 18000, 'bi-flower2');

INSERT INTO rol (nombre) VALUES
('Administrador'),
('Barista principal'),
('Barista'),
('Estilista'),
('Especialista en uñas');

INSERT INTO colaborador (area, nombre, rol_id, estado, iniciales) VALUES
('CAFE', 'Sendry Hernandez', (SELECT id FROM rol WHERE nombre = 'Barista principal'), 'ACTIVO', 'SH'),
('CAFE', 'Hellen Mora', (SELECT id FROM rol WHERE nombre = 'Barista'), 'ACTIVO', 'HM'),
('SALON', 'Daysi Araya', (SELECT id FROM rol WHERE nombre = 'Estilista'), 'ACTIVO', 'DA'),
('SALON', 'Beverly Chavarria', (SELECT id FROM rol WHERE nombre = 'Especialista en uñas'), 'ACTIVO', 'BC'),
('SALON', 'Roxana Alfaro', (SELECT id FROM rol WHERE nombre = 'Estilista'), 'ACTIVO', 'RA');

-- Usuario administrador de prueba para el Portal de colaboradores (solo desarrollo local).
--   Correo:     admin@privatezone.cr
--   Contraseña: PrivateZone#2026
-- La contraseña se guarda como hash BCrypt. El área ADMIN lo deja fuera de las listas públicas de baristas y especialistas.
INSERT INTO colaborador (area, nombre, rol_id, estado, iniciales, correo, password) VALUES
('ADMIN', 'Administrador', (SELECT id FROM rol WHERE nombre = 'Administrador'), 'ACTIVO', 'AD', 'admin@privatezone.cr', '$2a$10$X1YPnBCknh5DLVRFAq7KheyPKh0kpF9jGw5wGFSeZPLeNc.nscKfW');

INSERT INTO promocion (etiqueta, titulo, descripcion, precio, precio_anterior, icono, activa, orden, fecha_inicio, fecha_fin) VALUES
('Café + Salón · Combo', 'Desayuno + Manicura Express',
 'Empezá el día con un desayuno de la casa y salí con las uñas listas. Todo en una sola visita.',
 14500, 18000, 'bi-stars', 1, 1, NULL, NULL),
('Café · Temporada', 'Cold brew de la casa',
 'Nuestro café frío de lote pequeño, infusionado por 18 horas. Llévalo o disfrútalo aquí.',
 2900, NULL, 'bi-cup-straw', 1, 2, NULL, NULL),
('Salón · Bienestar', 'Limpieza facial + masaje relajante',
 'Un ritual de una hora para renovar tu piel y soltar la tensión, con café de cortesía.',
 32000, 40000, 'bi-flower2', 1, 3, NULL, NULL);

-- Datos de ejemplo: promoción con fecha de fin pasada para verificar el filtro de vigencia
INSERT INTO promocion (etiqueta, titulo, descripcion, precio, precio_anterior, icono, activa, orden, fecha_inicio, fecha_fin) VALUES
('Demo · Vencida', 'Promoción vencida (demo)',
 'Datos de ejemplo: no debe aparecer en el carrusel porque su fecha de fin ya pasó.',
 1000, NULL, 'bi-stars', 1, 4, NULL, DATE_SUB(CURDATE(), INTERVAL 7 DAY));

INSERT INTO producto (nombre, descripcion, precio, disponible, para_llevar, categoria_id) VALUES
('Espresso doble', 'Shot doble de Tarrazú, notas de chocolate oscuro y caramelo.', 1800, 1, 1,
 (SELECT id FROM categoria WHERE nombre = 'Espresso y métodos')),
('Capuchino de la casa', 'Espresso doble con leche texturizada y un toque de canela.', 2600, 1, 1,
 (SELECT id FROM categoria WHERE nombre = 'Espresso y métodos')),
('Latte de vainilla', 'Espresso suave, leche cremosa y vainilla natural.', 2900, 1, 1,
 (SELECT id FROM categoria WHERE nombre = 'Espresso y métodos')),
('Gallo pinto de la casa', 'Con huevo, natilla y plátano.', 4500, 1, 1,
 (SELECT id FROM categoria WHERE nombre = 'Desayunos y brunch')),
('Croissant de almendra', 'Horneado cada mañana.', 2200, 1, 1,
 (SELECT id FROM categoria WHERE nombre = 'Repostería artesanal'));

INSERT INTO alergeno (nombre, producto_id) VALUES
('lácteos', (SELECT id FROM producto WHERE nombre = 'Capuchino de la casa')),
('lácteos', (SELECT id FROM producto WHERE nombre = 'Latte de vainilla'));

-- Datos de ejemplo: servicios de demostración; el más barato de cada categoría coincide con su precio_desde
INSERT INTO servicio (nombre, descripcion, duracion_minutos, precio, disponible, categoria_id) VALUES
('Corte de cabello', 'Corte según tu tipo de cabello.', 45, 8000, 1,
 (SELECT id FROM categoria WHERE nombre = 'Corte y Estilismo')),
('Peinado', 'Peinado para el día o para una ocasión especial.', 45, 10000, 1,
 (SELECT id FROM categoria WHERE nombre = 'Corte y Estilismo')),
('Corte y peinado', 'Corte con peinado incluido.', 75, 15000, 1,
 (SELECT id FROM categoria WHERE nombre = 'Corte y Estilismo')),
('Corte clásico', 'Corte clásico con perfilado.', 30, 6500, 1,
 (SELECT id FROM categoria WHERE nombre = 'Barbería y Peluquería')),
('Arreglo de barba', 'Perfilado y arreglo de barba.', 30, 7000, 1,
 (SELECT id FROM categoria WHERE nombre = 'Barbería y Peluquería')),
('Corte y barba', 'Corte clásico más arreglo de barba.', 60, 11000, 1,
 (SELECT id FROM categoria WHERE nombre = 'Barbería y Peluquería')),
('Manicura', 'Manicura con esmaltado.', 45, 7000, 1,
 (SELECT id FROM categoria WHERE nombre = 'Uñas')),
('Pedicura', 'Pedicura con esmaltado.', 60, 9000, 1,
 (SELECT id FROM categoria WHERE nombre = 'Uñas')),
('Uñas en gel', 'Aplicación de gel con diseño sencillo.', 90, 15000, 1,
 (SELECT id FROM categoria WHERE nombre = 'Uñas')),
('Limpieza facial profunda', 'Limpieza profunda de la piel.', 60, 18000, 1,
 (SELECT id FROM categoria WHERE nombre = 'Tratamientos Faciales')),
('Tratamiento hidratante', 'Hidratación y cuidado de la piel.', 60, 22000, 1,
 (SELECT id FROM categoria WHERE nombre = 'Tratamientos Faciales'));

-- Especialistas: estilistas en corte y barbería, especialista en uñas en uñas; los faciales quedan sin especialistas
INSERT INTO servicio_especialista (servicio_id, colaborador_id)
SELECT s.id, c.id
FROM servicio s
JOIN categoria cat ON cat.id = s.categoria_id
JOIN colaborador c ON c.area = 'SALON'
JOIN rol r ON r.id = c.rol_id
WHERE (cat.nombre IN ('Corte y Estilismo', 'Barbería y Peluquería') AND r.nombre = 'Estilista')
   OR (cat.nombre = 'Uñas' AND r.nombre = 'Especialista en uñas');
