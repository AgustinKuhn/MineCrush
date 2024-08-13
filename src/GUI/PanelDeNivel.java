package GUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import Animadores.CentralAnimaciones;
import Logica.EntidadLogica;
import Logica.Juego;
import Logica.Reloj;

/**
 * Modela el comportamiento de la Ventana de la aplicación.
 * Ofrece servicios para comunicar los diferentes elementos que conforman la gráfica de la aplicación con la lógica de la misma.
 */
@SuppressWarnings("serial")
public class PanelDeNivel extends JPanel implements VentanaAnimable, VentanaNotificable{
	
	protected Juego mi_juego;
	protected CentralAnimaciones mi_animador;
	
	protected int filas, columnas, animaciones_pendientes, tiempoDelNivel;
	private int size_label = 60;
	private int movimientos, nivel, vidas, puntaje;
	
	protected boolean bloquear_intercambios, bloquear_movimientos;
	private boolean juego_pausado;

	protected JPanel panel_principal, informacion_de_nivel;
	protected JLabel lblMovimientos, lblTiempo, lblVidas, lblPuntaje, lblObjetivos;
	protected JButton btnVolver, btnSiguienteNivel;
	
	protected String fondo;
	protected Reloj reloj;
	
	protected VentanaJuego ventana_juego;
	
	/**
	 * Inicializa la ventana asociada al juego en progreso, considerando
	 * @param j El juego que controlará la lógica de la aplicación, y con quien comunicará los movimientos del jugador.
	 * @param f La cantidad de filas del tablero.
	 * @param c La cantidad de columnas del tablero.
	 */
	public PanelDeNivel(Juego j, int f, int c, String fondo,int nivel, VentanaJuego ventana_juego)  {
		mi_juego = j;
		filas = f;
		columnas = c;
		this.fondo = fondo;
		this.nivel = nivel;
		this.ventana_juego = ventana_juego;
		
		mi_animador = new CentralAnimaciones(this);
		vidas = j.getNivel().getVidas();
		tiempoDelNivel = j.getNivel().getTiempo(); 
		movimientos = j.getNivel().getMovimientos();
		puntaje = 0; //falta get puntaje
		juego_pausado = false;
		animaciones_pendientes = 0;
		bloquear_intercambios = false;
		bloquear_movimientos = false;
		reloj = new Reloj(this,tiempoDelNivel);
		inicializar();
	}
	
