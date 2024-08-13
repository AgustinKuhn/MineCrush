package Logica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import GUI.PanelDeNivel;

public class Reloj 
{
	private Timer timer;
	private int tiempoRestante;
	private PanelDeNivel nivel;
	
	public Reloj(PanelDeNivel nivel, int tiempo)
	{
		this.nivel = nivel;
		tiempoRestante = tiempo;
	}
	
	public void comenzarReloj()
	{
		timer = new Timer(1000, new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (tiempoRestante > 0) 
				{
					tiempoRestante--;
					nivel.mostrarTiempo(formatoTiempo(tiempoRestante));
	            }
				else 
	            {
					timer.stop();
					nivel.terminoElTiempo();
					nivel.reintentar();
	            }
			}
		});
		timer.start();
	}
	
	public void comenzarRelojDeEntidad()
	{
		timer = new Timer(1000, new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (tiempoRestante > 0) 
				{
					tiempoRestante--;
	            }
				else 
	            {
					timer.stop();
					
					/* decirle al nivel que finalice
					nivel.reintentar();*/
	            }
			}
		});
		timer.start();
	}
	
	private String formatoTiempo(int segundos) 
	{
	        int minutos = segundos / 60;
	        segundos = segundos % 60;
	        return String.format("Tiempo restante: %02d:%02d", minutos, segundos);
	}
	
	public void pararReloj()
	{
		timer.stop();
	}
	
}