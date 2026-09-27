// Hector Abenia
package app;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import dao.LibroRepositoryArchivo;
import dao.LibroRepositoryMySQL;
import libroRepository.GenericRepository;
import modelo.Libro;

public class Main {

	static Scanner sc = new Scanner(System.in);
	static final String RUTA_ARCHIVO = "libros.txt";
	static GenericRepository repositorio;
	static int tipoRepositorio;

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

	public static GenericRepository crearRepositorio(int tipo) {
		if (tipo == 1) {
			return new LibroRepositoryArchivo(RUTA_ARCHIVO);
		}
		return new LibroRepositoryMySQL();
	}

	public static String nombreRepositorio(int tipo) {
		if (tipo == 1) {
			return "archivo de texto";
		}
		return "MySQL";
	}

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

	public static void buscarPorTitulo() {
		String titulo = leerTexto("Título (o parte): ");
		mostrarResultados(repositorio.buscarPorTitulo(titulo));
	}

	public static void buscarPorAutor() {
		String autor = leerTexto("Autor (o parte): ");
		mostrarResultados(repositorio.buscarPorAutor(autor));
	}

	public static void buscarPorRangoPrecio() {
		double min = leerDouble("Precio mínimo: ");
		double max = leerDouble("Precio máximo: ");

		if (min > max) {
			System.out.println("El mínimo no puede ser mayor que el máximo.");
			return;
		}

		mostrarResultados(repositorio.buscarPorRangoPrecio(min, max));
	}

	public static void buscarPorStockMinimo() {
		int stock = leerEntero("Stock mínimo: ");
		mostrarResultados(repositorio.buscarPorStockMinimo(stock));
	}

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

	public static void mostrarResultados(ArrayList<Libro> libros) {
		if (libros.isEmpty()) {
			System.out.println("No se han encontrado libros.");
			return;
		}

		libros.forEach(System.out::println);
		System.out.println("Encontrados: " + libros.size());
	}

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