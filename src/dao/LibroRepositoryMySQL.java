// Hector Abenia
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import libroRepository.GenericRepository;
import modelo.Libro;
import util.ConexionBD;

/**
 * Implementación de {@link GenericRepository} que guarda los libros en la tabla
 * {@code libros} de una base de datos MySQL. Cada método abre su propia
 * conexión con {@link ConexionBD#conectar()}.
 *
 * @author Hector Abenia
 */
public class LibroRepositoryMySQL implements GenericRepository {

	/**
	 * Ejecuta un {@code SELECT} sobre la tabla {@code libros} y convierte cada fila
	 * del resultado en un {@link Libro}.
	 *
	 * @return la lista de libros de la tabla; vacía si la tabla no tiene libros o
	 *         no se ha podido consultar
	 */
	@Override
	public ArrayList<Libro> obtenerTodos() {
		ArrayList<Libro> lista = new ArrayList<Libro>();
		String sql = "SELECT id, titulo, autor, precio, stock FROM libros";

		try (Connection con = ConexionBD.conectar();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				lista.add(mapearFila(rs));
			}

		} catch (SQLException e) {
			System.out.println("Error al obtener los libros: " + e.getMessage());
		}
		return lista;
	}

	/**
	 * Consulta la tabla {@code libros} con {@code LIKE} y se queda con aquellos
	 * cuyo título contiene el texto indicado.
	 *
	 * @param titulo texto que debe contener el título del libro
	 * @return la lista de libros cuyo título contiene el texto; vacía si no hay
	 *         ninguno o no se ha podido consultar
	 */
	@Override
	public ArrayList<Libro> buscarPorTitulo(String titulo) {
		ArrayList<Libro> lista = new ArrayList<Libro>();
		String sql = "SELECT id, titulo, autor, precio, stock FROM libros WHERE titulo LIKE ?";

		try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, "%" + titulo + "%");

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					lista.add(mapearFila(rs));
				}
			}

		} catch (SQLException e) {
			System.out.println("Error al buscar por título: " + e.getMessage());
		}
		return lista;
	}

	/**
	 * Consulta la tabla {@code libros} con {@code LIKE} y se queda con aquellos
	 * cuyo autor contiene el el texto indicado.
	 *
	 * @param autor texto que debe contener el autor del libro
	 * @return la lista de libros cuyo autor contiene el texto; vacía si no hay
	 *         ninguno o no se ha podido consultar
	 */
	@Override
	public ArrayList<Libro> buscarPorAutor(String autor) {
		ArrayList<Libro> lista = new ArrayList<Libro>();
		String sql = "SELECT id, titulo, autor, precio, stock FROM libros WHERE autor LIKE ?";

		try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, "%" + autor + "%");

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					lista.add(mapearFila(rs));
				}
			}

		} catch (SQLException e) {
			System.out.println("Error al buscar por autor: " + e.getMessage());
		}
		return lista;
	}

	/**
	 * Consulta la tabla {@code libros} con {@code BETWEEN} y se queda con aquellos
	 * cuyo precio está dentro del rango indicado, ambos extremos incluidos.
	 *
	 * @param precioMin precio mínimo (incluido)
	 * @param precioMax precio máximo (incluido)
	 * @return la lista de libros con precio entre {@code precioMin} y
	 *         {@code precioMax}; vacía si no hay ninguno o no se ha podido
	 *         consultar
	 */
	@Override
	public ArrayList<Libro> buscarPorRangoPrecio(double precioMin, double precioMax) {
		ArrayList<Libro> lista = new ArrayList<Libro>();
		String sql = "SELECT id, titulo, autor, precio, stock FROM libros WHERE precio BETWEEN ? AND ?";

		try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setDouble(1, precioMin);
			ps.setDouble(2, precioMax);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					lista.add(mapearFila(rs));
				}
			}

		} catch (SQLException e) {
			System.out.println("Error al buscar por precio: " + e.getMessage());
		}
		return lista;
	}

	/**
	 * Consulta la tabla {@code libros} y se queda con aquellos cuyo stock es igual
	 * o superior al indicado.
	 *
	 * @param stockMinimo stock mínimo (incluido) que debe tener el libro
	 * @return la lista de libros con stock mayor o igual que {@code stockMinimo};
	 *         vacía si no hay ninguno o no se ha podido consultar
	 */
	@Override
	public ArrayList<Libro> buscarPorStockMinimo(int stockMinimo) {
		ArrayList<Libro> lista = new ArrayList<Libro>();
		String sql = "SELECT id, titulo, autor, precio, stock FROM libros WHERE stock >= ?";

		try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, stockMinimo);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					lista.add(mapearFila(rs));
				}
			}

		} catch (SQLException e) {
			System.out.println("Error al buscar por stock: " + e.getMessage());
		}
		return lista;
	}

	/**
	 * Inserta el libro como una fila nueva de la tabla {@code libros} con un
	 * {@code INSERT}. Si ya hay un libro con ese id no se inserta.
	 *
	 * @param libro libro que se quiere guardar
	 * @return {@code true} si se ha insertado la fila; {@code false} si el id ya
	 *         existe o ha habido un error con la base de datos
	 */
	@Override
	public boolean insertar(Libro libro) {
		String sql = "INSERT INTO libros (id, titulo, autor, precio, stock) VALUES (?, ?, ?, ?, ?)";

		try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, libro.getId());
			ps.setString(2, libro.getTitulo());
			ps.setString(3, libro.getAutor());
			ps.setDouble(4, libro.getPrecio());
			ps.setInt(5, libro.getStock());

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			System.out.println("Error al insertar '" + libro.getTitulo() + "': " + e.getMessage());
			return false;
		}
	}

	/**
	 * Borra de la tabla {@code libros} la fila con el id indicado con un
	 * {@code DELETE}.
	 *
	 * @param id id del libro que se quiere eliminar
	 * @return {@code true} si se ha borrado alguna fila; {@code false} si no existe
	 *         un libro con ese id o ha habido un error con la base de datos
	 */
	@Override
	public boolean eliminarPorId(String id) {
		String sql = "DELETE FROM libros WHERE id = ?";

		try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, id);
			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			System.out.println("Error al eliminar el id " + id + ": " + e.getMessage());
			return false;
		}
	}

	/**
	 * Obtiene todos los libros de la tabla y los inserta uno a uno en el
	 * repositorio de destino con su método {@code insertar}.
	 *
	 * @param destino repositorio al que se copian los libros
	 * @return {@code true} siempre, ya que no se comprueba el resultado de cada
	 *         inserción
	 */
	@Override
	public boolean copiarA(GenericRepository destino) {
		ArrayList<Libro> todos = obtenerTodos();
		for (Libro l : todos) {
			destino.insertar(l);
		}
		return true;
	}

	/**
	 * Crea un {@link Libro} con los datos de la fila actual del
	 * {@link ResultSet}, leyendo las columnas {@code id}, {@code titulo},
	 * {@code autor}, {@code precio} y {@code stock}.
	 *
	 * @param rs resultado de la consulta
	 * @return el libro con los datos de la fila
	 * @throws SQLException si no se puede leer alguna de las columnas
	 */
	private Libro mapearFila(ResultSet rs) throws SQLException {
		Libro l = new Libro();
		l.setId(rs.getString("id"));
		l.setTitulo(rs.getString("titulo"));
		l.setAutor(rs.getString("autor"));
		l.setPrecio(rs.getDouble("precio"));
		l.setStock(rs.getInt("stock"));
		return l;
	}

}