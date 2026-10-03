// Hector Abenia
package app;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import dao.LibroRepositoryArchivo;
import dao.LibroRepositoryMySQL;
import libroRepository.GenericRepository;
import modelo.Libro;

/**
 * Clase principal de la biblioteca. Muestra un menú por consola que permite
 * consultar, insertar, eliminar y copiar libros, trabajando con el repositorio
 * de archivo de texto o con el de MySQL.
 *
 * @author Hector Abenia
 */
public class Main {

	/** Scanner para leer lo que escribe el usuario. */
	static Scanner sc = new Scanner(System.in);
	/** Ruta del archivo de texto que usa el repositorio de archivo. */
	static final String RUTA_ARCHIVO = "libros.txt";
	/** Repositorio con el que se está trabajando en este momento. */
	static GenericRepository repositorio;
	/** Tipo del repositorio activo: 1 para archivo de texto y 2 para MySQL. */
	static int tipoRepositorio;

	/**
	 * Punto de entrada del programa. Pide el repositorio con el que trabajar y
	 * muestra el menú hasta que el usuario elige la opción 0 para salir.
	 *
	 * @param args argumentos de la línea de comandos (no se usan)
	 */
	public static void main(String[] args) {
		elegirRepositorio();

		int opcion;
		do {
			mostrarMenu();
			opcion = leerEntero("Elige opción: ");

			switch (opcion) {
			case 1:
				mostrarTodos();
				break;
			case 2:
				buscarPorTitulo();
				break;
			case 3:
				buscarPorAutor();
				break;
			case 4:
				buscarPorRangoPrecio();
				break;
			case 5:
				buscarPorStockMinimo();
				break;
			case 6:
				insertarLibro();
				break;
			case 7:
				eliminarLibro();
				break;
			case 8:
				copiarAlOtroRepositorio();
				break;
			case 9:
				elegirRepositorio();
				break;
			case 0:
				System.out.println("Fin del programa.");
				break;
			default:
				System.out.println("Opción no válida.");
			}

		} while (opcion != 0);

		sc.close();
	}

	/**
	 * Pide al usuario que elija entre el archivo de texto (1) y MySQL (2), repitiendo
	 * la pregunta hasta que la opción sea válida, y deja ese repositorio como el
	 * repositorio activo.
	 */
	public static void elegirRepositorio() {
		int tipo;
		do {
			System.out.println("\n--- ELIGE REPOSITORIO ---");
			System.out.println("1. Archivo de texto (" + RUTA_ARCHIVO + ")");
			System.out.println("2. Base de datos MySQL");
			tipo = leerEntero("Opción: ");
		} while (tipo != 1 && tipo != 2);

		tipoRepositorio = tipo;
		repositorio = crearRepositorio(tipo);
		System.out.println("Trabajando con: " + nombreRepositorio(tipo));
	}

	/**
	 * Crea el repositorio que corresponde al tipo indicado.
	 *
	 * @param tipo tipo de repositorio: 1 para archivo de texto; cualquier otro valor
	 *             para MySQL
	 * @return un {@link LibroRepositoryArchivo} sobre {@code RUTA_ARCHIVO} si el tipo
	 *         es 1; un {@link LibroRepositoryMySQL} en caso contrario
	 */
	public static GenericRepository crearRepositorio(int tipo) {
		if (tipo == 1) {
			return new LibroRepositoryArchivo(RUTA_ARCHIVO);
		}
		return new LibroRepositoryMySQL();
	}

	/**
	 * Devuelve el nombre del tipo de repositorio para mostrarlo en los mensajes y en
	 * el menú.
	 *
	 * @param tipo tipo de repositorio: 1 para archivo de texto; cualquier otro valor
	 *             para MySQL
	 * @return {@code "archivo de texto"} si el tipo es 1; {@code "MySQL"} en caso
	 *         contrario
	 */
	public static String nombreRepositorio(int tipo) {
		if (tipo == 1) {
			return "archivo de texto";
		}
		return "MySQL";
	}

