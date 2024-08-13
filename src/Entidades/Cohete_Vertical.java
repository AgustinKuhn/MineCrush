package Entidades;

import java.util.ArrayList;

import Logica.Color;
import Logica.Tablero;

public class Cohete_Vertical extends Potenciador{

	private static int puntaje = 35;
	
	public Cohete_Vertical(int f, int c, Color col,Tablero t,String imagenes) {
		super(f, c, col,imagenes,t);
	}

	public void detonar() {
		detonada = true;
		entidad_grafica.notificarse_cambio_estado();
		destruir_columna();
	}
	
	private void destruir_columna() {
		ArrayList<Entidad> destruir = new ArrayList<Entidad>();
		
		for(int f = 0; f < tablero.get_filas(); f++) { 
			Entidad e = tablero.get_entidad(f, columna);
			if(!e.detonada())
				destruir.add(e);
		}
		tablero.eliminar_entidades(destruir);
	}
	
	public int get_puntaje() {
		return puntaje;
	}
}