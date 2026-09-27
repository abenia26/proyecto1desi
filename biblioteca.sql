CREATE DATABASE IF NOT EXISTS biblioteca;
USE biblioteca;

CREATE TABLE IF NOT EXISTS libros (
    id     VARCHAR(20)  PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    autor  VARCHAR(100) NOT NULL,
    precio DOUBLE       NOT NULL,
    stock  INT          NOT NULL
);

INSERT INTO libros (id, titulo, autor, precio, stock) VALUES
('L001', 'libro1', 'Daniel', 35.50, 10),
('L002', 'libro2', 'Daniel', 19.95, 4),
('L003', 'libro3', 'Daniel', 22.00, 7),
('L004', 'libro4', 'Daniel', 49.90, 2),
('L005', 'libro5', 'Hector', 18.50, 12),
('L006', 'libro6', 'Hector', 42.00, 5),
('L007', 'libro7', 'Hector', 21.00, 0),
('L008', 'libro8', 'Hector', 38.75, 3);

CREATE USER IF NOT EXISTS 'dam'@'localhost' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON biblioteca.* TO 'dam'@'localhost';