	/**
	 * Muestra por consola las opciones del menú principal, indicando en la cabecera
	 * el repositorio con el que se está trabajando.
	 */
	public static void mostrarMenu() {
		System.out.println("\n--- BIBLIOTECA (" + nombreRepositorio(tipoRepositorio) + ") ---");
		System.out.println("1. Mostrar todos los libros");
		System.out.println("2. Buscar por título");
		System.out.println("3. Buscar por autor");
		System.out.println("4. Buscar por rango de precio");
		System.out.println("5. Buscar por stock mínimo");
		System.out.println("6. Insertar un libro");
		System.out.println("7. Eliminar un libro por id");
		System.out.println("8. Copiar todos los libros al otro repositorio");
		System.out.println("9. Cambiar de repositorio");
		System.out.println("0. Salir");
	}

	/**
	 * Muestra todos los libros del repositorio activo ordenados por título, seguidos
	 * del total. Si no hay libros, muestra un aviso.
	 */
	public static void mostrarTodos() {
		ArrayList<Libro> libros = repositorio.obtenerTodos();

		if (libros.isEmpty()) {
			System.out.println("No hay libros guardados.");
			return;
		}

		libros.stream()
				.sorted(Comparator.comparing(Libro::getTitulo))
				.forEach(System.out::println);
		System.out.println("Total: " + libros.size() + " libros");
	}

	/**
	 * Pide al usuario un texto y muestra los libros del repositorio activo cuyo
	 * título lo contiene.
	 */
	public static void buscarPorTitulo() {
		String titulo = leerTexto("Título (o parte): ");
		mostrarResultados(repositorio.buscarPorTitulo(titulo));
	}

	/**
	 * Pide al usuario un texto y muestra los libros del repositorio activo cuyo
	 * autor lo contiene.
	 */
	public static void buscarPorAutor() {
		String autor = leerTexto("Autor (o parte): ");
		mostrarResultados(repositorio.buscarPorAutor(autor));
	}

	/**
	 * Pide al usuario un precio mínimo y uno máximo y muestra los libros del
	 * repositorio activo cuyo precio está dentro de ese rango. Si el mínimo es
	 * mayor que el máximo, muestra un aviso y no realiza la búsqueda.
	 */
	public static void buscarPorRangoPrecio() {
		double min = leerDouble("Precio mínimo: ");
		double max = leerDouble("Precio máximo: ");

		if (min > max) {
			System.out.println("El mínimo no puede ser mayor que el máximo.");
			return;
		}

		mostrarResultados(repositorio.buscarPorRangoPrecio(min, max));
	}

	/**
	 * Pide al usuario un stock mínimo y muestra los libros del repositorio activo
	 * que tienen al menos ese stock.
	 */
	public static void buscarPorStockMinimo() {
		int stock = leerEntero("Stock mínimo: ");
		mostrarResultados(repositorio.buscarPorStockMinimo(stock));
	}

	/**
	 * Pide al usuario los datos de un libro nuevo y lo inserta en el repositorio
	 * activo. No lo inserta si ya existe  un libro con el mismo id o si el precio o el
	 * stock son negativos, y en ese caso muestra un aviso.
	 */
	public static void insertarLibro() {
		String id = leerTexto("Id: ");

		boolean existe = repositorio.obtenerTodos().stream()
				.anyMatch(l -> l.getId().equals(id));
		if (existe) {
			System.out.println("Ya existe un libro con ese id.");
			return;
		}

		String titulo = leerTexto("Título: ");
		String autor = leerTexto("Autor: ");
		double precio = leerDouble("Precio: ");
		int stock = leerEntero("Stock: ");

		if (precio < 0 || stock < 0) {
			System.out.println("El precio y el stock no pueden ser negativos.");
			return;
		}

		Libro libro = new Libro(id, titulo, autor, precio, stock);

		if (repositorio.insertar(libro)) {
			System.out.println("Libro insertado correctamente.");
		} else {
			System.out.println("No se pudo insertar el libro.");
		}
	}

