package Entidades;

import java.util.ArrayList;

import Logica.Color;
import Logica.Tablero;

public class TNT extends Potenciador{

	private static int puntaje = 50;
	
	public TNT(int f, int c, Color	col,Tablero t,String imagenes) {
		super(f, c, col, imagenes,t);
	}
	
	public void detonar() {
		detonada = true;
		entidad_grafica.notificarse_cambio_estado();
		destruir_area();
	}
	
	private void destruir_area() {
		ArrayList<Entidad> destruir = new ArrayList<Entidad>();
		int f = fila;
		int c = columna;
		Entidad e = null;
		for(f = fila -1; f <= fila + 1; f++)
			for(c = columna - 1; c <= columna + 1; c++)
				if(tablero.en_rango(f, c)) {
					e = tablero.get_entidad(f, c);
					if(!e.detonada)
						destruir.add(e);
				}
		tablero.eliminar_entidades(destruir);
	}
	
	public int get_puntaje() {
		return puntaje;
	}
	
}