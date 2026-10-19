-- schema.sql: estructura de la base de datos de Páginas de Villa Serena
-- Crea la base de datos libreria desde cero. Ejecutar antes que datos.sql.

DROP DATABASE IF EXISTS libreria;
CREATE DATABASE libreria CHARACTER SET utf8mb4;
USE libreria;

CREATE TABLE editorial (
    id        INT          PRIMARY KEY AUTO_INCREMENT,
    nombre    VARCHAR(100) NOT NULL UNIQUE,
    pais      VARCHAR(50)  NOT NULL,
    telefono  VARCHAR(20)  NOT NULL
);

CREATE TABLE autor (
    id                INT          PRIMARY KEY AUTO_INCREMENT,
    nombre            VARCHAR(100) NOT NULL,
    nacionalidad      VARCHAR(50)  NOT NULL,
    anio_nacimiento   INT          NOT NULL
);

CREATE TABLE libro (
    isbn           CHAR(13)      PRIMARY KEY CHECK (isbn REGEXP '^[0-9]{13}$'),
    titulo         VARCHAR(150)  NOT NULL,
    anio_publicacion INT         NOT NULL,
    paginas        INT           NOT NULL CHECK (paginas > 0),
    precio         DECIMAL(6,2)  NOT NULL CHECK (precio >= 0),
    editorial_id   INT           NOT NULL,
    FOREIGN KEY (editorial_id) REFERENCES editorial(id)
);

CREATE TABLE libro_autor (
    libro_isbn  CHAR(13)    NOT NULL,
    autor_id    INT         NOT NULL,
    rol         VARCHAR(20) NOT NULL CHECK (rol IN ('principal', 'colaborador')),
    PRIMARY KEY (libro_isbn, autor_id),
    FOREIGN KEY (libro_isbn) REFERENCES libro(isbn),
    FOREIGN KEY (autor_id)   REFERENCES autor(id)
);

CREATE TABLE tienda (
    id         INT          PRIMARY KEY AUTO_INCREMENT,
    nombre     VARCHAR(50)  NOT NULL UNIQUE,
    direccion  VARCHAR(150) NOT NULL,
    telefono   VARCHAR(20)  NOT NULL,
    ciudad     VARCHAR(50)  NOT NULL
);

CREATE TABLE inventario (
    tienda_id       INT      NOT NULL,
    libro_isbn      CHAR(13) NOT NULL,
    cantidad        INT      NOT NULL CHECK (cantidad >= 0),
    fecha_conteo    DATE     NOT NULL,
    PRIMARY KEY (tienda_id, libro_isbn),
    FOREIGN KEY (tienda_id)  REFERENCES tienda(id),
    FOREIGN KEY (libro_isbn) REFERENCES libro(isbn)
);

CREATE TABLE empleado (
    dni                CHAR(9)      PRIMARY KEY,
    nombre             VARCHAR(50)  NOT NULL,
    apellidos          VARCHAR(100) NOT NULL,
    cargo              VARCHAR(20)  NOT NULL CHECK (cargo IN ('librero', 'cajero', 'encargado')),
    fecha_contratacion DATE         NOT NULL,
    correo             VARCHAR(100) NOT NULL UNIQUE,
    tienda_id          INT          NOT NULL,
    FOREIGN KEY (tienda_id) REFERENCES tienda(id)
);

CREATE TABLE cliente (
    id          INT          PRIMARY KEY AUTO_INCREMENT,
    nombre      VARCHAR(100) NOT NULL,
    correo      VARCHAR(100) NOT NULL UNIQUE,
    telefono    VARCHAR(20),
    es_socio    BOOLEAN      NOT NULL DEFAULT FALSE,
    fecha_alta  DATE,
    CHECK (es_socio = FALSE OR fecha_alta IS NOT NULL)
);

CREATE TABLE pedido (
    id           INT         PRIMARY KEY AUTO_INCREMENT,
    fecha        DATE        NOT NULL,
    forma_pago   VARCHAR(10) NOT NULL CHECK (forma_pago IN ('efectivo', 'tarjeta', 'bizum')),
    estado       VARCHAR(10) NOT NULL CHECK (estado IN ('preparado', 'entregado', 'cancelado')),
    tienda_id    INT         NOT NULL,
    empleado_dni CHAR(9)     NOT NULL,
    cliente_id   INT         NOT NULL,
    FOREIGN KEY (tienda_id)    REFERENCES tienda(id),
    FOREIGN KEY (empleado_dni) REFERENCES empleado(dni),
    FOREIGN KEY (cliente_id)   REFERENCES cliente(id)
);

CREATE TABLE linea_pedido (
    pedido_id       INT          NOT NULL,
    libro_isbn      CHAR(13)     NOT NULL,
    cantidad        INT          NOT NULL CHECK (cantidad > 0),
    precio_cobrado  DECIMAL(6,2) NOT NULL CHECK (precio_cobrado >= 0),
    PRIMARY KEY (pedido_id, libro_isbn),
    FOREIGN KEY (pedido_id)  REFERENCES pedido(id),
    FOREIGN KEY (libro_isbn) REFERENCES libro(isbn)
);
