// Hector Abenia
package modelo;

/**
 * Representa un libro de la biblioteca con su id, título, autor, precio y
 * stock. Incluye la conversión desde y hacia una línea CSV separada por
 * {@code ^}, que usan los repositorios para guardar los libros en archivo.
 *
 * @author Hector Abenia
 */
public class Libro {
	protected String id;
	protected String titulo;
	protected String autor;
	protected double precio;
	protected int stock;

	/**
	 * Crea un libro indicando todos sus datos, incluido el id.
	 *
	 * @param id     identificador del libro
	 * @param titulo título del libro
	 * @param autor  autor del libro
	 * @param precio precio del libro
	 * @param stock  unidades disponibles del libro
	 */
	public Libro(String id, String titulo, String autor, double precio, int stock) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.precio = precio;
		this.stock = stock;
	}

	/**
	 * Crea un libro sin id, dejándolo en {@code null}.
	 *
	 * @param titulo título del libro
	 * @param autor  autor del libro
	 * @param precio precio del libro
	 * @param stock  unidades disponibles del libro
	 */
	public Libro(String titulo, String autor, double precio, int stock) {
		super();
		this.titulo = titulo;
		this.autor = autor;
		this.precio = precio;
		this.stock = stock;
	}

	/**
	 * Crea un libro vacío, sin ningún dato asignado. Los datos se rellenan después
	 * con los métodos {@code set}.
	 */
	public Libro() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String toCSV() {
		return id + "^" + titulo + "^" + autor + "^" + precio + "^" + stock;
	}

	public static Libro fromCSV(String linea) {
		String[] campos = linea.split("\\^");
		return new Libro(campos[0], campos[1], campos[2], Double.parseDouble(campos[3]),
				Integer.parseInt(campos[4]));
	}

	@Override
	public String toString() {
		return "Libro [id=" + id + ", titulo=" + titulo + ", autor=" + autor + ", precio=" + precio + ", stock=" + stock
				+ "]";
	}

}
