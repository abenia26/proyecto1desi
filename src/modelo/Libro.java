// Hector Abenia
package modelo;

public class Libro {
	protected String id;
	protected String titulo;
	protected String autor;
	protected double precio;
	protected int stock;

	public Libro(String id, String titulo, String autor, double precio, int stock) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.precio = precio;
		this.stock = stock;
	}

	public Libro(String titulo, String autor, double precio, int stock) {
		super();
		this.titulo = titulo;
		this.autor = autor;
		this.precio = precio;
		this.stock = stock;
	}

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
