// Daniel Ortego
package libroRepository;

import java.util.ArrayList;

import modelo.Libro;

public interface GenericRepository {

	ArrayList<Libro> obtenerTodos();

	ArrayList<Libro> buscarPorTitulo(String titulo);

	ArrayList<Libro> buscarPorAutor(String autor);

	ArrayList<Libro> buscarPorRangoPrecio(double precioMin, double precioMax);

	ArrayList<Libro> buscarPorStockMinimo(int stockMinimo);

	boolean insertar(Libro libro);

	boolean eliminarPorId(String id);

	boolean copiarA(GenericRepository destino);

}