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
