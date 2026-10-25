# Manual de despliegue, configuración y administración · Sistema de Gestión de Biblioteca

## 1. Introducción y alcance

Este manual explica cómo instalar y configurar la aplicación de biblioteca, un programa de consola hecho en Java. La aplicación permite ver, buscar, añadir y borrar libros, y puede guardar los datos en un archivo de texto (`libros.txt`) o en una base de datos MySQL.

Va dirigido a alguien que sabe usar Windows y la terminal, pero que **no conoce este proyecto**: no sabe cómo está montado ni dónde están las cosas.

El manual explica:

- Qué programas hay que instalar (JDK, MySQL, Eclipse, Git).
- Cómo descargar el proyecto y abrirlo en Eclipse.
- Cómo configurar las librerías y la conexión a la base de datos.
- Cómo crear la base de datos y arrancar la aplicación.
- Algunas tareas básicas de mantenimiento (copias de seguridad y solución de errores).

Al terminar este manual, cualquiera debería poder tener la aplicación funcionando en su ordenador, usando tanto el archivo de texto como la base de datos, sin tener que preguntar nada.

<!-- aqui va la imagen de Hector -->

## 2. Requisitos y entorno de pruebas

Antes de empezar hay que tener instalado este software, comprobando la versión con los comandos de verificación (no vale "poner lo que crees que tienes"):

| Software | Versión usada | Para qué |
|---|---|---|
| Windows 10/11 | — *(a confirmar por Héctor en la VM)* | Sistema operativo donde se prueba |
| JDK | 21 | Compilar y ejecutar la aplicación |
| Eclipse IDE for Java Developers | — *(a confirmar por Héctor)* | Abrir y ejecutar el proyecto |
| MySQL Community Server | — *(a confirmar por Héctor)* | Guardar los datos en base de datos |
| Git | — *(a confirmar por Héctor)* | Descargar el proyecto |

**Capturas obligatorias:**

- La VM recién creada (escritorio vacío).
- El snapshot creado.

<!-- aqui va la imagen de Hector -->

**Errores habituales a evitar:**

- Escribir "Java" o "MySQL" sin decir la versión.
- Poner la versión que crees que tienes sin comprobarla con `java -version` y `mysql --version`.

## 3. Instalación del software necesario

Pasos para instalar cada programa de la tabla del apartado 2 en un Windows limpio.

**JDK 21**

1. Descargarlo de la página oficial de Eclipse Temurin (o de Oracle).
2. Durante la instalación, marcar la opción de añadir Java al `PATH`, si el instalador la ofrece.
3. Comprobarlo abriendo una terminal **nueva** y escribiendo:

   ```
   java -version
   ```

**MySQL Community Server**

1. Descargar el instalador de la página oficial de MySQL.
2. Al instalarlo, pide una contraseña para el usuario `root`. **Hay que apuntarla**, se usa más adelante en el `.env`.
3. Comprobar que el servicio de MySQL se queda arrancado, abriendo `services.msc` y buscando `MySQL`.
4. Añadir la carpeta `bin` de MySQL al `PATH` (por ejemplo `C:\Program Files\MySQL\MySQL Server 8.4\bin`), desde *Configuración avanzada del sistema → Variables de entorno*.
5. Comprobarlo en una terminal nueva escribiendo:

   ```
   mysql --version
   ```

**Eclipse IDE for Java Developers**

1. Descargarlo de eclipse.org, el paquete "Eclipse IDE for Java Developers".
2. Instalar con las opciones por defecto.
3. Comprobar que abre y pide un *workspace*.

**Git**

1. Descargarlo de la página oficial de Git para Windows.
2. Dejar las opciones por defecto al instalar.
3. Comprobarlo en una terminal nueva escribiendo:

   ```
   git --version
   ```

**Capturas obligatorias:**

- Las pantallas del instalador donde hay que elegir algo.
- El `PATH` con la carpeta de MySQL añadida.
- La salida de los tres comandos de comprobación.

<!-- aqui va la imagen de Hector -->

**Errores habituales a evitar:**

- Comprobar el `PATH` en la misma terminal que ya estaba abierta (no se actualiza).
- No apuntar la contraseña de `root`: se necesita en el apartado 8.

## 4. Estructura del repositorio

Mapa del repositorio, con una línea explicando qué es cada cosa:

