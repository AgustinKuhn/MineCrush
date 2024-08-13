package Entidades;

import Logica.Color;

/**
 * Modela el comportamiento de los Glaseados.
 */
public class Obsidiana extends Entidad {

	private static int puntaje = 25;
	
	public Obsidiana(int f, int c, Color col,String imagenes) {
		super(f, c, col, imagenes);
	}
	
	@Override
	public boolean es_posible_intercambiar(Entidad e) {
		return false;
	}
	
	@Override
	public boolean puede_recibir(Mineral m) {
		return false;
	}
	
	@Override
	public boolean puede_recibir(Obsidiana o) {
		return false;
	}
	
	@Override
	public boolean puede_recibir(Potenciador p) {
		return false;
	}
	
	public boolean puede_recibir(Creeper c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean matcheable() {
		return false;
	}
	
	public int get_puntaje() {
		return puntaje;
	}

	@Override
	public boolean derribable() {
		return true;
	}
	
}