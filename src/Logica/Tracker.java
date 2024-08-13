package Logica;

import java.util.ArrayList;

import Entidades.Entidad;

public class Tracker {
	protected Objetivo mi_objetivo;
	protected Puntuacion puntuacion;
	
	public Tracker(Objetivo o, Puntuacion puntuacion) {
		mi_objetivo = o;
		this.puntuacion = puntuacion;
	}
	
	public void agregarAlTrucker(ArrayList<Entidad> l){
		for(Entidad e : l) {
			puntuacion.sumarPuntos(e.get_puntaje());
			mi_objetivo.reducirCantidad(e.get_color());	
		}	
	}
	public void descontarColor(int color) {
		mi_objetivo.reducirCantidad(color);
	}
}