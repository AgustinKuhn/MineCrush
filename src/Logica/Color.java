package Logica;

/**
 * Define los colores estandar a utilizar en los Caramelos.
 */
public class Color {
	
	private String nombreColor;
	private int color;
	private int puntuacion;
	
	private Color(int color, int puntuacion) {
		this.setColor(color);
		this.setPuntuacion(puntuacion);
	}
	
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getNombreColor() {
		return nombreColor;
	}

	public void setNombreColor(String nombreColor) {
		this.nombreColor = nombreColor;
	}

	public static final Color ESMERALDA = new Color(1,10);
	public static final Color HIERRO = new Color(2,15);
	public static final Color REDSTONE = new Color(3,5);
	public static final Color ORO = new Color(4,20);
	public static final Color LAPISLAZULI = new Color(5,20);
	public static final Color DIAMANTE = new Color(6,25);
	public static final Color OBSIDIANA = new Color(7,25);
	public static final Color SLIME = new Color(10,10);
	public static final Color CREEPER = new Color(11,150);
}