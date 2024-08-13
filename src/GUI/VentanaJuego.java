package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Logica.Juego;

public class VentanaJuego 
{
	private Juego mi_juego;
	private JFrame mainFrame;
	private JPanel selector_de_dominio, selector_de_niveles,panel_del_nivel, panel_ranking;
	private Dominio dominio;
	
	public VentanaJuego(Juego juego)
	{
		mi_juego = juego;
		mainFrame = new JFrame("Selector de dominio");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(600,600);
		mainFrame.setLayout(null);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
	
		selector_de_dominio = new SelectorDeDominio(this);
		mainFrame.add(selector_de_dominio);
		mainFrame.setVisible(true);
	}
	
	public void cerrarSelectorDeDominio()
	{
		selector_de_dominio.setVisible(false);
	}

	public void volverAlSelectorDeDominio(JPanel selector)
	{
		mainFrame.remove(selector);
		mainFrame.setTitle("Juego");
		selector_de_dominio.setVisible(true);
	}
	
	public void abrirSelectorDeNivel(Dominio dom) 
	{
		dominio = dom;
		mainFrame.setTitle(dominio.nombreDelDominio());
		mi_juego.setNombreDelJuego(dominio.nombreDelDominio());
		selector_de_niveles = new SelectorDeNivel(this,dominio.fondoDelSelectorDeNivel());
		mainFrame.add(selector_de_niveles);
		selector_de_niveles.requestFocusInWindow();
		mainFrame.revalidate();
        mainFrame.repaint();
	}

	public void cerrarSelectorDeNivel() {
		selector_de_niveles.setVisible(false);
	}
	
	public void abrirNivel(int numero_del_nivel) 
	{
		mainFrame.setTitle(dominio.nombreDelDominio() + " - Nivel " + numero_del_nivel);
		panel_del_nivel = mi_juego.iniciarJuego(numero_del_nivel, dominio.fondoDeNivel(numero_del_nivel),dominio);
		mainFrame.add(panel_del_nivel);
		panel_del_nivel.requestFocusInWindow();
		mainFrame.revalidate();
        mainFrame.repaint();
	}

	public void cerrarNivel(PanelDeNivel panel_del_nivel) 
	{
		mainFrame.remove(panel_del_nivel);
		mainFrame.setSize(600,600);
		mainFrame.setTitle(dominio.nombreDelDominio());
		selector_de_niveles.setVisible(true);
	}

	public void reiniciarNivel(int numero_del_nivel, String fondo,int vidas) 
	{
		mainFrame.remove(panel_del_nivel);
		panel_del_nivel = mi_juego.reiniciarJuego(numero_del_nivel, fondo, vidas);
		mainFrame.add(panel_del_nivel);
		panel_del_nivel.requestFocusInWindow();
		mainFrame.revalidate();
        mainFrame.repaint();
	}
	
	public void setBounds(int i, int j) {
		mainFrame.setSize(i ,j);
	}

	public void pasarNivel(int numero_del_nivel) 
	{
		mainFrame.remove(panel_del_nivel);
		panel_del_nivel = mi_juego.iniciarJuego(numero_del_nivel, dominio.fondoDeNivel(numero_del_nivel),dominio);
		mainFrame.add(panel_del_nivel);
		mainFrame.setTitle(dominio.nombreDelDominio() + " - Nivel " + numero_del_nivel);
		panel_del_nivel.requestFocusInWindow();
		mainFrame.revalidate();
        mainFrame.repaint();
	}
	
	public void abrirRanking()
	{
		selector_de_niveles.setVisible(false);
		mainFrame.setTitle(dominio.nombreDelDominio() + " - ranking");
		panel_ranking = new Ranking(this,dominio.fondoDelRanking());
		mainFrame.add(panel_ranking);
		mainFrame.revalidate();
        mainFrame.repaint();
	}
	
	public void cerrarRanking()
	{
		mainFrame.remove(panel_ranking);
		mainFrame.setTitle(dominio.nombreDelDominio());
		selector_de_niveles.setVisible(true);
	}
	
	public String getNombreDelJuego()
	{
		return dominio.nombreDelDominio();
	}
}