	protected void inicializar() {
		ventana_juego.setBounds(size_label * filas + 350 , size_label * columnas + 39);
		setSize(new Dimension(size_label * filas + 350 , size_label * columnas + 39));
		setLayout(null);
		
		
		panel_principal = new ImagenFondo();
		panel_principal.setSize(size_label * filas, size_label * columnas);
		panel_principal.setLayout(null);
		panel_principal.requestFocusInWindow();
		addKeyListener(new KeyAdapter()
		{
			
			public void keyPressed(KeyEvent e) 	
			{
				switch(e.getKeyCode()) {
					case KeyEvent.VK_LEFT: 	{ 
					if(!bloquear_movimientos) mi_juego.mover_jugador(Juego.IZQUIERDA);		
												break; }
					case KeyEvent.VK_RIGHT: { if(!bloquear_movimientos) mi_juego.mover_jugador(Juego.DERECHA); break; }
					case KeyEvent.VK_UP: 	{ if(!bloquear_movimientos) mi_juego.mover_jugador(Juego.ARRIBA);break; }
					case KeyEvent.VK_DOWN: 	{ if(!bloquear_movimientos) mi_juego.mover_jugador(Juego.ABAJO); break; }
					case KeyEvent.VK_W:		{ 
												if (!bloquear_intercambios) 
													if(mi_juego.intercambiar(Juego.ARRIBA))
													{
														resetearObjetivos();
														actualizarPuntaje();
													}
											break;}
					case KeyEvent.VK_S:		{ 
												if (!bloquear_intercambios) 
													if(mi_juego.intercambiar(Juego.ABAJO))
													{
														resetearObjetivos();
														actualizarPuntaje();
													}
											break;}
					case KeyEvent.VK_A:		{ 
												if (!bloquear_intercambios) 
													if(mi_juego.intercambiar(Juego.IZQUIERDA))
													{
														resetearObjetivos();
														actualizarPuntaje();
													}
											break;}
					case KeyEvent.VK_D:		{ 
												if (!bloquear_intercambios) 
													if(mi_juego.intercambiar(Juego.DERECHA))
													{
														resetearObjetivos();
														actualizarPuntaje();
													}
											break;}
				}
			}
		});
		
		add(panel_principal, BorderLayout.CENTER);
		
		//boton volver
		btnSiguienteNivel = new JButton("Siguiente nivel");
		btnSiguienteNivel.setEnabled(false);
		btnSiguienteNivel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						pasarNivel();
					}
				});
		btnSiguienteNivel.setBounds(size_label * filas + 10, size_label * columnas - 50 , 89 + 50, 23 + 15);
		add(btnSiguienteNivel);
				
		//boton volver
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volverAlSelectorDeNivel();
			}
		});
		btnVolver.setBounds(size_label * filas + 184, size_label * columnas - 50 , 89 + 50, 23 + 15);
		add(btnVolver);
		
		//Panel informacion de nivel
		informacion_de_nivel = new JPanel();
		informacion_de_nivel.setLayout(new BoxLayout(informacion_de_nivel, BoxLayout.Y_AXIS));			
		informacion_de_nivel.setBounds(size_label * filas , 0, 334, size_label * columnas); 
		informacion_de_nivel.setBackground(Color.gray);
		add(informacion_de_nivel);
			
		//vidas
	    lblVidas = new JLabel("Vidas restantes: " + vidas);
		lblVidas.setFont(new Font("Tahoma", Font.PLAIN, 20));
		informacion_de_nivel.add(lblVidas, BorderLayout.EAST);
		
		//Temporizador
		lblTiempo = new JLabel("Tiempo restante: --/-- ");
		lblTiempo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		informacion_de_nivel.add(lblTiempo, BorderLayout.EAST);
		
		reloj.comenzarReloj();
		
	    //Movimientos    
	    lblMovimientos = new JLabel("Movimientos restantes: " + movimientos);
	    lblMovimientos.setFont(new Font("Tahoma", Font.PLAIN, 20));
	    informacion_de_nivel.add(lblMovimientos, BorderLayout.SOUTH);
		
	    lblPuntaje = new JLabel("Puntaje: " + puntaje);
		lblPuntaje.setFont(new Font("Tahoma", Font.PLAIN, 20));
		informacion_de_nivel.add(lblPuntaje, BorderLayout.EAST);
		
	    //Objetivos
		lblObjetivos = new JLabel("<html>Minerales restantes: <br>"+ mi_juego.getNivel().getObjetivos().getObjetivos() +"</html>");
		lblObjetivos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		informacion_de_nivel.add(lblObjetivos, BorderLayout.EAST);
		
	}
	
	public void mostrarTiempo(String tiempoRestante)
	{
		lblTiempo.setText(tiempoRestante);
	}
	/**
	 * Crea una nueva celda, que quedará asociada a la entidad lógica parametrizada, a partir de la ubicación de esta.
	 * Agrega y deja visible la celda creada, por sobre la pantalla.
	 * @param e Entidad lógica con la que quedará asociada la celda.
	 * @return La entidad gráfica creada.
	 */
	public EntidadGrafica agregar_entidad(EntidadLogica e) {
		Celda celda = new Celda(this, e, size_label);
		panel_principal.add(celda);
		return celda;
	}
	
	@Override
	public void notificarse_animacion_en_progreso() {
		synchronized(this){
			animaciones_pendientes ++;
			bloquear_intercambios = true;
			bloquear_movimientos = true;
		}
	}
	
	@Override
	public void notificarse_animacion_finalizada() {
		synchronized(this){
			animaciones_pendientes --;
			bloquear_intercambios = animaciones_pendientes > 0;
			bloquear_movimientos = animaciones_pendientes > 0;
		}
	}
	
	@Override
	public void animar_movimiento(Celda c) {
		mi_animador.animar_cambio_posicion(c);
	}
	
	@Override
	public void animar_cambio_estado(Celda c) {
		mi_animador.animar_cambio_estado(c);
	}
	
	@Override
	public void animar_spawn_entidad(Celda c) {
		mi_animador.animar_spawn_entidad(c);
	}
	
	public void animar_timer_cero(Celda c) {
		vidas = 0;
		JOptionPane.showMessageDialog(null, "La bomba exploto", null, JOptionPane.INFORMATION_MESSAGE);
		volverAlSelectorDeNivel();
	}
	
	private void resetearObjetivos()
	{
		String objetivos = mi_juego.mostrar_objetivos();
		if(mi_juego.sinObjetivos())
		{
			lblObjetivos.setText("<html>Minerales restantes: <br> &emsp; ¡Ya minaste todos! </html>");
			mi_juego.juego_ganado();
		}
		else
			lblObjetivos.setText("<html>Minerales restantes: <br>"+ objetivos +"</html>");
	}
	
	private void actualizarPuntaje()
	{
		puntaje = mi_juego.getPuntuacion();
		lblPuntaje.setText("Puntaje: " + puntaje);
	}
	
	public void eliminar1Movimiento()
	{
		if(!juego_pausado)
		{
			--movimientos;
			lblMovimientos.setText("Movimientos restantes: " + movimientos);
			if(movimientos < 1)
			{
				reloj.pararReloj();
				JOptionPane.showMessageDialog(null, "Te quedaste sin movimientos", null, JOptionPane.INFORMATION_MESSAGE);
				reintentar();
			}
		}
	}
	
	public void reintentar()
	{
		if(vidas == 0)
		{
			JOptionPane.showMessageDialog(null, "Te quedaste sin vidas", null, JOptionPane.INFORMATION_MESSAGE);
			volverAlSelectorDeNivel();
		}
		else
		{
			int n = JOptionPane.showConfirmDialog(null, "¿Reintentar?",null, JOptionPane.YES_NO_OPTION);
			if(n == 1 || n == -1)
			{
				volverAlSelectorDeNivel();
			}
			else
			{
				ventana_juego.reiniciarNivel(nivel,fondo,--vidas);
			}
		}
	}
	
	private void volverAlSelectorDeNivel()
	{
		reloj.pararReloj();
		ventana_juego.cerrarNivel(this);
	}
	
	public void activarPasarNivel() 
	{
		pausarJuego();
		mi_juego.chequearPuntuacion(puntaje);
		btnSiguienteNivel.setEnabled(true);
	}
	
	public String preguntarSiQuiereGuardarPuntaje()
	{
		String respuesta = "";
		int n = JOptionPane.showConfirmDialog(null, "Tu puntuación entro al ranking, quieres guardar tu puntaje?",null, JOptionPane.YES_NO_OPTION);
		if(n == 1 || n == -1)
		{
			
		}
		else
		{
			respuesta = JOptionPane.showInputDialog("¿Como te llamas?");
		}
		return respuesta;
	}
	public void ganarJuego()
	{
		JOptionPane.showMessageDialog(null,"Ganaste el juego", null, JOptionPane.INFORMATION_MESSAGE);
		volverAlSelectorDeNivel();
	}
	
	private void pasarNivel()
	{
		ventana_juego.pasarNivel(++nivel);
	}
	
	private void pausarJuego()
	{
		juego_pausado = true;
		reloj.pararReloj();
	}
	
	class ImagenFondo extends JPanel{
		private Image imagen;
		public void paint(Graphics g) {
			imagen = new ImageIcon(getClass().getResource(fondo)).getImage();
			g.drawImage(imagen,0,0, panel_principal.getWidth(),panel_principal.getHeight(),this);
			setOpaque(false);
			super.paint(g);
		}
	}

	public void terminoElTiempo() 
	{
		JOptionPane.showMessageDialog(null, "¡Tiempo agotado!", null, JOptionPane.INFORMATION_MESSAGE);
	}

	
}