```
proyecto1desi/
├── src/                     Código fuente Java
│   ├── app/                 Clase principal (Main) y menú por consola
│   ├── dao/                 Repositorios: archivo de texto y MySQL
│   ├── libroRepository/     Interfaz genérica del repositorio
│   ├── modelo/              Clase Libro
│   ├── util/                Conexión a la base de datos (ConexionBD)
│   └── module-info.java     Módulo Java del proyecto
├── lib/                     Librerías (.jar) que necesita el proyecto
│   ├── mysql-connector-j-26.7.0.jar
│   └── dotenv-java-3.2.0.jar
├── sql/
│   └── init.sql             Crea la base de datos, la tabla y los datos de ejemplo
├── docs/
│   ├── manual_despliegue.pdf
│   └── imagenes/            Capturas del manual
├── javadoc/                 Documentación técnica (Fase 2)
├── .classpath               Rutas y librerías de Eclipse
├── .project                 Configuración del proyecto Eclipse
├── .env.example             Plantilla de configuración (se copia como .env)
├── .gitignore               Archivos que no se suben a GitHub
└── README.md
```

Y el `.gitignore` mínimo:

```
bin/
.env
*.env
```

**Errores habituales a evitar:**

- Subir `.env` al repositorio. El repositorio es público: la contraseña quedaría visible para todo el mundo y seguiría en el historial aunque luego se borre el archivo. Si ha pasado: `git rm --cached .env`, añadirlo al `.gitignore` y **cambiar esa contraseña**.
- Subir la carpeta `bin/`: son las clases ya compiladas, no hace falta subirlas.

## 5. Obtención del código

Pasos para pasar del enlace de GitHub a tener el proyecto abierto en Eclipse.

```
cd C:\proyectos
git clone https://github.com/<usuario-o-grupo>/proyecto1desi.git
```

Para abrirlo en Eclipse:

1. Abrir Eclipse y elegir un *workspace* (puede ser uno nuevo o el que ya se use).
2. *File → Import → General → Existing Projects into Workspace*.
3. En *Select root directory*, elegir la carpeta `proyecto1desi` que se acaba de clonar.
4. Marcar el proyecto en la lista y pulsar *Finish*.

Si todo ha ido bien, en el *Package Explorer* se ve el proyecto `proyecto1desi` con los paquetes `app`, `dao`, `libroRepository`, `modelo` y `util`. Puede salir un error en `module-info.java` hasta que se configuren las librerías, eso se arregla en el apartado 6.

**Capturas obligatorias:**

- El `git clone` terminado.
- El proyecto abierto en Eclipse, con el árbol de paquetes visible.

<!-- aqui va la imagen de Hector -->

**Errores habituales a evitar:**

- Poner la URL del perfil de GitHub en lugar de la del repositorio.
- Explicar solo la descarga en ZIP sin decir que también se puede clonar.

## 6. Librerías del proyecto

Este proyecto no usa Maven ni Gradle, así que las librerías no se descargan solas: los `.jar` tienen que estar en el repositorio, dentro de `lib/`, y Eclipse tiene que saber dónde están.

**Qué librerías usa el proyecto:**

- **MySQL Connector/J** (`mysql-connector-j-26.7.0.jar`): permite que Java se conecte a MySQL.
- **dotenv-java** (`dotenv-java-3.2.0.jar`): lee el archivo `.env` con los datos de conexión.

**Dónde están:** en la carpeta `lib/` del repositorio, ya vienen al clonarlo.

**Cómo añadirlas en Eclipse** (si no aparecen ya puestas, o para comprobarlo):

1. Clic derecho sobre el proyecto → *Build Path → Configure Build Path*.
2. Pestaña *Libraries* → *Classpath* → *Add JARs...* (no *Add External JARs*, porque esa opción guarda la ruta del disco de tu ordenador).
3. Buscar la carpeta `lib/` **dentro del proyecto** y seleccionar los dos `.jar`.
4. Aceptar.

**Por qué no vale `Add External JARs`:** si se añade así, Eclipse guarda una ruta como esta en el archivo `.classpath`:

```
C:/Users/Lucia/Downloads/mysql-connector-j-26.7.0.jar
```

Esa ruta solo existe en el ordenador de quien lo añadió. Con `Add JARs` queda así:

```
lib/mysql-connector-j-26.7.0.jar
```

Esta sí funciona en cualquier ordenador que clone el repositorio.

**Cómo comprobar que están bien puestas:** en *Build Path → Libraries* aparecen los dos `.jar` con ruta `lib/...`, el proyecto no tiene ningún icono de error y compila sin problemas.

**Capturas obligatorias:**

