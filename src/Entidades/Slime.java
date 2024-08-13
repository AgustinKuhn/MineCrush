package Entidades;

import GUI.EntidadGrafica;
import GUI.PanelDeNivel;
import Logica.Color;
import Logica.Tablero;

public class Slime extends Entidad{
	
	private Entidad mi_entidad;
	private Tablero mi_tablero;
	
	public Slime(int f, int c,Entidad e,Tablero t,String imagen) {	
		super(f, c, Color.SLIME, imagen);
		mi_entidad = e;
		mi_tablero = t;
	}
	
	public void asociar_entidad(PanelDeNivel v) {
		EntidadGrafica eg;
		eg = v.agregar_entidad(this);
		entidad_grafica = eg;
		eg  = v.agregar_entidad(mi_entidad);
		mi_entidad.set_entidad_grafica(eg);
	}
	
	@Override
	public void detonar() {
		mi_tablero.descontarColor(mi_entidad.get_color());
		detonada = true;
		getEntidad_Grafica().notificarse_cambio_estado();
		mi_entidad.desenfocar();
		mi_entidad.detonar();
		
	}
	
	public boolean enfocar() {
		mi_entidad.enfocar();
		return true;
	}
	
	public void desenfocar() {
		mi_entidad.desenfocar();
	}
	public Entidad cambiar_posicion(int nf, int nc) {
		mi_entidad.fila = nf;
		mi_entidad.columna = nc;
		mi_entidad.getEntidad_Grafica().notificarse_cambio_posicion();
		return mi_entidad;
	}
	
	
	public int get_color() {
		int toReturn  = mi_entidad.get_color();
		if(mi_entidad.detonada())
			toReturn = Color.SLIME.getColor();
		return toReturn;
	}
	
	public Color get_objeto_color(){
		return mi_entidad.color;
	}
	
	public void asociar_enTablero(Entidad e, Tablero t) {
		if(!detonada) {	
			if(e.getEntidad_Grafica() == null)
				e.cambiar_entidad(mi_entidad);
			mi_entidad = e;
			entidad_grafica.notificarse_cambio_estado();
			mi_entidad.getEntidad_Grafica().notificarse_cambio_estado();
		}
		else {
			t.agregar_entidad(e);
		}
	}

	public boolean es_posible_intercambiar(Entidad e) {
		return e.es_posible_intercambiar(mi_entidad);
	}
	
	public boolean puede_recibir(Mineral m) {
		
		return mi_entidad.puede_recibir(m);
	}

	public boolean puede_recibir(Obsidiana o) {

		return mi_entidad.puede_recibir(o);
	}

	public boolean puede_recibir(Potenciador p) {

		return mi_entidad.puede_recibir(p);
	}
	
	public boolean puede_recibir(Creeper c) {
		return mi_entidad.puede_recibir(c);
	}
	public Entidad get_entidad() {
		return mi_entidad;
	}

	@Override
	public boolean matcheable() {
		return mi_entidad.matcheable();
	}
	
	public int get_puntaje() {
		return color.getPuntuacion() + mi_entidad.get_objeto_color().getPuntuacion();
	}
	@Override
	public boolean derribable() {
		return mi_entidad.derribable();
	}

}