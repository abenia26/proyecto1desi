# Base de datos de Páginas de Villa Serena

## 1. Resumen del caso

Páginas de Villa Serena es una librería con tres tiendas: Centro, Ribera (en Aldeaverde) y Universidad. Su dueña, Elena Ruiz, lleva el control de cada tienda en una hoja de cálculo distinta. El problema es que esas hojas no coinciden con lo que hay en las estanterías.

Elena necesita una base de datos única donde conste cuántas copias de cada libro hay en cada tienda y cuándo se contaron por última vez. También quiere guardar los libros con sus autores y su editorial, los empleados de cada tienda y los clientes. Y los pedidos, con los libros que lleva cada uno y el precio que se cobró en ese momento, para que las facturas antiguas no cambien cuando suba el precio de un libro.

Con esa base de datos Elena quiere poder contestar preguntas como qué libros hay en cada tienda, cuánto ha facturado cada una o qué empleado ha atendido más pedidos.

## 2. Análisis del caso
 
### 2.1 Entidades y atributos
 
| Entidad | Atributos encontrados | Fragmento del caso |
|---|---|---|
| Tienda | nombre, dirección, teléfono, ciudad | «cada una tiene su nombre, su dirección, su teléfono y su ciudad» |
| Libro | ISBN, título, año de publicación, páginas, precio de catálogo | «se identifica por su ISBN (13 cifras), y de cada uno guardan el título, el año de publicación, el número de páginas y el precio de catálogo» |
| Editorial | nombre, país, teléfono | «de cada editorial anotan su nombre, el país y un teléfono de contacto» |
| Autor | nombre, nacionalidad, año de nacimiento | «de cada autor guardamos el nombre, la nacionalidad y el año de nacimiento» |
| Inventario (libro en tienda) | cantidad, fecha del último conteo | «cuántas copias hay, y cuándo se contó por última vez» |
| Empleado | DNI, nombre, apellidos, cargo, fecha de contratación, correo | «el DNI, el nombre y los apellidos, el cargo (librero, cajero o encargado), la fecha de contratación y el correo de trabajo» |
| Cliente | nombre, correo, teléfono (opcional), si es socio, fecha de alta | «el nombre completo, el correo electrónico (único, lo usan para enviar avisos), el teléfono (opcional) y la fecha de alta» |
| Pedido | fecha, forma de pago, estado | «Tiene una fecha, una forma de pago (efectivo, tarjeta o bizum) y un estado» |
| Línea de pedido | cantidad, precio cobrado | «de cada uno me interesa la cantidad» y «lo que realmente se cobró» |
 
También sale la relación entre libro y autor con un dato propio: el rol (principal o colaborador).
 
### 2.2 Relaciones
 
| Relación | Cardinalidad | Razonamiento |
|---|---|---|
| Editorial – Libro | 1:N | Una editorial publica muchos libros. Cada libro lo publica una sola editorial. |
| Libro – Autor | N:M | Un libro puede tener varios autores y un autor puede tener muchos libros. Se resuelve con `libro_autor`, que guarda el rol. |
| Tienda – Libro | N:M | Una tienda tiene muchos libros y un libro está en varias tiendas. Se resuelve con `inventario`, que guarda la cantidad y la fecha de conteo. |
| Tienda – Empleado | 1:N | En una tienda trabajan varios empleados. Cada empleado trabaja en una sola tienda. |
| Tienda – Pedido | 1:N | Un pedido se hace siempre en una tienda y una tienda tiene muchos pedidos. |
| Empleado – Pedido | 1:N | Un empleado atiende muchos pedidos. Cada pedido lo atiende un empleado. |
| Cliente – Pedido | 1:N | Un cliente hace muchos pedidos. Cada pedido lo compra un cliente. |
| Pedido – Libro | N:M | Un pedido lleva varios libros y un libro sale en muchos pedidos. Se resuelve con `linea_pedido`, que guarda la cantidad y el precio cobrado. |

### 2.3 Datos descartados
 
| Dato | Motivo |
|---|---|
| Total del pedido | Se calcula sumando `cantidad * precio_cobrado` de sus líneas. |
| Subtotal de cada línea | Se calcula multiplicando cantidad por precio. |
| Columna «stock» de la hoja de cálculo | Un stock por libro no sirve con tres tiendas. Se sustituye por `inventario`. |
| «Cortázar / Borges» en una sola celda | Son dos autores. Se guardan como dos filas en `libro_autor`. |
| Editorial repetida en cada fila de la hoja | Se guarda una vez en `editorial` y el libro apunta a ella. |
| Historial de tiendas de un empleado | Elena dice que no le importa. Solo se guarda la tienda actual. |
| Nombre de la tienda, teléfono y cargo del empleado en el ticket | Ya están en `tienda` y `empleado`. El pedido solo guarda las claves foráneas. |