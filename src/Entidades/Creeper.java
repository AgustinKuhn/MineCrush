package Entidades;

import java.util.Timer;
import java.util.TimerTask;

import Logica.Color;

public class Creeper extends Entidad{
	
	private int tiempoRestante;
	private int tiempoInicial;
    private Timer temporizador;

    public Creeper(int f, int c, String path_img, int tiempoInicial) {
		super(f, c, Color.CREEPER, path_img);
		tiempoRestante = tiempoInicial;
		this.tiempoInicial = tiempoInicial;
        detonada = false;
        temporizador = new Timer();
        temporizador.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                disminuirTiempo();
            }
        }, 1000, 1000); // Reducción de tiempo cada segundo
        cargar_imagenes_representativas(path_img);
    }
	

	public boolean es_posible_intercambiar(Entidad e) {
		return e.puede_recibir(this);
	}

	public boolean puede_recibir(Mineral m) {
		return true;
	}

	public boolean puede_recibir(Obsidiana o) {
		return false;
	}
	
	public boolean puede_recibir(Potenciador p) {
		return true;
	}
	
	public boolean puede_recibir(Creeper c) {
		return true;
	}
	
	public boolean matcheable() {
		return false;
	}
	
	public void detonar() {
		detonada = true;
		entidad_grafica.notificarse_cambio_estado();
		temporizador.cancel();
	}

	private void disminuirTiempo() {
		tiempoRestante--;
		entidad_grafica.notificarse_cambio_estado();
		if (tiempoRestante == 0) 
			explotar();
	}
	
	private void explotar() {
		temporizador.cancel();
		entidad_grafica.notificar_timer_cero();
	}
	
	@Override
	public boolean derribable() {
		return true;
	}
	public String get_imagen_representativa() {
		int indice = indice_segun_porcentaje();
		int simple = indice*2;
		int resaltado = indice*2 + 1;
		if(!detonada) {
			if(!enfocada) {
				return imagenes_representativas[simple];
			}
			else
				return imagenes_representativas[resaltado];
		}
		return imagenes_representativas[12];
	}
	private void cargar_imagenes_representativas(String path_img) {
		imagenes_representativas = new String [13];
		imagenes_representativas[0] = path_img + color.getColor() +"-0.png";
		imagenes_representativas[1] = path_img + color.getColor() +"-0-resaltado.png";
		imagenes_representativas[2] = path_img + color.getColor() +"-1.png";
		imagenes_representativas[3] = path_img + color.getColor() +"-1-resaltado.png";
		imagenes_representativas[4] = path_img + color.getColor() +"-2.png";
		imagenes_representativas[5] = path_img + color.getColor() +"-2-resaltado.png";
		imagenes_representativas[6] = path_img + color.getColor() +"-3.png";
		imagenes_representativas[7] = path_img + color.getColor() +"-3-resaltado.png";
		imagenes_representativas[8] = path_img + color.getColor() +"-4.png";
		imagenes_representativas[9] = path_img + color.getColor() +"-4-resaltado.png";
		imagenes_representativas[10] = path_img + color.getColor() +"-5.png";
		imagenes_representativas[11] = path_img + color.getColor() +"-5-resaltado.png";
		imagenes_representativas[12] = "/imagenes/entidad-vacia/vacia.png"; // antes "/imagenes/especiales/" + Color.VACIO +".png"
		//imagenes_representativas[13] = "/imagenes/entidad-vacia/vacia-resaltado.png"; //antes "/imagenes/especiales/" + Color.VACIO +"-resaltado.png"
	}
	
	private int indice_segun_porcentaje() {
		int indice = 0;
		int porcentaje = get_porcentaje_faltante();
		if(porcentaje <= 5 )
			indice = 5;
		else
			if(porcentaje <= 20)
				indice = 4;
			else
				if(porcentaje <= 40)
					indice = 3;
				else
					if(porcentaje <= 60)
						indice = 2;
					else
						if(porcentaje <= 80)
							indice = 1;
		return indice;
	}
	
	private int get_porcentaje_faltante() {
		int porcentaje = (tiempoRestante * 100) / tiempoInicial;  
		return porcentaje;
	}
	
}