- La carpeta `lib/` con los dos `.jar`.
- La ventana *Build Path → Libraries* con las rutas.
- El diálogo *Add JARs* usado para añadirlas.

<!-- aqui va la imagen de Hector -->

**Errores habituales a evitar:**

- Tener los `.jar` en *Descargas* y añadirlos con *Add External JARs* sin subirlos antes a `lib/`.
- No revisar el `.classpath` antes de entregar, para comprobar que no queda ninguna ruta del disco de tu ordenador.
- No decir la versión de cada librería.

## 7. Inicialización de la base de datos

Un solo script, `sql/init.sql`, deja la base de datos lista desde cero.

**Qué hace el script:**

- Crea la base de datos `biblioteca` si no existe, usando `utf8mb4` para que las tildes y la ñ se guarden bien.
- Borra la tabla `libros` si ya existía y la vuelve a crear. Así se puede ejecutar el script varias veces y siempre deja el mismo resultado.
- Inserta 8 libros de ejemplo, con algún autor repetido y precios y stocks distintos (incluso un libro con stock 0), para poder probar bien las búsquedas.

```sql
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
```

**Cómo ejecutarlo**, de cualquiera de estas formas:

- Desde `cmd` (no PowerShell):

  ```
  mysql -u root -p < sql\init.sql
  ```

- Desde el cliente de MySQL:

  ```
  mysql -u root -p
  source C:/proyectos/proyecto1desi/sql/init.sql;
  ```

- Desde MySQL Workbench: *File → Open SQL Script* y ejecutar.

**Cómo comprobar que ha funcionado:**

```sql
SHOW TABLES;
SELECT * FROM libros;
```

Deben salir la tabla `libros` y los 8 libros de ejemplo.

> **Atención:** en PowerShell, el comando con `<` da error. Hay que usar `cmd`, o la instrucción `source` desde el cliente de MySQL.

**Aviso:** el script borra los datos que hubiera antes en la tabla `libros`.

**Errores habituales a evitar:**

- Que el nombre de la base de datos del script no coincida con el que se pone luego en el `.env` (apartado 8).
- Confundir `cmd` con PowerShell al ejecutar el script.

## 8. Configuración con .env

El archivo `.env` indica a la aplicación a qué base de datos conectarse y con qué usuario y contraseña, sin tocar el código.

**Cómo funciona en este proyecto:** la clase `util.ConexionBD` lee esos datos con `dotenv-java`:

```java
Dotenv env = Dotenv.load();
String url = env.get("DB_URL");
String user = env.get("DB_USER");
String pass = env.get("DB_PASS");

return DriverManager.getConnection(url, user, pass);
```

Y el `.env.example` que se sube al repositorio:

```
# Copia este archivo como .env y rellena tus datos
DB_URL=jdbc:mysql://localhost:3306/biblioteca?allowPublicKeyRetrieval=true&useSSL=false
DB_USER=root
DB_PASS=pon_aqui_tu_contraseña
```

**Tabla de variables:**

| Variable | Para qué sirve | Valor de ejemplo | ¿Obligatoria? |
|---|---|---|---|
| `DB_URL` | Dirección de la base de datos (host, puerto y nombre) | `jdbc:mysql://localhost:3306/biblioteca?allowPublicKeyRetrieval=true&useSSL=false` | Sí |
| `DB_USER` | Usuario de MySQL con el que se conecta la aplicación | `root` | Sí |
| `DB_PASS` | Contraseña de ese usuario | la que se eligió al instalar MySQL (apartado 3) | Sí |

**Cómo crear el `.env`** a partir del ejemplo. Desde `cmd`, en la carpeta del proyecto:

```
copy .env.example .env
```

**Qué hay que cambiar:** como mínimo `DB_PASS`, con la contraseña real de MySQL, y `DB_USER` si no se usa `root`.

**Dónde debe estar el `.env`:** en la carpeta desde la que se lanza la aplicación (ver apartado 9).

> **Cuidado con Windows:** con las extensiones ocultas, el Bloc de notas puede guardar `.env.txt` sin que se note. Hay que activar *Vista → Mostrar → Extensiones de nombre de archivo* en el Explorador, y al crear el `.env` desde el Bloc de notas, elegir *Tipo: Todos los archivos*.

<!-- aqui va la imagen de Hector -->

**Errores habituales a evitar:**

- Explicar qué poner en el `.env` pero no dónde colocarlo.
- Hacer capturas con la contraseña real a la vista.
- Poner nombres de variable distintos en el `.env.example` y en el código (`DB_URL`, `DB_USER`, `DB_PASS`).
