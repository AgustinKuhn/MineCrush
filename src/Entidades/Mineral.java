package Entidades;

import Logica.Color;

/**
 * Modela el comportamiento de los Caramelos.
 */
public class Mineral extends Entidad {
	
	public Mineral(int f, int c, Color col, String imagen) {
		super(f, c, col, imagen); //"/imagenes/caramelos/"
	}
	
	public boolean es_posible_intercambiar(Entidad e) {
		if(detonada) return false;
		if(e.detonada) return false;
		return e.puede_recibir(this);
	}
	
	public boolean puede_recibir(Mineral m) {
		return true;
	}
	
	public boolean puede_recibir(Obsidiana o) {
		return false;
	}
	
	public boolean puede_recibir(Potenciador p) {
		return p.puede_recibir(this);
	}
	
	public boolean puede_recibir(Creeper c) {
		return true;
		
	}
	@Override
	public boolean matcheable() {
		return true;
	}
}