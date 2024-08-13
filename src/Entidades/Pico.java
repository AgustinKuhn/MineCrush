package Entidades;

import java.util.ArrayList;

import Logica.Color;
import Logica.Tablero;

public class Pico extends Potenciador{
	
	private static int puntaje = 100;
	
	public Pico(int f, int c,Color color, Tablero t,String imagen) {
		super(f, c, color, imagen, t);
	}

	public boolean puede_recibir(Mineral m) {
		if(m.get_color() == color.getColor()) 
			detonar();
		return true;

	}
	
	public void detonar() {
		detonada = true;
		entidad_grafica.notificarse_cambio_estado();
		destruir_columna_fila();
	}
	
	private void destruir_columna_fila() {
		ArrayList<Entidad> destruir = new ArrayList<Entidad>();
		Entidad e = null;
		for(int c = 0; c < tablero.get_columnas(); c++) {
			e =tablero.get_entidad(fila, c);
			if(!e.detonada())
				destruir.add(e);	
		}
		for(int f = 0; f < tablero.get_filas(); f++) { 
			e =tablero.get_entidad(f, columna);
			if(!e.detonada())
				destruir.add(e);
		}
		tablero.eliminar_entidades(destruir);
	}
	
	public boolean matcheable() {
		return false;
	}
	
	public int get_puntaje() {
		return puntaje;
	}
	
}
