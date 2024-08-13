package Logica;

import java.io.*;
import java.util.Random;

import Entidades.Creeper;
import Entidades.Mineral;
import Entidades.Obsidiana;
import Entidades.Slime;
import GUI.Dominio;

/**
 * Simula el comportamiento real de un Generador de Nivel, cableando la generación de entidades de forma manual.
 * Se espera que la clase permita parsear el contenido de un archivo de texto, desde donde se generará efectivamente el nivel.
 * En esta versión, se permiten:
 * - Caramelos de todos los colores.
 * - Potenciadores de color Ladrillo
 * - Glaseados de color transparanete.
 */
public class GeneradorNivel {
	private static Color entidad_random() {
		 // Define una lista de enteros
        Color[] colores = {Color.ESMERALDA,Color.HIERRO,Color.REDSTONE,Color.ORO,Color.LAPISLAZULI,Color.DIAMANTE};

        // Crea una instancia de la clase Random
        Random random = new Random();

        // Genera un índice aleatorio dentro del rango de la lista de enteros
        int indiceAleatorio = random.nextInt(colores.length);

        // Obtiene el numero al azar utilizando el índice generado
        
        return colores[indiceAleatorio];
	}

// al agregar un nuevo nivel hay que agregar su configuracion y su fondo respetando los nombres
@SuppressWarnings("resource")
public static Nivel cargar_nivel_y_tablero(int nivel, Tablero t,Juego juego,Dominio dominio) {
		
		FileReader fr = null;
		BufferedReader br = null;
		String[] parts = null;
		String[] color_cantidad = null;
		String tiempo = null;
		String movimientos = null;
		String vidas = null;
		Objetivo objetivos = new Objetivo(dominio);
		  try {
				  
		         // Apertura del fichero y creacion de BufferedReader para poder
		         // hacer una lectura comoda (disponer del metodo readLine()).
			  	fr = new FileReader ("src/NivelesYConfiguracion/nivel"+nivel+"-config.txt");
			  	br = new BufferedReader(fr);

		         // Lectura del fichero
		         String linea;
		         linea=br.readLine();
			     parts = linea.split(":");
			     //tiempo
			     tiempo = parts[0];
			     tiempo = tiempo.replace(" ","");
				 //movimientos
			     movimientos = parts[1];
			     movimientos = movimientos.replace(" ","");	
			     //vidas
			     vidas = parts[2];
			     vidas = vidas.replace(" ","");
				 
				 String Stamaño = parts[3];
				 Stamaño = Stamaño.replace(" ","");
		         int tamaño = Integer.parseInt(Stamaño);
				 t.resetar_tablero(tamaño, tamaño);
				 
				 //lee segunda linea
				 linea = br.readLine();
				 parts = linea.split("-");
				 int obj = Integer.parseInt(parts[0]);
				 for(int i = 1 ; i <= obj; i++ ) {
					 color_cantidad = parts[i].split(":");
					 objetivos.agregarObjetivo(Integer.parseInt(color_cantidad[0]), Integer.parseInt(color_cantidad[1]));
				 }
				 
				 fr = new FileReader ("src/NivelesYConfiguracion/nivel"+nivel+".txt");
				 br = new BufferedReader(fr);
				 linea = null;
		         
		         
		         for(int f = 0; f < tamaño; f++) {
		        	 linea=br.readLine();
		        	 parts = linea.split("-");
		        	 Color color = null;
		        	 for(int c = 0 ;c < tamaño; c++) {
		        		 String entidad = parts[c];
		        		 entidad = entidad.replace(" ","");
		        		 boolean caramelo = true;
		        		 boolean slime = false;
		        		 boolean creeper = false;
		        		 switch(entidad) {
		        		 	case "g": color = Color.ESMERALDA;
		        		 	break;
		        		 	case "o": color = Color.HIERRO;
		        		 	break;
		        		 	case "r": color = Color.REDSTONE;
		        		 	break;
		        		 	case "y": color = Color.ORO;
		        		 	break;
		        		 	case "b": color = Color.LAPISLAZULI;
		        		 	break;
		        		 	case "v": color = Color.DIAMANTE;
		        		 	break;
		        		 	case "m": color = Color.OBSIDIANA;
		        		 	caramelo = false;
		        		 	break;
		        		 	case "gg": color = Color.ESMERALDA;
		        		 	slime = true;
		        		 	break;
		        		 	case "go": color = Color.HIERRO;
		        		 	slime = true;
		        		 	break;
		        		 	case "gr": color = Color.REDSTONE;
		        		 	slime = true;
		        		 	break;
		        		 	case "gy": color = Color.ORO;
		        		 	slime = true;
		        		 	break;
		        		 	case "gb": color = Color.LAPISLAZULI;
		        		 	slime = true;
		        		 	break;
		        		 	case "gv": color = Color.DIAMANTE;
		        		 	slime = true;
		        		 	break;
		        		 	case "gm": color = Color.OBSIDIANA;
		        		 	slime = true;
		        		 	caramelo = false;
		        		 	break;
		        		 	case "c" : creeper = true;
		        		 	case "a": color = entidad_random();
		        		 	break;
		        		 }
		        		 if(creeper)
		        			 t.agregar_entidad(new Creeper(f,c,dominio.imagenesDeCreeper(), 40));
		        		 else
		        		 if (caramelo) {
		        			 if(slime)
		        				 t.agregar_entidad(new Slime(f,c,new Mineral(f,c,color,dominio.imagenesDeCaramelos()),t,dominio.imagenesDeSlime()));
		        			 else
		        				 t.agregar_entidad(new Mineral(f,c, color,dominio.imagenesDeCaramelos())); 
		        		 }
		        		 else
		        			 if(slime)
		        				 t.agregar_entidad(new Slime(f,c,new Obsidiana(f,c, color,dominio.imagenesDeObsidiana()),t,dominio.imagenesDeSlime()));
		        			 else
		        				 t.agregar_entidad(new Obsidiana(f,c, color,dominio.imagenesDeObsidiana()));
		        	 }
		         }
		         tamaño = 0;
		         
			
		      }
		      catch(Exception e){
		         e.printStackTrace();
		      }finally{
		         // En el finally cerramos el fichero, para asegurarnos
		         // que se cierra tanto si todo va bien como si salta 
		         // una excepcion.
		         try{                    
		            if( null != fr ){   
		               fr.close();     
		            }                  
		         }catch (Exception e2){ 
		            e2.printStackTrace();
		         }
		      }
		return new Nivel(2,2,Integer.parseInt(vidas),Integer.parseInt(movimientos),Integer.parseInt(tiempo),objetivos);
	}
}