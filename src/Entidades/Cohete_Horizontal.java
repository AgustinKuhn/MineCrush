package Entidades;

import Logica.Color;
import Logica.Tablero;
import java.util.ArrayList;

public class Cohete_Horizontal extends Potenciador{
	
	private static int puntaje = 45;
	
	public Cohete_Horizontal(int f, int c, Color col,Tablero t,String imagenes) {
		super(f, c, col, imagenes, t);
	}
	
	public void detonar() {
		detonada = true;
		entidad_grafica.notificarse_cambio_estado();
		destruir_fila();
	}
	
	private void destruir_fila() {
		ArrayList<Entidad> destruir = new ArrayList<Entidad>();
		
		for(int c = 0; c < tablero.get_columnas(); c++) {
			Entidad e =tablero.get_entidad(fila, c);
			if(!e.detonada())
				destruir.add(e);	
		}
		tablero.eliminar_entidades(destruir);
	}
	
	public int get_puntaje() {
		return puntaje;
	}
	
}