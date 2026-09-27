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

public class LibroRepositoryArchivo implements GenericRepository {

	private static final String SEPARADOR = "\\^";
	private String ruta;

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

	@Override
	public boolean insertar(Libro libro) {
		try (FileWriter fw = new FileWriter(ruta, true)) {
			fw.write(libro.getId() + "^" + libro.getTitulo() + "^" + libro.getAutor() + "^" + libro.getPrecio() + "^"
					+ libro.getStock() + "\n");
			return true;
		} catch (IOException e) {
			System.out.println("Error al insertar en el archivo: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean eliminarPorId(String id) {
		ArrayList<Libro> todos = obtenerTodos();
		boolean encontrado = false;

		try (FileWriter fw = new FileWriter(ruta, false)) {
			for (Libro l : todos) {
				if (l.getId().equals(id)) {
					encontrado = true;
				} else {
					fw.write(l.getId() + "^" + l.getTitulo() + "^" + l.getAutor() + "^" + l.getPrecio() + "^"
							+ l.getStock() + "\n");
				}
			}
		} catch (IOException e) {
			System.out.println("Error al eliminar del archivo: " + e.getMessage());
		}

		return encontrado;
	}

	@Override
	public ArrayList<Libro> obtenerTodos() {
		ArrayList<Libro> lista = new ArrayList<Libro>();
		try (Scanner sc = new Scanner(new File(ruta))) {
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				if (!linea.isBlank()) {
					String[] campos = linea.split(SEPARADOR);
					Libro l = new Libro();
					l.setId(campos[0]);
					l.setTitulo(campos[1]);
					l.setAutor(campos[2]);
					l.setPrecio(Double.parseDouble(campos[3]));
					l.setStock(Integer.parseInt(campos[4]));
					lista.add(l);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error al leer el archivo: " + e.getMessage());
		}
		return lista;
	}

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