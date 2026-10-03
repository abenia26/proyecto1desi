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

	/**
	 * Devuelve el identificador del libro.
	 *
	 * @return el id del libro; {@code null} si no se le ha asignado ninguno
	 */
	public String getId() {
		return id;
	}

	/**
	 * Asigna el identificador del libro.
	 *
	 * @param id nuevo id del libro
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Devuelve el título del libro.
	 *
	 * @return el título del libro
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Asigna el título del libro.
	 *
	 * @param titulo nuevo título del libro
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * Devuelve el autor del libro.
	 *
	 * @return el autor del libro
	 */
	public String getAutor() {
		return autor;
	}

	/**
	 * Asigna el autor del libro.
	 *
	 * @param autor nuevo autor del libro
	 */
	public void setAutor(String autor) {
		this.autor = autor;
	}

	/**
	 * Devuelve el precio del libro.
	 *
	 * @return el precio del libro
	 */
	public double getPrecio() {
		return precio;
	}

	/**
	 * Asigna el precio del libro.
	 *
	 * @param precio nuevo precio del libro
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	/**
	 * Devuelve las unidades disponibles del libro.
	 *
	 * @return el stock del libro
	 */
	public int getStock() {
		return stock;
	}

	/**
	 * Asigna las unidades disponibles del libro.
	 *
	 * @param stock nuevo stock del libro
	 */
	public void setStock(int stock) {
		this.stock = stock;
	}

	/**
	 * Convierte el libro en una línea de texto con sus cinco campos (id, título,
	 * autor, precio y stock) separados por {@code ^}.
	 *
	 * @return la línea CSV que representa al libro
	 */
	public String toCSV() {
		return id + "^" + titulo + "^" + autor + "^" + precio + "^" + stock;
	}

	/**
	 * Crea un libro a partir de una línea CSV, separándola por {@code ^} y
	 * convirtiendo el precio y el stock a número. Es el proceso inverso de
	 * {@link #toCSV()}.
	 *
	 * @param linea línea con los cinco campos del libro separados por {@code ^}
	 * @return el libro con los datos de la línea
	 * @throws ArrayIndexOutOfBoundsException si la línea tiene menos de cinco
	 *                                        campos
	 * @throws NumberFormatException          si el precio o el stock no son un
	 *                                        número válido
	 */
	public static Libro fromCSV(String linea) {
		String[] campos = linea.split("\\^");
		return new Libro(campos[0], campos[1], campos[2], Double.parseDouble(campos[3]),
				Integer.parseInt(campos[4]));
	}

	/**
	 * Devuelve el libro como texto legible, con el nombre y el valor de cada campo.
	 *
	 * @return el texto con todos los datos del libro
	 */
	@Override
	public String toString() {
		return "Libro [id=" + id + ", titulo=" + titulo + ", autor=" + autor + ", precio=" + precio + ", stock=" + stock
				+ "]";
	}

}
