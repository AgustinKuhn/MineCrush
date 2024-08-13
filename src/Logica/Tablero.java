package Logica;

import java.util.ArrayList;
import java.util.Random;

import Entidades.Cohete_Horizontal;
import Entidades.Cohete_Vertical;
import Entidades.Creeper;
import Entidades.Entidad;
import Entidades.Mineral;
import Entidades.TNT;
import Entidades.Pico;
import GUI.Dominio;

/**
 * Modela el tablero de la aplicación. Mantiene control sobre las entidades, y provee acceso a ellas.
 */
public class Tablero {
	
	protected Juego mi_juego;
	protected Entidad [][] entidades;
	protected int filas, columnas, pos_f_jugador, pos_c_jugador;
	
	protected Tracker mi_tracker;
	
	public Tablero(Juego j) {
		mi_juego = j;
		filas = 0;
		columnas = 0;
	}
	
	public void setTrucker(Tracker t) {
		mi_tracker = t;
	}
	public void descontarColor(int color) {
		mi_tracker.descontarColor(color);
	}
	public int get_filas() {
		return filas;
	}
	
	public int get_columnas() {
		return columnas;
	}
	
	public Entidad get_entidad(int f, int c) {
		return entidades[f][c];
	}
	
	public void resetar_tablero(int f, int c) {
		filas = f;
		columnas = c;
		pos_f_jugador = 0;
		pos_c_jugador = 0;
		entidades = new Entidad[f][c];
	}
	
	public void agregar_entidad(Entidad e) {
		entidades[e.get_fila()][e.get_columna()] = e;
	}
	
	public void fijar_jugador(int f, int c) {
		if (entidades[f][c].enfocar()) {
			entidades[pos_f_jugador][pos_c_jugador].desenfocar();
			pos_f_jugador = f;
			pos_c_jugador = c;
		}
	}
	
	public void mover_jugador(int d) {
		switch(d) {
			case Juego.ABAJO:{
				mover_jugador_auxiliar(pos_f_jugador + 1, pos_c_jugador);
				break;
			}
			case Juego.ARRIBA:{
				mover_jugador_auxiliar(pos_f_jugador - 1, pos_c_jugador);
				break;
			}
			case Juego.IZQUIERDA:{
				mover_jugador_auxiliar(pos_f_jugador, pos_c_jugador - 1);
				break;
			}
			case Juego.DERECHA:{
				mover_jugador_auxiliar(pos_f_jugador, pos_c_jugador + 1);
				break;
			}
		}
	}
	
	public boolean intercambiar(int d) {
		boolean huboDestruccion = false;
		switch(d) {
		case Juego.ABAJO:{
			huboDestruccion = intercambiar_auxiliar(pos_f_jugador + 1, pos_c_jugador);
			break;
		}
		case Juego.ARRIBA:{
			huboDestruccion = intercambiar_auxiliar(pos_f_jugador - 1, pos_c_jugador);
			break;
		}
		case Juego.IZQUIERDA:{
			huboDestruccion = intercambiar_auxiliar(pos_f_jugador, pos_c_jugador - 1);
			break;
		}
		case Juego.DERECHA:{
			huboDestruccion = intercambiar_auxiliar(pos_f_jugador, pos_c_jugador + 1);
			break;
		}
		}
		return huboDestruccion;
	}
	
	private void mover_jugador_auxiliar(int nf, int nc) {
		if (en_rango(nf,nc)) {
			if (entidades[nf][nc].enfocar()) {
				entidades[pos_f_jugador][pos_c_jugador].desenfocar();
				pos_f_jugador = nf;
				pos_c_jugador = nc;
			}
		}
	}
	
	private boolean intercambiar_auxiliar(int nf, int nc) {
		boolean huboDestruccion = false;
		int af = pos_f_jugador;
		int ac = pos_c_jugador;
		boolean no_intercambio = true;
		if ( en_rango(nf, nc) ) {	
			if (entidades[af][ac].es_posible_intercambiar( entidades[nf][nc] )) {
				aplicar_intercambio(af, ac, nf, nc);
				ArrayList<Entidad> [] entidades_adyacentes_a_e1 = entidades_adyacentes(entidades[af][ac]);
				ArrayList<Entidad> [] entidades_adyacentes_a_e2 = entidades_adyacentes(entidades[nf][nc]);		
				if (posible_intercambiar(entidades_adyacentes_a_e1)) {
					eliminar_matchs(entidades_adyacentes_a_e1);
					no_intercambio = false;
				}
				if(posible_intercambiar(entidades_adyacentes_a_e2)) {
					eliminar_matchs(entidades_adyacentes_a_e2);
					no_intercambio = false;
				}
				if (no_intercambio) {
					aplicar_intercambio(af, ac, nf, nc);
				}else {
					
					eliminar_matchs(entidades_adyacentes(entidades[af][ac]));
					eliminar_matchs(entidades_adyacentes(entidades[nf][nc]));
					
					huboDestruccion = true;
					
					//actualizo la posicion del jugador cuando se intercambia
					pos_f_jugador = nf;
					pos_c_jugador = nc;
				}
			}	
		}
		return huboDestruccion;
	}
	
