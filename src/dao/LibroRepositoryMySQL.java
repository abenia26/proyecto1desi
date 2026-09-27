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

public class LibroRepositoryMySQL implements GenericRepository {

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

	@Override
	public boolean copiarA(GenericRepository destino) {
		ArrayList<Libro> todos = obtenerTodos();
		for (Libro l : todos) {
			destino.insertar(l);
		}
		return true;
	}

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