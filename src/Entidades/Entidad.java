package Entidades;

import GUI.EntidadGrafica;
import GUI.PanelDeNivel;
import Logica.Color;
import Logica.EntidadLogica;
import Logica.Tablero;

/**
 * Generaliza el comportamiento estándar de todas las entidades que forman parte del tablero.
 */
public abstract class Entidad implements EntidadLogica, Enfocable, Intercambiable, Detonable {
	protected int fila, columna;
	protected Color color;
	protected boolean enfocada, detonada;
	
	protected String [] imagenes_representativas;
	protected EntidadGrafica entidad_grafica;
	
	/**
	 * Inicializa el estado interno de una entidad, considerando
	 * @param f La fila donde se ubica la entidad.
	 * @param c La columna donde se ubica la entidad.
	 * @param col El color asociado a la entidad. Se asume constante de la clase Color.
	 * @param path_img Ruta donde se encuentran todas las imágenes asociadas a la entidad creada.
	 */
	protected Entidad(int f, int c, Color col, String path_img) {
		fila = f;
		columna = c;
		color = col;
		enfocada = false;
		detonada = false;
		cargar_imagenes_representativas(path_img);
	}
	
	/**
	 * Vincula el elemento con su entidad gráfica asociada.
	 * @param e Entidad gráfica que se encuentra asociada al elemento.
	 */
	public void set_entidad_grafica(EntidadGrafica e) {
		entidad_grafica = e;
	}
	
	/**
	 * Retorna la fila donde se ubica la entidad.
	 */
	public int get_fila() {
		return fila;
	}
	
	/**
	 * Retorna la columna donde se ubica la entidad.
	 */
	public int get_columna() {
		return columna;
	}
	
	/**
	 * Retorna el color asociado a la entidad.
	 * @return Constante numérica que representa el color de la entidad. Se asume un valor declarado en clase Color.
	 */
	public int get_color() {
		return color.getColor();
	}
	
	public Color get_objeto_color(){
		return color;
	}
	
	public int get_puntaje() {
		return color.getPuntuacion();
	}
	
	public EntidadGrafica getEntidad_Grafica() {
		return entidad_grafica;
	}
	public String get_imagen_representativa() {
		int indice = 0;
		indice += (enfocada ? 1 : 0);
		indice += (detonada ? 2 : 0);
		return imagenes_representativas[indice];
	}
	
	@Override
	public boolean enfocar() {
		enfocada = true;
		entidad_grafica.notificarse_cambio_estado();
		return true;
	}
	
	@Override
	public void desenfocar() {
		enfocada = false;
		entidad_grafica.notificarse_cambio_estado();
	}
	
	@Override
	public Entidad cambiar_posicion(int nf, int nc) {
		fila = nf;
		columna = nc;
		entidad_grafica.notificarse_cambio_posicion();
		return this;
	}
	public void cambiar_entidad(Entidad e){
		entidad_grafica = e.getEntidad_Grafica();
		entidad_grafica.cambiar_entidad_logica(this);
		entidad_grafica.notificarse_spawn_entidad();	
	}
	public void asociar_entidad(PanelDeNivel v) {
		EntidadGrafica eg;
		eg = v.agregar_entidad(this);
		entidad_grafica = eg;
	}
	
	@Override
	public void detonar() {
		detonada = true;
		entidad_grafica.notificarse_cambio_estado();
	}
	
	public boolean detonada() {
		return detonada;
	}

	@Override
	public boolean derribable() {
		return false;
	}
	
	/**
	 * Inicializa el arreglo de paths que establecen las imágenes asociadas a los diferentes estados de la entidad.
	 * @param path_img Ruta donde se encuentran todas las imágenes asociadas a la entidad creada.
	 */
	private void cargar_imagenes_representativas(String path_img) {
		imagenes_representativas = new String [4];
		imagenes_representativas[0] = path_img + color.getColor() +".png";
		imagenes_representativas[1] = path_img + color.getColor() +"-resaltado.png";
		imagenes_representativas[2] = "/imagenes/entidad-vacia/vacia.png"; // antes "/imagenes/especiales/" + Color.VACIO +".png"
		imagenes_representativas[3] = "/imagenes/entidad-vacia/vacia-resaltado.png"; //antes "/imagenes/especiales/" + Color.VACIO +"-resaltado.png"
	}

	public void asociar_enTablero(Entidad e, Tablero t) {
		t.agregar_entidad(e);
	}
}