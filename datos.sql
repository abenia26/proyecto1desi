-- datos.sql: datos de prueba de Páginas de Villa Serena
-- Ejecutar después de schema.sql.

USE libreria;

INSERT INTO editorial (nombre, pais, telefono) VALUES
('Alfaguara', 'España', '913 000 111'),
('Alianza', 'España', '913 000 222'),
('Plaza & Janés', 'España', '934 000 333'),
('Cátedra', 'España', '915 000 444');

INSERT INTO autor (nombre, nacionalidad, anio_nacimiento) VALUES
('Julio Cortázar', 'Argentina', 1914),
('Jorge Luis Borges', 'Argentina', 1899),
('Isabel Allende', 'Chile', 1942),
('Juan Rulfo', 'México', 1917);

INSERT INTO libro (isbn, titulo, anio_publicacion, paginas, precio, editorial_id) VALUES
('9788420471839', 'Rayuela', 1963, 600, 16.50, 1),
('9788420633121', 'Ficciones', 1944, 220, 12.00, 2),
('9788401352836', 'Cuentos de Eva Luna', 1989, 320, 14.90, 3),
('9788420674209', 'Antología del cuento', 2005, 280, 18.00, 2),
('9788437604947', 'Pedro Páramo', 1955, 160, 10.00, 4),
('9788420412146', 'Bestiario', 1951, 190, 11.50, 1);

INSERT INTO libro_autor (libro_isbn, autor_id, rol) VALUES
('9788420471839', 1, 'principal'),
('9788420633121', 2, 'principal'),
('9788401352836', 3, 'principal'),
('9788420674209', 1, 'principal'),
('9788420674209', 2, 'colaborador'),
('9788437604947', 4, 'principal'),
('9788420412146', 1, 'principal');

INSERT INTO tienda (nombre, direccion, telefono, ciudad) VALUES
('Centro', 'Calle Mayor 12', '976 000 111', 'Villa Serena'),
('Ribera', 'Avenida del Río 5', '976 000 222', 'Aldeaverde'),
('Universidad', 'Plaza del Campus 1', '976 000 333', 'Villa Serena');

INSERT INTO inventario (tienda_id, libro_isbn, cantidad, fecha_conteo) VALUES
(1, '9788420471839', 3, '2026-03-01'),
(1, '9788420633121', 0, '2026-03-01'),
(1, '9788437604947', 5, '2026-03-01'),
(1, '9788420412146', 2, '2026-03-01'),
(2, '9788420471839', 2, '2026-03-03'),
(2, '9788401352836', 4, '2026-03-03'),
(3, '9788420471839', 4, '2026-03-02'),
(3, '9788420633121', 2, '2026-03-02'),
(3, '9788401352836', 0, '2026-03-02'),
(3, '9788420674209', 6, '2026-02-28');

INSERT INTO empleado (dni, nombre, apellidos, cargo, fecha_contratacion, correo, tienda_id) VALUES
('11111111A', 'Marta', 'López Gil', 'cajero', '2022-09-01', 'marta.lopez@villaserena.es', 1),
('22222222B', 'Pablo', 'Sanz Ruiz', 'encargado', '2019-02-15', 'pablo.sanz@villaserena.es', 2),
('33333333C', 'Lucía', 'Ferrer Mon', 'librero', '2023-01-10', 'lucia.ferrer@villaserena.es', 3);

INSERT INTO cliente (nombre, correo, telefono, es_socio, fecha_alta) VALUES
('Andrés Pérez', 'andres.p@correo.es', '600111222', TRUE, '2025-05-20'),
('Laura Gómez', 'laura.g@correo.es', NULL, FALSE, NULL),
('Sergio Navarro', 'sergio.n@correo.es', '600333444', TRUE, '2026-01-08');

INSERT INTO pedido (id, fecha, forma_pago, estado, tienda_id, empleado_dni, cliente_id) VALUES
(10120, '2025-02-10', 'efectivo', 'entregado', 1, '11111111A', 2),
(10482, '2026-03-12', 'tarjeta', 'entregado', 1, '11111111A', 1),
(10484, '2026-03-14', 'bizum', 'entregado', 2, '22222222B', 1),
(10485, '2026-03-15', 'tarjeta', 'preparado', 3, '33333333C', 3),
(10486, '2026-03-16', 'efectivo', 'cancelado', 3, '33333333C', 2),
(10487, '2026-03-17', 'tarjeta', 'entregado', 1, '11111111A', 1);

INSERT INTO linea_pedido (pedido_id, libro_isbn, cantidad, precio_cobrado) VALUES
(10120, '9788420633121', 1, 10.00),
(10120, '9788437604947', 2, 10.00),
(10482, '9788420471839', 1, 16.50),
(10482, '9788420633121', 2, 12.00),
(10482, '9788437604947', 1, 10.00),
(10484, '9788401352836', 1, 14.90),
(10484, '9788420471839', 1, 16.50),
(10485, '9788420674209', 1, 18.00),
(10485, '9788420471839', 2, 16.50),
(10486, '9788420633121', 1, 12.00),
(10487, '9788420471839', 3, 16.50),
(10487, '9788420412146', 1, 11.50);
