// Daniel Ortego
package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import libroRepository.GenericRepository;
import modelo.Libro;

/**
 * Implementación de {@link GenericRepository} que guarda los libros en un
 * archivo de texto, un libro por línea en formato CSV. Cada operación abre y
 * lee el archivo directamente, sin mantener los libros en memoria.
 *
 * @author Daniel Ortego
 */
public class LibroRepositoryArchivo implements GenericRepository {

	private String ruta;

	/**
	 * @param ruta ruta del archivo donde se guardarán los libros
	 */

	public LibroRepositoryArchivo(String ruta) {
		this.ruta = ruta;
		try {
			File f = new File(ruta);
			if (!f.exists()) {
				f.createNewFile();
			}
		} catch (IOException e) {
			System.out.println("Error al crear el archivo: " + e.getMessage());
		}
	}

	/**
	 * @param libro libro que se quiere guardar
	 * @return {@code true} si se ha escrito correctamente; {@code false} si ha
	 *         habido un error de escritura
	 */
	@Override
	public boolean insertar(Libro libro) {
		try (FileWriter fw = new FileWriter(ruta, true)) {
			fw.write(libro.toCSV() + "\n");
			return true;
		} catch (IOException e) {
			System.out.println("Error al insertar en el archivo: " + e.getMessage());
		}
		return false;
	}

	/**
	 * @param id id del libro que se quiere eliminar
	 * @return {@code true} si se ha encontrado un libro con ese id; {@code false} en caso contrario
	 */
	@Override
	public boolean eliminarPorId(String id) {
		ArrayList<Libro> todos = obtenerTodos();
		boolean encontrado = false;

		try (FileWriter fw = new FileWriter(ruta, false)) {
			for (Libro l : todos) {
				if (l.getId().equals(id)) {
					encontrado = true;
				} else {
					fw.write(l.toCSV() + "\n");
				}
			}
		} catch (IOException e) {
			System.out.println("Error al eliminar del archivo: " + e.getMessage());
		}

		return encontrado;
	}

	/**
	 * Lee el archivo línea a línea con un {@link Scanner}, ignora las líneas en
	 * blanco y convierte cada una en un {@link Libro} con {@code Libro.fromCSV}.
	 *
	 * @return la lista de libros del archivo; vacía si el archivo no tiene libros o
	 *         no se ha podido leer
	 */
	@Override
	public ArrayList<Libro> obtenerTodos() {
		ArrayList<Libro> lista = new ArrayList<Libro>();
		try (Scanner sc = new Scanner(new File(ruta))) {
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				if (!linea.isBlank()) {
					lista.add(Libro.fromCSV(linea));
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error al leer el archivo: " + e.getMessage());
		}
		return lista;
	}

	/**
	 * Recorre todos los libros del archivo y se queda con aquellos cuyo título
	 * contiene el texto indicado, distinguiendo mayúsculas de minúsculas.
	 *
	 * @param titulo texto que debe contener el título del libro
	 * @return la lista de libros cuyo título contiene el texto; vacía si no hay
	 *         ninguno
	 */
	@Override
	public ArrayList<Libro> buscarPorTitulo(String titulo) {
		ArrayList<Libro> encontrados = new ArrayList<Libro>();
		for (Libro l : obtenerTodos()) {
			if (l.getTitulo().contains(titulo)) {
				encontrados.add(l);
			}
		}
		return encontrados;
	}

	/**
	 * Recorre todos los libros del archivo y se queda con aquellos cuyo autor
	 * contiene el texto indicado, distinguiendo mayúsculas de minúsculas.
	 *
	 * @param autor texto que debe contener el autor del libro
	 * @return la lista de libros cuyo autor contiene el texto; vacía si no hay
	 *         ninguno
	 */
	@Override
	public ArrayList<Libro> buscarPorAutor(String autor) {
		ArrayList<Libro> encontrados = new ArrayList<Libro>();
		for (Libro l : obtenerTodos()) {
			if (l.getAutor().contains(autor)) {
				encontrados.add(l);
			}
		}
		return encontrados;
	}

	/**
	 * Recorre todos los libros del archivo y se queda con los que cuyo precio está
	 * dentro del rango indicado, ambos extremos incluidos.
	 *
	 * @param precioMin precio mínimo (incluido)
	 * @param precioMax precio máximo (incluido)
	 * @return la lista de libros con precio entre {@code precioMin} y  {@code precioMax}
	 */
	@Override
	public ArrayList<Libro> buscarPorRangoPrecio(double precioMin, double precioMax) {
		ArrayList<Libro> encontrados = new ArrayList<Libro>();
		for (Libro l : obtenerTodos()) {
			if (l.getPrecio() >= precioMin && l.getPrecio() <= precioMax) {
				encontrados.add(l);
			}
		}
		return encontrados;
	}

	@Override
	public ArrayList<Libro> buscarPorStockMinimo(int stockMinimo) {
		ArrayList<Libro> encontrados = new ArrayList<Libro>();
		for (Libro l : obtenerTodos()) {
			if (l.getStock() >= stockMinimo) {
				encontrados.add(l);
			}
		}
		return encontrados;
	}

	@Override
	public boolean copiarA(GenericRepository destino) {
		ArrayList<Libro> todos = obtenerTodos();
		for (Libro l : todos) {
			destino.insertar(l);
		}
		return true;
	}

}