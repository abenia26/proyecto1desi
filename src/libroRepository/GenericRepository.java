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

	/**
	 * Busca los libros cuyo precio está dentro del rango indicado, ambos extremos
	 * incluidos.
	 *
	 * @param precioMin precio mínimo (incluido)
	 * @param precioMax precio máximo (incluido)
	 * @return la lista de libros con precio dentro del rango; vacía si no hay
	 *         ninguno
	 */
	ArrayList<Libro> buscarPorRangoPrecio(double precioMin, double precioMax);

	/**
	 * Busca los libros cuyo stock es igual o superior al indicado.
	 *
	 * @param stockMinimo stock mínimo (incluido) que debe tener el libro
	 * @return la lista de libros con stock suficiente; vacía si no hay ninguno
	 */
	ArrayList<Libro> buscarPorStockMinimo(int stockMinimo);

	/**
	 * Guarda un libro nuevo en el repositorio.
	 *
	 * @param libro libro que se quiere guardar
	 * @return {@code true} si se ha guardado correctamente; {@code false} si ha
	 *         habido algún error
	 */
	boolean insertar(Libro libro);

	/**
	 * Elimina del repositorio el libro que tiene el id indicado.
	 *
	 * @param id id del libro que se quiere eliminar
	 * @return {@code true} si se ha encontrado un libro con ese id; {@code false}
	 *         en caso contrario
	 */
	boolean eliminarPorId(String id);

	/**
	 * Copia todos los libros de este repositorio al repositorio de destino.
	 *
	 * @param destino repositorio al que se copian los libros
	 * @return {@code true} si la copia se ha realizado; {@code false} en caso
	 *         contrario
	 */
	boolean copiarA(GenericRepository destino);

}