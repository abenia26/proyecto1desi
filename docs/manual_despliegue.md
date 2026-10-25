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
