package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;


import java.awt.Font;

public class SelectorDeNivel extends JPanel
{
	protected Image fondo;
	protected JButton btnNivel1, btnNivel2, btnNivel3, btnNivel4, btnNivel5, btnNivel6,btnVolver, btnRanking;
	protected VentanaJuego ventana;
	
	public SelectorDeNivel(VentanaJuego ventana, String fondo) 
	{
		this.ventana = ventana;
		this.fondo = new ImageIcon(getClass().getResource(fondo)).getImage();
		initialize();
	}
	
	public void initialize()
	{
		setSize(new Dimension(600, 600));
		setLayout(null);
		
		btnNivel1 = new JButton("Nivel 1");
		btnNivel1.setBackground(new Color(192, 192, 192));
		btnNivel1.setFont(new Font("Impact", Font.PLAIN, 20));
		btnNivel1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonNivelXClicked(1);
			}
		});
		btnNivel1.setBounds(75, 153, 450, 45);
		add(btnNivel1);
		
		btnNivel2 = new JButton("Nivel 2");
		btnNivel2.setBackground(new Color(192, 192, 192));
		btnNivel2.setFont(new Font("Impact", Font.PLAIN, 20));
		btnNivel2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				botonNivelXClicked(2);
			}
		});
		btnNivel2.setBounds(75, 209, 450, 45);
		add(btnNivel2);
		
		btnNivel3 = new JButton("Nivel 3");
		btnNivel3.setBackground(new Color(192, 192, 192));
		btnNivel3.setFont(new Font("Impact", Font.PLAIN, 20));
		btnNivel3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				botonNivelXClicked(3);
			}
		});
		btnNivel3.setBounds(75, 265, 450, 45);
		add(btnNivel3);
		
		btnNivel4 = new JButton("Nivel 4");
		btnNivel4.setBackground(new Color(192, 192, 192));
		btnNivel4.setFont(new Font("Impact", Font.PLAIN, 20));
		btnNivel4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				botonNivelXClicked(4);
			}
		});
		btnNivel4.setBounds(75, 321, 450, 45);
		add(btnNivel4);
		
		btnNivel5 = new JButton("Nivel 5");
		btnNivel5.setBackground(new Color(192, 192, 192));
		btnNivel5.setFont(new Font("Impact", Font.PLAIN, 20));
		btnNivel5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				botonNivelXClicked(5);
			}
		});
		btnNivel5.setBounds(75, 377, 450, 45);
		add(btnNivel5);
		
		btnNivel6 = new JButton("Nivel 6");
		btnNivel6.setBackground(new Color(192, 192, 192));
		btnNivel6.setFont(new Font("Impact", Font.PLAIN, 20));
		btnNivel6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				botonNivelXClicked(6);
			}
		});
		btnNivel6.setBounds(75, 433, 450, 45);
		add(btnNivel6);
		
		//boton volver
		btnVolver = new JButton("Selector de dominio");
		btnVolver.setBackground(new Color(192, 192, 192));
		btnVolver.setFont(new Font("Impact", Font.PLAIN, 20));
		btnVolver.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
						volver();
					}
		});
		btnVolver.setBounds(75, 489 , 220, 45);
		add(btnVolver);
		
		btnRanking = new JButton("Ranking");
		btnRanking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ranking();
			}
		});
		btnRanking.setFont(new Font("Impact", Font.PLAIN, 20));
		btnRanking.setBackground(Color.LIGHT_GRAY);
		btnRanking.setBounds(305, 489, 220, 45);
		add(btnRanking);
		
	}
	
	public void botonNivelXClicked(int numero_del_nivel) 
	{
		ventana.cerrarSelectorDeNivel();
		ventana.abrirNivel(numero_del_nivel);
	}
	
	public void volver() 
	{
		ventana.cerrarSelectorDeNivel();
		ventana.volverAlSelectorDeDominio(this);
	}
	
	public void ranking()
	{
		ventana.abrirRanking();
	}
	public void paint(Graphics g) {
		g.drawImage(fondo,0,0, getWidth(), getHeight(), this);
		setOpaque(false);
		super.paint(g);
	}
}