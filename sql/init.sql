CREATE DATABASE IF NOT EXISTS biblioteca CHARACTER SET utf8mb4;
USE biblioteca;

DROP TABLE IF EXISTS libros;

CREATE TABLE libros (
    id     VARCHAR(20)  PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    autor  VARCHAR(100) NOT NULL,
    precio DOUBLE       NOT NULL,
    stock  INT          NOT NULL
);

INSERT INTO libros (id, titulo, autor, precio, stock) VALUES
('L001', 'Cien años de soledad', 'Gabriel García Márquez', 19.95, 10),
('L002', 'El amor en los tiempos del cólera', 'Gabriel García Márquez', 17.50, 4),
('L003', 'Rayuela', 'Julio Cortázar', 22.00, 7),
('L004', 'Bestiario', 'Julio Cortázar', 15.90, 2),
('L005', 'Ficciones', 'Jorge Luis Borges', 18.50, 12),
('L006', 'El Aleph', 'Jorge Luis Borges', 16.00, 5),
('L007', 'Pedro Páramo', 'Juan Rulfo', 14.00, 0),
('L008', 'La casa de los espíritus', 'Isabel Allende', 21.75, 3);
