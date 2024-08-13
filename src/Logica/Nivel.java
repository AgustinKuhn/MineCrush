package Logica;

/**
 * Modela la clase nivel. Se espera que el mismo permita observar los objetivos esperados para el nivel.
 * También se espera que permita mantener cuenta de los objetivos cumplidos y no, y que permita que el juego pueda consultar si
 * los objetivos fueron cumplimentados, para transicionar de nivel.
 */
public class Nivel {
	
	protected int fila_inicial_jugador, columna_inicial_jugador, vidas, movimientos, tiempo;
	protected Objetivo objetivos;

	public Nivel(int f, int c, int v, int m, int t,Objetivo o) {
		fila_inicial_jugador = f;
		columna_inicial_jugador = c;
		vidas = v;
		movimientos = m;
		tiempo = t;
		objetivos = o;
	}
	
	public Objetivo getObjetivos() {
		return objetivos;
	}
	
	public int get_fila_inicial_jugador() {
		return fila_inicial_jugador;
	}
	
	public int get_columna_inicial_jugador() {
		return columna_inicial_jugador;
	}
	
	public void setTiempo(int t)
	{
		tiempo = t;
	}
	
	public void setMovimientos(int m)
	{
		movimientos = m;
	}
	
	public void setVidas(int v)
	{
		vidas = v;
	}
	
	public int getTiempo()
	{
		return tiempo;
	}
	
	public int getMovimientos()
	{
		return movimientos;
	}
	
	public int getVidas()
	{
		return vidas;
	}
}