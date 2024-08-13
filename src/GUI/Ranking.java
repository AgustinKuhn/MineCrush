package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class Ranking extends JPanel
{
	protected Image fondo;
	protected JButton btnVolver;
	protected JLabel lblPuesto1, lblPuesto2, lblPuesto3, lblPuesto4, lblPuesto5;
	protected VentanaJuego ventana;
	
	public Ranking(VentanaJuego ventana,String fondo) 
	{
		this.fondo = new ImageIcon(getClass().getResource(fondo)).getImage();
		this.ventana = ventana;
		initialize();
	}
	
	public void initialize()
	{
		setSize(new Dimension(600, 600));
		setLayout(null);
		
		String archivo = "src/Ranking/Ranking"+ventana.getNombreDelJuego()+".txt";
		try {
		BufferedReader br = new BufferedReader(new FileReader(archivo));
		String linea; 
		String[] parts = null;
		linea = br.readLine();
		parts = linea.split(":");
		
		//boton volver
		btnVolver = new JButton("Selector de nivel");
		btnVolver.setBackground(new Color(192, 192, 192));
		btnVolver.setFont(new Font("Impact", Font.PLAIN, 20));
		btnVolver.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
							volver();
						}
		});
		btnVolver.setBounds(190, 489 , 220, 45);
		add(btnVolver);
		
		lblPuesto1 = new JLabel("#1 - " + parts[0] + " - " + parts[1]);
		lblPuesto1.setForeground(new Color(255, 255, 0));
		lblPuesto1.setFont(new Font("Impact", Font.PLAIN, 50));
		lblPuesto1.setBounds(75, 181, 450, 45);
		add(lblPuesto1);
		
		linea = br.readLine();
		parts = linea.split(":");
		
		lblPuesto2 = new JLabel("#2 - " + parts[0] + " - " + parts[1]);
		lblPuesto2.setForeground(new Color(255, 255, 0));
		lblPuesto2.setFont(new Font("Impact", Font.PLAIN, 50));
		lblPuesto2.setBounds(75, 237, 450, 45);
		add(lblPuesto2);
		
		linea = br.readLine();
		parts = linea.split(":");
		
		lblPuesto3 = new JLabel("#3 - " + parts[0] + " - " + parts[1]);
		lblPuesto3.setForeground(new Color(255, 255, 0));
		lblPuesto3.setFont(new Font("Impact", Font.PLAIN, 50));
		lblPuesto3.setBounds(75, 286, 450, 45);
		add(lblPuesto3);
		
		linea = br.readLine();
		parts = linea.split(":");
		
		lblPuesto4 = new JLabel("#4 - " + parts[0] + " - " + parts[1]);
		lblPuesto4.setForeground(new Color(255, 255, 0));
		lblPuesto4.setFont(new Font("Impact", Font.PLAIN, 50));
		lblPuesto4.setBounds(75, 338, 450, 45);
		add(lblPuesto4);
		
		linea = br.readLine();
		parts = linea.split(":");
		
		lblPuesto5 = new JLabel("#5 - " + parts[0] + " - " + parts[1]);
		lblPuesto5.setForeground(new Color(255, 255, 0));
		lblPuesto5.setFont(new Font("Impact", Font.PLAIN, 50));
		lblPuesto5.setBounds(75, 394, 450, 45);
		add(lblPuesto5);
		
		br.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void volver() {
		ventana.cerrarRanking();
	}
	public void paint(Graphics g) {
		g.drawImage(fondo,0,0, getWidth(), getHeight(), this);
		setOpaque(false);
		super.paint(g);
	}
}