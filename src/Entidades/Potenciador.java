package Entidades;

import Logica.Color;
import Logica.Tablero;
import java.util.ArrayList;
/**
 * Modela el comportamiento de los Potenciadores.
 */
public abstract class Potenciador extends Entidad {
	
	Tablero tablero;
	
	public Potenciador(int f, int c, Color col, String path_img,Tablero t) {
		super(f, c, col, path_img);
		tablero = t;
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
	
	public boolean puede_recibir(Creeper c) {
		return true;
	}

	public  boolean puede_recibir(Potenciador p) {
		ArrayList<Entidad> l = new ArrayList<Entidad>();
		l.add(this);
		l.add(p);
		tablero.eliminar_entidades(l);
		return true;
	}
	
	public boolean matcheable() {
		return true;
	}
	
}