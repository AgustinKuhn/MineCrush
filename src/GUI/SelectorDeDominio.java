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

public class SelectorDeDominio extends JPanel
{
	protected Image fondo;
	protected JButton btnDominio1, btnDominio2;
	protected VentanaJuego ventana;
	
	public SelectorDeDominio(VentanaJuego ventana) 
	{
		fondo = new ImageIcon(getClass().getResource("/Imagenes/Fondo-selector-dominio.png")).getImage();
		this.ventana = ventana;
		initialize();
	}
	
	public void initialize()
	{
		setSize(new Dimension(600, 600));
		setLayout(null);
		setFocusable(true);
		
		btnDominio1 = new JButton("Minecrush");
		btnDominio1.setBackground(new Color(192, 192, 192));
		btnDominio1.setFont(new Font("Impact", Font.PLAIN, 20));
		btnDominio1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonDominioXClicked(new Dominio("Minecrush"));
			}
		});
		btnDominio1.setBounds(51, 211, 484, 82);
		add(btnDominio1);
		
		btnDominio2 = new JButton("Mortalcrush");
		btnDominio2.setBackground(new Color(192, 192, 192));
		btnDominio2.setFont(new Font("Impact", Font.PLAIN, 20));
		btnDominio2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				botonDominioXClicked(new Dominio("Mortalcrush"));
			}
		});
		btnDominio2.setBounds(51, 322, 484, 82);
		add(btnDominio2);
		
	}
	
	public void botonDominioXClicked(Dominio dom) 
	{
		ventana.cerrarSelectorDeDominio();
		ventana.abrirSelectorDeNivel(dom);
	}
	
	public void paint(Graphics g) {
		g.drawImage(fondo,0,0, getWidth(), getHeight(), this);
		setOpaque(false);
		super.paint(g);
	}
}
