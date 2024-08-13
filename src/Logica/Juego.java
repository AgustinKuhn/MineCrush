package Logica;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Entidades.Entidad;
import GUI.Dominio;
import GUI.PanelDeNivel;
import GUI.VentanaJuego;

/**
 * Modela el comportamiento del Juego.
 * Ofrece servicios para comunicar los diferentes elementos que conforman la lógica de la aplicación con la gráfica de la misma.
 */
public class Juego {
	
	public static final int ARRIBA = 15000;
	public static final int ABAJO = 15001;
	public static final int IZQUIERDA = 15002;
	public static final int DERECHA = 15003;
	
	protected Tablero mi_tablero;
	protected VentanaJuego ventana_juego;
	protected PanelDeNivel panel_del_nivel;
	protected Nivel mi_nivel;
	protected Objetivo mi_objetivo_actual;
	protected boolean juego_ganado = false;
	protected int numero_nivel;
	protected Tracker mi_tracker;
	protected String imagenes_de_caramelos,nombre_del_juego;
	protected Dominio dominio;
	protected Puntuacion puntuacion;
	
	public Juego() 
	{
		mi_tablero = new Tablero(this);
		ventana_juego = new VentanaJuego(this);
	}
	
	public PanelDeNivel iniciarJuego(int numero_del_nivel,String fondo, Dominio dominio)
	{
		this.dominio = dominio;
		mi_nivel = GeneradorNivel.cargar_nivel_y_tablero(numero_del_nivel, mi_tablero,this,dominio);
		panel_del_nivel = new PanelDeNivel(this, mi_tablero.get_filas(), mi_tablero.get_columnas(),fondo,numero_del_nivel, ventana_juego);
		asociar_entidades_logicas_graficas();
		mi_tablero.fijar_jugador(mi_nivel.get_fila_inicial_jugador(), mi_nivel.get_columna_inicial_jugador());
		
		puntuacion = new Puntuacion();
		mi_objetivo_actual = mi_nivel.getObjetivos();
		mi_tracker = new Tracker(mi_objetivo_actual,puntuacion);
		mi_tablero.setTrucker(mi_tracker);
		juego_ganado = false;
		numero_nivel = numero_del_nivel;
		
		return panel_del_nivel;
	}
	
	public PanelDeNivel reiniciarJuego(int numero_del_nivel,String fondo,int vidas)
	{
		mi_nivel = GeneradorNivel.cargar_nivel_y_tablero(numero_del_nivel, mi_tablero,this,dominio);
		mi_nivel.setVidas(vidas);
		panel_del_nivel = new PanelDeNivel(this, mi_tablero.get_filas(), mi_tablero.get_columnas(),fondo,numero_del_nivel,ventana_juego);
		asociar_entidades_logicas_graficas();
		mi_tablero.fijar_jugador(mi_nivel.get_fila_inicial_jugador(), mi_nivel.get_columna_inicial_jugador());
		
		puntuacion = new Puntuacion();
		mi_objetivo_actual = mi_nivel.getObjetivos();
		mi_tracker = new Tracker(mi_objetivo_actual,puntuacion);
		mi_tablero.setTrucker(mi_tracker);
		juego_ganado = false;
		numero_nivel = numero_del_nivel;
		
		return panel_del_nivel;
	}
	
	public void setNombreDelJuego(String nombre)
	{
		nombre_del_juego = nombre;
	}
	
	public Dominio getDominio()
	{
		return dominio;
	}
	
	public int getPuntuacion()
	{
		return puntuacion.getPuntaje();
	}
	public Nivel getNivel() {
		return mi_nivel;
	}
	
	public boolean sinObjetivos()
	{
		return mi_objetivo_actual.isEmpty();
	}
	
	public String mostrar_objetivos() 
	{
		return mi_objetivo_actual.getObjetivos();
	}
	
	public void juego_ganado() {
		juego_ganado = true;
		if(numero_nivel < 6) {
			panel_del_nivel.activarPasarNivel();
		}
		else {	
			panel_del_nivel.ganarJuego();
		}
	}
	
	public void mover_jugador(int d) {
		mi_tablero.mover_jugador(d);
	}
	
	public boolean intercambiar(int d) {
		boolean intercambio = false;
		if(mi_tablero.intercambiar(d))
		{
			panel_del_nivel.eliminar1Movimiento();
			intercambio = true;
		}
		return intercambio;
	}
	
	private void asociar_entidades_logicas_graficas() {
		Entidad e;
		
		for (int f=0; f<mi_tablero.get_filas(); f++) {
			for (int c=0; c<mi_tablero.get_columnas(); c++) {
				e = mi_tablero.get_entidad(f, c);
				e.asociar_entidad(panel_del_nivel);
			}
		}
	}
	
	public void chequearPuntuacion(int puntuacion)
	{
		BufferedReader br = null;
		try {
			String archivo = "src/Ranking/Ranking"+nombre_del_juego+".txt";
			br = new BufferedReader(new FileReader(archivo));
			StringBuilder contenidoActualizado = new StringBuilder();
				boolean cortar = false;
				boolean yaEntro = false;
				// Lectura del fichero
				String linea; 
				String[] parts = null;
				String puntaje;
				String nuevoContenido = "";
				
				int i = 5;
				while(!cortar && i > 0 && (linea = br.readLine()) != null)
				{
					parts = linea.split(":");
					
					//puntaje
					puntaje = parts[1];
					puntaje = puntaje.replace(" ","");
					
					
					if(Integer.parseInt(puntaje) < puntuacion && !yaEntro)
					{
						nuevoContenido = panel_del_nivel.preguntarSiQuiereGuardarPuntaje();
						if(nuevoContenido != "")
						{
							nuevoContenido += ":" + puntuacion;
							contenidoActualizado.append(nuevoContenido).append(System.lineSeparator());
						}
						yaEntro = true;
					}
					contenidoActualizado.append(linea).append(System.lineSeparator());
					
					--i;
				}
				if(yaEntro)
					contenidoActualizado.delete(contenidoActualizado.length() - nuevoContenido.length() - 2, contenidoActualizado.length());
				br.close();
			
				BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
				bw.write(contenidoActualizado.toString());
				bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String [] args) {
		EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	new Juego();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
	}

	public void descontarBombas(){
		mi_tablero.descontarBombas();
	}
}