// Daniel Ortego
package libroRepository;

import java.util.ArrayList;

import modelo.Libro;

/**
 * Contrato común para los repositorios de libros. Define las operaciones de
 * consulta, inserción, borrado y copia, y cada implementación decide dónde se
 * guardan los libros (por ejemplo, un archivo o una base de datos).
 *
 * @author Daniel Ortego
 */
public interface GenericRepository {

	/**
	 * Devuelve todos los libros guardados en el repositorio.
	 *
	 * @return la lista con todos los libros; vacía si no hay ninguno
	 */
	ArrayList<Libro> obtenerTodos();

	/**
	 * Busca los libros cuyo título contiene el texto indicado.
	 *
	 * @param titulo texto que debe contener el título del libro
	 * @return la lista de libros que coinciden; vacía si no hay ninguno
	 */
	ArrayList<Libro> buscarPorTitulo(String titulo);

	/**
	 * Busca los libros cuyo autor contiene el texto indicado.
	 *
	 * @param autor texto que debe contener el autor del libro
	 * @return la lista de libros que coinciden; vacía si no hay ninguno
	 */
	ArrayList<Libro> buscarPorAutor(String autor);

	ArrayList<Libro> buscarPorRangoPrecio(double precioMin, double precioMax);

	ArrayList<Libro> buscarPorStockMinimo(int stockMinimo);

	boolean insertar(Libro libro);

	boolean eliminarPorId(String id);

	boolean copiarA(GenericRepository destino);

}