	private void aplicar_intercambio(int af, int ac, int nf, int nc) {

		Entidad entidad_anterior;
		Entidad nueva_entidad;
		
		entidad_anterior = entidades[af][ac].cambiar_posicion(nf, nc);
		nueva_entidad = entidades[nf][nc].cambiar_posicion(af, ac);
		
		entidades[af][ac].asociar_enTablero(nueva_entidad,this);
		entidades[nf][nc].asociar_enTablero(entidad_anterior, this);
		
		
	}
	
	private boolean posible_intercambiar(ArrayList<Entidad> [] l){
		boolean toReturn = false;
		if(l[0].size() >= 3 || l[1].size() >= 3)
			toReturn = true;
		return toReturn;
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<Entidad> [] entidades_adyacentes(Entidad e){
		ArrayList<Entidad> [] toReturn = new ArrayList[3];
		ArrayList<Entidad> AdyacentesHorizontales = new ArrayList<Entidad>();
		ArrayList<Entidad> AdyacentesVerticales = new ArrayList<Entidad>();
		ArrayList<Entidad> entidad = new ArrayList<Entidad>();
		entidad.add(e);
		int c = e.get_columna();
		int f = e.get_fila();
		while(en_rango(f,c) && e.matcheable() && e.get_color() == entidades[f][c].get_color() && !entidades[f][c].detonada()) {
			AdyacentesHorizontales.add(entidades[f][c]);
			c--;
		}
		c = e.get_columna() + 1;
		f = e.get_fila();
		while(en_rango(f,c) && e.matcheable() && e.get_color() == entidades[f][c].get_color() && !entidades[f][c].detonada()) {
			AdyacentesHorizontales.add(entidades[f][c]);
			c++;
		}
		c = e.get_columna();
		f = e.get_fila();
		while(en_rango(f,c) && e.matcheable() && e.get_color() == entidades[f][c].get_color() && !entidades[f][c].detonada()) {
			AdyacentesVerticales.add(entidades[f][c]);
			f--;
		}
		c = e.get_columna();
		f = e.get_fila() + 1;
		while(en_rango(f,c) && e.matcheable() && e.get_color() == entidades[f][c].get_color() && !entidades[f][c].detonada()) {
			AdyacentesVerticales.add(entidades[f][c]);
			f++;
		}
		toReturn[0] = AdyacentesHorizontales;
		toReturn[1] = AdyacentesVerticales;
		toReturn[2] = entidad;
		return toReturn;
	}
	
	private ArrayList<Entidad> entidades_derribables(Entidad e) {
		ArrayList<Entidad> derribables= new ArrayList<Entidad>();
		if(en_rango(e.get_fila()-1,e.get_columna()))
			if(entidades[e.get_fila()-1][e.get_columna()].derribable())
				derribables.add(entidades[e.get_fila()-1][e.get_columna()]);
		if(en_rango(e.get_fila()+1,e.get_columna()))
			if(entidades[e.get_fila()+1][e.get_columna()].derribable())
				derribables.add(entidades[e.get_fila()+1][e.get_columna()]);
		if(en_rango(e.get_fila(),e.get_columna()-1))
			if(entidades[e.get_fila()][e.get_columna()-1].derribable())
				derribables.add(entidades[e.get_fila()][e.get_columna()-1]);
		if(en_rango(e.get_fila(),e.get_columna()+1))
			if(entidades[e.get_fila()][e.get_columna()+1].derribable())
				derribables.add(entidades[e.get_fila()][e.get_columna()+1]);
		return derribables;
	}
	
	public void eliminar_entidades(ArrayList<Entidad> l) {
		if(!l.isEmpty()) {
			for(Entidad e : l) 
				e.detonar();
			mi_tracker.agregarAlTrucker(l);
			gravedad(lista_vacios());
			rellenar_tablero();
		}
	}
	
	private void eliminar_matchs(ArrayList<Entidad> [] a) {
		Entidad enti = a[2].get(0);
		Color color = enti.get_objeto_color();
		ArrayList<Entidad> destruidos = new ArrayList<Entidad>();
		ArrayList<Entidad> derribables = null;
		int puntaje = retorno_puntuacion(a);
		if(a[1].size() >= 3) 
			for(Entidad e : a[1]){
					destruidos.add(e);
					derribables = entidades_derribables(e);
					for (Entidad o : derribables) {
						destruidos.add(o);
					}
				}
		
		if(a[0].size() >= 3) 
			for(Entidad e : a[0]) {
					destruidos.add(e);
					derribables = entidades_derribables(e);
					for (Entidad o : derribables) {
						destruidos.add(o);
					}
				}
		
		
		switch(puntaje) {
		case 1 : remplazar_entidad(enti,new Cohete_Vertical(enti.get_fila(),enti.get_columna(),color,this,mi_juego.getDominio().imagenesDeCohete_Vertical() ));
		break;
		case 2 : remplazar_entidad(enti,new Cohete_Horizontal(enti.get_fila(),enti.get_columna(),color,this, mi_juego.getDominio().imagenesDeCohete_Horizontal()));
		break;
		case 3 : remplazar_entidad(enti,new TNT(enti.get_fila(),enti.get_columna(),color,this, mi_juego.getDominio().imagenesDeEnvuelto()));
		break;
		}
		eliminar_entidades(destruidos);

	}
	
	public void actualizar_tablero() {
		boolean hubo_match = false;
		for(int f = 0; f < entidades.length; f++) 
			for(int c = entidades[0].length - 1; c >= 0; c--) {
				ArrayList<Entidad>[] l = entidades_adyacentes(entidades[f][c]);
				if(posible_intercambiar(l)) {
					eliminar_matchs(l);
					hubo_match = true;
				}
			}
		if(hubo_match) {
			gravedad(lista_vacios());
			rellenar_tablero();
		}
	}
	
	private int retorno_puntuacion(ArrayList<Entidad> [] a) {
		int toReturn = 0;
		if(a[0].size() == 3 && a[1].size() == 3)
			return 3;
		if(a[1].size() >= 4)
			return 2;
		if(a[0].size() >= 4)
			return 1;
		return toReturn;	
	}
	
	private void remplazar_entidad(Entidad e, Entidad en) {
		
		e.desenfocar();
		
		entidades[e.get_fila()][e.get_columna()].asociar_enTablero(en, this);
		
		entidades[e.get_fila()][e.get_columna()].cambiar_entidad(e);
	}
	
	public boolean en_rango(int nf, int nc){
		return  ((0 <= nf) && (nf < filas) && (0 <= nc) && (nc < columnas)) && entidades[nf][nc] != null;
	}

	public void gravedad(ArrayList<Entidad> a) {
		for(Entidad e : a) {
			int f = e.get_fila();
			int c = e.get_columna();
			if(e.detonada())
				gravedad_auxiliar(f,c);
			}
		
	}
	
	private void gravedad_auxiliar(int f, int c) {
		int nf = f - 1;
		
		if (!detenete_aca(f,c) && en_rango(nf,c)){	
			aplicar_intercambio(f, c, nf, c);
			gravedad_auxiliar(nf,c);
		}
		
	}
	
	private boolean detenete_aca(int f, int c) {
			if(entidades[f][c].detonada()) {
				if(en_rango(f-1,c))
					return detenete_aca(f-1,c);
			}else
				if(!entidades[f][c].detonada())
					return false;
		return true;
	}
	
	public ArrayList<Entidad> lista_vacios(){
		ArrayList<Entidad> vacios = new ArrayList<Entidad>();
		for(int f = filas - 1; f >= 0;f--)
			for(int c = 0; c < columnas;c++)
				if(entidades[f][c].detonada() || entidades[f][c].get_color() == Color.SLIME.getColor() )
					vacios.add(entidades[f][c]);
		return vacios;			
				
	}
	
	public void rellenar_tablero() {
		ArrayList<Entidad> l = lista_vacios();
		for(Entidad e : l) {
			
			Color[] colores = {Color.ESMERALDA,Color.HIERRO,Color.REDSTONE,Color.ORO,Color.LAPISLAZULI,Color.DIAMANTE};
			Random random = new Random();
		    int indiceAleatorio = random.nextInt(15);
		    
		    if(indiceAleatorio == 2)
		    	remplazar_entidad(e,new Pico(e.get_fila(),e.get_columna(),colores[random.nextInt(colores.length)], this, mi_juego.getDominio().imagenesDeTdp1()));
		    else
		    	remplazar_entidad(e,new Mineral(e.get_fila(),e.get_columna(),colores[random.nextInt(colores.length)], mi_juego.getDominio().imagenesDeCaramelos()));
			
		}
		if(!l.isEmpty())
			actualizar_tablero();
	}


	public void descontarBombas(){ //asco
		for (int i = 0; i < entidades.length; i++) {
			for (int j = 0; j < entidades[0].length; j++) {
				if (entidades[i][j].get_color() == Color.CREEPER.getColor()){
					//entidades[i][j].descontarTimer();
				}
			}
		}
	}
}