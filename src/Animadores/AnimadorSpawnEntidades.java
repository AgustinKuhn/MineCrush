package Animadores;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import GUI.Celda;

public class AnimadorSpawnEntidades extends Thread implements Animador{
	
	protected ManejadorAnimaciones mi_manager;
	protected Celda mi_celda_animada;
	protected int delay;
	protected String path_img;
	
	public AnimadorSpawnEntidades(ManejadorAnimaciones m, int d, Celda c) {
		mi_manager = m;
		mi_celda_animada = c;
		
		delay = d;
		
		path_img = c.get_entidad_logica().get_imagen_representativa();
	}
	
	@Override
	public Celda get_celda_asociada() {
		return mi_celda_animada;
	}

	@Override
	public void comenzar_animacion() {
		this.start();
	}
	
	public void run() {
		
		int size_label = mi_celda_animada.get_size_label();
		ImageIcon imgIcon = new ImageIcon(this.getClass().getResource(path_img));
		Image imgEscalada = imgIcon.getImage().getScaledInstance(size_label, size_label, Image.SCALE_SMOOTH);
		Icon iconoEscalado = new ImageIcon(imgEscalada);
		mi_celda_animada.setIcon(iconoEscalado);
		
		mi_manager.notificarse_finalizacion_animacion(this);
	
	}
}