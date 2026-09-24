package libroRepository;

import java.util.ArrayList;

import modelo.Libro;

public interface GenericRepository<T> {

	ArrayList<Libro> obtenerTodos();

	ArrayList<Libro> buscarPorTitulo(String titulo);

	ArrayList<Libro> buscarPorAutor(String autor);

	ArrayList<Libro> buscarPorRangoPrecio(double precioMin, double precioMax);

	ArrayList<Libro> buscarPorStockMinimo(int stockMinimo);

	void insertar(Libro libro);

	void eliminarPorId(String id);
}
