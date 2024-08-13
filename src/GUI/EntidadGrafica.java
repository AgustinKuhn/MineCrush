package GUI;

import Entidades.Entidad;

/**
 * Define los mensajes posibles de solicitar por sobre las entidades gráficas de la aplicación.
 */
public interface EntidadGrafica {
	/**
	 * Notifica a la entidad gráfica de que, la entidad lógica, modificó su estado.
	 * Desencadena la actualización de la entidad gráfica en la ventana de la aplicación, considerando el nuevo estado.
	 */
	public void notificarse_cambio_estado();
	
	/**
	 * Notifica a la entidad gráfica de que, la entidad lógica, modificó su posición.
	 * Desencadena la actualización de la entidad gráfica en la ventana de la aplicación, considerando su nueva posición, y animando este
	 * movimiento.
	 */
	public void notificarse_cambio_posicion();
	
	public void notificarse_spawn_entidad();
	
	public void cambiar_entidad_logica(Entidad e);

	public void notificar_timer_cero();	
}