	/**
	 * Muestra todos los libros, pide el id del libro que se quiere eliminar y, si el
	 * usuario lo confirma con {@code s}, lo elimina del repositorio activo.
	 */
	public static void eliminarLibro() {
		mostrarTodos();
		String id = leerTexto("Id del libro a eliminar: ");
		String confirmacion = leerTexto("¿Seguro? (s/n): ");

		if (!confirmacion.equalsIgnoreCase("s")) {
			System.out.println("Operación cancelada.");
			return;
		}

		if (repositorio.eliminarPorId(id)) {
			System.out.println("Libro eliminado.");
		} else {
			System.out.println("No existe un libro con ese id.");
		}
	}

	/**
	 * Copia todos los libros del repositorio activo al otro tipo de repositorio (del
	 * archivo a MySQL o de MySQL al archivo), después de pedir confirmación al
	 * usuario con {@code s}.
	 */
	public static void copiarAlOtroRepositorio() {
		int tipoDestino;
		if (tipoRepositorio == 1) {
			tipoDestino = 2;
		} else {
			tipoDestino = 1;
		}

		String confirmacion = leerTexto("Se copiarán todos los libros de " + nombreRepositorio(tipoRepositorio)
				+ " a " + nombreRepositorio(tipoDestino) + ". ¿Continuar? (s/n): ");
		if (!confirmacion.equalsIgnoreCase("s")) {
			System.out.println("Operación cancelada.");
			return;
		}

		GenericRepository destino = crearRepositorio(tipoDestino);

		if (repositorio.copiarA(destino)) {
			System.out.println("Copia terminada.");
		} else {
			System.out.println("No se pudo copiar.");
		}
	}

	/**
	 * Muestra por consola los libros de una búsqueda seguidos del número de libros
	 * encontrados. Si la lista está vacía, muestra un aviso.
	 *
	 * @param libros libros que se quieren mostrar
	 */
	public static void mostrarResultados(ArrayList<Libro> libros) {
		if (libros.isEmpty()) {
			System.out.println("No se han encontrado libros.");
			return;
		}

		libros.forEach(System.out::println);
		System.out.println("Encontrados: " + libros.size());
	}

	/**
	 * Muestra el mensaje y lee un número entero por teclado, repitiendo la pregunta
	 * hasta que el usuario escribe un número válido.
	 *
	 * @param mensaje texto que se muestra antes de leer
	 * @return el número entero introducido
	 */
	public static int leerEntero(String mensaje) {
		while (true) {
			System.out.print(mensaje);
			try {
				return Integer.parseInt(sc.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("Introduce un número entero.");
			}
		}
	}

	/**
	 * Muestra el mensaje y lee un número decimal por teclado, repitiendo la
	 * pregunta hasta que el usuario escribe un número válido. Acepta tanto el punto
	 * como la coma como separador decimal.
	 *
	 * @param mensaje texto que se muestra antes de leer
	 * @return el número decimal introducido
	 */
	public static double leerDouble(String mensaje) {
		while (true) {
			System.out.print(mensaje);
			try {
				return Double.parseDouble(sc.nextLine().trim().replace(",", "."));
			} catch (NumberFormatException e) {
				System.out.println("Introduce un número (ej: 19.95).");
			}
		}
	}

	/**
	 * Muestra el mensaje y lee una línea de texto por teclado. Vuelve a preguntar
	 * si el texto está vacío o tiene el carácter {@code ^}, que es el separador
	 * del archivo.
	 *
	 * @param mensaje texto que se muestra antes de leer
	 * @return el texto introducido
	 */
	public static String leerTexto(String mensaje) {
		String texto;
		do {
			System.out.print(mensaje);
			texto = sc.nextLine().trim();
			if (texto.isEmpty()) {
				System.out.println("No puede estar vacío.");
			} else if (texto.contains("^")) {
				System.out.println("No puede contener el carácter ^ (es el separador del archivo).");
				texto = "";
			}
		} while (texto.isEmpty());
		return texto;
	}
}